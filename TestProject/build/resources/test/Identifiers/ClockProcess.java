package Test;

import java.util.*;

public class ClockProcess {
    private String id;
    private int memory;
    private List<Page> pages;
    private List<Page> frames;
    private List<Integer> faults;
    private int releaseTime = 0;
    private int numberOfFaults = 0;
    private int turnaround = 0;

    /**
     * Overloaded Constructor - creates a process which has an ID
     * @param id
     * @param memory
     */
    public ClockProcess(String id, int memory){
        this.id = id;
        this.memory = memory;
        this.pages = new LinkedList<>();
        this.frames = new LinkedList<>();
        this.faults = new LinkedList<>();
    }

    /**
     * Check to see what action to take about process.
     * @param time
     * @return
     */
    public int checkProcess(int time){
        if(pages.isEmpty())
            return -1;
        Page page = pages.remove(0);
        if(checkMemory(page) == true)
            return 0;
        pages.add(0, page);
        addFault(time);
        pageFault(page);
        releaseTime = time + 6;
        return 1;
    }

    /**
     * Adds page of process to list
     * @param p
     */
    public void addPage(int p){
        Page page = new Page(p);
        page.set_clockNotice(1);
        pages.add(page);
    }

    /**
     * Removes the first page of process from list
     * @return
     */
    public Page removePage(){
        if(pages.isEmpty())
            return null;
        Page page = pages.remove(0);
        return page;
    }

    /**
     * Add fault time to faults list
     * @param t
     */
    public void addFault(int t){
        faults.add(t);
        numberOfFaults++;
    }

    /**
     * Checks if page is already in process memory. Returns true if it is.
     * @param page
     * @return
     */
    public boolean checkMemory(Page page){
        return frames.stream().anyMatch((p) -> (p.getNumber() == (page.getNumber())));
    }

    /**
     * If memory is not full, the given page will be added into memory. If memory
     * is full, then the used value of all pages in memory will be compared.
     * The page with the lowest value is the one that has been least recently used.
     * This is therefore the page that will be swapped out.
     * @param page
     */
    public void pageFault(Page page){
        if(frames.size() < memory)
            frames.add(page);
        else{
            Page other = null;
            for(int i = 0; i < frames.size(); i++){
                other = frames.get(i);
                if(other.get_clockNotice() == 1)
                    other.set_clockNotice(0);
            }
            for(int i = 0; i < frames.size(); i++){
                other = frames.get(i);
                if(other.get_clockNotice() == 0)
                    break;
            }
            frames.remove(other);
            frames.add(page);
        }
    }

    /**
     * Returns value of releaseTime
     * @return
     */
    public int getRelease(){
        return releaseTime;
    }

    /**
     * Sets value of releaseTime
     * @param time
     */
    public void setRelease(int time){
        this.releaseTime = time;
    }

    /**
     * Sets value of turnaround
     * @param turn
     */
    public void setTurnaround(int turn){
        this.turnaround = turn;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String faultString = "{";
        for(int i = 0; i < numberOfFaults; i++){
            int fault = faults.remove(0);
            faultString = faultString + fault + ",";
        }
        faultString = faultString + "}";
        return id + "\t " + turnaround + "\t\t\t " + numberOfFaults + "\t\t " + faultString;
    }

    public String getID(){
        return this.id;
    }
}

