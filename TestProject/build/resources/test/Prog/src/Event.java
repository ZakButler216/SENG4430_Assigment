/*
 * Event
 * A class which represents an event in a discrete event simulation of a production line,
 * which contains the stage that created the event, the time the event was created, and the time the event is ready
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
public class Event implements Comparable<Event> {

	private Stage stage;
	private double timeCreated;
	private double timeReady;
	
	/*
	 * Constructor
	 * Precondition:
	 * All input parameters are valid instances of their types
	 * Postconditions:
	 * A new Event object is returned and its member variables initialized according to the parameters
	 */
	public Event(Stage stage, double timeCreated, double timeReady) {
		this.stage = stage;
		this.timeCreated = timeCreated;
		this.timeReady = timeReady;
	}
	
	/*
	 * Precondition:
	 * The timeReady member variable has been initialized properly
	 * Postcondition:
	 * The value of the timeReady member variable is returned
	 */
	public double getTimeReady() {
		return timeReady;
	}
	
	/*
	 * Precondition:
	 * The stage member variable has been initialized properly
	 * Postcondition:
	 * The value of the stage member variable is returned
	 */
	public Stage getStage() {
		return stage;
	}
	
	/*
	 * Precondition:
	 * The timeCreated member variable has been initialized properly
	 * Postcondition:
	 * The value of the timeCreated member variable is returned
	 */
	public double getTimeCreated() {
		return timeCreated;
	}
	
	/*
	 * Implementation of compareTo from the Comparable<Event> interface
	 * Preconditions:
	 * The parameter is a valid Event object,
	 * and the getTimeReady method of both Event objects (this and the parameter) returns valid Doubles
	 */
	@Override
	public int compareTo(Event right) {
		if (getTimeReady() < right.getTimeReady()) {
			return -1;
		}
		else if (getTimeReady() > right.getTimeReady()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}
