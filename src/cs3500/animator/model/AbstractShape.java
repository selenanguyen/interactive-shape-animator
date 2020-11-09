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
  protected int rotation;
  protected int layer;

  protected AbstractShape(String id, int layer) {
    this.id = id;
    this.layer = layer;
    this.makeInvisible();
  }

  protected AbstractShape(String id, int height, int width, Color color, Position position,
                          int rot, int layer) {
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
    this.rotation = rot;
    this.layer = layer;
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
  public int getLayer() {
    return this.layer;
  }

  @Override
  public void makeInvisible() {
    this.height = -1;
    this.width = -1;
    this.rotation = 0;
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
    return Integer.hashCode(this.height + this.width + this.rotation)
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
  public void setRotation(int rot) {
    this.rotation = rot;
  }

  @Override
  public int getRotation() {
    return this.rotation;
  }

  @Override
  public void setState(int height, int width, Color color, int rotation, Position position) {
    this.setPosition(position);
    this.setWidth(width);
    this.setColor(color);
    this.setHeight(height);
    this.setRotation(rotation);
    this.isVisible = true;
  }

  @Override
  public void setLayer(int l) {
    this.layer = l;
  }
}
