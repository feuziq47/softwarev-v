package RDM;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class WorldTime extends Mode {

    private String[] city = {"NYC", "SYD", "LAX", "LON"};
    private int[] timeDifference = {14, 1, 16, 8};
    private int index = -1;

    public WorldTime() {
    }

    // LocalDateTime으로 현재 시간을 받고, city로 현재 도시를 받습니다. -> 수정했어
    public LocalDateTime getWorldTime(LocalDateTime dir, String upOrDown) {
        // TODO implement here
        switch(upOrDown){
            case "UP":
                if(this.index < 3){
                    this.index++;
                } else{
                    this.index = 0;
                }
                break;
            case "DOWN":
                if(this.index > 0){
                    this.index--;
                } else{
                    this.index = 3;
                }
                break;
        }
        return dir.plusHours(timeDifference[index]);
    }

    public String getCity() {
        // TODO implement here
        return city[this.index];
    }

}
