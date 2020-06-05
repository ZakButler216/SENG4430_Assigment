/*
 * Stage
 * An abstract class which represents a stage in a production line
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */

import java.util.List;
import java.util.Random;

public abstract class Stage {
	
	protected Item current;
	protected boolean blocked;
	protected boolean starved;
	protected double mean, range;
	protected Random r;
	protected double timeDone;
	protected double timeInProduction;
	protected double timeStarved;
	protected double timeBlocked;
	protected double timeStartBlocked;
	protected double timeStartStarved;
	protected List<Stage> adjacentStages;
	protected String name;

	/*
	 * An abstract method that must be implemented by concrete subclasses
	 * Preconditions:
	 * The currentTime parameter is a valid Double that is not negative
	 * Postconditions:
	 * An Item moves out of current into an output, another Item is placed inside current,
	 * and the other member variables are updated accordingly
	 */
	public abstract Event processNextItem(double currentTime);
	
	/*
	 * Preconditions:
	 * The timeInProduction and timeDone member variables have been initialized properly,
	 * and endTime is a valid Double that is not negative
	 * Postconditions:
	 * A Double is returned which represents the percentage of time spent in production out of the total time (which is endTime)
	 */
	public double getProductionTimePercentage(double endTime) {
		double timeInProductionUntilEnd = timeInProduction;
		if (timeDone > endTime) {
			timeInProductionUntilEnd -= timeDone - endTime;
		}
		return (timeInProductionUntilEnd / (timeInProduction + this.getTimeBlocked(endTime) + this.getTimeStarved(endTime))) * 100;
	}
	
	/*
	 * Preconditions:
	 * The starved, timeStarved and timeStartStarved member variables have been initialized properly
	 * Postcondition:
	 * The time the stage spent starved up until currentTime is returned
	 */
	public double getTimeStarved(double currentTime) {
		if (starved) {
			return timeStarved + (currentTime - timeStartStarved);
		}
		return timeStarved;
	}
	
	/*
	 * Preconditions:
	 * The blocked, timeBlocked and timeStartBlocked member variables have been initialized properly
	 * Postcondition:
	 * The time the stage spent blocked up until currentTime is returned
	 */
	public double getTimeBlocked(double currentTime) {
		if (blocked) {
			return timeBlocked + (currentTime - timeStartBlocked);
		}
		return timeBlocked;
	}
	
	/*
	 * Precondition:
	 * The adjacentStages member variable has been initialized properly, and the stage parameter is a valid Stage
	 * Postcondition:
	 * The parameter is added into the adjacentStages member variable
	 */
	public void addAdjacentStage(Stage stage) {
		adjacentStages.add(stage);
	}
	
	/*
	 * This method returns an array to avoid modification of the original list from outside (except using addAdjacentStage())
	 * Precondition:
	 * The adjacentStages member variable has been initialized properly
	 * Postcondition:
	 * An array which contains all the elements of adjacentStages is returned
	 */
	public Stage[] getAdjacentStages() {
		return adjacentStages.toArray(new Stage[adjacentStages.size()]);
	}
	
	/*
	 * Precondition:
	 * The name member variable has been properly initialized
	 * Postcondition:
	 * The value of the name member variable is returned
	 */
	public String getName() {
		return name;
	}
}
