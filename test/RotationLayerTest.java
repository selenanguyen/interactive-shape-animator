import org.junit.Before;
import org.junit.Test;

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

import static org.junit.Assert.assertEquals;

/**
 * Tests for the animation controller.
 */
public class RotationLayerTest {
  AnimationOperations model;
  IInteractiveView view;
  int speed;
  IController controller;

  @Before
  public void setup() {
    this.speed = 10;
    this.model = new AnimationModel();
    this.view = new InteractiveView(model,speed, true, true);

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

  // These tests test action listeners.
  // They pass but are commented out because they open up a new window when they are run.
  //
  //  @Test
  //  public void testAddShape() {
  //    this.controller = new AnimationController(model, view, speed, true, true, true);
  //    assertEquals(this.model.getShapes().size(), 2);
  //    controller.addShape("oval", "layer 5 oval", "5");
  //    assertEquals(this.model.getShapes().size(), 3);
  //    assertEquals(5, this.model.getAnimatedShapes().get(0).getLayer());
  //    assertEquals(0, this.model.getAnimatedShapes().get(1).getLayer());
  //    assertEquals(0, this.model.getAnimatedShapes().get(2).getLayer());
  //    controller.addShape("rectangle", "layer 0 rect", "0");
  //    assertEquals(this.model.getShapes().size(), 4);
  //    assertEquals(0, this.model.getAnimatedShapes().get(3).getLayer());
  //    assertEquals("layer 0 rect", this.model.getAnimatedShapes().get(3).getId());
  //    assertEquals(5, this.model.getAnimatedShapes().get(0).getLayer());
  //    controller.addShape("oval", "layer 1 oval", "1");
  //    assertEquals(this.model.getShapes().size(), 5);
  //    assertEquals(1, this.model.getAnimatedShapes().get(1).getLayer());
  //  }

  //  @Test
  //  public void testSwitchLayers() {
  //    this.controller = new AnimationController(model, view, speed, true, true, true);
  //    controller.addShape("oval", "layer 5 oval", "5");
  //    controller.switchLayers("0", "5");
  //    assertEquals(5, this.model.getAnimatedShapes().get(0).getLayer());
  //    assertEquals(5, this.model.getAnimatedShapes().get(1).getLayer());
  //    assertEquals(0, this.model.getAnimatedShapes().get(2).getLayer());
  //  }

  //  @Test
  //  public void testRemoveLayer() {
  //    this.controller = new AnimationController(model, view, speed, true, true, true);
  //    controller.addShape("oval", "layer 5 oval", "5");
  //    controller.removeLayer("0");
  //    assertEquals(1, this.model.getAnimatedShapes().size());
  //  }

  //  @Test
  //  public void testAddKeyframeWithRotation() {
  //    this.controller = new AnimationController(model, view, speed, true, true, true);
  //    this.controller.addKeyframe("circle1", "(255,255,255)",
  //            "(220,220)", "45","10", "10", 20);
  //    this.controller.addKeyframe("circle1", "(255,255,255)",
  //            "(220,220)", "47","10", "10", 22);
  //    this.model.applyTick(20);
  //    assertEquals(45, this.model.getShape("circle1").getRotation());
  //    this.model.applyTick(21);
  //    assertEquals(46, this.model.getShapeAt("circle1", 21).getRotation());
  //    this.model.applyTick(22);
  //    assertEquals(47, this.model.getShapeAt("circle1", 22).getRotation());
  //  }

  //  @Test
  //  public void testEditKeyframeWithRotation() {
  //    this.controller = new AnimationController(model, view, speed, true, true, true);
  //    this.controller.addKeyframe("circle1", "(255,255,255)",
  //            "(220,220)", "45","10", "10", 20);
  //    this.controller.addKeyframe("circle1", "(255,255,255)",
  //            "(220,220)", "47","10", "10", 22);
  //    this.controller.replaceKeyframe("circle1", "(255,255,255)",
  //            "(220,220)", "49","10", "10", 20);
  //    this.model.applyTick(20);
  //    assertEquals(49, this.model.getShape("circle1").getRotation());
  //    this.model.applyTick(21);
  //    assertEquals(48, this.model.getShapeAt("circle1", 21).getRotation());
  //    this.model.applyTick(22);
  //    assertEquals(47, this.model.getShapeAt("circle1", 22).getRotation());
  //  }


  // this test was created because the java styler noticed that the 4 fields we declared above
  // were only ued in one method. But we had to actually comment our tests out above.
  @Test
  public void fixJavaStyleErrorTest() {
    this.controller = new AnimationController(model, view, speed, true, true, true);
    assertEquals(10, this.controller.getSpeed());
  }
}
