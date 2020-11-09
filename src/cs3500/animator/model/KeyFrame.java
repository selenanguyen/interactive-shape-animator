/*package cs3500.animator.model;

/**
 * A key frame class representing a moment of time in the animation that
 * contains the status of the shape's features.
 */
//public class KeyFrame {
//  private final Color color;
//  private final Position position;
//  private final int width;
//  private final int height;
//  private final boolean isVisible;
//  private final int tick;
//
//  /**
//   * Represents an instance where the shape is invisible.
//   * @param tick the tick that this KeyFrame represents
//   */
//  public KeyFrame(int tick) {
//    color = null;
//    position = null;
//    width = -1;
//    height = -1;
//    this.isVisible = false;
//    this.tick = tick;
//  }
//
//  /**
//   * Constructor of a key frame with a tick and shape features.
//   * @param c color of the shape
//   * @param p position of the shape
//   * @param w width of the shape
//   * @param h height of the shape
//   * @param tick tick of the animation action
//   */
//  public KeyFrame(Color c, Position p, int w, int h, int tick) {
//    this.color = c;
//    this.position = p;
//    this.width = w;
//    this.height = h;
//    this.tick = tick;
//    this.isVisible = true;
//  }
//
//  public int getTick() {
//    return this.tick;
//  }
//
//  public Color getColor() {
//    return this.color;
//  }
//
//  public Position getPosition() {
//    return this.position;
//  }
//
//  public int getWidth() {
//    return this.width;
//  }
//
//  public int getHeight() {
//    return this.height;
//  }
//
//  /**
//   * Returns whether the shape is visible at this keyframe.
//   * @return whether the shape is visible at this keyframe.
//   */
//  public boolean isVisible() {
//    return this.isVisible;
//  }
//
//  @Override
//  public String toString() {
//    if (!this.isVisible) {
//      return "invisible";
//    }
//    return "height = " + this.height + ", width = " + this.width
//            + ", position: " + this.position.toString()
//            + ", color: " + this.color.toString() + ", this.tick = " + this.tick;
//  }
//
//}
