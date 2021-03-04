package task2;

import java.util.ArrayList;

public class Journal {

    private ArrayList<Group> groups;

    public Journal(ArrayList<Group> groups){
        this.groups = groups;
    }

    public ArrayList<Group> getGroups(){
        return this.groups;
    }

    public synchronized void getMarks(){
        for (Group group: groups){
            System.out.println(group.getName());
            for (Student student: group.getStudents()){
                System.out.println(student.getName() + " " +student.getTotalMarkSync());
            }
        }
    }
}
