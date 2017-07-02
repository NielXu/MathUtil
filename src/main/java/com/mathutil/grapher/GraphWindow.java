package com.mathutil.grapher;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GraphWindow {
	
	private JFrame frame;
	
	public GraphWindow(String title, JPanel pane){
		frame = new JFrame(title);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(pane.getWidth() , pane.getHeight()));
		frame.add(pane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
