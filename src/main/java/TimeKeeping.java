
import java.time.*;

public class TimeKeeping extends Mode {

    private LocalDateTime currentTime;

    public TimeKeeping() {
        this.currentTime = LocalDateTime.now();
    }

    //시간이 흐르는 것을 실제로 표현하기 위해 tictok이라는 함수를 새롭게 추가하였습니다.
    public void tictok() {

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
            case "MONTH":
                this.currentTime = this.currentTime.plusMonths(1);
            case "DAY":
                this.currentTime = this.currentTime.plusDays(1);
            case "HOUR":
                this.currentTime = this.currentTime.plusHours(1);
            case "MIN":
                this.currentTime = this.currentTime.plusMinutes(1);
            case "SEC":
                this.currentTime = this.currentTime.plusSeconds(1);
            default:
                System.err.println("Invalid Unit Name");
        }
    }

    public void decrease(String unitName) {
        // 현재 시간을 수정할 때, 수정 하려는 유닛 이름에 맞춰서 시간을 수정해줍니다.
        switch(unitName){
            case "YEAR":
                this.currentTime = this.currentTime.minusYears(1);
            case "MONTH":
                this.currentTime = this.currentTime.minusMonths(1);
            case "DAY":
                this.currentTime = this.currentTime.minusDays(1);
            case "HOUR":
                this.currentTime = this.currentTime.minusHours(1);
            case "MIN":
                this.currentTime = this.currentTime.minusMinutes(1);
            case "SEC":
                this.currentTime = this.currentTime.minusSeconds(1);
            default:
                System.err.println("Invalid Unit Name");
        }
    }

    public void selectUnitTime() {
        // TODO implement here
        return null;
    }

    public void saveCurrentTime() {
        // TODO implement here
        return null;
    }

}