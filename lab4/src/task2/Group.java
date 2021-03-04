package task2;

import java.util.ArrayList;

public class Group {

    private String name;
    private ArrayList<Student> students;

    public Group(String name, ArrayList<Student> students){
        this.name = name;
        this.students = students;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }
}
