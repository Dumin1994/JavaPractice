/**
 * 
 */
package calculator;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import zsc.dumin_fivechess.com.GameFiveChess;
import zsc.dumin_gamesnack.com.GameSnake;
import zsc_dumin_game2048.com.Game2048;

/**
 * @author ����
 *
 */
public class CalFrame extends JFrame {

	private JTextField txtResult; // ��ʾ��������
	private JPanel panel1, panel2, panel3, panel4; // �ĸ����
	private JMenuBar myMenuBar; // ˮƽ�˵���
	private JMenu menuGame, menuFraction; // ��Ϸ�˵�����������˵�
	private JMenuItem game2048_item, gameFiveChess_item, gameSnack_item, fraction_item;// 2048�����������塢̰ʳ�ߡ���������
	private DecimalFormat df; // �����ָ�ʽ��
	private String oper = "=";// ���������
	private double result = 0;// ���������
	private boolean IfResult = true, flag = false;
	private Num numActionListener; // ���ּ��������
	private ActionListener actionListener;// ���������
	// �˵���ť����
	private Font menuFont = new Font("����", Font.PLAIN, 12);
	private Font buttonFont = new Font("����", Font.PLAIN, 12);

	/**
	 * ���캯��
	 */
	public CalFrame() {
		// TODO Auto-generated constructor stub
		super("DM11��ѧ������");// ���ñ����������ó���Ĺ��캯����
		df = new DecimalFormat("#.####");// ������λС��
		this.setLayout(new BorderLayout(10, 5));// ��ťˮƽ��������ֱ�Ϊ10,5
		panel1 = new JPanel(new GridLayout(1, 3, 10, 10));
		panel2 = new JPanel(new GridLayout(5, 6, 5, 5));// 5��6�� ,��ťˮƽ���������Ϊ5
		panel3 = new JPanel(new GridLayout(4, 1, 5, 5));
		panel4 = new JPanel(new BorderLayout(5, 5));

		myMenuBar = new JMenuBar(); // ����ˮƽ�˵���

		/**
		 * �����˵���ť
		 */
		menuGame = new JMenu("С��Ϸ");
		menuFraction = new JMenu("����");
		menuGame.setFont(menuFont);
		menuFraction.setFont(menuFont);

		/**
		 * ������Ϸ�˵��Ӳ˵�
		 */
		game2048_item = new JMenuItem("2048С��Ϸ");
		gameFiveChess_item = new JMenuItem("����������");
		gameSnack_item = new JMenuItem("̰ʳ��С��Ϸ");

		// ����Ϸ�˵�������Ӳ˵�
		menuGame.add(game2048_item);
		menuGame.addSeparator();// ��ӷָ���
		menuGame.add(gameFiveChess_item);
		menuGame.addSeparator();// ��ӷָ���
		menuGame.add(gameSnack_item);

		/**
		 * ���������˵��Ӳ˵�
		 */
		fraction_item = new JMenuItem("��������");

		// ������˵�������Ӳ˵�
		menuFraction.add(fraction_item);

		// ��ˮƽ�˵�����Ӳ˵���
		myMenuBar.add(menuGame);
		myMenuBar.add(menuFraction);
		this.setJMenuBar(myMenuBar);

		/**
		 * ��Ӳ˵���ť�����¼�
		 */
		game2048_item.addActionListener(getActionListener());
		gameFiveChess_item.addActionListener(getActionListener());
		gameSnack_item.addActionListener(getActionListener());
		fraction_item.addActionListener(getActionListener());

		/**
		 * �ı��򣬼�Ϊ����������Ļ��ʾ����
		 */
		txtResult = new JTextField();
		txtResult.setEditable(false);// �ı����򲻿ɱ༭
		txtResult.setBackground(Color.white);// �ı�����ı���ɫ
		txtResult.setHorizontalAlignment(JTextField.RIGHT);// �����Ҷ���
		txtResult.setText("0");
		txtResult.setBorder(BorderFactory.createLoweredBevelBorder());
		init();// �Լ��������г�ʼ��
	}

	/**
	 * 
	 * @param panel
	 * @param name
	 * @param action
	 * @param color
	 */
	private void addButton(JPanel panel, String name, ActionListener action, Color color) {
		JButton bt = new JButton(name);
		panel.add(bt);// ����������Ӱ�ť
		bt.setForeground(color);// ����ǰ�������壩��ɫ
		bt.addActionListener(action);// ���Ӽ����¼�
	}

