package cs3500.animator.model;

import cs3500.animator.view.TextualView;

/**
 * A class representing a Rectangle Shape.
 */
public class Rectangle extends AbstractShape {

  protected String shapeAsString = "Rectangle";

  public Rectangle(String id) {
    super(id);
  }

  public Rectangle(String id, int height, int width, Color color, Position p) {
    super(id, height, width, color, p);
  }

  @Override
  public Shape cloneShape() {
    if (!this.isVisible) {
      return new Rectangle(id);
    }
    return new Rectangle(id, height, width, color, position);
  }

  @Override
  public String getShapeAsString() {
    return "Rectangle";
  }

  @Override
  public String getShapeAsString(TextualView view) {
    return view.getRectangleAsString();
  }

  @Override
  public Shape makeCopy() {
    Shape copy;
    if (isVisible) {
      copy = new Rectangle(this.getId(), this.getHeight(), this.getWidth(),
              this.getColor(), this.getPosition());
    }
    else {
      copy = new Rectangle(this.getId());
    }
    return copy;
  }

  @Override
  public boolean isRectangle() {
    return true;
  }
}
