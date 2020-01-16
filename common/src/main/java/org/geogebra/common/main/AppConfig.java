package org.geogebra.common.main;

import java.util.Set;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.common.gui.toolcategorization.AppType;
import org.geogebra.common.io.layout.DockPanelData;
import org.geogebra.common.kernel.arithmetic.SymbolicMode;
import org.geogebra.common.kernel.arithmetic.filter.OperationArgumentFilter;
import org.geogebra.common.kernel.commands.selector.CommandFilter;
import org.geogebra.common.kernel.geos.properties.FillType;
import org.geogebra.common.kernel.parser.function.ParserFunctions;
import org.geogebra.common.main.settings.updater.SettingsUpdater;

public interface AppConfig {

    void adjust(DockPanelData dp);

    String getAVTitle();

    int getLineDisplayStyle();

    String getAppTitle();

    String getAppName();

    String getAppNameShort();

    String getTutorialKey();

    boolean showKeyboardHelpButton();

    boolean isSimpleMaterialPicker();

    boolean hasPreviewPoints();

    boolean allowsSuggestions();

    boolean shouldKeepRatioEuclidian();

    int getDefaultPrintDecimals();

    boolean hasSingleEuclidianViewWhichIs3D();

    /**
     * @return the decimal places that this app uses.
     */
    int[] getDecimalPlaces();

    /**
     * @return the significant places that this app uses.
     */
    int[] getSignificantFigures();

    /**
     * @return the array of characters that can be used for the angle labels.
     */
    boolean isGreekAngleLabels();

    /**
     * @return whether to allow CAS commands in AV
     */
    boolean isCASEnabled();

    /**
     * @return suffix for preferences (in web)
     */
    String getPreferencesKey();

    /**
     * @return preferred perspective ID or null if user setting should be used
     */
    String getForcedPerspective();

    /**
     * @return whether to use scientific keyboard layout
     */
    boolean hasScientificKeyboard();

    /**
     * @return whether match structures (functions, equations, vectors) are
     * enabled
     */
    boolean isEnableStructures();

    /**
     * @return the toolbar type of the current app.
     */
    AppType getToolbarType();

    /**
     * @return true if grid is shown at start on the active (main) euclidian view
     */
    boolean showGridOnFileNew();

    /**
     * @return true if axes are shown at start on the active (main) euclidian view
     */
    boolean showAxesOnFileNew();

    /**
     * @return whether table view is available
     */
    boolean hasTableView();

    /**
     * @return symbolic mode for algebra view
     */
    SymbolicMode getSymbolicMode();

    /**
     * @return whether sliders in AV are allowed
     */
    boolean hasSlidersInAV();

    /**
     * @return true if sliders are created automatically
     */
    boolean hasAutomaticSliders();

    /**
     * @return whether objects should be labeled a, b, ...
     */
    boolean hasAutomaticLabels();

    /**
     * @return algebra style
     */
    int getDefaultAlgebraStyle();

    /**
     * @return search tag for Open Material screen
     */
    String getDefaultSearchTag();

    /**
     * @return labeling style
     */
    int getDefaultLabelingStyle();

    /**
     * @return the Command filter for the app.
     */
    CommandFilter getCommandFilter();

    /**
     * @return whether the app should show the tools panel or not
     */
    boolean showToolsPanel();

    /**
     * @return with the app code which is also used in the url, like graphing,cas,
     * classic etc..
     */
    String getAppCode();

    /**
     * @return creates a settings updater
     */
    SettingsUpdater createSettingsUpdater();

    /**
     * Get the app version in enum.
     *
     * @return app version
     */
    GeoGebraConstants.Version getVersion();

    /**
     * @return weather has exam or not (currently only graphing and cas)
     */
    boolean hasExam();

    /**
     * @return the ggbtranskey for the exam starting menu item in the MainMenu
     */
    String getExamMenuItemText();

    /**
     * Whether it shows the error dialog for the input boxes.
     *
     * @return true iff it should show the error dialog
     */
    boolean isShowingErrorDialogForInputBox();

    /**
     * Create app specific operation argument filter.
     * <code>null</code> is allowed.
     *
     * @return operation argument filter
     */
    OperationArgumentFilter createOperationArgumentFilter();

    /**
     * @return creates app specific parser functions
     */
    ParserFunctions createParserFunctions();

    /**
     * Get the available fill types.
     *
     * @return fill types
     */
    Set<FillType> getAvailableFillTypes();

    /**
     * Returns an equation form constant declared in the GeoLine class,
     * or -1 if it's not set
     *
     * @return equation form or -1
     */
    int getEnforcedLineEquationForm();

    /**
     * Returns an equation form constant declared in the GeoConicND class,
     * or -1 if it's not set
     *
     * @return equation form or -1
     */
    int getEnforcedConicEquationForm();

    /**
     * Whether it shows the equation in AV.
     *
     * @return true if equation should be hidden in AV
     */
    boolean shouldHideEquations();

    /**
     * @return whether the apps uses restricted dragging for certain objects or not
     */
    boolean isObjectDraggingRestricted();
}