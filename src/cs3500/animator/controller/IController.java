package cs3500.animator.controller;

import java.awt.event.ActionListener;

/**
 * Interface representing a controller that controls the flow of data between the
 * model and the view.
 */
public interface IController extends ActionListener {

  /**
   * Starts the animation.
   * Displays the view in the controller.
   */
  public void start();

  /**
   * Returns the speed of the animation currently.
   * @return the speed
   */
  public int getSpeed();

  /**
   * Sets the name of the shape to be added.
   * @param id the shape id
   */
  public void setAddShapeId(String id);

  /**
   * Removes the shape with the given id.
   * @param id the shape id
   */
  public void removeShape(String id);


  /**
   * Sets the speed of the animation.
   * @param ticksPerSecond ticks per second
   */
  public void setSpeed(String ticksPerSecond);

  /**
   * Increments or decrements the tick by 1.
   * @param inc the increment (1 or -1)
   */
  public void incrementTick(int inc);

  /**
   * Sets the tick.
   * @param tick the tick
   */
  public void setTick(int tick);

  public void pause();

  /**
   * Removes the keyframe at the given tick for the shape with the given id.
   * @param id the shape id
   * @param tick the tick
   */
  public void removeKeyframe(String id, int tick);

  public void replaceKeyframe(String id, String c, String p, String w, String h, int tick);

  public void displayError(String errorMessage);

  public void displaySuccess(String message);

  /**
   * Adds a keyframe to the given shape.
   * @param id the shape id
   * @param c the color as a string in format (r,g,b)
   * @param p the position as a string in format (x,y)
   * @param w the width as a string (integer)
   * @param h the height as a string (integer)
   * @param tick the tick as an int
   */
  public void addKeyframe(String id, String c, String p, String w, String h, int tick);

}
