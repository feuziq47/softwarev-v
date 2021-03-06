package RDM;

import java.time.LocalTime;
import java.util.TimerTask;

//import java.util.Timer;
// test
/**
 *
 */
public class Timer extends Mode {
    private Timer_Beep_Callback timer_beep_callback;
    private Timer_Callback timerCallback;
    private boolean isTimerStart;

    /**
     * Default constructor
     */
    public Timer() {
        currentTime = LocalTime.of(0,0,0);
        leftTime = LocalTime.of(0,0,0);
        endTime = LocalTime.of(0,0,0);
        timer_beep_callback = null;
        timer = new java.util.Timer();
        timerTask = null;
        timerCallback = null;
        isTimerStart = false;
    }
    /**
     * 시간 자료형은 localtime
     * 사용자가 설정한 시간으로 간주
     */
    private LocalTime currentTime;

    /**
     * 남은 시간 출력
     */
    private LocalTime leftTime;

    /**
     * 카운트 다운이 0초인데, 끝 시간이 필요?
     * 0초로 그냥 고정시킴
     * 0초인지 조건 확인할때 사용
     */
    private LocalTime endTime;

    /**
     * @return
     */

    /**
     * 타이머 변수 추가
     */

    private static java.util.Timer timer;
    private java.util.TimerTask timerTask;

    public void setCallback(Timer_Beep_Callback callback){
        this.timer_beep_callback = callback;
    }
    /*
    public void editTimer() {
        // TODO implement here
        //return null;
    }

    /**
     * @return
     */

    public void increase(String unitName) {
        // TODO implement here
        // 현재 시간을 수정할 때, 수정 하려는 유닛 이름에 맞춰서 시간을 수정해줍니다.
        if(this.currentTime != LocalTime.MAX) {
            switch (unitName) {
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
        } else {
            this.currentTime = LocalTime.MIN;
        }
        leftTime = currentTime;
        System.out.println(currentTime);
    }

    /**
     * @return
     */
    public void decrease(String unitName) {
        // TODO implement here
        if(this.currentTime != LocalTime.MIN) {
            switch (unitName) {
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
        } else {
            this.currentTime = LocalTime.MAX;
        }
        leftTime = currentTime;
        System.out.println(currentTime);
    }

    /**
     * @return
     *빠집니다.ㅎㅎ
    public void selectUnitTime() {
    // TODO implement here
    //return null;
    }
    ㅎ
     * @return
     * 빠지세요 -> 지우세요
    public void saveTimer() {
    leftTime = LocalTime.from(currentTime);
    }
     * @return
     */
    public void startTimer() {
        isTimerStart = true;
        countDown();
    }

    /**
     * @return
     */
    public void countDown() {
        timerTask = new TimerTask() {
            public void run() {
                if (!leftTime.equals(endTime)) {
                    leftTime = leftTime.minusSeconds(1);
                    timerCallback.callbackMethod();
                } else {
                    isTimerStart = false;
                    timerTask.cancel();
                    timer_beep_callback.callbackMethod();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * @return
     */
    public void pauseTimer() {

        timerTask.cancel();
        isTimerStart = false;
    }

    /**
     * @return
     */
    public void resetTimer() {
        leftTime = currentTime;
        isTimerStart = false;
//        if(!leftTime.equals(endTime)){
//            leftTime = currentTime;
//        }
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public LocalTime getLeftTime() {
        return leftTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public void setLeftTime(LocalTime leftTime) {
        this.leftTime = leftTime;
    }

    public void setTimerCallback(Timer_Callback timerCallback) { this.timerCallback = timerCallback; }


    public boolean isTimerStart() {
        return isTimerStart;
    }
}