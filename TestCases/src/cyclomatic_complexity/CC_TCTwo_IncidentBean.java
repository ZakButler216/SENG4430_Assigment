package cyclomatic_complexity;

import java.util.*;
import java.io.Serializable;
//import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

//is now a JavaBean
public class CC_TCTwo_IncidentBean implements Serializable{


    //JOIN of IncidentBean and PostIncidentBean
    //incidentID same
    //act as primary key
    //split into two tables coz was tough to read in one big table

    //------------------------------------
    //New phase
    private int incidentID;
    private String userReportedID;//user who reported the incident



    //private UserBean userReportedIncident;//can be converted to String
    private String incidentTitle;
    private Category incidentCategory;
    private int incidentDateOfMonth;
    private String incidentMonth;
    private int incidentYear;
    private String descriptionOfIncident;
    private Priority priorityRating;
    private String[] incidentKeywords;//can be converted to String?
    private Timestamp ts;
    private Status incidentStatus;
    private boolean read;
    private String solutionImplemented;







    //---------------------------------------
    //Assigned phase
    private String incidentLog;
    private String[] staffAssigned;


    //----------------------------------------
    //Fixed phase

    //---------------------------------------
    //Verified phase




    //-------------------------------------------
    //enums

    public enum Status {
        New {
            public String toString() {
                return "New Incident";
            }
        },

        Assigned {
            public String toString() {
                return "Staff Assigned";
            }
        },

        Fixed {
            public String toString() {
                return "Incident fixed";
            }
        },

        Verified {
            public String toString() {
                return "Solution verified";
            }
        },

        Analysis {
            public String toString() {
                return "Undergoing analysis";
            }
        },

        Strategy {
            public String toString() {
                return "Strategy implemented";
            }
        },

        Archived {
            public String toString() {
                return "Archived";
            }
        }
    }

    public enum Category {
        Regulatory_Law {
            public String toString() {
                return "Regulatory Law";
            }
        },
        Cyber_Security {
            public String toString() {
                return "Cyber Security";
            }
        },
        Human_Issues {
            public String toString() {
                return "Human Issues";
            }
        },
        Bank_Equipment {
            public String toString() {
                return "Bank Equipment";
            }
        },
        Bank_Algorithms {
            public String toString() {
                return "Bank Algorithms";
            }
        },
        Other {
            public String toString() {
                return "Other";
            }
        };
    }

    public enum Priority {
        Low, Medium, High;
    }



    public CC_TCTwo_IncidentBean() {

        setTimeStamp();
        setIncidentDateOfMonth();
        setIncidentMonth();
        setIncidentYear();
        staffAssigned= new String[0];
        solutionImplemented="";

    }




    //Methods for New phase
    //-----------------------------------------------------------------------------------------------------------------------------------------

    public String getUserReportedID() {
        return userReportedID;
    }




    public void setUserReportedID(String id) {
        this.userReportedID = id;
    }


    public Status getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(Status status) {
        this.incidentStatus = status;
    }


    public int detectDuplicate() {
        int duplicateID=-1;

        //traverse through Incident Database
        for(int i = 0; i< CC_TCTwo_IncidentDAO.getIncidentsList().size(); i++) {

            //get Incident Category of database incident
            Category databaseIncidentCategory= CC_TCTwo_IncidentDAO.getIncidentsList().get(i).getIncidentCategory();

            //get incident date of database incident
            Timestamp databaseIncidentTimestamp= CC_TCTwo_IncidentDAO.getIncidentsList().get(i).getTimeStamp();
            LocalDateTime databaseIncidentDateTime=databaseIncidentTimestamp.toLocalDateTime();
            LocalDate databaseIncidentDate=databaseIncidentDateTime.toLocalDate();

            //get incident date of current incident
            LocalDateTime incidentDateTime=ts.toLocalDateTime();
            LocalDate incidentDate=incidentDateTime.toLocalDate();

            //compare amount of days between current incident and database incident, to check if it's within a week.
            long days,absoluteDays;
            days=ChronoUnit.DAYS.between(incidentDate, databaseIncidentDate);
            absoluteDays=Math.abs(days);
            boolean isInAWeekRange;
            if(absoluteDays<7) {
                isInAWeekRange=true;
            } else {
                isInAWeekRange=false;
            }

            String[] databaseIncidentKeywords= CC_TCTwo_IncidentDAO.getIncidentsList().get(i).getIncidentKeywords();

            //if incident category matches database incident, and days between both incidents are within a week
            if(incidentCategory==databaseIncidentCategory&&isInAWeekRange==true) {

                //check if there is at least one keyword matching
                outerloop:
                for(int a=0;a<incidentKeywords.length;a++) {

                    for(int b=0;b<databaseIncidentKeywords.length;b++) {

                        //if there is at least one keyword matching
                        if(incidentKeywords[a].equalsIgnoreCase(databaseIncidentKeywords[b])) {

                            //take note of index of orginal incident in database
                            duplicateID= CC_TCTwo_IncidentDAO.getIncidentsList().get(i).getIncidentID();

                            //break whole loop
                            break outerloop;
                        }

                    }

                }


            }

        }

        //returns index of duplicate;
        //no duplicate if -1
        //has duplicate if anything else
        return duplicateID;


    }

