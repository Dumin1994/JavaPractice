package zsc.dumin_fivechess.com;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 * �������
 * @author ����
 */
public class ChessPanel extends JPanel {
	static ImageIcon WHITE_CHESS_ICON;  //����ͼ��
	static ImageIcon BLACK_CHESS_ICON;  //����ͼ��
	final static int OPRATION_REPENT = 0xEF; // ��������
	final static int OPRATION_NODE_REPENT = 0xCF; // ���ܻ�������
	final static int OPRATION_DRAW = 0xFE; // ��������
	final static int OPRATION_NODE_DRAW = 0xEE; // ���ܺ�������
	final static int OPRATION_START = 0xFd; // ��ʼ����
	final static int OPRATION_ALL_START = 0xEd; // ���ܿ�ʼ����
	final static int OPRATION_GIVEUP = 0xFc; // ��������
	final static int WIN = 88; // ʤ������
	private boolean towardsStart = false;
	private Image backImg;
	protected JButton backButton;
	private JToggleButton backplayToggleButton;
	private JLabel bannerLabel;
	private JButton giveupButton;
	private GobangPanel gobangPanel1;
	private JButton heqiButton;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	protected JLabel leftInfoLabel;
	protected JLabel myChessColorLabel;
	protected JLabel rightInfoLabel;
	private JButton startButton;
	protected JLabel towardsChessColorLabel;
	int backIndex = 1;

	/**
	 * �������Ĺ��췽��
	 */
	public ChessPanel() {
		WHITE_CHESS_ICON = new javax.swing.ImageIcon(getClass().getResource(
				"/res/whiteChess.png")); // ��ʼ���������ͼƬ
		BLACK_CHESS_ICON = new javax.swing.ImageIcon(getClass().getResource(
				"/res/blackChess.png")); // ��ʼ���������ͼƬ
		URL url = getClass().getResource("/res/bg/1.jpg");
		backImg = new ImageIcon(url).getImage(); // ��ʼ������ͼƬ
		initComponents(); // ���ó�ʼ������ķ���
	}

	/**
	 * ��дpaintComponent���������Ʊ���ͼƬ
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// ���Ʊ���ͼƬ
		g.drawImage(backImg, 0, 0, getWidth(), getHeight(), null);
	}

	/**
	 * ����������ɫ�ķ������������ɫΪ��
	 * 
	 * @param color
	 *            - ָ����ɫ�����ͼƬ
	 */
	public void setChessColor(ImageIcon color) {
		myChessColorLabel.setIcon(color); // ���ñ����û������ͼ��
		if (color.equals(WHITE_CHESS_ICON)) { // ���ð���
			gobangPanel1.setMyColor(GobangPanel.WHITE_CHESSMAN);
			towardsChessColorLabel.setIcon(BLACK_CHESS_ICON);
		} else if (color.equals(BLACK_CHESS_ICON)) {// ���ú���
			gobangPanel1.setMyColor(GobangPanel.BLACK_CHESSMAN);
			towardsChessColorLabel.setIcon(WHITE_CHESS_ICON);
		}
		revalidate();
	}

	/**
	 * �����ֻ�״̬�ķ���
	 * 
	 * @param turn
	 *            - �Ƿ�������Ȩ��
	 */
	public void setTurn(boolean turn) {
		if (turn) { // ����������Ȩ��
			myChessColorLabel.setVisible(true); // ��ʾ���
			towardsChessColorLabel.setVisible(false); // ���ضԼ����
		} else {// ����
			myChessColorLabel.setVisible(false); // �����Լ������
			towardsChessColorLabel.setVisible(true); // ��ʾ�Լҵ����
		}
	}

	public boolean isTowardsStart() {
		return towardsStart;
	}

	public void setTowardsStart(boolean towardsStart) {
		this.towardsStart = towardsStart;
	}

	public GobangPanel getGobangPanel1() {
		return gobangPanel1;
	}

	/**
	 * �����ҵ������
	 */
	public synchronized void repentOperation() {
		// ��ȡ�������
		Deque<byte[][]> chessQueue = gobangPanel1.getChessQueue();
		if (chessQueue.isEmpty()) {
			return;
		}
		// ��ȡ�����δ����������
		for (int i = 0; i < 2 && !chessQueue.isEmpty(); i++) {
			byte[][] pop = chessQueue.pop(); // �������岽��
		}
		if (chessQueue.size() < 1) {
			chessQueue.push(new byte[15][15]);
		}
		byte[][] pop = chessQueue.peek();
		GobangModel.getInstance().updateChessmanArray(pop);// �������̵����Ӳ���
		repaint();
	}