	/**
	 * �������Ļ���������+ - �� �£�
	 * 
	 * @param x
	 */
	private void getResult(double x) {
		if (oper == "+") {
			result += x;
		} else if (oper == "-") {
			result -= x;
		} else if (oper == "��") {
			result *= x;
		} else if (oper == "��") {
			result /= x;
		} else if (oper == "=") {
			result = x;
		}
		txtResult.setText(df.format(result));
	}

	/**
	 * 
	 */
	private void init() {
		// TODO Auto-generated method stub
		addButton(panel1, "Backspace", new Clear(), Color.red);
		addButton(panel1, "CE", new Clear(), Color.red);
		addButton(panel1, "C", new Clear(), Color.red);
		addButton(panel2, "-/+", new Clear(), Color.blue);
		
		addButton(panel2, "7", numActionListener, Color.blue);
		addButton(panel2, "8", numActionListener, Color.blue);
		addButton(panel2, "9", numActionListener, Color.blue);
		addButton(panel2, "4", numActionListener, Color.blue);
		addButton(panel2, "5", numActionListener, Color.blue);
		addButton(panel2, "6", numActionListener, Color.blue);	
		addButton(panel2, "1", numActionListener, Color.blue);
		addButton(panel2, "2", numActionListener, Color.blue);
		addButton(panel2, "3", numActionListener, Color.blue);
		addButton(panel2, "0", numActionListener, Color.blue);
		addButton(panel2, "��", numActionListener, Color.orange);
		addButton(panel2, "e", numActionListener, Color.orange);
		
		addButton(panel2, ".", new Dot(), Color.blue);
		
		addButton(panel2, "��", new Signs(), Color.red);
		addButton(panel2, "n!", new Signs(), Color.magenta);
		addButton(panel2, "sqrt", new Signs(), Color.magenta);
		addButton(panel2, "+", new Signs(), Color.red);
		addButton(panel2, "tan", new Signs(), Color.magenta);
		addButton(panel2, "%", new Signs(), Color.magenta);
		addButton(panel2, "���", new Signs(), Color.orange);
		addButton(panel2, "=", new Signs(), Color.red);
		addButton(panel2, "-", new Signs(), Color.red);
		addButton(panel2, "cos", new Signs(), Color.magenta);
		addButton(panel2, "x^3", new Signs(), Color.magenta);
		addButton(panel2, "��", new Signs(), Color.red);
		addButton(panel2, "sin", new Signs(), Color.magenta);
		addButton(panel2, "x^2", new Signs(), Color.magenta);
		addButton(panel2, "1/x", new Signs(), Color.magenta);
		addButton(panel2, "log", new Signs(), Color.magenta);
		
		panel4.add(panel1, BorderLayout.NORTH);
		panel4.add(panel2, BorderLayout.CENTER);
		this.add(txtResult, BorderLayout.NORTH);
		this.add(panel4);
	}

