import java.awt.*;

import javax.swing.*;

/**
 * Agacı cizdirmek için ornek
 */

public class JPanelTest extends JPanel
{

    public static void main(String[] args)
    {
        JFrame jFrame = new JFrame();
        jFrame.add(new JPanelTest());
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // Draw Tree Here

        g.drawOval(5, 5, 25, 25);


    }

}