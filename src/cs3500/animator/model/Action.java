package cs3500.animator.model;

import java.util.HashMap;
import java.util.Map;

import cs3500.animator.controller.Keyframe;

/**
 * The animation action to be executed on a shape.
 */
public final class Action {
  private final Position startPos;
  private final Position endPos;
  private final int startWidth;
  private final int endWidth;
  private final int startHeight;
  private final int endHeight;
  private final Color startColor;
  private final Color endColor;
  private final int startRot;
  private final int endRot;
  private final int startTick;
  private final int endTick;

  private final Map<Integer, Color> colors = new HashMap<Integer, Color>();
  private final Map<Integer, Position> positions = new HashMap<Integer, Position>();
  private final Map<Integer, Integer> heights = new HashMap<Integer, Integer>();
  private final Map<Integer, Integer> widths = new HashMap<Integer, Integer>();
  private final Map<Integer, Integer> rotations = new HashMap<>();


  /**
   * Constructor of the action with start and end values of all attributes.
   * @param startPos start position of shape
   * @param endPos end position of shape
   * @param startWidth start width of shape
   * @param endWidth end width of shape
   * @param startHeight start height of shape
   * @param endHeight end height of shape
   * @param startColor start color of shape
   * @param endColor end color of shape
   * @param startTick start tick of shape
   * @param endTick end tick of shape
   */
  public Action(Position startPos, Position endPos, int startWidth, int endWidth,
                int startHeight, int endHeight, Color startColor, Color endColor,
                int startTick, int endTick) {
    if (startPos == null || endPos == null || startWidth < 0 || endWidth < 0
            || startHeight < 0 || endHeight < 0 || startColor == null
            || endColor == null) {
      throw new IllegalArgumentException("Action fields can't be null or negative");
    }
    if (startTick <= 0 || endTick <= 0) {
      throw new IllegalArgumentException("Start or end tick has to be greater than 0");
    }
    if (endTick < startTick) {
      throw new IllegalArgumentException("End tick must be greater than start tick: "
      + startTick + " to " + endTick);
    }

    this.startPos = startPos;
    this.endPos = endPos;
    this.startWidth = startWidth;
    this.endWidth = endWidth;
    this.startHeight = startHeight;
    this.endHeight = endHeight;
    this.startColor = startColor;
    this.endColor = endColor;
    this.startTick = startTick;
    this.endTick = endTick;
    this.startRot = 0;
    this.endRot = 0;
    this.setShapeStates();
  }

  /**
   * Initializes an action with start and end rotations.
   * @param startPos the start position
   * @param endPos the end position
   * @param startWidth the start width
   * @param endWidth the end width
   * @param startHeight the sstart height
   * @param endHeight the end height
   * @param startColor the start color
   * @param endColor the end color
   * @param startRot the start rotation
   * @param endRot the end rotation
   * @param startTick the start tick
   * @param endTick the end tick
   */
  public Action(Position startPos, Position endPos, int startWidth, int endWidth,
                int startHeight, int endHeight, Color startColor, Color endColor,
                int startRot, int endRot,
                int startTick, int endTick) {
    if (startPos == null || endPos == null || startWidth < 0 || endWidth < 0
            || startHeight < 0 || endHeight < 0 || startColor == null
            || endColor == null) {
      throw new IllegalArgumentException("Action fields can't be null or negative");
    }
    if (startTick <= 0 || endTick <= 0) {
      throw new IllegalArgumentException("Start or end tick has to be greater than 0");
    }
    if (endTick < startTick) {
      throw new IllegalArgumentException("End tick must be greater than start tick: "
              + startTick + " to " + endTick);
    }

    this.startPos = startPos;
    this.endPos = endPos;
    this.startWidth = startWidth;
    this.endWidth = endWidth;
    this.startHeight = startHeight;
    this.endHeight = endHeight;
    this.startColor = startColor;
    this.endColor = endColor;
    this.startTick = startTick;
    this.endTick = endTick;
    this.startRot = startRot;
    this.endRot = endRot;
    this.setShapeStates();
  }

  /**
   * Initializes an action using a start and end keyframe.
   * @param kf1 the start kf
   * @param kf2 the end kf
   */
  public Action(Keyframe kf1, Keyframe kf2) {
    this(kf1.getPosition(), kf2.getPosition(), kf1.getWidth(), kf2.getWidth(), kf1.getHeight(),
            kf2.getHeight(), kf1.getColor(), kf2.getColor(), kf1.getRotation(), kf2.getRotation(),
            kf1.getTick(), kf2.getTick());
  }

