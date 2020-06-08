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
        boolean tmp1 = false, tmp2 = false;
        rdm.decodeButtonInput("Long Mo");
        tmp1 = rdm.isSettingMode();

        rdm.decodeButtonInput("RE");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getYear() - testLDT.getYear();
        tmp2 = (temp == 1);

        rdm.decodeButtonInput("ST");
        testLDT = LocalDateTime.now();
        temp = rdm.getTimeKeepingCurrentTime().getYear() - testLDT.getYear();
        tmp2 = (temp == 0);
    }
}
