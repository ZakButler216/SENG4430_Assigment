/*
 * BeginStage
 * An implementation of the Stage abstract class, which will create Item objects instead of taking them from an input queue
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
import java.util.LinkedList;
import java.util.Random;

public class BeginStage extends Stage {

	private InterStageQueue out; //this implementation of Stage only has an output queue
	
	/*
	 * Constructor
	 * Precondition:
	 * All parameters are valid instances of their types, range is not negative, mean is greater than 0,
	 * and range is not greater than 2 * mean (to prevent the calculated time from becoming negative/going back)
	 * Postconditions:
	 * A new BeginStage object is created and its member variables initialized according to the parameters
	 */
	public BeginStage(String name, InterStageQueue out, double mean, double range) {
		this.out = out;
		current = null;
		blocked = false;
		starved = false; //never starved
		this.mean = mean;
		this.range = range;
		r = new Random();
		timeDone = 0;
		timeInProduction = 0;
		timeBlocked = 0;
		timeStarved = 0;
		timeStartBlocked = Double.NaN; //not a number when it starts because the stage does not start off blocked
		timeStartStarved = Double.NaN; //the stage starts off starved, so start at 0
		adjacentStages = new LinkedList<Stage>();
		this.name = name;
	}
	
	/*
	 * Implementation of processNextItem from the Stage abstract class
	 * Preconditions:
	 * All member variables have been properly initialized, and currentTime is a valid Double that is not negative
	 * Postconditions:
	 * If currentTime is less than timeDone (so it is not done yet):
	 * null is returned
	 * If the output InterStageQueue is full:
	 * The stage becomes blocked, and null is returned
	 * Otherwise:
	 * The Item object in current is transferred to the output queue, a new Item object is created and placed in current,
	 * the stage name is added to the path of the new Item object, the stage becomes unblocked,
	 * and a new Event object is returned
	 */
	@Override
	public Event processNextItem(double currentTime) {
		if (currentTime < timeDone) { //if still processing
			return null;
		}
		if (blocked && out.remainingCapacity() == 0) { //if still can't continue do nothing
			return null;
		}
		if (current != null) { //if not empty push to next queue
			if (!out.offer(current, currentTime)) { //next queue full, so blocked
				timeStartBlocked = currentTime;
				blocked = true;
				return null;
			}
		}
		current = new Item(); //as beginning stage just create new item
		current.addStageToPath(name);
		if (blocked) { //if this point is reached and it was blocked before update timeBlocked statistic and set blocked to false
			blocked = false;
			timeBlocked += currentTime - timeStartBlocked;
			timeStartBlocked = Double.NaN;
		}
		double timeToProcess = mean + (range * (r.nextDouble() - 0.5));
		timeInProduction += timeToProcess;
		timeDone = currentTime + timeToProcess;
		return new Event(this, currentTime, timeDone);
	}

}
