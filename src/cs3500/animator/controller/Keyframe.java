package cs3500.animator.controller;

import cs3500.animator.model.Color;
import cs3500.animator.model.Position;

/**
 * A key frame class representing a moment of time in the animation that
 * contains the status of the shape's features.
 */
public final class Keyframe {
  private final Color color;
  private final Position position;
  private final int width;
  private final int height;
  private final int tick;
  private final int rotation;

  /**
   * Represents an instance where the shape is invisible.
   *
   * @param tick the tick that this KeyFrame represents
   */
  public Keyframe(int tick) {
    color = null;
    position = null;
    width = -1;
    height = -1;
    this.tick = tick;
    this.rotation = 0;
  }

  /**
   * Initializes a keyframe with no rotation.
   *
   * @param tick the tick that this KeyFrame represents
   */
  public Keyframe(Color c, Position p, int w, int h, int tick) {
    color = c;
    position = p;
    width = w;
    height = h;
    this.tick = tick;
    this.rotation = 0;
  }

  /**
   * Initializes a keyframe with rotation.
   * @param c the color
   * @param p the position
   * @param w the width
   * @param h the height
   * @param rot the rotation in degrees
   * @param tick the tick
   */
  public Keyframe(Color c, Position p, int w, int h, int rot, int tick) {
    color = c;
    position = p;
    width = w;
    height = h;
    this.tick = tick;
    this.rotation = rot;
  }

  public int getTick() {
    return this.tick;
  }

  public Color getColor() {
    return this.color;
  }

  public Position getPosition() {
    return this.position;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getRotation() {
    return this.rotation;
  }

  @Override
  public String toString() {
    return "Tick " + this.tick + ": " + String.join(", ",
            this.position.toString(), Integer.toString(this.rotation) + "˚",
            Integer.toString(this.width),
            Integer.toString(this.height), this.color.toString());
  }
}