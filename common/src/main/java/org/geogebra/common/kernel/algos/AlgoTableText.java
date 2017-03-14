/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

 */

package org.geogebra.common.kernel.algos;

import org.geogebra.common.awt.GColor;
import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.StringTemplate;
import org.geogebra.common.kernel.commands.Commands;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.GeoNumberValue;
import org.geogebra.common.kernel.geos.GeoText;
import org.geogebra.common.kernel.geos.TextProperties;
import org.geogebra.common.util.StringUtil;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * Algo for TableText[matrix], TableText[matrix,args]
 *
 */
public class AlgoTableText extends AlgoElement implements TableAlgo {

	private GeoList geoList; // input
	private GeoText text; // output
	private GeoText args; // input

	private GeoList[] geoLists;

	private StringBuffer sb = new StringBuffer();

	private enum Alignment {
		VERTICAL, HORIZONTAL
	}

	// style variables
	private Alignment alignment;
	private boolean verticalLines, horizontalLines;
	private StringBuilder verticalLinesArray = null,
			horizontalLinesArray = null;
	private boolean verticalLinesJustEdges, horizontalLinesJustEdges;
	private String justification, openBracket, closeBracket, openString,
			closeString;
	private int columns;
	private int rows;

	// getters for style variables (used by EuclidianStyleBar)

	/**
	 * @return "v" or "h" for vertical / horizontal alignment
	 */
	public char getAlignment() {
		return alignment.equals(Alignment.VERTICAL) ? 'v' : 'h';
	}

	/**
	 * @return whether table has vertical lines
	 */
	public boolean isVerticalLines() {
		return verticalLines;
	}

	/**
	 * @return whether table has horizontal lines
	 */
	public boolean isHorizontalLines() {
		return horizontalLines;
	}

	/**
	 * @return justification l, r or c (just of first column if they have
	 *         different alignment)
	 */
	public String getJustification() {
		if (justification == null) {
			parseArgs();
		}
		return justification;
	}

	/**
	 * @return opening bracket for matrices
	 */
	public String getOpenSymbol() {
		return openString;
	}

	/**
	 * @return closing bracket for matrices
	 */
	public String getCloseSymbol() {
		return closeString;
	}

	/**
	 * @param cons
	 *            construction
	 * @param label
	 *            label for output
	 * @param geoList
	 *            input matrix
	 * @param args
	 *            table formating, see parseArgs()
	 */
	public AlgoTableText(Construction cons, String label, GeoList geoList,
			GeoText args) {
		this(cons, geoList, args);
		text.setLabel(label);
	}

	/**
	 * @param cons
	 *            construction
	 * @param geoList
	 *            input matrix
	 * @param args
	 *            table formating, see parseArgs()
	 */
	AlgoTableText(Construction cons, GeoList geoList, GeoText args) {
		super(cons);
		this.geoList = geoList;
		this.args = args;

		text = new GeoText(cons);
		text.setAbsoluteScreenLoc(0, 0);
		text.setAbsoluteScreenLocActive(true);

		text.setLaTeX(true, false);

		text.setIsTextCommand(true); // stop editing as text

		setInputOutput();
		compute();

		// set sans-serif LaTeX default
		text.setSerifFont(false);
	}

	@Override
	public Commands getClassName() {
		return Commands.TableText;
	}

	@Override
	protected void setInputOutput() {
		if (args == null) {
			input = new GeoElement[1];
			input[0] = geoList;
		} else {
			input = new GeoElement[2];
			input[0] = geoList;
			input[1] = args;
		}

		super.setOutputLength(1);
		super.setOutput(0, text);
		setDependencies(); // done by AlgoElement
	}

	/**
	 * @return resulting text
	 */
	public GeoText getResult() {
		return text;
	}

	// get the lrc.a%p from middle of ABCDlrc.a%pEFGH
	private final static RegExp matchLRC = RegExp
			.compile("([^.%lrcap]*)([.%lrcap]*)([^.%lrcap]*)");

