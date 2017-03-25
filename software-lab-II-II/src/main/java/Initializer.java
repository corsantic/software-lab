public class Initializer
{
    public static void main(String[] args)
    {
        new Initializer().run();
    }

    void run()
    {
        Thread_Read threadRead=new Thread_Read();
        Thread_Write threadWrite=new Thread_Write();
        threadRead.start();
        threadWrite.start();

    }
}

