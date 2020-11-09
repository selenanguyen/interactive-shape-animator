package cs3500.animator.view;

/**
 * Template for the visual view - a graphical representation of the animation.
 */
public interface VisualView extends IAnimationView {

  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Refreshes the view.
   */
  void refresh();

}
