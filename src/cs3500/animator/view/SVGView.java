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
              + "\" " + this.getXLabel(s) + "=\"" + this.getXValue(s) + "\" "
              + this.getYLabel(s) + "=\""
              + this.getYValue(s) + "\" " + this.getWidthLabel(s) + "=\""
              + this.getWidthValue(s)  + "\" " + this.getHeightLabel(s) + "=\""
              + this.getHeightValue(s)
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
   * Gets the value corresponding to the shape's x position, based on which shape it is.
   * @param s the shape
   * @return the label corresponding to the shape's x position
   */
  private String getXValue(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? Double.toString(s.getShapeAtStart().getPosition().getX()
              + s.getShapeAtStart().getWidth() / 2)
            : Integer.toString(s.getShapeAtStart().getPosition().getX());
  }

  /**
   * Gets the value corresponding to the shape's y position, based on which shape it is.
   * @param s the shape
   * @return the value corresponding to the shape's y position
   */
  private String getYValue(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? Double.toString(s.getShapeAtStart().getPosition().getY()
            + s.getShapeAtStart().getHeight() / 2)
            : Integer.toString(s.getShapeAtStart().getPosition().getY());
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

  /**
   * Gets the value corresponding to the shape's width, based on which shape it is.
   * @param s the shape
   * @return the value corresponding to the shape's width
   */
  private String getWidthValue(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? Double.toString(s.getShapeAtStart().getWidth() / 2.0) :
            Integer.toString(s.getShapeAtStart().getWidth());
  }

  /**
   * Gets the value corresponding to the shape's height, based on which shape it is.
   * @param s the shape
   * @return the value corresponding to the shape's height
   */
  private String getHeightValue(IAnimatedShape s) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? Double.toString(s.getShapeAtStart().getHeight() / 2.0) :
            Integer.toString(s.getShapeAtStart().getHeight());
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
   * Gets a string representation of the starting center position.
   * @param action the action
   * @param s the shape
   * @return a string representation of the shape's starting center position
   */
  private String getStartCenter(Action action, IAnimatedShape s) {
    float x = s.getShapeAtStart().getPosition().getX() + s.getShapeAtStart().getWidth() / 2;
    float y = s.getShapeAtStart().getPosition().getY() + s.getShapeAtStart().getHeight() / 2;
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? String.format("%d %d", (action.getStartPos().getX() + action.getStartWidth() / 2),
            action.getStartPos().getY() + action.getStartHeight() / 2)
            : String.format("%.1f %.1f", x, y);

    //return s.getShapeAsString(this) == this.getOvalAsString()
    //        ? String.format("%.1f %.1f", x, y) : String.format("%.1f %.1f", x, y);
  }

  /**
   * Gets a string representation of the ending center position.
   * @param action the action
   * @param s the shape
   * @return a string representation of the shape's ending center position
   */
  private String getEndCenter(Action action, IAnimatedShape s) {
    float x = action.getStartPos() == action.getEndPos()
            && action.getStartWidth() != action.getEndWidth()
            && s.getShapeAsString(this) == this.getOvalAsString()
            ? action.getStartPos().getX() + action.getStartWidth() / 2
            : action.getEndPos().getX() + action.getEndWidth() / 2;
    float y = action.getStartPos() == action.getEndPos()
            && action.getStartHeight() != action.getEndHeight()
            && s.getShapeAsString(this) == this.getOvalAsString()
            ? action.getStartPos().getY() + action.getStartHeight() / 2
            : action.getEndPos().getY() + action.getEndHeight() / 2;
    return String.format("%.1f %.1f", x, y);

    //return s.getShapeAsString(this) == this.getOvalAsString()
    //       ? String.format("%.1f %.1f", x, y) : String.format("%.1f %.1f", x, y);
  }

  private String shapeSpecificPosition(IAnimatedShape s, int xOrY, int dimension) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? Double.toString(xOrY + dimension / 2.0)
            : Integer.toString(xOrY);
  }

  private String shapeSpecificDimension(IAnimatedShape s, int dimension) {
    return s.getShapeAsString(this) == this.getOvalAsString()
            ? Double.toString(dimension / 2.0)
            : Integer.toString(dimension);
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
              + shapeSpecificDimension(s, a.getStartHeight()) + "\" to=\""
              + shapeSpecificDimension(s, a.getEndHeight()) + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartWidth() != a.getEndWidth()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getWidthLabel(s) + "\" from=\""
              + shapeSpecificDimension(s, a.getStartWidth()) + "\" to=\""
              + shapeSpecificDimension(s, a.getEndWidth()) + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartPos().getX() != a.getEndPos().getX()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getXLabel(s) + "\" from=\""
              + shapeSpecificPosition(s, a.getStartPos().getX(), a.getStartWidth())
              + "\" to=\"" + shapeSpecificPosition(s, a.getEndPos().getX(), a.getEndWidth())
              + "\" fill=\"" + fill + "\"/>\n");
    }
    if (a.getStartPos().getY() != a.getEndPos().getY()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + getMsFromTick(a.getStartTick())
              + "ms\" dur=\"" + getDuration(a)
              + "ms\" attributeName=\"" + this.getYLabel(s) + "\" from=\""
              + shapeSpecificPosition(s, a.getStartPos().getY(), a.getStartHeight())
              + "\" to=\"" + shapeSpecificPosition(s, a.getEndPos().getY(), a.getEndHeight())
              + "\" fill=\"" + fill + "\"/>\n");
    }
    if (isLast && s.getLastTick() < model.getLastTick()) {
      str.append("\t<animate attributeType=\"xml\" begin=\"" + this.getMsFromTick(a.getEndTick())
              + "ms\" dur=\"0.1ms\" attributeName=\"visibility\" from=\"visible\" "
              + "to=\"hidden\" fill=\"freeze\"/>\n");
    }
    if (a.getStartRotation() != a.getEndRotation()) {
      str.append("\t<animateTransform attributeType=\"xml\" begin=\""
              + this.getMsFromTick(a.getStartTick()) + "ms\" end=\""
              + this.getMsFromTick(a.getEndTick()) + "ms\" dur=\"" + getDuration(a) + "ms\" "
              + "attributeName=\"transform\" type=\"rotate\" from=\"" + a.getStartRotation()
              + " " + getStartCenter(a, s) + "\" to=\"" + a.getEndRotation()
              + " " + getEndCenter(a, s) + "\"/>\n");
    }
  }
}
