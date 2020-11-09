package cs3500.animator.controller;

import cs3500.animator.model.AnimationOperations;
import cs3500.animator.model.Color;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.AnimationModel;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;

import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.VisualView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Controller class representing the window the user will be able to interact with.
 */
public class AnimationController implements IController {
  private AnimationOperations model;
  private final VisualView view;
  private final JFrame messageFrame = new JFrame("Animator");

  // Represent the state of the animation
  private int tick = 1;
  private boolean isPlaying = false;
  private boolean suppressSuccessMessages = false;
  private boolean hasEnded = false;
  private boolean enableLooping = true; // default = true?
  private boolean enableEditing = false;

  // speed of the animation in ticks per second
  private final int DEFAULT_SPEED = 1;
  private int speed;
  private Timer t = new Timer();

  // Text fields
  private String addShapeId = "";
  private String removeShapeId = "";
  private String editShapeId = "";

  // TODO: combine resume/start button -> play button
  // TODO: test looping
  // TODO: visual view vs interactive view...

  /**
   * Constructor of the Animation model controller.
   * @param m animation model
   * @param v visual view
   * @param speed speed of the animation
   */
  public AnimationController(AnimationOperations m, VisualView v, int speed) {
    if (v == null || m == null) {
      throw new IllegalArgumentException("Your model or view cannot be null");
    }
    this.model = m;
    this.view = v;
    this.speed = DEFAULT_SPEED;
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed cannot be negative or zero");
    } else {
      this.speed = speed;
    }
  }

  /**
   * Constructor of the Animation model control that also takes in a boolean to enable
   * editing of the animation or not.
   * @param m animation model
   * @param v visual view
   * @param speed speed of the animation
   * @param enableEditing boolean for enabling editing or not
   */
  public AnimationController(AnimationOperations m, IInteractiveView v, int speed,
                             boolean enableEditing) {
    if (m == null || v == null) {
      throw new IllegalArgumentException("Your model or view cannot be null");
    }
    this.model = m;
    this.view = v;
    this.speed = DEFAULT_SPEED;
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed cannot be negative or zero");
    } else {
      this.speed = speed;
    }
    if (enableEditing) {
      v.setListener(this);
    }
    this.enableEditing = enableEditing;
  }

  @Override
  public void start() {
    this.view.makeVisible();
    view.makeVisible();
    model.applyTick(1);
    if (!enableEditing) {
      play();
    }
  }

  @Override
  public void pause() {
    if (isPlaying) {
      t.cancel();
      isPlaying = false;
    }
  }

  private void play() {
    if (!isPlaying) {
      this.t = new Timer();
      setTimerTask();
      isPlaying = true;
    }
  }

  private void toggleLooping() {
    if (!this.enableLooping) {
      hasEnded = model.getTick() >= model.getLastTick();
    }
    this.enableLooping = !this.enableLooping;
    pause();
    play();
  }

  private void setTimerTask() {
    IInteractiveView v = (IInteractiveView) view;
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        model.applyTick(tick);
        if (enableEditing) {
          v.refreshAnimation();
        }
        else {
          view.refresh();
        }
        tick += speed < 0 ? -1 : 1;
        validateTick();
      }
    }, 0, 1000 / Math.abs(speed));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IInteractiveView view = (IInteractiveView) this.view;
    if (e == null || e.getActionCommand() == null) {
      throw new IllegalArgumentException("The action event or its action command "
              + "cannot be null.");
    }

    // TODO: update buttons
    switch (e.getActionCommand()) {
      case "Play":
        if (hasEnded && !enableLooping) {
          displayError("Animation has ended. Click \"Restart\" to play again");
        }
        play();
        break;
      case "Pause":
        pause();
        break;
      case "Restart":
        tick = 1;
        play();
        break;
      case "Enable Looping":
        if (!enableLooping) {
          toggleLooping();
          displaySuccess("Looping is enabled");
        }
        else {
          displayError("Looping is already enabled");
        }
        break;
      case "Disable Looping":
        if (enableLooping) {
          toggleLooping();
          displaySuccess("Looping is disabled");
        }
        else {
          displayError("Looping is already disabled");
        }
        break;
      case "Add Oval":
        if (isShapeIdValid(addShapeId)) {
          try {
            this.model.addShape(new Oval(addShapeId));
            displaySuccess("Oval " + addShapeId + " added");
            view.refresh();
          }
          catch (IllegalArgumentException err) {
            displayError(err.getMessage());
          }
        }
        break;
      case "Add Rectangle":
        if (isShapeIdValid(addShapeId)) {
          try {
            this.model.addShape(new Rectangle(addShapeId));
            displaySuccess("Rectangle " + addShapeId + " added");
            view.refresh();
          }
          catch (IllegalArgumentException err) {
            displayError(err.getMessage());
          }
        }
        else {
          displayError("You must enter the shape's name");
        }
        break;
      case "Remove Shape":
        if (isShapeIdValid(removeShapeId)) {
          try {
            this.model.removeShape(removeShapeId);
          }
          catch (IllegalArgumentException err) {
            displayError(err.getMessage());
          }
        }
        break;
      case "Load File":
        String loadFile = view.getLoadFileName();
        Readable in;
        try {
          in = new FileReader(loadFile);
        } catch (FileNotFoundException z) {
          displayError("Cannot load this file. Make sure the path is right!");
          throw new IllegalArgumentException(z.getMessage());
        }
        AnimationBuilder<AnimationModel> builder = new AnimationModel.Builder();
        try {
          AnimationReader.parseFile(in, builder);
          AnimationOperations newModel = builder.build();
          this.model.clear();
          for (IAnimatedShape animatedShape : newModel.getAnimatedShapes()) {
            this.model.addAnimatedShape(animatedShape);
          }
        } catch (Exception x) {
          displayError("Cannot load this file. Make sure the path is right!");
        }
        this.view.refresh();
        this.view.makeVisible();
        tick = 1;
        setTimerTask();
        view.updateComboBoxes();
        break;
      case "Save":
        String saveFile = view.getSaveFileName();
        if (saveFile.equals("") || saveFile.length() < 3) {
          displayError("Please specify a valid file name");
        }
        String fileType = saveFile.substring(saveFile.length() - 3).toLowerCase();
        TextualView textView;
        switch (fileType) {
          case "svg":
            textView = new SVGView(this.model, saveFile, speed);
            textView.write();
            break;
          case "txt":
            textView = new TextView(this.model, saveFile);
            textView.write();
            break;
          default:
            displayError("This export type is not supported.");
            break;
        }
        break;
      default:
        break;
    }
    view.refresh();
  }

  @Override
  public void setAddShapeId(String id) {
    this.addShapeId = id;
  }

  private boolean isShapeIdValid(String addShapeId) {
    return addShapeId.length() > 0;
  }

  @Override
  public void displayError(String errorMessage) {
    JOptionPane.showMessageDialog(messageFrame,
            errorMessage,
            "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displaySuccess(String message) {
    if (!this.suppressSuccessMessages) {
      JOptionPane.showMessageDialog(messageFrame,
              message,
              "Success",
              JOptionPane.PLAIN_MESSAGE);
    }
  }

  @Override
  public void removeShape(String id) {
    try {
      this.model.removeShape(id);
      displaySuccess("Succesfully removed shape " + id);
    }
    catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    }
  }

  @Override
  public void setSpeed(String speed) {
    try {
      this.speed = Integer.parseInt(speed);
    }
    catch (NumberFormatException e) {
      displayError("Speed must be an integer.");
    }
    this.pause();
    this.play();
  }

  private void validateTick() {
    if (enableLooping) {
      tick = ((tick - 1) % model.getLastTick()) + 1;
    }
    else if (tick > model.getLastTick() || tick < 1) {
      pause();
      tick = tick > model.getLastTick() ? tick - 1 : tick + 1;
      isPlaying = false;
      hasEnded = true;
    }
  }

  @Override
  public void incrementTick(int inc) {
    tick += inc;
    validateTick();
    model.applyTick(tick);
    view.refresh();
  }

  @Override
  public void setTick(int tick) {
    this.tick = tick;
    validateTick();
    model.applyTick(tick);
    view.refresh();
  }

  @Override
  public void removeKeyframe(String id, int tick) {
    try {
      model.removeKeyframe(id, tick);
    }
    catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    }
  }

  @Override
  public void addKeyframe(String id, String c, String p, String w, String h, int tick) {
    try {
      String errorMsg = "Enter color in the form (r,g,b)";
      if (c.charAt(0) != '(' | c.charAt(c.length() - 1) != ')') {
        displayError(errorMsg);
      }
      String[] values = c.split(",");
      values[0] = values[0].substring(1);
      values[2] = values[2].substring(0, values[2].length() - 1);
      String str = String.join("\n", values);
      Scanner scan = new Scanner(new StringReader(str));
      int r = scan.nextInt();
      int g = scan.nextInt();
      int b = scan.nextInt();

      errorMsg = "Enter position in the form (x,y)";
      if (p.charAt(0) != '(' | p.charAt(p.length() - 1) != ')') {
        displayError(errorMsg);
      }
      values = p.split(",");
      values[0] = values[0].substring(1);
      values[1] = values[1].substring(0, values[1].length() - 1);
      str = String.join("\n",  values);
      scan = new Scanner(new StringReader(str));
      int x = scan.nextInt();
      int y = scan.nextInt();

      Keyframe kf = new Keyframe(new Color(r, g, b), new Position(x, y), Integer.parseInt(w),
              Integer.parseInt(h), tick);
      model.addKeyFrame(id, kf);
      displaySuccess("Added keyframe " + kf.toString());
    }
    catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    }
  }

  private void addKeyframe(String id, Keyframe kf) {
    try {
      model.addKeyFrame(id, kf);
      displaySuccess("Added keyframe " + kf.toString());
    }
    catch (IllegalArgumentException e) {
      displayError(e.getMessage());
    }
  }

  @Override
  public void replaceKeyframe(String id, String c, String p, String w, String h, int tick) {
    this.toggleSuppressSuccessMessages();
    removeKeyframe(id, tick);
    addKeyframe(id, c, p, w, h, tick);
    this.toggleSuppressSuccessMessages();
    displaySuccess("Successfully edited shape " + id + " at tick " + tick);

  }

  @Override
  public int getSpeed() {
    return speed;
  }

  private void toggleSuppressSuccessMessages() {
    this.suppressSuccessMessages = !this.suppressSuccessMessages;
  }
}
