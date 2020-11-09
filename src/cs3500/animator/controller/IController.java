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
  void start();

  /**
   * Returns the speed of the animation currently.
   * @return the speed
   */
  int getSpeed();

  /**
   * Removes the shape with the given id.
   * @param id the shape id
   */
  void removeShape(String id);


  /**
   * Sets the speed of the animation.
   * @param ticksPerSecond ticks per second
   */
  void setSpeed(String ticksPerSecond);

  /**
   * Increments or decrements the tick by 1.
   * @param inc the increment (1 or -1)
   */
  void incrementTick(int inc);

  /**
   * Sets the tick.
   * @param tick the tick
   */
  void setTick(int tick);

  void pause();

  /**
   * Removes the keyframe at the given tick for the shape with the given id.
   * @param id the shape id
   * @param tick the tick
   */
  void removeKeyframe(String id, int tick);

  void replaceKeyframe(String id, String c, String p, String rot, String w, String h, int tick);

  void displayError(String errorMessage);

  void displaySuccess(String message);

  /**
   * Adds a keyframe to the given shape.
   * @param id the shape id
   * @param c the color as a string in format (r,g,b)
   * @param p the position as a string in format (x,y)
   * @param w the width as a string (integer)
   * @param h the height as a string (integer)
   * @param tick the tick as an int
   */
  void addKeyframe(String id, String c, String p, String rot, String w, String h, int tick);

  void addShape(String type, String id, String layer);

  void removeLayer(String layer);

  void switchLayers(String l1, String l2);

}
