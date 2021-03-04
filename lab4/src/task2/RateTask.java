package task2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class RateTask extends RecursiveAction {
    private final Journal journal;
    private String tutorName;
    private final String[] tutorNames = new String[]{"lecturer", "as1", "as2", "as3"};
    private Random random = new Random();

    public RateTask(Journal journal, String tutorName){
        this.journal = journal;
        this.tutorName = tutorName;
    }

    void rateStudents(String tutorName){
        for (int i=0; i<10000; i++){
            for (Group group: journal.getGroups()){
                for (Student student: group.getStudents()){
                    double mark=5;
                    student.rateSync(mark);
//                    System.out.println("Thread: "+ Thread.currentThread().getName()+
//                            " Group " + group.getName() + " Tutor " + tutorName + " rates "
//                            + student.getName() + " by " + mark + " points; Total: " + student.getTotalMarkSync() );
                }
            }
        }
    }

    @Override
    protected void compute() {
        List<RateTask> tasks = new LinkedList<>();
        if (tutorName==" "){
            for (String tutor: tutorNames){
                RateTask task = new RateTask(journal, tutor);
                tasks.add(task);
            }
        }
        else {
            rateStudents(tutorName);
        }

    }
}
