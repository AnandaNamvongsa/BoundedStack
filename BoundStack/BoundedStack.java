import java.util.*;
/**
 * BoundedStack
 */
public class BoundedStack {
    //
    //AF(province,capacity) =  province แทนค่าข้อมูลของ Stack ที่เก็บสมาชิกชนิด String
    //                         โดยสมาชิกตัวสุดท้ายเป็น top ของ stack
    //                        
    //RI 
    //   province != null
    //   province.size() <= capacity
    //   ข้อมูลใน province ต้องไม่มีข้อมูลว่าง
    //   

    //Safety from rep exposure:
    //   - province เป็น private final
    //   - constructor คัดลอก List ที่รับเข้ามา
    //   - songs() คืนสำเนาของ songs ไม่คืน reference ตรงๆ

    /**
     * 
     * 
     * 
     * 
     * @param capacity
     * 
     */
    private final List<String> province ;
    private final int capacity = 100;
    public BoundedStack(){
        this.province = new ArrayList<>();
        
    }

    
    public boolean isFull(){

        return province.size() >= capacity;

    }

    /**
    * เพิ่มข้อมูลลงบนสุดของ Stack
    *
    * @param name_province ข้อมูลที่ต้องการเพิ่มลงใน Stack
    * @throws IllegalStateException ถ้า Stack เต็ม
    */

    public void push(String name_province){
        if(isFull()){
            throw new IllegalStateException("Stack is Full");
        }
        province.add(name_province);
    }

    public String pop(){
        if(province.isEmpty()){
            throw new IllegalStateException("Stack is Empty");
        }
        return province.remove(province.size()-1);

    }
    public String peek(){
        if(province.isEmpty()){
            throw new IllegalStateException("Stack is Empty");
        }
        return province.get(province.size()-1);//คืนค่าข้อมูลตัวสุดท้ายใน List
    }

    
}