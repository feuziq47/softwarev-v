
import java.time.LocalDateTime;
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
   // private Mode[] disable;

    private LocalDateTime currentTime;

    private int modeIndex;
    private int selectModeIndex = 0;
    private int attrIndex = 0; //각 클래스의 attr인덱스 ex Year,Month
    private int worldTimeIndex = 0;

    final private String[] timeKeepingAtt = {"YEAR","MONTH", "DAY", "HOUR","MIN"};
    final private String[] alarmAtt = {"HOUR", "MIN", "SEC"};

    private boolean isSettingMode;
    private boolean isStopwatchStart = false;
    private boolean isTimerStart = false;
    private boolean isSeleteMode = false;
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


    public void decodeButtonInput(String buttonInput) {
        Mode currentMode = availableMode[modeIndex];
        if(isSeleteMode){
            if(buttonInput == "AD") {
                selectModeIndex +=1;
                selectModeIndex %= allMode.length;
            } else if(buttonInput == "ST") {
                isAvailable[selectModeIndex] = !isAvailable[selectModeIndex];
                selectModeIndex+=1;
                selectModeIndex %= allMode.length;
            } else if(buttonInput == "MO" && checkAvailableModeNum()){ //MO를 눌렀을 때 선택한 모드 갯수가 4개일 때만
                isSeleteMode = !isSeleteMode;
            }
        } else if(currentMode instanceof TimeKeeping){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "LONG_MO") {
                    isSettingMode = !isSettingMode;
                    this.currentTime = ((TimeKeeping) currentMode).getCurrentTime();
                    return;
                } else if(buttonInput == "MO") {
                    modeIndex += 1;
                    modeIndex %= 4; //4넘을 경우 처리
                } else if (buttonInput == "LONG_AD"){
                    isSeleteMode = !isSeleteMode;
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
                    attrIndex = 0;
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
                    switchCurrentMode();

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
                } else if (buttonInput == "LONG_AD"){
                    isSeleteMode = !isSeleteMode;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "RE") {
                    ((StopWatch) currentMode).getLaptime("up");
                } else if(buttonInput == "ST") {
                    ((StopWatch) currentMode).getLaptime("down");
                } else if(buttonInput == "MO"){
                    isSettingMode = !isSettingMode;
                }
            }
        } else if(currentMode instanceof Timer) {
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "LONG_MO") { //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput == "MO") {
                    switchCurrentMode();
                } else if(buttonInput == "ST"){
                    if(isStopwatchStart){
                        ((Timer) currentMode).pauseTimer();
                        isTimerStart = !isTimerStart;
                    } else {
                        ((Timer) currentMode).startTimer();
                        isTimerStart = !isTimerStart;
                    }
                } else if(buttonInput == "RE") {
                    isStopwatchStart = false;
                    ((Timer) currentMode).resetTimer();
                } else if (buttonInput == "LONG_AD"){
                    isSeleteMode = !isSeleteMode;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "AD") {
                    attrIndex++;
                    attrIndex = attrIndex % timeKeepingAtt.length; //이부분 TimerAtt로 바꿔야됨 Att는 각 클래스가 가지고 있는게 좋을듯
                } else if(buttonInput == "RE") {
                    ((Timer) currentMode).increase(); //클래스 내에서 시분 구분해야함
                } else if(buttonInput == "ST") {
                    ((Timer) currentMode).decrease();
                } else if(buttonInput == "MO"){
                    isSettingMode = !isSettingMode;
                    attrIndex = 0;
                }
            }
        } else if(currentMode instanceof Alarm){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "LONG_MO") { //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput == "MO") {
                    switchCurrentMode();
                } else if(buttonInput == "ST"){
                    ((Alarm) currentMode).selectAlarm("DOWN");
                } else if(buttonInput == "RE") {
                    ((Alarm) currentMode).selectAlarm("UP");
                } else if (buttonInput == "LONG_AD"){
                    isSeleteMode = !isSeleteMode;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "AD") {
                    attrIndex++;
                    attrIndex = attrIndex % alarmAtt.length;
                } else if(buttonInput == "RE") {
                    ((Alarm) currentMode).increase(alarmAtt[attrIndex]); //클래스 내에서 시분 구분해야함
                } else if(buttonInput == "ST") {
                    ((Alarm) currentMode).decrease(alarmAtt[attrIndex]);
                } else if(buttonInput == "MO"){
                    isSettingMode = !isSettingMode;
                    attrIndex = 0;
                }
            }

        } else if(currentMode instanceof DecisionMaker) {
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "LONG_MO") { // MO버튼을 길게 눌렀을 때
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput == "MO") {
                    switchCurrentMode();
                } else if(buttonInput == "ST"){
                    ((DecisionMaker) currentMode).getCase();
                } else if (buttonInput == "LONG_AD"){
                    isSeleteMode = !isSeleteMode;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput == "RE") {
                    ((DecisionMaker) currentMode).increase();
                } else if(buttonInput == "ST") {
                    ((DecisionMaker) currentMode).decrease();
                } else if(buttonInput == "MO"){
                    isSettingMode = !isSettingMode;
                }
            }
        } else if(currentMode instanceof WorldTime) {
            if(buttonInput == "RE") {
                worldTimeIndex++;
            } else if(buttonInput == "ST") {
                worldTimeIndex--;
            }
        }
    }
    public void selectMode() {
        // TODO implement here

    }
    public void switchCurrentMode() {
        // TODO implement here
        this.modeIndex += 1;
        this.modeIndex %= 4; //4넘을 경우 처리

    }
    public void callPrevMode() {
        // TODO implement here

    }

}
