package cyclomatic_complexity;


import java.util.ArrayList;

public class CC_TCTwo_PostIncidentDAO {

    private static ArrayList<CC_TCTwo_PostIncidentBean> postIncidentsList;

    static {
        postIncidentsList = new ArrayList<>();

    }

    public static ArrayList<CC_TCTwo_PostIncidentBean> getPostIncidentList() {
        return postIncidentsList;
    }

    public static void addPostIncident(CC_TCTwo_PostIncidentBean postIncident) {
        postIncidentsList.add(postIncident);

    }

    public static CC_TCTwo_PostIncidentBean getPostIncidentByIncidentID(int id) {
        CC_TCTwo_PostIncidentBean postIncident = new CC_TCTwo_PostIncidentBean();

        for(int i=0;i<postIncidentsList.size();i++) {
            if(postIncidentsList.get(i).getIncidentID()==id) {
                postIncident=postIncidentsList.get(i);
                break;
            }
        }

        return postIncident;
    }
}
