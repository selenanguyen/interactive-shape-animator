package cs3500.animator.provider.view;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import cs3500.animator.adapter.IModel;

/**
 * represent the Visual View class.
 */
public class VisualView extends JFrame implements IVisualView {
  private final ViewPanel vp;

  /**
   * the constructor.
   *
   * @param model the animation model
   * @param tempo the time tick
   */
  public VisualView(IModel model, double tempo) {
    super("So-Called !Simple Animation");
    if (tempo <= 0) {
      throw new IllegalArgumentException("speed should be positive");
    }
    //Double tempo = tempo;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //IModel model = model;
    this.vp = new ViewPanel(model, tempo);
    setLocation(model.getBoundX(), model.getBoundY());
    setPreferredSize(new Dimension(model.getBoundWidth(),model.getBoundHeight()));
    this.add(vp);
    JScrollPane scrollPane = new JScrollPane(vp);
    this.add(scrollPane, BorderLayout.CENTER);
    this.pack();
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
    vp.setSpeed(speed);
  }
}
