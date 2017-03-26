package samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
Thread example multi
 */
public class Thread_Read extends Thread {
    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String text="";

        while(true) {
            try {
                text = bufferedReader.readLine();
            }
            catch (IOException Exc) {
                System.out.println(Exc);
            }
            System.out.println("Echo: "+text);
        }
    }
}
