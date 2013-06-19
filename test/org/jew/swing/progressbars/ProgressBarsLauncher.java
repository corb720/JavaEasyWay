package org.jew.swing.progressbars;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jew.swing.progressbar.CircularProgressBar;


public class ProgressBarsLauncher {
	
	public static void main(final String[] args) {
		JFrame frame = new JFrame();
		
		CircularProgressBar progress = new CircularProgressBar();
						
		JPanel p = new JPanel(new BorderLayout());
		p.add(progress,BorderLayout.CENTER);
		
		frame.add(p, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
	}
}