    //sets the incident ID
    public void setIncidentID() {
        //at the start of history, incidentCounter is 0
        //incidentCounter is static variable

        //For first incident, incident id is 1. For second incident, incident id is 2. etc etc.
        incidentID= CC_TCTwo_IncidentDAO.getIncidentCounter()+1;

        //Increment the counter by 1
        CC_TCTwo_IncidentDAO.setIncidentCounter(CC_TCTwo_IncidentDAO.getIncidentCounter()+1);

        //creates a PostIncidentBean object, sets it's incidentID to be same as this IncidentBean's incidentID
        //and stores that PostIncidentBean in PostIncidentDAO
        setPostIncident();
    }

    public int getIncidentID() {
        return incidentID;
    }




    /**public String getDateTimeFromInputtedTimeStamp(Timestamp timestamp) {

     LocalDateTime dateTime=	timestamp.toLocalDateTime();

     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

     String printDateTime=dateTime.format(formatter);

     return printDateTime;
     }*/


    public void setTimeStamp() {

        ts = new Timestamp(System.currentTimeMillis());

    }

    public Timestamp getTimeStamp() {
        return ts;
    }

    public int getDifferenceInDaysBetweenTwoTimeStamps(Timestamp tsOne,Timestamp tsTwo) {
        LocalDateTime dateTimeOne = tsOne.toLocalDateTime();
        LocalDateTime dateTimeTwo = tsTwo.toLocalDateTime();

        long diff = ChronoUnit.DAYS.between(dateTimeOne, dateTimeTwo);

        int difference= (int) diff;

        return difference;

    }
    public String getDateTimeFromTimeStamp() {

        LocalDateTime dateTime=	ts.toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String printDateTime=dateTime.format(formatter);

        return printDateTime;
    }

    public void setIncidentTitle(String title) {
        this.incidentTitle=title;
    }

    public String getIncidentTitle() {
        return incidentTitle;
    }

    public CC_TCTwo_UserBean getUserReportedIncident() {
        CC_TCTwo_UserBean user = CC_TCTwo_UserDAO.getUserByStaffID(userReportedID);
        return user;
    }

    public void setUserReportedIncident(CC_TCTwo_UserBean u) {
        String userID = u.getStaffID();
        userReportedID=userID;
    }

    public String[] getIncidentKeywords() {
        return incidentKeywords;
    }

    //gets the incident keywords in a string
    public String getIncidentKeywordsInString() {
        String keywords="";

        int i=0;

        //as long as the keyword is not the last keyword in the incidentKeywords array
        while(i!=(incidentKeywords.length-1)) {

            //append incidentKeywords to the String keywords and add comma
            keywords=keywords+incidentKeywords[i]+", ";
            i++;
        }
        //if it is the last keyword
        //append without adding commas
        keywords=keywords+incidentKeywords[i];

        //return String of keywords
        return keywords;
    }

    public void setIncidentKeywords(String[] keywords) {
        this.incidentKeywords = keywords;
    }

    public Category getIncidentCategory() {
        return incidentCategory;
    }
    public void setIncidentCategory(Category aCategory) {
        this.incidentCategory = aCategory;
    }

    public String getIncidentMonth() {
        return incidentMonth;
    }

    public void setIncidentMonth() {

        LocalDateTime datetime =ts.toLocalDateTime();

        LocalDate date = datetime.toLocalDate();

        incidentMonth=date.getMonth().toString();

    }


    public int getIncidentYear() {
        return incidentYear;
    }

    public void setIncidentYear() {

        LocalDateTime datetime =ts.toLocalDateTime();
        LocalDate date = datetime.toLocalDate();
        incidentYear=date.getYear();

    }
    public int getIncidentDateOfMonth() {
        return incidentDateOfMonth;
    }

