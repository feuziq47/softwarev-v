package RDM;
import java.util.*;

/**
 *
 */
public class DecisionMaker extends Mode {

    /**
     * Default constructor
     */
    public DecisionMaker() {
        caseNum = 2;
    }

    /**
     *
     */
    private int caseNum;
    private int randNum;
    private boolean isInitial = true;

    /**
     * caseNum의 getter가 추가되었습니다.
     */
    public int getCaseNum(){
        return caseNum;
    }

    /**
     * editCaeNumber는 현재 숫자를 반환해야 할 것 같습니다.
     * 따라서 반환형이 int로 변경되었습니다.

    public int editCaseNumber() {
        // TODO implement here
        return caseNum;
    }
     */
    /**
     * 마찬가지 입니다. 현재 숫자를 반환해야 할것 같아 increase와 decrease의 반환형이 있어야 할 것 같아서
     * 반환형을 int로 변경합니다.
     */
    public int increase() {
        // TODO implement here
        caseNum++;
        if(caseNum > 99) caseNum = 2;
        return caseNum;
    }

    /**
     * @return
     */
    public int decrease() {
        // TODO implement here
        caseNum--;
        if(caseNum < 2) caseNum = 99;
        return caseNum;
    }

    /**
     * saveCaseNumber는 우리와 함께 가지 못하게 되었습니다.
     * saveCaseNumber가 만약에 필요하다면
     * RDMSystem단에서 화면을 빠저나가라 명령하는 수준에 그치기에 일단은
     * Decision making에서는 빠저야 할 것 같습니다.
     */

    /**
     * @return
     */
    public void getCase() {
        // TODO implement here
        Random rand = new Random();
        randNum = rand.nextInt(caseNum);
    }

    public int getRandNum(){
        return this.randNum;
    }

    public void setCaseNum(int caseNum) {
        this.caseNum = caseNum;
    }

    public boolean isInitialized() { return this.isInitial; }

    public void setIsInitialized(boolean input) {
        isInitial = input;
    }
}
