# Assignment 7

<h3>Run the Program</h3>
Using a command-prompt/terminal, navigate to <code>\<projectRoot>/resources</code>.
<br /><br />
Replacing the proper arguments, enter: <code>java -jar Animation.jar -in NameOfInputFile -view ViewType -out 
NameOfOutputFile -speed Speed</code>

<h3>Our Model</h3>
![Class diagram](https://d1b10bmlvqabco.cloudfront.net/attach/k3z1ghrz7630h/jc6oeh9j3kx36m/k7z678ligq0g/IMG_0368.jpg)
The idea behind our model design stemmed from the relationship between shapes and actions. 
We wanted to plan our animator to be able to keep track of large and long running animators and 
have the ability to rewind or find a moment of time in the animation. To do this, we created a 
model that is responsible for the data of the animation. Keyframes are responsible for referencing 
a shape’s state at a specific time of the animation. The animation model has a list of animated 
shapes. The animated shape consists of a shape objects and a list of action objects related to that 
shape. The action represents possible animation effects to the shape such as changing the x and y 
coordinates, changing dimensions, and changing color. To show a change in the shape, we recorded 
the shape’s beginning and ending states for color, position, size, and time of action occurrence. 

Most thought was put into our design choices for restrictive access. For instance, our model gives 
public access to the state of the shape(s) at any given tick, and the shape(s) at the current state 
once a tick is applied. There is also public access from the model to add a shape, remove a shape, 
add an action to a shape, and remove an action from the shape, where validation of the change is 
handled internally. We also provide a restricted interface of a model that can be read by the view,
in addition to a model that can be updated by the controller.

All types of shapes extend an abstract shape class (AShape) with common fields like id, position, 
color, height, width and tick. AnimatedShape has an AShape and a list of actions. 
An instance of AShape represents the shape and its state at a point in time. An AnimatedShape is 
an entire shape in an animation and can provide a shape and its state at any given time.

An Action class had start and end properties. The action class can provide all properties at any 
given tick, calculated using some basic math. Our AnimatedShape class keeps a list of actions per 
shape, and can retrieve a state at a given tick (from Action) and apply it to the shape.

<h3>Our View</h3>

Our view is provided a read-only model to display in different forms. We decided to provide a 
hierarchy of views. IAnimationView is the highest-level view, extended by TextualView and 
VisualView. TextualView offers public access to methods shared by SVGView and TextView. VisualView 
offers public access necessary for the controller to interact with the JFrame. Our current 
implementation of IAnimationView has no methods because we could not find common methods among 
our TextualView and VisualView. However, we kept the interface for use in the future. 

<h3>Our Controller</h3>

Our controller relays information between the model and the view. The model provided to the controller
is muutable due to fact that editing of the animation is enabled. The controller implements Action 
Listeners to listen to events happening in the view. When actions are performed the controller is able
to make the specific changes to the animation. 

The actions available to the user delegated by the Controller :
* Start
* Pause
* Restart
* Enable Looping
* Disable Looping
* Add Oval
* Add Rectangle
* Load File
* Save to File

<h3>Changes to the Model in Assignment 6</h3>
<u>Interfaces added</u>
* ReadOnlyAnimationOperations

<u>Public methods added (readonly interface):</u>
* getMotionsAppliedToShape(String id, int currentTick)
* getLastTick()
* getAnimatedShape(String id)
* getCanvasStartingY()
* getCanvasStartingX()
* getCanvasWidth()
* getCanvasHeight()
* getAnimatedShapes()

<u>Public methods added (updatable interface):</u>
* removeAction(String id, int tick)
* removeShape(String id)

We added these methods because we needed to make the canvas dimensions and shape actions more 
accessible in order to implement the view. We also added a readonly interface for the model
so that the view had restricted access and could not make actual changes to the model. We were also
careful to return only copies of shapes so that they could not be mutated from outside the model.

<h3>Changes to the Model in Assignment 7</h3>
Previously, the object Keyframe lived in the model package but we moved it to the controller 
package where it was more relevant. The AnimatedShape object was change to keep track of a list of 
Keyframes. AnimatedShapes could now be constructed with either a list of action or a list of 
Keyframes. To enable editing of this list of keyframes stored in AnimatedShape, we created public 
addKeyFrame() and removeKeyFrame() methods in Animated Shape. Then, these methods were accessed in 
the model's own addKeyFrame() and removeKeyFrame() methods. With this functionality, we would be 
let the users make changes directly into the model from our Animator.

We also made a minor change to allow the canvas dimensions to be negative.

<h3>Changes to the View in Assignment 7</h3>
To accommodate a more interactive view for the user, we added an InteractiveView extending JFrame 
and implementing an IInteractiveView interface and ActionListener. This view mainly consisted of 
buttons, text fields, combo boxes, and an AnimationPanel. The JFrame components were given an 
ActionListener to react when triggered. 

The interactive view consisted of two combo boxes. One combo box contained a list of all shapes 
in the animation. When one shape was selected, the second combo box would be populated with all 
of the keyframes associated with that shape. The text fields stored their own value that could be 
sent to the controller when needed. However text fields and buttons related to editing keyframes 
(edit, add, and remove) and changing speed were delegated by the view. Text fields and buttons 
related to the playing of the animation and adding and removing shapes were delegated by the
controller. 

The actions available to the user delegated by the Interactive View :
* Set Speed
* Remove Shape
* Edit Keyframe
* Add Keyframe
* Remove Keyframe
* Save Keyframe (after editing)

<h3>Working with the Provider</h3>

While we were able to set up our own model and controller adapters for the provider's views, we
were unfortunately unable to use the provider's view to properly display our animation. Their views 
were missing key implementation of any feature the animator would have. 

Our providers later clarified that their view was not functioning, so our hybrid view shows a blank 
window, as its panels are never painted. However, we were able to verify that the data being sent to the 
views from our model was accurate. Our new model adapter accurately translates our old model class to 
our providers’ IModel interface.

In our InteractiveNewVisualView class, which extends their interactive view and implements our own 
interactive view interface (so that it can be used by our controller), we removed and re-added their 
NewViewPanel so that we can invoke the addActionListener method. We ignored unnecessary 
reimplementations of methods such as setListeners(), updateComboBoxes(), and getLoadFileName() because 
the provider did not include this functionality in their own views. 

### See our reviews of our providers' code and our experience in <code>./resources/Reviews.pdf</code>