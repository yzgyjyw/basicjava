import java.util.Stack;



public class MinStack {

    public static void main(String[] args) {
        MinStack stack = new MinStack();

        stack.push(512);
        stack.push(-1024);
        stack.push(-1024);
        stack.push(512);

        stack.pop();

        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.getMin());

    }

    private Stack<Integer> stack;

    private Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int data) {
        if(minStack.isEmpty()||minStack.peek() >= data){
            minStack.push(data);
        }

        stack.push(data);
    }

    public void pop() {
        if(stack.isEmpty()){
            return;
        }
        Integer data = stack.pop();
        if(minStack.peek()==data){
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
