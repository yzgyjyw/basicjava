package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// forkjoinpool适合CPU密集型的计算
// forkjoin适合递归操作
public class Fibonacci {
    public static void main(String[] args) {
        FibonacciTask task = new FibonacciTask(10);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        Integer result = forkJoinPool.invoke(task);
        System.out.println(result);
    }

}


class FibonacciTask extends RecursiveTask<Integer> {

    private int n;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return 1;
        }

        FibonacciTask task1 = new FibonacciTask(n - 1);
        task1.fork();
        FibonacciTask task2 = new FibonacciTask(n - 2);
        task2.fork();
        return task1.join() + task2.join();
    }
}