package cs3500.animator.provider.view;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.animator.adapter.IController;
import cs3500.animator.adapter.IModel;
import cs3500.animator.adapter.KeyFrame;

/**
 * Provider's visual view.
 */
public class NewVisualVIew extends JFrame implements IVisualView {
  private NewViewPanel nvp;

  /**
   * Constructs the view.
   * @param model m
   * @param controller c
   */
  public NewVisualVIew(IModel model, IController controller) {
    //IModel m = model;
    nvp = new NewViewPanel(model,controller);
    //this.controller = controller;
    this.add(nvp);
    setLayout(new BorderLayout());
    setLocation(model.getBoundX(),model.getBoundY());
    setPreferredSize(new Dimension(model.getBoundWidth(),model.getBoundHeight() + 60));
    this.pack();
    this.makeVisible();
  }


  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }


  @Override
  public void dealError(String error) {
    JOptionPane.showMessageDialog(this, error, "Something goes wrong", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setSpeed(double speed) {
    // nothing
  }

  /**
   * Gets the input keyframe text.
   * @return keyframe inputs
   */
  public KeyFrame getInputKF() {
    String name = nvp.getInputKeyFrameName();
    List<Integer> nums = nvp.getInputKeyFrameInt();
    KeyFrame result = null;
    try {
      result = new KeyFrame(name,nums.get(0),nums.get(1),nums.get(2),nums.get(3),nums.get(4),
              nums.get(5),nums.get(6),nums.get(7));

    } catch (IndexOutOfBoundsException | NullPointerException e) {
      this.dealError("Please input valid key frame");
    }
    return result;
  }


}
