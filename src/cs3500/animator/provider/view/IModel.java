package cs3500.animator.provider.view;

import java.util.List;
import java.util.Map;


import cs3500.animator.adapter.IShape;
import cs3500.animator.adapter.KeyFrame;

/**
 * represent the animation model interface.
 */
public interface IModel {

  /**
   * add the shape to the list of shapes.
   *
   * @param shape the given shape
   */
  public void addShape(IShape shape);


  /**
   * remove the shape from the list of shapes.
   *
   * @param shape the given shape
   */
  public void removeShape(IShape shape);

  /**
   * add the key frame to the list of key frames.
   *
   * @param key k
   */
  public void addKeyFrame(KeyFrame key);

  /**
   * remove the key frame from the list of key frames.
   *
   * @param key k
   */
  public void removeKeyFrame(KeyFrame key);

  /**
   * get the list of the shapes from the model.
   *
   * @return the list of the shapes
   */
  public List<IShape> getShape();

  /**
   * get the list of the key frames from the model.
   *
   * @param name the shape's name
   * @return the shape's list of key frames
   */
  public List<KeyFrame> getKeyFrame(String name);

  /**
   * set the Map of the model.
   *
   * @return the map of the model
   */
  public Map<IShape, List<KeyFrame>> getMap();

  /**
   * set the original x point.
   *
   * @param i x point
   */
  public void setBoundX(int i);

  /**
   * set the original y point.
   *
   * @param i y point
   */
  public void setBoundY(int i);

  /**
   * set the original width size.
   *
   * @param i width of the screen
   */
  public void setBoundWidth(int i);

  /**
   * set the original height size.
   *
   * @param i height of the screen
   */
  public void setBoundHeight(int i);

  /**
   * get the original x point.
   *
   * @return x point
   */
  public int getBoundX();

  /**
   * get the original y point.
   *
   * @return y point
   */
  public int getBoundY();

  /**
   * get the width bound.
   *
   * @return width bound
   */
  public int getBoundWidth();

  /**
   * get the height bound.
   *
   * @return height bound
   */
  public int getBoundHeight();

}
