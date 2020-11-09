package cs3500.animator.provider.view;

import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;

import cs3500.animator.adapter.IController;
import cs3500.animator.adapter.IModel;
import cs3500.animator.adapter.KeyFrame;
import cs3500.animator.adapter.IShape;

/**
 * Our provider's view panel.
 */
public class NewViewPanel extends JPanel {
  private IController controller;
  private IModel model;
  private JButton start;
  private JButton pause;
  private JButton resume;
  private JButton restart;
  private JButton looping;
  private JButton speedup;
  private JButton speeddown;
  private JTextField addKeyFrameInt;
  private JTextField addKeyFrameName;
  private JButton addKey;

  /**
   * Constructs this view panel.
   * @param model the model
   * @param controller the controller
   */
  public NewViewPanel(IModel model,IController controller) {
    this.controller = controller;
    this.model = model;
    JPanel flash = new JPanel();
    flash.setLayout(null);
    JPanel buttons = new JPanel();
    buttons.setLayout(new FlowLayout());
    flash.setBackground(Color.white);
    flash.setPreferredSize(new Dimension(model.getBoundWidth(),model.getBoundHeight()));

    buttons.setBackground(Color.lightGray);
    buttons.setPreferredSize(new Dimension(model.getBoundWidth(),60));
    this.add(flash, BorderLayout.NORTH);
    this.add(buttons, BorderLayout.SOUTH);
    this.setLocation(model.getBoundX(),model.getBoundY());
    start = new JButton("Start");
    start.setActionCommand("start");
    buttons.add(start);

    pause = new JButton("Pause");
    pause.setActionCommand("pause");
    buttons.add(pause);

    resume = new JButton("Resume");
    resume.setActionCommand("resume");
    buttons.add(resume);

    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    buttons.add(restart);

    looping = new JButton("Looping");
    looping.setActionCommand("looping");
    buttons.add(looping);

    speedup = new JButton("Speed up");
    speedup.setActionCommand("speedup");
    buttons.add(speedup);

    speeddown = new JButton("Speed down");
    speeddown.setActionCommand("speeddown");
    buttons.add(speeddown);

    addKeyFrameInt = new JTextField(20);
    buttons.add(addKeyFrameInt);

    addKeyFrameName = new JTextField(20);
    buttons.add(addKeyFrameName);

    addKey = new JButton("Add Key");
    addKey.setActionCommand("addKey");
    buttons.add(addKey);
  }

  /**
   * Adds the given controler as an action listener.
   * @param l the controller
   */
  public void addActionListener(ActionListener l) {
    start.addActionListener(l);
    pause.addActionListener(l);
    resume.addActionListener(l);
    restart.addActionListener(l);
    looping.addActionListener(l);
    speedup.addActionListener(l);
    speeddown.addActionListener(l);
    addKey.addActionListener(l);
  }

  /**
   * Returns the input keyframe.
   * @return the input text for ht ekeyframe
   */
  public String getInputKeyFrameName() {
    return  addKeyFrameName.getText();
  }

  /**
   * Returns the keyframe inputs as an array of ints.
   * @return the array
   */
  public List<Integer> getInputKeyFrameInt() {
    List<Integer> result = new ArrayList<>();
    for (String s: addKeyFrameInt.getText().split(" ")) {
      result.add(Integer.valueOf(s));
    }
    return result;
  }

  public JButton start() {
    return start;
  }

  public JButton pause() {
    return pause;
  }

  public JButton resume() {
    return resume;
  }

  public JButton restart() {
    return restart;
  }

  public JButton looping() {
    return looping;
  }

  public JButton speedup() {
    return speedup;
  }

