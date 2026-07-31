import java.util.*;
/**
 * BoundedStack
 */
public class BoundedStack {
    //
    //AF:
    //   province แทน Stack ที่เก็บข้อมูลชนิด String
    //   โดยสมาชิกตัวสุดท้ายของ province คือ Top ของ Stack
    //                        
    //RI 
    //   province != null
    //   province.size() <= capacity
    //   province ต้องไม่มีสมาชิกที่เป็น null
    //   

    //Safety from rep exposure:
    //   - province เป็น private final จึงไม่สามารถเข้าถึงจากภายนอกได้
    //   - ไม่มีเมธอดที่คืน reference ของ province ให้ผู้ใช้
    //   - การแก้ไขข้อมูลทำได้ผ่าน push() และ pop() เท่านั้น

   
    private final List<String> province ;
    private final int capacity;
    /**
     * 
     * @param capacity รับค่าข้อมูลสูงสุดใน stack
     * @throws IllegalArgumentException ถ้า capacity เป็น 0 หรือเป็นค่าติดลบ
     */
    public BoundedStack(int capacity){
    if(capacity <= 0){
        throw new IllegalArgumentException("Capacity must be greater than 0");
    }

    this.capacity = capacity;
    this.province = new ArrayList<>();

    checkRep();
    }

     /**
     * ตรวจสอบว่า Stack เต็มหรือไม่
     *
     * @return true ถ้า Stack เต็ม, false ถ้ายังไม่เต็ม
     */
    public boolean isFull(){

        return province.size() == capacity;

    }


     /**
     * ตรวจสอบว่า Stack ว่างหรือไม่
     *
     * @return true ถ้า Stack ว่าง, false ถ้าไม่ว่าง
     */
    public boolean isEmpty() {
        return province.isEmpty();
    }

    
     /**
     * คืนค่าจำนวนสมาชิกใน Stack
     *
     * @return จำนวนสมาชิกปัจจุบัน
     */ 
    public int size() {
        return province.size();
    }


    private void checkRep() {
    assert province != null : "Province list must not be null";
    assert province.size() <= capacity : "Stack size exceeds capacity";
    assert !province.contains(null) : "Stack contains null element";
    }

    /**
     * @param name_province ข้อมูลที่ต้องการเพิ่ม
     * @throws IllegalStateException ถ้า Stack เต็ม
     * @throws IllegalArgumentException ถ้าข้อมูลเป็น null
     */
    public void push(String name_province){
        if (name_province == null) {
        throw new IllegalArgumentException("Element cannot be null");
        }
        if(isFull()){
            throw new IllegalStateException("Stack is Full");
        }
        
        province.add(name_province);
        checkRep();
    }
    
    /**
    * นำข้อมูลบนสุดออกจาก Stack
    *
    * @return ข้อมูลบนสุดของ Stack
    * @throws IllegalStateException ถ้า Stack ว่าง
    */
    public String pop(){
        if(province.isEmpty()){
            throw new IllegalStateException("Stack is Empty");
        }
        String result = province.remove(province.size() - 1);
        checkRep();
        return result;

    }
     /**
     * คืนค่าข้อมูลบนสุดของ Stack โดยไม่ลบออก
     *
     * @return ข้อมูลบนสุดของ Stack
     * @throws IllegalStateException ถ้า Stack ว่าง
     */
    public String peek(){
        checkRep();
        if(province.isEmpty()){
            throw new IllegalStateException("Stack is Empty");
        }
        return province.get(province.size()-1);
    }

    
}