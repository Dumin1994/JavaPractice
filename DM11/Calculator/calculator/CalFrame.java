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

import fraction.FractionMain;
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
	private String oper = "=";// ���������
	private double result = 0;// ���������
	private boolean IfResult = true, flag = false;
	private Num numActionListener; // ���ּ��������
	private ActionListener actionListener;
	private Font menuFont = new Font("����", Font.PLAIN, 12);// �˵���ť����

	/**
	 * ���캯��
	 */
	public CalFrame() {
		// TODO Auto-generated constructor stub
		super("DM11��ѧ������");// ���ñ�����
		this.setLayout(new BorderLayout(10, 5));// ˮƽ��������ֱ�Ϊ10,5
		panel1 = new JPanel(new GridLayout(1, 3, 10, 10));
		panel2 = new JPanel(new GridLayout(5, 6, 5, 5));// 5��6�� ,��ťˮƽ���������Ϊ5
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

		// ��ʼ��������
		numActionListener = new Num();

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
		txtResult.setText(String.valueOf(result));
	}

	/**
	 * 
	 */
	private void init() {
		// TODO Auto-generated method stub
		addButton(panel1, "Backspace", new Clear(), Color.red);
		addButton(panel1, "CE", new Clear(), Color.red);
		addButton(panel1, "C", new Clear(), Color.red);

		addButton(panel2, "1/x", new Signs(), Color.magenta);
		addButton(panel2, "log", new Signs(), Color.magenta);
		addButton(panel2, "7", numActionListener, Color.blue);
		addButton(panel2, "8", numActionListener, Color.blue);
		addButton(panel2, "9", numActionListener, Color.blue);
		addButton(panel2, "��", new Signs(), Color.red);

		addButton(panel2, "n!", new Signs(), Color.magenta);
		addButton(panel2, "sqrt", new Signs(), Color.magenta);
		addButton(panel2, "4", numActionListener, Color.blue);
		addButton(panel2, "5", numActionListener, Color.blue);
		addButton(panel2, "6", numActionListener, Color.blue);
		addButton(panel2, "��", new Signs(), Color.red);

		addButton(panel2, "sin", new Signs(), Color.magenta);
		addButton(panel2, "x^2", new Signs(), Color.magenta);
		addButton(panel2, "1", numActionListener, Color.blue);
		addButton(panel2, "2", numActionListener, Color.blue);
		addButton(panel2, "3", numActionListener, Color.blue);
		addButton(panel2, "-", new Signs(), Color.red);

		addButton(panel2, "cos", new Signs(), Color.magenta);
		addButton(panel2, "x^3", new Signs(), Color.magenta);
		addButton(panel2, "0", numActionListener, Color.blue);
		addButton(panel2, "-/+", new Clear(), Color.blue);
		addButton(panel2, ".", new Dot(), Color.blue);
		addButton(panel2, "+", new Signs(), Color.red);

		addButton(panel2, "tan", new Signs(), Color.magenta);
		addButton(panel2, "%", new Signs(), Color.magenta);
		addButton(panel2, "��", numActionListener, Color.orange);
		addButton(panel2, "e", numActionListener, Color.orange);
		addButton(panel2, "���", new Signs(), Color.orange);
		addButton(panel2, "=", new Signs(), Color.red);

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
					String cmd = e.getActionCommand();
					if (cmd.equals("2048С��Ϸ")) {
						Game2048 game2048 = new Game2048();
						game2048.runGame();
					} else if (cmd.equals("����������")) {
						GameFiveChess gameFiveChess = new GameFiveChess();
						gameFiveChess.runGame();
					} else if (cmd.equals("̰ʳ��С��Ϸ")) {
						GameSnake gameSnake = new GameSnake();
					} else if (cmd.equals("��������")) {
                        FractionMain fractionMain=new FractionMain();
                        fractionMain.run();
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
					txtResult.setText("�������ܿ�ƽ������");
				}
			} else if (cmd.equals("log")) {
				double num = Double.parseDouble(txtResult.getText());
				if (num > 0) {
					txtResult.setText(String.valueOf(MathCal.log(num)));
				} else {
					txtResult.setText("���������������");
				}
			}

			/* %��ٷֱ� */
			else if (cmd.equals("%")) {
				double num = Double.parseDouble(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.percent(num)));
			}

			/* 1/x���� */
			else if (cmd.equals("1/x")) {
				double num = Double.parseDouble(txtResult.getText());
				if (num == 0) {
					txtResult.setText("0û�е�����");
				} else {
					txtResult.setText(String.valueOf(MathCal.daoShu(num)));
				}
			}

			/* �����Һ��� */
			else if (cmd.equals("sin")) {
				double num = Double.parseDouble(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.sin(num)));
			}

			/* �����Һ��� */
			else if (cmd.equals("cos")) {
				double num = Double.parseDouble(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.cos(num)));
			}

			/* �����к��� */
			else if (cmd.equals("tan")) {
				double num = Double.parseDouble(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.tan(num)));
			}

			/* ��׳� */
			else if (cmd.equals("n!")) {
				int num = Integer.parseInt(txtResult.getText());
				if (num < 0) {
					txtResult.setText("�����޷����н׳�");
				} else if (num == 0) {
					txtResult.setText("0");
				} else {
					txtResult.setText(String.valueOf(MathCal.jieCheng(num)));
				}
			}

			/* ��ƽ�� */
			else if (cmd.equals("x^2")) {
				int num = Integer.parseInt(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.pow(num, 2)));
			}

			/* ������ */
			else if (cmd.equals("x^3")) {
				int num = Integer.parseInt(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.pow(num, 3)));
			}

			/* ���Ƕ�ת�� */
			else if (cmd.equals("���")) {
				double num = Double.parseDouble(txtResult.getText());
				txtResult.setText(String.valueOf(MathCal.angle(num)));
			}

			else {
				if (flag) {
					IfResult = false;
				}
				if (IfResult) {
					oper = cmd;
				} else {
					getResult(Double.parseDouble(txtResult.getText()));
					oper = cmd;
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
			String cmd = e.getActionCommand();
			if (cmd == "C") {
				txtResult.setText("0");
				IfResult = true;
				result = 0;
			} else if (cmd == "-/+") {
				double num = 0 - Double.parseDouble(txtResult.getText().trim());
				txtResult.setText(String.valueOf(num));
			} else if (cmd == "Backspace") {
				if (Double.parseDouble(txtResult.getText()) > 0) {
					if (txtResult.getText().length() > 1) {
						txtResult.setText(txtResult.getText().substring(0, txtResult.getText().length() - 1));
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
			} else if (cmd == "CE") {
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
			String cmd = e.getActionCommand();
			if (IfResult) {
				txtResult.setText("");
				IfResult = false;
			}
			if (cmd == "��") {
				txtResult.setText(String.valueOf(Math.PI));
			} else if (cmd == "e") {
				txtResult.setText(String.valueOf(Math.E));
			} else {
				txtResult.setText(txtResult.getText().trim() + cmd);
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
