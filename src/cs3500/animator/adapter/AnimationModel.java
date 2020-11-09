package cs3500.animator.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.Color;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.Position;

/**
 * Our model adapter. Extends our original model and implements IModel.
 */
public class AnimationModel extends cs3500.animator.model.AnimationModel implements IModel {

  // Necessary to keep track of list of KeyFrames, since the view class takes the
  // list of keyframes directly and mutates it, leaving the model to keep
  // track of the mutated list.
  private final Map<String, List<KeyFrame>> map = new HashMap<>();

  /**
   * Uses the given model to initialize this model and the new map of shape id to keyframe.
   * @param model the model
   */
  public AnimationModel(AnimationOperations model) {
    super(model.getCanvasWidth(), model.getCanvasHeight(), model.getCanvasStartingX(),
            model.getCanvasStartingY());
    for (IAnimatedShape animatedShape : model.getAnimatedShapes()) {
      super.addShape(animatedShape.getShape());
      List<KeyFrame> newKfs = new ArrayList<>();
      for (Keyframe kf : animatedShape.getKeyframes()) {
        newKfs.add(new KeyFrame(animatedShape.getId(), kf));
        if (super.getKeyframes(animatedShape.getId()).size() == 1
                && animatedShape.getKeyframes().get(0).getTick() == kf.getTick()) {
          continue;
        }

        super.addKeyFrame(animatedShape.getId(), new Keyframe(kf.getColor(),
                kf.getPosition(), kf.getWidth(), kf.getHeight(), kf.getTick()));
      }
      map.put(animatedShape.getId(), newKfs);
    }
  }

  @Override
  public int getBoundHeight() {
    return super.getCanvasHeight();
  }

  @Override
  public int getBoundWidth() {
    return super.getCanvasWidth();
  }

  @Override
  public int getBoundX() {
    return super.getCanvasStartingX();
  }

  @Override
  public int getBoundY() {
    return super.getCanvasStartingY();
  }

  @Override
  public Map<IShape, List<KeyFrame>> getMap() {
    Map<IShape, List<KeyFrame>> map = new HashMap<>();
    for (IAnimatedShape shape : super.getAnimatedShapes()) {
      IShape newShape = shape.getShapeAsString().equals("Oval")
              ? new Oval(shape, this.map.get(shape.getId()))
              : new Rectangle(shape, this.map.get(shape.getId()));
      map.put(newShape, this.map.get(shape.getId()));
    }
    return map;
  }

  @Override
  public List<IShape> getShape() {
    List<IAnimatedShape> animatedShapes = super.getAnimatedShapes();
    List<IShape> shapes = new ArrayList<>();
    for (int i = 0; i < animatedShapes.size(); i++) {
      IShape shape = animatedShapes.get(i).getShapeAsString().equals("Oval")
              ? new Oval(animatedShapes.get(i), map.get(animatedShapes.get(i).getId()))
              : new Rectangle(animatedShapes.get(i), map.get(animatedShapes.get(i).getId()));
      shapes.add(shape);
    }
    return shapes;
  }

  @Override
  public int getTick() {
    return super.getTick();
  }

  @Override
  public void applyTick(int tick) {
    super.applyTick(tick);
  }

  @Override
  public void addKeyFrame(KeyFrame kf) {
    super.addKeyFrame(kf.getName(),
            new Keyframe(new Color(kf.getRed(), kf.getGreen(), kf.getBlue()),
                    new Position(kf.getX(), kf.getY()), kf.getWidth(),
                    kf.getHeight(), kf.getTime()));
    this.map.get(kf.getName()).add(kf);
  }

  @Override
  public void removeKeyFrame(KeyFrame key) {
    super.removeKeyframe(key.getName(), key.getTime());
    this.map.get(key.getName()).remove(key);
  }
}
