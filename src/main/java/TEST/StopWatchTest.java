package TEST;

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
     */
    @Test
    public void startStopwatch() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.startStopwatch();
        TimeUnit.SECONDS.sleep(3);
        assertTrue(sw.getStartTime().isAfter(LocalTime.of(0,0,0)));

    }

    @Test
    public void pauseStopwatch(){

    }

    @Test
    public void clearStopwatch(){

    }

}
