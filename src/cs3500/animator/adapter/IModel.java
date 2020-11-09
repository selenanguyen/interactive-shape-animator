package cs3500.animator.adapter;

import java.util.List;
import java.util.Map;

import cs3500.animator.model.AnimationOperations;

/**
 * Represents the animation model with a list of animated shapes.
 * It will execute actions within the list of animated shapes.
 */
public interface IModel extends AnimationOperations {
  /**
   * Returns the canvas height.
   * @return the canvas height
   */
  int getBoundHeight();

  /**
   * Returns the canvas width.
   * @return the canvas width
   */
  int getBoundWidth();

  /**
   * Returns the canvas starting x position.
   * @return the ccanvas starting x position
   */
  int getBoundX();

  /**
   * Returns the canvas starting y position.
   * @return the canvas starting y position
   */
  int getBoundY();

  /**
   * Returns a map from each shape to its list of keyframes.
   * @return the map
   */
  Map<IShape, List<KeyFrame>> getMap();

  /**
   * Returns the list of shapes.
   * @return the list of shapes
   */
  List<IShape> getShape();

  /**
   * add the key frame to the list of key frames.
   * @param key the keyframe
   */
  public void addKeyFrame(KeyFrame key);

  /**
   * remove the key frame from the list of key frames.
   *
   * @param key the keyframe
   */
  public void removeKeyFrame(KeyFrame key);

}
