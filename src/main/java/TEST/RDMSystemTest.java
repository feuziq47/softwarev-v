package TEST;

import RDM.RDMSystem;
import RDM.StopWatch;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RDMSystemTest {//

    /**
     * 완성된 파일 없으므로 지금은 생략
     * 지정된 버튼을 눌렀을 때, 다음 Mode로 변경되는지 Test한다.
     */
    @Test
    public void changeCurrentMode() throws InterruptedException{
        RDMSystem rdms = new RDMSystem();
        rdms.changeCurrentMode();
        assertTrue(rdms.getCurrentMode() instanceof StopWatch);
    }

    /**
     * 완성된 파일 없으므로 지금은 생략
     * 지정된 버튼을 눌렀을 때, Mode 선택창으로 넘어가는지 Test한다.
     * Mode 선택창에서 선택한 4가지의 Mode가 Activate 하게 설정 되는지 Test한다.
     */
    @Test
    public void selectModes() {
        RDMSystem rdms = new RDMSystem();
        rdms.selectMode("ST");
        assertTrue(rdms.getIsAvailable()[0] == false);

    }


}
