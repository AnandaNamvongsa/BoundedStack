
public class BoundedStackTest {

    private static int passCount = 0;
    private static int failCount = 0;

    public static void main(String[] args) {
        
        // --- Creator ---
        testCustomCapacity();
        testNewStackPeekThrows();
        testNewStackPopThrows();
        testNewStackIsNotFull();
        testInvalidCapacityThrows();
        testNewStackIsEmpty();
        // --- Producer (push) + Observer (peek) ---
        testPushThenPeekReturnsSameElement();
        testPushTwiceThenPeekReturnsLastPushed();
        testPushMultipleThenPeekIsLIFO();
        testPushMakesStackNotEmpty();
        testSizeAfterPushAndPop();
        testPeekDoesNotRemoveElement();

        // --- Mutator (pop) ---
        testPushThenPopReturnsElementAndEmptiesStack();
        testPushTwiceThenPopReturnsInLIFOOrder();
        testPopAfterEmptyingThrowsAgain();
        testPopMakesStackEmpty();

        // --- Capacity boundary ---
        testPushUntilCapacityMakesFull();
        testPushBeyondCapacityThrows();
        testPeekAfterFillingReturnsLastPushed();
        testPopFromFullStackMakesItNotFull();

        // --- Invalid input ---
        testPushNullThrows();
        testPushEmptyStringSucceeds();

        // --- push/pop สลับกันไปมา ---
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

    // ---------- เคสเทสต่างๆ ----------
    private static void testPopMakesStackEmpty() {
    BoundedStack s = new BoundedStack(100);

    s.push("A");
    s.pop();

    assertTrue(
        "after pop last element, stack should be empty",
        s.isEmpty()
    );
    }
    
    private static void testInvalidCapacityThrows() {
    assertThrows(
        "capacity <= 0 must throw IllegalArgumentException",
        IllegalArgumentException.class,
        () -> new BoundedStack(0)
    );

    assertThrows(
        "negative capacity must throw IllegalArgumentException",
        IllegalArgumentException.class,
        () -> new BoundedStack(-1)
    );
    }   
    private static void testCustomCapacity() {
    BoundedStack s = new BoundedStack(3);

    s.push("A");
    s.push("B");
    s.push("C");

    assertTrue(
        "stack with capacity 3 should be full",
        s.isFull()
    );
    assertThrows(
        "pushing over custom capacity should throw",
        IllegalStateException.class,
        () -> s.push("D")
    );
    }
    private static void testNewStackPeekThrows() {
        BoundedStack s = new BoundedStack(100);
        assertThrows("new stack: peek() must throw IllegalStateException", IllegalStateException.class, s::peek);
    }

    private static void testNewStackPopThrows() {
        BoundedStack s = new BoundedStack(100);
        assertThrows("new stack: pop() must throw IllegalStateException", IllegalStateException.class, s::pop);
    }

    private static void testNewStackIsNotFull() {
        BoundedStack s = new BoundedStack(100);
        assertFalse("new stack: isFull() must be false", s.isFull());
    }

    private static void testPushThenPeekReturnsSameElement() {
        BoundedStack s = new BoundedStack(100);
        s.push("Bangkok");
        assertEquals("push 1 element: peek() must return that same element", "Bangkok", s.peek());
    }

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

    private static void testPushThenPopReturnsElementAndEmptiesStack() {
        BoundedStack s = new BoundedStack(100);
        s.push("Bangkok");
        String popped = s.pop();
        assertEquals("pop() must return the element just pushed", "Bangkok", popped);
        assertThrows("after popping to empty, peek() must throw", IllegalStateException.class, s::peek);
    }

    private static void testPushTwiceThenPopReturnsInLIFOOrder() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.push("B");
        assertEquals("first pop() must return B (last pushed)", "B", s.pop());
        assertEquals("second pop() must return A", "A", s.pop());
    }

    private static void testPopAfterEmptyingThrowsAgain() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.pop();
        assertThrows("pop() on an already-empty stack must throw again", IllegalStateException.class, s::pop);
    }

    // ---------- Capacity boundary ----------

    private static void testPushUntilCapacityMakesFull() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push("p" + i);
        }
        assertTrue("after pushing 100 elements (capacity), isFull() must be true", s.isFull());
    }

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

    private static void testPopFromFullStackMakesItNotFull() {
        BoundedStack s = new BoundedStack(100);
        for (int i = 0; i < 100; i++) {
            s.push("p" + i);
        }
        s.pop();
        assertFalse("after popping once from a full stack, isFull() must be false", s.isFull());
    }

    // ---------- Invalid input ----------

    private static void testPushNullThrows() {
        BoundedStack s = new BoundedStack(100);

        assertThrows(
                "push(null) must throw IllegalArgumentException",
                IllegalArgumentException.class,
                () -> s.push(null));
    }

    private static void testPushEmptyStringSucceeds() {
        BoundedStack s = new BoundedStack(100);
        s.push("");
        assertEquals("push(\"\") should allow empty string", "", s.peek());
    }

    // ---------- Batch 2: push/pop สลับกันไปมา ----------

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

    private static void testEmptyThenPushAgainWorks() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.pop();
        assertThrows("stack is empty: peek() must throw", IllegalStateException.class, s::peek);
        s.push("B");
        assertEquals("pushing again after being emptied must work normally", "B", s.peek());
        assertFalse("after pushing 1 element, isFull() must still be false", s.isFull());
    }

    private static void testNewStackIsEmpty() {
        BoundedStack s = new BoundedStack(100);
        assertTrue(
                "new stack should be empty",
                s.isEmpty());
    }

    private static void testPushMakesStackNotEmpty() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        assertFalse(
                "stack should not be empty after push",
                s.isEmpty());
    }

    private static void testSizeAfterPushAndPop() {
        BoundedStack s = new BoundedStack(100);
        assertEquals("new stack size", 0, s.size());
        s.push("A");
        assertEquals("size after push", 1, s.size());
        s.pop();
        assertEquals("size after pop", 0, s.size());
    }

    private static void testPeekDoesNotRemoveElement() {
        BoundedStack s = new BoundedStack(100);
        s.push("A");
        s.peek();
        assertEquals(
                "peek should not remove element",
                1,
                s.size());
    }

    // ---------- ตัวช่วย assert ----------

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
