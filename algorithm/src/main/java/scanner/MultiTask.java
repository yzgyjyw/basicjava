package scanner;

import java.util.*;

public class MultiTask {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String coresAndTasks = scanner.nextLine();
        String taskHandleTime = scanner.nextLine();

        String[] s = coresAndTasks.split("\t");

        int cores = Integer.valueOf(s[0]);
        int tasks = Integer.valueOf(s[1]);

        String[] taskTimes = taskHandleTime.split("\t");

        List<Integer> list = new ArrayList<>();

        for (String str : taskTimes) {
            list.add(Integer.valueOf(str));
        }

        Collections.sort(list);

        int[] currentTask = new int[cores];


        for (int i = 0; i < cores; i++) {
            currentTask[i] = list.remove(0);
        }

        int sum = 0;

        while (!list.isEmpty()) {

            for (int i = 0; i < cores; i++) {
                currentTask[i]--;

                if (currentTask[i] == 0) {
                    currentTask[i] = list.remove(0);
                }
            }


            sum++;
        }

        System.out.println(sum + Arrays.stream(currentTask).max().getAsInt());
    }

}
