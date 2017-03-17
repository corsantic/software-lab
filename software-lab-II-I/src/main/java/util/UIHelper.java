package util;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class UIHelper
{
    public JButton createStandardButton(String solve)
    {
        JButton jButton = new JButton(solve);
        jButton.setPreferredSize(new Dimension(100, 50));
        return jButton;
    }

    public JTextField createInput(String st)
    {
        JTextField jTextField = new JTextField(st);
        jTextField.setPreferredSize(new Dimension(100, 50));
        jTextField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (jTextField.getText().equals(st))
                {
                    jTextField.setText("");
                    jTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (jTextField.getText().isEmpty())
                {
                    jTextField.setForeground(Color.GRAY);
                    jTextField.setText(st);
                }
            }
        });

        return jTextField;
    }

    public JFrame createStandardJFrame()
    {
        JFrame frame = new JFrame("Maze Solver");
        frame.setPreferredSize(new Dimension(800, 800));
        return frame;
    }
}