	public void send(Object opration) {
		GameFiveChess mainFrame = (GameFiveChess) getRootPane().getParent();
		mainFrame.send(opration); // ��������

	}

	/**
	 * ���³�ʼ����Ϸ״̬
	 */
	void reInit() {
		gobangPanel1.oldRec();
		startButton.setEnabled(true);
		giveupButton.setEnabled(false);
		heqiButton.setEnabled(false);
		backButton.setEnabled(false);
		gobangPanel1.setStart(false);
		setTowardsStart(false);
	}

	/**
	 * Ϊ˫����ҷ������ӵķ���
	 */
	private void fenqi() {
		GameFiveChess frame = (GameFiveChess) getRootPane().getParent(); // ��ȡ���������
		// ��ȡ�Լҿ�ʼ��Ϸ��ʱ��
		long towardsTime = frame.getTowardsUser().getTime().getTime();
		// ��ȡ�Լ���ʼ��Ϸ��ʱ��
		long meTime = frame.getUser().getTime().getTime();
		// ����������ҿ�ʼ��Ϸʱ����Ⱥ󣬷������ӵ���ɫ
		if (meTime >= towardsTime) {
			frame.getChessPanel1().setChessColor(ChessPanel.WHITE_CHESS_ICON);
			frame.getChessPanel1().getGobangPanel1().setTurn(true);
		} else {
			frame.getChessPanel1().setChessColor(ChessPanel.BLACK_CHESS_ICON);
			frame.getChessPanel1().getGobangPanel1().setTurn(false);
		}
	}

	/**
	 * ���� ������̵ķ���������ʹ��1��-1�ƶ�������̵����ӣ�ʹ��0�������
	 * 
	 * @param chessman
	 *            - ������̵����ӵ���ɫ����
	 */
	private void fillChessBoard(final byte chessman) {
		try {
			Runnable runnable = new Runnable() { // ���������Ķ����߳�
				/**
				 * �̵߳����巽��
				 * 
				 * @see java.lang.Runnable#run()
				 */
				public void run() {
					byte[][] chessmanArray = GobangModel.getInstance()
							.getChessmanArray(); // ��ȡ��������
					for (int i = 0; i < chessmanArray.length; i += 2) {
						try {
							Thread.sleep(10); // �������ʱ��
						} catch (InterruptedException ex) {
							Logger.getLogger(ChessPanel.class.getName()).log(
									Level.SEVERE, null, ex);
						}
						// ʹ��ָ����ɫ��������������һ��
						Arrays.fill(chessmanArray[i], chessman);
						Arrays.fill(chessmanArray[(i + 1) % 15], chessman);
						GobangModel.getInstance().updateChessmanArray(
								chessmanArray); // ���������ϵ�����
						gobangPanel1.paintImmediately(0, 0, getWidth(),
								getHeight()); // �����ػ�ָ�����������
					}
				}
			};
			// ���¼�������ִ������
			if (SwingUtilities.isEventDispatchThread()) {
				runnable.run();
			} else {
				SwingUtilities.invokeAndWait(runnable);
			}
		} catch (Exception ex) {
			Logger.getLogger(ChessPanel.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * ��ʼ���������ķ���
	 */
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jPanel1.setOpaque(false);
		backButton = new javax.swing.JButton();
		heqiButton = new javax.swing.JButton();
		startButton = new javax.swing.JButton();
		giveupButton = new javax.swing.JButton();
		backplayToggleButton = new javax.swing.JToggleButton();
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setOpaque(false);
		jLabel5 = new javax.swing.JLabel();
		leftInfoLabel = new javax.swing.JLabel();
		leftInfoLabel.setForeground(new Color(0, 255, 0));
		leftInfoLabel.setFont(new Font("����", Font.PLAIN, 22));
		myChessColorLabel = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jPanel3.setOpaque(false);
		jLabel6 = new javax.swing.JLabel();
		rightInfoLabel = new javax.swing.JLabel();
		rightInfoLabel.setForeground(Color.GREEN);
		rightInfoLabel.setFont(new Font("����", Font.PLAIN, 22));
		towardsChessColorLabel = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jPanel4.setOpaque(false);
		bannerLabel = new javax.swing.JLabel();
		gobangPanel1 = new zsc.dumin_fivechess.com.GobangPanel();

		setLayout(new java.awt.BorderLayout());
		setOpaque(false);

		backButton.setText("����");
		backButton.setEnabled(false);
		backButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backButtonActionPerformed(evt);
			}
		});
		jPanel1.add(backButton);

