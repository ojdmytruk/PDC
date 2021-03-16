package task4;

import java.util.ArrayList;
import java.util.Random;

public class RateThread implements Runnable{

    private final Journal journal;
    private final String tutorName;
    private Random random = new Random();
    private JournalMain main;


    public RateThread(Journal journal, String tutorName, JournalMain main){
        this.journal = journal;
        this.tutorName = tutorName;
        this.main = main;
    }

    @Override
    public void run() {
        for (int i=0; i<18; i++){
            for (Group group: journal.getGroups()){
                for (Student student: group.getStudents()){
                    double mark=5;
                    long timeStart = System.nanoTime();
                    student.rateSync(mark);
                    long timeCompute = System.nanoTime() - timeStart;
                    System.out.println(timeCompute);
                    main.incrementAvgTime(timeCompute);
//                    System.out.println("Thread: "+ Thread.currentThread().getName()+
//                            " Group " + group.getName() + " Tutor " + this.tutorName + " rates "
//                            + student.getName() + " by " + mark + " points; Total: " + student.getTotalMarkSync() );
                }
            }
        }
//        for (int i=0; i<18; i++) {
//            for (Group group: journal.getGroups()){
//                for (Student student: group.getStudents()){
//                    double mark=5;
//                    student.rateAsync(mark);
//                    System.out.println("Thread: "+ Thread.currentThread().getName()+
//                            " Group " + group.getName() + " Tutor " + this.tutorName + " rates "
//                            + student.getName() + " by " + mark + " points; Total: " + student.getTotalMarkAsync() );
//                }
//            }
//        }
    }


}

