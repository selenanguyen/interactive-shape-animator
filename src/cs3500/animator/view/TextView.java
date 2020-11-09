package cs3500.animator.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.animator.model.Action;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.ReadOnlyAnimationOperations;

/**
 * Class representing a text view that shows a textual description of the animation.
 */
public class TextView implements TextualView {
  private String outputFileName = "default";
  private final ReadOnlyAnimationOperations model;

  /**
   * Constructor of the text view. Takes in a model and an output field name.
   * @param model the read only animation operation model
   * @param outputFileName output file name for the textual description
   */
  public TextView(ReadOnlyAnimationOperations model, String outputFileName) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model =  model;
    this.outputFileName = outputFileName;
  }

  /**
   * Creates the descriptive text for the animation.
   * @return a string of the text
   */
  public String getText() {
    StringBuilder str = new StringBuilder();
    List<String> canvasProps = new ArrayList(
            Arrays.asList(Integer.toString(model.getCanvasStartingX()),
            Integer.toString(model.getCanvasStartingY()), Integer.toString(model.getCanvasWidth()),
            Integer.toString(model.getCanvasHeight())));
    str.append("canvas " + String.join(" ", canvasProps) + "\n");
    List<IAnimatedShape> shapes = model.getAnimatedShapes();
    for (IAnimatedShape s : shapes) {
      str.append("shape " + s.getId() + " " + s.getShapeAsString().toLowerCase() + "\n");
      for (Action a : s.getActions()) {
        List motions = new ArrayList(Arrays.asList(Integer.toString(a.getStartTick()),
                Integer.toString(a.getStartPos().getX()), Integer.toString(a.getStartPos().getY()),
                Integer.toString(a.getStartWidth()), Integer.toString(a.getStartHeight()),
                Integer.toString(a.getStartColor().getR()),
                Integer.toString(a.getStartColor().getG()),
                Integer.toString(a.getStartColor().getB()), Integer.toString(a.getEndTick()),
                Integer.toString(a.getEndPos().getX()), Integer.toString(a.getEndPos().getY()),
                Integer.toString(a.getEndWidth()), Integer.toString(a.getEndHeight()),
                Integer.toString(a.getEndColor().getR()), Integer.toString(a.getEndColor().getG()),
                Integer.toString(a.getEndColor().getB())));
        str.append("motion " + s.getId() + " " + String.join(" ", motions) + "\n");
      }
    }
    str.deleteCharAt(str.length() - 1);
    return str.toString();
  }

  @Override
  public void write() {
    if (outputFileName.equals("default")) {
      System.out.print(getText());
    }
    else {
      try {
        FileWriter fileWriter = new FileWriter(this.outputFileName);
        fileWriter.write(getText());
        fileWriter.close();
      }
      catch (IOException e) {
        throw new IllegalArgumentException("Could not write to file.");
      }
    }
  }

  @Override
  public String getOvalAsString() {
    return "oval";
  }

  @Override
  public String getRectangleAsString() {
    return "rectangle";
  }


}
