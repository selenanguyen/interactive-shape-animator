package cs3500.animator.provider.view;


/**
 * represent the Visual View interface.
 */
public interface IVisualView extends AnimationView {
  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * To handle the error message.
   * @param error the error message
   */
  public void dealError(String error);

  void setSpeed(double speed);
}