	public ActionListener getActionListener() {
		if (actionListener == null) {
			actionListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
                    String cmd=e.toString();
                    if(cmd.equals("2048С��Ϸ"))
                    {
                    	Game2048 game2048=new Game2048();
                    	game2048.runGame();
                    }
                    else if(cmd.equals("����������")){
						GameFiveChess gameFiveChess=new GameFiveChess();
					}
                    else if(cmd.equals("̰ʳ��С��Ϸ"))
                    {
                    	GameSnake gameSnake=new GameSnake();
                    }
                    else if(cmd.equals("��������"))
                    {
                    	
                    }
				}
			};
		}
		return actionListener;
	}

	/**
	 * ������ŵ��¼�����
	 */
	class Signs implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			String cmd = e.getActionCommand();
			if (cmd.equals("sqrt")) {
				double num = Double.parseDouble(txtResult.getText());
				if (num >= 0) {
					txtResult.setText(String.valueOf(MathCal.sqrt(num))); 
				} else {
					txtResult.setText("�������ܿ�ƽ����");
				}
			}
			else if(cmd.equals("log")) {
				double num = Double.parseDouble(txtResult.getText());
				if (num > 0) {
					txtResult.setText(String.valueOf(MathCal.log(num)));
				} else {
					txtResult.setText("�������������");
				}
			}

			/* %��ٷֱ� */
			else if (cmd.equals("%")) {
				double num = Double.parseDouble(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.percent(num)));
			}

			/* 1/x���� */
			else if (str.equals("1/x")) {
				if (Double.parseDouble(txtResult.getText()) == 0) {
					txtResult.setText("��������Ϊ��");
				} else {
					txtResult.setText(
							txtResult.getText() + "�ĵ���Ϊ�� " + df.format(1 / Double.parseDouble(txtResult.getText())));
				}
			}

			/* sin�����Һ��� */
			else if (str.equals("sin")) {
				double i = Double.parseDouble(txtResult.getText());
				txtResult.setText("sin(" + txtResult.getText() + ") = " + String.valueOf(df.format(Math.sin(i))));
			}

			/* cos�����Һ��� */
			else if (str.equals("cos")) {
				double i = Double.parseDouble(txtResult.getText());
				txtResult.setText("cos(" + txtResult.getText() + ") = " + String.valueOf(df.format(Math.cos(i))));
			}

			/* tan�����к��� */
			else if (str.equals("tan")) {
				double i = Double.parseDouble(txtResult.getText());
				txtResult.setText("tan(" + txtResult.getText() + ") = " + String.valueOf(df.format(Math.tan(i))));
			}

			/* n!��׳� */
			else if (str.equals("n!")) {
				double i = Double.parseDouble(txtResult.getText());
				if ((i % 2 == 0) || (i % 2 == 1))// �ж�Ϊ�����Ž��н׳˲���
				{
					int j = (int) i;// ǿ������ת��
					int result = 1;
					for (int k = 1; k <= j; k++)
						result *= k;
					txtResult.setText(txtResult.getText() + "�Ľ׳�Ϊ��" + String.valueOf(result));
				} else {
					txtResult.setText("�޷����н׳�");
				}
			}

			/* x^2��ƽ�� */
			else if (str.equals("x^2")) {
				double i = Double.parseDouble(txtResult.getText());
				txtResult.setText(txtResult.getText() + "��ƽ��Ϊ��" + String.valueOf(df.format(i * i)));
			}

			/* x^3������ */
			else if (str.equals("x^3")) {
				double i = Double.parseDouble(txtResult.getText());
				txtResult.setText(txtResult.getText() + "������Ϊ��" + String.valueOf(df.format(i * i * i)));
			}

			/* ���Ƕ�ת�� */
			/**
			 * ���Ƕ�ֵת���ɻ���ֵ���������Ǻ����ļ���
			 */
			else if (str.equals("���")) {
				double i = Double.parseDouble(txtResult.getText());
				txtResult.setText(txtResult.getText() + "�Ļ���Ϊ��" + String.valueOf(i / 180 * Math.PI));
			}

			else {
				if (flag) {
					IfResult = false;
				}
				if (IfResult) {
					oper = str;
				} else {
					getResult(Double.parseDouble(txtResult.getText()));
					oper = str;
					IfResult = true;
				}
			}
		}
	}

	/**
	 * �����ť���¼�����
	 */
	class Clear implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/*
			 * ��ActionEvent�����getActionCommand()���� ȡ���������¼�������ص��ַ���
			 */
			String str = e.getActionCommand();
			if (str == "C") {
				txtResult.setText("0");
				IfResult = true;
				result = 0;
			} else if (str == "-/+") {
				double i = 0 - Double.parseDouble(txtResult.getText().trim());
				txtResult.setText(df.format(i));
			} else if (str == "Backspace") {
				if (Double.parseDouble(txtResult.getText()) > 0) {
					if (txtResult.getText().length() > 1) {
						txtResult.setText(txtResult.getText().substring(0, txtResult.getText().length() - 1));
						// ʹ���˸�ɾ�����һλ�ַ�
					} else {
						txtResult.setText("0");
						IfResult = true;
					}
				} else {
					if (txtResult.getText().length() > 2) {
						txtResult.setText(txtResult.getText().substring(0, txtResult.getText().length() - 1));
					} else {
						txtResult.setText("0");
						IfResult = true;
					}
				}
			} else if (str == "CE") {
				txtResult.setText("0");
				IfResult = true;
			}
		}
	}

	/**
	 * ����������¼�����
	 */
	class Num implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str = e.getActionCommand();
			if (IfResult) {
				txtResult.setText("");
				IfResult = false;
			}
			if (str == "��") {
				txtResult.setText("��= " + String.valueOf(Math.PI));
			} else if (str == "e") {
				txtResult.setText("e= " + String.valueOf(Math.E));
			} else {
				txtResult.setText(txtResult.getText().trim() + str);
				if (txtResult.getText().equals("0")) {
					txtResult.setText("0");
					IfResult = true;
					flag = true;

				}
			}
		}
	}

	/**
	 * С������¼�����
	 */
	class Dot implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			IfResult = false;
			if (txtResult.getText().trim().indexOf(".") == -1) {
				txtResult.setText(txtResult.getText() + ".");
			}
		}
	}
}
