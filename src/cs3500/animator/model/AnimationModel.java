package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.util.AnimationBuilder;


/**
 * An animation model that is the implementation of animation operations.
 * Contains a list of animated shapes that hold their own information and action list.
 */
public class AnimationModel implements AnimationOperations {

  private static int width = 500;
  private static int height = 500;
  private static int x = 200;
  private static int y = 200;
  private int tick = 1;

  /**
   * The list of shapes that appear in this model.
   */
  private final List<IAnimatedShape> shapes;

  /**
   * Constructor of the animation model.
   * The list of shapes in the animation model is initially empty.
   */
  public AnimationModel() {
    shapes = new ArrayList<>();
  }

  /**
   *Constructs a model with the given canvas width, canvas height, and x and y offset.
   * @param width the canvas width
   * @param height the canvas height
   * @param x x-offset
   * @param y y-offset
   */
  public AnimationModel(int width, int height, int x, int y) {
    shapes = new ArrayList<>();
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }

  /**
   * A private constructor that takes in the width, height, x, y, and a list of animated shapes.
   * @param width the canvas width
   * @param height the canvas height
   * @param x the x offset
   * @param y the y offset
   * @param shapes the animated shapes
   */
  private AnimationModel(int width, int height, int x, int y, List<IAnimatedShape> shapes) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.shapes = shapes;
    this.sortByLayer();
  }

  /**
   * A builder for a an animation model.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {
    private int width = AnimationModel.width;
    private int height = AnimationModel.height;
    private int x = AnimationModel.x;
    private int y = AnimationModel.y;
    List<IAnimatedShape> shapes = new ArrayList<IAnimatedShape>();

    @Override
    public AnimationModel build() {
      return new AnimationModel(width, height, x, y, shapes);
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      if (width < 0 || height < 0) {
        throw new IllegalArgumentException("Can't have negative width or height.");
      }
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type, int layer) {
      Shape s = type.equals("ellipse") ? new Oval(name, layer) : new Rectangle(name, layer);
      shapes.add(new AnimatedShape(s));
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1,
                                                           int w1, int h1, int r1, int g1, int b1,
                                                           int t2, int x2, int y2, int w2, int h2,
                                                           int r2, int g2, int b2) {
      return addMotionHelp(name, new Action(new Position(x1, y1), new Position(x2, y2), w1, w2, h1,
              h2, new Color(r1, g1, b1), new Color(r2, g2, b2), t1, t2));
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1,
                                                      int w1, int h1, int r1, int g1, int b1,
                                                      int rot1,
                                                      int t2, int x2, int y2, int w2, int h2,
                                                      int r2, int g2, int b2, int rot2) {
      return addMotionHelp(name, new Action(new Position(x1, y1), new Position(x2, y2), w1, w2, h1,
              h2, new Color(r1, g1, b1), new Color(r2, g2, b2), rot1, rot2, t1, t2));
    }

    private AnimationBuilder<AnimationModel> addMotionHelp(String name, Action a) {
      if (a.getStartTick() == a.getEndTick() && !a.isTeleportedFrom(a)) {
        return this;
      }
      for (IAnimatedShape s : this.shapes) {
        if (s.getId().equals(name)) {
          s.addAction(a);
        }
      }
      return this;
    }

    private IAnimatedShape getShape(String name) {
      for (IAnimatedShape s : this.shapes) {
        if (s.getId().equals(name)) {
          return s;
        }
      }
      throw new IllegalArgumentException("No such shape exists");
    }

    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x,
                                                        int y, int w, int h, int r, int g, int b) {
      getShape(name).addKeyframe(new Keyframe(new Color(r, g, b), new Position(x, y), w, h, t));
      return this;
    }
  }

  @Override
  public List<Shape> getShapes() {
    List<Shape> shapes = new ArrayList<Shape>();
    for (IAnimatedShape s : this.shapes) {
      shapes.add(s.getShape().cloneShape());
    }
    return shapes;
  }

  @Override
  public void addAction(String id, Action action) {
    Objects.requireNonNull(action);
    if (id == null) {
      throw new IllegalArgumentException("Invalid ID: null ID");
    }
    for (IAnimatedShape s : this.shapes) {
      if (id == s.getId()) {
        s.addAction(action);
      }
    }
  }

  @Override
  public void addShape(Shape shape) {
    validateAddShape(shape);
    this.shapes.add(new AnimatedShape(shape));
    this.sortByLayer();
  }

  @Override
  public Shape removeShape(String id) {
    for (int i = 0; i < this.shapes.size(); i++) {
      IAnimatedShape shape = this.shapes.get(i);
      if (shape.getId().equals(id)) {
        this.shapes.remove(i);
        return shape.getShape();
      }
    }
    throw new IllegalArgumentException("No shape exists with id=\"" + id + "\"");
  }

  @Override
  public Action removeAction(String id, int tick) {
    return getTrueAnimatedShape(id).removeAction(tick);
  }

  /**
   * Ensures that the given shape does not already exist within the model.
   * @param shape the shape being attempted to be added
   */
  private void validateAddShape(Shape shape) {
    Objects.requireNonNull(shape);
    for (IAnimatedShape s : this.shapes) {
      if (s.getId().equals(shape.getId())) {
        throw new IllegalArgumentException("Invalid ID: ID already exists.");
      }
    }
  }

  @Override
  public Shape getShape(String id) {
    for (IAnimatedShape shape : this.shapes) {
      if (shape.getId().equals(id)) {
        return shape.getShape();
      }
    }
    throw new IllegalArgumentException("Shape does not exist.");
  }

  @Override
  public void applyTick(int tick) {
    this.tick = tick;
    for (IAnimatedShape shape : this.shapes) {
      shape.applyTick(tick);
    }
  }

  @Override
  public void editKeyframe(String id, Keyframe kf) {
    this.getTrueAnimatedShape(id).editKeyframe(kf);
  }

  @Override
  public void addKeyFrame(String id, Keyframe kf) {
    this.getTrueAnimatedShape(id).addKeyframe(kf);
  }

  @Override
  public List<Action> getMotionsAppliedToShape(String id, int currentTick) {
    List<Action> actionsApplied = new ArrayList<>();

    for (IAnimatedShape shape : this.shapes) {
      if (shape.getId().equals(id)) {
        actionsApplied = shape.getActionsAppliedBefore(currentTick);
      }
    }
    return actionsApplied;
  }

  @Override
  public List<Shape> getShapesAt(int tick) {
    List<Shape> shapes = new ArrayList<Shape>();
    for (IAnimatedShape shape : this.shapes) {
      shapes.add(shape.getShapeAt(tick));
    }
    return shapes;
  }

  @Override
  public Shape getShapeAt(String id, int tick) {
    for (IAnimatedShape s : this.shapes) {
      if (s.getId().equals(id)) {
        return s.getShapeAt(tick);
      }
    }
    throw new IllegalArgumentException("No such shape exists");
  }

  @Override
  public int getCanvasHeight() {
    return this.height;
  }

  @Override
  public int getCanvasWidth() {
    return this.width;
  }

  @Override
  public int getCanvasStartingX() {
    return this.x;
  }

  @Override
  public int getCanvasStartingY() {
    return this.y;
  }

  @Override
  public String toString() {
    List<String> createShapeStrings = new ArrayList<>();
    List<String> actionStrings = new ArrayList<>();
    for (IAnimatedShape s : this.shapes) {
      createShapeStrings.add("Create " + s.getShape().getShapeAsString().toLowerCase()
              + " named \"" + s.getShape().getId() + "\"");
      actionStrings.add(s.toString());
    }
    String actions = String.join("\n\n", actionStrings);
    String creations = String.join("\n", createShapeStrings);
    return String.join("\n\n", new ArrayList<>(Arrays.asList(creations, actions)));
  }

  @Override
  public List<IAnimatedShape> getAnimatedShapes() {
    List<IAnimatedShape> copy = new ArrayList<IAnimatedShape>();
    for (IAnimatedShape s : this.shapes) {
      copy.add(s.makeCopy());
    }
    return copy;
  }

  @Override
  public IAnimatedShape getAnimatedShape(String id) {
    for (IAnimatedShape s : this.shapes) {
      if (s.getId().equals(id)) {
        return s.makeCopy();
      }
    }
    throw new IllegalArgumentException("No shape exists with id=\"" + id + "\"");
  }

  @Override
  public int getLastTick() {
    int tick = 1;
    for (IAnimatedShape s : this.shapes) {
      tick = Math.max(s.getLastTick(), tick);
    }
    return tick;
  }

  /**
   * Gets the animated shape with the given id (not a copy).
   * @param id the shape id
   * @return the animated shape (not a copy)
   */
  private IAnimatedShape getTrueAnimatedShape(String id) {
    for (IAnimatedShape s : this.shapes) {
      if (s.getId().equals(id)) {
        return s;
      }
    }
    throw new IllegalArgumentException("No shape with id " + id + " exists");
  }

  @Override
  public Keyframe removeKeyframe(String id, int tick) {
    return this.getTrueAnimatedShape(id).removeKeyframe(tick);
  }

  @Override
  public void clear() {
    this.shapes.removeAll(shapes);
  }

  @Override
  public void addAnimatedShape(IAnimatedShape animatedShape) {
    if (animatedShape == null) {
      throw new IllegalArgumentException("Animated Shape cannot be null");
    }
    for (IAnimatedShape as : this.getAnimatedShapes()) {
      if (as.getId() == animatedShape.getId()) {
        throw new IllegalArgumentException("Can't add a duplicate shape");
      }
    }
    this.shapes.add(animatedShape);

  }

  @Override
  public List<Keyframe> getKeyframes(String id) {
    return this.getTrueAnimatedShape(id).getKeyframes();
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  /**
   * Sorts the list of animated shapes by layer.
   */
  private void sortByLayer() {
    this.shapes.sort((s1, s2) -> -1 * (Integer.compare(s1.getLayer(), s2.getLayer())));
  }

  @Override
  public void removeLayer(int l) {
    if (!this.shapes.removeIf((s) -> s.getLayer() == l)) {
      throw new IllegalArgumentException("Layer " + l + " does not exist");
    }
  }

  @Override
  public void switchLayers(int l1, int l2) {
    List<IAnimatedShape> l1s = new ArrayList<>();
    List<IAnimatedShape> l2s = new ArrayList<>();
    if (l1 == l2) {
      throw new IllegalArgumentException("Layer cannot be switched with itself");
    }
    for (IAnimatedShape s : this.shapes) {
      if (s.getLayer() == l1) {
        l1s.add(s);
      }
      else if (s.getLayer() == l2) {
        l2s.add(s);
      }
    }
    if (l1s.isEmpty() || l2s.isEmpty()) {
      throw new IllegalArgumentException("At least one of these layers does not exist.");
    }
    for (IAnimatedShape s : l1s) {
      s.setLayer(l2);
    }
    for (IAnimatedShape s : l2s) {
      s.setLayer(l1);
    }
    this.sortByLayer();
  }

}
