package TEST;

import RDM.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AlarmTest {

    /**
     * 활성화 비활성화 행위 주체는 System이므로, System에서 Test해야 함
     *
     * 활성화 된 Alarm 시간이 되면 Beep음이 울리는 지 Test한다.
     */
    @Test
    public void activateAlarm() {
        RDMSystem rdm = new RDMSystem();
        Alarm al = new Alarm();
        if(al.getStaticTime().getIsActivated() && al.getStaticTime().getAlarmTime().isBefore(LocalTime.now())){
            assertTrue(rdm.getBeep().isActivated());
        }
    }

    /**
     * 활성화 비활성화 행위 주체는 System이므로, System에서 Test해야 함
     *
     * 비활성화된 Alarm 시간이 됐을 때, Beep이 울리지 않는지를 Test한다.
     * 선택된 4가지 Mode에서 제외 될 경우 비활성화 되는지 test한다.
     */
    @Test
    public void deactivateAlarm() {
        RDMSystem rdm = new RDMSystem();
        Alarm al = new Alarm();
        if(!al.getStaticTime().getIsActivated() && al.getStaticTime().getAlarmTime().isBefore(LocalTime.now())){
            assertFalse(rdm.getBeep().isActivated());
        }
    }

    /**
     * Alarm, StaticTime 이 서로 섞여 있어서, 알람 리스트 활용하는 메소드를 만들던지,
     * System에서 Test 하던지 해야 함
     *
     * Alarm시간이 설정된 시간으로 변경되는지 Test한다.
     *
     * v2 -> 두 단계로 쪼개갰습니다. increase decrease
     */
    @Test
    public void increase() {
        RDMSystem rdm = new RDMSystem();
        if((rdm.getCurrentMode() instanceof Alarm) && rdm.getIsSelectMode()){
            LocalTime beforeTime = ((Alarm)rdm.getAllMode()[3]).getStaticTime().getAlarmTime();
            rdm.decodeButtonInput("RE");
            assertEquals(((Alarm)rdm.getAllMode()[3]).getStaticTime().getAlarmTime(), beforeTime.plusHours(1));
        }
    }

    @Test
    public void decrease() {
        RDMSystem rdm = new RDMSystem();
        if((rdm.getCurrentMode() instanceof Alarm) && rdm.getIsSelectMode()){
            LocalTime beforeTime = ((Alarm)rdm.getAllMode()[3]).getStaticTime().getAlarmTime();
            rdm.decodeButtonInput("ST");
            assertEquals(((Alarm)rdm.getAllMode()[3]).getStaticTime().getAlarmTime(), beforeTime.minusHours(1));
        }
    }

    /**
     * 알람이 다음 알람으로 제대로 바뀌는 지 확인한다.
     */

    @Test
    public void switchAlarm() {
        RDMSystem rdm = new RDMSystem();
        Alarm al = new Alarm();
        if(rdm.getCurrentMode() instanceof Alarm && !rdm.getIsSelectMode()){
            int beforeIndex = ((Alarm)rdm.getAllMode()[3]).getIndex();
            rdm.decodeButtonInput("ST");
            assertEquals(((Alarm)rdm.getAllMode()[3]).getIndex(), beforeIndex+1);

        }
    }


    /**
     * Alarm, StaticTime 이 서로 섞여 있어서, 알람 리스트 활용하는 메소드를 만들던지,
     * System에서 Test 하던지 해야 함
     *
     * 활성화된 Alarm만 울리는지 Test한다.
     * v2 -> 위에서 체크한 듯 합니다.
     *
     * @Test
     * public void notifyAlarm() {
     * RDMSystem rdm = new RDMSystem();
     * if(rdm.get)
     * }
     */

     @Test
     public void notifyAlarm() throws ParseException, InterruptedException {
        RDMSystem rdms = new RDMSystem();
        Alarm alarm = (Alarm)rdms.getAllMode()[3];
        StaticTime st = alarm.getAlarmList(0);
        st.setIsActivated(true);
        st.setAlarmTime(LocalTime.of(22,49,30));
        Beep beep = rdms.getBeep();
        assertFalse(beep.isActivated());
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
        Date date = dateFormatter.parse("22:49:50");

        java.util.Timer timer = new java.util.Timer();

        TimerTask timerTask = new TimerTask() {
             public void run() {
                 assertTrue(beep.isActivated());
                 timer.cancel();
             }
        };

        timer.schedule(timerTask , date);

        long currentMillis = System.currentTimeMillis();
        long givenDateMillis = LocalDateTime.of(2020, 6, 8, 22, 50, 0)
                 .atZone(ZoneId.systemDefault())
                 .toInstant()
                 .toEpochMilli();

        Thread.sleep(givenDateMillis - currentMillis);
     }
}