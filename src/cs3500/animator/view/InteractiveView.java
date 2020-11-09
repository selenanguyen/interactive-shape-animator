package cs3500.animator.view;

import cs3500.animator.controller.Keyframe;
import cs3500.animator.controller.IController;
import cs3500.animator.model.ReadOnlyAnimationOperations;
import cs3500.animator.model.Shape;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the interactive view. Takes in action listeners to get feedback from
 * the user and uses the JFrame class to build an interactive window.
 */
public class InteractiveView extends JFrame implements IInteractiveView, ActionListener {

  // the readonly model
  private ReadOnlyAnimationOperations model;
  private AnimationPanel animationPanel;
  private JPanel buttonPanel;
  private JPanel editAnimationPanel;
  private JComboBox shapeList = null;
  private JComboBox framesList;
  private Map<ButtonNames, JButton> buttonMap;
  private Map<TextFieldNames, JTextField> textFieldMap;
  private final List<ButtonNames> animationButtons = new ArrayList<ButtonNames>(
          Arrays.asList(ButtonNames.PLAY, ButtonNames.PAUSE, ButtonNames.RESTART,
          ButtonNames.ENABLE_LOOPING, ButtonNames.DISABLE_LOOPING));
  private boolean enableShapeEdit = false;
  private boolean enableKeyframeEdit = false;
  private ButtonNames editType;
  private String shapeEdit = "";
  private Keyframe keyFrameToEdit = null;
  private final List<TextFieldNames> editKeyframeFields = new ArrayList<>(
          Arrays.asList(TextFieldNames.POSITION_ADD_KEYFRAME,
                  TextFieldNames.WIDTH_ADD_KEYFRAME,
                  TextFieldNames.HEIGHT_ADD_KEYFRAME,
                  TextFieldNames.COLOR_ADD_KEYFRAME));

