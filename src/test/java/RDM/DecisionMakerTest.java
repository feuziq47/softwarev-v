package RDM;

import RDM.DecisionMaker;
import RDM.StopWatch;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DecisionMakerTest {
    /**
     * 입력한 값으로 Case Number가 설정 되는지 Test한다.
     */
    @Test
    public void setCaseNumber() throws InterruptedException {
        DecisionMaker dm = new DecisionMaker();
        dm.setCaseNum(10);
        assertSame(dm.getCaseNum(), 10);

    }
    /**
     *입력한 Case Number내의 Case만 나오는지 Test한다.
     * Random한 값으로 Case가 나오는지 Test한다.
     */
    @Test
    public void getCase() throws InterruptedException {
        DecisionMaker dm = new DecisionMaker();
        dm.setCaseNum(10);
        for (int i = 0; i < 100; i++) {
            dm.getCase();
            int caseNum = dm.getRandNum();
            assertTrue(caseNum <= 10 && caseNum >= 0);
        }

    }
}
