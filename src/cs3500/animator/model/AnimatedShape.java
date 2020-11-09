package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.view.TextualView;

/**
 * An animation shape to be seen in the animation.
 * Contains the shape object and a list of animation to be executed in order.
 */
public class AnimatedShape implements IAnimatedShape {
  private final Shape shape;
  private final List<Action> actions = new ArrayList<>();
  private final List<Keyframe> keyframes = new ArrayList<>();

  /**
   * Constructor of the animated shape consisting of a shape object and a
   * list of actions executed in the animation.
   * @param s the shape object
   * @param actions the list of actions to be executed
   */
  public AnimatedShape(Shape s, List<Action> actions, boolean copyByActions) {
    this.shape = s.cloneShape();
    actions.stream().forEach(action -> {
      if (!this.actions.isEmpty() && action.isTeleportedFrom(this.getLastAction())) {
        throw new IllegalArgumentException("Invalid action: teleportation.");
      }
      this.actions.add(action);
    });
    if (actions.isEmpty()) {
      return;
    }
    for (Action a : actions) {
      this.keyframes.add(a.getEndKeyframe());
    }
  }

  /**
   * Another constructor of the animated shape from
   * a list of keyframes executed in the animation.
   * @param s the shape object
   * @param keyframes the list of keyframes
   */
  public AnimatedShape(Shape s, List<Keyframe> keyframes) {
    this.shape = s.cloneShape();
    Keyframe prev = null;

    for (Keyframe kf : keyframes) {
      this.keyframes.add(kf);
      if (keyframes.indexOf(kf) > 0) {
        this.actions.add(new Action(prev, kf));
        prev = kf;
      }
      else {
        prev = keyframes.get(0);
      }
    }
    if (actions.isEmpty()) {
      return;
    }
  }

  /**
   * Another constructor of the animated shape without a list of actions to be
   * executed in the animation. Used when a description just says to create the object
   * and no events are required yet.
   * @param s the shape to use in the animation
   */
  public AnimatedShape(Shape s) {
    this.shape = s.cloneShape();
  }

  @Override
  public Shape getShape() {
    return this.shape.cloneShape();
  }

  @Override
  public String getId() {
    return this.shape.getId();
  }

  @Override
  public List<Action> getActions() {
    return this.actions;
  }

  @Override
  public void addAction(Action action) {
    // check if action is valid before adding or throw IAE
    if (action == null) {
      throw new IllegalArgumentException("Action can't be null");
    }
    if (!this.actions.isEmpty() && action.isTeleportedFrom(this.getLastAction())) {
      throw new IllegalArgumentException("Action is invalid: teleportation");
    }
    this.actions.add(action);
    if (this.keyframes.isEmpty()) {
      this.keyframes.add(action.getStartKeyframe());
    }
    this.keyframes.add(action.getEndKeyframe());
  }

  /**
   * Returns the last action in this shape's action list.
   * @return the last action in this shape's action list
   */
  private Action getLastAction() {
    if (this.actions.isEmpty()) {
      throw new IllegalArgumentException("No actions exist.");
    }
    return this.actions.get(this.actions.size() - 1);
  }

  @Override
  public void applyTick(int tick) {
    try {
      this.getActionAt(tick).applyTick(this.shape, tick);
    }
    catch (IllegalArgumentException e) {
      this.shape.makeInvisible();
    }
  }

  /**
   * Returns the action that occurs at the given tick.
   * @param tick the tick
   * @return the action
   * @throws IllegalArgumentException if no action occurs at the tick
   */
  private Action getActionAt(int tick) {
    for (Action action : this.actions) {
      if (action.occursAt(tick)) {
        return action;
      }
    }
    throw new IllegalArgumentException("No action occurs at tick " + tick);
  }

  @Override
  public void editKeyframe(Keyframe kf) {
    System.out.println(this.actions);
    for (int i = 0; i < this.keyframes.size(); i++) {
      if (keyframes.get(i).getTick() == kf.getTick()) {
        this.keyframes.remove(i);
        this.keyframes.add(i, kf);
      }
    }
    for (int i = 0; i < this.actions.size(); i++) {
      Action a = this.actions.get(i);
      if (a.getEndKeyframe().getTick() == kf.getTick()) {
        this.actions.remove(i);
        this.actions.add(i, new Action(a.getStartKeyframe(), kf));
      }
      if (a.getStartKeyframe().getTick() == kf.getTick()) {
        Action a1 = this.actions.get(i);
        this.actions.remove(i);
        this.actions.add(i, new Action(kf, a1.getEndKeyframe()));
      }
    }
    System.out.println(this.actions);
  }

