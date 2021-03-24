package Journal;

import java.util.ArrayList;
import java.util.Random;

public class RateThread implements Runnable{

    private final Journal journal;
    private final String tutorName;
    private Random random = new Random();
    private int groupIndex = -1;


    public RateThread(Journal journal, String tutorName){
        this.journal = journal;
        this.tutorName = tutorName;
    }

    public RateThread(Journal journal, String tutorName, int groupIndex){
        this.journal = journal;
        this.tutorName = tutorName;
        this.groupIndex = groupIndex;
    }

    @Override
    public void run() {
        for (int i=0; i<18; i++){
            if (groupIndex != -1){
                for (Student student: journal.getGroups().get(groupIndex).getStudents()){
                    double mark=5;
                    student.rateSync(mark);
                    System.out.println("Thread: "+ Thread.currentThread().getName()+
                            " Group " + journal.getGroups().get(groupIndex).getName() + " Tutor " + this.tutorName + " rates "
                            + student.getName() + " by " + mark + " points; Total: " + student.getTotalMarkSync() );
                }
            }
            else{
                for (Group group: journal.getGroups()){
                    for (Student student: group.getStudents()){
                        double mark=5;
                        student.rateSync(mark);
                        System.out.println("Thread: "+ Thread.currentThread().getName()+
                                " Group " + group.getName() + " Tutor " + this.tutorName + " rates "
                                + student.getName() + " by " + mark + " points; Total: " + student.getTotalMarkSync() );
                    }
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

