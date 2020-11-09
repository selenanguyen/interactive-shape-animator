package cs3500.animator.adapter;

import java.awt.event.ActionListener;

/**
 * Interface representing a controller that controls the flow of data between the
 * model and the view.
 */
public interface IController extends ActionListener {

  /**
   * Gets the tick of the animation currently.
   * @return the current tick
   */
  int getTick();

  /**
   * Changes the speed of the animation to the given speed.
   * @param tempo the speed
   */
  public void changeTempo(double tempo);

  /**
   * Returns the current speed of the animation.
   * @return the current speed
   */
  public double getTempo();

  /**
   * Initializes the view and the delegate controller and adds itself as the action listener.
   */
  void setController();
}
