package RDM;
import java.time.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeKeeping extends Mode {

    private LocalDateTime currentTime;
    private static Timer timer = new java.util.Timer();

    public TimeKeeping() {
        this.currentTime = LocalDateTime.now();
    }

    //시간이 흐르는 것을 실제로 표현하기 위해 tictok()이라는 함수를 새롭게 추가하였습니다.
    public void tictok() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                currentTime.plusSeconds(1);
            }
        }, 0, 1000);
    }
    //필요없는 기능이므로 삭제하겠습니다. -이정우
    /*public void editTimeKeeping() {
        // TODO implement here
        return null;
    }*/

    public void increase(String unitName) {
        // 현재 시간을 수정할 때, 수정 하려는 유닛 이름에 맞춰서 시간을 수정해줍니다.
        switch(unitName){
            case "YEAR":
                this.currentTime = this.currentTime.plusYears(1);
                break;
            case "MONTH":
                this.currentTime = this.currentTime.plusMonths(1);
                break;
            case "DAY":
                this.currentTime = this.currentTime.plusDays(1);
                break;
            case "HOUR":
                this.currentTime = this.currentTime.plusHours(1);
                break;
            case "MIN":
                this.currentTime = this.currentTime.plusMinutes(1);
                break;
            case "SEC":
                this.currentTime = this.currentTime.plusSeconds(1);
                break;
            default:
                System.err.println("Invalid Unit Name");
                break;
        }
    }

    public void decrease(String unitName) {
        // 현재 시간을 수정할 때, 수정 하려는 유닛 이름에 맞춰서 시간을 수정해줍니다.
        switch(unitName){
            case "YEAR":
                this.currentTime = this.currentTime.minusYears(1);
                break;
            case "MONTH":
                this.currentTime = this.currentTime.minusMonths(1);
                break;
            case "DAY":
                this.currentTime = this.currentTime.minusDays(1);
                break;
            case "HOUR":
                this.currentTime = this.currentTime.minusHours(1);
                break;
            case "MIN":
                this.currentTime = this.currentTime.minusMinutes(1);
                break;
            case "SEC":
                this.currentTime = this.currentTime.minusSeconds(1);
                break;
            default:
                System.err.println("Invalid Unit Name");
                break;
        }
    }
    /* 일단 빠집니다.
    public String selectUnitTime(String unitName) {
        // 수정 시에 커서를 다음 유닛으로 옮겨준다.
        switch(unitName){
            case "HOUR":
                return "MIN";
            case "MIN":
                return "SEC";
            case "SEC":
                return "HOUR";
            default:
                System.err.println("Invalid Unit Name");
                return null;
        }
    }
    */

    public LocalDateTime getCurrentTime(){
        return this.currentTime;
    }

    // 필요 없어서 삭제 하겠습니다 - 이정우
    /*
    public void saveCurrentTime() {
        // TODO implement here
        return null;
    }
    */

}