import processing.core.PVector;

/**
 * A button that allow the user to navigate through all the menus
 */
class Button {
	PVector buttonPos; // Amount across the screen the button is located
	PVector buttonSize; // x and y size of the button
	String buttonText; // Text to write into the button

	// Create a new button with given text, position and sizes
	public Button(String text, float x, float y, float xSize, float ySize) {
		buttonPos = new PVector(x, y);
		buttonSize = new PVector(xSize, ySize);
		buttonText = text;
	}

	// Draw the button, depends of the position if the mouse
	void drawButton(MainSketch canvas) {
		PVector buttonLoc = new PVector();
		buttonLoc.x = canvas.width * buttonPos.x;
		buttonLoc.y = canvas.height * buttonPos.y;
		// Make a copy of the buttons size to use so it can be changed temporarily
		PVector curSize = buttonSize.copy();

		// If the mouse isn't on the button, draw it normally
		if (!mouseOnButton(canvas)) {
			canvas.fill(170, 190, 255);
		}
		// If the mouse is on the button, make it darker and large in not pressed
		else {
			canvas.fill(120, 150, 255);
			// If the mouse is not pressed on the button make it temporarily larger
			if (!canvas.mousePressed) {
				curSize.mult(1.12f);
			}
		}
		
		// Draw the rectangle with actually makes up the button
		canvas.rect(buttonLoc.x - curSize.x / 2, buttonLoc.y - curSize.y / 2, curSize.x, curSize.y);

		canvas.fill(0); // Black text
		// The start button has larger text on it
		if (buttonText == "Start") {
			canvas.textSize(curSize.y / 1.3f);
		}
		// If not a start button, make text fit to box size
		else {
			canvas.textSize(curSize.y / 2);
		}

		// Display the text on the button
		canvas.text(buttonText, buttonLoc.x, buttonLoc.y);
	}

	// Return weather the mouse is over the button
	boolean mouseOnButton(MainSketch canvas) {
		return Math.abs(canvas.mouseX - canvas.width * buttonPos.x) < buttonSize.x / 2 && Math.abs(canvas.mouseY - canvas.height * buttonPos.y) < buttonSize.y / 2;
	}
}
