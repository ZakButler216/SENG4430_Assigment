package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

    public class testClass1 {
        private static int processId = 1;   //for process id
        private static int memory = 0;   //memory space for each process
        private static int time = 0;
        private static int quantum = 0;
        //queues for ready and blocked processes
        private static Queue<Process> lruReady;
        private static Queue<Process> lruBlocked;
        private static Queue<Process> lruFinished;
        private static Queue<ClockProcess> clockReady;
        private static Queue<ClockProcess> clockBlocked;
        private static Queue<ClockProcess> clockFinished;

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            //initialise all queues
            lruReady = new LinkedList<>();
            clockReady = new LinkedList<>();
            lruBlocked = new LinkedList<>();
            clockBlocked = new LinkedList<>();
            lruFinished = new LinkedList<>();
            clockFinished = new LinkedList<>();
            //amount of memory space each process will get
            int total = args.length;
            memory = 30/total;

            for (String arg : args) {
                openFile(arg, processId);
                processId++;
            }

            //start running processes using LRU algorithm
            runLRU();

            //print results
            System.out.println("LRU - Fixed:");
            System.out.println("PID \t Turnaround Time \t # Faults \t Fault Times");
            int x = 1;
            String y;
            while(!lruFinished.isEmpty()){
                y = "P" + String.valueOf(x);
                Process process = lruFinished.remove();
                if(process.getID().equals(y)){
                    String processString = process.toString();
                    System.out.println(processString);
                    x++;
                }
                else{
                    lruFinished.add(process);
                }
            }

            System.out.println("\n----------------------------------------------------\n");

            //reset time to run clock
            time = 0;
            runClock();

            //print results
            System.out.println("Clock - Fixed:");
            System.out.println("PID \t Turnaround Time \t # Faults \t Fault Times");
            x = 1;
            while(!clockFinished.isEmpty()){
                y = "P" + String.valueOf(x);
                ClockProcess process = clockFinished.remove();
                if(process.getID().equals(y)){
                    String processString = process.toString();
                    System.out.println(processString);
                    x++;
                }
                else{
                    clockFinished.add(process);
                }
            }

            System.exit(0);
        }

        /**
         * Opens an external file and collects all integers from that file
         * @param fileName
         * @param i
         */
        public static void openFile(String fileName, int i){
            Scanner inputStream;
            int next;   //for input stream
            boolean eof = false;    //for end of file
            Process lruProcess = null;
            ClockProcess clockProcess = null;
            //process id
            String id = String.valueOf(i);
            id = "P" + id;

            try{
                inputStream = new Scanner(new File(fileName));
                //create process
                lruProcess = new Process(id, memory);
                clockProcess = new ClockProcess(id,memory);
                //all integers in the file indicate pages. these will now
                //be extracted and entered into the process
                while(inputStream.hasNext()){
                    //keep getting next until next is an int
                    while(!inputStream.hasNextInt()){
                        //if file does not have next then we are at the end of the
                        //file. need to break out of while loops and stop reading
                        //from file.
                        if(!inputStream.hasNext()){
                            eof = true;
                            break;
                        }
                        inputStream.next();
                    }
                    if(eof == true)
                        break;
                    next = inputStream.nextInt();
                    lruProcess.addPage(next);
                    clockProcess.addPage(next);
                }

            }
            catch(FileNotFoundException e){
                System.out.println("This file does not exist.");
                System.exit(0);
            }
            lruReady.add(lruProcess);
            clockReady.add(clockProcess);
        }

        public static void runLRU(){
            //if ready queue and blocked queue are both empty, exit runLRU method
            if(lruReady.isEmpty() && lruBlocked.isEmpty())
                return;
            //check blocked processes. if ready to be unblocked, can be moved to ready queue
            int size = lruBlocked.size();
            do{
                for(int j = 0; j < size; j++){
                    Process process = lruBlocked.remove();
                    if(process.getRelease() <= time)
                        lruReady.add(process);
                    else
                        lruBlocked.add(process);
                }
                if(lruReady.isEmpty())
                    time++;
            }while(lruReady.isEmpty());
            boolean runAgain = true;
            //get first process from ready queue
            Process process = lruReady.remove();
            //check if process has a page fault (returns 1), out of pages (returns -1) or if page is already in memory (returns 0)int c = process.checkProcess(time);
            while(runAgain){
                int c = process.checkProcess(time);
                switch(c){
                    case -1: process.setTurnaround(time);
                        lruFinished.add(process);
                        runAgain = false;
                        quantum = 0;
                        break;
                    case 0:  time++;
                        quantum++;
                        if(quantum == 3){
                            lruReady.add(process);
                            quantum = 0;
                            runAgain = false;
                        }
                        break;
                    case 1:  lruBlocked.add(process);
                        runAgain = false;
                        quantum = 0;
                        break;
                }
            }
            runLRU();
        }

        public static void runClock(){
            //if ready queue and blocked queue are both empty, exit runClock method
            if(clockReady.isEmpty() && clockBlocked.isEmpty())
                return;
            //check blocked processes. if ready to be unblocked, can be moved to ready queue
            int size = clockBlocked.size();
            do{
                for(int j = 0; j < size; j++){
                    ClockProcess process = clockBlocked.remove();
                    if(process.getRelease() <= time)
                        clockReady.add(process);
                    else
                        clockBlocked.add(process);
                }
                if(clockReady.isEmpty())
                    time++;
            }while(clockReady.isEmpty());
            boolean runAgain = true;
            //get first process from ready queue
            ClockProcess process = clockReady.remove();
            //check if process has a page fault (returns 1), out of pages (returns -1) or if page is already in memory (returns 0)int c = process.checkProcess(time);
            while(runAgain){
                int c = process.checkProcess(time);
                switch(c){
                    case -1: process.setTurnaround(time);
                        clockFinished.add(process);
                        runAgain = false;
                        quantum = 0;
                        break;
                    case 0:  if(quantum == 3){
                        clockReady.add(process);
                        quantum = 0;
                        runAgain = false;
                    }
                    else{
                        time++;
                        quantum++;
                    }
                        break;
                    case 1:  clockBlocked.add(process);
                        runAgain = false;
                        quantum = 0;
                        break;
                }
            }
            runClock();
        }
    }


