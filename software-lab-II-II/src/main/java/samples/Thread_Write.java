package samples;

/**
 * Thread Example multi
 */
public class Thread_Write extends Thread
{
    String text = "Thread example-1";

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                System.out.println(text);
                Thread.sleep(5000);
            }
            catch (InterruptedException Exc)
            {
                System.out.println(Exc);
            }
        }
    }
}