  /**
   * Constructor of the interactive view.
   * @param m read only animation operation model
   * @param s speed of the animation
   */
  public InteractiveView(ReadOnlyAnimationOperations m, int s) {
    if (m == null) {
      throw new IllegalArgumentException("The model can't be null");
    }

    if (s <= 0) {
      throw new IllegalArgumentException("The speed can't be negative or 0");
    }

    this.model = m;

    this.buttonMap = new HashMap<>();
    this.addButtonsToMap(ButtonNames.values());

    this.textFieldMap = new HashMap<>();
    this.addTextFieldsToMap(TextFieldNames.values());

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Adding the view panel...
    AnimationPanel animationPanel = new AnimationPanel(model);
    animationPanel.setPreferredSize(new Dimension(model.getCanvasWidth(),
            model.getCanvasHeight()));
    animationPanel.setLocation(model.getCanvasStartingX(), model.getCanvasStartingY());
    this.add(new JScrollPane(animationPanel), BorderLayout.EAST);
    this.animationPanel = animationPanel;

    // Adding a button panel to the bottom of the VIEW panel
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 1));
    fillPlayButtonPanel();
    fillFileButtonPanel();
    this.add(buttonPanel, BorderLayout.SOUTH);

    // Adding an Edit Animation panel to the view panel
    editAnimationPanel = new JPanel();
    fillEditAnimationPanel();
    JScrollPane scrollable = new JScrollPane(editAnimationPanel);
    scrollable.setMaximumSize(scrollable.getSize());
    this.add(scrollable, BorderLayout.WEST);

    this.pack();

  }

  /**
   * Fills the file button panel.
   */
  private void fillFileButtonPanel() {
    JPanel fileButtonPanel = new JPanel();
    fileButtonPanel.setLayout(new FlowLayout());
    fileButtonPanel.add(buttonMap.get(ButtonNames.LOAD));
    fileButtonPanel.add(textFieldMap.get(TextFieldNames.LOAD_FILE));
    fileButtonPanel.add(buttonMap.get(ButtonNames.SAVE));
    fileButtonPanel.add(textFieldMap.get(TextFieldNames.SAVE_FILE));
    buttonPanel.add(fileButtonPanel);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refreshAnimation() {
    this.animationPanel.repaint();
  }

  @Override
  public void setListener(IController listener) {
    textFieldMap.get(TextFieldNames.SET_SPEED).setText(Integer.toString(listener.getSpeed()));
    // Add shape listener
    ActionListener al = (e) -> {
      listener.setAddShapeId(textFieldMap.get(TextFieldNames.NAME_ADD_SHAPE).getText());
      listener.actionPerformed(e);
    };
    this.buttonMap.get(ButtonNames.ADD_OVAL).addActionListener(al);
    this.buttonMap.get(ButtonNames.ADD_RECT).addActionListener(al);

    // Remove shape listener
    this.buttonMap.get(ButtonNames.REMOVE_SHAPE).addActionListener((e) -> {
      //listener.setRemoveShapeId(textFieldMap.get(TextFieldNam));
      if (shapeList.getSelectedIndex() == -1) {
        listener.displayError("No shape to remove");
        return;
      }
      listener.removeShape(model.getAnimatedShapes().get(shapeList.getSelectedIndex()).getId());
      refresh();
    });

    // Set speed listener
    this.buttonMap.get(ButtonNames.SET_SPEED).addActionListener((e) -> {
      listener.setSpeed(textFieldMap.get(TextFieldNames.SET_SPEED).getText());
    });

    // Set edit shape listener
    this.buttonMap.get(ButtonNames.EDIT_SHAPE).addActionListener((e) -> {
      if (shapeList.getSelectedIndex() == -1) {
        listener.displayError("No shape to edit");
        return;
      }
      shapeEdit = model.getAnimatedShapes().get(shapeList.getSelectedIndex()).getId();
      enableShapeEdit = true; // TODO: set when enalbeShapeEdit -> false
      List<String> frameTickList = new ArrayList<>();
      for (Keyframe kf : model.getKeyframes(shapeEdit)) {
        frameTickList.add("Tick " + kf.getTick());
      }
      framesList = new JComboBox(frameTickList.toArray());
      setSelectedFrameIndex(0);
      //editKeyframeOptionsPanel.repaint();
      this.refresh();
      //keyFrameToEdit = new Keyframe(getSelectedKeyframe().getTick());
      //editKeyframeOptionsPanel.repaint();
    });

    // TODO: set when enableKeyframeEdit -> false
    // Set edit keyframes listener
    this.buttonMap.get(ButtonNames.EDIT_KEYFRAME_OPTION).addActionListener((e) -> {
      if (model.getKeyframes(shapeEdit).isEmpty()) {
        listener.displayError("Shape " + shapeEdit + " has no keyframes to edit");
        return;
      }
      enableKeyframeEdit = true;
      editType = ButtonNames.EDIT_KEYFRAME_OPTION;
      setSelectedFrameIndex(0);
      keyFrameToEdit = getSelectedKeyframe();
      setEditKeyframeFields(keyFrameToEdit);
      listener.setTick(keyFrameToEdit.getTick());
      listener.pause();
      refresh();
      //editKeyframePanel.repaint();
    });

    // Set add keyframe listener
    this.buttonMap.get(ButtonNames.ADD_KEYFRAME_OPTION).addActionListener((e) -> {
      enableKeyframeEdit = true;
      int selectedTick = model.getKeyframes(shapeEdit).isEmpty()
              ? 1 : model.getAnimatedShape(shapeEdit).getFirstTick();
      keyFrameToEdit = new Keyframe(selectedTick);
      editType = ButtonNames.ADD_KEYFRAME_OPTION;
      setEditKeyframeFields(keyFrameToEdit);
      refresh();
    });

    // Set remove keyframes listener
    this.buttonMap.get(ButtonNames.REMOVE_KEYFRAME_OPTION).addActionListener((e) -> {
      if (model.getKeyframes(shapeEdit).isEmpty()) {
        listener.displayError("Shape " + shapeEdit + " has no keyframes to remove");
        return;
      }
      enableKeyframeEdit = true;
      setSelectedFrameIndex(0);
      keyFrameToEdit = getSelectedKeyframe();
      editType = ButtonNames.REMOVE_KEYFRAME_OPTION;
      refresh();
    });

    // Set keyframe selector listeners:
    // Select keyframe to edit
    this.buttonMap.get(ButtonNames.SELECT_KEYFRAME_TO_EDIT).addActionListener((e) -> {
      keyFrameToEdit = model.getKeyframes(shapeEdit).get(framesList.getSelectedIndex());
      listener.setTick(keyFrameToEdit.getTick());
      listener.pause();
      refresh();
    });

    // Select keyframe to remove
    this.buttonMap.get(ButtonNames.SELECT_KEYFRAME_TO_REMOVE).addActionListener((e) -> {
      listener.removeKeyframe(shapeEdit, getSelectedKeyframe().getTick());
      enableKeyframeEdit = false;
      updateFramesList();
      refresh();
    });

    // Select keyframe (tick) to add
    this.buttonMap.get(ButtonNames.SELECT_TICK_TO_ADD_KEYFRAME).addActionListener((e) -> {
      try {
        keyFrameToEdit = new Keyframe(Integer.parseInt(textFieldMap
                .get(TextFieldNames.TICK_ADD_KEYFRAME).getText()));
      }
      catch (NumberFormatException err) {
        listener.displayError("Invalid tick number.");
      }
      refresh();
    });

    // Save the added or edited keyframe
    this.buttonMap.get(ButtonNames.SAVE_KEYFRAME).addActionListener((e) -> {
      if (editType == ButtonNames.EDIT_KEYFRAME_OPTION) {
        listener.replaceKeyframe(shapeEdit,
                textFieldMap.get(TextFieldNames.COLOR_ADD_KEYFRAME).getText(),
                textFieldMap.get(TextFieldNames.POSITION_ADD_KEYFRAME).getText(),
                textFieldMap.get(TextFieldNames.WIDTH_ADD_KEYFRAME).getText(),
                textFieldMap.get(TextFieldNames.HEIGHT_ADD_KEYFRAME).getText(),
                keyFrameToEdit.getTick());
      }
      else {
        listener.addKeyframe(shapeEdit,
                textFieldMap.get(TextFieldNames.COLOR_ADD_KEYFRAME).getText(),
                textFieldMap.get(TextFieldNames.POSITION_ADD_KEYFRAME).getText(),
                textFieldMap.get(TextFieldNames.WIDTH_ADD_KEYFRAME).getText(),
                textFieldMap.get(TextFieldNames.HEIGHT_ADD_KEYFRAME).getText(),
                keyFrameToEdit.getTick());
      }
      listener.setTick(keyFrameToEdit.getTick());
      updateFramesList();
      editType = null;
      keyFrameToEdit = null;
      enableKeyframeEdit = false;
      this.refresh();
    });

    // Add the controller as a listener for the animation buttons (no custom action listener
    // necessary)
    for (ButtonNames b: animationButtons) {
      this.buttonMap.get(b).addActionListener(listener);
    }

    this.buttonMap.get(ButtonNames.SAVE).addActionListener(listener);
    this.buttonMap.get(ButtonNames.LOAD).addActionListener(listener);
  }

  /**
   * Returns the selected keyframe.
   * @return the keyframe
   */
  private Keyframe getSelectedKeyframe() {
    return model.getKeyframes(shapeEdit)
            .get(framesList.getSelectedIndex());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e == null) {
      throw new IllegalArgumentException("Action event cannot be null");
    }
  }

  /**
   * Updates the list of keyframe for th current shape being edited.
   */
  private void updateFramesList() {
    List<Keyframe> frames = model.getKeyframes(shapeEdit);
    framesList.removeAllItems();
    for (Keyframe kf : frames) {
      framesList.addItem("Tick " + kf.getTick());
    }
    if (editType != null) {
      switch (editType) {
        case ADD_KEYFRAME_OPTION:
          keyFrameToEdit = new Keyframe(model.getAnimatedShape(shapeEdit).getFirstTick());
          break;
        case EDIT_KEYFRAME_OPTION:
        case REMOVE_KEYFRAME_OPTION:
          keyFrameToEdit = getSelectedKeyframe();
          break;
        default:
          break;
      }
      setEditKeyframeFields(keyFrameToEdit);
    }
  }

  /**
   * Sets the initial text field values based on the keyframe being edited.
   * @param kf the keyframe being edited
   */
  private void setEditKeyframeFields(Keyframe kf) {
    String toString = "";
    if (editType == ButtonNames.ADD_KEYFRAME_OPTION) {
      textFieldMap.get(TextFieldNames.TICK_ADD_KEYFRAME).setText(Integer.toString(kf.getTick()));
    }

    if (kf.getWidth() != -1) {
      for (TextFieldNames name : editKeyframeFields) {
        switch (name) {
          case COLOR_ADD_KEYFRAME:
            toString = kf.getColor().toString().substring(3);
            break;
          case POSITION_ADD_KEYFRAME:
            toString = kf.getPosition().toString();
            break;
          case WIDTH_ADD_KEYFRAME:
            toString = Integer.toString(kf.getWidth());
            break;
          case HEIGHT_ADD_KEYFRAME:
            toString = Integer.toString(kf.getHeight());
            break;
          default:
            toString = "";
            break;
        }
        textFieldMap.get(name).setText(toString);
      }
      return;
    }
    for (TextFieldNames name : editKeyframeFields) {
      textFieldMap.get(name).setText(toString);
    }
  }

  @Override
  public void updateComboBoxes() {
    try {
      shapeList.removeAllItems();
      framesList.removeAll();
      for (Shape s : model.getShapes()) {
        shapeList.addItem("Shape " + s.getId());
      }
      shapeList.setSelectedIndex(0);
      setSelectedFrameIndex(0);
    } catch (Exception e) {
      // do nothing
    }
  }

  @Override
  public String getLoadFileName() {
    return this.textFieldMap.get(TextFieldNames.LOAD_FILE).getText();
  }

  @Override
  public String getSaveFileName() {
    return this.textFieldMap.get(TextFieldNames.SAVE_FILE).getText();
  }

  /**
   * Validates and sets the initially selected index of the frame list.
   * @param index the index
   */
  private void setSelectedFrameIndex(int index) {
    if (index < model.getKeyframes(shapeEdit).size()) {
      framesList.setSelectedIndex(index);
    }
    else {
      framesList.setSelectedIndex(-1);
      enableKeyframeEdit = false;
    }
  }

  /**
   * Creates and loads all the buttons used in this view into a map of button names and JButtons.
   * @param buttonNames all button names
   */
  private void addButtonsToMap(ButtonNames... buttonNames) {
    for (ButtonNames name : buttonNames) {
      JButton button = new JButton(name.toString());
      buttonMap.put(name, button);
    }
  }

  /**
   * Creates and loads all the text fields used in this view into a map of
   * text field names and JTextField.
   * @param textFieldNames all button names
   */
  private void addTextFieldsToMap(TextFieldNames... textFieldNames) {
    for (TextFieldNames name : textFieldNames) {
      JTextField textField = new JTextField(10);
      textFieldMap.put(name, textField);
    }
  }

  /**
   * Fills the play button panel with the animation control buttons.
   */
  private void fillPlayButtonPanel() {
    JPanel playButtonPanel = new JPanel();
    playButtonPanel.setLayout(new FlowLayout());
    for (ButtonNames name : animationButtons) {
      playButtonPanel.add(this.buttonMap.get(name));
    }
    buttonPanel.add(playButtonPanel);
  }

  /**
   * Creates a label based on the text field name.
   * @param n the text field name
   * @return the JLabel component
   */
  private JLabel getLabel(TextFieldNames n) {
    return new JLabel(n.toString());
  }

  /**
   * Fills the editAnimationPanel with the proper components.
   */
  private void fillEditAnimationPanel() {
    List<JPanel> rows = new ArrayList<>();
    JPanel editKeyframePanel = getEditKeyframePanel();
    int shapeIndex = 0;
    List<String> shapeNameList = new ArrayList<>();
    for (Shape shape : model.getShapes()) {
      shapeNameList.add(shape.getId());
    }
    if (shapeList != null && shapeList.getSelectedIndex() < shapeNameList.size()) {
      shapeIndex = shapeList.getSelectedIndex();
    }

    shapeList = new JComboBox(shapeNameList.toArray());
    shapeList.setSelectedIndex(shapeNameList.isEmpty() ? -1 : shapeIndex);

    // row 0
    rows.add(getAboveAndBelowPanel(getLabel(TextFieldNames.SET_SPEED),
           getSideBySidePanel(textFieldMap.get(TextFieldNames.SET_SPEED),
                   buttonMap.get(ButtonNames.SET_SPEED))));


    // row 1
    rows.add(getAboveAndBelowPanel(getLabel(TextFieldNames.NAME_ADD_SHAPE),
            getSideBySidePanel(textFieldMap.get(TextFieldNames.NAME_ADD_SHAPE),
                    getSideBySidePanel(buttonMap.get(ButtonNames.ADD_OVAL),
                            buttonMap.get(ButtonNames.ADD_RECT)))));

    // row 2
    rows.add(getAboveAndBelowPanel(new JLabel("Edit shape:"),
            getSideBySidePanel(shapeList,
                    buttonMap.get(ButtonNames.REMOVE_SHAPE),
                    buttonMap.get(ButtonNames.EDIT_SHAPE))));

    if (enableShapeEdit) {
      rows.add(getAboveAndBelowPanel(new JLabel("Editing shape " + shapeEdit + ":"),
              getSideBySidePanel(buttonMap.get(ButtonNames.EDIT_KEYFRAME_OPTION),
                      buttonMap.get(ButtonNames.ADD_KEYFRAME_OPTION),
                      buttonMap.get(ButtonNames.REMOVE_KEYFRAME_OPTION))));
    }
    if (enableKeyframeEdit) {
      switch (editType) {
        case EDIT_KEYFRAME_OPTION:
          rows.add(getAboveAndBelowPanel(new JLabel("Select keyframe to edit:"),
                  getSideBySidePanel(framesList,
                          buttonMap.get(ButtonNames.SELECT_KEYFRAME_TO_EDIT)),
                  new JLabel("Editing keyframe (tick "
                          + Integer.toString(keyFrameToEdit.getTick()) + "):")));
          rows.add(editKeyframePanel);
          JPanel c = new JPanel();
          c.setLayout(new FlowLayout());
          c.add(buttonMap.get(ButtonNames.SAVE_KEYFRAME));
          rows.add(c);
          break;
        case ADD_KEYFRAME_OPTION:
          rows.add(getAboveAndBelowPanel(new JLabel("Select tick to insert keyframe:"),
                  getSideBySidePanel(textFieldMap.get(TextFieldNames.TICK_ADD_KEYFRAME),
                          buttonMap.get(ButtonNames.SELECT_TICK_TO_ADD_KEYFRAME)),
                  new JLabel("Keyframe to add at tick "
                          + Integer.toString(keyFrameToEdit.getTick()))));
          rows.add(editKeyframePanel);
          JPanel c2 = new JPanel();
          c2.setLayout(new FlowLayout());
          c2.add(buttonMap.get(ButtonNames.SAVE_KEYFRAME));
          rows.add(c2);
          break;
        case REMOVE_KEYFRAME_OPTION:
          rows.add(getAboveAndBelowPanel(new JLabel("Select keyframe to remove:"),
                  getSideBySidePanel(framesList,
                          buttonMap.get(ButtonNames.SELECT_KEYFRAME_TO_REMOVE))));
          break;
        default:
          break;
      }
    }

    editAnimationPanel.setLayout(new GridLayout(rows.size(), 1));
    editAnimationPanel.removeAll();
    for (JComponent c : rows) {
      this.editAnimationPanel.add(c);
    }

    this.pack();
  }

  /**
   * Gets the panel with fields for editing/adding a keyframe.
   * @return the JPanel
   */
  private JPanel getEditKeyframePanel() {
    JPanel panel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        if (enableKeyframeEdit) {
          this.setVisible(true);
        }
        else {
          this.setVisible(false);
        }
        super.paintComponent(g);
      }

      @Override
      public void repaint() {
        if (enableKeyframeEdit) {
          this.setVisible(true);
          super.repaint();
        }
        super.repaint();
      }
    };
    panel.setLayout(new GridLayout(2, 2));
    for (TextFieldNames name : editKeyframeFields) {
      panel.add(getTextFieldPanel(name));
    }
    return panel;
  }

  /**
   * Creates and returns a JPanel with the label above and the text field below.
   * @param textField the textfield name
   * @return the JPanel component
   */
  private JPanel getTextFieldPanel(TextFieldNames textField) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1));
    JLabel jlabel = new JLabel(textField.toString());
    panel.add(jlabel);
    panel.add(this.textFieldMap.get(textField));
    return panel;
  }

  /**
   * Creates a panel with the given components laid side by side.
   * @param c the components
   * @return the panel
   */
  private JPanel getSideBySidePanel(Component... c) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, c.length));
    for (Component ci : c) {
      panel.add(ci);
    }
    return panel;
  }

  /**
   * Creates a panel with the given components laid in a column.
   * @param c the components
   * @return the panel
   */
  private JPanel getAboveAndBelowPanel(Component... c) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(c.length, 1));
    for (Component ci : c) {
      panel.add(ci);
    }
    return panel;
  }

  @Override
  public void refresh() {
    fillEditAnimationPanel();
    refreshAnimation();
    this.repaint();
  }
}
