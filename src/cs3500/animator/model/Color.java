package cs3500.animator.model;

/**
 * The class representing color of a shape in a RGB scale.
 */
public final class Color {
  private final int r;
  private final int g;
  private final int b;

  /**
   * The color constructor on an RGB scale.
   *
   * @param r red value
   * @param g green value
   * @param b blue value
   */
  public Color(int r, int g, int b) {
    if (r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("Color values cant be greater than 255");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public int getR() {
    return this.r;
  }

  public int getG() {
    return this.g;
  }

  public int getB() {
    return this.b;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Color)) {
      return false;
    }
    Color c = (Color) that;
    return c.getG() == this.getG() && c.getR() == this.getR() && c.getB() == this.getB();
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.g + this.b + this.r);
  }

  @Override
  public String toString() {
    return "rgb(" + this.getR() + "," + this.getG() + "," + this.getB() + ")";
  }
}
