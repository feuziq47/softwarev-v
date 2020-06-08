
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalTime;

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

    final private String[] timeKeepingAtt = {"YEAR", "MONTH", "DAY", "HOUR","MIN"};
    final private String[] alarmAtt = {"HOUR", "MIN", "SEC"};

    private boolean isSettingMode;
    private boolean isStopwatchStart = false;
    private boolean isTimerStart = false;
    private boolean isSelectMode = false;
    private Beep beep;

    private LocalDateTime timeKeepingTime;
    private String mainString;
    private String subString;
    private Mode currentMode;
    /**
     * Default constructor
     */
    public RDMSystem() {
        this.allMode = new Mode[]{new TimeKeeping(), new StopWatch(), new Timer(), new Alarm(), new DecisionMaker(), new WorldTime()};
        this.isAvailable = new boolean[]{true,true,true,true,false,false};
        this.isSettingMode = false;
        this.modeIndex = 0;
        this.beep = new Beep();
        this.availableMode = new Mode[4];
        checkAvailableMode();
        this.currentMode = availableMode[modeIndex];
        this.timeKeepingTime = LocalDateTime.now();
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
        currentMode = availableMode[modeIndex];
        if(isSelectMode){
            if (!buttonInput.equals("AD")) {
                if(buttonInput.equals("ST")) {
                    isAvailable[selectModeIndex] = !isAvailable[selectModeIndex];
                    selectModeIndex+=1;
                    selectModeIndex %= allMode.length;
                } else if(buttonInput.equals("MO") && checkAvailableModeNum()){ //MO를 눌렀을 때 선택한 모드 갯수가 4개일 때만
                    isSelectMode = !isSelectMode;
                    selectModeIndex = 0;
                    checkAvailableMode();
                }
            } else {
                selectModeIndex +=1;
                selectModeIndex %= allMode.length;
            }
        } else if(currentMode instanceof TimeKeeping){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "LONG_MO":
                        isSettingMode = !isSettingMode;
                        this.currentTime = ((TimeKeeping) currentMode).getCurrentTime();
                        return;
                    case "MO":
                        switchCurrentMode();
                        break;
                    case "LONG_AD":
                        isSelectMode = !isSelectMode;
                        break;
                }
            }
            else { //세팅모드인 경우
                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "AD":
                        attrIndex++;
                        attrIndex = attrIndex % timeKeepingAtt.length; //분 -> year

                        break;
                    case "RE":
                        ((TimeKeeping) currentMode).increase(timeKeepingAtt[attrIndex]);
                        break;
                    case "ST":
                        ((TimeKeeping) currentMode).decrease(timeKeepingAtt[attrIndex]);
                        break;
                    case "MO":
                        isSettingMode = !isSettingMode;
                        attrIndex = 0;
                        break;
                }
            }
            timeKeepingTime = ((TimeKeeping) currentMode).getCurrentTime();
        }else if(currentMode instanceof StopWatch){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput.equals("LONG_MO") && isSettingMode == false) { //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput.equals("MO")) {
                    switchCurrentMode();

                } else if(buttonInput.equals("ST")){
                    if(isStopwatchStart){
                        ((StopWatch) currentMode).pauseStopwatch();
                        isStopwatchStart = !isStopwatchStart;
                    } else {
                        ((StopWatch) currentMode).startStopwatch();
                        isStopwatchStart = !isStopwatchStart;
                    }
                } else if(buttonInput.equals("RE") && !isStopwatchStart) {
                    ((StopWatch) currentMode).clearStopwatch();
                } else if (buttonInput.equals("LONG_AD")){
                    isSelectMode = !isSelectMode;
                } else if(buttonInput.equals("AD") && isStopwatchStart) { //진행 중일 때만 기록
                    ((StopWatch) currentMode).recordLapTime();
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "RE":
                        ((StopWatch) currentMode).getLaptime("up");
                        break;
                    case "ST":
                        ((StopWatch) currentMode).getLaptime("down");
                        break;
                    case "MO":
                        isSettingMode = !isSettingMode;
                        break;
                }
            }
        } else if(currentMode instanceof Timer) {
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                if(buttonInput.equals("LONG_MO")) {
                    isSettingMode = !isSettingMode;
                    return;
                } else if(buttonInput.equals("MO")) {
                    switchCurrentMode();
                } else if(buttonInput.equals("ST")){
                    if(isTimerStart){
                        ((Timer) currentMode).pauseTimer();
                        isTimerStart = !isTimerStart;
                    } else {
                        ((Timer) currentMode).startTimer();
                        isTimerStart = !isTimerStart;
                    }
                } else if(buttonInput.equals("RE") && !isTimerStart) { //중지상태에서만 리셋
                    isTimerStart = false;
                    ((Timer) currentMode).resetTimer();
                } else if (buttonInput.equals("LONG_AD")){
                    isSelectMode = !isSelectMode;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "AD":
                        attrIndex++;
                        attrIndex = attrIndex % timeKeepingAtt.length; //이부분 TimerAtt로 바꿔야됨 Att는 각 클래스가 가지고 있는게 좋을듯
                        break;
                    case "RE":
                        ((Timer) currentMode).increase(alarmAtt[attrIndex]); //클래스 내에서 시분 구분해야함

                        break;
                    case "ST":
                        ((Timer) currentMode).decrease(alarmAtt[attrIndex]);
                        break;
                    case "MO":
                        isSettingMode = !isSettingMode;
                        attrIndex = 0;
                        break;
                }
            }
        } else if(currentMode instanceof Alarm){
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "LONG_MO":  //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                        isSettingMode = !isSettingMode;
                        return;
                    case "MO":
                        switchCurrentMode();
                        break;
                    case "ST":
                        ((Alarm) currentMode).selectAlarm("DOWN");
                        break;
                    case "RE":
                        ((Alarm) currentMode).selectAlarm("UP");
                        break;
                    case "LONG_AD":
                        isSelectMode = !isSelectMode;
                        break;
                    case "AD":
                        ((Alarm) currentMode).switchAlarmStatus();
                        break;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "AD":
                        attrIndex++;
                        attrIndex = attrIndex % alarmAtt.length;
                        break;
                    case "RE":
                        ((Alarm) currentMode).increase(alarmAtt[attrIndex]); //클래스 내에서 시분 구분해야함
                        break;
                    case "ST":
                        ((Alarm) currentMode).decrease(alarmAtt[attrIndex]);
                        break;
                    case "MO":
                        isSettingMode = !isSettingMode;
                        attrIndex = 0;
                        break;
                }
            }

        } else if(currentMode instanceof DecisionMaker) {
            if(!isSettingMode) {  //세팅모드가 아닐 떄
                //displayMain(currentMode,isSettingMode)
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "LONG_MO":  // MO버튼을 길게 눌렀을 때
                        isSettingMode = !isSettingMode;
                        return;
                    case "MO":
                        switchCurrentMode();
                        break;
                    case "ST":
                        ((DecisionMaker) currentMode).getCase();
                        break;
                    case "LONG_AD":
                        isSelectMode = !isSelectMode;
                        break;
                }
            }
            else {//세팅모드인 경우

                //blinkDisplayMain(timeKeepingAtt[attrIndex]
                //displayIcon(currentMode,isSettingMode)
                //diplayTopRight(currentMode,isSettingMode)
                switch (buttonInput) {
                    case "RE":
                        ((DecisionMaker) currentMode).increase();
                        break;
                    case "ST":
                        ((DecisionMaker) currentMode).decrease();
                        break;
                    case "MO":
                        isSettingMode = !isSettingMode;
                        break;
                }
            }
        } else if(currentMode instanceof WorldTime) {
            switch (buttonInput) {
                case "RE":
                    ((WorldTime) currentMode).getWorldTime(currentTime,"UP");
                    break;
                case "ST":
                    ((WorldTime) currentMode).getWorldTime(currentTime,"DOWN");
                    break;
                case "LONG_AD":
                    isSelectMode = !isSelectMode;
                    break;
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

    private String makeHtmlFormat(String str){
        return "<html>"+str+"</html>";
    }
    //final private String[] timeKeepingAtt = {"YEAR", "MONTH", "DAY", "HOUR","MIN"};

    private String makeMainDateTimeString(LocalDateTime time, int index, boolean isSettingMode){
        String hour = Integer.toString(time.getHour());
        String min = Integer.toString(time.getMinute());
        String sec = Integer.toString(time.getSecond());
        String[] timeArr = new String[]{hour,min,sec};

        String returnStr = "";


        if(isSettingMode && index > 2){
            for(int i=0; i<timeArr.length; i++){
                if(i == index-3){ //3 = Hour, 4 = Min
                    returnStr += "<font color='red'>" + timeArr[i] + "</font>";
                }
                else{
                    returnStr += timeArr[i];
                }
                returnStr +=" : ";
             }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                returnStr += timeArr[i];
                returnStr +=" : ";
            }
        }
        return makeHtmlFormat(returnStr);
    }

    private String makeSubDateTimeString(LocalDateTime time, int index, boolean isSettingMode){
        String year = Integer.toString(time.getYear());
        String month = time.getMonth().toString();
        String day = time.getDayOfWeek().toString();
        String[] timeArr = new String[]{year, month, day};

        String returnStr = "";

        if(isSettingMode && index <= 2){
            for(int i=0; i<timeArr.length; i++){
                if(i == index){
                    returnStr += "<font color='red'>" + timeArr[i] + "</font>";
                }
                else{
                    returnStr += timeArr[i];
                }
                returnStr +=" ";
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){
                returnStr += timeArr[i];
                returnStr +=" ";
            }
        }
        return returnStr;
    }

    private String makeMainDateTimeString(LocalTime time, int index){
        String hour = Integer.toString(time.getHour());
        String min = Integer.toString(time.getMinute());
        String sec = Integer.toString(time.getSecond());
        String[] timeArr = new String[]{hour,min,sec};

        String returnStr = "";


        if(isSettingMode){
            for(int i=0; i<timeArr.length; i++){
                if(i == index){
                    returnStr += "<font color='red'>" + timeArr[i] + "</font>";
                }
                else{
                    returnStr += timeArr[i];
                }
                returnStr +=" : ";
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                returnStr += timeArr[i];
                returnStr +=" : ";
            }
        }
        return makeHtmlFormat(returnStr);
    }


    public String makeAlarmSubString(int alarmIndex, boolean isActivate){
        if(isActivate){
            return alarmIndex + " / " + "AC";
        }
        return alarmIndex + " / " + "DA";
    }



    public void processDisplay(){
        if(currentMode instanceof TimeKeeping){
            mainString = makeMainDateTimeString(((TimeKeeping) currentMode).getCurrentTime(),attrIndex,isSettingMode);
            subString = makeSubDateTimeString(((TimeKeeping) currentMode).getCurrentTime(),attrIndex,isSettingMode);
        }
        else if(currentMode instanceof  Alarm) {
            LocalDateTime time = ((Alarm) currentMode).getStaticTime().getAlarmTime();
            mainString = makeMainDateTimeString(time, attrIndex+3,isSettingMode);
            subString = makeAlarmSubString(((Alarm) currentMode).getIndex(),((Alarm) currentMode).getStaticTime().getIsActivated());
        }
        else if(currentMode instanceof  Timer){
            subString = makeSubDateTimeString(getTimeKeepingTime(),3,isSettingMode);
            mainString = makeMainDateTimeString(((Timer) currentMode).getCurrentTime(),attrIndex);
        }
        else if(currentMode instanceof  StopWatch){
            if(isSettingMode){
                mainString = ((StopWatch) currentMode).getNowLapTime().toString();
            }
            else {
                mainString = makeMainDateTimeString(((StopWatch) currentMode).getStopwatchTime(),100);
            }
            subString = makeSubDateTimeString(getTimeKeepingTime(),100,isSettingMode);
        }
        else if(currentMode instanceof WorldTime){
            LocalDateTime curWorldTime = ((WorldTime) currentMode).getWorldTime(getTimeKeepingTime(),"NOW");
            mainString = ((WorldTime) currentMode).getCity() + makeMainDateTimeString(curWorldTime,100,false);
            subString = makeSubDateTimeString(getTimeKeepingTime(),100,false);
        }
        else if(currentMode instanceof  DecisionMaker){
            if(isSettingMode){
                mainString = makeHtmlFormat(Integer.toString(((DecisionMaker) currentMode).getCaseNum()));
            }
            else{
                mainString = makeHtmlFormat(Integer.toString(((DecisionMaker) currentMode).getCase()));
            }
            subString = makeSubDateTimeString(getTimeKeepingTime(),100,false);
        }

    }

    public String getMainString(){
        return mainString;
    }
    public  String getSubString(){
        return subString;
    }

    public int getSelectModeIndex() {
        return selectModeIndex;
    }
    public int getModeIndex(){
        return modeIndex;
    }

    public LocalDateTime getTimeKeepingTime(){
        return ((TimeKeeping)allMode[0]).getCurrentTime();
    }







}
