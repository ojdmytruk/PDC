package task2;

import java.util.Random;

public class RateThread implements Runnable{

    private final Journal journal;
    private final String tutorName;
    private Random random = new Random();


    public RateThread(Journal journal, String tutorName){
        this.journal = journal;
        this.tutorName = tutorName;
    }

    @Override
    public void run() {
        for (int i=0; i<10000; i++){
            for (Group group: journal.getGroups()){
                for (Student student: group.getStudents()){
                    double mark=5;
                    student.rateSync(mark);
//                    System.out.println("Thread: "+ Thread.currentThread().getName()+
//                            " Group " + group.getName() + " Tutor " + this.tutorName + " rates "
//                            + student.getName() + " by " + mark + " points; Total: " + student.getTotalMarkSync() );
                }
            }
        }
    }


}

