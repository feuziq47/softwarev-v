package TEST;

import RDM.RDMSystem;
import RDM.StopWatch;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeKeepingTest {//
    /**
     *기존의 시간에서 설정한 시간으로 현재 시간이 제대로 변경되는지 Test한다.
     */
    @Test
    public void setCurrentTime(){
        RDMSystem rdm = new RDMSystem();
        LocalDateTime testLDT;
        int temp = 0;
        boolean tmp1 = false, tmp2 = false, tmp3 = false, tmp4 = false, tmp5 = false, tmp6 = false, tmp7 = false,tmp8 = false, tmp9 = false, tmp10 = false, tmp11 = false;
        rdm.decodeButtonInput("LONG_MO");
        tmp1 = rdm.isSettingMode();

        rdm.decodeButtonInput("RE");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getYear() - testLDT.getYear();
        tmp2 = (temp == 1);

        rdm.decodeButtonInput("ST");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getYear() - testLDT.getYear();
        tmp3 = (temp == 0);

        rdm.decodeButtonInput("AD");
        rdm.decodeButtonInput("RE");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getMonth().getValue() - testLDT.getMonth().getValue();
        tmp4 = (temp == 1);

        rdm.decodeButtonInput("ST");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getMonth().getValue() - testLDT.getMonth().getValue();
        tmp5 = (temp == 0);

        rdm.decodeButtonInput("AD");
        rdm.decodeButtonInput("RE");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getDayOfMonth() - testLDT.getDayOfMonth();
        tmp6 = (temp == 1);

        rdm.decodeButtonInput("ST");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getDayOfMonth() - testLDT.getDayOfMonth();
        tmp7 = (temp == 0);

        rdm.decodeButtonInput("AD");
        rdm.decodeButtonInput("RE");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getHour() - testLDT.getHour();
        tmp8 = (temp == 1);

        rdm.decodeButtonInput("ST");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getHour() - testLDT.getHour();
        tmp9 = (temp == 0);

        rdm.decodeButtonInput("AD");
        rdm.decodeButtonInput("RE");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getMinute() - testLDT.getMinute();
        tmp10 = (temp == 1);

        rdm.decodeButtonInput("ST");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getMinute() - testLDT.getMinute();
        tmp11 = (temp == 0);

        assertTrue(tmp1 && tmp2 && tmp3 && tmp4 && tmp5 && tmp6 && tmp7 && tmp8 && tmp9 && tmp10 && tmp11);
    }
}
