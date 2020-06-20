package RDM;

import GUI.MainScreen;
import GUI.ModeIcon;
import GUI.SubScreen;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimerTask;

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


    final private int timeKeepingIndex = 0;
    final private int stopwatchIndex = 1;
    final private int timerIndex = 2;
    final private int alarmIndex = 3;
    private int modeIndex;
    private int selectModeIndex = 0;
    private int attrIndex = 0; //각 클래스의 attr인덱스 ex Year,Month

    final private String[] timeKeepingAtt = {"YEAR", "MONTH", "DAY", "HOUR","MIN"};
    final private String[] alarmAtt = {"HOUR", "MIN", "SEC"};
    final private String LONG_MO = "LONG_MO";
    final private String MO = "MO";
    final private String AD = "AD";
    final private String ST = "ST";
    final private String RE = "RE";


    private boolean isSettingMode;
    private boolean isStopwatchStart = false;
    private boolean isTimerStart = false;
    private boolean isSelectMode = false;
    private Beep beep;

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

        Timer_Beep_Callback beep_callback = new Timer_Beep_Callback() {
            @Override
            public void callbackMethod() {
                if(isAvailable[timerIndex] == true && ((Timer) allMode[timerIndex]).getCurrentTime() != LocalTime.of(0,0,0)) {
                    if (beep.isActivated()) {
                    } else {
                        beep.beepStart();
                    }
                }
            }
        };

        Alarm_Beep_Callback alarm_beep_callback = new Alarm_Beep_Callback() {
            @Override
            public void callbackMethod() {
                if(isAvailable[alarmIndex] == true) {
                    if (beep.isActivated()) {
                    } else {
                        beep.beepStart();
                    }
                }
            }
        };

        ((Timer)allMode[timerIndex]).setCallback(beep_callback);
        for(int i = 0; i < 4; i++){
            ((Alarm)allMode[alarmIndex]).getAlarmList(i).setCallback(alarm_beep_callback);
        }
        ((TimeKeeping) allMode[timeKeepingIndex]).setCallback(timeKeepingCallback);
        ((StopWatch) allMode[stopwatchIndex]).setCountUpCallback(stopWatchCallback);
        ((StopWatch) allMode[stopwatchIndex]).setLapTimeCallback(lapTimeCallback);
        ((Timer) allMode[timerIndex]).setTimerCallback(timerCallback);
        ((TimeKeeping) allMode[timeKeepingIndex]).tictok();

    }

    //현재 사용가능한 모드 체크
    final public void checkAvailableMode(){
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
                        case LONG_MO:
                            isSettingMode = !isSettingMode;
                            this.currentTime = ((TimeKeeping) currentMode).getCurrentTime();
                        case MO:
                            changeCurrentMode();
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            break;
                        default:
                            break;
                    }
                } else { //세팅모드인 경우
                    switch (buttonInput) {
                        case AD:
                            attrIndex++;
                            attrIndex = attrIndex % timeKeepingAtt.length; //분 -> year

                            break;
                        case RE:
                            ((TimeKeeping) currentMode).increase(timeKeepingAtt[attrIndex]);
                            break;
                        case ST:
                            ((TimeKeeping) currentMode).decrease(timeKeepingAtt[attrIndex]);
                            break;
                        case MO:
                            isSettingMode = !isSettingMode;
                            attrIndex = 0;
                            break;
                        default:
                            break;
                    }
                }
            } else if (currentMode instanceof StopWatch) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case LONG_MO:  //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                            if (isSettingMode == false) {
                                isSettingMode = !isSettingMode;
                            }
                            break;
                        case MO:
                            changeCurrentMode();
                            break;
                        case ST:
                            if (isStopwatchStart) {
                                ((StopWatch) currentMode).pauseStopwatch();
                                isStopwatchStart = !isStopwatchStart;
                            } else {
                                ((StopWatch) currentMode).startStopwatch();
                                isStopwatchStart = !isStopwatchStart;
                            }
                            break;
                        case RE:
                            if (!isStopwatchStart) {
                                ((StopWatch) currentMode).clearStopwatch();
                            }
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            break;
                        case AD:  //진행 중일 때만 기록
                            if (isStopwatchStart) {
                                ((StopWatch) currentMode).recordLapTime();

                            }
                            break;
                        default:
                            break;
                    }
                } else {//세팅모드인 경우
                    switch (buttonInput) {
                        case RE:
                            ((StopWatch) currentMode).getLapTime("up");
                            break;
                        case ST:
                            ((StopWatch) currentMode).getLapTime("down");
                            break;
                        case MO:
                            isSettingMode = !isSettingMode;
                            break;
                        default:
                            break;
                    }
                }
            } else if (currentMode instanceof Timer) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    switch (buttonInput) {
                        case LONG_MO:
                            isSettingMode = !isSettingMode;
                            break;
                        case MO:
                            changeCurrentMode();
                            break;
                        case ST:
                            if (((Timer) currentMode).isTimerStart()) {
                                ((Timer) currentMode).pauseTimer();
                            } else {
                                ((Timer) currentMode).startTimer();
                            }
                            break;
                        case RE:  //중지상태에서만 리셋
                            if (!((Timer) currentMode).isTimerStart()) {
                                ((Timer) currentMode).resetTimer();
                            }
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            break;
                        default:
                            break;
                    }
                } else {//세팅모드인 경우

                    switch (buttonInput) {
                        case AD:
                            attrIndex++;
                            attrIndex = attrIndex % alarmAtt.length; //이부분 TimerAtt로 바꿔야됨 Att는 각 클래스가 가지고 있는게 좋을듯
                            break;
                        case RE:
                            ((Timer) currentMode).increase(alarmAtt[attrIndex]); //클래스 내에서 시분 구분해야함

                            break;
                        case ST:
                            ((Timer) currentMode).decrease(alarmAtt[attrIndex]);
                            break;
                        case MO:
                            isSettingMode = !isSettingMode;
                            attrIndex = 0;
                            break;
                        default:
                            break;
                    }
                }
            } else if (currentMode instanceof Alarm) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    switch (buttonInput) {
                        case LONG_MO:  //일시정지 상태이고, MO버튼을 길게 눌렀을 때
                            isSettingMode = !isSettingMode;
                            break;
                        case MO:
                            changeCurrentMode();
                            break;
                        case ST:
                            ((Alarm) currentMode).selectAlarm("DOWN");
                            break;
                        case RE:
                            ((Alarm) currentMode).selectAlarm("UP");
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            break;
                        case AD:
                            ((Alarm) currentMode).switchAlarmStatus();
                            break;
                        default:
                            break;
                    }
                } else {//세팅모드인 경우

                    //blinkDisplayMain(timeKeepingAtt[attrIndex]
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case AD:
                            attrIndex++;
                            attrIndex = attrIndex % alarmAtt.length;
                            break;
                        case RE:
                            ((Alarm) currentMode).increase(alarmAtt[attrIndex]); //클래스 내에서 시분 구분해야함
                            break;
                        case ST:
                            ((Alarm) currentMode).decrease(alarmAtt[attrIndex]);
                            break;
                        case MO:
                            isSettingMode = !isSettingMode;
                            ((Alarm) currentMode).getStaticTime().setIsAlreadyNotified();
                            attrIndex = 0;
                            break;
                        default:
                            break;
                    }
                }

            } else if (currentMode instanceof DecisionMaker) {
                if (!isSettingMode) {  //세팅모드가 아닐 떄
                    //displayMain(currentMode,isSettingMode)
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case LONG_MO:  // MO버튼을 길게 눌렀을 때
                            isSettingMode = !isSettingMode;
                            ((DecisionMaker) currentMode).setIsInitialized(true);
                            break;
                        case MO:
                            changeCurrentMode();
                            ((DecisionMaker) currentMode).setIsInitialized(true);
                            break;
                        case ST:
                            ((DecisionMaker) currentMode).getCase();
                            ((DecisionMaker) currentMode).setIsInitialized(false);
                            break;
                        case "LONG_AD":
                            isSelectMode = !isSelectMode;
                            ((DecisionMaker) currentMode).setIsInitialized(true);
                            break;
                        default:
                            break;
                    }
                } else {//세팅모드인 경우

                    //blinkDisplayMain(timeKeepingAtt[attrIndex]
                    //displayIcon(currentMode,isSettingMode)
                    //diplayTopRight(currentMode,isSettingMode)
                    switch (buttonInput) {
                        case RE:
                            ((DecisionMaker) currentMode).increase();
                            break;
                        case ST:
                            ((DecisionMaker) currentMode).decrease();
                            break;
                        case MO:
                            isSettingMode = !isSettingMode;
                            break;
                        default:
                            break;
                    }
                }
            } else if (currentMode instanceof WorldTime) {
                switch (buttonInput) {
                    case RE:
                        ((WorldTime) currentMode).upIndex();
                        break;
                    case ST:
                        ((WorldTime) currentMode).downIndex();
                        break;
                    case LONG_MO:
                        isSelectMode = !isSelectMode;
                        break;
                    case MO:
                        changeCurrentMode();
                        break;
                    default:
                        break;
                }
            } else {

            }
        }
