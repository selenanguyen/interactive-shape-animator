package cs3500.animator.model;

import cs3500.animator.controller.Keyframe;

/**
 * Represents the animation model with a list of animated shapes.
 * It will execute actions within the list of animated shapes.
 */
public interface AnimationOperations extends ReadOnlyAnimationOperations {

  /**
   * Adds the given action to the corresponding shape.
   * @param id id of the shape
   * @param action the action to add
   */
  void addAction(String id, Action action);

  /**
   * Adds the given shape to the model.
   * @param shape the shape to add to the model
   */
  void addShape(Shape shape);

  /**
   * Removes the shape with the given id from the model.
   * @param id the shape id
   * @return the shape being removed
   */
  Shape removeShape(String id);

  /**
   * Removes the action that occurs at the given tick for the shape with the given id.
   * @param id the shape id
   * @param tick the tick
   * @return the action being removed
   */
  Action removeAction(String id, int tick);

  /**
   * Applies the changes at the given tick.
   * @param tick the tick to be applied.
   */
  void applyTick(int tick);

  /**
   * Adds the keyframe to the model for the shape with the given id.
   * @param id the shape id
   * @param kf the keyframe
   */
  void addKeyFrame(String id, Keyframe kf);

  /**
   * Removes the keyframe at the given tick for the given shape.
   * @param id the shape id
   * @param tick the tick
   * @return the keyframe
   * @throws IllegalArgumentException if no keyframe exists at the tick
   */
  Keyframe removeKeyframe(String id, int tick);

  /**
   * Deletes all animated shapes from the model.
   */
  void clear();

  /**
   * Adds an animated shape to the model.
   * @param animatedShape animated shape to be added to the model
   */
  public void addAnimatedShape(IAnimatedShape animatedShape);

}
