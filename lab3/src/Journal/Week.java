package Journal;

public class Week {

    private int week;

    public Week(){
        this.week = 1;
    }

    public int getWeek(){
        return this.week;
    }

    public void incrementWeek(){
        this.week++;
    }

    public boolean checkWeek(){
        if (this.week==2) return false;
        else return true;
    }
}
