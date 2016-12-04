package zsc.dumin_fivechess.com;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * ��¼���
 * 
 * @author ����
 */
public class LoginPanel extends javax.swing.JPanel {

	private Socket socket;
	private UserBean user;
	protected boolean linked;

	/**
	 * ���췽��
	 */
	public LoginPanel() {
		initComponents(); // ���ó�ʼ������ķ���
	}

	public boolean isLinked() {
		return linked;
	}

	public void setLinked(boolean linked) {
		this.linked = linked;
	}

	void setLinkIp(String ip) {
		ipTextField.setText(ip);
		ipTextField.setEditable(false);
		nameTextField.requestFocus();
	}

	/**
	 * ��ʼ����¼����ķ���
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jLabel1 = new javax.swing.JLabel();
		nameTextField = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		ipTextField = new javax.swing.JTextField();
		loginButton = new javax.swing.JButton();
		closeButton = new javax.swing.JButton();

		setForeground(java.awt.Color.gray);
		setOpaque(false);
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				formMouseClicked(evt);
			}
		});
		setLayout(new java.awt.GridBagLayout());

		jLabel1.setFont(new java.awt.Font("����_gbk", 2, 24));
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("��  �ƣ�");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		add(jLabel1, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipady = -5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		add(nameTextField, gridBagConstraints);

		jLabel2.setFont(new java.awt.Font("����_gbk", 2, 24));
		jLabel2.setForeground(java.awt.Color.white);
		jLabel2.setText("�Է� IP��");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(jLabel2, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.ipady = -5;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
		add(ipTextField, gridBagConstraints);

		loginButton.setText("��¼");
		loginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 40);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		add(loginButton, gridBagConstraints);

		closeButton.setText("�ر�");
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 55);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		add(closeButton, gridBagConstraints);
	}

	private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	private void formMouseClicked(java.awt.event.MouseEvent evt) {
		JOptionPane.showMessageDialog(this, "��û��¼�أ����ĵ㣿");
	}

	/**
	 * ��¼��ť���¼�������
	 * 
	 * @param evt
	 *            - ��ť���¼�����
	 */
	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			// ��ȡ�������ʵ������
			GameFiveChess mainFrame = (GameFiveChess) getParent().getParent();
			String name = nameTextField.getText(); // ��ȡ�û��ǳ�
			if (name.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "�������ǳ�");
				return;
			}
			String ipText = ipTextField.getText(); // ��ȡ�Լ�IP��ַ
			if(ipText==null||ipText.isEmpty()){
				JOptionPane.showMessageDialog(this, "������Լ�IP��ַ");
				return;
			}
			ipTextField.setEditable(true);
			InetAddress ip = InetAddress.getByName(ipText);
			if(ip.equals(InetAddress.getLocalHost())){
				JOptionPane.showMessageDialog(this, "���������Լ���IP��ַ");
				return;
			}
			socket = new Socket(ip, 9528); // ����Socket���ӶԼ�����
			if (socket.isConnected()) { // ������ӳɹ�
				user = new UserBean(); // �����û�����
				// ��ȡ��ǰʱ�����
				Time time = new Time(System.currentTimeMillis());
				user.setName(name); // ��ʼ���û��ǳ�
				user.setHost(InetAddress.getLocalHost()); // ��ʼ���û�IP
				user.setTime(time); // ��ʼ���û���¼ʱ��
				socket.setOOBInline(true); // ���ý������ݵĽ���
				mainFrame.setSocket(socket); // �����������Socket���Ӷ���
				mainFrame.setUser(user); // ��ӱ����û��������������
				mainFrame.send(user); // ���ͱ����û����󵽶Լ�����
				setVisible(false); // ���ص�¼����
			}
		} catch (UnknownHostException ex) {
			Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE,
					null, ex);
			JOptionPane.showMessageDialog(this, "�����IP����ȷ");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "�Է������޷�����");
		}
	}

	/**
	 * �����������ķ���
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // ��ȡ2D��ͼ������
		Composite composite = g2.getComposite(); // ���ݺϳ�ģʽ
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.8f)); // ���û�ͼʹ��͸���ϳɹ���
		g2.fillRect(0, 0, getWidth(), getHeight()); // ʹ�õ�ǰ��ɫ�����οռ�
		g2.setComposite(composite); // �ָ�ԭ�кϳ�ģʽ
		super.paintComponent(g2); // ִ�г����������Ʒ���
	}

	private javax.swing.JButton closeButton;
	private javax.swing.JTextField ipTextField;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JButton loginButton;
	private javax.swing.JTextField nameTextField;
}
