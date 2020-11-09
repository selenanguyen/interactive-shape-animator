package cs3500.animator.view;

import cs3500.animator.controller.IController;

/**
 * Interface representing a view type that the user can physically interact with.
 * In this view, the user can start, pause, resume, restart, enable looping, disable looping,
 * and change the speed of the animation.
 */
public interface IInteractiveView extends IAnimationView, VisualView {

  /**
   * Sets listeners for button, text field, and check box components in the view.
   */
  void setListener(IController listener);

  /**
   * Updates the shape list and key frames combo boxes when shapes have been added
   * or removed from the model or when the animation is restarted.
   */
  void updateComboBoxes();

  /**
   * Gets the file name specific to loading the animation.
   * @return the load file name
   */
  String getLoadFileName();

  /**
   * Gets the file name specific for exporting.
   * @return file name user wants to save animation in
   */
  String getSaveFileName();

  /**
   * Refreshes the animation panel only (not the editing panel).
   * This is used for playing the animation.
   */
  void refreshAnimation();
}
