package ku.cs.models;
import javafx.beans.property.SimpleStringProperty;


public class Work {

    private SimpleStringProperty workID;
    private SimpleStringProperty dateStart;
    private SimpleStringProperty dateDone;

    private SimpleStringProperty workName;
    private SimpleStringProperty statusName;

    public Work(String workName, String statusName, String dateStart, String dateDone) {
        this.workName = new SimpleStringProperty(workName);
        this.statusName = new SimpleStringProperty(statusName);
        this.dateStart = new SimpleStringProperty(dateStart);
        this.dateDone = new SimpleStringProperty(dateDone);
    }

    public Work(String workName, String statusName) {
        this.workName = new SimpleStringProperty(workName);
        this.statusName = new SimpleStringProperty(statusName);
    }


    public String getWorkID () {return workID.get();}

    public void setWorkID (String workID){this.workID = new SimpleStringProperty(workID);}

    public String getWorkName () {return workName.get();}

    public void setWorkName (String workName){this.workName = new SimpleStringProperty(workName);}

    public String getStatusName () {return statusName.get();}

    public void setStatusWork (String statusName){this.statusName = new SimpleStringProperty(statusName);}

    public String getDateStart () {return dateStart.get();}

    public void setDateStart (String dateStart){this.dateStart = new SimpleStringProperty(dateStart);}

    public String getDateDone () {return dateDone.get();}

    public void setDateDone (String dateDone){this.dateDone = new SimpleStringProperty(dateDone);}

}
