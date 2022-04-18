package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class store the information of a territory
 * If it is for player's own, just store the real-time information
 * If it is for enemy, store the latest version that enemy can see
 */
public class TerritoryDisplayInfo<T> implements java.io.Serializable{
    private ArrayList<Integer> levelUnitNum; //store the amount of unit in each level to display
    private boolean isLatest;  //indicate whether this information is updated to current.
                               //for my own info, always true

    public TerritoryDisplayInfo() {
        this.levelUnitNum = new ArrayList<>(Collections.nCopies(7,0));
        this.isLatest = true;
    }

    public ArrayList<Integer> getUnitNumList() {
        return this.levelUnitNum;
    }

    public void updateLevelInfo(int level, int num) {
        levelUnitNum.set(level,num);
    }

    public void setIsLatest(boolean toSet) {
        this.isLatest = toSet;
    }

    public boolean checkLatest() {
        return isLatest;
    }
}
