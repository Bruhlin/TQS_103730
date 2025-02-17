package com.TQS.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TqsStackTest {
    private TqsStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>();
    }

    @Test
    @DisplayName("A stack is empty on construction")
    void emptyOnConstruction() {
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("A stack has size 0 on construction")
    void size0onConstruction() {
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("After n > 0 pushes to an empty stack, the stack is not empty and its size is n")
    void stackNotEmpty() {
        int n = 3;
        for(int i= 0; i < n; i++) {
            stack.push(String.valueOf(i));
        }
        assertFalse(stack.isEmpty());
        assertEquals(n, stack.size());
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x")
    void pushAndPop() {
        String value = "test";
        stack.push(value);
        assertEquals(value, stack.pop());
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    void pushAndPeek() {
        String value = "test";
        stack.push(value);
        int size = stack.size();
        assertEquals(value, stack.peek());
        assertEquals(size, stack.size());
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    void afterPop() {
        int n = 3;
        for(int i= 0; i < n; i++) {
            stack.push(String.valueOf(i));
        }
        for(int i= 0; i < n; i++) {
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Popping from an empty stack throws a NoSuchElementException")
    void popEmpty() {
        assertTrue(stack.isEmpty());
        try {
            stack.pop();
        } catch (Exception e) {
            assertTrue(e instanceof java.util.NoSuchElementException);
        }
    }

    @Test
    @DisplayName("Peeking into an empty stack throws a NoSuchElementException")
    void peekEmpty() {
        assertTrue(stack.isEmpty());
        try {
            stack.peek();
        } catch (Exception e) {
            assertTrue(e instanceof java.util.NoSuchElementException);
        }
    }

    @Test
    @DisplayName("For bounded stacks only, pushing onto a full stack throws an IllegalStateException")
    void pushFull() {
        int n = 3;
        for(int i= 0; i < n; i++) {
            stack.push(String.valueOf(i));
        }
        assertEquals(n, stack.size());
        try {
            stack.push("test");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    @DisplayName("Returns the n-th item, discarding the n-1 top elements")
    void popTopN()  {
        int elems = 5;
        int n = 4;
        for(int i= 0; i < elems; i++) {
            stack.push(String.valueOf(i));
        }

        assertEquals(elems, stack.size());
        assertEquals(String.valueOf(n - 1), stack.popTopN(n));
        assertEquals(elems-n, stack.size());
        
    }
    
}
