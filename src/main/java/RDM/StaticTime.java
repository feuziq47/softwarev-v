package RDM;

import RDM.Callback;

import java.time.LocalDate;
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
        alreadyNotified =  false;
        todayDate = LocalDate.now();
        timerTask = new TimerTask() {
            public void run() {
                if ( alarmTime.isBefore(LocalTime.now())&& isAlreadyNotified() && isActivated) {
                    alarm_callback.callbackMethod();
                    alreadyNotified = false;
                }

                if(LocalDate.now().isAfter(todayDate)) {
                    todayDate = LocalDate.now();
                    alreadyNotified = true;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private LocalDate todayDate;
    private boolean alreadyNotified;
    private LocalTime alarmTime;
    private boolean isActivated;
    private Alarm_Beep_Callback alarm_callback;
    private static java.util.Timer timer;
    private java.util.TimerTask timerTask;

    /**
     * @return
     */
    public LocalTime getAlarmTime() {
        // TODO implement here
        return alarmTime;
    }

    public boolean isAlreadyNotified(){
        return alreadyNotified;
    }

    public void setIsAlreadyNotified(){
        if(alarmTime.isBefore(LocalTime.now())){
            alreadyNotified = false;
        } else {
            alreadyNotified = true;
        }
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

    public void setCallback(Alarm_Beep_Callback callback){
        this.alarm_callback = callback;
    }



}