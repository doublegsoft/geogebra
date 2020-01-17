package org.geogebra.common.euclidian.draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.geogebra.common.awt.GAffineTransform;
import org.geogebra.common.awt.GGraphics2D;
import org.geogebra.common.awt.GPoint2D;
import org.geogebra.common.awt.GRectangle;
import org.geogebra.common.euclidian.Drawable;
import org.geogebra.common.euclidian.EuclidianBoundingBoxHandler;
import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.euclidian.RemoveNeeded;
import org.geogebra.common.euclidian.TextBoundingBox;
import org.geogebra.common.euclidian.text.InlineTextController;
import org.geogebra.common.factories.AwtFactory;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoInlineText;
import org.geogebra.common.util.debug.Log;

/**
 * Class that handles drawing inline text elements.
 */
public class DrawInlineText extends Drawable implements RemoveNeeded, DrawWidget {

	public static final int PADDING = 8;

	private GeoInlineText text;
	private InlineTextController textController;

	private GAffineTransform directTransform;
	private GAffineTransform inverseTransform;

	private GPoint2D corner0;
	private GPoint2D corner1;
	private GPoint2D corner2;
	private GPoint2D corner3;

	private TextBoundingBox boundingBox;

	/**
	 * Create a new DrawInlineText instance.
	 *
	 * @param view view
	 * @param text geo element
	 */
	public DrawInlineText(EuclidianView view, GeoInlineText text) {
		super(view, text);

		this.text = text;
		this.textController = view.createInlineTextController(text);
		createEditor();
		update();
	}

	private void createEditor() {
		if (textController != null) {
			textController.create();
		}
	}

	@Override
	public void update() {
		updateTransforms();

		GPoint2D point = text.getLocation();
		double angle = text.getAngle();
		double width = text.getWidth();
		double height = text.getHeight();

		if (textController != null) {
			textController.setLocation(view.toScreenCoordX(point.getX()),
					view.toScreenCoordY(point.getY()));
			textController.setHeight((int) (height - 2 * PADDING));
			textController.setWidth((int) (width - 2 * PADDING));
			textController.setAngle(angle);
			if (text.updateFontSize()) {
				textController.updateContent();
			}
		}
	}

	private void updateTransforms() {
		GPoint2D point = text.getLocation();

		double angle = text.getAngle();
		double width = text.getWidth();
		double height = text.getHeight();

		directTransform = AwtFactory.getPrototype().newAffineTransform();
		directTransform.translate(view.toScreenCoordXd(point.getX()),
				view.toScreenCoordYd(point.getY()));
		directTransform.rotate(angle);
		directTransform.scale(width, height);

		try {
			inverseTransform = directTransform.createInverse();
		} catch (Exception e) {
			Log.error(e.getMessage());
		}

		corner0 = directTransform.transform(new GPoint2D.Double(0, 0), null);
		corner1 = directTransform.transform(new GPoint2D.Double(1, 0), null);
		corner2 = directTransform.transform(new GPoint2D.Double(1, 1), null);
		corner3 = directTransform.transform(new GPoint2D.Double(0, 1), null);

		if (boundingBox != null) {
			boundingBox.setRectangle(getBounds());
			boundingBox.setTransform(directTransform);
		}
	}

	/**
	 * Send this to background
	 */
	public void toBackground() {
		if (textController != null) {
			textController.toBackground();
		}
	}

	/**
	 * Send this to foreground
	 * @param x x mouse coordinates in pixels
	 * @param y y mouse coordinates in pixels
	 */
	public void toForeground(int x, int y) {
		if (textController != null) {
			textController.toForeground(x, y);
		}
	}

	@Override
	public GRectangle getBounds() {
		return AwtFactory.getPrototype().newRectangle(getLeft(), getTop(), getWidth(), getHeight());
	}

	@Override
	public TextBoundingBox getBoundingBox() {
		if (boundingBox == null) {
			boundingBox = new TextBoundingBox();
			boundingBox.setColor(view.getApplication().getPrimaryColor());
		}
		boundingBox.updateFrom(geo);
		return boundingBox;
	}

	@Override
	public double getWidthThreshold() {
		if (text.getHeight() - text.getMinHeight() < 2) {
			return text.getWidth();
		}

		return GeoInlineText.DEFAULT_WIDTH;
	}

	@Override
	public double getHeightThreshold() {
		return text.getMinHeight();
	}

	@Override
	public void draw(GGraphics2D g2) {
		if (textController != null) {
			textController.draw(g2);
		}
	}

	@Override
	public boolean hit(int x, int y, int hitThreshold) {
		GPoint2D p = inverseTransform.transform(new GPoint2D.Double(x, y), null);
		return 0 < p.getX() && p.getX() < 1 && 0 < p.getY() && p.getY() < 1;
	}

	@Override
	public boolean isInside(GRectangle rect) {
		return rect.contains(getBounds());
	}

