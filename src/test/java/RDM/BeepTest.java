package RDM;

import RDM.Beep;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeepTest {

    /**
     * 완성된 파일 없으므로 지금은 생략
     * Beep음을 활성화 시켰을때, Beep음이 울려야 하는 모든 상황에서 Beep음이 울리는지 Test 한다.
     */
    @Test
    public void activateBeep() {
        Beep beep = new Beep();
        beep.beepStart();
        assertTrue(beep.isActivated());
        beep.deactivateBeep();
    }

    /**
     * 완성된 파일 없으므로 지금은 생략
     * 지정된 버튼을 눌렀을 때, Beep음이 종료되는지 test 한다.
     */
    @Test
    public void deactivateBeep() {
        Beep beep = new Beep();
        boolean tmp = false;
        beep.beepStart();
        tmp = beep.isActivated();
        beep.deactivateBeep();
        assertTrue(tmp && !beep.isActivated() );
    }
}
