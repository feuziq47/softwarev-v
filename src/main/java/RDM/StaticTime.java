package RDM;
import java.time.LocalDateTime;
import java.util.*;
import java.time.*;
/**
 *
 */
public class StaticTime {

    /**
     * Default constructor
     */
    public StaticTime() {
    }

    private LocalDateTime alarmTime;
    private boolean isActivated;

    /**
     * @return
     */
    public LocalDateTime getAlarmTime() {
        // TODO implement here
        return alarmTime;
    }

    /**
     * @param time
     * @return
     */
    public void setAlarmTime(LocalDateTime time) {
        // TODO implement here
        this.alarmTime = time;
    }

    /**
     * @return
     */
    public boolean getIsActivated() {
        // TODO implement here
        return isActivated;
    }

    /**
     * @param isActivated
     * @return
     */
    public void setIsActivated(boolean isActivated) {
        // TODO implement here
        this.isActivated = isActivated;
    }

}