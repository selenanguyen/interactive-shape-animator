package cs3500.animator.view;

/**
 * Template for the textual view - a textual representation of the animation.
 * Will be use for both text and svg views.
 */
public interface TextualView extends IAnimationView {
  /**
   * Retrieves the textual representation of the view.
   * @return the textual representation of the view
   */
  String getText();

  /**
   * Retrieves the textual representation of an oval.
   * @return the textual representation of an oval.
   */
  String getOvalAsString();

  /**
   * Retrieves the textual representation of a rectangle.
   * @return the textual representation of a rectangle.
   */
  String getRectangleAsString();

  /**
   * Writes text information about the animation into a file.
   */
  void write();


}
