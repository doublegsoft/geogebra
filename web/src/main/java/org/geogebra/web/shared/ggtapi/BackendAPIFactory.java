package org.geogebra.web.shared.ggtapi;

import org.geogebra.common.main.Feature;
import org.geogebra.common.move.ggtapi.models.MarvlAPI;
import org.geogebra.common.move.ggtapi.models.MowBAPI;
import org.geogebra.common.move.ggtapi.operations.BackendAPI;
import org.geogebra.common.util.StringUtil;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.util.ArticleElementInterface;
import org.geogebra.web.shared.ggtapi.models.GeoGebraTubeAPIW;
import org.geogebra.web.shared.ggtapi.models.NotesTubeApi;

/**
 * Class to provide the right backend API
 * for the given application.
 *
 * @author laszlo
 */
public class BackendAPIFactory {
	private AppW app;
	private ArticleElementInterface articleElement;
	private BackendAPI api = null;
	private String backendURL;

	/**
	 *
	 * @param app The appication.
	 */
	BackendAPIFactory(AppW app) {
		this.app = app;
		articleElement = app.getArticleElement();
		backendURL = articleElement.getParamBackendURL();
	}

	/**
	 *
	 * @return the backend API suitable for the applicaion.
	 */
	public BackendAPI get() {
		if (api == null) {
			api = hasBackendURL() ? newMowBAPI() : newGeoGebraAPI(); ;
		}

		api.setClient(app.getClientInfo());
		return this.api;
	}

	private BackendAPI newGeoGebraAPI() {
		return app.isWhiteboardActive() ? newNotesAPI(): newTubeAPI();
	}

	private boolean hasBackendURL() {
		return !StringUtil.empty(backendURL);
	}

	private BackendAPI newMowBAPI() {
		return new MowBAPI(backendURL, new MarvlURLChecker());
	}

	private BackendAPI newMarvlAPI() {
		return new MarvlAPI(new MarvlURLChecker());
	}

	private BackendAPI newNotesAPI() {
		return new NotesTubeApi(newTubeAPI());
	}
	private GeoGebraTubeAPIW newTubeAPI() {
		return new GeoGebraTubeAPIW(app.getClientInfo(),
				app.has(Feature.TUBE_BETA),
				articleElement);
	}
}
