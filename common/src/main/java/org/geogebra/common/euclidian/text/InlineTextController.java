package org.geogebra.common.euclidian.text;

import org.geogebra.common.awt.GGraphics2D;

/**
 * Controller for the inline text editor.
 */
public interface InlineTextController {

	/**
	 * Create the inline text editor.
	 */
	void create();

	/**
	 * Discard the inline text editor.
	 */
	void discard();

	/**
	 * Set the location of the text editor.
	 *
	 * @param x top coordinate
	 * @param y left coordinate
	 */
	void setLocation(int x, int y);

	/**
	 * Set the width of the editor.
	 *
	 * @param width width
	 */
	void setWidth(int width);

	/**
	 * Set the height of the editor.
	 *
	 * @param height height
	 */
	void setHeight(int height);

	void setAngle(double angle);

	/**
	 * Put the editor behind the canvas
	 */
	void toBackground();

	/**
	 * Bring the editor to the foreground and start editing
	 */
	void toForeground(int x, int y);

	/**
	 * @param key
	 *            property name
	 * @param val
	 *            property value
	 */
	void format(String key, Object val);

	/**
	 * Set content from geo
	 */
	void updateContent();

	<T> T getFormat(String key, T fallback);

	void draw(GGraphics2D g2);
}