		heqiButton.setText("����");
		heqiButton.setEnabled(false);
		heqiButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				heqiButtonActionPerformed(evt);
			}
		});
		jPanel1.add(heqiButton);

		startButton.setText("��ʼ");
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});
		jPanel1.add(startButton);

		giveupButton.setText("����");
		giveupButton.setEnabled(false);
		giveupButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				giveupButtonActionPerformed(evt);
			}
		});
		jPanel1.add(giveupButton);

		backplayToggleButton.setText("��Ϸ�ط�");
		backplayToggleButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						backplayToggleButtonActionPerformed(evt);
					}
				});
		jPanel1.add(backplayToggleButton);

		add(jPanel1, java.awt.BorderLayout.PAGE_END);

		final JButton button = new JButton();
		button.addActionListener(new ButtonActionListener());
		button.setText("��������");
		jPanel1.add(button);

		jPanel2.setPreferredSize(new java.awt.Dimension(110, 100));
		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,
				50, 60));

		jLabel5.setPreferredSize(new java.awt.Dimension(42, 55));
		jPanel2.add(jLabel5);

		leftInfoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/res/infoPanelLeft.png")));
		leftInfoLabel
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		leftInfoLabel
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jPanel2.add(leftInfoLabel);

		myChessColorLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/res/whiteChess.png")));
		jPanel2.add(myChessColorLabel);

		add(jPanel2, java.awt.BorderLayout.LINE_START);

		jPanel3.setPreferredSize(new java.awt.Dimension(110, 100));
		jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,
				50, 60));

		jLabel6.setPreferredSize(new java.awt.Dimension(42, 55));
		jPanel3.add(jLabel6);

		rightInfoLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/res/infoPanel.png")));
		rightInfoLabel
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		rightInfoLabel
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jPanel3.add(rightInfoLabel);

		towardsChessColorLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/res/blackChess.png"))); // NOI18N
		jPanel3.add(towardsChessColorLabel);

		add(jPanel3, java.awt.BorderLayout.LINE_END);

		jPanel4.setLayout(new java.awt.BorderLayout());

		bannerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		bannerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/res/logo.png"))); // NOI18N
		bannerLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1,
				5, 1));
		bannerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bannerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				bannerLabelMouseClicked(evt);
			}
		});
		jPanel4.add(bannerLabel, java.awt.BorderLayout.CENTER);

		add(jPanel4, java.awt.BorderLayout.PAGE_START);

		add(gobangPanel1, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout gobangPanel1Layout = new javax.swing.GroupLayout(
				gobangPanel1);
		gobangPanel1Layout.setHorizontalGroup(gobangPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						280, Short.MAX_VALUE));
		gobangPanel1Layout.setVerticalGroup(gobangPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						248, Short.MAX_VALUE));
		gobangPanel1.setLayout(gobangPanel1Layout);
	}

	/**
	 * ��ʼ��ť���¼�������
	 * 
	 * @param evt
	 *            - �¼�����
	 */
	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// ��ȡ���������
		GameFiveChess mainFrame = (GameFiveChess) getRootPane().getParent();
		if (mainFrame.serverSocket == null) {
			JOptionPane.showMessageDialog(this, "��ȴ��Է����ӡ�");
			return;
		}
		if (gobangPanel1.isStart()) {
			return;
		}
		// ���ø�����ť�Ŀ���״̬
		startButton.setEnabled(false);
		giveupButton.setEnabled(true);
		heqiButton.setEnabled(true);
		backButton.setEnabled(true);
		gobangPanel1.setStart(true); // ������Ϸ�Ŀ�ʼ״̬
		gobangPanel1.setTowardsWin(false); // ���öԼ�ʤ��״̬
		gobangPanel1.setWin(false); // �����Լ�ʤ��״̬
		gobangPanel1.setDraw(false); // ���ú���״̬
		send(OPRATION_START);// ���Ϳ�ʼָ��
		fenqi(); // ����˫������
		fillChessBoard(gobangPanel1.getMyColor());// ʹ���Լ���������ɫ����
		fillChessBoard((byte) 0); // ʹ�ÿ���������
		byte[][] data = new byte[15][15]; // ����һ���յ����̲���
		GobangModel.getInstance().setChessmanArray(data);// ��������ʹ�ÿղ���
	}

	/**
	 * ���䰴ť���¼�������
	 * 
	 * @param evt
	 *            - ��ť���¼�����
	 */
	private void giveupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// ���û���Լ����壬��ʾ�û��ȴ�
		if (!gobangPanel1.isTurn()) {
			JOptionPane.showMessageDialog(this, "û���������ء�", "��ȴ�...",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		send(OPRATION_GIVEUP);// ��������ָ��
		// ����һ���µ��߳�ʹ���䰴ť5�벻����
		new Thread() {
			@Override
			public void run() {
				try {
					giveupButton.setEnabled(false);
					sleep(5000);
					giveupButton.setEnabled(true);
				} catch (InterruptedException ex) {
					Logger.getLogger(ChessPanel.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}.start();
	}

	/**
	 * ���尴ť���¼�������
	 * 
	 * @param evt
	 */
	private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// ���û���Լ����壬��ʾ�û�
		if (!gobangPanel1.isTurn()) {
			JOptionPane.showMessageDialog(this, "û���������ء�", "��ȴ�...",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		send(OPRATION_REPENT);// ���ͻ�������
		new Thread() { // �����µ��̣߳�ʹ���尴ť����5��
			@Override
			public void run() {
				try {
					backButton.setEnabled(false);
					sleep(5000);
					backButton.setEnabled(true);
				} catch (InterruptedException ex) {
					Logger.getLogger(ChessPanel.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}.start();
	}

	/**
	 * ���尴ť���¼�������
	 * 
	 * @param evt
	 *            - ��ť��action�¼�����
	 */
	private void heqiButtonActionPerformed(java.awt.event.ActionEvent evt) {
		send(OPRATION_DRAW);// ���ͺ���ָ��
		new Thread() { // �����µ��߳�ʹ���尴ť5�벻����
			@Override
			public void run() {
				try {
					heqiButton.setEnabled(false);
					sleep(5000);
					heqiButton.setEnabled(true);
				} catch (InterruptedException ex) {
					Logger.getLogger(ChessPanel.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}.start();
	}

	/**
	 * ���ͼƬ����굥���¼�������
	 * 
	 * @param evt
	 *            - ����¼�����
	 */
	private void bannerLabelMouseClicked(java.awt.event.MouseEvent evt) {
		try {
			// ����Desktop���browse���������̴ʵ���ҳ
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(
						new URL("http://www.mrbccd.com").toURI());
			} else {
				JOptionPane.showMessageDialog(this, "��ǰϵͳ��֧�ָò���");
			}
		} catch (Exception ex) {
			Logger.getLogger(ChessPanel.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * ��Ϸ�طŰ�ť���¼�������
	 * 
	 * @param evt
	 *            - �¼�����
	 */
	private void backplayToggleButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		// �����Ϸ�����У���ʾ�û���Ϸ�������ڹۿ���Ϸ�ط�
		if (gobangPanel1.isStart()) {
			JOptionPane.showMessageDialog(this, "������Ϸ�����󣬹ۿ���Ϸ�طš�");
			backplayToggleButton.setSelected(false);
			return;
		}
		if (!backplayToggleButton.isSelected()) {
			backplayToggleButton.setText("��Ϸ�ط�");
		} else {
			backplayToggleButton.setText("��ֹ�ط�");
			new Thread() { // �����µ��̲߳�����Ϸ��¼
				public void run() {
					Object[] toArray = gobangPanel1.getOldRec();
					if (toArray == null) {
						JOptionPane.showMessageDialog(ChessPanel.this,
								"û����Ϸ��¼", "��Ϸ�ط�", JOptionPane.WARNING_MESSAGE);
						backplayToggleButton.setText("��Ϸ�ط�");
						backplayToggleButton.setSelected(false);
						return;
					}
					// �������Ľ�����֣������Է�ʤ������ʤ���ˡ���սƽ��
					gobangPanel1.setTowardsWin(false);
					gobangPanel1.setWin(false);
					gobangPanel1.setDraw(false);
					for (int i = toArray.length - 1; !gobangPanel1.isStart()
							&& backplayToggleButton.isSelected() && i >= 0; i--) {
						try {
							Thread.sleep(1000); // �߳�����1��
						} catch (InterruptedException ex) {
							Logger.getLogger(ChessPanel.class.getName()).log(
									Level.SEVERE, null, ex);
						}
						GobangModel.getInstance().updateChessmanArray(
								(byte[][]) toArray[i]); // ������Ϸ��¼����ÿһ����Ϸ������
						gobangPanel1.repaint(); // �ػ�����
					}
					backplayToggleButton.setSelected(false);
					backplayToggleButton.setText("��Ϸ�ط�");
				}
			}.start();
		}
	}

	/**
	 * ��������ͼƬ�İ�ť�¼�������
	 * 
	 * @author Li Zhong Wei
	 */
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			backIndex = backIndex % 9 + 1; // ��ȡ9�ű���ͼƬ�������ĵ���
			URL url = getClass().getResource("/res/bg/" + backIndex + ".jpg");
			backImg = new ImageIcon(url).getImage(); // ��ʼ������ͼƬ
			repaint(); // ���»����������
		}
	}
}
