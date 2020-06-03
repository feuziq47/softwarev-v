
import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
//import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
// test
/**
 * 
 */
public class Timer extends Mode {

    /**
     * Default constructor
     */
    public Timer() {
        currentTime = LocalTime.of(0,0,0);
        leftTime = LocalTime.of(0,0,0);
        endTime = LocalTime.of(0,0,0);
        timer = new java.util.Timer();

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

    public void editTimer() {
        // TODO implement here
        //return null;
    }

    /**
     * @return
     */
    public void increase() {
        // TODO implement here
        //return null;
    }

    /**
     * @return
     */
    public void decrease() {
        // TODO implement here
        //return null;
    }

    /**
     * @return
     */
    public void selectUnitTime() {
        // TODO implement here
        //return null;
    }

    /**
     * @return
     * deep copy
     */
    public void saveTimer() {
        leftTime = LocalTime.from(currentTime);
    }

    /**
     * @return
     */
    public void startTimer() {
        countDown();
    }

    /**
     * @return
     */
    public void countDown() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(!leftTime.equals(endTime)){
                    leftTime.minusSeconds(1);
                }
                else timer.cancel();
            }
        }, 0, 1000);
    }

    /**
     * @return
     */
    public void pauseTimer() {
        timer.cancel();
    }

    /**
     * @return
     */
    public void resetTimer() {
        if(!leftTime.equals(endTime)){
            leftTime = currentTime;
        }
    }

}