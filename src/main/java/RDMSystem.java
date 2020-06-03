
import java.util.*;

/**
 *
 */
public class RDMSystem {


    private Mode[] availableMode;

    //add
    private boolean[] isAvailable;

    //add
    private Mode[] allMode;
    /**
     *
     */
    private Mode[] disable;

    /**
     *
     */
    private int modeIndex;
    private int attrIndex = 0; //각 클래스의 attr인덱스 ex Year,Month

    final private String[] timeKeepingAtt = {"YEAR","MONTH", "DAY", "HOUR","MIN"};

    private boolean isSettingMode;
    private boolean isStopwatchStart = false;
    /**
     * Default constructor
     */
    public RDMSystem() {
        this.allMode = new Mode[]{new TimeKeeping(), new StopWatch(), new Timer(), new Alarm(), new DecisionMaker(), new WorldTime()};
        this.isAvailable = new boolean[]{true,true,true,true,false,false};
        checkAvailableMode();
        this.isSettingMode = false;
        this.modeIndex = 0;
    }

    //현재 사용가능한 모드 체크
    public void checkAvailableMode(){
        int index = 0;
        for(int i=0; i<allMode.length;i++){
            if(!isAvailable[i]) continue;
            availableMode[index] = allMode[i];
            index++;
        }
    }

    //모드가 4개인지 확인하는 함수
    public boolean checkAvailableModeNum(){
        int count = 0;
        for(int i=0; i<isAvailable.length; i++) {
            if(isAvailable[i]) count++;
        }
        if(count==4) return true;
        return false;
    }

    /**
     *
     */






    /**
     * @param buttonInput
     * @return
     */

    public void decodeButtonInput(String buttonInput) {
        // TODO implement here
        Mode currentMode = availableMode[modeIndex];
        if(currentMode instanceof TimeKeeping){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "LONG_MO") {
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput == "MO") {
                    modeIndex += 1;
                    modeIndex %= 4; //4넘을 경우 처리
                }
            }
            else { //세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "AD") {
                    attrIndex++;
                    attrIndex = attrIndex % timeKeepingAtt.length; //분 -> year
                } else if(buttonInput == "RE") {
                    ((TimeKeeping) currentMode).increase(timeKeepingAtt[attrIndex]);
                } else if(buttonInput == "ST") {
                    ((TimeKeeping) currentMode).decrease(timeKeepingAtt[attrIndex]);
                } else if(buttonInput == "MO"){
                    isSettingMode = !isSettingMode;
                }
            }
        }else if(currentMode instanceof StopWatch){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "LONG_MO" && isSettingMode == false) { //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput == "MO") {
                    modeIndex += 1;
                    modeIndex %= 4; //4넘을 경우 처리
                } else if(buttonInput == "ST"){
                    if(isStopwatchStart){
                        ((StopWatch) currentMode).pauseStopwatch();
                        isStopwatchStart = !isStopwatchStart;
                    } else {
                        ((StopWatch) currentMode).startStopwatch();
                        isStopwatchStart = !isStopwatchStart;
                    }
                } else if(buttonInput == "RE") {
                    isStopwatchStart = false;
                    ((StopWatch) currentMode).clearStopwatch();
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "AD") {
                    attrIndex++;
                    attrIndex = attrIndex % timeKeepingAtt.length; //분 -> year
                } else if(buttonInput == "RE") {
                    ((StopWatch) currentMode).getLapTime("UP");
                } else if(buttonInput == "ST") {
                    ((StopWatch) currentMode).getLapTime("DOWN");
                } else if(buttonInput == "MO"){
                    isSettingMode = !isSettingMode;
                }
            }
        } else if
    }

    /**
     * @return
     */
    public void selectMode() {
        // TODO implement here

    }

    /**
     * @return
     */
    public void switchCurrentMode() {
        // TODO implement here

    }

    /**
     * @return
     */
    public void callPrevMode() {
        // TODO implement here

    }

}
