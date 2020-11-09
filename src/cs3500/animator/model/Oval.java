package cs3500.animator.model;

import cs3500.animator.view.TextualView;

/**
 * A class representing an oval shape.
 */
public class Oval extends AbstractShape {

  public Oval(String id) {
    super(id);
  }

  public Oval(String id, int height, int width, Color color, Position p) {
    super(id, height, width, color, p);
  }

  @Override
  public Shape cloneShape() {
    if (!this.isVisible) {
      return new Oval(id);
    }
    return new Oval(id, height, width, color, position);
  }

  @Override
  public String getShapeAsString() {
    return "Oval";
  }

  @Override
  public String getShapeAsString(TextualView view) {
    return view.getOvalAsString();
  }

  @Override
  public Shape makeCopy() {
    Shape copy;
    if (isVisible) {
      copy = new Oval(this.getId(), this.getHeight(), this.getWidth(),
              this.getColor(), this.getPosition());
    }
    else {
      copy = new Oval(this.getId());
    }
    return copy;
  }

  @Override
  public boolean isOval() {
    return true;
  }

}
