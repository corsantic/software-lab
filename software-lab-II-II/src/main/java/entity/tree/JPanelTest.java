import java.awt.*;

import javax.swing.*;

import entity.tree.Node;

/** Agacı cizdirmek için ornek
 *
 * */

public class JPanelTest extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here

        g.drawOval(5, 5, 25, 25);



    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new JPanelTest());
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }

}