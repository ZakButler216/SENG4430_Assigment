package cyclomatic_complexity;
import java.util.*;

public class CC_TCTwo_IncidentDAO {

    private static int incidentCounter;



    private static ArrayList<CC_TCTwo_IncidentBean> incidentsList;

    static {
        incidentsList = new ArrayList<>();
        incidentCounter=0;
    }

    public CC_TCTwo_IncidentDAO() {

    }


    public static int getIncidentCounter() {
        return incidentCounter;
    }

    public static void setIncidentCounter(int counter) {
        CC_TCTwo_IncidentDAO.incidentCounter = counter;
    }

    public static ArrayList<CC_TCTwo_IncidentBean> getIncidentsList() {
        return incidentsList;
    }


    public static void addIncident(CC_TCTwo_IncidentBean in) {

        //add the incidentBean to Incident database
        incidentsList.add(in);

    }

    public static CC_TCTwo_IncidentBean getIncidentByIncidentID(int id) {
        CC_TCTwo_IncidentBean incident = new CC_TCTwo_IncidentBean();

        for(int i=0;i<incidentsList.size();i++) {
            if(incidentsList.get(i).getIncidentID()==id) {
                incident=incidentsList.get(i);
                break;
            }
        }

        return incident;

    }

    public static void deleteIncidentByIncidentID(int id) {
        for(int i=0;i<incidentsList.size();i++) {
            if(incidentsList.get(i).getIncidentID()==id) {
                incidentsList.remove(i);
                break;
            }
        }
    }
    public static void setIncidents(ArrayList<CC_TCTwo_IncidentBean> in) {
        incidentsList = in;
    }

    public static ArrayList<CC_TCTwo_IncidentBean> getArchivedList() {
        ArrayList<CC_TCTwo_IncidentBean> archivedList= new ArrayList<>();
        for(int i=0;i<incidentsList.size();i++) {
            if(incidentsList.get(i).getIncidentStatus().equals(CC_TCTwo_IncidentBean.Status.Archived)) {
                archivedList.add(incidentsList.get(i));
            }
        }

        return archivedList;
    }

    public static ArrayList<CC_TCTwo_IncidentBean> getNonArchivedList() {
        ArrayList<CC_TCTwo_IncidentBean> nonArchivedList = new ArrayList<>();

        for(int i=0;i<incidentsList.size();i++) {
            if(!incidentsList.get(i).getIncidentStatus().equals(CC_TCTwo_IncidentBean.Status.Archived)) {
                nonArchivedList.add(incidentsList.get(i));
            }
        }

        return nonArchivedList;
    }

}
