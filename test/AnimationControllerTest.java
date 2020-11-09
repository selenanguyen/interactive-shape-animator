import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.IController;
import cs3500.animator.model.Action;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.Color;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.InteractiveView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the animation controller.
 */
public class AnimationControllerTest {
  private AnimationOperations model;
  private IInteractiveView view;
  private int speed;

  private IController controller;

  @Before
  public void setup() {
    this.speed = 10;
    this.model = new AnimationModel();
    this.view = new InteractiveView(model,speed, false, false);

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
    this.model.addShape(oval);
    this.model.addShape(rect);
    this.model.addAction(oval.getId(), actionOval1);
    this.model.addAction(oval.getId(), actionOval2);
    this.model.addAction(rect.getId(), actionRect);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new AnimationController(null, view, speed);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new AnimationController(model, null, speed);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpeed() {
    new AnimationController(model, view, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpeed() {
    new AnimationController(model, view, 0);
  }

  @Test
  public void testSetSpeed() {
    IController controller = new AnimationController(model, view, speed, true);
    controller.setSpeed("10");
    assertEquals(10, controller.getSpeed());
  }

  /* These tests pass but you need to physically close the popup menu
  @Test
  public void testRemoveShpae() {
    IController testController = new AnimationController(model, view, 1, true);
    testController.removeShape("circle1");
    assertEquals(1, model.getShapes().size());
    assertEquals("rect1", model.getShapes().get(0).getId());
  }

  @Test
  public void testAddKeyframe() {
    IController testController = new AnimationController(model, view, 1, true);
    testController.addKeyframe("circle1", "(255,0,0)",
            "(200,200)", "10", "10", 20);
    assertEquals(model.getKeyframes("circle1")
            .get(model.getKeyframes("circle1").size() - 1).toString(),
            "Tick 20: (200,200), 10, 10, rgb(255,0,0)");
  }

  @Test
  public void testRemoveKeyframe() {
    IController testController = new AnimationController(model, view, 1, true);
    testController.removeKeyframe("circle1", 15);
    assertEquals(model.getKeyframes("circle1")
                    .get(model.getKeyframes("circle1").size() - 1).toString(),
            "Tick 10: (200,200), 20, 20, rgb(0,255,0)");
  }

  @Test
  public void testReplaceKeyframe() {
    IController testController = new AnimationController(model, view, 1, true);
    testController.replaceKeyframe("circle1", "(0,0,0)", "(200,200)",
            "20", "20", 15);
    assertEquals(model.getKeyframes("circle1")
                    .get(model.getKeyframes("circle1").size() - 1).toString(),
            "Tick 15: (200,200), 20, 20, rgb(0,0,0)");
  }
  */
}
