package cs3500.animator.model;

import cs3500.animator.controller.Keyframe;

import java.util.List;

/**
 * Represents the read-only animation model with a list of animated shapes.
 */
public interface ReadOnlyAnimationOperations {

  /**
   * Returns a hash map of existing shapes in the animation as a key and
   * position as its associated value.
   *
   * @return the existing shapes as a hash map
   */
  List<Shape> getShapes();

  /**
   * Returns the shape with the given id.
   * @param id the shape to be retrieved.
   */
  Shape getShape(String id);


  /**
   * Returns the shapes at the given tick, without actually updating the current shape.
   * @param tick the tick
   */
  List<Shape> getShapesAt(int tick);

  /**
   * Returns the shape at the given tick, without actually updating the current shape.
   * @param tick the tick
   */
  Shape getShapeAt(String id, int tick);

  /**
   * Returns the list of animated shapes.
   */
  List<IAnimatedShape> getAnimatedShapes();

  /**
   * Returns the canvas height.
   * @return the canvas height
   */
  int getCanvasHeight();

  /**
   * Returns the canvas width.
   * @return the canvas width
   */
  int getCanvasWidth();

  /**
   * Returns the canvas starting x position.
   * @return the ccanvas starting x position
   */
  int getCanvasStartingX();

  /**
   * Returns the canvas starting y position.
   * @return the canvas starting y position
   */
  int getCanvasStartingY();

  /**
   * Returns the animated shape with the given id.
   * @param id the shape id
   * @return the animated shape
   */
  IAnimatedShape getAnimatedShape(String id);

  /**
   * Returns the last tick in this animation.
   * @return the last tick
   */
  int getLastTick();

  /**
   * Returns the list of actions that have been applied to a shape at a specific time.
   * @param id the id of the shape we are getting it's actions from
   * @param currentTick specific time in animation
   * @return list of actions completed in the animation
   */
  List<Action> getMotionsAppliedToShape(String id, int currentTick);

  /**
   * Returns the tick currently applied to this animation.
   * @return the tick
   */
  int getTick();

  /**
   * Returns the list of keyframes for the shape with the given id.
   * @param id the shape id
   * @return the list of keyframes
   * @throws IllegalArgumentException if no shape exists with the given id
   */
  List<Keyframe> getKeyframes(String id);

}
