import cs3500.animator.model.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for position class.
 */
public class PositionTest {
  Position zero = new Position(0, 0);
  Position one = new Position(1, 1);

  @Test
  public void getXPosition() {
    assertEquals(0, zero.getX(), 0);
    assertEquals(1, one.getX(), 0);
  }

  @Test
  public void getYPosition() {
    assertEquals(0, zero.getY(), 0);
    assertEquals(1, one.getY(), 0);
  }
}
