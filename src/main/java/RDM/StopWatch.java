package RDM;
import java.time.LocalTime;
import java.util.*;

/**
 *
 */
public class StopWatch extends Mode {

    /**
     * 타이머 변수 추가
     */
    private static java.util.Timer timer;

    /**
     *  LocalDateTime -> LocalTime으로 모두 변경
     */
    private ArrayList<LocalTime> lapTime;
    private LocalTime startTime;
    private LocalTime savedTime;
    private Integer index;
    private LocalTime availTime;
    private boolean isRunning;
    private StopWatch_Callback stopWatch_Callback;
    private LapTime_Callback lapTime_Callback;
    private TimerTask timerTask;

    /**
     * Default constructor
     */
    public StopWatch() {
        lapTime = new ArrayList<LocalTime>();
        //lapTime = new LocalTime[10];
        startTime = LocalTime.of(0,0,0);
        savedTime = LocalTime.of(0,0,0);
        availTime = LocalTime.MAX;
        index = 0;
        stopWatch_Callback = null;
        lapTime_Callback = null;
        timerTask = null;
        timer = new java.util.Timer();
        // 작동중인지 상태변수 추가
        //isRunning = false;
    }


    /**
     * @return
     */
    public void startStopwatch() {
        timerTask = new TimerTask() {
            public void run() {
                startTime = startTime.plusSeconds(1);
                stopWatch_Callback.callbackMethod();
            }
        };
        if(startTime.isBefore(availTime)){
            timer.scheduleAtFixedRate(timerTask , 0, 1000);
        } else{
            timer.cancel();
            resetStopwatch();
        }
        //isRunning = true;
    }

    /**
     * @return
     */
    public void resetStopwatch() {
        startTime = LocalTime.of(0,0,0);
    }

    /**
     * @return
     */
    public void pauseStopwatch() {
        //if(isRunning){
        timerTask.cancel();
        //isRunning = false;
        //}
    }

    /**
     * @return

    public void activateBeep() {
    // TODO implement here
    //return null;
    }
     */

    /**
     * @param dir
     * @return 반환형 변경 : LocalDateTime - > LocalTime
     */
    public LocalTime getLapTime(int dir) {
        assert dir >= 0 && dir < 10;
        return lapTime.get(dir);
    }

    /**
     * @return
     */
    public void clearStopwatch() {
        //if(!isRunning){
        startTime = LocalTime.of(0,0,0);
        lapTime.clear();
        //}
    }

    public void recordLapTime(){
        if(lapTime.size() == 10) {
            lapTime.remove(9);
        }
        lapTime.add(0,LocalTime.from(startTime));
        lapTime_Callback.callbackMethod();
    }
    /**
     * 인자: 인덱스 로 검색
     public LocalTime fetchLapTime(int idx){

     return lapTime.get(idx);
     }
     *
     * 인자 : up down string으로 검색
     */


    public LocalTime getLaptime(String dir){
        assert dir.equals("up") || dir.equals("down");
        int size = lapTime.size();
        if(dir.equals("up")){
            if(index == size - 1){
                index = 0;
                return lapTime.get(index);
            }
            else return lapTime.get(index++);
        }
        else if(dir.equals("down")){
            if(index == 0){
                index = size - 1;
                return lapTime.get(index);
            }
            else return lapTime.get(index--);
        }

        assert false;
        return LocalTime.of(4,4,4);
    }

    public LocalTime getStopwatchTime() {
        return startTime;

    }

    public LocalTime getNowLapTime(){
        if(lapTime.isEmpty()){
            return null;
        }
        return lapTime.get(index);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setCountUpCallback(StopWatch_Callback stopWatchCallback){ this.stopWatch_Callback = stopWatchCallback; }

    public void setLapTimeCallback(LapTime_Callback lapTimeCallback){ this.lapTime_Callback = lapTimeCallback; }
}