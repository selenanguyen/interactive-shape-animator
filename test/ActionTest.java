import cs3500.animator.model.Action;
import cs3500.animator.model.Color;
import cs3500.animator.model.Position;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Testing class for Action class.
 */
public class ActionTest {
  Action action1;

  @Before
  public void setup() {
    action1 = new Action(new Position(100,100), new Position(100,100),
              5,6,5,5,
              new Color(0,0,255), new Color(0,0,255), 1,10);
  }

  @Test
  public void testActionGetMethods() {
    assertEquals(new Position(100, 100), action1.getStartPos());
    assertEquals(new Position(100, 100), action1.getEndPos());
    assertEquals(5, action1.getStartWidth());
    assertEquals(6, action1.getEndWidth());
    assertEquals(5, action1.getStartHeight());
    assertEquals(5, action1.getEndHeight());
    assertEquals(new Color(0, 0, 255), action1.getStartColor());
    assertEquals(new Color(0, 0, 255), action1.getEndColor());
    assertEquals(1, action1.getStartTick());
    assertEquals(10, action1.getEndTick());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEndTickSmallerThanStartTick() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,5,5,
            new Color(0,0,255), new Color(0,0,255), 5,4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartTick() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,5,5,
            new Color(0,0,255), new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeEndTick() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,5,5,
            new Color(0,0,255), new Color(0,0,255), 1,-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullStartPosition() {
    new Action(null, new Position(100,100),
            5,6,5,5,
            new Color(0,0,255), new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEndPosition() {
    new Action(new Position(100,100), null, 5,6,
            5,5, new Color(0,0,255),
            new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartWidth() {
    new Action(new Position(100,100), new Position(100,100),
            -1,6,5,5,
            new Color(0,0,255), new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeEndWidth() {
    new Action(new Position(100,100), new Position(100,100),
            5,-1,5,5,
            new Color(0,0,255), new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartHeight() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,-1,5,
            new Color(0,0,255), new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeEndHeight() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,5,-1,
            new Color(0,0,255), new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullStartColor() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,5,5,
            null, new Color(0,0,255), -1,5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEndColor() {
    new Action(new Position(100,100), new Position(100,100),
            5,6,5,5,
            new Color(0,0,255), null, -1,5);
  }
}
