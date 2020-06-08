package TEST;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import RDM.StopWatch;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class AlarmTest {

    /**
     * deleteAlarm 케이스는 삭제했으므로, 불필요
     */
    @Test
    public void deleteAlarm() {

    }

    /**
     * 활성화 비활성화 행위 주체는 System이므로, System에서 Test해야 함
     */
    @Test
    public void activateAlarm() {
        
    }

    /**
     * 활성화 비활성화 행위 주체는 System이므로, System에서 Test해야 함
     */
    @Test
    public void deactivateAlarm() {

    }

    /**
     * Alarm, StaticTime 이 서로 섞여 있어서, 알람 리스트 활용하는 메소드를 만들던지,
     * System에서 Test 하던지 해야 함
     */
    @Test
    public void setAlarm() {

    }

    /**
     * Alarm, StaticTime 이 서로 섞여 있어서, 알람 리스트 활용하는 메소드를 만들던지,
     * System에서 Test 하던지 해야 함
     */
    @Test
    public void notifyAlarm() {

    }
}
