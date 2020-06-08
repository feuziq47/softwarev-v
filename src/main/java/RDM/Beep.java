package RDM;

import java.util.*;
import java.awt.Toolkit;

/**
 *
 */
public class Beep extends Thread {

    /**
     * Default constructor
     */
    public Beep() {
        beepTool = Toolkit.getDefaultToolkit();
        activated = false;
    }

    /**
     *  변경사항  : Beep의 Atribute Toolkit이 추가됨.
     */
    private boolean activated;
    private Toolkit beepTool;

    /**
     * ActivateBeep은 우리와 함께하지 못하게 되었습니다.
     * run으로 변경합니다.
     * beep은 0.5초에 한번씩 울림
     * activated가 true인 이상 계속해서 Beep이 울림
     * beepd이 작동하는 와중에 deactivateBeep이 작동하면 activated는 false로 변경되고
     * 예외를 던지며 Beep이 종료됨.
     */
    public void run() {
        // TODO implement here
        activated = true;
        while(true) {
            beepTool.beep();
            try {
                if(activated) {
                    Thread.sleep(500);
                }
                else{
                    Exception e = new Exception("Stop Signal");
                    throw e;
                }
            } catch(Exception e) {
                break;
            }
        }
    }

    /**
     * @return
     */
    public void deactivateBeep() {
        // TODO implement here
        activated = false;
    }

    /**
     * @return
     */
    public boolean isActivated() {
        // TODO implement here
        return activated;
    }

}