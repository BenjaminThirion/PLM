package lessons.welcome.traversal.line;

import jlm.universe.bugglequest.SimpleBuggle;

public class TraversalByLineEntity extends SimpleBuggle {
	/* BEGIN TEMPLATE */
	/* BEGIN SOLUTION */

	public void nextStep() {
		int x=getX();
		int y=getY();
		if (x < getWorldWidth()-1) {
			x++;
		} else {
			x = 0; 
			if (y < getWorldHeight()-1) {
				y++; 
			} else {
				y = 0;
			}
		}
		setPos(x,y);
	}

	public boolean endingPosition() {
		return (getX() == getWorldWidth()-1) && (getY() == getWorldHeight()-1);
	}


	@Override
	public void run() {
		int cpt=0;
		do {
			writeMessage(cpt);
			nextStep();
			cpt++;
		} while (!endingPosition());
		writeMessage(cpt);
	}
	/* END SOLUTION */
	/* END TEMPLATE */	

	@Override
	public void forward()  {
		if (isInited())
			throw new RuntimeException("forward() forbidden in this exercise");
	}
	@Override
	public void forward(int step)  {
		if (isInited())
			throw new RuntimeException("forward(int step) forbidden in this exercise");
	}
	@Override
	public void backward()  {
		if (isInited())
			throw new RuntimeException("backward() forbidden in this exercise");
	}
	@Override
	public void backward(int step)  {
		if (isInited())
			throw new RuntimeException("backward(int step) forbidden in this exercise");
	}
	@Override
	public void left()  {
		if (isInited())
			throw new RuntimeException("left() forbidden in this exercise");
	}
	@Override
	public void right()  {
		if (isInited())
			throw new RuntimeException("right() forbidden in this exercise");
	}
	@Override
	public void back()  {
		if (isInited())
			throw new RuntimeException("back() forbidden in this exercise");
	}
	@Override
	public boolean isFacingWall()  {
		if (isInited())
			throw new RuntimeException("isFacingWall() forbidden in this exercise");
		return false;
	}
	@Override
	public boolean isBackingWall()  {
		if (isInited())
			throw new RuntimeException("isBackingWall() forbidden in this exercise");
		return false;
	}
}
