/*
 * InterStageQueue
 * A class which acts as a queue for Item objects with a maximum size limit,
 * and keeps track of the average number of items inside the InterStageQueue
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InterStageQueue {

	private BlockingQueue<Item> queue; //BlockingQueue is used here so we can enforce maximum size limit and use remainingCapacity()
	private double avgNoOfItems;
	private double currentTime;
	private String name;
	
	/*
	 * Constructor
	 * Precondition:
	 * All input parameters are valid instances of their types
	 * Postconditions:
	 * A new InterStageQueue object is returned and its member variables initialized according to the parameters
	 */
	public InterStageQueue(int max, String name) {
		avgNoOfItems = 0;
		currentTime = 0;
		queue = new LinkedBlockingQueue<Item>(max);
		this.name = name;
	}
	
	/*
	 * Precondition:
	 * The queue member variable has been initialized properly
	 * Postconditions:
	 * The Item object at the head of the queue is returned (null if the queue is empty)
	 */
	public Item peek() {
		return queue.peek();
	}
	
	/*
	 * Preconditions:
	 * The queue member variable has been initialized properly, and the parameters are valid instances of their types
	 * Postconditions:
	 * If the queue is not full:
	 * The Item object in the parameter is added to the tail of the queue,
	 * the average number of items statistic is updated, enteredQueue() is called on the Item object, and true is returned
	 * If the queue is full:
	 * false is returned, and the Item object is not added to the queue
	 */
	public boolean offer(Item e, double currentTime) {
		boolean out = queue.offer(e);
		if (out) {
			avgNoOfItems += (currentTime - this.currentTime) * (queue.size() - 1);
			this.currentTime = currentTime;
			e.enteredQueue(currentTime);
		}
		return out;
	}
	
	/*
	 * Precondition:
	 * The queue member variable has been initialized properly
	 * Postconditions:
	 * If the queue is not empty:
	 * The Item object at the head of the queue is removed and returned,
	 * the average number of items statistic is updated, and leftQueue() is called on the Item object
	 * If the queue is empty:
	 * null is returned
	 */
	public Item poll(double currentTime) {
		Item out = queue.poll();
		if (out != null) {
			avgNoOfItems += (currentTime - this.currentTime) * (queue.size() + 1);
			this.currentTime = currentTime;
			out.leftQueue(currentTime);
		}
		return out;
	}
	
	/*
	 * Precondition:
	 * The currentTime parameter is a valid Double, and all member variables have been properly initialized
	 * Postcondition:
	 * The average number of items up to currentTime is returned
	 */
	public double getAvgNoOfItems(double currentTime) {
		double tempAvg = avgNoOfItems + (currentTime - this.currentTime) * queue.size();
		tempAvg /= currentTime;
		return tempAvg;
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
	
	/*
	 * Precondition:
	 * The queue member variable has been properly initialized
	 * Postcondition:
	 * The number of additional items the queue can accept is returned
	 */
	public int remainingCapacity() {
		return queue.remainingCapacity();
	}

	/*
	 * Precondition:
	 * The queue member variable has been properly initialized
	 * Postcondition:
	 * The number of items in the queue is returned
	 */
	public int size() {
		return queue.size();
	}
	
}
