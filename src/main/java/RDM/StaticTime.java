package RDM;

import RDM.Callback;

import java.time.LocalTime;
import java.util.TimerTask;

/**
 *
 */
public class StaticTime{

    /**
     * Default constructor
     */
    public StaticTime() {
        alarmTime = LocalTime.MIN;
        isActivated = false;
        this.alarm_callback = null;
        timer = new java.util.Timer();
        timerTask = new TimerTask() {
            public void run() {
                if (alarmTime.isBefore(LocalTime.now()) && isActivated) {
                    alarm_callback.callbackMethod();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private LocalTime alarmTime;
    private boolean isActivated;
    private Callback alarm_callback;
    private static java.util.Timer timer;
    private java.util.TimerTask timerTask;

    /**
     * @return
     */
    public LocalTime getAlarmTime() {
        // TODO implement here
        return alarmTime;
    }

    /**
     * @param time
     * @return
     */
    public void setAlarmTime(LocalTime time) {
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

    public void setCallback(Callback callback){
        this.alarm_callback = callback;
    }



}