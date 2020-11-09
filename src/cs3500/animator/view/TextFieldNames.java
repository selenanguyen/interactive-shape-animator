package cs3500.animator.view;

/**
 * Enums representing the possible text fields names in the
 * interactive animation view.
 */
public enum TextFieldNames {

  SET_SPEED("Speed (ticks/second): "),

  NAME_ADD_SHAPE("Shape Name: "),

  TICK_ADD_KEYFRAME("Tick: "),
  POSITION_ADD_KEYFRAME("Position (X,Y): "),
  WIDTH_ADD_KEYFRAME("Width: "),
  HEIGHT_ADD_KEYFRAME("Height: "),
  COLOR_ADD_KEYFRAME("Color (R,G,B): "),

  LOAD_FILE("Load file:"),
  SAVE_FILE("Ssave to:");


  private final String name;

  TextFieldNames(String n) {
    this.name = n;
  }

  public String toString() {
    return this.name;
  }
}
