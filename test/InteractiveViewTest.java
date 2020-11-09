import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ReadOnlyAnimationOperations;
import cs3500.animator.view.InteractiveView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the interactive animation view.
 */
public class InteractiveViewTest {

  ReadOnlyAnimationOperations model;
  int speed;


  @Before
  public void setup() {
    this.model = new AnimationModel();
    speed = 1;
  }

  @Test
  public void testNullModel() {
    try {
      new InteractiveView(null, speed);
    }
    catch (IllegalArgumentException e) {
      assertEquals("The model can't be null", e.getMessage());
    }
    catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testNegativeTempo() {
    try {
      new InteractiveView(model, -1);
    }
    catch (IllegalArgumentException e) {
      assertEquals("The speed can't be negative or 0", e.getMessage());
    }
    catch (Exception e) {
      fail();
    }
  }
}
