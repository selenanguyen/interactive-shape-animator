import cs3500.animator.model.Action;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.Color;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the SVG View.
 */
public class SVGViewTest {
  String outputFileName = "test.svg";
  Integer ticksPerSecond = 10;
  AnimationOperations testModel = new AnimationModel();

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    TextualView testView = new SVGView(null, outputFileName, ticksPerSecond);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroTicksPerSecond() {
    TextualView testView = new SVGView(testModel, outputFileName, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTicksPerSecond() {
    TextualView testView = new SVGView(testModel, outputFileName, -10);
  }

  @Test
  public void testSVGNoShapeOrAction() {
    AnimationOperations model = new AnimationModel();
    TextualView testView = new SVGView(model, outputFileName, ticksPerSecond);
    assertEquals("<svg viewbox=\"200 200 500 500\" version=\"1.1\"\n"
                    + "     xmlns=\"http://www.w3.org/2000/svg\">\n</svg>",
            testView.getText());
  }

  @Test
  public void testSVGOneRectOneAction() {
    Color red = new Color(255, 0, 0);
    Position p1 = new Position(100, 100);
    Shape rect = new Rectangle("rect1", 10, 10,
            red, p1, 0, 0);
    Action action = new Action(p1, p1,
            10, 11, 10, 10,
            red, red, 1, 10);
    AnimationOperations model = new AnimationModel();
    model.addShape(rect);
    model.addAction(rect.getId(), action);
    TextualView testView = new SVGView(model, outputFileName, ticksPerSecond);
    assertEquals("<svg viewbox=\"200 200 500 500\" version=\"1.1\"\n"
                    + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "<rect id=\"rect1\" x=\"100\" y=\"100\" width=\"10\" height=\"10\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"width\" from=\"10\" to=\"11\" fill=\"remove\"/>\n"
                    + "</rect>\n"
                    + "</svg>",
            testView.getText());
  }

  @Test
  public void testSVGAllActions() {
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(250, 250);
    Position p2 = new Position(200, 200);
    Shape oval = new Oval("circle1", 0);
    Shape rect = new Rectangle("rect1", 0);
    Action actionRect = new Action(p2, p1, 20, 10, 20, 10,
            green, red, 5, 30);
    Action actionOval1 = new Action(p1, p2,
            10, 20, 10, 20,
            red, green, 1, 10);
    Action actionOval2 = new Action(p2, p2, 20, 10, 20, 10,
            green, green, 10, 15);
    AnimationOperations model = new AnimationModel();
    model.addShape(oval);
    model.addShape(rect);
    model.addAction(oval.getId(), actionOval1);
    model.addAction(oval.getId(), actionOval2);
    model.addAction(rect.getId(), actionRect);
    TextualView testView = new SVGView(model, outputFileName, ticksPerSecond);
    assertEquals("<svg viewbox=\"200 200 500 500\" version=\"1.1\"\n"
                    + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "<ellipse id=\"circle1\" cx=\"255.0\" cy=\"255.0\" rx=\"5.0\" ry=\"5.0\""
                    + " fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(0,255,0)\" "
                    + "fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"ry\" from=\"5.0\" to=\"10.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"rx\" from=\"5.0\" to=\"10.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"cx\" from=\"255.0\" to=\"210.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"cy\" from=\"255.0\" to=\"210.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"500.0ms\" "
                    + "attributeName=\"ry\" from=\"10.0\" to=\"5.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"500.0ms\" "
                    + "attributeName=\"rx\" from=\"10.0\" to=\"5.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"0.1ms\" "
                    + "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" "
                    + "fill=\"freeze\"/>\n"
                    + "</ellipse>\n"
                    + "<rect id=\"rect1\" x=\"200\" y=\"200\" width=\"20\" height=\"20\" "
                    + "fill=\"rgb(0,255,0)\" visibility=\"hidden\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"400.0ms\" "
                    + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" "
                    + "fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(255,0,0)\" "
                    + "fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"height\" from=\"20\" to=\"10\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"width\" from=\"20\" to=\"10\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"x\" from=\"200\" to=\"250\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"y\" from=\"200\" to=\"250\" fill=\"remove\"/>\n"
                    + "</rect>\n</svg>",
            testView.getText());
  }

  @Test
  public void testSVGRotate() {
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(250, 250);
    Position p2 = new Position(200, 200);
    Shape oval = new Oval("circle1", 1);
    Shape rect = new Rectangle("rect1",1);
    Action actionRect = new Action(p2, p1, 20, 10, 20, 10,
            green, red, 0, 30, 5, 30);
    Action actionOval1 = new Action(p1, p2,
            20, 40, 20, 40,
            red, green, 0, 360, 1, 10);
    Action actionOval2 = new Action(p2, p2, 40, 20, 40, 20,
            green, green, 360, 360, 10, 15);
    AnimationOperations model = new AnimationModel();
    model.addShape(oval);
    model.addShape(rect);
    model.addAction(oval.getId(), actionOval1);
    model.addAction(oval.getId(), actionOval2);
    model.addAction(rect.getId(), actionRect);
    TextualView testView = new SVGView(model, outputFileName, ticksPerSecond);
    assertEquals("<svg viewbox=\"200 200 500 500\" version=\"1.1\"\n"
                    + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "<ellipse id=\"circle1\" cx=\"260.0\" cy=\"260.0\" rx=\"10.0\" ry=\"10.0\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(0,255,0)\" "
                    + "fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"ry\" from=\"10.0\" to=\"20.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"rx\" from=\"10.0\" to=\"20.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"cx\" from=\"260.0\" to=\"220.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"cy\" from=\"260.0\" to=\"220.0\" fill=\"freeze\"/>\n"
                    + "\t<animateTransform attributeType=\"xml\" begin=\"100.0ms\" "
                    + "end=\"1000.0ms\" dur=\"900.0ms\" attributeName=\"transform\" "
                    + "type=\"rotate\" from=\"0 260 260\" to=\"360 220.0 220.0\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"500.0ms\" "
                    + "attributeName=\"ry\" from=\"20.0\" to=\"10.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"500.0ms\" "
                    + "attributeName=\"rx\" from=\"20.0\" to=\"10.0\" fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"1500.0ms\" dur=\"0.1ms\" "
                    + "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" "
                    + "fill=\"freeze\"/>\n"
                    + "</ellipse>\n"
                    + "<rect id=\"rect1\" x=\"200\" y=\"200\" width=\"20\" height=\"20\" "
                    + "fill=\"rgb(0,255,0)\" visibility=\"hidden\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"400.0ms\" "
                    + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" "
                    + "fill=\"freeze\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(255,0,0)\" "
                    + "fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"height\" from=\"20\" to=\"10\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"width\" from=\"20\" to=\"10\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"x\" from=\"200\" to=\"250\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"2500.0ms\" "
                    + "attributeName=\"y\" from=\"200\" to=\"250\" fill=\"remove\"/>\n"
                    + "\t<animateTransform attributeType=\"xml\" begin=\"500.0ms\" "
                    + "end=\"3000.0ms\" dur=\"2500.0ms\" attributeName=\"transform\" "
                    + "type=\"rotate\" from=\"0 210.0 210.0\" to=\"30 255.0 255.0\"/>\n"
                    + "</rect>\n</svg>",
            testView.getText());
  }

  @Test
  public void resizeAndRotateInPlaceTest() {
    Shape oval = new Oval("circle1", 1);
    Shape rect = new Rectangle("rect1", 1);
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(250, 250);
    Position p2 = new Position(200, 200);
    Action actionRect = new Action(p1, p1, 20, 10, 20, 10,
            green, red, 0, 360, 1, 10);
    Action actionOval1 = new Action(p2, p2,
            10, 20, 10, 20,
            red, green, 0, 360, 1, 10);

    AnimationOperations model = new AnimationModel();
    model.addShape(oval);
    model.addShape(rect);
    model.addAction(oval.getId(), actionOval1);
    model.addAction(rect.getId(), actionRect);
    TextualView testView = new SVGView(model, outputFileName, ticksPerSecond);
    assertEquals("<svg viewbox=\"200 200 500 500\" version=\"1.1\"\n"
                    + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
                    + "<ellipse id=\"circle1\" cx=\"205.0\" cy=\"205.0\" rx=\"5.0\" ry=\"5.0\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(0,255,0)\" "
                    + "fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"ry\" from=\"5.0\" to=\"10.0\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"rx\" from=\"5.0\" to=\"10.0\" fill=\"remove\"/>\n"
                    + "\t<animateTransform attributeType=\"xml\" begin=\"100.0ms\" "
                    + "end=\"1000.0ms\" dur=\"900.0ms\" attributeName=\"transform\" "
                    + "type=\"rotate\" from=\"0 205 205\" to=\"360 205.0 205.0\"/>\n"
                    + "</ellipse>\n"
                    + "<rect id=\"rect1\" x=\"250\" y=\"250\" width=\"20\" height=\"20\" "
                    + "fill=\"rgb(0,255,0)\" visibility=\"visible\" >\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(255,0,0)\" "
                    + "fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"height\" from=\"20\" to=\"10\" fill=\"remove\"/>\n"
                    + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
                    + "attributeName=\"width\" from=\"20\" to=\"10\" fill=\"remove\"/>\n"
                    + "\t<animateTransform attributeType=\"xml\" begin=\"100.0ms\" "
                    + "end=\"1000.0ms\" dur=\"900.0ms\" attributeName=\"transform\" "
                    + "type=\"rotate\" from=\"0 260.0 260.0\" to=\"360 255.0 255.0\"/>\n"
                    + "</rect>\n</svg>",
            testView.getText());
  }

  @Test
  public void rotateInPlaceTes() {
    Shape oval = new Oval("circle1", 1);
    Shape rect = new Rectangle("rect1", 1);
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(250, 250);
    Position p2 = new Position(200, 200);
    Action actionRect = new Action(p1, p1, 20, 20, 20, 20,
            green, red, 0, 360, 1, 10);
    Action actionOval1 = new Action(p2, p2,
            10, 10, 10, 10,
            red, green, 0, 360, 1, 10);

    AnimationOperations model = new AnimationModel();
    model.addShape(oval);
    model.addShape(rect);
    model.addAction(oval.getId(), actionOval1);
    model.addAction(rect.getId(), actionRect);
    TextualView testView = new SVGView(model, outputFileName, ticksPerSecond);
    assertEquals("<svg viewbox=\"200 200 500 500\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<ellipse id=\"circle1\" cx=\"205.0\" cy=\"205.0\" rx=\"5.0\" ry=\"5.0\" "
            + "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
            + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
            + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(0,255,0)\" "
            + "fill=\"remove\"/>\n"
            + "\t<animateTransform attributeType=\"xml\" begin=\"100.0ms\" end=\"1000.0ms\" "
            + "dur=\"900.0ms\" attributeName=\"transform\" type=\"rotate\" "
            + "from=\"0 205 205\" to=\"360 205.0 205.0\"/>\n"
            + "</ellipse>\n"
            + "<rect id=\"rect1\" x=\"250\" y=\"250\" width=\"20\" height=\"20\" "
            + "fill=\"rgb(0,255,0)\" visibility=\"visible\" >\n"
            + "\t<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"900.0ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(255,0,0)\" "
            + "fill=\"remove\"/>\n"
            + "\t<animateTransform attributeType=\"xml\" begin=\"100.0ms\" end=\"1000.0ms\" "
            + "dur=\"900.0ms\" attributeName=\"transform\" type=\"rotate\" from=\"0 260.0 260.0\" "
            + "to=\"360 260.0 260.0\"/>\n"
            + "</rect>\n"
            + "</svg>", testView.getText());
  }
}
