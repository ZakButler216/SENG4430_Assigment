/*
 * Event
 * A class which represents an item in a discrete event simulation of a production line,
 * which keeps track of the time it spends in each queue and the path it takes in the production line
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class Item {

    private List<Double> timeInQueues;
    private double timeEnteredQueue;
    private List<String> path;

    /*
     * Constructor
     * Precondition:
     * None
     * Postconditions:
     * A new Item object is returned and its member variables initialized
     */
    public Item() {
        timeInQueues = new LinkedList<Double>();
        timeEnteredQueue = Double.NaN; //has not entered any queues yet
        path = new LinkedList<String>();
    }

    /*
     * Precondition:
     * The time parameter is a valid Double
     * Postcondition:
     * The value of the parameter is copied to the timeEnteredQueue member variable
     */
    public void enteredQueue(double time) {
        timeEnteredQueue = time;
    }

    /*
     * Precondition:
     * The time parameter is a valid Double,
     * the timeInQueues and timeEnteredQueue member variables have been initialized properly,
     * and there has been a corresponding call to enteredQueue() which does not have leftQueue() called yet
     * Postcondition:
     * The time this Item object spent in the queue is calculated and stored in timeInQueues,
     * and timeEnteredQueue is reset
     */
    public void leftQueue(double time) {
        timeInQueues.add(time - timeEnteredQueue);
        timeEnteredQueue = Double.NaN;
    }

    /*
     * Precondition:
     * The index parameter is a valid Integer, the timeInQueues member variable has been initialized properly,
     * and there exists a value with the given index in timeInQueues
     * Postcondition:
     * The value contained in timeInQueues on the given index is returned
     */
    public double getTimeInQueue(int index) {
        return timeInQueues.get(index);
    }

    /*
     * Preconditions:
     * stageName is a valid String, and the path member variable has been properly initialized
     * Postcondition:
     * The stageName is added to the path member variable.
     */
    public void addStageToPath(String stageName) {
        path.add(stageName);
    }

    /*
     * Precondition:
     * The path member variable has been properly initialized
     * Postcondition:
     * A String containing all the stage names inside the path member variable is returned
     * (format is simply all of them concatenated together, example: "S1S2S3")
     */
    public String getPath() {
        Iterator<String> iter = path.iterator();
        String out = "";
        while (iter.hasNext()) {
            out += iter.next();
        }
        return out;
    }
}
