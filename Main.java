package ru.java2e;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		gameplay objGameplay = new gameplay();
		obj.setBounds(10,10,700,600);
		obj.setTitle("Break the Berlin Wall");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		obj.add(objGameplay);
		
	}

}
