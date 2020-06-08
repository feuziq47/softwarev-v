package RDM;

import GUI.MainScreen;
import GUI.ModeIcon;
import GUI.SubScreen;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class RDMSystem {
    public Mode[] getAvailableMode() {
        return availableMode;
    }

    public void setAvailableMode(Mode[] availableMode) {
        this.availableMode = availableMode;
    }

    public boolean[] getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean[] isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setAllMode(Mode[] allMode) {
        this.allMode = allMode;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public void setModeIndex(int modeIndex) {
        this.modeIndex = modeIndex;
    }

    public void setSelectModeIndex(int selectModeIndex) {
        this.selectModeIndex = selectModeIndex;
    }

    public int getAttrIndex() {
        return attrIndex;
    }

    public void setAttrIndex(int attrIndex) {
        this.attrIndex = attrIndex;
    }

    public String[] getTimeKeepingAtt() {
        return timeKeepingAtt;
    }

    public String[] getAlarmAtt() {
        return alarmAtt;
    }

    public boolean isSettingMode() {
        return isSettingMode;
    }

    public void setSettingMode(boolean settingMode) {
        isSettingMode = settingMode;
    }

    public boolean isStopwatchStart() {
        return isStopwatchStart;
    }

    public void setStopwatchStart(boolean stopwatchStart) {
        isStopwatchStart = stopwatchStart;
    }

    public boolean isTimerStart() {
        return isTimerStart;
    }

    public void setTimerStart(boolean timerStart) {
        isTimerStart = timerStart;
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public void setSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
    }

    public void setBeep(Beep beep) {
        this.beep = beep;
    }

    public void setTimeKeepingTime(LocalDateTime timeKeepingTime) {
        this.timeKeepingTime = timeKeepingTime;
    }

    public void setMainString(String mainString) {
        this.mainString = mainString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

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

        TimeKeeping_Callback timeKeepingCallback = new TimeKeeping_Callback(){
            @Override
            public void callbackMethod(){
                if(currentMode instanceof TimeKeeping){
                    processDisplay();
                } else if(currentMode instanceof WorldTime){
                    LocalDateTime tmp = ((TimeKeeping) allMode[0]).getCurrentTime();
                    tmp = tmp.plusHours(((WorldTime)currentMode).getTimeDifference());
                    ((WorldTime)currentMode).setCurrentTime(tmp);
                    processDisplay();
                }
            }
        };

        Timer_Callback timerCallback = new Timer_Callback(){
            @Override
            public void callbackMethod(){
                if(currentMode instanceof Timer){
                    processDisplay();
                }
            }
        };

        StopWatch_Callback stopWatchCallback = new StopWatch_Callback(){
            @Override
            public void callbackMethod(){
                if(currentMode instanceof StopWatch){
                    processDisplay();
                }
            }
        };

        LapTime_Callback lapTimeCallback = new LapTime_Callback(){
            @Override
            public void callbackMethod(){
                if(currentMode instanceof StopWatch){
                    processDisplay();
                }
            }
        };

        Callback beep_callback = new Callback() {
            @Override
            public void callbackMethod() {
                beep.beepStart();
            }
        };

        ((Timer)allMode[2]).setCallback(beep_callback);
        for(int i = 0; i < 4; i++){
            ((Alarm)allMode[3]).getAlarmList(i).setCallback(beep_callback);
        }
        ((TimeKeeping) allMode[0]).setCallback(timeKeepingCallback);
        ((StopWatch) allMode[1]).setCountUpCallback(stopWatchCallback);
        ((StopWatch) allMode[1]).setLapTimeCallback(lapTimeCallback);
        ((Timer) allMode[2]).setTimerCallback(timerCallback);
        ((TimeKeeping) allMode[0]).tictok();

        cd();
    }

    //현재 사용가능한 모드 체크
    public void checkAvailableMode(){
        int index = 0;
        for(int i=0; i<allMode.length;i++){
            if(!isAvailable[i]) {
                continue;
            }
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
        if(beep.isActivated()){
            beep.deactivateBeep();
        }
        else {
            if (isSelectMode) {
                selectMode(buttonInput);
            } else if (currentMode instanceof TimeKeeping) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case "LONG_MO":
                            isSettingMode = !isSettingMode;
                            this.currentTime = ((TimeKeeping) currentMode).getCurrentTime();
                            return;
                        case "MO":
                            changeCurrentMode();
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            break;
                    }
                } else { //세팅모드인 경우
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
            } else if (currentMode instanceof StopWatch) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    if (buttonInput.equals("LONG_MO") && isSettingMode == false) { //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                        isSettingMode = !isSettingMode;
                        return;
                    } else if (buttonInput.equals("MO")) {
                        changeCurrentMode();
                    } else if (buttonInput.equals("ST")) {
                        if (isStopwatchStart) {
                            ((StopWatch) currentMode).pauseStopwatch();
                            isStopwatchStart = !isStopwatchStart;
                        } else {
                            ((StopWatch) currentMode).startStopwatch();
                            isStopwatchStart = !isStopwatchStart;
                        }
                    } else if (buttonInput.equals("RE") && !isStopwatchStart) {
                        ((StopWatch) currentMode).clearStopwatch();
                    } else if (buttonInput.equals("LONG_AD")) {
                        isSelectMode = !isSelectMode;
                    } else if (buttonInput.equals("AD") && isStopwatchStart) { //진행 중일 때만 기록
                        ((StopWatch) currentMode).recordLapTime();
                    }
                } else {//세팅모드인 경우

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
            } else if (currentMode instanceof Timer) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    if (buttonInput.equals("LONG_MO")) {
                        isSettingMode = !isSettingMode;
                        return;
                    } else if (buttonInput.equals("MO")) {
                        changeCurrentMode();
                    } else if (buttonInput.equals("ST")) {
                        if (isTimerStart) {
                            ((Timer) currentMode).pauseTimer();
                            isTimerStart = !isTimerStart;
                        } else {
                            ((Timer) currentMode).startTimer();
                            isTimerStart = !isTimerStart;
                        }
                    } else if (buttonInput.equals("RE") && !isTimerStart) { //중지상태에서만 리셋
                        isTimerStart = false;
                        ((Timer) currentMode).resetTimer();
                    } else if (buttonInput.equals("LONG_AD")) {
                        isSelectMode = !isSelectMode;
                    }
                } else {//세팅모드인 경우

                    //blinkDisplayMain(timeKeepingAtt[attrIndex]
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case "AD":
                            attrIndex++;
                            attrIndex = attrIndex % alarmAtt.length; //이부분 TimerAtt로 바꿔야됨 Att는 각 클래스가 가지고 있는게 좋을듯
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
            } else if (currentMode instanceof Alarm) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case "LONG_MO":  //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                            isSettingMode = !isSettingMode;
                            return;
                        case "MO":
                            changeCurrentMode();
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
                } else {//세팅모드인 경우

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

            } else if (currentMode instanceof DecisionMaker) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case "LONG_MO":  // MO버튼을 길게 눌렀을 때
                            isSettingMode = !isSettingMode;
                            return;
                        case "MO":
                            changeCurrentMode();
                            break;
                        case "ST":
                            ((DecisionMaker) currentMode).getCase();
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            break;
                    }
                } else {//세팅모드인 경우

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
            } else if (currentMode instanceof WorldTime) {
                switch (buttonInput) {
                    case "RE":
                        ((WorldTime) currentMode).upIndex();
                        break;
                    case "ST":
                        ((WorldTime) currentMode).downIndex();
                        break;
                    case "LONG_AD":
                        isSelectMode = !isSelectMode;
                        break;
                    case "MO":
                        changeCurrentMode();
                        break;
                }
            }
        }
