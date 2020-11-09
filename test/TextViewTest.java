import cs3500.animator.model.Action;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.Color;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import cs3500.animator.view.TextView;
import cs3500.animator.view.TextualView;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Test for the text view.
 */
public class TextViewTest {
  String outputFileName = "resources/test.txt";
  AnimationOperations testModel = new AnimationModel();

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    TextualView testView = new TextView(null, outputFileName);
  }

  @Test
  public void testTextNoShapeOrAction() {
    AnimationOperations model = new AnimationModel();
    TextualView testView = new TextView(model, outputFileName);
    assertEquals("canvas " + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
                    + " " + model.getCanvasWidth() + " " + model.getCanvasHeight(),
            testView.getText());
  }

  @Test
  public void testTextOneOvalShape() {
    Shape oval = new Oval("circle1", 10, 10,
            new Color(255, 0,0), new Position(100, 100));
    AnimationOperations model = new AnimationModel();
    model.addShape(oval);
    TextualView testView = new TextView(model, outputFileName);
    assertEquals("canvas " + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
                    + " " + model.getCanvasWidth() + " " + model.getCanvasHeight() + "\n"
                    + "shape circle1 oval",
            testView.getText());
  }

  @Test
  public void testTextOneRectShape() {
    Shape oval = new Rectangle("rect1", 10, 10,
            new Color(255, 0,0), new Position(100, 100));
    AnimationOperations model = new AnimationModel();
    model.addShape(oval);
    TextualView testView = new TextView(model, outputFileName);
    assertEquals("canvas " + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
                    + " " + model.getCanvasWidth() + " " + model.getCanvasHeight() + "\n"
                    + "shape rect1 rectangle",
                testView.getText());
  }

  @Test
  public void testTextRectAllActions() {
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(100, 100);
    Position p2 = new Position(110, 100);
    Position p3 = new Position(110, 110);
    Shape rect = new Rectangle("rect1", 10, 10,
                red, p1);
    Action changeWidth = new Action(p1, p1,
            10, 11, 10, 10,
            red, red, 1, 10);
    Action changeHeight = new Action(p1, p1,
            11, 11, 10, 11,
            red, red, 10, 20);
    Action changeColor = new Action(p1, p1,
            11, 11, 11, 11,
            red, green, 20, 30);
    Action changeCenterX = new Action(p1, p2,
            11, 11, 11, 11,
            green, green, 30, 40);
    Action changeCenterY = new Action(p2, p3,
            11, 11, 11, 11,
            green, green, 40, 50);

    AnimationOperations model = new AnimationModel();
    model.addShape(rect);

    model.addAction(rect.getId(), changeWidth);
    model.addAction(rect.getId(), changeHeight);
    model.addAction(rect.getId(), changeColor);
    model.addAction(rect.getId(), changeCenterX);
    model.addAction(rect.getId(), changeCenterY);

    TextualView testView = new TextView(model, outputFileName);
    assertEquals("canvas " + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
                    + " " + model.getCanvasWidth() + " " + model.getCanvasHeight() + "\n"
                    + "shape rect1 rectangle\n"
                    + "motion rect1 1 100 100 10 10 255 0 0 10 100 100 11 10 255 0 0\n"
                    + "motion rect1 10 100 100 11 10 255 0 0 20 100 100 11 11 255 0 0\n"
                    + "motion rect1 20 100 100 11 11 255 0 0 30 100 100 11 11 0 255 0\n"
                    + "motion rect1 30 100 100 11 11 0 255 0 40 110 100 11 11 0 255 0\n"
                    + "motion rect1 40 110 100 11 11 0 255 0 50 110 110 11 11 0 255 0",
            testView.getText());
  }

  @Test
  public void testTextOvalAllActions() {
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(100, 100);
    Position p2 = new Position(110, 100);
    Position p3 = new Position(110, 110);
    Shape oval = new Oval("circle1", 10, 10,
            red, p1);
    Action changeWidth = new Action(p1, p1,
            10, 11, 10, 10,
            red, red, 1, 10);
    Action changeHeight = new Action(p1, p1,
            11, 11, 10, 11,
            red, red, 10, 20);
    Action changeColor = new Action(p1, p1,
            11, 11, 11, 11,
            red, green, 20, 30);
    Action changeCenterX = new Action(p1, p2,
            11, 11, 11, 11,
            green, green, 30, 40);
    Action changeCenterY = new Action(p2, p3,
            11, 11, 11, 11,
            green, green, 40, 50);

    AnimationOperations model = new AnimationModel();
    model.addShape(oval);

    model.addAction(oval.getId(), changeWidth);
    model.addAction(oval.getId(), changeHeight);
    model.addAction(oval.getId(), changeColor);
    model.addAction(oval.getId(), changeCenterX);
    model.addAction(oval.getId(), changeCenterY);

    TextualView testView = new TextView(model, outputFileName);
    assertEquals("canvas " + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
                    + " " + model.getCanvasWidth() + " " + model.getCanvasHeight() + "\n"
                    + "shape circle1 oval\n"
                    + "motion circle1 1 100 100 10 10 255 0 0 10 100 100 11 10 255 0 0\n"
                    + "motion circle1 10 100 100 11 10 255 0 0 20 100 100 11 11 255 0 0\n"
                    + "motion circle1 20 100 100 11 11 255 0 0 30 100 100 11 11 0 255 0\n"
                    + "motion circle1 30 100 100 11 11 0 255 0 40 110 100 11 11 0 255 0\n"
                    + "motion circle1 40 110 100 11 11 0 255 0 50 110 110 11 11 0 255 0",
            testView.getText());
  }

  @Test
  public void testBothShapes() {
    Color red = new Color(255, 0, 0);
    Color green = new Color(0, 255, 0);
    Position p1 = new Position(100, 100);
    Position p2 = new Position(110, 100);
    Position p3 = new Position(110, 110);
    Shape rect = new Rectangle("rect1", 10, 10,
            red, p1);
    Action changeWidth = new Action(p1, p1,
            10, 11, 10, 10,
            red, red, 1, 10);
    Action changeHeight = new Action(p1, p1,
            11, 11, 10, 11,
            red, red, 10, 20);
    Action changeColor = new Action(p1, p1,
            11, 11, 11, 11,
            red, green, 20, 30);
    Action changeCenterX = new Action(p1, p2,
            11, 11, 11, 11,
            green, green, 30, 40);
    Action changeCenterY = new Action(p2, p3,
            11, 11, 11, 11,
            green, green, 40, 45);

    AnimationOperations model = new AnimationModel();
    model.addShape(rect);

    model.addAction(rect.getId(), changeWidth);
    model.addAction(rect.getId(), changeHeight);
    model.addAction(rect.getId(), changeColor);
    model.addAction(rect.getId(), changeCenterX);
    model.addAction(rect.getId(), changeCenterY);

    Shape oval = new Oval("circle1", 10, 10,
            red, p1);
    Action changeWidthO = new Action(p1, p1,
            10, 11, 10, 10,
            red, red, 1, 10);
    Action changeHeightO = new Action(p1, p1,
            11, 11, 10, 11,
            red, red, 10, 20);
    Action changeColorO = new Action(p1, p1,
            11, 11, 11, 11,
            red, green, 20, 30);
    Action changeCenterXO = new Action(p1, p2,
            11, 11, 11, 11,
            green, green, 30, 40);
    Action changeCenterYO = new Action(p2, p3,
            11, 11, 11, 11,
            green, green, 40, 50);

    model.addShape(oval);

    model.addAction(oval.getId(), changeWidthO);
    model.addAction(oval.getId(), changeHeightO);
    model.addAction(oval.getId(), changeColorO);
    model.addAction(oval.getId(), changeCenterXO);
    model.addAction(oval.getId(), changeCenterYO);

    TextualView testView = new TextView(model, outputFileName);
    assertEquals("canvas " + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
                    + " " + model.getCanvasWidth() + " " + model.getCanvasHeight() + "\n"
                    + "shape rect1 rectangle\n"
                    + "motion rect1 1 100 100 10 10 255 0 0 10 100 100 11 10 255 0 0\n"
                    + "motion rect1 10 100 100 11 10 255 0 0 20 100 100 11 11 255 0 0\n"
                    + "motion rect1 20 100 100 11 11 255 0 0 30 100 100 11 11 0 255 0\n"
                    + "motion rect1 30 100 100 11 11 0 255 0 40 110 100 11 11 0 255 0\n"
                    + "motion rect1 40 110 100 11 11 0 255 0 45 110 110 11 11 0 255 0\n"
                    + "shape circle1 oval\n"
                    + "motion circle1 1 100 100 10 10 255 0 0 10 100 100 11 10 255 0 0\n"
                    + "motion circle1 10 100 100 11 10 255 0 0 20 100 100 11 11 255 0 0\n"
                    + "motion circle1 20 100 100 11 11 255 0 0 30 100 100 11 11 0 255 0\n"
                    + "motion circle1 30 100 100 11 11 0 255 0 40 110 100 11 11 0 255 0\n"
                    + "motion circle1 40 110 100 11 11 0 255 0 50 110 110 11 11 0 255 0",
            testView.getText());
  }
}
