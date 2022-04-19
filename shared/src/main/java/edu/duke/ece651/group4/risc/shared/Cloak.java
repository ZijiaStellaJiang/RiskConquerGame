package edu.duke.ece651.group4.risc.shared;

public class Cloak  implements java.io.Serializable {
    private int count;
    public Cloak() {
        this.count = 0;
    }
    public void countDown() {
        count += count > 0 ? -1 : 0;
    }

    public void renewCount(int count) {
        this.count += count > 0 ? count : 0;
    }

    public int getCount() {
        return count;
    }
}
