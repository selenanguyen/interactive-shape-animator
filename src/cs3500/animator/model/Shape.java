package cs3500.animator.model;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.view.TextualView;

/**
 * Represents a shape and its attributes that are manipulated by animation actions.
 */
public interface Shape {

  /**
   * Get the name of the shape.
   *
   * @return the id
   */
  String getId();

  /**
   * Get the height of the shape.
   *
   * @return the height
   */
  int getHeight();

  /**
   * Get the width of the shape.
   *
   * @return the width
   */
  int getWidth();

  /**
   * Get the color of the shape.
   *
   * @return the color
   */
  Color getColor();

  /**
   * Make a copy of the shape.
   *
   * @return the copy Shape
   */
  Shape cloneShape();

  /**
   * Returns whether this shape is currently invisible.
   *
   * @return whether this shape is currently invisible (has not yet appeared)
   */
  boolean isInvisible();

  /**
   * Get the position of the shape.
   *
   * @return the position of the shape
   */
  Position getPosition();

  /**
   * Returns whether this shape is an oval.
   * @return whether this shape is an oval.
   */
  boolean isOval();

  /**
   * Returns whether this shape is a rectangle.
   * @return whether this shape is a rectangle.
   */
  boolean isRectangle();

  /**
   * Returns the string representation of this type of shape.
   * @return the string representation of this type of shape
   */
  String getShapeAsString();

  /**
   * Returns the string representation of this type of shape, depending on the view.
   * @return the string representation of this type of shape
   */
  String getShapeAsString(TextualView view);

  void setRotation(int rot);

  void makeInvisible();

  void setState(int height, int width, Color color, int rot, Position position);

  void applyKeyframe(Keyframe kf);

  int getRotation();

  int getLayer();

  void setLayer(int l);

}
