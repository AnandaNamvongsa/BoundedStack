import java.util.*;
/**
 * BoundedStack
 */
public class BoundedStack {
    //
    //AF(elements,capasity) = elements คือ
    //                        capacity คือ ความจุของตัวเก็บข้อมูล
    //
    //
    //
    /**
     * 
     * 
     * 
     * 
     * @param capacity
     * 
     */
    private final List<String> elements ;
    private final int capacity;
    public BoundedStack(int capacity){
        this.elements = new ArrayList<>();
        this.capacity = 100;
    }
}