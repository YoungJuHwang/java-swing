package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

public class TopLevelDemo {
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TopLevelDemo");
        //프레임을 닫는 이벤트
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true); // 투명도 true면 100%
        greenMenuBar.setBackground(new Color(154, 165, 127)); //바탕 녹색
        greenMenuBar.setPreferredSize(new Dimension(200, 20));// 사이즈
 
        //Create a yellow label to put in the content pane.
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(new Color(248, 213, 131));
        yellowLabel.setPreferredSize(new Dimension(200, 180));
 
        //Set the menu bar and add the label to the content pane.
        //메뉴바 생성. 보더레이아웃은 중앙정렬 가능.
        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);
 
        //Display the window.
        //pack()메소드는 컴포넌트들을 패킹시키는 역할.(묶음)
        frame.pack();
        frame.setVisible(true); //보이나마나
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
