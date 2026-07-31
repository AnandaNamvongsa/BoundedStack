/**
 * เทส BoundedStack แบบเขียนเช็คเอง ไม่ง้อ JUnit
 * รันแล้วดูช่อง PASS/FAIL ของแต่ละบรรทัด แล้วดูสรุปท้ายสุดว่าผ่านหมดรึเปล่า
 */
public class BoundedStackTest {

    private static int passCount = 0;
    private static int failCount = 0;

    public static void main(String[] args) {

        // สร้าง stack แบบต่างๆ
        testCustomCapacity();
        testNewStackPeekThrows();
        testNewStackPopThrows();
        testNewStackIsNotFull();
        testInvalidCapacityThrows();
        testNewStackIsEmpty();

        // push แล้วดูด้วย peek
        testPushThenPeekReturnsSameElement();
        testPushTwiceThenPeekReturnsLastPushed();
        testPushMultipleThenPeekIsLIFO();
        testPushMakesStackNotEmpty();
        testSizeAfterPushAndPop();
        testPeekDoesNotRemoveElement();

        // pop
        testPushThenPopReturnsElementAndEmptiesStack();
        testPushTwiceThenPopReturnsInLIFOOrder();
        testPopAfterEmptyingThrowsAgain();
        testPopMakesStackEmpty();

        // เต็มพอดี / ล้น
        testPushUntilCapacityMakesFull();
        testPushBeyondCapacityThrows();
        testPeekAfterFillingReturnsLastPushed();
        testPopFromFullStackMakesItNotFull();

        // ค่าแปลกๆ
        testPushNullThrows();
        testPushEmptyStringSucceeds();

        // ใช้งานจริงแบบสลับไปมา
        testInterleavedPushPopMaintainsLIFO();
        testEmptyThenPushAgainWorks();

        System.out.println();
        System.out.println("SUMMARY");
        System.out.println("PASS: " + passCount + "  FAIL: " + failCount + "  TOTAL: " + (passCount + failCount));
        if (failCount > 0) {
            System.out.println("RESULT: FAIL");
        } else {
            System.out.println("RESULT: PASS");
        }
    }

    // ---------- สร้าง stack แบบต่างๆ ----------

    // capacity กำหนดเองได้ ลองตั้ง 3 ดู push จนเต็มแล้วเช็คว่ามันรู้ตัวว่าเต็ม
    // แล้วลอง push ตัวที่ 4 ดูว่าโดนบล็อกจริงไหม
    private static void testCustomCapacity() {
        BoundedStack s = new BoundedStack(3);
        s.push("A");
        s.push("B");
        s.push("C");
        assertTrue("stack with capacity 3 should be full", s.isFull());
        assertThrows("pushing over custom capacity should throw", IllegalStateException.class,
                () -> s.push("D"));
    }

    // stack ใหม่ ยังไม่ทันใส่อะไรเลย peek() ต้องบ่นว่าว่าง
    private static void testNewStackPeekThrows() {
        BoundedStack s = new BoundedStack(100);
        assertThrows("new stack: peek() must throw IllegalStateException", IllegalStateException.class, s::peek);
    }

    // เหมือนกัน แต่เปลี่ยนเป็น pop() แทน
    private static void testNewStackPopThrows() {
        BoundedStack s = new BoundedStack(100);
        assertThrows("new stack: pop() must throw IllegalStateException", IllegalStateException.class, s::pop);
    }

    // stack เพิ่งสร้าง ไม่มีทางเต็มอยู่แล้ว
    private static void testNewStackIsNotFull() {
        BoundedStack s = new BoundedStack(100);
        assertFalse("new stack: isFull() must be false", s.isFull());
    }