//        System.out.println(currentMode.toString());
        processDisplay();
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
            case "TUESDAY":
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
        return "월";
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

        StringBuffer buf = new StringBuffer();

        if(isSettingMode && index > 2){
            for(int i=0; i<timeArr.length; i++){
                if(i == index-3){ //3 = Hour, 4 = Min
                    buf.append("<font color='red'>" + timeArr[i] + "</font>");
                }
                else{
                    buf.append(timeArr[i]);
                }
                if(i != timeArr.length-1) buf.append(" : ");
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                buf.append(timeArr[i]);
                if(i != timeArr.length-1) buf.append(" : ");
            }
        }
        return makeHtmlFormat(buf.toString());
    }

    private String makeSubDateTimeString(LocalDateTime time, int index, boolean isSettingMode){
        String year = makeTwoStr(Integer.toString(time.getYear()%100));
        String month = makeTwoStr(Integer.toString(time.getMonth().getValue()));
        String day = makeTwoStr(Integer.toString(time.getDayOfMonth()));
        String dayOfWeek = getDayofWeek(time.getDayOfWeek().toString());

        String[] timeArr = new String[]{year, month, day};

//        String returnStr = "";
        StringBuffer buf = new StringBuffer();

        if(isSettingMode && index <= 2){
            for(int i=0; i<timeArr.length; i++){
                if(i == index){
//                    returnStr += "<font color='red'>" + timeArr[i] + "</font>";
                    buf.append("<font color='red'>" + timeArr[i] + "</font>");
                }
                else{
//                    returnStr += timeArr[i];
                    buf.append(timeArr[i]);
                }
//                returnStr +=" ";
                buf.append(" ");
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){
//                returnStr += timeArr[i];
//                returnStr +=" ";
                buf.append(timeArr[i]);
                buf.append(" ");
            }
        }
        buf.append(dayOfWeek);
        return makeHtmlFormat(buf.toString());
    }

    private String makeMainDateTimeString(LocalTime time, int index){
        String hour = makeTwoStr(Integer.toString(time.getHour()));
        String min = makeTwoStr(Integer.toString(time.getMinute()));
        String sec = makeTwoStr(Integer.toString(time.getSecond()));
        String[] timeArr = new String[]{hour,min,sec};

//        String returnStr = "";
        StringBuffer buf = new StringBuffer();



        if(isSettingMode){
            for(int i=0; i<timeArr.length; i++){
                if(i == index){
                    buf.append("<font color='red'>" + timeArr[i] + "</font>");
                }
                else{
                    buf.append(timeArr[i]);
                }

                if(i != timeArr.length-1) buf.append(" : ");
            }
        }
        else{
            for(int i=0; i<timeArr.length; i++){

                buf.append(timeArr[i]);
                if(i != timeArr.length-1) buf.append(" : ");
            }
        }
        return makeHtmlFormat(buf.toString());
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
    public String lapTimeString(LocalTime time, int index){
        String hour = makeTwoStr(Integer.toString(time.getHour()));
        String min = makeTwoStr(Integer.toString(time.getMinute()));
        String sec = makeTwoStr(Integer.toString(time.getSecond()));
        String[] timeArr = new String[]{hour,min,sec};

        StringBuffer buf = new StringBuffer();

        for(int i=0; i<timeArr.length; i++){

            buf.append(timeArr[i]);

            if(i != timeArr.length-1) buf.append(" : ");
        }
        return Integer.toString(index+1) + ". " + buf.toString();
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
            if(isSettingMode) {
                LocalTime tmptime = ((Timer) currentMode).getCurrentTime();
                subString = makeSubDateTimeString(getTimeKeepingTime(),3,isSettingMode);
                mainString = makeMainDateTimeString(tmptime,attrIndex);
                System.out.println("in Timer");
            } else {
                LocalTime tmptime = ((Timer) currentMode).getLeftTime();
                subString = makeSubDateTimeString(getTimeKeepingTime(), 3, isSettingMode);
                mainString = makeMainDateTimeString(tmptime, attrIndex);
                System.out.println("in Timer");
            }
        }
        else if(currentMode instanceof  StopWatch){
            if(isSettingMode){
                if(((StopWatch) currentMode).getNowLapTime() == null){
                    mainString = "no lap time";
                } else{
                    mainString = lapTimeString(((StopWatch) currentMode).getNowLapTime(),((StopWatch) currentMode).getIndex());
                }
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
                mainString = makeHtmlFormat("Limit : " +Integer.toString(((DecisionMaker) currentMode).getCaseNum()));
            }
            else if(((DecisionMaker)currentMode).isInitialized()){
                mainString = makeHtmlFormat("DecisionMaker : "+Integer.toString(((DecisionMaker) currentMode).getCaseNum()));
            }
            else{
                mainString = makeHtmlFormat("Case : "+Integer.toString(((DecisionMaker) currentMode).getRandNum()));
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



    public String makeTwoStr(String str)
    {
        if(str.length() < 2){
            return "0" + str;
        }
        return str;
    }





    public void selectMode(String buttonInput){
        if (!buttonInput.equals(AD)) {
            if (buttonInput.equals(ST)) {
                isAvailable[selectModeIndex] = !isAvailable[selectModeIndex];
                selectModeIndex += 1;
                selectModeIndex %= allMode.length;
            } else if (buttonInput.equals(MO) && checkAvailableModeNum()) { //MO를 눌렀을 때 선택한 모드 갯수가 4개일 때만
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