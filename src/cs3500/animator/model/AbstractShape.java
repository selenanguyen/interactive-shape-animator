package cs3500.animator.model;

import cs3500.animator.controller.Keyframe;

/**
 * An abstract shape object that represents different shapes in the animation.
 * Contains data on the physical features and location of the shape.
 */
public abstract class AbstractShape implements Shape {
  protected final String id;
  protected int height;
  protected int width;
  protected Color color;
  protected Position position;
  protected boolean isVisible;

  protected AbstractShape(String id) {
    this.id = id;
    this.makeInvisible();
  }

  protected AbstractShape(String id, int height, int width, Color color, Position position) {
    if (id == null || height < 0 || width < 0 || color == null || position == null) {
      throw new IllegalArgumentException("All shape fields must not be initialized "
              + "to null or negative");
    }

    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Shapes can't have negative or zero dimensions");
    }
    this.id = id;
    this.height = height;
    this.width = width;
    this.color = color;
    this.isVisible = true;
    this.position = position;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  protected void setHeight(int h) {
    this.height = h;
  }

  protected void setWidth(int w) {
    this.width = w;
  }

  protected void setColor(Color c) {
    this.color = c;
  }

  @Override
  public void makeInvisible() {
    this.height = -1;
    this.width = -1;
    this.color = null;
    this.position = null;
    this.isVisible = false;
  }

  @Override
  public void applyKeyframe(Keyframe kf) {
    this.height = kf.getHeight();
    this.width = kf.getWidth();
    this.color = kf.getColor();
    this.position = kf.getPosition();
    if (kf.getWidth() == -1) {
      this.isVisible = false;
    }
    else {
      this.isVisible = true;
    }
  }

  @Override
  public boolean isInvisible() {
    return !this.isVisible;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public String toString() {
    if (!isVisible) {
      return "Invisible " + this.getShapeAsString() + " \'" + this.id + "\'";
    }
    return this.getShapeAsString() + " \'" + this.id + "\': width = "
            + this.width + ", height = " + this.height
            + ", color = " + this.color.toString() + ", position = " + this.position.toString()
            + ", visible: " + this.isVisible;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof AbstractShape)) {
      return false;
    }
    AbstractShape s = (AbstractShape) that;
    if (!this.isVisible) {
      return this.id.equals(s.id) && !s.isVisible;
    }
    return s.isVisible && this.position.equals(s.position)
            && this.id.equals(s.id) && this.color.equals(s.color)
            && this.width == s.width
            && this.height == s.height
            && this.getShapeAsString().equals(s.getShapeAsString());
  }

  protected void setPosition(Position p) {
    this.position = p;
  }

  @Override
  public int hashCode() {
    if (!this.isVisible) {
      return this.id.hashCode();
    }
    return Integer.hashCode(this.height + this.width)
            + id.hashCode() + getShapeAsString().hashCode()
            + this.position.hashCode();
  }

  @Override
  public boolean isOval() {
    return false;
  }

  @Override
  public boolean isRectangle() {
    return false;
  }

  @Override
  public void setState(int height, int width, Color color, Position position) {
    this.setPosition(position);
    this.setWidth(width);
    this.setColor(color);
    this.setHeight(height);
    this.isVisible = true;
  }
}
