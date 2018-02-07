package com.ddpm.project.monkeyislandclientmock2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Terminal extends JFrame {

	private JPanel consolePanel;
	private JTextArea commandTA;
	private JScrollPane jsp;

	private ArrayList<Observateur> listObservateurs;
	
	/**
	 * Builds a new Terminal frame
	 */
	public Terminal(){
		createControls();
		this.add(consolePanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setTitle("CLIENT 2");
		listObservateurs=new ArrayList<>();
	}

	public void addObservateur(Observateur observateur){
		this.listObservateurs.add(observateur);
	}

	public void removeObservateur(Observateur observateur){
		this.listObservateurs.add(observateur);
	}

	public void notifySendText(String text){
		for(int i=0;i<listObservateurs.size();i++){
			listObservateurs.get(i).sendText(text);
		}
	}

	/**
	 * Initializes all controls for the TerminalFrame
	 */
	private void createControls() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.consolePanel = new JPanel();
		this.consolePanel.setBackground(Color.black);
		createTextArea();
		this.consolePanel.add(jsp);
	}
	
	/**
	 * Initializes the terminal text area
	 */
	private void createTextArea() {
		this.commandTA = new JTextArea(24,80);
		this.commandTA.setBackground(Color.black);
		this.commandTA.setForeground(Color.green);
		this.commandTA.setCaretColor(this.commandTA.getForeground());
		this.commandTA.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10){
					notifySendText(String.valueOf(commandTA.getText()));
					new java.util.Timer().schedule(
							new java.util.TimerTask() {
								@Override
								public void run() {
									commandTA.setText("");
								}
							},
							1000
					);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		this.jsp = new JScrollPane(commandTA);
	}

}

