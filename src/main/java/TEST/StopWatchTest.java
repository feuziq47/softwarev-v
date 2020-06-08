package TEST;

import RDM.RDMSystem;
import RDM.StopWatch;
import RDM.Timer;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class StopWatchTest {//

    /**
     * startStopwatch을 했을때, 시간이 startTime이 증가 되었는지 확인
     * 콜백 함수로 인하여, System 단에서 test해야 한다.
     * 이하 동문
     *
     * Stopwatch가 0초부터 시작되는지 Test한다.
     * Stopwatch의 시간이 시간의 흐름에 따라 제대로 증가하는 지 Test한다.
     *
     */
    @Test
    public void startStopwatch() throws InterruptedException {
        RDMSystem rdms = new RDMSystem();
        StopWatch sw = new StopWatch();
        sw.startStopwatch();
        TimeUnit.SECONDS.sleep(3);
        assertTrue(sw.getStartTime().isAfter(LocalTime.of(0,0,0)));

    }

    /**
     * startStopwatch을 했을때, 시간이 startTime이 증가 되었는지 확인
     * 콜백 함수로 인하여, System 단에서 test해야 한다.
     * 이하 동문
     *
     * Stopwatch가 정지 버튼에 해당하는 신호가 주어졌을때
     * Stopwatch에 표시된 현재 시간에서 정지하는지 Test한다.
     */
    @Test
    public void pauseStopwatch(){

    }

    /**
     * startStopwatch을 했을때, 시간이 startTime이 증가 되었는지 확인
     * 콜백 함수로 인하여, System 단에서 test해야 한다.
     * 이하 동문
     *
     * Stopwatch에서 Lap Time에 해당하는 신호가 주어졌을때
     * Stopwatch에 표시된 현재 시간이 기록되는지 test한다.
     */
    @Test
    public void clearStopwatch(){

    }

    /**
     * startStopwatch을 했을때, 시간이 startTime이 증가 되었는지 확인
     * 콜백 함수로 인하여, System 단에서 test해야 한다.
     * 이하 동문
     *
     * Stopwatch에서 Lap Time에 해당하는 신호가 주어졌을때
     * Stopwatch에 표시된 현재 시간이 기록되는지 test한다.
     */
    @Test
    public void recordLapTime(){

    }

}