	private void parseArgs() {

		int tableColumns = geoList.size();

		// set defaults
		alignment = Alignment.HORIZONTAL;
		verticalLines = false;
		horizontalLines = false;
		justification = "l";
		// need an open & close together, so can't use ""
		openBracket = "\\left.";
		closeBracket = "\\right.";

		if (args != null) {
			String optionsStr = args.getTextString();
			if (optionsStr.indexOf("v") > -1) {
				alignment = Alignment.VERTICAL; // vertical table
			}

			int pos;

			if ((pos = optionsStr.indexOf("|")) > -1
					&& optionsStr.indexOf("||") == -1) {
				verticalLines = true;

				verticalLinesArray = new StringBuilder();

				for (int i = pos + 1; i < optionsStr.length(); i++) {

					char ch = charAt(optionsStr, i);

					if (ch == '0' || ch == '1') {
						verticalLinesArray.append(ch);
					} else {
						break;
					}
				}

				// Log.debug("verticalLinesArray = "
				// + verticalLinesArray.toString());
			}

			if ((pos = optionsStr.indexOf("_")) > -1) {
				horizontalLines = true; // vertical table

				horizontalLinesArray = new StringBuilder();

				for (int i = pos + 1; i < optionsStr.length(); i++) {

					char ch = charAt(optionsStr, i);

					if (ch == '0' || ch == '1') {
						horizontalLinesArray.append(ch);
					} else {
						break;
					}
				}

				// Log.debug("horizontalLinesArray = "
				// + horizontalLinesArray.toString());
			}

			verticalLinesJustEdges = optionsStr.indexOf("/") > -1;
			horizontalLinesJustEdges = optionsStr.indexOf("-") > -1;

			MatchResult matcher = matchLRC.exec(optionsStr);
			justification = matcher.getGroup(2);
			if ("".equals(justification)) {
				justification = "l";
			}

			if (optionsStr.indexOf("||||") > -1) {
				openBracket = "\\left| \\left|";
				closeBracket = "\\right| \\right|";
				openString = "||";
				closeString = "||";
			} else if (optionsStr.indexOf("||") > -1) {
				openBracket = "\\left|";
				closeBracket = "\\right|";
				openString = "|";
				closeString = "|";
			} else if (optionsStr.indexOf('(') > -1) {
				openBracket = "\\left(";
				openString = "(";
			} else if (optionsStr.indexOf('[') > -1) {
				openBracket = "\\left[";
				openString = "[";

			} else if (optionsStr.indexOf('{') > -1) {
				openBracket = "\\left\\{";
				openString = "{";
			}

			if (optionsStr.indexOf(')') > -1) {
				closeBracket = "\\right)";
				closeString = ")";
			} else if (optionsStr.indexOf(']') > -1) {
				closeBracket = "\\right]";
				closeString = "]";
			} else if (optionsStr.indexOf('}') > -1) {
				closeBracket = "\\right\\}";
				closeString = "}";
			}

		} else if (geoList.get(tableColumns - 1).isGeoText()) {

			// support for older files before the fix
			GeoText options = (GeoText) geoList.get(tableColumns - 1);
			String optionsStr = options.getTextString();

			if (optionsStr.indexOf("h") > -1) {
				alignment = Alignment.HORIZONTAL; // horizontal table
			}

			MatchResult matcher = matchLRC.exec(optionsStr);
			justification = matcher.getGroup(2);
			if ("".equals(justification)) {
				justification = "l";
			}

		}

		if ("\\left.".equals(openBracket) && "\\right.".equals(closeBracket)) {
			openBracket = "";
			closeBracket = "";
		}

	}

	// default: return '1'
	private static char charAt(Object o, int i) {
		if (o == null) {
			return '1';
		}

		String str = o.toString();

		if (i < 0 || i >= str.length()) {
			return '1';
		}

		return str.charAt(i);
	}

