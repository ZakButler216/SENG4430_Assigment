public class PA3 {
	
	public static void main(String[] args) {
		int maxQueueSize;
		double m, n;
		if (args.length == 3) {
			m = Double.parseDouble(args[0]);
			n = Double.parseDouble(args[1]);
			maxQueueSize = Integer.parseInt(args[2]);
		}
		else {
			System.out.println("Invalid number of parameters");
			return;
		}
		Simulation sim = new Simulation(maxQueueSize, m, n, 10000000);
		sim.runSimulation();
		sim.printStatistics();
	}
}
