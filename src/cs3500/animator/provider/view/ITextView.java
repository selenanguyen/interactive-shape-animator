package cs3500.animator.provider.view;

/**
 * represent the Text view interface.
 */
public interface ITextView extends AnimationView {
  /**
   * to get the information of the animation.
   *
   * @return strings as information
   */
  public Appendable getInfo();

  /**
   * write the file down.
   *
   * @param name file name
   */
  public void writeFile(String name);
}
