package cs3500.animator.provider.view;

/**
 * represent the SVG View interface.
 */
public interface ISVGView extends AnimationView {
  /**
   * to get the svg format.
   *
   * @return strings as svg format
   */
  public Appendable svgFormat();

  /**
   * to write the file down.
   *
   * @param name file name
   */
  public void writeFile(String name);
}
