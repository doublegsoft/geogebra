package org.geogebra.desktop.factories;

import org.geogebra.common.euclidian.Drawable;
import org.geogebra.common.euclidian.EuclidianController;
import org.geogebra.common.gui.inputfield.AutoCompleteTextField;
import org.geogebra.common.javax.swing.GBox;
import org.geogebra.common.javax.swing.GLabel;
import org.geogebra.common.javax.swing.GPanel;
import org.geogebra.common.main.App;
import org.geogebra.desktop.gui.inputfield.AutoCompleteTextFieldD;
import org.geogebra.desktop.javax.swing.BoxD;
import org.geogebra.desktop.javax.swing.GComboBoxD;
import org.geogebra.desktop.javax.swing.GLabelD;
import org.geogebra.desktop.javax.swing.GPanelD;

public class SwingFactoryD extends org.geogebra.common.factories.SwingFactory {

	// TODO: find another place for this function
	@Override
	public AutoCompleteTextField newAutoCompleteTextField(int length,
			App application, Drawable drawTextField) {
		return new AutoCompleteTextFieldD(length,
				application, drawTextField);
	}

	@Override
	public GLabel newJLabel(String string, boolean bool) {
		return new GLabelD(string);
	}

	@Override
	public GBox createHorizontalBox(EuclidianController style) {
		return new BoxD(
				javax.swing.Box.createHorizontalBox());
	}

	@Override
	public GComboBoxD newJComboBox(App app, int ev) {
		return new GComboBoxD();
	}

	@Override
	public GPanel newGPanel() {
		return new GPanelD();
	}

}
