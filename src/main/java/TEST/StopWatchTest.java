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
        rdms.decodeButtonInput("MO");
        rdms.decodeButtonInput("ST");

        StopWatch sw = (StopWatch)rdms.getAllMode()[1];

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
    public void pauseStopwatch() throws InterruptedException {
        RDMSystem rdms = new RDMSystem();
        StopWatch sw = (StopWatch)rdms.getAllMode()[1];
        rdms.decodeButtonInput("MO");
        rdms.decodeButtonInput("ST");

        LocalTime start = sw.getStartTime();

        rdms.decodeButtonInput("ST");

        LocalTime stop = sw.getStartTime();

        TimeUnit.SECONDS.sleep(3);

        assertTrue(start.getHour() == stop.getHour() &&
                start.getMinute() == stop.getMinute() &&
                start.getSecond() == stop.getSecond());
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
    public void clearStopwatch() throws InterruptedException {
        RDMSystem rdms = new RDMSystem();
        StopWatch sw = (StopWatch)rdms.getAllMode()[1];
        rdms.decodeButtonInput("MO");
        rdms.decodeButtonInput("ST");

        TimeUnit.SECONDS.sleep(3);
        rdms.decodeButtonInput("ST");
        assertTrue(sw.getStartTime().isAfter(LocalTime.of(0,0,0)));
        rdms.decodeButtonInput("RE");

        LocalTime temp = sw.getStartTime();

        assertTrue(temp.getHour() == 0 &&
                temp.getMinute() == 0 &&
                temp.getSecond() == 0);

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
    public void recordLapTime() throws InterruptedException {
        RDMSystem rdms = new RDMSystem();
        StopWatch sw = (StopWatch)rdms.getAllMode()[1];
        rdms.decodeButtonInput("MO");
        rdms.decodeButtonInput("ST");

        TimeUnit.SECONDS.sleep(3);

        // 랩 타임
        rdms.decodeButtonInput("AD");
        //정지
        rdms.decodeButtonInput("ST");

        LocalTime lap = sw.getLapTime(0);
        LocalTime cur = sw.getStartTime();
        assertTrue(lap.getHour() == cur.getHour() &&
                lap.getMinute() == cur.getMinute() &&
                lap.getSecond() == cur.getSecond());
    }

    @Test
    public void showLapTime() {
        RDMSystem rdm = new RDMSystem();
        StopWatch sw = new StopWatch();
        ((StopWatch)rdm.getAllMode()[1]).startStopwatch();
        rdm.decodeButtonInput("AD");

    }

    public void switchLapTime(){

    }

}
