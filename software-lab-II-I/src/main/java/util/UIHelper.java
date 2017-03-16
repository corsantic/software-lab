package util;

import java.awt.*;

import javax.swing.*;

public class UIHelper
{
    public JButton createStandartButton(String solve)
    {
        JButton jButton = new JButton(solve);
        jButton.setPreferredSize(new Dimension(100, 50));
        return jButton;
    }

    public JTextField createInput(String st)
    {
        JTextField jTextField = new JTextField(st);
        jTextField.setPreferredSize(new Dimension(100, 50));

        return jTextField;
    }

    public JFrame createStandartJPanel()
    {
        JFrame frame = new JFrame("Maze Solver");
        frame.setPreferredSize(new Dimension(800, 800));
        return frame;
    }
}
