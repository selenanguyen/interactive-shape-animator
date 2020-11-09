package cs3500.animator.adapter;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.provider.view.IVisualView;
import cs3500.animator.provider.view.NewVisualVIew;
import cs3500.animator.view.IInteractiveView;

import java.awt.event.ActionEvent;

/**
 * Controller adapter.
 */
public class Controller implements IController {

  private IModel model;
  private boolean enableLooping = true;
  private IVisualView view;
  private int speed;
  // Delegate controller.
  private cs3500.animator.controller.IController controller;

  /**
   * Sets the model and the speed of controller.
   * setController() is to be called after initialization.
   * @param model the model
   * @param speed integer ticks per second
   */
  public Controller(IModel model, int speed) {
    this.model = model;
    this.speed = speed;
  }

  /**
   * Initializes the view and passes itself as the action listener.
   */
  public void setController() {
    InteractiveNewVisualView v = new InteractiveNewVisualView(model, this);
    IInteractiveView vv = (IInteractiveView) v;
    this.view = (IVisualView) vv;
    this.controller = new AnimationController(model, vv, speed, true);
    v.initializeSpeed();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    NewVisualVIew view = (NewVisualVIew) this.view;
    if (e == null || e.getActionCommand() == null) {
      throw new IllegalArgumentException("The action event or its action command "
              + "cannot be null.");
    }
    ActionEvent newE;
    switch (e.getActionCommand()) {
      case "resume":
      case "start":
        newE = new ActionEvent(e.getSource(), e.getID(), "Play");
        controller.actionPerformed(newE);
        break;
      case "pause":
        newE = new ActionEvent(e.getSource(), e.getID(), "Pause");
        controller.actionPerformed(newE);
        break;
      case "looping":
        String command = this.enableLooping ? "Disable Looping" : "Enable Looping";
        newE = new ActionEvent(e.getSource(), e.getID(), command);
        controller.actionPerformed(newE);
        this.enableLooping = !this.enableLooping;
        break;
      case "speedup":
        controller.setSpeed(Integer.toString((int) (controller.getSpeed() + 1)));
        view.setSpeed(controller.getSpeed());
        break;
      case "speeddown":
        controller.setSpeed(Integer.toString((int) (controller.getSpeed() - 1)));
        view.setSpeed(controller.getSpeed());
        break;
      case "addKey":
        NewVisualVIew editView = (NewVisualVIew) view;
        model.addKeyFrame(editView.getInputKF());
        break;
      default:
        break;
    }
  }


  @Override
  public void changeTempo(double tempo) {
    this.controller.setSpeed(Integer.toString((int)tempo));
  }

  @Override
  public double getTempo() {
    return this.controller.getSpeed();
  }

  @Override
  public int getTick() {
    return model.getTick();
  }
}
