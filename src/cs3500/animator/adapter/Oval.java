package cs3500.animator.adapter;

import java.util.List;

import cs3500.animator.model.IAnimatedShape;

/**
 * Represents an oval.
 */
public class Oval extends Shape {

  /**
   * Initializes this oval with the given shape and list of keyframes.
   * @param shape the shape
   * @param kfs the list of keyframes
   */
  public Oval(IAnimatedShape shape, List<KeyFrame> kfs) {
    super(shape, kfs);
  }

  @Override
  public ShapeType getType() {
    return ShapeType.Oval;
  }
}