    public void setIncidentDateOfMonth() {

        LocalDateTime datetime =ts.toLocalDateTime();
        LocalDate date = datetime.toLocalDate();
        incidentDateOfMonth=date.getDayOfMonth();
    }
    public String getDescriptionOfIncident() {
        return descriptionOfIncident;
    }
    public void setDescriptionOfIncident(String description) {
        this.descriptionOfIncident = description;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //End Methods for New phase


    public void autoAssignStaff() {

        //set certain priority for certain keywords
        String totalKeywords="";
        for(int i=0;i<incidentKeywords.length;i++) {
            totalKeywords = totalKeywords+incidentKeywords[i]+". ";
        }

        //keywords for high priority
        if(totalKeywords.contains("data")||
                totalKeywords.contains("security")) {
            priorityRating = CC_TCTwo_IncidentBean.Priority.High;
        }

        //keywords for medium priority
        else if
        (totalKeywords.contains("algorithm")||
                        totalKeywords.contains("law"))
        {
            priorityRating = CC_TCTwo_IncidentBean.Priority.Medium;
        }

        //keywords for low priority
        else if (totalKeywords.contains("equipment")||
                totalKeywords.contains("upgrade"))
        {
            priorityRating = CC_TCTwo_IncidentBean.Priority.Low;
        }



        ArrayList<CC_TCTwo_UserBean> staffList = CC_TCTwo_UserDAO.getUsersList();
        ArrayList<CC_TCTwo_UserBean> qualifiedForIncident = new ArrayList<>();

        //find all the staff who are qualified for the job
        //add them to the qualifiedForIncident list
        switch(incidentCategory) {

            case Regulatory_Law:

                for(int i=0;i<staffList.size();i++) {

                    if(staffList.get(i).getRolesToDo().contains("Regulatory Law")) {

                        qualifiedForIncident.add(staffList.get(i));

                    }

                }
                break;

            case Cyber_Security:

                for(int i=0;i<staffList.size();i++) {

                    if(staffList.get(i).getRolesToDo().contains("Cyber Security")) {

                        qualifiedForIncident.add(staffList.get(i));

                    }

                }
                break;

            case Human_Issues:

                for(int i=0;i<staffList.size();i++) {

                    if(staffList.get(i).getRolesToDo().contains("Human Issues")) {

                        qualifiedForIncident.add(staffList.get(i));

                    }

                }
                break;

            case Bank_Equipment:

                for(int i=0;i<staffList.size();i++) {

                    if(staffList.get(i).getRolesToDo().contains("Bank Equipment")) {

                        qualifiedForIncident.add(staffList.get(i));

                    }

                }
                break;


            case Bank_Algorithms:

                for(int i=0;i<staffList.size();i++) {

                    if(staffList.get(i).getRolesToDo().contains("Bank Algorithms")) {

                        qualifiedForIncident.add(staffList.get(i));

                    }

                }
                break;

            case Other:

                for(int i=0;i<staffList.size();i++) {

                    if(staffList.get(i).getRolesToDo().contains("Other")) {

                        qualifiedForIncident.add(staffList.get(i));

                    }

                }
                break;

            default:
                break;

        }

        //if there are qualified staff with roles assigned corresponding to the incident's category
        if(qualifiedForIncident.size()>0) {

            //sort qualified staff list according to their workload
            Collections.sort(qualifiedForIncident, new CC_TCTwo_UserBean.WorkloadComparator());

            String assignToStaff="";


            //from the qualified staff list
            switch(priorityRating) {

                //allocate low priority to staff with high workload
                case Low:
                    assignToStaff=qualifiedForIncident.get(qualifiedForIncident.size()-1).getStaffID();
                    break;

                //allocate medium priority to staff with medium workload
                case Medium:

                    int median;
                    median=qualifiedForIncident.size()/2;
                    assignToStaff=qualifiedForIncident.get(median).getStaffID();
                    break;

                //allocate high priority to staff with low workload
                case High:
                    assignToStaff=qualifiedForIncident.get(0).getStaffID();
                    break;

                default:
                    break;

            }

            String[] assignArray = new String[] {assignToStaff};

            //set assigned staff
            setAssignedStaffID(assignArray);

        }

    }


    public String getAssignedStaffIDInString() {
        String total="";
        String append="";
        for(int i=0;i<staffAssigned.length;i++) {
            append=staffAssigned[i];
            total=total+append+". ";
        }

        return total;
    }
    public String[] getAssignedStaffID() {

        return staffAssigned;
    }

    public void setAssignedStaffID(String[] staff) {

        List<String> staffAssignedList = new ArrayList<>();
        List<String> staffList = new ArrayList<>();
        List<String> overlapList = new ArrayList<>();

        //make an arraylist copy of previous staff assigned to incident
        for(int i=0;i<staffAssigned.length;i++) {
            staffAssignedList.add(staffAssigned[i]);
        }

        //make an arraylist copy of updated staff assigned to incident
        for(int i=0;i<staff.length;i++) {
            staffList.add(staff[i]);
        }

        //find which are the staff that overlap in both lists
        //because this means their assignedIncidents don't have to be changed
        for(int i=0;i<staffAssignedList.size();i++) {
            for(int j=0;j<staffList.size();j++) {
                if(staffList.get(j).equals(staffAssignedList.get(i))) {
                    overlapList.add(staffAssignedList.get(i));
                }
            }
        }

        //remove overlapping staff from both previous and updated lists
        staffAssignedList.removeAll(overlapList);
        staffList.removeAll(overlapList);

        //the remaining staff in previous list
        //have assigned incidents removed
        for(int i=0;i<staffAssignedList.size();i++) {
            String userID=staffAssignedList.get(i);
            CC_TCTwo_UserDAO.getUserByStaffID(userID).removeAssignedIncidentsID(incidentID);
        }

        //the remaining staff in updated list
        //have assigned incidents added
        for(int i=0;i<staffList.size();i++) {
            String userID=staffList.get(i);
            CC_TCTwo_UserDAO.getUserByStaffID(userID).addAssignedIncidentsID(incidentID);
        }



        //store the user ids of staff assigned, in the IncidentBean
        staffAssigned=staff;

        if(staffAssigned.length!=0) {
            setIncidentStatus(Status.Assigned);
        }
    }

    //gets the assigned staff name and position
    //like this
    //Bob Smith (Financial Analyst)
    //Alice Diaz (Internal Auditor)
    public String getAssignedStaffNameAndPosition() {

        String total="";

        String staff;

        String name;
        String position;

        //if assigned staff id array has nothing, then no staff assigned
        if(staffAssigned.length==0) {
            total="No staff assigned";

            //else
        } else {

            //traverse through staffAssigned array
            for(int i=0;i<staffAssigned.length;i++) {

                //get the assigned staff id in the array element
                String userId = staffAssigned[i];

                //get that staff's name and position

                CC_TCTwo_UserBean user = CC_TCTwo_UserDAO.getUserByStaffID(userId);

                name=user.getName();
                position = user.getPosition().toString();

                //append staff name and position
                if(i==0) {
                    staff = name+" ("+position+")";
                } else {
                    staff = "<br>"+ name+" ("+position+")";
                }

                //keep combining the assigned staff
                total = total+staff;

            }
        }

        //print out assigned staff
        return total;

    }



    //returns a PostIncidentBean through using this IncidentBean's id
    //because an IncidentBean's incidentID is same as it's corresponding PostIncidentBean's incidentID
    public CC_TCTwo_PostIncidentBean getPostIncident() {
        CC_TCTwo_PostIncidentBean postIncident = new CC_TCTwo_PostIncidentBean();
        postIncident= CC_TCTwo_PostIncidentDAO.getPostIncidentByIncidentID(incidentID);
        return postIncident;
    }

    //adds the PostIncidentBean for this incident. Method called upon creation of this incident.
    public void setPostIncident() {

        CC_TCTwo_PostIncidentBean postIncident = new CC_TCTwo_PostIncidentBean();

        //set the new PostIncidentBean's incidentID to be this IncidentBean's incidentID
        postIncident.setIncidentID(incidentID);

        //add this new PostIncidentBean to the PostIncidentDAO database
        CC_TCTwo_PostIncidentDAO.addPostIncident(postIncident);

    }



    public Priority getPriorityRating() {
        return priorityRating;
    }
    public void setPriorityRating(Priority p) {
        this.priorityRating = p;
    }


    public boolean getRead() {
        return read;
    }

    public void setRead() {
        this.read = true;
    }

    public String getSolutionImplemented() {
        return solutionImplemented;
    }


    public void setSolutionImplemented(String solution) {
        solutionImplemented = solution;
    }


}
