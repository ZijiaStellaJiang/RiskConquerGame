package edu.duke.ece651.group4.risc.shared;

/**
 * this abstract class is resource that territory can produce
 * @field num is the number of this resource type
 */
public abstract class Resource<T> implements java.io.Serializable {
    private int num;

    public Resource(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
    public void addResource(int toAdd){
        this.num = num + toAdd;
    }
    public void consumeResource(int consume){
        if(num<consume){
            throw new IllegalArgumentException("don't have enough resource to consume");
        }
        this.num = num - consume;
    }

    /**
     * check whether is same type of resource, number doesn't matter.
     */
    @Override
    public boolean equals(Object o){
        return o.getClass().equals(getClass());
    }
}
