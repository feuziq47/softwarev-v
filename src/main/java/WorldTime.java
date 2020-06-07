
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
    public LocalDateTime getWorldTime(LocalDateTime dir, String city) {
        // TODO implement here
        switch(city){
            case "NYC":
                this.index = 0;
            case "SYD":
                this.index = 1;
            case "LAX":
                this.index = 2;
            case "LON":
                this.index = 3;
            default:
                System.out.println("Wrong City name");
        }
        return dir.plusHours(timeDifference[index]);
    }

    public String getCity() {
        // TODO implement here
        return city[this.index];
    }

}
