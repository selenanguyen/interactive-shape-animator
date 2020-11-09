package cs3500.animator.provider.view;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import cs3500.animator.adapter.IModel;
import cs3500.animator.adapter.KeyFrame;
import cs3500.animator.adapter.IShape;

/**
 * represent the View Panel.
 */
public class ViewPanel extends JPanel implements ActionListener {
  private IModel model;
  private double tempo;
  private Timer time;
  private double tick = 0;

  /**
   * the constructor.
   *
   * @param model the animation model
   * @param tempo the time tick
   */
  public ViewPanel(IModel model, double tempo) {
    super();
    this.model = model;
    setLayout(null);
    this.setBackground(Color.white);
    this.tempo = tempo;
    this.time = new Timer((int) (500 / tempo), this);
    time.start();
  }


  @Override
  protected void paintComponent(Graphics g) {
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
    for (Map.Entry<IShape, List<KeyFrame>> entry : model.getMap().entrySet()) {
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
  private void dRec(List<KeyFrame> value, Graphics2D gg) {
    for (int i = 0; i < value.size(); i++) {
      if (this.tick == value.get(i).getTime()) {
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
      if (tick > value.get(i).getTime() && tick < value.get(i + 1).getTime()) {
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
    gg.drawRect(((int) (x1 + (x2 - x1) / (t2 - t1) * (tick - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (tick - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (tick - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (tick - t1))));
    gg.setColor(new Color(((int) (r1 + (r2 - r1) / (t2 - t1) * (tick - t1))),
            ((int) (g1 + (g2 - g1) / (t2 - t1) * (tick - t1))),
            ((int) (b1 + (b2 - b1) / (t2 - t1) * (tick - t1)))));
    gg.fillRect(((int) (x1 + (x2 - x1) / (t2 - t1) * (tick - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (tick - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (tick - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (tick - t1))));
  }


  /**
   * the helper method to run, oval case.
   *
   * @param value the list of key frame
   * @param gg    the 2d graphy
   */
  private void dOval(List<KeyFrame> value, Graphics2D gg) {
    for (int i = 0; i < value.size(); i++) {
      if (this.tick  == value.get(i).getTime()) {
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
      if (tick > value.get(i).getTime() && tick < value.get(i + 1).getTime()) {
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
    gg.drawOval(((int) (x1 + (x2 - x1) / (t2 - t1) * (tick - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (tick - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (tick - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (tick - t1))));
    gg.setColor(new Color(((int) (r1 + (r2 - r1) / (t2 - t1) * (tick - t1))),
            ((int) (g1 + (g2 - g1) / (t2 - t1) * (tick - t1))),
            ((int) (b1 + (b2 - b1) / (t2 - t1) * (tick - t1)))));
    gg.fillOval(((int) (x1 + (x2 - x1) / (t2 - t1) * (tick - t1))),
            ((int) (y1 + (y2 - y1) / (t2 - t1) * (tick - t1))),
            ((int) (w1 + (w2 - w1) / (t2 - t1) * (tick - t1))),
            ((int) (h1 + (h2 - h1) / (t2 - t1) * (tick - t1))));
  }


  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    tick++;
    this.repaint();
    checkMaxTime();
    System.out.println(tick);
  }

  /**
   * to check if the tick reach the limit of the time or not.
   */
  private void checkMaxTime() {
    double maxTime = 0;
    for (Map.Entry<IShape, List<KeyFrame>> entry : model.getMap().entrySet()) {
      for (int i = 0; i < entry.getValue().size(); i++) {
        maxTime = Math.max(entry.getValue().get(i).getTime(), maxTime);
      }
    }
    if (tick >= maxTime) {
      time.stop();
    }
  }

  public void setSpeed(double speed) {
    this.tempo = speed;
  }

}
