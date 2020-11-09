package cs3500.animator.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cs3500.animator.model.Action;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.ReadOnlyAnimationOperations;

/**
 * A view that constructs an svg file based on an animation model.
 */
public class SVGView implements TextualView {
  private int ticksPerSecond = 1;
  private String outputFileName = "default";
  private final ReadOnlyAnimationOperations model;
  private final StringBuilder str = new StringBuilder();

  /**
   * Constructs an SVGView that will output to a file.
   * @param model the read-only animation model
   * @param outputFileName the name of the output file
   * @param ticksPerSecond ticks per second (speed)
   */
  public SVGView(ReadOnlyAnimationOperations model, String outputFileName, int ticksPerSecond) {

    if (model == null) {
      throw new IllegalArgumentException("Model name cannot be null");
    }
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("Ticks per second cannot be zero or negative");
    }

    this.model = model;
    this.outputFileName = outputFileName;
    this.ticksPerSecond = ticksPerSecond;
  }

  @Override
  public String getText() {
    str.append("<svg viewbox=\"" + model.getCanvasStartingX() + " " + model.getCanvasStartingY()
            + " " + model.getCanvasWidth() + " " + model.getCanvasHeight() + "\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">" + "\n");
    List<IAnimatedShape> shapes = model.getAnimatedShapes();

    for (IAnimatedShape s : shapes) {
      str.append("<" + s.getShapeAsString(this) + " id=\"" + s.getShapeAtStart().getId()
              + "\" " + this.getXLabel(s) + "=\"" + s.getShapeAtStart().getPosition().getX() + "\" "
              + this.getYLabel(s) + "=\""
              + s.getShapeAtStart().getPosition().getY() + "\" " + this.getWidthLabel(s) + "=\""
              + s.getShapeAtStart().getWidth() + "\" " + this.getHeightLabel(s) + "=\""
              + s.getShapeAtStart().getHeight()
              + "\" fill=\"" + s.getShapeAtStart().getColor().toString() + "\" visibility=\"");
      Boolean isHidden = false;
      if (! s.getActions().isEmpty() && s.getActions().get(0).getStartTick() > 1) {
        isHidden = true;
        str.append("hidden");
      }
      else {
        str.append("visible");
      }

      str.append("\" >\n");
      if (isHidden) {
        String fill = s.getActions().size() >= 1 ? "freeze" : "remove";
        str.append("\t<animate attributeType=\"xml\" begin=\"" + this.getMsFromTick(1)
                + "ms\" dur=\"" + getMsFromTick(s.getActions().get(0).getStartTick() - 1)
                + "ms\" attributeName=\"visibility\" from=\"hidden\" "
                + "to=\"visible\" fill=\"" + fill + "\"/>\n");
      }

      boolean isLast = false;

      for (int i = 0; i < s.getActions().size(); i++) {
        if (i == s.getActions().size() - 1) {
          isLast = true;
        }
        appendAnimationSpecificXML(s.getActions().get(i), s, isLast);
      }
      str.append("</" + s.getShapeAsString(this) + ">\n");
    }
    str.append("</svg>");
    return str.toString();
  }

  /**
   * Gets the label corresponding to the shape's x position, based on which shape it is.
   * @param s the shape
   * @return the label corresponding to the shape's x position
   */
  private String getXLabel(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? "cx" : "x";
  }

  /**
   * Gets the label corresponding to the shape's y position, based on which shape it is.
   * @param s the shape
   * @return the label corresponding to the shape's y position
   */
  private String getYLabel(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? "cy" : "y";
  }

  /**
   * Gets the label corresponding to the shape's width, based on which shape it is.
   * @param s the shape
   * @return the label corresponding to the shape's width
   */
  private String getWidthLabel(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? "rx" : "width";
  }

  /**
   * Gets the label corresponding to the shape's height, based on which shape it is.
   * @param s the shape
   * @return the label corresponding to the shape's height
   */
  private String getHeightLabel(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? "ry" : "height";
  }

  @Override
  public String getOvalAsString() {
    return "ellipse";
  }

  @Override
  public String getRectangleAsString() {
    return "rect";
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

  /**
   * Gets a string representation of the tick in ms.
   * @param tick the tick
   * @return a string representation of the duration in ms
   */
  private String getMsFromTick(int tick) {
    Float seconds = (float) tick / ticksPerSecond;
    Float ms = seconds * 1000;
    return String.format("%.1f", ms);
  }

  /**
   * Gets a string representation of the duration in ms.
   * @param action the action
   * @return a string representation of the duration in ms
   */
  private String getDuration(Action action) {
    int tickDuration = action.getEndTick() - action.getStartTick();
    return getMsFromTick(tickDuration);
  }

  /**
   * Appends the proper animation tags to the text, corresponding to the given action.
   * @param a the action
   * @param s the animated shape
   * @param isLast whether the action is the last in the shape's list of actions
   */
  private void appendAnimationSpecificXML(Action a, IAnimatedShape s, boolean isLast) {
    String fill;
    if (isLast && s.getLastTick() == model.getLastTick()) {
      fill = "remove";
    }
    else {
      fill = "freeze";
    }


    if (!a.getStartColor().equals(a.getEndColor())) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"fill\" from=\"" + a.getStartColor().toString()
              + "\" to=\"" + a.getEndColor().toString() + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartHeight() != a.getEndHeight()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getHeightLabel(s) + "\" from=\""
              + a.getStartHeight() + "\" to=\""
              + a.getEndHeight() + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartWidth() != a.getEndWidth()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getWidthLabel(s) + "\" from=\""
              + a.getStartWidth() + "\" to=\""
              + a.getEndWidth() + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartPos().getX() != a.getEndPos().getX()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getXLabel(s) + "\" from=\""
              + a.getStartPos().getX() + "\" to=\""
              + a.getEndPos().getX() + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartPos().getY() != a.getEndPos().getY()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getYLabel(s) + "\" from=\""
              + a.getStartPos().getY() + "\" to=\""
              + a.getEndPos().getY() + "\" fill=\"" + fill + "\"/>\n");
    }
    if (isLast && s.getLastTick() < model.getLastTick()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + this.getMsFromTick(a.getEndTick())
              + "ms\" dur=\"0.1ms\" attributeName=\"visibility\" from=\"visible\" "
              + "to=\"hidden\" fill=\"freeze\"/>\n");
    }
  }
}
