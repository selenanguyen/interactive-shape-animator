package cs3500.animator.model;

import java.util.List;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.view.TextualView;

/**
 * An interface for an animated shape object.
 * This animated shape will consist of an immutable shape and
 * an array list of actions associated with that shape.
 */
public interface IAnimatedShape {
  /**
   * Returns a shape that will be animated.
   * @return the shape
   */

  Shape getShape();

  /**
   * Returns an array list of actions to complete in the full animation
   * that is associated with a specific shape.
   *
   * @return the list of actions
   */
  List<Action> getActions();

  /**
   * Adds a new animation action event to the list of existing actions.
   *
   * @param action new animation action
   */
  void addAction(Action action);

  /**
   * Returns the id of this shape.
   * @return the id of the shape
   */
  String getId();

  /**
   * Applies the given tick to this shape.
   * @param tick the tick number of the keyframe to be applied
   */
  void applyTick(int tick);

  /**
   * Returns the shape at the given tick, without actually updating the shape.
   * @param tick the tick to be applied
   * @return the shape at the given tick
   */
  Shape getShapeAt(int tick);

  /**
   * Returns the string representation of this type of the animated shape.
   * @return the string representation of this type of the animated shape
   */
  String getShapeAsString();

  /**
   * Returns the string representation of this type of the animated shape, depending on the view.
   * @return the string representation of this type of the animated shape.
   */
  String getShapeAsString(TextualView view);

  /**
   * Makes a copy of a shape.
   * @return a copy of the shape
   */
  IAnimatedShape makeCopy();

  /**
   * Gets the state of the shape at the tick of it's first action.
   * @return a copy of the shape at it's first action tick
   */
  Shape getShapeAtStart();

  /**
   * Gets a list of actions that have been applied before a certain tick.
   * @param currentTick moment in time (tick)
   * @return list of applied actions
   */
  List<Action> getActionsAppliedBefore(int currentTick);

  /**
   * Removes the action that occurs at the given tick.
   * @param tick the tick
   * @return the Action being removed
   */
  Action removeAction(int tick);

  /**
   * Returns the last tick of the last action.
   * @return the last tick of this shape's animation
   * @throws IllegalArgumentException if there are no keyframes
   */
  int getLastTick();

  /**
   * Returns the first tick of the first keyframe.
   * @return the first tick
   * @throws IllegalArgumentException if there are no keyframes
   */
  int getFirstTick();

  /**
   * Adds the given keyframe to this shape's animation.
   * @param kf the keyframe
   */
  void addKeyframe(Keyframe kf);

  /**
   * Removes the keyframe that occurs at the given tick.
   * @param tick the tick
   */
  Keyframe removeKeyframe(int tick);

  /**
   * Returns the list of keyframes in this animation.
   * @return the list of keyframes
   */
  List<Keyframe> getKeyframes();
}