	@Override
	public final void compute() {

		columns = geoList.size();

		if (!geoList.isDefined() || columns == 0) {
			text.setTextString("");
			return;
			// throw new MyError(app, app.getError("InvalidInput"));
		}

		parseArgs();

		// support for older files before the fix
		if (geoList.get(columns - 1).isGeoText()) {
			columns--;
		}

		if (columns == 0) {
			text.setTextString("");
			return;
			// throw new MyError(app, app.getError("InvalidInput"));
		}

		if (geoLists == null || geoLists.length < columns) {
			geoLists = new GeoList[columns];
		}

		rows = 0;

		for (int c = 0; c < columns; c++) {
			GeoElement geo = geoList.get(c);
			if (!geo.isGeoList()) {
				text.setTextString("");
				return;
				// throw new MyError(app,
				// loc.getPlain("SyntaxErrorAisNotAList",geo.toValueString()));
			}
			geoLists[c] = (GeoList) geoList.get(c);
			if (geoLists[c].size() > rows) {
				rows = geoLists[c].size();
			}
		}

		if (rows == 0) {
			text.setTextString("");
			return;
			// throw new MyError(app, app.getError("InvalidInput"));
		}

		sb.setLength(0);

		StringTemplate tpl = text.getStringTemplate();

		latex(tpl);

		// Application.debug(sb.toString());
		text.setTextString(sb.toString());
	}

