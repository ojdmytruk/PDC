package Journal;

import java.util.ArrayList;
import java.util.Random;

public class RateThread implements Runnable{

    private Journal journal;
    private String tutorName;
    private Random random = new Random();
    private int semesterLength = 18;
    private int studentsCounter = 0;

    public RateThread(Journal journal, String tutorName){
        this.journal = journal;
        this.tutorName = tutorName;
    }

    @Override
    public void run() {
        for (Group group: journal.getGroups()){
            for (Student student: group.getStudents()){
                studentsCounter++;
            }
        }
        for (int i=0; i<semesterLength; i++){
            for (int j=0; j<studentsCounter; j++){
                Group group = journal.getGroups().get((int) (journal.getGroups().size()*Math.random()));
                Student student = group.getStudents().get((int) (group.getStudents().size()*Math.random()));
                if (student.getWeekCounter() <= i){
                    double mark = (double) random.nextInt(100);
                    //double mark=5;
                    student.rate(mark);
                    System.out.println("Thread: "+ Thread.currentThread().getName()+
                            " Group " + group.getName() + " Tutor " + this.tutorName + " rates "
                            + student.getName() + " by " + mark + " points");
                }

            }
        }

    }
}
