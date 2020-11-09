package cs3500.animator.adapter;

/**
 * Represents the type of shape: Oval or rectangle.
 */
public enum ShapeType {
  Oval("oval"),
  Rec("rectangle");

  // the name representation of the shape.
  private final String name;

  // initializes the enum
  ShapeType(String n) {
    this.name = n;
  }

  @Override
  public String toString() {
    return this.name;
  }
}

