package cs3500.animator.adapter;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import cs3500.animator.controller.IController;
import cs3500.animator.provider.view.NewViewPanel;
import cs3500.animator.provider.view.NewVisualVIew;
import cs3500.animator.view.IInteractiveView;

/**
 * Adapter class for our provider's NewVisualView class.
 *  The purpose is to allow the view to be passed to our contructoras an IInteractiveView.
 */
public class InteractiveNewVisualView extends NewVisualVIew implements IInteractiveView {

  // the controller
  private final cs3500.animator.adapter.IController controller;

  /**
   * This constructor removes and re-adds the panel, this time calling addActionListener.
   * This was necessary to add the action listener to the JPanel, since our provider's code
   * does not.
   * @param model the IModel model
   * @param c the controller
   */
  public InteractiveNewVisualView(IModel model,
                                  cs3500.animator.adapter.IController c) {
    super(model, c);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    NewViewPanel nvp = new NewViewPanel(model,c);
    // re-adding the panel they used. this is the only way to set the action listener.
    this.removeAll();
    nvp.addActionListener(c);
    // this.add(nvp);
    nvp.setSize(new Dimension(model.getBoundWidth(), model.getBoundHeight()));
    nvp.setPreferredSize(new Dimension(model.getBoundWidth(), model.getBoundHeight()));
    nvp.setLocation(model.getBoundX(), model.getBoundY());
    this.setLocation(model.getBoundX(),model.getBoundY());
    this.setPreferredSize(new Dimension(model.getBoundWidth(),model.getBoundHeight() + 60));
    this.add(nvp, BorderLayout.NORTH);
    nvp.setVisible(true);
    this.controller = c;
    this.pack();
    this.makeVisible();
  }

  @Override
  public void refresh() {
    System.out.println("refreshing");
    this.repaint();
  }

  public void initializeSpeed() {
    this.setSpeed(this.controller.getTempo());
  }

  @Override
  public void setListener(IController listener) {
    // unnecesary for providers' view; listener is set in constructor
  }

  @Override
  public void updateComboBoxes() {
    // unnecesary for providers' view
  }

  @Override
  public String getLoadFileName() {
    // unnecesary for providers' view
    return null;
  }

  @Override
  public String getSaveFileName() {
    // unnecessary
    return null;
  }

  @Override
  public void refreshAnimation() {
    super.refresh();
  }
}
