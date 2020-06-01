package Test;

public class Page {
    private int number;
    private int lruNotice;
    private int clockNotice;

    /**
     * Overloaded constructor
     * @param number
     */
    public Page(int number){
        this.number = number;
        this.lruNotice = 0;
        this.clockNotice = 0;
    }

    /**
     * Compares two pages with each other based on their lruNotice value. Returns
     * the page with the lowest lruNotice value.
     * @param other
     * @return
     */
    public Page compareTo(Page other){
        if(this.lruNotice < other.lruNotice)
            return this;
        return other;
    }

    /**
     * Returns value of number
     * @return
     */
    public int getNumber(){
        return this.number;
    }

    /**
     * Increments value of lruNotice
     */
    public void increment(){
        this.lruNotice++;
    }

    /**
     * Returns value of lruNotice
     * @return
     */
    public int get_lruNotice(){
        return this.lruNotice;
    }

    /**
     * Set value of clockNotice
     * @param i
     */
    public void set_clockNotice(int i){
        this.clockNotice = i;
    }

    /**
     * Returns value of clockNotice
     * @return
     */
    public int get_clockNotice(){
        return this.clockNotice;
    }
}

