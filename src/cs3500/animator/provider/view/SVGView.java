package cs3500.animator.provider.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cs3500.animator.adapter.IModel;
import cs3500.animator.adapter.KeyFrame;
import cs3500.animator.adapter.IShape;

/**
 * represent the SVGView class to achieve all operations relating to SVG.
 */
public class SVGView implements ISVGView {

  private IModel model;
  private double tempo;


  /**
   * represent the constructor.
   *
   * @param model the animation model
   * @param tempo tempo
   */
  public SVGView(IModel model, double tempo) {
    if (tempo <= 0) {
      throw new IllegalArgumentException("tempo should be positive");
    }
    this.model = model;
    this.tempo = tempo;
  }


  @Override
  public Appendable svgFormat() {
    Appendable app = new StringBuffer();
    String result = "";
    result += "<svg width=\"" + model.getBoundWidth() + "\" height=\""
            + model.getBoundHeight() + "\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">\n";
    Map<IShape, List<KeyFrame>> map = model.getMap();
    for (Map.Entry<IShape, List<KeyFrame>> entry : map.entrySet()) {
      result += relevantFormat(entry.getKey(), entry.getValue());
    }
    result += "</svg>";

    try {
      app.append(result);
      return app;
    } catch (IOException e) {
      throw new IllegalArgumentException("couldn't append");
    }

  }

  /**
   * the helper method to get the relevantFormat.
   *
   * @param key   the shape
   * @param value the list of key frame
   * @return the relevantFormat
   */
  private String relevantFormat(IShape key, List<KeyFrame> value) {
    switch (key.getType()) {
      case Oval:
        return relevantFormatOval(key, value);
      case Rec:
        return relevantFormatRectangle(key, value);
      default:
        throw new IllegalArgumentException("Nothing here");
    }
  }

  /**
   * the helper method to get the relevantFormat for the rectangle.
   *
   * @param key   the shape
   * @param value the list of key frame
   * @return the relevantFormat for the rectangle
   */
  private String relevantFormatRectangle(IShape key, List<KeyFrame> value) {
    String result = "";
    result += "<rect ";
    result += "id= \"" + key.getName() + "\" ";
    result += "x= \"" + value.get(0).getX() + "\" ";
    result += "y= \"" + value.get(0).getY() + "\" ";
    result += "width= \"" + value.get(0).getWidth() + "\" ";
    result += "height= \"" + value.get(0).getHeight() + "\" ";
    result += "fill=\"rgb(" + value.get(0).getRed() + "," + value.get(0).getGreen() + ","
            + value.get(0).getBlue() + ")\" visibility=\"visible\" >\n";

    for (int i = 0; i < value.size() - 1; i++) {
      result += whatChange(value.get(i), value.get(i + 1));
    }
    result += "</rect>\n";
    return result;
  }

  /**
   * helper method to check what has changed.
   *
   * @param k1 given key frame
   * @param k2 given key frame
   * @return the changing as string
   */
  private String whatChange(KeyFrame k1, KeyFrame k2) {
    String result = "";
    if (k1.getX() != k2.getX()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "x" + " ";
      result += "from=\"" + k1.getX() + "\" " + "to=\"" + k2.getX() + "\"  fill=\"freeze\" />\n";
    }

    if (k1.getY() != k2.getY()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "y" + " ";
      result += "from=\"" + k1.getY() + "\" " + "to=\"" + k2.getY() + "\"  fill=\"freeze\" />\n";
    }


    if (k1.getWidth() != k2.getWidth()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "width" + " ";
      result += "from=\"" + k1.getWidth() + "\" " + "to=\"" + k2.getWidth() + "\"  "
              + "fill=\"freeze\" />\n";
    }


    if (k1.getHeight() != k2.getHeight()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "height" + " ";
      result += "from=\"" + k1.getHeight() + "\" " + "to=\"" + k2.getHeight() + "\" "
              + " fill=\"freeze\" />\n";
    }

    return getStringColor(k1, k2, result);
  }


  /**
   * the helper method to get the relevantFormat for the oval.
   *
   * @param key   the shape
   * @param value the list of key frame
   * @return the relevantFormat for the oval
   */
  private String relevantFormatOval(IShape key, List<KeyFrame> value) {
    String result = "";
    result += "<ellipse ";
    result += "id= \"" + key.getName() + "\" ";
    result += "cx= \"" + value.get(0).getX() + "\" ";
    result += "cy= \"" + value.get(0).getY() + "\" ";
    result += "rx= \"" + value.get(0).getWidth() + "\" ";
    result += "ry= \"" + value.get(0).getHeight() + "\" ";
    result += "fill=\"rgb(" + value.get(0).getRed() + "," + value.get(0).getGreen() + ","
            + value.get(0).getBlue() + ")\" visibility=\"visible\" >\n";

    for (int i = 0; i < value.size() - 1; i++) {
      result += whatChange2(value.get(i), value.get(i + 1));
    }
    result += "</ellipse>\n";
    return result;

  }

  /**
   * helper method to check what has changed.
   *
   * @param k1 given key frame
   * @param k2 given key frame
   * @return the changing as string
   */
  private String whatChange2(KeyFrame k1, KeyFrame k2) {
    String result = "";
    if (k1.getX() != k2.getX()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "cx" + " ";
      result += "from=\"" + k1.getX() + "\" " + "to=\"" + k2.getX() + "\"  fill=\"freeze\" />\n";
    }

    if (k1.getY() != k2.getY()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "cy" + " ";
      result += "from=\"" + k1.getY() + "\" " + "to=\"" + k2.getY() + "\"  fill=\"freeze\" />\n";
    }


    if (k1.getWidth() != k2.getWidth()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "dx" + " ";
      result += "from=\"" + k1.getWidth() + "\" " + "to=\"" + k2.getWidth() + "\"  "
              + "fill=\"freeze\" />\n";
    }


    if (k1.getHeight() != k2.getHeight()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "dy" + " ";
      result += "from=\"" + k1.getHeight() + "\" " + "to=\"" + k2.getHeight() + "\" "
              + " fill=\"freeze\" />\n";
    }

    return getStringColor(k1, k2, result);
  }

  /**
   * get the shape's color as string.
   *
   * @param k1     the given key frame
   * @param k2     the given key frame
   * @param result the final result
   * @return the result of the color
   */
  private String getStringColor(KeyFrame k1, KeyFrame k2, String result) {
    if (k1.getRed() != k2.getRed()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "r" + " ";
      result += "from=\"" + k1.getRed() + "\" " + "to=\"" + k2.getRed() + "\" "
              + " fill=\"freeze\" />\n";
    }

    if (k1.getGreen() != k2.getGreen()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "g" + " ";
      result += "from=\"" + k1.getGreen() + "\" " + "to=\"" + k2.getGreen() + "\" "
              + " fill=\"freeze\" />\n";
    }

    if (k1.getBlue() != k2.getBlue()) {
      result += "\t<animate attributeType=\"xml\" begin=\"" + k1.getTime() * 1000 / tempo + "ms\" ";
      result += "dur=\"" + (k1.getTime() - k2.getTime()) * 1000 / tempo + "ms\" ";
      result += "attributeName=" + "b" + " ";
      result += "from=\"" + k1.getBlue() + "\" " + "to=\"" + k2.getBlue() + "\" "
              + " fill=\"freeze\" />\n";
    }
    return result;
  }


  @Override
  public void writeFile(String name) {
    try {
      FileWriter fileWriter = new FileWriter(new File(name));
      //fileWriter.write((CharSequence)this.svgFormat());
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}