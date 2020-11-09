import cs3500.animator.model.Action;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.Color;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for animation model class.git
 */
public class AnimationModelTest {

  private AnimationOperations model;
  private String oval1id = "oval1";
  private String rect1id = "rect1";
  private Shape oval1;
  private Shape rect1;
  private Position o1p1;
  private Position o1p3;
  private int o1w1;
  private int o1h1;
  private int o1h3;
  private int o1w3;
  private Color o1c1;
  private Color o1c3;
  private Position r1p2;
  private Position r1p3;
  private int r1w2;
  private int r1w3;
  private int r1h2;
  private int r1h3;
  private Color r1c2;
  private Color r1c3;
  int r1t1;
  int r1t2;
  int r1t3;

  @Before
  public void setup() {
    this.model = new AnimationModel();
    o1p1 = new Position(10, 20);
    Position o1p2 = new Position(10, 20);
    o1w1 = 1;
    int o1w2 = 5;
    o1h1 = 10;
    int o1h2 = 20;
    o1c1 = new Color(25, 25, 25);
    Color o1c2 = new Color(30, 30, 30);
    int o1t1 = 1;
    int o1t2 = 5;
    o1p3 = new Position(20, 40);
    o1w3 = 5;
    o1h3 = 40;
    o1c3 = new Color(25, 25, 25);
    int o1t3 = 6;
    oval1 = new Oval(oval1id, 0);
    rect1 = new Rectangle(rect1id, 0);
    Position r1p1 = new Position(10, 20);
    r1p2 = new Position(10, 20);
    r1p3 = new Position(15, 25);
    int r1w1 = 1;
    r1w2 = 5;
    r1w3 = 10;
    int r1h1 = 10;
    r1h2 = 20;
    r1h3 = 25;
    Color r1c1 = new Color(25, 25, 25);
    r1c2 = new Color(30, 30, 30);
    r1c3 = new Color(35, 35, 35);
    r1t1 = 1;
    r1t2 = 5;
    r1t3 = 10;
    oval1 = new Oval(oval1id, 0);
    rect1 = new Rectangle(rect1id, 0);
    this.model.addShape(oval1);
    this.model.addShape(rect1);
    this.model.addAction(oval1id, new Action(o1p1, o1p2, o1w1, o1w2, o1h1, o1h2,
            o1c1, o1c2, o1t1, o1t2));
    this.model.addAction(oval1id,
            new Action(o1p2, o1p3, o1w2, o1w3, o1h2, o1h3, o1c2, o1c3, o1t2, o1t3));
    this.model.addAction(rect1id, new Action(r1p1, r1p2, r1w1, r1w2, r1h1, r1h2,
            r1c1, r1c2, r1t1, r1t2));
    this.model.addAction(rect1id,
            new Action(r1p2, r1p3, r1w2, r1w3, r1h2, r1h3, r1c2, r1c3, r1t2, r1t3));
  }

  @Test
  public void testAddActions() {
    this.model.addAction(oval1id, new Action(new Position(20, 40),
            new Position(25, 45),
            5, 10, 40, 45,
            new Color(25, 25, 25), new Color(30, 30, 30),
            6, 11));
    Shape s = this.model.getShapeAt(oval1id, 7);
    assertEquals(s.getId(), oval1id);

    // Tests increments of actions and equality of position/color.
    assertEquals(s.getColor(), new Color(26, 26, 26));
    assertEquals(s.getHeight(), 41);
    assertEquals(s.getWidth(), 6);
    assertEquals(s.getPosition(), new Position(21, 41));
    assertFalse(s.isInvisible());
    this.model.getShapes().stream().forEach(shape -> {
      if (!shape.isInvisible()) {
        fail("Shapes should be invisible.");
      }
    });
    // Testing invalid action adds
    try {
      this.model.addAction(rect1id, new Action(r1p2, o1p1, r1w3, o1w1,
              r1h3, o1w1, r1c3, o1c1, r1t3, r1t3 + 1));
      fail("Position teleportation detection fail.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addAction(rect1id, new Action(r1p3, o1p1, r1w2, o1w1,
              r1h3, o1h1, r1c3, o1c1, r1t3, r1t3 + 1));
      fail("Width teleportation detection fail.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addAction(rect1id, new Action(r1p3, o1p1, r1w3, o1w1,
              r1h2, o1w1, r1c3, o1c1, r1t3, r1t3 + 1));
      fail("Height teleportation detection fail.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addAction(rect1id, new Action(r1p3, o1p1, r1w3, o1w1,
              r1h3, o1h1, r1c2, o1c1, r1t3, r1t3 + 1));
      fail("Color teleportation detection fail.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addAction(rect1id, new Action(r1p3, o1p1, r1w3, o1w1,
              r1h3, o1h1, r1c3, o1c1, r1t2, r1t3 + 1));
      fail("Tick teleportation detection fail.");
    }
    catch (IllegalArgumentException e) {
      // Do nothing
    }
    // Adding valid action to rectangle
    this.model.addAction(rect1id, new Action(r1p3, new Position(r1p3.getX() + 5,
            r1p3.getY() + 5), r1w3, r1w3 + 5,
            r1h3, r1h3 + 5, r1c3, new Color(r1c3.getR(), r1c3.getG(), r1c3.getB()),
            r1t3, r1t3 + 5));
    List<Shape> shapes = this.model.getShapes();

    // Ticks not applied; shapes should be invisible
    for (int i = 0; i < shapes.size(); i++) {
      assertTrue(shapes.get(i).isInvisible());
    }
    this.model.applyTick(6);
    shapes = this.model.getShapesAt(6);
    assertEquals(this.model.getShape(rect1id), shapes.get(1));
    assertEquals(new Oval(oval1id, o1h3, o1w3, o1c3, o1p3, 0, 0), this.model.getShape(oval1id));
    this.model.applyTick(7);
    assertEquals(new Rectangle(rect1id, r1h2 + 2, r1w2 + 2,
            new Color(32, 32, 32), new Position(12, 22), 0, 0),
            this.model.getShape(rect1id));
    this.model.applyTick(11);
    assertEquals(new Oval(oval1id, 45, 10, new Color(30, 30, 30),
            new Position(25, 45), 0, 0), this.model.getShape(oval1id));
    this.model.applyTick(15);
    assertFalse(this.model.getShape(rect1id).isInvisible());
    assertTrue(this.model.getShape(oval1id).isInvisible());
  }

  @Test
  public void testGetShapes() {
    List<Shape> shapes = new ArrayList(Arrays.asList(oval1, rect1));
    for (int i = 0; i < shapes.size(); i++) {
      assertEquals(model.getShapes().get(i), shapes.get(i));
    }
  }

  @Test
  public void testToString() {
    assertEquals("Create oval named \"oval1\"\n"
            + "Create rectangle named \"rect1\"\n"
            + "\n" + "From time 1 to 5, oval1 stays put at (10,20), "
            + "grows from 1x10 to 5x20, and turns rgb(30,30,30).\n"
            + "From time 5 to 6, oval1 moves from (10,20) to (20,40), "
            + "grows from 5x20 to 5x40, and turns rgb(25,25,25).\n"
            + "\n" + "From time 1 to 5, rect1 stays put at (10,20), "
            + "grows from 1x10 to 5x20, and turns rgb(30,30,30).\n"
            + "From time 5 to 10, rect1 moves from (10,20) to (15,25), "
            + "grows from 5x20 to 10x25, and turns rgb(35,35,35).", this.model.toString());
  }
}