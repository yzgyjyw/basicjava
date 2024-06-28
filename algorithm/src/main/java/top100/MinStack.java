package top100;

import java.util.Stack;

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 实现 MinStack 类:
 *
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 */
public class MinStack {

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();
        minStack.pop();
        minStack.top();
        minStack.getMin();
    }
    // 大于等于出栈  小于入栈
    private Stack<Integer> originStack;
    private Stack<Integer> minStack;

    public MinStack() {
        originStack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        originStack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()){
            minStack.push(val);
        }
    }

    public void pop() {
        Integer top = originStack.pop();
        if(minStack.peek().equals(top)){
            minStack.pop();
        }
    }

    public int top() {
        return originStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

}