  public JButton speeddown() {
    return speeddown;
  }



  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D gg = (Graphics2D) g;
    act(gg);
  }

  /**
   * the helper method to run the visual setting.
   *
   * @param gg the 2d graphy
   */
  private void act(Graphics2D gg) {
    for (Map.Entry<IShape, java.util.List<KeyFrame>> entry : model.getMap().entrySet()) {
      switch (entry.getKey().getType()) {
        case Oval:
          entry.getValue().sort(KeyFrame::compareTo);
          dOval(entry.getValue(), gg);
          break;
        case Rec:
          entry.getValue().sort(KeyFrame::compareTo);
          dRec(entry.getValue(), gg);
          break;
        default:
          throw new IllegalArgumentException("couldn't be null");
      }
    }
  }

  /**
   * the helper method to run, rectangle case.
   *
   * @param value the list of key frame
   * @param gg    the 2d graphy
   */
  private void dRec(java.util.List<KeyFrame> value, Graphics2D gg) {
    for (int i = 0; i < value.size(); i++) {
      if (controller.getTick() == value.get(i).getTime()) {
        int x1 = value.get(i).getX();
        int y1 = value.get(i).getY();
        int w1 = value.get(i).getWidth();
        int h1 = value.get(i).getHeight();
        int r1 = value.get(i).getRed();
        int g1 = value.get(i).getGreen();
        int b1 = value.get(i).getBlue();
        gg.drawRect(x1,y1,w1,h1);
        gg.setColor(new Color(r1, g1, b1));
        gg.fillRect(x1,y1,w1,h1);
      }
    }

    for (int i = 0; i < value.size() - 1; i++) {
      if (controller.getTick() > value.get(i).getTime()
              && controller.getTick() < value.get(i + 1).getTime()) {
        twinningRec( value.get(i), value.get(i + 1),gg);
      }
    }
  }

  private void twinningRec(KeyFrame k1, KeyFrame k2, Graphics2D gg) {
    int x1 = k1.getX();
    int x2 = k2.getX();
    int y1 = k1.getY();
    int y2 = k2.getY();
    int w1 = k1.getWidth();
    int w2 = k2.getWidth();
    int h1 = k1.getHeight();
    int h2 = k2.getHeight();
    int r1 = k1.getRed();
    int g1 = k1.getGreen();
    int b1 = k1.getBlue();
    int r2 = k2.getRed();
    int g2 = k2.getGreen();
    int b2 = k2.getBlue();
    int t1 = k1.getTime();
    int t2 = k2.getTime();
    gg.drawRect(((int) (x1 + (x2 - x1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (controller.getTick() - t1))));
    gg.setColor(new Color(((int) (r1 + (r2 - r1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (g1 + (g2 - g1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (b1 + (b2 - b1) / (t2 - t1) * (controller.getTick() - t1)))));
    gg.fillRect(((int) (x1 + (x2 - x1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (controller.getTick() - t1))));
  }


  /**
   * the helper method to run, oval case.
   *
   * @param value the list of key frame
   * @param gg    the 2d graphy
   */
  private void dOval(java.util.List<KeyFrame> value, Graphics2D gg) {
    for (int i = 0; i < value.size(); i++) {
      if (this.controller.getTick()  == value.get(i).getTime()) {
        int x1 = value.get(i).getX();
        int y1 = value.get(i).getY();
        int w1 = value.get(i).getWidth();
        int h1 = value.get(i).getHeight();
        int r1 = value.get(i).getRed();
        int g1 = value.get(i).getGreen();
        int b1 = value.get(i).getBlue();
        gg.drawOval(x1,y1,w1,h1);
        gg.setColor(new Color(r1, g1, b1));
        gg.fillOval(x1,y1,w1,h1);
      }
    }

    for (int i = 0; i < value.size() - 1; i++) {
      if (controller.getTick() > value.get(i).getTime()
              && controller.getTick() < value.get(i + 1).getTime()) {
        twinningOval( value.get(i), value.get(i + 1),gg);
      }
    }

  }

  private void twinningOval(KeyFrame k1, KeyFrame k2, Graphics2D gg) {
    int x1 = k1.getX();
    int x2 = k2.getX();
    int y1 = k1.getY();
    int y2 = k2.getY();
    int w1 = k1.getWidth();
    int w2 = k2.getWidth();
    int h1 = k1.getHeight();
    int h2 = k2.getHeight();
    int r1 = k1.getRed();
    int g1 = k1.getGreen();
    int b1 = k1.getBlue();
    int r2 = k2.getRed();
    int g2 = k2.getGreen();
    int b2 = k2.getBlue();
    int t1 = k1.getTime();
    int t2 = k2.getTime();
    gg.drawOval(((int) (x1 + (x2 - x1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (controller.getTick() - t1))));
    gg.setColor(new Color(((int) (r1 + (r2 - r1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (g1 + (g2 - g1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (b1 + (b2 - b1) / (t2 - t1) * (controller.getTick() - t1)))));
    gg.fillOval(((int) (x1 + (x2 - x1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (controller.getTick() - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (controller.getTick() - t1))));
  }

}