    // ใส่ cap มั่วๆ 0 กับติดลบ ต้องโดนเตะกลับด้วย IllegalArgumentException
    private static void testInvalidCapacityThrows() {
        assertThrows("capacity <= 0 must throw IllegalArgumentException", IllegalArgumentException.class,
                () -> new BoundedStack(0));
        assertThrows("negative capacity must throw IllegalArgumentException", IllegalArgumentException.class,
                () -> new BoundedStack(-1));
    }

    private static void testNewStackIsEmpty() {
        BoundedStack s = new BoundedStack(100);
        assertTrue("new stack should be empty", s.isEmpty());
    }

    // ---------- push แล้วดูด้วย peek ----------

    private static void testPushThenPeekReturnsSameElement() {
        BoundedStack s = new BoundedStack(100);
        s.push("Bangkok");
        assertEquals("push 1 element: peek() must return that same element", "Bangkok", s.peek());
    }

    // push ไป 2 ตัว ของที่โผล่มาตอน peek ต้องเป็นตัวที่ใส่ล่าสุด ไม่ใช่ตัวแรก
    private static void testPushTwiceThenPeekReturnsLastPushed() {
        BoundedStack s = new BoundedStack(100);
        s.push("Bangkok");
        s.push("Chiangmai");
        assertEquals("peek() must return the last pushed element (LIFO)", "Chiangmai", s.peek());
    }

