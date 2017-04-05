package samples;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadSample
{
    /**
     * ayni islemi bir 3 thread le birde 1 thread ile calistirir isek fark anlasilir
     */
    private static final int THREAD_COUNT = 3;

    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();
        new MultiThreadSample().invokeAll();
        long end = System.currentTimeMillis();

        System.out.println("process done " + (end - start) + " ms");
    }


    private void invokeAll() throws InterruptedException
    {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        /**
         * her biri paralel calisacak bir method olarak dusunulebilir
         * her bir method'da yaptigi islem sonucunda bir degisken donuyor
         * test amacli her bir task 1 sn bekliyor
         */
        List<Callable<String>> tasks = Arrays.asList(
                () ->
                {
                    // do stuff
                    TimeUnit.SECONDS.sleep(1);
                    return "task 1";
                },
                () ->
                {
                    TimeUnit.SECONDS.sleep(1);
                    return "task 2";
                },
                () ->
                {
                    TimeUnit.SECONDS.sleep(1);
                    return "task 3";
                });

        executor.invokeAll(tasks) /** invokeAll: tum tasklarin tamamlanmasini bekliyor */
                .stream()
                .map(future ->
                {
                    try
                    {
                        return future.get();
                    }
                    catch (Exception e)
                    {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(taskResult ->
                {
                    System.out.println(taskResult + " done.");
                });

        executor.shutdown();
    }
}
