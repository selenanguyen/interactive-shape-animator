package cs3500.animator.adapter;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.model.Color;
import cs3500.animator.model.Position;

/**
 * Represents the animation model with a list of animated shapes.
 * It will execute actions within the list of animated shapes.
 */
public final class KeyFrame implements Comparable<KeyFrame> {

  private final Keyframe kf;
  private final String name;

  /**
   * Initializes this keyframe with the given values.
   * @param name the keyframe name (referring to a shape id)
   * @param tick the tick
   * @param w the width
   * @param h the height
   * @param x the x position
   * @param y the y position
   * @param r RGB red value
   * @param g RGB green value
   * @param b RGB blue value
   */
  public KeyFrame(String name, int tick, int w, int h, int x, int y, int r, int g, int b) {
    this.name = name;
    this.kf = new Keyframe(new Color(r, g, b), new Position(x, y), w, h, tick);
  }

  /**
   * Initializes this keyframe using a name and a Keyframe delegate.
   * @param name the name of the corresponding shape
   * @param kf the delegate keyfram
   */
  public KeyFrame(String name, Keyframe kf) {
    this.name = name;
    this.kf = kf;
  }

  public int getX() {
    return kf.getPosition().getX();
  }

  public int getY() {
    return kf.getPosition().getY();
  }

  public int getBlue() {
    return kf.getColor().getB();
  }

  public int getGreen() {
    return kf.getColor().getG();
  }

  public int getRed() {
    return kf.getColor().getR();
  }

  public String getName() {
    return this.name;
  }

  public int getTime() {
    return kf.getTick();
  }

  public int getWidth() {
    return kf.getWidth();
  }

  public int getHeight() {
    return kf.getHeight();
  }

  @Override
  public int compareTo(KeyFrame o) {
    if (kf.getTick() > o.getTime()) {
      return 1;
    }
    if (kf.getTick() < o.getTime()) {
      return -1;
    }
    return 0;
  }
  
}
