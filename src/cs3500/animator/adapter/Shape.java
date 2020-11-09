package cs3500.animator.adapter;

import java.util.List;
import cs3500.animator.model.IAnimatedShape;

/**
 * Represents an animated shape.
 */
public abstract class Shape implements IShape {

  // The delegate shape
  private final IAnimatedShape shape;

  /**
   * Initializes this shape using an animated shape and a list of keyframes.
   * @param shape the shape
   * @param kfs the keyframes
   */
  protected Shape(IAnimatedShape shape, List<KeyFrame> kfs) {
    this.shape = shape;
  }

  @Override
  public String getName() {
    return this.shape.getId();
  }
}
