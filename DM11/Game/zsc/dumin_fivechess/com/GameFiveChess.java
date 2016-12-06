package zsc.dumin_fivechess.com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author ����
 */
public class GameFiveChess extends JFrame {

	private JTextArea chatArea;
	private JTextField chatTextField;
	private ChessPanel chessPanel1;
	private JLabel jLabel1;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private LoginPanel loginPanel1;
	private JButton sendButton;
	protected JTable userInfoTable;
	private JTextArea userInfoTextArea;
	private Socket socket;
	private ObjectOutputStream objout;
	private UserBean towardsUser;// �Լ�
	protected UserBean user;
	Socket serverSocket;

	public Socket getServerSocket() {
		return serverSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	/**
	 * ��Լҷ�����Ϣ�ķ���
	 * 
	 * @param message
	 *            - Ҫ���͵��ı����������͵Ķ���
	 */
	public void send(Object message) {
		try {
			objout.writeObject(message); // ������������Ӷ���
			objout.flush();
		} catch (IOException ex) {
			Logger.getLogger(GameFiveChess.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public UserBean getUser() {
		return user;
	}

	/**
	 * �����û���Ϣ�ķ���
	 * 
	 * @param user
	 *            - �����û�����
	 */
	public void setUser(UserBean user) {
		this.user = user;
		// ���û���Ϣ�������ǳ�
		userInfoTextArea.setText("�ǳƣ�" + user.getName() + "\n");
		// ���IP��Ϣ
		userInfoTextArea.append("�ɣУ�" + user.getHost().getHostAddress() + "\n");
		// ��ȡ�û���Ϣ������������ģ�Ͷ���
		DefaultTableModel model = (DefaultTableModel) userInfoTable.getModel();
		Vector dataVector = model.getDataVector();
		Vector row = new Vector(); // ʹ���û���Ϣ�����������ݵ�����
		row.add(user.getName());
		row.add(user.getHost().getHostName());
		row.add(user.getTime());
		if (!dataVector.contains(row)) {
			model.getDataVector().add(row); // ���û���Ϣ��ӵ���������
		}
		// ���ñ����û����ǳ�
		chessPanel1.leftInfoLabel.setText(user.getName());
		userInfoTable.revalidate();
	}

	/**
	 * ����Socket���Ӻͳ�ʼ������������ķ���
	 * 
	 * @param chatSocketArg
	 *            - Socket����
	 */
	public void setSocket(Socket chatSocketArg) {
		try {
			socket = chatSocketArg;
			OutputStream os = socket.getOutputStream(); // ��ȡSocket�������
			objout = new ObjectOutputStream(os); // �������������
		} catch (IOException ex) {
			Logger.getLogger(GameFiveChess.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	/**
	 * ������Ĺ��췽��
	 */
	public GameFiveChess() {
		initComponents(); // ��ʼ���������
		setGlassPane(loginPanel1); // ���õ�¼���Ϊ�������
		loginPanel1.setVisible(true); // ��ʾ��¼���
	}

	/**
	 * ���öԼ��û���Ϣ�ķ���
	 * 
	 * @param user
	 *            - �Լ�ͨ�����緢�������û�����
	 */
	public void setTowardsUser(UserBean user) {
		this.towardsUser = user; // �Լ��û�����
		// ��ȡ�û���Ϣ�б�ı������ģ��
		DefaultTableModel model = (DefaultTableModel) userInfoTable.getModel();
		Vector row = new Vector(); // �������ر�������ݵ��������϶���
		row.add(towardsUser.getName()); // ����û�����
		row.add(towardsUser.getHost().getHostName());// �����������
		row.add(towardsUser.getTime()); // ����û���¼ʱ��
		Vector dataVector = model.getDataVector();
		if (!dataVector.contains(row)) {
			model.getDataVector().add(row); // ����û���Ϣ�������
		}
		// ���öԼ��û�ͷ����ǳ�
		chessPanel1.rightInfoLabel.setText(towardsUser.getName());
		userInfoTable.revalidate();
	}

	public UserBean getTowardsUser() {
		return towardsUser;
	}

	/**
	 * ��ʼ�����������ķ���
	 */
	private void initComponents() {
		loginPanel1 = new LoginPanel();
		chessPanel1 = new ChessPanel();
		jPanel1 = new JPanel();
		jPanel3 = new JPanel();
		jLabel1 = new JLabel();
		jLabel1.setOpaque(true);
		jLabel1.setBackground(Color.WHITE);
		jScrollPane2 = new JScrollPane();
		userInfoTextArea = new JTextArea();
		jPanel4 = new JPanel();
		jScrollPane1 = new JScrollPane();
		userInfoTable = new JTable();
		jPanel2 = new JPanel();
		jPanel5 = new JPanel();
		chatTextField = new JTextField();
		sendButton = new JButton();
		jScrollPane3 = new JScrollPane();
		chatArea = new JTextArea();

		setTitle("DM11������");
		getContentPane().add(chessPanel1, BorderLayout.CENTER);

		jPanel1.setLayout(new BoxLayout(jPanel1,
				BoxLayout.PAGE_AXIS));

		jPanel3.setBorder(BorderFactory
				.createLineBorder(new Color(0, 0, 0)));
		jPanel3.setPreferredSize(new Dimension(225, 50));
		jPanel3.setLayout(new BorderLayout());

		jLabel1.setIcon(new ImageIcon(getClass().getResource(
				"/res/infoPanelLeft.png"))); // NOI18N
		jPanel3.add(jLabel1, BorderLayout.WEST);

		userInfoTextArea.setColumns(20);
		userInfoTextArea.setEditable(false);
		userInfoTextArea.setLineWrap(true);
		userInfoTextArea.setRows(5);
		jScrollPane2.setViewportView(userInfoTextArea);

		jPanel3.add(jScrollPane2, BorderLayout.CENTER);

		jPanel1.add(jPanel3);

		jPanel4.setBorder(BorderFactory
				.createLineBorder(new Color(0, 0, 0)));
		jPanel4.setPreferredSize(new Dimension(100, 20));
		jPanel4.setLayout(new BorderLayout());

		jScrollPane1.setMaximumSize(new Dimension(32767, 30));
		jScrollPane1.setPreferredSize(new Dimension(241, 30));

		userInfoTable.setModel(new DefaultTableModel(
				new Object[][] {

				}, new String[] { "�ǳ�", "����", "����ʱ��" }) {
			boolean[] canEdit = new boolean[] { false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		userInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jScrollPane1.setViewportView(userInfoTable);

		jPanel4.add(jScrollPane1, BorderLayout.CENTER);

		jPanel1.add(jPanel4);

		jPanel2.setBorder(BorderFactory
				.createLineBorder(new Color(0, 0, 0)));
		jPanel2.setPreferredSize(new Dimension(100, 300));
		jPanel2.setLayout(new BorderLayout());

		jPanel5.setLayout(new BoxLayout(jPanel5,
				BoxLayout.LINE_AXIS));

		chatTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				chatTextFieldKeyPressed(evt);
			}
		});
		jPanel5.add(chatTextField);

		sendButton.setText("����");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				sendButtonActionPerformed(evt);
			}
		});
		jPanel5.add(sendButton);

		jPanel2.add(jPanel5, BorderLayout.PAGE_END);

		chatArea.setColumns(20);
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setTabSize(4);
		jScrollPane3.setViewportView(chatArea);

		jPanel2.add(jScrollPane3, BorderLayout.CENTER);

		jPanel1.add(jPanel2);

		getContentPane().add(jPanel1, BorderLayout.EAST);

		Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 1000) / 2, (screenSize.height - 700) / 2,
				1000, 700);
	}

	/**
	 * ���촰��ķ��Ͱ�ť�¼�������
	 * 
	 * @param evt
	 *            - �¼�����
	 */
	private void sendButtonActionPerformed(ActionEvent evt) {
		String message = (String) chatTextField.getText(); // ��ȡ�ı���Ϣ
		if (message == null || message.isEmpty()) {
			return;
		}
		chatTextField.setText(""); // ����ı�������
		appendMessage(user.getName() + "��" + message); // �����͵���Ϣ��ӵ������¼
		send(message); // ������Ϣ
	}

	private void chatTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyChar() == '\n') {
			sendButton.doClick();
		}
	}

	/**
	 * ���������Ϣ�ķ���
	 * 
	 * @param message
	 *            - ������Ϣ�ı�
	 */
	protected void appendMessage(final String message) {
		Runnable runnable = new Runnable() { // �����̶߳���
			@Override
			public void run() {
				chatArea.append("\n" + message); // �������ı��������׷�ӻ����ı�
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run(); // ���¼������߳���ִ�и��̶߳���
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	/**
	 * ����Socket������
	 */
	public void startServer() {
		try {
			// ����Socket����������
			final ServerSocket chatSocketServer = new ServerSocket(9528);
			// ����������Ϣ���߳�
			new ReceiveThread(chatSocketServer, this).start();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "�������ֹ�ظ����У�ֻ��ͬʱ����һ��ʵ����",
					"����ظ����У�", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			Logger.getLogger(GameFiveChess.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public ChessPanel getChessPanel1() {
		return chessPanel1;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void runGame() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager
							.setLookAndFeel(new NimbusLookAndFeel());
					GameFiveChess frame = new GameFiveChess();
					frame.startServer();
					frame.setVisible(true);
				} catch (UnsupportedLookAndFeelException ex) {
					Logger.getLogger(GameFiveChess.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
	}
}
