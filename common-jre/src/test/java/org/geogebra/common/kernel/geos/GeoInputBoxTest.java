package org.geogebra.common.kernel.geos;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.kernel.geos.properties.TextAlignment;
import org.geogebra.common.main.App;
import org.geogebra.common.util.TextObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GeoInputBoxTest extends BaseUnitTest {

	private TextObject textObject;

	@Before
	public void setUp() {
		textObject = Mockito.mock(TextObject.class);
	}

	@Test
	public void symbolicInputBoxUseDefinitionForFunctions() {
		add("f = x+1");
		add("g = 2f(x+2)+1");
		GeoInputBox inputBox1 = (GeoInputBox) add("InputBox(f)");
		GeoInputBox inputBox2 = (GeoInputBox) add("InputBox(g)");
		inputBox2.setSymbolicMode(true, false);
		Assert.assertEquals("x + 1", inputBox1.getText());
		Assert.assertEquals("2f(x + 2) + 1", inputBox2.getTextForEditor());
	}

	@Test
	public void symbolicInputBoxUseDefinitionForFunctionsNVar() {
		add("f = x*y+1");
		add("g = 2f(x+2, y)+1");
		GeoInputBox inputBox1 = (GeoInputBox) add("InputBox(f)");
		GeoInputBox inputBox2 = (GeoInputBox) add("InputBox(g)");
		inputBox2.setSymbolicMode(true, false);
		Assert.assertEquals("x y + 1", inputBox1.getText());
		Assert.assertEquals("2f(x + 2, y) + 1", inputBox2.getTextForEditor());
		Assert.assertEquals("2 \\; f\\left(x + 2, y \\right) + 1",
				inputBox2.getText());
	}

	@Test
	public void symbolicInputBoxTextShouldBeInLaTeX() {
		add("f = x + 12");
		add("g = 2f(x + 1) + 2");
		GeoInputBox inputBox2 = (GeoInputBox) add("InputBox(g)");
		inputBox2.setSymbolicMode(true, false);
		Assert.assertEquals("2 \\; f\\left(x + 1 \\right) + 2", inputBox2.getText());
	}

	@Test
	public void testMatrixShouldBeInLaTeX() {
		add("m1 = {{1, 2, 3}, {4, 5, 6}}");
		GeoInputBox inputBox = (GeoInputBox) add("InputBox(m1)");
		inputBox.setSymbolicMode(true, false);
		Assert.assertEquals("\\left(\\begin{array}{rrr}1&2&3\\\\4&5&6\\\\ \\end{array}\\right)",
				inputBox.getText());
	}

	@Test
	public void inputBoxTextAlignmentIsInXMLTest() {
		App app = getApp();
		add("A = (1,1)");
		GeoInputBox inputBox = (GeoInputBox) add("B = Inputbox(A)");
		Assert.assertEquals(TextAlignment.LEFT, inputBox.getAlignment());
		inputBox.setAlignment(TextAlignment.CENTER);
		Assert.assertEquals(TextAlignment.CENTER, inputBox.getAlignment());
		String appXML = app.getXML();
		app.setXML(appXML, true);
		inputBox = (GeoInputBox) lookup("B");
		Assert.assertEquals(TextAlignment.CENTER, inputBox.getAlignment());
	}

	@Test
	public void testTempUserInputNotInXml() {
		add("A = (1,1)");
		GeoInputBox inputBox = (GeoInputBox) add("B = Inputbox(A)");
		inputBox.updateLinkedGeo("(1,2)");

		App app = getApp();
		String appXML = app.getXML();
		app.setXML(appXML, true);
		inputBox = (GeoInputBox) lookup("B");
		Assert.assertNull(inputBox.getTempUserDisplayInput());
		Assert.assertNull(inputBox.getTempUserEvalInput());
	}

	@Test
	public void testTempUserInputInXml() {
		add("A = (1,1)");
		GeoInputBox inputBox = (GeoInputBox) add("B = Inputbox(A)");

		String wrongSyntax = "(1,1)+";
		inputBox.updateLinkedGeo(wrongSyntax);

		App app = getApp();
		String appXML = app.getXML();
		app.setXML(appXML, true);
		inputBox = (GeoInputBox) lookup("B");
		Assert.assertEquals(wrongSyntax, inputBox.getTempUserEvalInput());
		Assert.assertEquals(wrongSyntax, inputBox.getTempUserDisplayInput());
	}

	@Test
	public void testSymbolicUserInput() {
		add("a = 5");
		GeoInputBox inputBox = (GeoInputBox) add("Inputbox(a)");
		String tempDisplayInput = "\\frac{5}{\\nbsp}";
		inputBox.setTempUserDisplayInput(tempDisplayInput);
		inputBox.updateLinkedGeo("5/");
		inputBox.setSymbolicMode(true);
		Assert.assertEquals(tempDisplayInput, inputBox.getDisplayText());
		Assert.assertEquals("5/", inputBox.getText());
	}

	@Test
	public void testInputBoxGetTextWithError() {
		add("A = Point({1, 2})");
		GeoInputBox box = (GeoInputBox) add("InputBox(A)");
		Assert.assertEquals(box.getText(), "Point({1, 2})");
		box.updateLinkedGeo("Point({1, 2})+");
		Assert.assertEquals(box.getText(), "Point({1, 2})+");
		box.updateLinkedGeo("Point(1)");
		Assert.assertEquals(box.getText(), "Point(1)");
	}

	@Test
	public void testSymbolicInputBoxGetTextWithError() {
		add("a = 1");
		GeoInputBox box = (GeoInputBox) add("InputBox(a)");
		box.setSymbolicMode(true, true);
		Assert.assertEquals("1", box.getTextForEditor());
		box.updateLinkedGeo("1+/");
		Assert.assertEquals("1+/", box.getTextForEditor());
	}

	@Test
	public void testForSimpleUndefinedGeo() {
		add("a=?");
		GeoInputBox inputBox = (GeoInputBox) add("InputBox(a)");
		inputBox.setSymbolicMode(true, false);
		Assert.assertEquals("", inputBox.getText());
		Assert.assertEquals("", inputBox.getTextForEditor());

		inputBox.setSymbolicMode(false, false);
		Assert.assertEquals("", inputBox.getText());

	}

	@Test
	public void testForDependentUndefinedGeo() {
		add("a=1");
		add("b=?a");

		GeoInputBox inputBox = (GeoInputBox) add("InputBox(b)");
		inputBox.setSymbolicMode(true, false);
		Assert.assertEquals("? \\; a", inputBox.getText());
		Assert.assertEquals("?a", inputBox.getTextForEditor());

		inputBox.setSymbolicMode(false, false);
		Assert.assertEquals("?a", inputBox.getText());
	}

	@Test
	public void testForEmptyInput() {
		add("a=1");

		GeoInputBox inputBox = (GeoInputBox) add("InputBox(a)");
		inputBox.setSymbolicMode(true, false);

		Mockito.when(textObject.getText()).thenReturn("");
		inputBox.textObjectUpdated(textObject);

		Assert.assertEquals("", inputBox.getText());
		Assert.assertEquals("", inputBox.getTextForEditor());

		inputBox.setSymbolicMode(false, false);

		Assert.assertEquals("", inputBox.getText());
	}

	@Test
	public void testForUndefinedInputInput() {
		add("a=1");

		GeoInputBox inputBox = (GeoInputBox) add("InputBox(a)");
		inputBox.setSymbolicMode(true, false);

		Mockito.when(textObject.getText()).thenReturn("?");
		inputBox.textObjectUpdated(textObject);

		Assert.assertEquals("", inputBox.getText());
		Assert.assertEquals("", inputBox.getTextForEditor());

		inputBox.setSymbolicMode(false, false);

		Assert.assertEquals("", inputBox.getText());
	}

	@Test
	public void testInputForGeoText() {
		add("text = \"?\" ");
		GeoInputBox inputBox = (GeoInputBox) add("InputBox(text)");

		inputBox.setSymbolicMode(true, false);
		Assert.assertEquals("?", inputBox.getText());
		Assert.assertEquals("?", inputBox.getTextForEditor());

		inputBox.setSymbolicMode(false, false);
		Assert.assertEquals("?", inputBox.getText());
	}

	@Test
	public void testCanBeSymbolicForNVarFunction() {
		add("f(x, y) = x + y");
		GeoInputBox inputBox = (GeoInputBox) add("InputBox(f)");
		Assert.assertTrue(inputBox.canBeSymbolic());
	}

	@Test
	public void testCanBeSymbolicForBooleanFunction() {
		add("f(x, y) = x == y");
		GeoInputBox inputBox = (GeoInputBox) add("InputBox(f)");
		Assert.assertTrue(inputBox.canBeSymbolic());
	}

	@Test
	public void testCanBeSymbolicForLine() {
		add("A = (0,0)");
		add("B = (2,2)");
		add("f:Line(A,B)");
		GeoInputBox inputBox = (GeoInputBox) add("InputBox(f)");
		Assert.assertTrue(inputBox.canBeSymbolic());
	}

	@Test
	public void testErrorWorksWithString() {
		add("a = 5");
		GeoInputBox inputBox = (GeoInputBox) add("ib = InputBox(a)");
		GeoText text = (GeoText) add("ib + \"\"");

		inputBox.updateLinkedGeo("1+");
		Assert.assertEquals("1+", text.getTextString());
	}
}
