package cs3500.animator.adapter;

/**
 * Represents the animation model with a list of animated shapes.
 * It will execute actions within the list of animated shapes.
 */
public interface IShape {

  /**
   * Gets the shape type.
   * @return the type of shape
   */
  ShapeType getType();

  /**
   * Gets the id of this shape.
   * @return the shape id
   */
  String getName();
  
}
