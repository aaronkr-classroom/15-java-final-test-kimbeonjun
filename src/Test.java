import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test extends JFrame {
	
	private JTextField inputSpace;
	private String num = "";
	private String prev_operation = "";
	private ArrayList<String> equation = new ArrayList<String>();
	
	public Test() {
		
		setLayout(null);
		setTitle("계산기");
		setSize(300, 370);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu01 = new JMenu("File");
		JMenu menu02 = new JMenu("Edit");
		JMenu menu03 = new JMenu("Help");
		menuBar.add(menu01);
		menuBar.add(menu02);
		menuBar.add(menu03);
		
		JMenuItem item01 = new JMenuItem("New");
		JMenuItem item02 = new JMenuItem("Open");
		menu01.add(item01);
		menu01.add(item02);
		
		setJMenuBar(menuBar);
		
		setLayout(null);
		setVisible(true);
		
		inputSpace = new JTextField();
		inputSpace.setEditable(false);
		inputSpace.setBackground(Color.WHITE);
		inputSpace.setHorizontalAlignment(JTextField.RIGHT);
		inputSpace.setFont(new Font("Arial", Font.BOLD, 50));
		inputSpace.setBounds(0, 0, 370, 70);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 4, 0, 0));
		buttonPanel.setBounds(0, 70, 270, 235);
		String button_names[] = { "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0", "AC", "=", "/" };
		JButton buttons[] = new JButton[button_names.length];
		
		for (int i = 0; i < button_names.length; i++) {
			buttons[i] = new JButton(button_names[i]);
			buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
			buttons[i].addActionListener(new PadActionListener());
			buttonPanel.add(buttons[i]);
		}
		
		add(inputSpace);
		add(buttonPanel);
		setVisible(true);
	}
	
	class PadActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String operation = e.getActionCommand();
			
			if (operation.equals("AC")) {
				inputSpace.setText("");
			} else if (operation.equals("=")) {
				String result = Double.toString(test(inputSpace.getText()));
				inputSpace.setText("" + result);
				num = "";
			} else if(operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
				if (inputSpace.getText().equals("") && operation.equals("-")) {
					inputSpace.setText(inputSpace.getText() + e.getActionCommand());
				} else if ( !inputSpace.getText().equals("") && !prev_operation.equals("+") && !prev_operation.equals("-") && !prev_operation.equals("*") && !prev_operation.equals("/")) {
					inputSpace.setText(inputSpace.getText() + e.getActionCommand());
				}
			}
			else {
				inputSpace.setText(inputSpace.getText() + e.getActionCommand());
			}
			prev_operation = e.getActionCommand();
		}
	}
	
	private void fullTextParsing(String inputText) {
		equation.clear();
		
		for (int i = 0; i < inputText.length(); i++) {
			char ch = inputText.charAt(i);
			
			if (ch == '-' || ch == '+' || ch == '*' || ch == '/') {
				equation.add(num);
				num = "";
				equation.add(ch + "");
			} else {
				num = num + ch;
			}
		}
		equation.add(num);
		equation.remove("");
	}
	
	public double test(String inputText) {
		fullTextParsing(inputText);
		
		double prev = 0;
		double current = 0;
		String mode = "";
		
		for (String s : equation) {
			if (s.equals("+")) {
				mode = "add";
			} else if (s.equals("-")) {
				mode = "sub";
			} else if (s.equals("*")) {
				mode = "mul";
			} else if (s.equals("/")) {
				mode = "div";
			} else {
				current = Double.parseDouble(s);
				
				if (mode.equals("add")) {
					prev += current;
				} else if (mode.equals("sub")) {
					prev -= current;
				} else if (mode.equals("mul")) {
					prev *= current;
				} else if (mode.equals("div")) {
					prev /= current;
				} else {
					prev = current;
				}
			}
			return prev;
		}
		return prev;
	}

	public static void main(String[] args) {
		new Test();
	}
}
