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
 * represent the Text View class.
 */
public class TextView implements ITextView {

  private IModel model;



  /**
   * represent the constructor.
   *
   * @param model the animation model we needed
   */
  public TextView(IModel model) {
    this.model = model;
  }


  @Override
  public Appendable getInfo() {
    Appendable output = new StringBuffer();
    List<IShape> shapes = model.getShape();
    Map<IShape, List<KeyFrame>> map = model.getMap();
    String result = "";
    result += "canvas " + model.getBoundX() + " " + model.getBoundY() + " " + model.getBoundWidth()
            + " " + model.getBoundHeight() + "\n";
    for (IShape s : shapes) {
      result += "shape " + s.getName() + " " + s.getType() + "\n";
    }
    for (Map.Entry<IShape, List<KeyFrame>> entry : map.entrySet()) {
      List<KeyFrame> keys = entry.getValue();
      for (int i = 0; i < keys.size() - 1; i++) {
        result += "motion " + keys.get(i).getName() + " " + keys.get(i).getTime() + " "
                + keys.get(i).getX() + " " + keys.get(i).getY() + " " + keys.get(i).getWidth()
                + " " + keys.get(i).getHeight() + " " + keys.get(i).getRed() + " "
                + keys.get(i).getGreen()
                + " " + keys.get(i).getBlue() + "     "
                + keys.get(i + 1).getX() + " " + keys.get(i + 1).getY() + " "
                + keys.get(i + 1).getWidth() + " "
                + keys.get(i + 1).getHeight() + " " + keys.get(i + 1).getRed()
                + " " + keys.get(i + 1).getGreen()
                + " " + keys.get(i + 1).getBlue() + "\n";
      }
    }
    try {
      output.append(result);
    } catch ( IOException e) {
      throw new IllegalArgumentException("couldn't appendable");
    }
    return output;
  }


  @Override
  public void writeFile(String name) {
    try {
      FileWriter fileWriter = new FileWriter(new File(name));
      fileWriter.append((CharSequence) this.getInfo());
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}