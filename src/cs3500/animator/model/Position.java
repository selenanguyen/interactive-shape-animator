package cs3500.animator.model;

/**
 * A class representing the position data of a shape.
 */
public final class Position {

  private final int x;
  private final int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Position)) {
      return false;
    }
    Position p = (Position) that;
    return this.x == p.getX()
            && this.y == p.getY();
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.x + this.y);
  }

  @Override
  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }

}