	@Override
	public GeoElement getGeoElement() {
		return geo;
	}

	@Override
	public void remove() {
		if (textController != null) {
			textController.discard();
		}
	}

	@Override
	public void setWidth(int newWidth) {
		text.setWidth(newWidth);
		if (textController != null) {
			textController.setWidth(newWidth);
		}
	}

	@Override
	public void setHeight(int newHeight) {
		text.setHeight(newHeight);
		if (textController != null) {
			textController.setHeight(newHeight);
		}
	}

	@Override
	public int getLeft() {
		return (int) Math.min(Math.min(corner0.getX(), corner1.getX()),
				Math.min(corner2.getX(), corner3.getX()));
	}

	@Override
	public int getTop() {
		return (int) Math.min(Math.min(corner0.getY(), corner1.getY()),
				Math.min(corner2.getY(), corner3.getY()));
	}

	@Override
	public void setAbsoluteScreenLoc(int x, int y) {
		// Not implemented
	}

	@Override
	public double getOriginalRatio() {
		return 0;
	}

	@Override
	public int getWidth() {
		return (int) (Math.max(Math.max(corner0.getX(), corner1.getX()),
				Math.max(corner2.getX(), corner3.getX()))
				- Math.min(Math.min(corner0.getX(), corner1.getX()),
				Math.min(corner2.getX(), corner3.getX())));
	}

	@Override
	public int getHeight() {
		return (int) (Math.max(Math.max(corner0.getY(), corner1.getY()),
				Math.max(corner2.getY(), corner3.getY()))
				- Math.min(Math.min(corner0.getY(), corner1.getY()),
				Math.min(corner2.getY(), corner3.getY())));
	}

	@Override
	public void resetRatio() {
		// Not implemented
	}

	@Override
	public boolean isFixedRatio() {
		return false;
	}

	@Override
	public void updateByBoundingBoxResize(GPoint2D point, EuclidianBoundingBoxHandler handler) {
		GPoint2D transformed = inverseTransform.transform(point, null);

		double x = 0;
		double y = 0;
		double width = text.getWidth();
		double height = text.getHeight();

		if (handler.getDx() == 1) {
			width *= transformed.getX();
		} else if (handler.getDx() == -1) {
			width *= 1 - transformed.getX();
			x = transformed.getX();
		}

		if (handler.getDy() == 1) {
			height *= transformed.getY();
		} else if (handler.getDy() == -1) {
			height *= 1 - transformed.getY();
			y = transformed.getY();
		}

		if (height < text.getMinHeight() && width < text.getWidth()) {
			return;
		}

		if (height < text.getMinHeight()) {
			if (y != 0) {
				y = 1 - text.getMinHeight() / text.getHeight();
			}
			height = text.getMinHeight();
		}

		if (width < GeoInlineText.DEFAULT_WIDTH) {
			if (x != 0) {
				x = 1 - GeoInlineText.DEFAULT_WIDTH / text.getWidth();
			}
			width = GeoInlineText.DEFAULT_WIDTH;
		}

		GPoint2D origin = directTransform.transform(new GPoint2D.Double(x, y), null);

		text.setLocation(new GPoint2D.Double(view.toRealWorldCoordX(origin.getX()),
				view.toRealWorldCoordY(origin.getY())));
		text.setWidth(width);
		text.setHeight(height);
		text.updateRepaint();

		updateTransforms();
	}

	@Override
	public void fromPoints(ArrayList<GPoint2D> points) {
		double newAngle = Math.atan2(points.get(1).getY() - points.get(0).getY(),
				points.get(1).getX() - points.get(0).getX());

		double newWidth = Math.max(GeoInlineText.DEFAULT_WIDTH,
				points.get(1).distance(points.get(0)));
		double newHeight = Math.max(text.getMinHeight(),
				points.get(2).distance(points.get(0)));

		text.setWidth(newWidth);
		text.setHeight(newHeight);
		text.setAngle(newAngle);
		text.setLocation(
				AwtFactory.getPrototype().newPoint2D(
						view.toRealWorldCoordX(points.get(0).getX()),
						view.toRealWorldCoordY(points.get(0).getY())
				)
		);
	}

	@Override
	protected List<GPoint2D> toPoints() {
		return Arrays.asList(corner0, corner1, corner3);
	}

	/**
	 * @param key
	 *            formatting option
	 * @param val
	 *            value (String, int or bool, depending on key)
	 */
	public void format(String key, Object val) {
		if (textController != null) {
			textController.format(key, val);
		}
	}

	/**
	 * @param key formatting option name
	 * @param fallback fallback when not set / indeterminate
	 * @param <T> option type
	 * @return formatting option value or fallback
	 */
	public <T> T getFormat(String key, T fallback) {
		if (textController != null) {
			return textController.getFormat(key, fallback);
		}
		return fallback;
	}
}
