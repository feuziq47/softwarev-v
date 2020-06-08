package RDM;
import java.time.LocalDateTime;

public class Alarm extends Mode {

    private StaticTime alarmList[] = new StaticTime[4];
    private int index;
    public Alarm() {
        this.alarmList = new StaticTime[]{new StaticTime(), new StaticTime(), new StaticTime(), new StaticTime()};
        this.index = 0;
    }

    public int getIndex(){
        return this.index;
    }

    public StaticTime getStaticTime(){
        return this.alarmList[index];
    }

    public StaticTime getAlarmList(int index){
        return this.alarmList[index];
    }

    public void switchAlarmStatus() {
        //현재 알람을 활성화 한다.
        if(!this.alarmList[this.index].getIsActivated()) {
            this.alarmList[this.index].setIsActivated(true);
        }else {
            this.alarmList[this.index].setIsActivated(false);
        }
        System.out.println(this.alarmList[this.index].getIsActivated());
    }
    /** switchAlarmStatus로 통합
     public void deactivateAlarm() {
     // 현재 알람을 비활성화 한다.

     }

     // 필요 없는 기능이므로 삭제하겠습니다.
     /*public void editAlarm() {
     // 현재 알람을 수정한다.

     }*/

    // 세팅할 때 유닛을 증가 또는 감소시키려면 유닛이 시간인지 분인지 초인지를 알아야 하므로 인자를 추가했습니다. - 이정우
    public void increase(String unitName) {
        // 현재 깜빡이는 유닛을 증가시킨다.
        switch(unitName){
            case "HOUR":
                this.alarmList[this.index].setAlarmTime(this.alarmList[this.index].getAlarmTime().plusHours(1));
                break;
            case "MIN":
                this.alarmList[this.index].setAlarmTime(this.alarmList[this.index].getAlarmTime().plusMinutes(1));
                break;
            case "SEC":
                this.alarmList[this.index].setAlarmTime(this.alarmList[this.index].getAlarmTime().plusSeconds(1));
                break;
            default:
                System.err.println("Invalid Unit Name");
                break;
        }
        System.out.println(this.index + " : " + this.alarmList[this.index].getAlarmTime());
    }

    public void decrease(String unitName) {
        // 현재 깜빡이는 유닛을 감소시킨다.
        switch(unitName){
            case "HOUR":
                this.alarmList[this.index].setAlarmTime(this.alarmList[this.index].getAlarmTime().minusHours(1));
                break;
            case "MIN":
                this.alarmList[this.index].setAlarmTime(this.alarmList[this.index].getAlarmTime().minusMinutes(1));
                break;
            case "SEC":
                this.alarmList[this.index].setAlarmTime(this.alarmList[this.index].getAlarmTime().minusSeconds(1));
                break;
            default:
                System.err.println("Invalid Unit Name");
                break;
        }
        System.out.println(this.index + ":" + this.alarmList[this.index].getAlarmTime());
    }

    /** 일단 selectUnitTime 주석처
     public String selectUnitTime(String unitName) {
     // 수정 시에 다음 유닛으로 커서를 옮긴다.
     switch(unitName){
     case "HOUR":
     return "MIN";
     case "MIN":
     return "SEC";
     case "SEC":
     return "HOUR";
     default:
     System.err.println("Invalid Unit Name");
     return null;
     }
     }
     */
    // 시퀀스 다이어그램 에서 getAlarm과 selectAlarm을 합쳤습니다.(getAlarm삭제) - 이정
    public StaticTime selectAlarm(String upOrDown) {
        // 현재 알람을 바꿔 줍니다.
        switch(upOrDown){
            case "UP":
                if(this.index < 3){
                    this.index++;
                } else{
                    this.index = 0;
                }
                break;
            case "DOWN":
                if(this.index > 0){
                    this.index--;
                } else{
                    this.index = 3;
                }
                break;
        }
        System.out.println(this.index + "번째 알람 : "+ this.alarmList[this.index].getAlarmTime());
        return this.alarmList[this.index];
    }
}