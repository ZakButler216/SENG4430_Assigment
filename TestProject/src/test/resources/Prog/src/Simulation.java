/*
 * Simulation
 * A class which can run a discrete event simulation of a production line and print some statistics of the simulation
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Simulation {
	private double currentTime;
	private double maxTime;
	private List<InterStageQueue> isqs;
	private Queue<Item> last; //the queue which will contain the Item objects that has finished production
	private List<Stage> stages;
	private PriorityQueue<Event> events;
	private List<String> possiblePaths;
	private List<String> possiblePathNames;
	
	/*
	 * Constructor
	 * The structure of the production line is determined by the constructor,
	 * so to change the structure the constructor implementation needs to be changed/overridden
	 * Precondition:
	 * All the parameters are valid instances of their types
	 * Postconditions:
	 * A Simulation object is created and its member variables initialized according to the parameters
	 */
	public Simulation(int maxQueueSize, double m, double n, double maxTime) {
		currentTime = 0;
		this.maxTime = maxTime;
		isqs = new LinkedList<InterStageQueue>();
		InterStageQueue q01 = new InterStageQueue(maxQueueSize, "Q01");
		isqs.add(q01);
		InterStageQueue q12 = new InterStageQueue(maxQueueSize, "Q12");
		isqs.add(q12);
		InterStageQueue q23 = new InterStageQueue(maxQueueSize, "Q23");
		isqs.add(q23);
		InterStageQueue q34 = new InterStageQueue(maxQueueSize, "Q34");
		isqs.add(q34);
		InterStageQueue q45 = new InterStageQueue(maxQueueSize, "Q45");
		isqs.add(q45);
		last = new LinkedList<Item>();
		stages = new LinkedList<Stage>();
		Stage s0 = new BeginStage("S0", q01, m, n);
		stages.add(s0);
		Stage s1 = new InternalStage("S1", q01, q12, m, n);
		stages.add(s1);
		Stage s2a = new InternalStage("S2a", q12, q23, 2 * m, 2 * n);
		stages.add(s2a);
		Stage s2b = new InternalStage("S2b", q12, q23, m, 0.5 * n);
		stages.add(s2b);
		Stage s3 = new InternalStage("S3", q23, q34, m, n);
		stages.add(s3);
		Stage s4a = new InternalStage("S4a", q34, q45, m, 0.5 * n);
		stages.add(s4a);
		Stage s4b = new InternalStage("S4b", q34, q45, 2 * m, 2 * n);
		stages.add(s4b);
		Stage s5 = new EndStage("S5", q45, last, m, n);
		stages.add(s5);
		s0.addAdjacentStage(s1);
		s1.addAdjacentStage(s0);
		s1.addAdjacentStage(s2b); //in case there is only 1 item in the input ISQ, and both s2a and s2b are available, prioritize s2b, since it is typically faster
		s1.addAdjacentStage(s2a);
		s2a.addAdjacentStage(s1);
		s2a.addAdjacentStage(s3);
		s2b.addAdjacentStage(s1);
		s2b.addAdjacentStage(s3);
		s3.addAdjacentStage(s2b); //same as above
		s3.addAdjacentStage(s2a);
		s3.addAdjacentStage(s4a); //s4a is typically faster this time, so prioritize it
		s3.addAdjacentStage(s4b);
		s4a.addAdjacentStage(s3);
		s4a.addAdjacentStage(s5);
		s4b.addAdjacentStage(s3);
		s4b.addAdjacentStage(s5);
		s5.addAdjacentStage(s4a); //same as above
		s5.addAdjacentStage(s4b);
		events = new PriorityQueue<Event>();
		possiblePaths = new LinkedList<String>();
		possiblePaths.add("S0S1S2aS3S4aS5");
		possiblePaths.add("S0S1S2aS3S4bS5");
		possiblePaths.add("S0S1S2bS3S4aS5");
		possiblePaths.add("S0S1S2bS3S4bS5");
		possiblePathNames = new LinkedList<String>();
		possiblePathNames.add("S2a-S4a");
		possiblePathNames.add("S2a-S4b");
		possiblePathNames.add("S2b-S4a");
		possiblePathNames.add("S2b-S4b");
	}
	
	/*
	 * Preconditions:
	 * All member variables have been initialized properly, and runSimulation() has not been called before
	 * Postconditions:
	 * The simulation will run until the time limit, and the member variables will be updated accordingly
	 */
	public void runSimulation() {
		events.add(stages.get(0).processNextItem(currentTime)); //first processed item
		while (true) {
			Event e = events.poll(); //get the next event
			if (e.getTimeReady() >= maxTime) { //if we are going to exceed the time limit break the loop
				break;
			}
			else { //otherwise update the time
				currentTime = e.getTimeReady();
			}
			Event e2 = e.getStage().processNextItem(currentTime); //try to get the current stage to process the next item
			if (e2 != null) { //if the stage did start processing the next item add that event to the priority queue
				events.add(e2);
			}
			Stage[] adjacentStages = e.getStage().getAdjacentStages();
			for (Stage stage : adjacentStages) { //try to get the adjacent stages to start processing the next item as well
				e2 = stage.processNextItem(currentTime);
				if (e2 != null) { //if they can then add those events to the priority queue as well
					events.add(e2);
				}
			}
		}
	}
	
	/*
	 * Preconditions:
	 * All member variables have been initialized properly, and runSimulation() has been called before
	 * Postconditions:
	 * The statistics of the production line after the simulation will be printed to standard output
	 */
	public void printStatistics() {
		System.out.format("%-8s%-20s%-20s%-20s\n", "Stage", "Production (%)", "Starve (t)", "Block (t)");
		for (Stage stage : stages) { //print the table of statistics for the stages
			System.out.format("%-8s%-20s%-20s%-20s\n", stage.getName(), stage.getProductionTimePercentage(maxTime), stage.getTimeStarved(maxTime), stage.getTimeBlocked(maxTime));
		}
		System.out.println();
		System.out.format("%-8s%-20s%-20s\n", "ISQ", "Average (t)", "Average (items)");
		int counter = 0;
		for (InterStageQueue isq : isqs) { //print the table of statistics for the InterStageQueues
			double avgTimeOfItemInQueue = 0;
			for (Item item : last) { //get the total time Item objects spent in the queue
				avgTimeOfItemInQueue += item.getTimeInQueue(counter);
			}
			avgTimeOfItemInQueue /= last.size(); //divide the total by the number of Item objects to get the average
			System.out.format("%-8s%-20s%-20s\n", isq.getName(), avgTimeOfItemInQueue, isq.getAvgNoOfItems(maxTime));
			counter++;
		}
		System.out.println();
		List<Integer> numberOfItemsPerPath = new LinkedList<Integer>();
		for (String possiblePath : possiblePaths) { //count the number of items per possible path
			counter = 0;
			for (Item item : last) {
				if (item.getPath().equals(possiblePath)) {
					counter++;
				}
			}
			numberOfItemsPerPath.add(counter);
		}
		System.out.format("%-12s%-20s\n", "Path", "Total Items");
		counter = 0;
		for (int noOfItems : numberOfItemsPerPath) { //print the table of statistics for the possible paths
			System.out.format("%-12s%-20s\n", possiblePathNames.get(counter), noOfItems);
			counter++;
		}
		System.out.println("\nTotal output: " + last.size());
	}
}
