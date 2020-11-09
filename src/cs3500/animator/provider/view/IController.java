package cs3500.animator.provider.view;

import java.awt.event.ActionListener;

/**
 * Our provider's controller.
 */
public interface IController extends ActionListener {

  public int getTick();

  public void changeTempo(double tempo);

  public double getTempo();
}
