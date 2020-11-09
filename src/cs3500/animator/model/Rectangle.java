package cs3500.animator.model;

import cs3500.animator.view.TextualView;

/**
 * A class representing a Rectangle Shape.
 */
public class Rectangle extends AbstractShape {

  public Rectangle(String id, int layer) {
    super(id, layer);
  }

  public Rectangle(String id, int height, int width, Color color, Position p,
                   int rot, int layer) {
    super(id, height, width, color, p, rot, layer);
  }

  @Override
  public Shape cloneShape() {
    if (!this.isVisible) {
      return new Rectangle(id, layer);
    }
    return new Rectangle(id, height, width, color, position, rotation, layer);
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
  public boolean isRectangle() {
    return true;
  }
}
