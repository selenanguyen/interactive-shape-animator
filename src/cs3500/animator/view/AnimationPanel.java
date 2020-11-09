package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import java.awt.geom.Ellipse2D;
import java.util.List;

import cs3500.animator.model.Shape;

import cs3500.animator.model.ReadOnlyAnimationOperations;

/**
 * The panel for the animation, which draws the shapes.
 */
public class AnimationPanel extends JPanel {
  private final ReadOnlyAnimationOperations model;

  /**
   * Constructs the panel from a read-only model.
   * @param model the model
   */
  public AnimationPanel(ReadOnlyAnimationOperations model) {
    super();
    this.model = model;
    this.setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);
    List<Shape> shapes = this.model.getShapes();
    for (Shape s : shapes) {
      if (s.isInvisible()) {
        continue;
      }
      java.awt.Shape shape = s.isOval() ? new Ellipse2D.Double(s.getPosition().getX()
              - model.getCanvasStartingX(), s.getPosition().getY() - model.getCanvasStartingY(),
              s.getWidth(), s.getHeight())
              : new Rectangle.Double(s.getPosition().getX() - model.getCanvasStartingX(),
              s.getPosition().getY() - model.getCanvasStartingY(),
              s.getWidth(), s.getHeight());
      g2d.setColor(new Color(s.getColor().getR(), s.getColor().getG(), s.getColor().getB()));
      g2d.fill(shape);
    }

    AffineTransform originalTransform = g2d.getTransform();

    g2d.translate(0, this.getPreferredSize().getHeight());
    g2d.scale(1, -1);
    g2d.setTransform(originalTransform);
  }
}
