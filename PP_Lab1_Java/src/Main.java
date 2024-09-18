//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // Клас для обчислення суми частини масиву в окремому потоці
    static class SumThread extends Thread {
        private int[] array;
        private int start;
        private int finish;
        private int partialSum = 0;

        public SumThread(int[] array, int start, int finish) {
            this.array = array;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public void run() {
            for (int i = start; i < finish; i++) {
                partialSum += array[i];
            }
        }

        public int getPartialSum() {
            return partialSum;
        }
    }

    public static int parallelSum(int[] matrix, int threadCount) throws InterruptedException {
        int chunkSize = matrix.length / threadCount;
        SumThread[] threads = new SumThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int finish;
            if (i == threadCount - 1)
            {
                finish = matrix.length;
            }
            else
            {
                finish = start+chunkSize;
            }
            threads[i] = new SumThread(matrix, start, finish);
            threads[i].start();
        }

        int totalSum = 0;
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
            totalSum += threads[i].getPartialSum();
        }

        return totalSum;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Кількість потоків: "+ threadCount);
        int[] matrix = new int[50000];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = i + 1;
        }
        int totalSum = parallelSum(matrix, threadCount);
        System.out.println("Загальна сума: " + totalSum);

    }
}