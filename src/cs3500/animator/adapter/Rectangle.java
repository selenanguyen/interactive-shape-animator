package cs3500.animator.adapter;

import java.util.List;

import cs3500.animator.model.IAnimatedShape;

/**
 * Represents a rectangle.
 */
public class Rectangle extends Shape {

  /**
   * Initializes the rectangle using the given shape and a list of keyframes.
   * @param shape the shape
   * @param kfs the keyframes
   */
  public Rectangle(IAnimatedShape shape, List<KeyFrame> kfs) {
    super(shape, kfs);
  }

  @Override
  public ShapeType getType() {
    return ShapeType.Rec;
  }
}