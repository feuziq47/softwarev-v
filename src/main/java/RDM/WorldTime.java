package RDM;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class WorldTime extends Mode {

    private String[] city = {"NYC", "SYD", "LAX", "LON"};
    private int[] timeDifference = {-14, 1, -16, -8};
    private int index = 0;
    private LocalDateTime worldTime;

    public WorldTime() {
    }



    // LocalDateTime으로 현재 시간을 받고, city로 현재 도시를 받습니다. -> 수정했어
    public LocalDateTime getWorldTime() {
        // TODO implement here
        return worldTime;
    }

    public void upIndex(){
        this.index++;
        this.index %= timeDifference.length;
    }

    public void downIndex(){
        this.index++;
        this.index %= timeDifference.length;
    }

    public int getTimeDifference(){
        return this.timeDifference[this.index];
    }

    public void setCurrentTime(LocalDateTime localDateTime){
        this.worldTime = localDateTime;
    }

    public String getCity() {
        // TODO implement here
        return city[this.index];
    }

}
