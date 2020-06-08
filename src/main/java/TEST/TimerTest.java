package TEST;

import RDM.Timer;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {//

    /**
     * setCurrentTime 이 설정된 값으로 초기화 되는지 확인
     */
    @Test
    void setTimer() {
        Timer timer = new Timer();
        LocalTime standard = LocalTime.of(0,0,3);
        timer.setCurrentTime(standard);

        assertTrue(timer.getCurrentTime().getHour() == standard.getHour() &&
        timer.getCurrentTime().getMinute() == standard.getMinute() &&
                timer.getCurrentTime().getSecond() == standard.getSecond());
    }
    /**
     * LeftTime 이 countDown을 호출한뒤, 시간이 감소하는지 확인
     */
    @Test
    void StartTimer() throws InterruptedException {
        Timer timer = new Timer();
        LocalTime standard = LocalTime.of(0,0,10);
        timer.setLeftTime(standard);
        timer.countDown();
        TimeUnit.SECONDS.sleep(2);
        assertTrue(timer.getLeftTime().isBefore(standard));
    }
    /**
     * countDown 한 후, pauseTimer을 했을때, LeftTime이 정지 상태로 유지하는지 확인
     */
    @Test
    void PauseTimer() throws InterruptedException {
        Timer timer = new Timer();
        LocalTime standard = LocalTime.of(0,0,10);
        timer.setLeftTime(standard);
        timer.countDown();
        TimeUnit.SECONDS.sleep(3);
        timer.pauseTimer();
        TimeUnit.SECONDS.sleep(3);
        assertEquals(timer.getLeftTime().getSecond(),
                LocalTime.of(0, 0, 6).getSecond());
    }
    /**
     * resetTimer을 했을때, LeftTime값과 CurrentTime으로 초기화되는지 확인
     */
    @Test
    void ClearTimer() throws InterruptedException {
        Timer timer = new Timer();
        LocalTime standard = LocalTime.of(0,0,10);
        timer.setCurrentTime(standard);
        timer.setLeftTime(LocalTime.of(0,0,5));
        timer.resetTimer();
        assertTrue(timer.getCurrentTime().getHour() == timer.getLeftTime().getHour() &&
                timer.getCurrentTime().getMinute() == timer.getLeftTime().getMinute() &&
                timer.getCurrentTime().getSecond() == timer.getLeftTime().getSecond());
    }

    /**
     * RDMSystem 에서 확인 필요할 듯
     */
    @Test
    void NotifyTheEndOfTimer(){
        
    }
}
