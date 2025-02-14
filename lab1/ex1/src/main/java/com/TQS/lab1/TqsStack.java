package com.TQS.lab1;

import java.util.LinkedList;

public class TqsStack<E> {
    private LinkedList<E> stack = new LinkedList<>();

    public void push(E element) {
        stack.add(element);
    }

    public E pop() {
        if (stack.isEmpty()) {
            throw new java.util.NoSuchElementException();
        } else {
            return stack.remove(stack.size() - 1);
        }
    }

    public E peek() {
        if (stack.isEmpty()) {
            throw new java.util.NoSuchElementException();
        } else {
            return stack.get(stack.size() - 1);
        }
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public E popTopN(int n) {
        E top = null;
        for (int i = 0; i < n; i++) {
            top = stack.removeFirst();
        }
        return top;
    }
}
