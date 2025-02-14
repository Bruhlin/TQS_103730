package com.TQS.lab1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class TqsStackAiTest {

    private TqsStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new TqsStack<>();
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty(), "Stack should be empty");
    }

    @Test
    public void testPush() {
        stack.push(1);
        assertFalse(stack.isEmpty(), "Stack should not be empty after push");
    }

    @Test
    public void testPop() {
        stack.push(1);
        int value = stack.pop();
        assertEquals(1, value, "Popped value should be 1");
        assertTrue(stack.isEmpty(), "Stack should be empty after pop");
    }

    @Test
    public void testPeek() {
        stack.push(1);
        int value = stack.peek();
        assertEquals(1, value, "Peeked value should be 1");
        assertFalse(stack.isEmpty(), "Stack should not be empty after peek");
    }

    @Test
    public void testPopEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        }, "Popping from an empty stack should throw NoSuchElementException");
    }

    @Test
    public void testPeekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        }, "Peeking into an empty stack should throw NoSuchElementException");
    }
}