//        System.out.println(currentMode.toString());
        processDisplay();
    }
    public void selectMode() {
        // TODO implement here

    }
    public void changeCurrentMode() {
        // TODO implement here
        this.modeIndex += 1;
        this.modeIndex %= 4; //4넘을 경우 처리
        this.currentMode = this.availableMode[modeIndex];
    }
    public void callPrevMode() {
        // TODO implement here

    }

    public String getDayofWeek(String dow){
        switch (dow) {
            case "MONDAY":
                return "월";
            case "THUESDAY":
                return "화";
            case "WEDNESDAY":
                return "수";
            case "THURSDAY":
                return "목";
            case "FRIDAY":
                return "금";
            case "SATURDAY":
                return "토";
            case "SUNDAY":
                return "일";
        }
        return "MON";
    }

    private String makeHtmlFormat(String str){
        return "<html>"+str+"</html>";
    }
    //final private String[] timeKeepingAtt = {"YEAR", "MONTH", "DAY", "HOUR","MIN"};

    private String makeMainDateTimeString(String country, LocalDateTime time, int index, boolean isSettingMode){
        String hour = makeTwoStr(Integer.toString(time.getHour()));
        String min = makeTwoStr(Integer.toString(time.getMinute()));
        String sec = makeTwoStr(Integer.toString(time.getSecond()));
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
                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                returnStr += timeArr[i];
                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        return makeHtmlFormat(country+" "+returnStr);
    }
    private String makeMainDateTimeString(LocalDateTime time, int index, boolean isSettingMode){
        String hour = makeTwoStr(Integer.toString(time.getHour()));
        String min = makeTwoStr(Integer.toString(time.getMinute()));
        String sec = makeTwoStr(Integer.toString(time.getSecond()));
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
                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                returnStr += timeArr[i];
                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        return makeHtmlFormat(returnStr);
    }

    private String makeMainTimeString(LocalTime time, int index, boolean isSettingMode){
        String hour = makeTwoStr(Integer.toString(time.getHour()));
        String min = makeTwoStr(Integer.toString(time.getMinute()));
        String sec = makeTwoStr(Integer.toString(time.getSecond()));
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
                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                returnStr += timeArr[i];
                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        return makeHtmlFormat(returnStr);
    }

    private String makeSubDateTimeString(LocalDateTime time, int index, boolean isSettingMode){
        String year = makeTwoStr(Integer.toString(time.getYear()%100));
        String month = makeTwoStr(Integer.toString(time.getMonth().getValue()));
        String day = makeTwoStr(Integer.toString(time.getDayOfMonth()));
        String dayOfWeek = getDayofWeek(time.getDayOfWeek().toString());

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
        return returnStr + dayOfWeek;
    }

    private String makeMainDateTimeString(LocalTime time, int index){
        String hour = makeTwoStr(Integer.toString(time.getHour()));
        String min = makeTwoStr(Integer.toString(time.getMinute()));
        String sec = makeTwoStr(Integer.toString(time.getSecond()));
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

                if(i != timeArr.length-1) returnStr +=" : ";
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                returnStr += timeArr[i];
                if(i != timeArr.length-1) returnStr +=" : ";
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


    private Color[] iconDisplay(){
        Color[] iconColor = new Color[6];
        int offset = 0;
        for(int i=0; i<allMode.length; i++){
            if(isSelectMode){
                if(i==selectModeIndex){
                    iconColor[i] = Color.GREEN;
                } else if(isAvailable[i]){
                    iconColor[i] = Color.YELLOW;
                } else {
                    iconColor[i] = Color.BLACK;
                }
            }
            else {
                if(!isAvailable[i]) {
                    offset++;
                    iconColor[i] = Color.BLACK;
                    continue;
                };
                if(i==modeIndex+offset){
                    iconColor[i] = Color.GREEN;
                }else{
                    iconColor[i] = Color.BLACK;
                }
            }
        }
        return iconColor;
    }
    public void processDisplay(){
        if(currentMode instanceof TimeKeeping){
            mainString = makeMainDateTimeString(((TimeKeeping) currentMode).getCurrentTime(),attrIndex,isSettingMode);
            subString = makeSubDateTimeString(((TimeKeeping) currentMode).getCurrentTime(),attrIndex,isSettingMode);
            System.out.println(((TimeKeeping) currentMode).getCurrentTime());
            System.out.println("in Timekeeping");
        }
        else if(currentMode instanceof Alarm) {
            LocalTime time = ((Alarm) currentMode).getStaticTime().getAlarmTime();
            mainString = makeMainTimeString(time, attrIndex+3,isSettingMode);
            subString = makeAlarmSubString(((Alarm) currentMode).getIndex(),((Alarm) currentMode).getStaticTime().getIsActivated());
            System.out.println("in Alarm");
        }
        else if(currentMode instanceof  Timer){
            subString = makeSubDateTimeString(getTimeKeepingTime(),3,isSettingMode);
            mainString = makeMainDateTimeString(((Timer) currentMode).getLeftTime(),attrIndex);
            System.out.println("in Timer");
        }
        else if(currentMode instanceof  StopWatch){
            if(isSettingMode){
                mainString = makeTwoStr(((StopWatch) currentMode).getNowLapTime().toString());
            }
            else {
                mainString = makeMainDateTimeString(((StopWatch) currentMode).getStopwatchTime(),100);
            }
            subString = makeSubDateTimeString(getTimeKeepingTime(),100,isSettingMode);
            System.out.println("in StopWatch");
        }
        else if(currentMode instanceof WorldTime){
            LocalDateTime curWorldTime = ((WorldTime) currentMode).getWorldTime();
            mainString = makeMainDateTimeString(((WorldTime) currentMode).getCity(), curWorldTime,100,false);
            subString = makeSubDateTimeString(getTimeKeepingTime(),100,false);
            System.out.println("in WorldTime");
        }
        else if(currentMode instanceof  DecisionMaker){
            if(isSettingMode){
                mainString = makeHtmlFormat(Integer.toString(((DecisionMaker) currentMode).getCaseNum()));
            }
            else{
                mainString = makeHtmlFormat(Integer.toString(((DecisionMaker) currentMode).getCase()));
            }
            subString = makeSubDateTimeString(getTimeKeepingTime(),100,false);
            System.out.println("in DecisionMaker");
        }

        MainScreen.getInstance().display(mainString);
        SubScreen.getInstance().display(subString);
        ModeIcon.getInstance().setModeColor(iconDisplay());

//        System.out.println(mainString);
//        System.out.println(subString);

    }

    public String getMainString(){
        MainScreen.getInstance().display(mainString);
        return mainString;
    }
    public  String getSubString(){
        MainScreen.getInstance().display(subString);
        return subString;
    }

    public int getSelectModeIndex() {
        return selectModeIndex;
    }
    public int getModeIndex(){
        return modeIndex;
    }
    public int getFirstAvaIndex(){
        for(int i=0;i<isAvailable.length;i++){
            if(isAvailable[i]) return i;
        }
        return 0;
    }

    public LocalDateTime getTimeKeepingTime(){
        return ((TimeKeeping)allMode[0]).getCurrentTime();
    }


    public class th3 extends Thread {

        public void run() {

        }
    }

    public String makeTwoStr(String str)
    {
        if(str.length() < 2){
            return "0" + str;
        }
        return str;
    }


    public void cd() {
        java.util.Timer timers = new java.util.Timer();
        timers.schedule(new TimerTask() {
            public void run() {
                System.out.println("=============================================");
                processDisplay();
                System.out.println();
            }
        }, 0, 5000);
    }


    public void selectMode(String buttonInput){
        if (!buttonInput.equals("AD")) {
            if (buttonInput.equals("ST")) {
                isAvailable[selectModeIndex] = !isAvailable[selectModeIndex];
                selectModeIndex += 1;
                selectModeIndex %= allMode.length;
            } else if (buttonInput.equals("MO") && checkAvailableModeNum()) { //MO를 눌렀을 때 선택한 모드 갯수가 4개일 때만
                isSelectMode = !isSelectMode;
                selectModeIndex = 0;
                checkAvailableMode();
                modeIndex = 0;
                this.currentMode = availableMode[0];
                processDisplay();
            }
        } else {
            selectModeIndex += 1;
            selectModeIndex %= allMode.length;
        }
    }

    public Mode[] getAllMode() {
        return allMode;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public Beep getBeep() {
        return beep;
    }

}