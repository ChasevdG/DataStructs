package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Panel;

public class GuiTrial {

	private JFrame frame;
	private char[][] dice;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiTrial window = new GuiTrial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiTrial() {
		/*BufferedReader reader;
		try{
		File input = new File(cubeFile);
			reader = new BufferedReader(new FileReader(cubeFile));
		String line;
			while((line = reader.readLine()) != null){
			char[] images = new char[6];
			for(int n = 0; n<6;n++){
				images[n] = line.charAt(n);
			}
				
			}
		} catch (IOException e) {}*/
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		frame = new JFrame();
		frame.setBounds(100, 100, 678, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnRoll = new JButton("Roll");
		btnRoll.setBounds(248, 373, 116, 34);
		btnRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "rolling");
			}
		});
		frame.getContentPane().setLayout(null);
		btnRoll.setFont(new Font("Impact", Font.PLAIN, 14));
		frame.getContentPane().add(btnRoll);
		
		JLabel lblBoggle = new JLabel("BOGGLE");
		lblBoggle.setBounds(241, 11, 123, 39);
		lblBoggle.setFont(new Font("Tahoma", Font.ITALIC, 28));
		frame.getContentPane().add(lblBoggle);
		dice= new char[1][1];
		dice[0][0] = 'v';
		char diceValue = dice[0][0];
		JLabel label = new JLabel(""+diceValue);
		label.setFont(new Font("Tahoma", Font.PLAIN, 70));
		Timer tick = new Timer(1,null);
		label.setBounds(89, 68, 86, 65);
		frame.getContentPane().add(label);
	}
}