    private static void testPushMultipleThenPeekIsLIFO() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.push("B");
        s.push("C");
        assertEquals("peek() must return the top element (C)", "C", s.peek());
    }

    private static void testPushMakesStackNotEmpty() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        assertFalse("stack should not be empty after push", s.isEmpty());
    }

    // ไล่ดู size ตั้งแต่ว่าง -> push -> pop กลับมาว่าง ตัวเลขต้องขยับถูกจังหวะ
    private static void testSizeAfterPushAndPop() {
        BoundedStack s = new BoundedStack(100);
        assertEquals("new stack size", 0, s.size());
        s.push("A");
        assertEquals("size after push", 1, s.size());
        s.pop();
        assertEquals("size after pop", 0, s.size());
    }

    // peek แค่แอบดู ไม่ได้หยิบออก เรียกกี่รอบก็ควรเห็นของเดิม
    private static void testPeekDoesNotRemoveElement() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.peek();
        assertEquals("peek should not remove element", 1, s.size());
    }

    // ---------- pop ----------

    private static void testPushThenPopReturnsElementAndEmptiesStack() {
        BoundedStack s = new BoundedStack(100);
        s.push("Bangkok");
        String popped = s.pop();
        assertEquals("pop() must return the element just pushed", "Bangkok", popped);
        assertThrows("after popping to empty, peek() must throw", IllegalStateException.class, s::peek);
    }

    // เข้า A ก่อน B ทีหลัง ตอนออกต้องสลับ - B ออกก่อน A
    private static void testPushTwiceThenPopReturnsInLIFOOrder() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.push("B");
        assertEquals("first pop() must return B (last pushed)", "B", s.pop());
        assertEquals("second pop() must return A", "A", s.pop());
    }

    // pop จนหมดแล้ว ดันจะ pop อีก ก็ต้องโดนแบบเดิม ไม่ใช่จู่ๆ ทำงานได้เฉยๆ
    private static void testPopAfterEmptyingThrowsAgain() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.pop();
        assertThrows("pop() on an already-empty stack must throw again", IllegalStateException.class, s::pop);
    }

    private static void testPopMakesStackEmpty() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.pop();
        assertTrue("after pop last element, stack should be empty", s.isEmpty());
    }

    // ---------- เต็มพอดี หรือ ล้น ----------

    private static void testPushUntilCapacityMakesFull() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push("p" + i);
        }
        assertTrue("after pushing 100 elements (capacity), isFull() must be true", s.isFull());
    }

    // ยัดจนเต็มแล้วยังจะยัดต่ออีกตัว อันนี้แหละที่ต้องกันไว้ไม่ให้ล้น
    private static void testPushBeyondCapacityThrows() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push("p" + i);
        }
        assertThrows("pushing the 101st element must throw IllegalStateException", IllegalStateException.class,
                () -> s.push("overflow"));
    }

    private static void testPeekAfterFillingReturnsLastPushed() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push("p" + i);
        }
        assertEquals("once full, peek() must return the last pushed element (p99)", "p99", s.peek());
    }

    // เต็มอยู่ พอ pop ออกไปตัวนึง ก็ควรมีที่ว่างกลับมาให้ push ต่อได้
    private static void testPopFromFullStackMakesItNotFull() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push("p" + i);
        }
        s.pop();
        assertFalse("after popping once from a full stack, isFull() must be false", s.isFull());
    }

    // ---------- ค่าแปลก ----------

    private static void testPushNullThrows() {
        BoundedStack s = new BoundedStack(100);
        assertThrows("push(null) must throw IllegalArgumentException", IllegalArgumentException.class,
                () -> s.push(null));
    }

    // string ว่าง "" ไม่เหมือน null นะ อันนี้ผ่านได้ตาม spec ที่ห้ามแค่ null
    private static void testPushEmptyStringSucceeds() {
        BoundedStack s = new BoundedStack(100);
        s.push("");
        assertEquals("push(\"\") should allow empty string", "", s.peek());
    }

    // ---------- ใช้งานจริงแบบสลับไปมา ----------

    // จำลองการใช้งานจริงหน่อย ไม่ push รวดเดียวแล้ว pop รวดเดียว
    // แต่สลับกันไปมา ดูว่าลำดับยังถูกอยู่ตลอด
    private static void testInterleavedPushPopMaintainsLIFO() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.push("B");
        assertEquals("push A,B then first pop must return B", "B", s.pop());
        s.push("C");
        assertEquals("push C after popping B: peek() must return C", "C", s.peek());
        assertEquals("pop must return C", "C", s.pop());
        assertEquals("pop must return A (the last one left at the bottom)", "A", s.pop());
    }

    // เคยว่างมาแล้วรอบนึง แล้วกลับมาใช้ใหม่ ต้องทำงานเหมือน stack ปกติ ไม่มีอะไรค้าง
    private static void testEmptyThenPushAgainWorks() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.pop();
        assertThrows("stack is empty: peek() must throw", IllegalStateException.class, s::peek);
        s.push("B");
        assertEquals("pushing again after being emptied must work normally", "B", s.peek());
        assertFalse("after pushing 1 element, isFull() must still be false", s.isFull());
    }

    // ---------- ช่วยเช็คผล---------

    private interface ThrowingAction {
        void run();
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        boolean ok = (expected == null) ? (actual == null) : expected.equals(actual);
        report(testName, ok, "expected=<" + expected + "> actual=<" + actual + ">");
    }

    private static void assertTrue(String testName, boolean condition) {
        report(testName, condition, "expected true, got false");
    }

    private static void assertFalse(String testName, boolean condition) {
        report(testName, !condition, "expected false, got true");
    }

    // ลองรัน action แล้วดักดูว่ามัน throw ตามที่คาดไว้จริงไหม
    // ไม่ throw เลย หรือ throw ผิดตัว ก็ถือว่าไม่ผ่าน
    private static void assertThrows(String testName, Class<? extends Throwable> expectedType, ThrowingAction action) {
        try {
            action.run();
            report(testName, false, "no exception was thrown (expected " + expectedType.getSimpleName() + ")");
        } catch (Throwable t) {
            boolean ok = expectedType.isInstance(t);
            report(testName, ok,
                    "expected=" + expectedType.getSimpleName() + " actual=" + t.getClass().getSimpleName());
        }
    }

    private static void report(String testName, boolean passed, String detail) {
        if (passed) {
            passCount++;
            System.out.println("[PASS] " + testName);
        } else {
            failCount++;
            System.out.println("[FAIL] " + testName + "  (" + detail + ")");
        }
    }
}