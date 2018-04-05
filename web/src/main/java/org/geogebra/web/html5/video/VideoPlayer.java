package org.geogebra.web.html5.video;

import org.geogebra.common.kernel.geos.GeoVideo;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Frame;

/**
 * Represents a placeholder for videos.
 * 
 * @author Laszlo Gal
 *
 */
public class VideoPlayer extends Frame {
	private GeoVideo video;

	/**
	 * Constructor.
	 * 
	 * @param video
	 *            the video object.
	 */
	public VideoPlayer(GeoVideo video) {
		super(video.getEmbeddedUrl());
		this.video = video;
		addStyleName("mowVideo");
		getElement().setId(video.getYouTubeId());
	}
	
	/**
	 * Updates the player based on video object.
	 */
	public void update() {
		Style style = getElement().getStyle();
		style.setLeft(video.getAbsoluteScreenLocX(), Unit.PX);
		style.setTop(video.getAbsoluteScreenLocY(), Unit.PX);
		if (video.isPlaying()) {
			String embed = video.getEmbeddedUrl();
			removeStyleName("hidden");
			setUrl(embed);
			setWidth(video.getWidth() + "px");
			setHeight(video.getHeight() + "px");
			video.setChanged(false);
		} else {
			addStyleName("hidden");
		}
	}
}

