package models;

import javafx.beans.property.SimpleStringProperty;

public class Work {

    private SimpleStringProperty workName;
    private SimpleStringProperty statusWork;

    public Work(String workName,String statusWork) {
        this.workName = new SimpleStringProperty(workName);
        this.statusWork = new SimpleStringProperty(statusWork);
    }


    public String getWorkName() {
        return workName.get();
    }

    public void setWorkName(String workName) {
        this.workName = new SimpleStringProperty(workName);
    }

    public String getStatusWork() {
        return statusWork.get();
    }

    public void setStatusWork(String statusWork) {
        this.statusWork = new SimpleStringProperty(statusWork);
    }
}
