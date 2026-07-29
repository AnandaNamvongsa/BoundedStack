/**
 *
 *
 *
 * checkRep() ยังบ่ได้ทำ
 */
public class BoundedStackTest {

    private static int passCount = 0;
    private static int failCount = 0;

    public static void main(String[] args) {
        // --- Creator ---
        testNewStackPeekThrows();
        testNewStackPopThrows();
        testNewStackIsNotFull();

        // --- Producer (push) + Observer (peek) ---
        testPushThenPeekReturnsSameElement();
        testPushTwiceThenPeekReturnsLastPushed();
        testPushMultipleThenPeekIsLIFO();

        // --- Mutator (pop) ---
        testPushThenPopReturnsElementAndEmptiesStack();
        testPushTwiceThenPopReturnsInLIFOOrder();
        testPopAfterEmptyingThrowsAgain();

        System.out.println();
        System.out.println("===== SUMMARY =====");
        System.out.println("PASS: " + passCount + "  FAIL: " + failCount + "  TOTAL: " + (passCount + failCount));
        if (failCount > 0) {
            System.out.println("RESULT: FAIL");
        } else {
            System.out.println("RESULT: PASS");
        }
    }

    // ---------- เคสเทสต่างๆ ----------

    private static void testNewStackPeekThrows() {
        BoundedStack s = new BoundedStack();
        assertThrows("new stack: peek() must throw IllegalStateException", IllegalStateException.class, s::peek);
    }

    private static void testNewStackPopThrows() {
        BoundedStack s = new BoundedStack();
        assertThrows("new stack: pop() must throw IllegalStateException", IllegalStateException.class, s::pop);
    }

    private static void testNewStackIsNotFull() {
        BoundedStack s = new BoundedStack();
        assertFalse("new stack: isFull() must be false", s.isFull());
    }

    private static void testPushThenPeekReturnsSameElement() {
        BoundedStack s = new BoundedStack();
        s.push("Bangkok");
        assertEquals("push 1 element: peek() must return that same element", "Bangkok", s.peek());
    }

    private static void testPushTwiceThenPeekReturnsLastPushed() {
        BoundedStack s = new BoundedStack();
        s.push("Bangkok");
        s.push("Chiangmai");
        assertEquals("peek() must return the last pushed element (LIFO)", "Chiangmai", s.peek());
    }

    private static void testPushMultipleThenPeekIsLIFO() {
        BoundedStack s = new BoundedStack();
        s.push("A");
        s.push("B");
        s.push("C");
        assertEquals("peek() must return the top element (C)", "C", s.peek());
    }

    private static void testPushThenPopReturnsElementAndEmptiesStack() {
        BoundedStack s = new BoundedStack();
        s.push("Bangkok");
        String popped = s.pop();
        assertEquals("pop() must return the element just pushed", "Bangkok", popped);
        assertThrows("after popping to empty, peek() must throw", IllegalStateException.class, s::peek);
    }

    private static void testPushTwiceThenPopReturnsInLIFOOrder() {
        BoundedStack s = new BoundedStack();
        s.push("A");
        s.push("B");
        assertEquals("first pop() must return B (last pushed)", "B", s.pop());
        assertEquals("second pop() must return A", "A", s.pop());
    }

    private static void testPopAfterEmptyingThrowsAgain() {
        BoundedStack s = new BoundedStack();
        s.push("A");
        s.pop();
        assertThrows("pop() on an already-empty stack must throw again", IllegalStateException.class, s::pop);
    }

    // ---------- ตัวช่วย assert เล็กๆ ทำเองกับมือ (ตาม C4) ----------

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

    private static void assertThrows(String testName, Class<? extends Throwable> expectedType, ThrowingAction action) {
        try {
            action.run();
            report(testName, false, "no exception was thrown (expected " + expectedType.getSimpleName() + ")");
        } catch (Throwable t) {
            boolean ok = expectedType.isInstance(t);
            report(testName, ok, "expected=" + expectedType.getSimpleName() + " actual=" + t.getClass().getSimpleName());
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