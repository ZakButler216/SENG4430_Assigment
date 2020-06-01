/*
 * EndStage
 * An implementation of the Stage abstract class, which will place finished items into a standard Queue<Item> object
 * instead of an InterStageQueue object
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class EndStage extends Stage {
	
	private InterStageQueue in;
	private Queue<Item> out; //this is assumed to have unlimited capacity
	
	/*
	 * Constructor
	 * Precondition:
	 * All parameters are valid instances of their types, range is not negative, mean is greater than 0,
	 * and range is not greater than 2 * mean (to prevent the calculated time from becoming negative/going back)
	 * Postconditions:
	 * A new BeginStage object is created and its member variables initialized according to the parameters
	 */
	public EndStage(String name, InterStageQueue in, Queue<Item> out, double mean, double range) {
		this.in = in;
		this.out = out;
		current = null;
		blocked = false; //never blocked
		starved = true;
		this.mean = mean;
		this.range = range;
		r = new Random();
		timeDone = 0;
		timeInProduction = 0;
		timeBlocked = 0;
		timeStarved = 0;
		timeStartBlocked = Double.NaN; //never blocked
		timeStartStarved = 0; //start off starved, so 0
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
	 * If the input InterStageQueue is empty:
	 * The stage becomes starved, and null is returned
	 * Otherwise:
	 * The Item object in current is transferred to the output InterStageQueue,
	 * an Item object is taken from the input InterStageQueue and placed in current,
	 * the stage name is added to the path of the Item object from the input InterStageQueue,
	 * the stage becomes unblocked, and a new Event object is returned
	 */
	@Override
	public Event processNextItem(double currentTime) {
		if (currentTime < timeDone) { //still processing
			return null;
		}
		if (starved && in.size() == 0) { //if still can't continue do nothing
			return null;
		}
		if (current != null) { //if not empty push to next queue
			out.offer(current); //output queue will never be full
		}
		if (in.peek() == null) { //if input queue is empty stage becomes starved
			timeStartStarved = currentTime;
			current = null;
			starved = true;
			return null;
		}
		else {
			current = in.poll(currentTime);
			current.addStageToPath(name);
		}
		if (starved) { //if this point is reached and it was starved before update timeStarved statistic and set starved to false
			starved = false;
			timeStarved += currentTime - timeStartStarved;
			timeStartStarved = Double.NaN;
		}
		double timeToProcess = mean + (range * (r.nextDouble() - 0.5));
		timeInProduction += timeToProcess;
		timeDone = currentTime + timeToProcess;
		return new Event(this, currentTime, timeDone);
	}

}
