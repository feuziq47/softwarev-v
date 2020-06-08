package TEST;

import RDM.DecisionMaker;
import RDM.WorldTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTimeTest {//

    /**
     * 완성된 파일 없으므로 지금은 생략
     * 선택된 City로 설정 되는지 Test한다.
     */
    @Test
    public void chooseCity() {
        WorldTime wt = new WorldTime();
        wt.upIndex();
        assertTrue(wt.getCity().equals("SYD"));
        wt.downIndex();
        assertTrue(wt.getCity().equals("NYC"));
    }

    /**
     * 완성된 파일 없으므로 지금은 생략
     * 현재 시간을 기반하여 다른 City의 시간으로 설정 되는지 Test한다.
     */
    @Test
    public void calculateCurrentTimeOfThatCity() {
//        WorldTime wt = new WorldTime();
//        wt.upIndex();
//        assertTrue(wt.getTimeDifference());
//        wt.downIndex();
//        assertTrue(wt.getCity().equals("NYC"));
    }
}
