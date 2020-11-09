package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.animator.adapter.Controller;
import cs3500.animator.adapter.IModel;
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

/**
 * Main method to build model and run the view, used for testing purposes.
 */
public final class ExcellenceTest {
  private static JFrame frame = new JFrame("Animator");
  private static AnimationOperations model = new AnimationModel();

  /**
   * Main method, which takes the arguments and constructs the corresponding model and view.
   * @param args the arguments (view type, input file, output file, etc.)
   */
  public static void main(String[] args) {
    //String inputFileName = "src/rotation-layers.txt";
    String inputFileName = "src/toh-3rotate.txt";
    //String inputFileName = "src/buildings.txt";
    String viewType = "edit";
    String outputFileName = "resources/test.txt";
    boolean hasLayers = false; // change to default = false
    boolean canRotate = true; // change to default = false
    TextualView textView;
    VisualView visualView;
    IInteractiveView interactiveView;
    int integerTicksPerSecond = 20;

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
        case "-rotate":
          canRotate = true;
          break;
        case "-layer":
          hasLayers = true;
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
      model = initializeAnimationModel(inputFileName, canRotate, hasLayers);
    }
    catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(frame,
              e.getMessage(),
              "Error",
              JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    IController controller;

    switch (viewType) {
      case "provider":
        IModel m = new cs3500.animator.adapter.AnimationModel(model);
        cs3500.animator.adapter.IController c =
                new Controller(m, integerTicksPerSecond);
        c.setController();
        break;
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
        controller = new AnimationController(model, visualView, integerTicksPerSecond, canRotate,
                hasLayers);
        controller.start();
        break;
      case "edit":
        interactiveView = new InteractiveView(model, integerTicksPerSecond, canRotate, hasLayers);
        controller = new AnimationController(model, interactiveView, integerTicksPerSecond,
                true, canRotate, hasLayers);
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
  private static AnimationOperations initializeAnimationModel(String inputFileName,
                                                              boolean canRotate,
                                                              boolean hasLayers) {
    Readable in;
    try {
      in = new FileReader(inputFileName);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    AnimationBuilder<AnimationModel> builder = new AnimationModel.Builder();
    AnimationReader.parseFile(in, builder, canRotate, hasLayers);
    return builder.build();
  }
}

