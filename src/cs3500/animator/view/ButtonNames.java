package cs3500.animator.view;

/**
 * Enums for all buttons names used in the interactive animation view.
 */
public enum ButtonNames {
  PLAY("Play"),
  PAUSE("Pause"),
  RESTART("Restart"),
  ENABLE_LOOPING("Enable Looping"),
  DISABLE_LOOPING("Disable Looping"),
  SET_SPEED("Set Speed (ticks/second)"),
  ADD_OVAL("Add Oval"),
  ADD_RECT("Add Rectangle"),
  REMOVE_SHAPE("Remove Shape"),
  EDIT_SHAPE("Edit keyframes"),
  EDIT_KEYFRAME_OPTION("Edit keyframe"),
  SELECT_KEYFRAME_TO_EDIT("Edit"),
  SELECT_KEYFRAME_TO_REMOVE("Remove"),
  SAVE_KEYFRAME("Save keyframe"),
  ADD_KEYFRAME_OPTION("Add keyframe"),
  SELECT_TICK_TO_ADD_KEYFRAME("Select tick"),
  REMOVE_KEYFRAME_OPTION("Remove keyframe"),
  ADD_KEYFRAME("Add"),
  REMOVE_KEYFRAME("Remove"),
  SAVE("Save"),
  SWITCH_LAYERS("Switch Layers"),
  REMOVE_LAYER("Remove Layer"),
  LOAD("Load File");

  private final String name;

  ButtonNames(String n) {
    this.name = n;
  }

  public String toString() {
    return this.name;
  }
}

