package TEST;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import RDM.StopWatch;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class AlarmTest {

    /**
     * deleteAlarm 케이스는 삭제했으므로, 불필요
     *
     * 선택한 알람이 제대로 삭제되었는지 Test한다..
     */
    @Test
    public void deleteAlarm() {

    }

    /**
     * 활성화 비활성화 행위 주체는 System이므로, System에서 Test해야 함
     *
     * 활성화 된 Alarm 시간이 되면 Beep음이 울리는 지 Test한다.
     */
    @Test
    public void activateAlarm() {
        
    }

    /**
     * 활성화 비활성화 행위 주체는 System이므로, System에서 Test해야 함
     *
     * 비활성화된 Alarm 시간이 됐을 때, Beep이 울리지 않는지를 Test한다.
     * 선택된 4가지 Mode에서 제외 될 경우 비활성화 되는지 test한다.
     */
    @Test
    public void deactivateAlarm() {

    }

    /**
     * Alarm, StaticTime 이 서로 섞여 있어서, 알람 리스트 활용하는 메소드를 만들던지,
     * System에서 Test 하던지 해야 함
     *
     * Alarm시간이 설정된 시간으로 변경되는지 Test한다.
     */
    @Test
    public void setAlarm() {

    }

    /**
     * Alarm, StaticTime 이 서로 섞여 있어서, 알람 리스트 활용하는 메소드를 만들던지,
     * System에서 Test 하던지 해야 함
     *
     * 활성화된 Alarm만 울리는지 Test한다.
     */
    @Test
    public void notifyAlarm() {

    }
}
