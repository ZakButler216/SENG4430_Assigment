package cyclomatic_complexity;

import java.util.*;
import java.text.*;
//import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

//simulation class to store simulation data
public class CC_TCTwo_Simulation {

    private String rootCauseTargeted;
    private String actionsTaken;
    private String resultsFound;
    private String staff;
    private Timestamp ts;

    public String getStaff() {
        return staff;
    }

    public void setStaff(String name,String position) {
        staff=name+", "+position;
    }



    public CC_TCTwo_Simulation() {

        setTimeStamp();
    }


    public void setTimeStamp() {
        ts = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getTimeStamp() {
        return ts;
    }

    public String getDateTimeFromTimeStamp() {
        LocalDateTime dateTime=	ts.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String printDateTime=dateTime.format(formatter);
        return printDateTime;
    }


    public String getRootCauseTargeted() {
        return rootCauseTargeted;
    }

    public void setRootCauseTargeted(String rootCause) {
        this.rootCauseTargeted = rootCause;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public void setActionsTaken(String actions) {
        this.actionsTaken = actions;
    }

    public String getResultsFound() {
        return resultsFound;
    }

    public void setResultsFound(String results) {
        this.resultsFound = results;
    }
}