	private void latex(StringTemplate tpl) {

		// surround in { } to make eg this work:
		// FormulaText["\bgcolor{ff0000}"+TableText[matrix1]]
		sb.append('{');

		sb.append(openBracket);
		sb.append("\\begin{array}{");

		if (alignment == Alignment.VERTICAL) {

			for (int c = 0; c < columns; c++) {
				if (verticalLines && (!verticalLinesJustEdges || c == 0)
						&& charAt(verticalLinesArray, c) == '1') {
					sb.append("|");
				}
				sb.append(getJustificationLaTeX(c)); // "l", "r" or "c"
			}
			if (verticalLines && charAt(verticalLinesArray, columns) == '1') {
				sb.append("|");
			}

			sb.append("}");

			if (horizontalLines && charAt(horizontalLinesArray, 0) == '1') {
				sb.append("\\hline ");
			}

			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < columns; c++) {
					boolean finalCell = (c == columns - 1);
					addCellLaTeX(c, r, finalCell, tpl, getJustification(c));
				}
				sb.append(" \\\\ "); // newline in LaTeX ie \\
				if (horizontalLines
						&& (!horizontalLinesJustEdges || r + 1 == rows)
						&& charAt(horizontalLinesArray, r + 1) == '1') {
					sb.append("\\hline ");
				}
			}

		} else { // alignment == HORIZONTAL

			for (int c = 0; c < rows; c++) {
				if (verticalLines && (!verticalLinesJustEdges || c == 0)
						&& charAt(verticalLinesArray, c) == '1') {
					sb.append("|");
				}

				sb.append(getJustificationLaTeX(c)); // "l", "r" or "c"
			}
			if (verticalLines && charAt(verticalLinesArray, rows) == '1') {
				sb.append("|");
			}

			sb.append("}");

			if (horizontalLines && charAt(horizontalLinesArray, 0) == '1') {
				sb.append("\\hline ");
			}

			// TableText[{11.1,322,3.11},{4,55,666,7777,88888},{6.11,7.99,8.01,9.81},{(1,2)},"c()"]

			for (int c = 0; c < columns; c++) {
				for (int r = 0; r < rows; r++) {
					boolean finalCell = (r == rows - 1);
					addCellLaTeX(c, r, finalCell, tpl, getJustification(c));
				}
				sb.append(" \\\\ "); // newline in LaTeX ie \\
				if (horizontalLines
						&& (!horizontalLinesJustEdges || c + 1 == columns)
						&& charAt(horizontalLinesArray, c + 1) == '1') {
					sb.append("\\hline ");
				}
			}
		}

		sb.append("\\end{array}");
		sb.append(closeBracket);

		// surround in { } to make eg this work:
		// FormulaText["\bgcolor{ff0000}"+TableText[matrix1]]
		sb.append('}');
	}

	/**
	 * 
	 * @param c
	 *            column/row
	 * @return 'l', 'r', 'c' for left/right/center
	 */
	private char getJustification(int c) {

		if (c < 0 || c >= justification.length()) {
			// default, if user passes "c" then use for all columns
			return justification.charAt(0);
		}

		return justification.charAt(c);
	}

	/**
	 * 
	 * @param c
	 *            column/row
	 * @return 'l', 'r', 'c' for left/right/center
	 */
	private char getJustificationLaTeX(int c) {

		char j = getJustification(c);

		switch (j) {
		case 'r':
		case 'c':
		case 'l':
			return j;

		// for 'a', '.', '%"
		default:
			return 'r';
		}

	}

	private void addCellLaTeX(int c, int r, boolean finalCell,
			StringTemplate tpl, char justification1) {

		if (geoLists[c].size() > r) { // check list has an element at this
										// position
			GeoElement geo1 = geoLists[c].get(r);

			GColor col = geo1.getObjectColor();
			GColor bgCol = geo1.getBackgroundColor();

			// check isLabelSet() so that eg TableText[{{1, 2, 3}}] isn't green
			if (GColor.BLACK.equals(col) || !geo1.isLabelSet()) {
				col = null;
			}

			if (bgCol != null
					&& (!geo1.isLabelSet() || bgCol.getAlpha() == 0)) {
				bgCol = null;
			}

			if (bgCol != null) {
				sb.append("\\cellcolor{#");
				sb.append(StringUtil.toHexString(bgCol));
				sb.append("}{");
			}

			if (col != null) {
				sb.append("\\textcolor{#");
				sb.append(StringUtil.toHexString(col));
				sb.append("}{");
			}

			// replace " " and "" with a hard space (allow blank columns/rows)
			String text1 = geo1.toLaTeXString(false, tpl);
			if (geo1.isGeoText() && !((GeoText) geo1).isLaTeX()) {
				text1 = text1.replace("$", "\\dollar");

			}
			switch (justification1) {
			default:
				// do nothing
				break;
			case '.':
			case 'a':
				if (geo1 instanceof GeoNumberValue) {

					double num = ((GeoNumberValue) geo1).getDouble();

					text1 = kernel.format(num, tpl);
				}
				text1 = tpl.padZerosAfterDecimalPoint(text1,
						justification1 == '.', kernel.getPrintDecimals(), "");
				break;
			case '%':
			case 'p':

				if (geo1 instanceof GeoNumberValue) {

					double num = ((GeoNumberValue) geo1).getDouble();

					String numStr = kernel.format(num * 100, tpl);

					text1 = tpl.padZerosAfterDecimalPoint(numStr,
							justification1 == '%', kernel.getPrintDecimals(),
							"%");
				}

			}

			if (" ".equals(text1) || "".equals(text1)) {
				text1 = "\\;"; // problem with JLaTeXMath, was "\u00a0";
			}

			// make sure latex isn't wrapped in \text{}
			if (((geo1 instanceof TextProperties
					&& !((TextProperties) geo1).isLaTeXTextCommand())
					&& (!(geo1 instanceof GeoText)
							|| !((GeoText) geo1).isLaTeX()))

					// check for "raw" LaTeX
					// eg TableText[{"\frac{2}{3}","2","3"},{"4","5","6"}]
					&& text1.indexOf(" ") > -1 && text1.indexOf("^") == -1
					&& text1.indexOf("{") == -1 && text1.indexOf("}") == -1
					&& text1.indexOf("+") == -1 && text1.indexOf("-") == -1
					&& text1.indexOf("=") == -1 && text1.indexOf("\\") == -1

			) {

				sb.append("\\text{"); // preserve spaces
				sb.append(text1);
				sb.append("}");

			} else {
				sb.append(text1);
			}

			if (col != null) {
				sb.append('}');
			}
			if (bgCol != null) {
				sb.append('}');
			}

		}
		if (!finalCell)
		 {
			sb.append("&"); // separate columns
		}
	}

	@Override
	public boolean isLaTeXTextCommand() {
		return true;
	}

}
