package task4;

import java.util.ArrayList;

public class Journal {

    private ArrayList<Group> groups;

    public Journal(ArrayList<Group> groups){
        this.groups = groups;
    }

    public ArrayList<Group> getGroups(){
        return this.groups;
    }
}