  /**
   * Returns whether a keyframe exists at the given tick.
   * @param tick the tick
   * @return whether a keyframe exists at the tick
   */
  private boolean doesKeyframeExistAt(int tick) {
    for (Keyframe kf : this.keyframes) {
      if (kf.getTick() == tick) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void setLayer(int l) {
    this.shape.setLayer(l);
  }

  /**
   * Returns the keyframe that occurs at the given tick.
   * @param tick the tick
   * @return the keyframe
   * @throws IllegalArgumentException if no keyframe exists at that tick
   */
  private Keyframe getKeyframe(int tick) {
    for (Keyframe kf : this.keyframes) {
      if (kf.getTick() == tick) {
        return kf;
      }
    }
    throw new IllegalArgumentException("No keyframe exists at tick " + tick);
  }

  @Override
  public Keyframe removeKeyframe(int tick) {
    Keyframe kf = getKeyframe(tick);
    keyframes.remove(kf);
    if (keyframes.isEmpty()) {
      return kf;
    }
    Action a1 = getActionAt(tick);
    int index = actions.indexOf(a1);
    actions.remove(a1);
    if (actions.size() > index + 1 && kf.getTick() == a1.getEndTick()) {
      Action a2 = actions.get(index + 1);
      actions.remove(a2);
      actions.add(index, new Action(a1.getStartKeyframe(), a2.getEndKeyframe()));
    }
    return kf;
  }


  @Override
  public void addKeyframe(Keyframe kf) {
    if (doesKeyframeExistAt(kf.getTick())) {
      throw new IllegalArgumentException("A keyframe already exists at tick " + kf.getTick());
    }
    // Adding the first keyframe doesnot require adding an action
    if (this.keyframes.isEmpty()) {
      this.keyframes.add(kf);
      return;
    }
    // If shape currently has 1 keyframe, adding this keyframe will create the first action
    if (this.keyframes.size() == 1) {
      if (kf.getTick() > keyframes.get(0).getTick()) {
        keyframes.add(kf);
      }
      else {
        keyframes.add(0, kf);
      }
      actions.add(new Action(keyframes.get(0), keyframes.get(1)));
      return;
    }
    // Adding a keyframe to the beginning
    else if (kf.getTick() < this.getStartTick()) {
      this.keyframes.add(0, kf);
      this.actions.add(0, new Action(kf, this.actions.get(0).getStartKeyframe()));
      return;
    }
    // Adding a keyframe to the end
    else if (kf.getTick() > this.getEndTick()) {
      this.keyframes.add(kf);
      this.actions.add(new Action(getLastAction().getEndKeyframe(), kf));
      return;
    }
    // Otherwise, we add a keyframe and two actions
    int kfIndex = 0;
    for (int i = 0; i < keyframes.size(); i++) {
      if (keyframes.get(i).getTick() > kf.getTick()) {
        kfIndex = i;
        break;
      }
    }
    this.keyframes.add(kfIndex, kf);
    Action a = getActionAt(kf.getTick());
    int actionIndex = this.actions.indexOf(a);
    this.actions.remove(a);
    this.actions.add(actionIndex, new Action(a.getStartKeyframe(), kf));
    this.actions.add(actionIndex + 1, new Action(kf, a.getEndKeyframe()));
  }

  /**
   * Returns the first tick of this shape.
   * @return the first tick
   */
  private int getStartTick() {
    if (this.actions.isEmpty()) {
      return -1;
    }
    return this.actions.get(0).getStartTick();
  }

  /**
   * Returns the last tick of this shape.
   * @return the last tick
   */
  private int getEndTick() {
    if (this.actions.isEmpty()) {
      return -1;
    }
    return this.getLastAction().getEndTick();
  }

  /**
   * Returns whether this shape exists at the given tick.
   * @param tick the tick
   * @return whether this shape exists at the given tick
   */
  private boolean existsAtTick(int tick) {
    for (Action a : this.actions) {
      if (a.occursAt(tick)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Shape getShapeAt(int tick) {
    Shape copy = this.shape.cloneShape();
    try {
      for (Keyframe kf : this.keyframes) {
        if (kf.getTick() == tick) {
          Shape clone = shape.cloneShape();
          clone.applyKeyframe(kf);
          return clone;
        }
      }
      return this.getActionAt(tick).getShapeAt(this.shape, tick);
    }
    catch (IllegalArgumentException e) {
      copy.makeInvisible();
      return copy;
    }
  }

  @Override
  public IAnimatedShape makeCopy() {
    IAnimatedShape copy = new AnimatedShape(this.shape.cloneShape(), this.keyframes);
    return copy;
  }

  /**
   * Returns the string representation of the position change of the given action.
   * @param a the action to be represented
   * @return the string representation of the position change
   */
  private String getPositionChangeString(Action a) {
    if (a.getStartPos().equals(a.getEndPos())) {
      return "stays put at " + a.getStartPos().toString();
    }
    return "moves from " + a.getStartPos().toString() + " to " + a.getEndPos().toString();
  }

  /**
   * Returns the string representation of the size change of the given action.
   * @param a the action to be represented
   * @return the string representation of the size change
   */
  private String getSizeChangeString(Action a) {
    if (a.getStartWidth() == a.getEndWidth()
            && a.getStartHeight() == a.getEndHeight()) {
      return "stays size " + this.getSizeAsString(true, a);
    }
    if (a.getStartWidth() * a.getStartHeight() < a.getEndHeight() * a.getEndWidth()) {
      return "grows from " + this.getSizeAsString(true, a)
              + " to " + this.getSizeAsString(false, a);
    }
    if (a.getStartWidth() * a.getStartHeight() > a.getEndHeight() * a.getEndWidth()) {
      return "shrinks from " + this.getSizeAsString(true, a)
              + " to " + this.getSizeAsString(false, a);
    }
    return "changes from size " + this.getSizeAsString(true, a)
            + " to " + this.getSizeAsString(false, a);
  }

  /**
   * Returns the string representation of the given size from the given action.
   * @param isStartSize if the start size is the size to be represented (otherwise the end size)
   * @param a the action whose size is to be represented
   * @return the string representation of the given size
   */
  private String getSizeAsString(boolean isStartSize, Action a) {
    if (isStartSize) {
      return a.getStartWidth() + "x" + a.getStartHeight();
    }
    else {
      return a.getEndWidth() + "x" + a.getEndHeight();
    }
  }

  /**
   * Returns the string representation of the color change of the given action.
   * @param a the action to be represented
   * @return the string representation of the color change
   */
  private String getColorChangeString(Action a) {
    if (a.getStartColor().equals(a.getEndColor())) {
      return "stays " + a.getStartColor().toString();
    }
    return "turns " + a.getEndColor().toString();
  }

  /**
   * Returns the string representation of the tick change of the given action.
   * @param a the action to be represented
   * @return the string representation of the tick change
   */
  private String getTickChange(Action a) {
    return "From time " + a.getStartTick() + " to " + a.getEndTick();
  }

  @Override
  public String toString() {
    List<String> actionsAsString = new ArrayList<>(this.actions.size());
    for (Action a : this.actions) {
      actionsAsString.add(getTickChange(a) + ", " + this.shape.getId() + " "
              + this.getPositionChangeString(a) + ", " + this.getSizeChangeString(a) + ", and "
              + this.getColorChangeString(a) + ".");
    }
    return String.join("\n", actionsAsString);
  }

  @Override
  public String getShapeAsString() {
    return this.shape.getShapeAsString();
  }

  @Override
  public String getShapeAsString(TextualView view) {
    return this.shape.getShapeAsString(view);
  }

  @Override
  public Shape getShapeAtStart() {
    if (actions.isEmpty()) {
      Shape copy = this.shape.cloneShape();
      copy.makeInvisible();
      return copy;
    }
    int tickOfFirstAction = actions.get(0).getStartTick();
    return getShapeAt(tickOfFirstAction).cloneShape();
  }

  @Override
  public List<Action> getActionsAppliedBefore(int currentTick) {
    List<Action> copy = new ArrayList<>();
    for (Action a : this.actions) {
      if ((a.getStartTick() < currentTick)
              || (a.getStartTick() == currentTick && a.getEndTick() == currentTick)) {
        copy.add(a);
      }
    }
    return copy;
  }

  @Override
  public Action removeAction(int tick) {
    List<Action> copy = new ArrayList<Action>(this.actions);
    for (Action a : this.actions) {
      if (a.occursAt(tick)) {
        copy.remove(a);
        if (!isValid(copy)) {
          throw new IllegalArgumentException("Action removal causes teleportation");
        }
        this.actions.remove(a);
        this.keyframes.remove(a.getStartKeyframe());
        this.keyframes.remove(a.getEndKeyframe());
        return a;
      }
    }
    throw new IllegalArgumentException("No action exists at tick " + Integer.toString(tick));
  }

  //  @Override
  //  public Action removeAction() {
  //    if (this.actions.isEmpty()) {
  //      throw new IllegalArgumentException("No actions to remove");
  //    }
  //    Action a = this.actions.get(this.actions.size() - 1);
  //    this.actions.remove(a);
  //    keyframes.remove(a.getStartKeyframe());
  //    keyframes.remove(a.getEndKeyframe());
  //    return a;
  //  }

  @Override
  public int getLastTick() {
    if (this.actions.isEmpty()) {
      return 1;
    }
    return this.actions.get(this.actions.size() - 1).getEndTick();
  }

  /**
   * Returns whether the given list of actions is valid (has no teleportation).
   * @param actions the list of actions
   * @return whether the list of actions is valid
   */
  private boolean isValid(List<Action> actions) {
    for (int i = 0; i < actions.size(); i++) {
      if (i == 0) {
        continue;
      }
      if (actions.get(i).isTeleportedFrom(actions.get(i - 1))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<Keyframe> getKeyframes() {
    return this.keyframes;
  }

  @Override
  public int getFirstTick() {
    if (this.keyframes.isEmpty()) {
      throw new IllegalArgumentException("No keyframes exist");
    }
    return this.keyframes.get(0).getTick();
  }

  @Override
  public int getLayer() {
    return this.shape.getLayer();
  }
}
