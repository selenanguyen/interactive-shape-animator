package cs3500.animator;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationOperations;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.InteractiveView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.SwingView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.VisualView;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Timer;

/**
 * Main method to build model and run the view.
 */
public final class Excellence {
  private static JFrame frame = new JFrame("Animator");
  private static AnimationOperations model = new AnimationModel();

  /**
   * Main method, which takes the arguments and constructs the corresponding model and view.
   * @param args the arguments (view type, input file, output file, etc.)
   */
  public static void main(String[] args) {
    String inputFileName = null;
    String viewType = null;
    String outputFileName = "default";
    TextualView textView;
    VisualView visualView;
    IInteractiveView interactiveView;

    int integerTicksPerSecond = 1;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          inputFileName = args[i + 1];
          i++;
          break;
        case "-view":
          viewType = args[i + 1];
          i++;
          break;
        case "-out":
          outputFileName = args[i + 1];
          i++;
          break;
        case "-speed":
          integerTicksPerSecond = Integer.parseInt(args[i + 1]);
          i++;
          break;
        default:
          JOptionPane.showMessageDialog(frame,
                  "Command line argument \"" + args[i] + "\" is invalid.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          System.exit(-1);
      }
    }

    if (inputFileName == null || viewType == null) {
      JOptionPane.showMessageDialog(frame,
              "Input file and view type must be specified in the command line",
              "Error",
              JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    try {
      model = initializeAnimationModel(inputFileName);
    }
    catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(frame,
              e.getMessage(),
              "Error",
              JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    Timer t = new Timer();
    IController controller;

    switch (viewType) {
      case "text":
        textView = new TextView(model, outputFileName);
        textView.write();
        break;
      case "svg":
        textView = new SVGView(model, outputFileName, integerTicksPerSecond);
        textView.write();
        break;
      case "visual":
        visualView = new SwingView(model);
        controller = new AnimationController(model, visualView, integerTicksPerSecond);
        controller.start();
        break;
      case "edit":
        interactiveView = new InteractiveView(model, integerTicksPerSecond);
        controller = new AnimationController(model, interactiveView, integerTicksPerSecond, true);
        controller.start();
        break;
      default:
        JOptionPane.showMessageDialog(frame,
                "Invalid view type.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
  }

  /**
   * Returns a new animation model based on the intput file.
   * @param inputFileName the file name of the input file
   * @return an AnimationOperations model
   */
  private static AnimationOperations initializeAnimationModel(String inputFileName) {
    Readable in;
    try {
      in = new FileReader(inputFileName);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    AnimationBuilder<AnimationModel> builder = new AnimationModel.Builder();
    AnimationReader.parseFile(in, builder);
    return builder.build();
  }
}

