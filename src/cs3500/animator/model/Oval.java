package cs3500.animator.model;

import cs3500.animator.view.TextualView;

/**
 * A class representing an oval shape.
 */
public class Oval extends AbstractShape {

  public Oval(String id, int layer) {
    super(id, layer);
  }

  public Oval(String id, int height, int width, Color color, Position p, int rot, int layer) {
    super(id, height, width, color, p, rot, layer);
  }

  @Override
  public Shape cloneShape() {
    if (!this.isVisible) {
      return new Oval(id, layer);
    }
    return new Oval(id, height, width, color, position, rotation, layer);
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
  public boolean isOval() {
    return true;
  }

}
