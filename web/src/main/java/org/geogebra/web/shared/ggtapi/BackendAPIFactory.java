package org.geogebra.web.shared.ggtapi;

import org.geogebra.common.main.Feature;
import org.geogebra.common.move.ggtapi.models.MarvlAPI;
import org.geogebra.common.move.ggtapi.models.MowBAPI;
import org.geogebra.common.move.ggtapi.operations.BackendAPI;
import org.geogebra.common.util.StringUtil;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.util.ArticleElementInterface;
import org.geogebra.web.shared.ggtapi.models.GeoGebraTubeAPIW;

public class BackendAPIFactory {
	private AppW app;
	private ArticleElementInterface articleElement;
	private BackendAPI api = null;
	private String backendURL;

	BackendAPIFactory(AppW app) {
		this.app = app;
		articleElement = app.getArticleElement();
		backendURL = articleElement.getParamBackendURL();
	}

	public BackendAPI get() {
		if (api == null) {
			api = hasBackendURL() ? newMowBAPI() : newGeoGebraAPI(); ;
		}

		api.setClient(app.getClientInfo());
		return this.api;
	}

	private BackendAPI newGeoGebraAPI() {
		return app.isWhiteboardActive() ? newMarvlAPI(): newTubeAPI();
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

	private BackendAPI newTubeAPI() {
		return new GeoGebraTubeAPIW(app.getClientInfo(),
				app.has(Feature.TUBE_BETA),
				articleElement);
	}
}
