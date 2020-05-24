package cyclomatic_complexity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;

public class CC_TCTwo_UserBean implements java.io.Serializable{

    static class WorkloadComparator implements Comparator<CC_TCTwo_UserBean> {

        public int compare(CC_TCTwo_UserBean staffA, CC_TCTwo_UserBean staffB) {
            if(staffA.getWorkloadBasedOnPriority()<staffB.getWorkloadBasedOnPriority()) {
                return -1;
            } else if (staffA.getWorkloadBasedOnPriority()>staffB.getWorkloadBasedOnPriority()) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    private String name;
    private String address;
    private String contactNumber;
    private String password;

    private String staffID;
    private String rolesToDo;

    private Integer[] assignedIncidentsID;

    private int failedLoginCounter;
    boolean locked;


    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean lock) {
        locked=lock;
    }

    public int getFailedLoginCounter() {
        return failedLoginCounter;
    }

    public void setFailedLoginCounter(int failedLogin) {
        this.failedLoginCounter = failedLogin;
    }

    public void resetFailedLoginCounter() {
        failedLoginCounter=0;
    }

    public void addFailedLoginCounter() {
        failedLoginCounter++;

        if(failedLoginCounter==3) {
            lockAccount();

        }
    }

    public void lockAccount() {
        locked=true;
        failedLoginCounter=0;
    }

    public void addAssignedIncidentsID(int id) {

        //convert array to linked list
        List<Integer> storeAssignedIncidentsID = new LinkedList<Integer>(Arrays.asList(assignedIncidentsID));

        //add new incident assigned
        storeAssignedIncidentsID.add(id);

        //convert linked list to array
        assignedIncidentsID = storeAssignedIncidentsID.toArray(new Integer[storeAssignedIncidentsID.size()]);

    }

    public void removeAssignedIncidentsID(int id) {

        //initialize new linked list
        List<Integer> assignedListAfterRemoved = new LinkedList<Integer>();

        //put all currently assigned incidents into the linked list, except for the one to be removed
        for(int i=0;i<assignedIncidentsID.length;i++) {

            if(!assignedIncidentsID[i].equals(id)) {
                assignedListAfterRemoved.add(assignedIncidentsID[i]);
            }
        }

        //convert linked list to array
        assignedIncidentsID=assignedListAfterRemoved.toArray(new Integer[assignedListAfterRemoved.size()]);
    }

    public int getAmountOfIncidentsAssigned() {

        return assignedIncidentsID.length;
    }

    public int[] getAssignedIncidentsID() {

        int[] printAssignedIncidents = new int[assignedIncidentsID.length];

        for(int i=0;i<assignedIncidentsID.length;i++) {
            printAssignedIncidents[i]=assignedIncidentsID[i];
        }

        return printAssignedIncidents;
    }

    public int getWorkloadBasedOnPriority() {
        int lowPriorityIncidents,mediumPriorityIncidents,highPriorityIncidents,workload;

        lowPriorityIncidents=0;
        mediumPriorityIncidents=0;
        highPriorityIncidents=0;

        CC_TCTwo_IncidentBean.Priority incidentPriority;

        int[] assignedIncidents=getAssignedIncidentsID();

        for(int i=0;i<assignedIncidents.length;i++) {
            incidentPriority = CC_TCTwo_IncidentDAO.getIncidentByIncidentID(assignedIncidents[i]).getPriorityRating();

            innerloop:
            switch(incidentPriority) {
                case Low:
                    lowPriorityIncidents++;
                    break innerloop;

                case Medium:
                    mediumPriorityIncidents++;
                    break innerloop;

                case High:
                    highPriorityIncidents++;
                    break innerloop;
            }
        }

        workload = lowPriorityIncidents*1 + mediumPriorityIncidents*3 + highPriorityIncidents*5;

        return workload;
    }


    /**
     private boolean ratedStrategy;

     public boolean isRatedStrategy() {
     return ratedStrategy;
     }

     public void setRatedStrategy() {
     ratedStrategy = true;
     }
     */

    public String getRolesToDo() {
        String roles;

        if(rolesToDo.isBlank()) {
            roles="No roles assigned.";
        } else {
            roles=rolesToDo;
        }

        return roles;
    }

    public void setRolesToDo(String roles) {

        this.rolesToDo = roles;
    }




    public Position getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(Position userPosition) {
        this.userPosition = userPosition;
    }


    private Position userPosition;

    //very simple
    //name etc

    public CC_TCTwo_UserBean() {
        rolesToDo="";
        //ratedStrategy=false;
        //password="password";
        assignedIncidentsID = new Integer[0];

        failedLoginCounter=0;
        locked=false;

    }


    public enum Position {
        Branch_Manager {
            public String toString() {
                return "Branch Manager";
            }
        },
        Data_Processing_Officer {
            public String toString() {
                return "Data Processing Officer";
            }
        },
        IT {
            public String toString() {
                return "IT";
            }
        },
        Financial_Analyst {
            public String toString() {
                return "Financial Analyst";
            }
        },
        Internal_Auditor {
            public String toString() {
                return "Internal Auditor";
            }
        };
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String id) {

        this.staffID=id;

    }

    public String getName() {
        return name;
    }

    public void setName(String nm) {
        this.name = nm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String add) {
        this.address = add;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String number) {
        this.contactNumber = number;
    }

    public Position getPosition() {
        return userPosition;
    }


    public void setPosition(Position p) {
        this.userPosition = p;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;
    }

}
