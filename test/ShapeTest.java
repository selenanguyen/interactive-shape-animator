
import cs3500.animator.model.Color;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Test for shape class.
 */
public class ShapeTest {
  Position zero;
  Position neg;
  Color red;
  Shape circle;
  Shape rectangle;

  @Before
  public void setup() {
    zero = new Position(0, 0);
    neg = new Position( -1, -1);
    red = new Color(255, 0, 0);
    circle = new Oval("circle", 5, 5, red, zero, 0, 0);
    rectangle = new Rectangle("rectangle", 5, 5, red, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOvalBadColor() {
    Color bad = new Color(256, 256, 256);
    Shape oval = new Oval("oval", 5, 5, bad, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOvalNullId() {
    Shape oval = new Oval(null, 5, 5, red, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOvalNegHeight() {
    Shape oval = new Oval("oval", -1, 5, red, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOvalNegWidth() {
    Shape oval = new Oval("oval", 5, -1, red, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOvalNullColor() {
    Shape oval = new Oval("oval", 5, 5, null, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectNegHeight() {
    Shape rect = new Rectangle("rect", -5, 5, red, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectNegWidth() {
    Shape rect = new Rectangle("rect",5, -5, red,
            zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectBadColor() {
    Color bad = new Color(256, 256, 256);
    Shape rect = new Rectangle("rect", 5, 5, bad, zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectNullId() {
    Shape rect = new Rectangle(null, 5, 5, red,
            zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectNullHeight() {
    Shape rect = new Rectangle("rect", -1, 5, red,
            zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectNullWidth() {
    Shape rect = new Rectangle("rect", 5, -1, red,
            zero, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectNullColor() {
    Shape rect = new Rectangle("rect", 5, 5, null,
            zero, 0, 0);
  }

  @Test
  public void testOvalGetMethods() {
    assertEquals("circle", circle.getId());
    assertEquals(5, circle.getHeight());
    assertEquals(5, circle.getWidth());
    assertEquals(red, circle.getColor());
  }

  @Test
  public void testRectGetMethods() {
    assertEquals("rectangle", rectangle.getId());
    assertEquals(5, rectangle.getHeight());
    assertEquals(5, rectangle.getWidth());
    assertEquals(red, rectangle.getColor());
  }

  @Test
  public void testIsOval() {
    assertTrue(circle.isOval());
    assertFalse(rectangle.isOval());
  }

  @Test
  public void testIsRectangle() {
    assertFalse(circle.isRectangle());
    assertTrue(rectangle.isRectangle());
  }
}
