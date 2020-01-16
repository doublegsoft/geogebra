package org.geogebra.common.main.settings;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.common.io.layout.Perspective;
import org.geogebra.common.main.AppConfigDefault;

/**
 * Config for Mebis Notes and GGB Notes
 *
 */
public class AppConfigNotes extends AppConfigDefault {

	@Override
	public String getPreferencesKey() {
		return "_notes";
	}

	@Override
	public String getForcedPerspective() {
		return Perspective.NOTES + "";
	}

	@Override
	public String getAppTitle() {
		return "Notes";
	}

	@Override
	public String getAppCode() {
		return "notes";
	}

	@Override
	public String getTutorialKey() {
		return "notes_tutorials";
	}

	@Override
	public GeoGebraConstants.Version getVersion() {
		return GeoGebraConstants.Version.NOTES;
	}

	@Override
	public int getEnforcedLineEquationForm() {
		return -1;
	}

	@Override
	public int getEnforcedConicEquationForm() {
		return -1;
	}

	@Override
	public boolean shouldHideEquations() {
		return false;
	}
}
