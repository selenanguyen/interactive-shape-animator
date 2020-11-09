import cs3500.animator.controller.Keyframe;
import cs3500.animator.model.Action;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Color;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.fail;

/**
 * Testing class for Animated Shape class.
 * TODO: require non null and non-negative values in action constructor (tick values > 0)
 * TODO: test that ^^^^
 * TODO: ^^ same for color and shape
 * TODO: if there is no last action, isTeleportedFrom should
 *   return false and the action should be added
 */
public class AnimatedShapeTest {
  IAnimatedShape oval1;
  IAnimatedShape rectangle1;
  IAnimatedShape oval2;
  IAnimatedShape rectangle2;
  Position o2p1;
  Position o2p2;
  Position o2p3;
  Position r2p1;
  int o2w1;
  int o2w2;
  int o2h1;
  int o2h2;
  int o2h3;
  int o2w3;
  Color o2c1;
  Color o2c2;
  Color o2c3;
  int o2t1;
  int o2t2;
  int o2t3;

  @Before
  public void setup() {
    o2p1 = new Position(10, 20);
    o2p2 = new Position(10, 20);
    o2w1 = 1;
    o2w2 = 5;
    o2h1 = 10;
    o2h2 = 20;
    o2c1 = new Color(25, 25, 25);
    o2c2 = new Color(30, 30, 30);
    o2t1 = 1;
    o2t2 = 5;
    o2p3 = new Position(20, 40);
    o2w3 = 5;
    o2h3 = 40;
    o2c3 = new Color(25, 25, 25);
    o2t3 = 6;
    r2p1 = new Position(10, 20);

    List<Keyframe> oval2Keyframes = new ArrayList<>((
            Arrays.asList(
              new Keyframe(o2c1, o2p1, o2w1, o2h1, o2t1),
              new Keyframe(o2c2, o2p2, o2w2, o2h2, o2t2),
              new Keyframe(o2c3, o2p3, o2w3, o2h3, o2t3))));

    List<Action> oval2Actions = new ArrayList<Action>(
            Arrays.asList(
              new Action(o2p1, o2p2, o2w1, o2w2, o2h1, o2h2, o2c1, o2c2, o2t1, o2t2),
              new Action(o2p2, o2p3, o2w2, o2w3, o2h2, o2h3, o2c2, o2c3, o2t2, o2t3)));

    List<Keyframe> rectangle2Keyframes = new ArrayList<>(
            Arrays.asList(new Keyframe(new Color(25, 25, 25),
                            r2p1, 1, 10, 1),
                    new Keyframe(new Color(30, 30, 30),
                            new Position(10, 20), 5, 20, 5),
                    new Keyframe(new Color(25, 25, 25),
                            new Position(20, 40), 10, 40, 6),
                    new Keyframe(new Color(25,25, 25),
                            new Position(20, 40), 10, 40, 11)));
    List<Action> rectangle2Actions = new ArrayList<Action>(
            Arrays.asList(
                    new Action(r2p1,
                            new Position(10, 20), 1, 5,
                            10, 20,
                            new Color(25, 25, 25),
                            new Color(30, 30, 30), 1, 5),
                    new Action(new Position(10, 20), new Position(20, 40),
                            5, 10, 20, 40,
                            new Color(30, 30, 30), new Color(25, 25, 25),
                            5, 6),
                    new Action(new Position(20, 40), new Position(20, 40),
                            10, 10, 40, 40,
                            new Color(25, 25, 25), new Color(25, 25, 25),
                            6, 11)));
    oval1 = new AnimatedShape(new Oval("oval1"));
    rectangle1 = new AnimatedShape(new Rectangle("rectangle1"));
    oval2 = new AnimatedShape(new Oval("oval1"), oval2Keyframes);
    rectangle2 = new AnimatedShape(new Rectangle("rectangle1"), rectangle2Keyframes);
  }

  @Test
  public void testAddActions() {
    try {
      oval2.addAction(new Action(o2p2, new Position(20, 10),
              10, 10, 40, 40,
              new Color(25, 25, 25), new Color(25, 25, 25),
              6, 10));
      fail("Start position for oval2 should be invalid.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    oval2.addAction(new Action(o2p3, new Position(20, 40),
            o2w3, 10, o2h3, 40,
            o2c3, new Color(25, 25, 25),
            o2t3, 10));
    try {
      rectangle2.addAction(new Action(new Position(20, 41),
              new Position(22, 42), 10,
              12, 40, 42,
              new Color(25, 25, 25), new Color(27, 27, 27),
              11, 13));
      fail("Start position should be invalid.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      rectangle2.addAction(new Action(new Position(20, 40),
              new Position(22, 42), 11, 12,
              40, 42,
              new Color(25, 25, 25), new Color(27, 27, 27),
              11, 13));
      fail("Start width should be invalid.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      rectangle2.addAction(new Action(
              new Position(20, 40), new Position(22, 42),
              10, 12, 39, 42,
              new Color(25, 25, 25), new Color(27, 27, 27),
              11, 13));
      fail("Start height should be invalid.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      rectangle2.addAction(new Action(new Position(20, 40),
              new Position(22, 42),
              10, 12, 40, 42,
              new Color(25, 26, 25), new Color(27, 27, 27),
              11, 13));
      fail("Start color should be invalid.");
    }
    catch (IllegalArgumentException e) {
      // Do noting
    }
    rectangle2.addAction(new Action(new Position(20, 40), new Position(22, 42),
            10, 12, 40, 42,
            new Color(25, 25, 25), new Color(27, 27, 27),
            11, 13));
  }

}