  /**
   * Gets the state of the shape at a specific tick.
   * @param s the shape
   * @param tick time
   * @return a shape
   */
  public Shape getShapeAt(Shape s, int tick) {
    Shape copy = s.cloneShape();
    applyTick(copy, tick);
    return copy;
  }

  /**
   * Adds a state to the shape at a specific time.
   * @param s the shape's state
   * @param tick time
   */
  public void applyTick(Shape s, int tick) {
    if (tick < this.startTick || tick > this.endTick) {
      s.makeInvisible();
    }
    if (this.startTick == this.endTick) {
      s.setState(endHeight, endWidth, endColor, endRot, endPos);
    }
    else {
      s.setState(heights.get(tick), widths.get(tick), colors.get(tick), rotations.get(tick),
              positions.get(tick));
    }
  }

  private void setShapeStates() {
    double ticks = this.endTick - this.startTick;
    // Increments per tick:
    double x = (endPos.getX() - startPos.getX());
    double y = (endPos.getY() - startPos.getY());
    double w = (endWidth - startWidth);
    double h = (endHeight - startHeight);
    double r = (endColor.getR() - startColor.getR());
    double g = (endColor.getG() - startColor.getG());
    double b = (endColor.getB() - startColor.getB());
    double rot = (endRot - startRot);

    if (ticks != 0) {
      x = x / (double) ticks;
      y = y / (double) ticks;
      w = w / (double) ticks;
      h = h / (double) ticks;
      r = r / (double) ticks;
      g = g / (double) ticks;
      b = b / (double) ticks;
      rot = rot / (double) ticks;
    }
    for (int i = 0; i <= endTick - startTick; i++) {

      this.colors.put(startTick + i, new Color((int)Math.round(startColor.getR() + r * i),
              (int)Math.round(startColor.getG() + g * i),
              (int)Math.round(startColor.getB() + b * i)));
      this.positions.put(startTick + i, new Position((int)Math.round(startPos.getX() + x * i),
              (int)Math.round(startPos.getY() + y * i)));
      this.widths.put(startTick + i, (int)Math.round(startWidth + w * i));
      this.heights.put(startTick + i, (int)Math.round(startHeight + h * i));
      this.rotations.put(startTick + i, (int)Math.round(startRot + rot * i));
    }
  }

  public Position getStartPos() {
    return this.startPos;
  }

  public Position getEndPos() {
    return this.endPos;
  }

  public Color getStartColor() {
    return this.startColor;
  }

  public Color getEndColor() {
    return endColor;
  }

  public int getStartWidth() {
    return this.startWidth;
  }

  public int getEndWidth() {
    return this.endWidth;
  }

  public int getStartTick() {
    return this.startTick;
  }

  public int getStartHeight() {
    return this.startHeight;
  }

  public int getEndHeight() {
    return this.endHeight;
  }

  @Override
  public String toString() {
    return "Tick " + this.getStartTick() + " to tick " + this.getEndTick() + ": "
            + this.startPos.toString() + " to " + this.endPos.toString() + ", "
            + "width " + this.startWidth + " to " + this.endWidth + ", height " + this.startHeight
            + " to " + this.endHeight + ", " + this.startColor.toString() + " to "
            + this.endColor.toString() + ".";
  }

  /**
   * Returns the last tick at which this shape is visible.
   * @return the last tick where this shape is visible.
   */
  public int getEndTick() {
    return this.endTick;
  }

  /**
   * Determines whether this action is occurring at the given tick.
   * @param tick the tick being checked
   * @return whether this action is occurring at the given tick.
   */
  public boolean occursAt(int tick) {
    return tick >= this.startTick && tick <= this.endTick;
  }

  /**
   * Determines whether the action has teleported or not.
   * @param o action
   * @return true or false
   */
  public boolean isTeleportedFrom(Action o) {
    return this.startTick != o.endTick
            || !this.startColor.equals(o.getEndColor())
            || this.startWidth != o.getEndWidth()
            || this.startHeight != o.getEndHeight()
            || !this.startPos.equals(o.getEndPos())
            || this.startRot != o.getEndRotation();
  }

  public Keyframe getStartKeyframe() {
    return new Keyframe(startColor, startPos, startWidth, startHeight, startRot, startTick);
  }

  public Keyframe getEndKeyframe() {
    return new Keyframe(endColor, endPos, endWidth, endHeight, endRot, endTick);
  }

  public int getStartRotation() {
    return this.startRot;
  }

  public int getEndRotation() {
    return this.endRot;
  }
}
