package zsc.dumin_fivechess.com;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.ImageIcon;

/**
 * �������
 * 
 * @author ����
 */
public class GobangPanel extends javax.swing.JPanel {
	// �ڰ���е�ͼ�������Ǻŵ�ͼ������Լ�����ͼƬ����
	private Image backImg;
	private Image white_chessman_img;
	private Image black_chessman_img;
	private Image rightTop_img;
	int chessWidth, chessHeight; // ���ӿ����߶�
	public final static byte WHITE_CHESSMAN = 1;
	public final static byte BLACK_CHESSMAN = -1;
	Dimension size; // �������Ĵ�С
	private boolean start = false; // ��ʼ
	private Object[] oldRec;
	Deque<byte[][]> chessQueue = new LinkedList(); // ��Ϸ�Ķ��м�¼
	private boolean turn = false; // �Ƿ��Լ�����
	private boolean towardsWin; // �Է�ʤ��
	private boolean win; // ʤ��
	private boolean draw; // ����
	private ChessPanel chessPanel;

	/**
	 * �������Ĺ��췽��
	 */
	public GobangPanel() {
		URL white_url = getClass().getResource("/res/whiteChessman.png");
		URL black_url = getClass().getResource("/res/blackChessman.png");
		URL rightTop_url = getClass().getResource("/res/rightTop.gif");
		white_chessman_img = new ImageIcon(white_url).getImage(); // ��ʼ������ͼƬ
		black_chessman_img = new ImageIcon(black_url).getImage(); // ��ʼ������ͼƬ
		// ��ʼ�������ߵ������ϵ���ͼ
		rightTop_img = new ImageIcon(rightTop_url).getImage();
		size = new Dimension(getWidth(), getHeight());
		setPreferredSize(size);
		initComponents();
	}

	/**
	 * ��¼��Ϸ����ǰ�����̼�¼
	 */
	public void oldRec() {
		oldRec = (Object[]) chessQueue.toArray();
	}

	public Object[] getOldRec() {
		return oldRec;
	}

	public boolean isTurn() {
		return turn;
	}

	protected void setTurn(boolean turn) {
		this.turn = turn;
		chessPanel.setTurn(turn);
	}

	protected boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		chessQueue.clear();
		this.start = start;
		if (chessPanel == null) {
			chessPanel = (ChessPanel) getParent();
		}
		repaint();
	}

	public boolean isTowardsWin() {
		return towardsWin;
	}

	public void setTowardsWin(boolean towardsWin) {
		this.towardsWin = towardsWin;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public Deque<byte[][]> getChessQueue() {
		return chessQueue;
	}

	/**
	 * ��д�����paint�����������Լ����������
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		super.paint(g); // ���ø���Ļ�ͼ����
		if (chessPanel != null) {
			chessPanel.setTurn(turn);
		}
		Composite composite = g.getComposite(); // ���ݺϳ�ģʽ
		drawPanel(g); // ���û������̵ķ���
		g.translate(4, 4);
		size = new Dimension(getWidth(), getHeight());// ��ȡ�������Ĵ�С
		chessWidth = size.width / 15; // ��ʼ�����ӿ�
		chessHeight = size.height / 15; // ��ʼ�����Ӹ�
		byte[][] chessmanArray = gobangModel1.getChessmanArrayCopy();
		for (int i = 0; i < chessmanArray.length; i++) {// ������������ģ�ͻ�������
			for (int j = 0; j < chessmanArray[i].length; j++) {
				byte chessman = chessmanArray[i][j];
				int x = i * chessWidth;
				int y = j * chessHeight;
				if (chessman != 0)
					System.out.println("chess is:" + chessman);
				if (chessman == WHITE_CHESSMAN) { // ���ư���
					g.drawImage(white_chessman_img, x, y, chessWidth,
							chessHeight, this);
				} else if (chessman == BLACK_CHESSMAN) {// ���ƺ���
					g.drawImage(black_chessman_img, x, y, chessWidth,
							chessHeight, this);
				} else if (chessman == (WHITE_CHESSMAN ^ 3)) { // ��������İ�������
					g.drawImage(white_chessman_img, x, y, chessWidth,
							chessHeight, this);
					g.drawRect(x, y, chessWidth, chessHeight);
				} else if (chessman == (BLACK_CHESSMAN ^ 3)) { // ��������ĺ�������
					g.drawImage(black_chessman_img, x, y, chessWidth,
							chessHeight, this);
					g.drawRect(x, y, chessWidth, chessHeight);
				} else if (chessman == ((byte) (WHITE_CHESSMAN ^ 8))) {// ���Ƶ���ʤ�������߰���
					g.drawImage(white_chessman_img, x, y, chessWidth,
							chessHeight, this);
					g.drawImage(rightTop_img, x, y, chessWidth, chessHeight,
							this);
				} else if (chessman == (BLACK_CHESSMAN ^ 8)) {// ���Ƶ���ʤ�������ߺ���
					g.drawImage(black_chessman_img, x, y, chessWidth,
							chessHeight, this);
					g.drawImage(rightTop_img, x, y, chessWidth, chessHeight,
							this);
				}
			}
		}
		if (!isStart()) { // �����Ϸ�����ڿ�ʼ״̬
			if (towardsWin || win || draw) { // �����Ϸ����ʤ�������״̬������������ʾ��Ϣ
				g.setComposite(AlphaComposite.SrcOver.derive(0.7f)); // ����70%
				// ͸���ĺϳɹ���
				String mess = "�Է�ʤ��"; // ������ʾ��Ϣ
				g.setColor(Color.RED); // ����ǰ��ɫΪ��ɫ
				if (win) { // ������Լ�ʤ��
					mess = "��ʤ����"; // ����ʤ����ʾ��Ϣ
					g.setColor(new Color(0x007700)); // ������ɫǰ��ɫ
				} else if (draw) { // ����Ǻ���״̬
					mess = "��սƽ��"; // ���������ʾ��Ϣ
					g.setColor(Color.YELLOW); // ���ú�����Ϣʹ�û�ɫ��ʾ
				}
				// ������ʾ�ı�������Ϊ���顢��б�塢��С72
				Font font = new Font("����", Font.ITALIC | Font.BOLD, 72);
				g.setFont(font);
				// ��ȡ������Ⱦ�����Ķ���
				FontRenderContext context = g.getFontRenderContext();
				// ������ʾ��Ϣ���ı���ռ�õ����ؿռ�
				Rectangle2D stringBounds = font.getStringBounds(mess, context);
				double fontWidth = stringBounds.getWidth(); // ��ȡ��ʾ�ı��Ŀ��
				g.drawString(mess, (int) ((getWidth() - fontWidth) / 2),
						getHeight() / 2); // ���л�����ʾ��Ϣ
				g.setComposite(composite); // �ָ�ԭ�кϳɹ���
			} else { // �����ǰ��������δ��ʼ��Ϸ��״̬
				String mess = "�ȴ���ʼ��"; // ���������ʾ��Ϣ
				Font font = new Font("����", Font.ITALIC | Font.BOLD, 48);
				g.setFont(font); // ����48����������
				FontRenderContext context = g.getFontRenderContext();
				Rectangle2D stringBounds = font.getStringBounds(mess, context);
				double fontWidth = stringBounds.getWidth(); // ��ȡ��ʾ�ı��Ŀ��
				g.drawString(mess, (int) ((getWidth() - fontWidth) / 2),
						getHeight() / 2); // ���л�����ʾ�ı�
			}
		}
	}

	/**
	 * �������̵ķ���
	 * 
	 * @param g
	 *            - ��ͼ����
	 */
	private void drawPanel(Graphics2D g) {
		Composite composite = g.getComposite(); // ���ݺϳɹ���
		Color color = g.getColor(); // ����ǰ����ɫ
		g.setComposite(AlphaComposite.SrcOver.derive(0.6f));// ����͸���ϳ�
		g.setColor(new Color(0xAABBAA)); // ����ǰ����ɫ
		g.fill3DRect(0, 0, getWidth(), getHeight(), true); // ���ư�͸���ľ���
		g.setComposite(composite); // �ָ��ϳɹ���
		g.setColor(color); // �ָ�ԭ��ǰ��ɫ
		int w = getWidth(); // ���̿��
		int h = getHeight(); // ���̸߶�
		int chessW = w / 15, chessH = h / 15; // ���ӿ�Ⱥ͸߶�
		int left = chessW / 2 + (w % 15) / 2; // ������߽�
		int right = left + chessW * 14; // �����ұ߽�
		int top = chessH / 2 + (h % 15) / 2; // �����ϱ߽�
		int bottom = top + chessH * 14; // �����±߽�
		for (int i = 0; i < 15; i++) {
			// ��ÿ������
			g.drawLine(left, top + (i * chessH), right, top + (i * chessH));
		}
		for (int i = 0; i < 15; i++) {
			// ��ÿ������
			g.drawLine(left + (i * chessW), top, left + (i * chessW), bottom);
		}
	}

	private void initComponents() {
		gobangModel1 = new GobangModel(); // ��������ģ�͵�ʵ������
		// Ϊ��������¼�������
		gobangModel1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				gobangModel1PropertyChange(evt);// �����¼�������
			}
		});

		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				formMouseClicked(evt);
			}
		});
		setOpaque(false);
		setLayout(null);
	}

	public boolean isTowardsStart() {
		ChessPanel panel = (ChessPanel) getParent();
		return panel.isTowardsStart();
	}

	private void formMouseClicked(java.awt.event.MouseEvent evt) {
		// �ж��Ƿ�ʼ��Ϸ
		if (!start || !isTowardsStart() || myColor == 0 || !turn) {
			return;
		}
		Point point = evt.getPoint();
		int xindex = point.x / chessWidth;
		int yindex = point.y / chessHeight;
		byte[][] chessmanArray = gobangModel1.getChessmanArray();
		if (chessmanArray[xindex][yindex] == 0) {
			turn = !turn;
			chessmanArray[xindex][yindex] = (byte) (myColor ^ 3);
			gobangModel1.setChessmanArray(chessmanArray);
			chessPanel.backButton.setEnabled(false);
			zhengliBoard((byte) -myColor);
			int winColor = arithmetic(myColor, xindex, yindex);
			chessPanel.send(gobangModel1.getChessmanArrayCopy());
			if (winColor != 0 && winColor == myColor) {
				chessPanel.send(ChessPanel.WIN); // ����ʤ������
				win = true;
				chessPanel.reInit();
			}
			// ���ж�ʤ��������ٷ���model�е��������飬��Ϊ���������ܴ��б�ʶ���ߵ���������
		}
	}

	/* �������̣�����ʶ��һ�������ӻָ���ͨ���� */
	public void zhengliBoard(byte color) {
		byte[][] chessmanArray = gobangModel1.getChessmanArray();
		for (int i = 0; i < chessmanArray.length; i++) {
			for (int j = 0; j < chessmanArray[i].length; j++) {
				if (chessmanArray[i][j] == (color ^ 3)) {
					chessmanArray[i][j] = color;
				}
			}
		}
		gobangModel1.updateChessmanArray(chessmanArray);
		repaint();
	}

	/**
	 * �������㷨
	 * 
	 * @param n
	 *            - ����������ɫ������
	 * @param Arow
	 *            - �б��
	 * @param Acolumn
	 *            - �б��
	 * @return ʤ��һ����������ɫ������
	 */
	public int arithmetic(int n, int Arow, int Acolumn) {
		int n3 = n ^ 3;
		byte n8 = (byte) (n ^ 8);
		byte[][] note = gobangModel1.getChessmanArrayCopy();
		int BCount = 1;
		// �������
		boolean Lbol = true;
		boolean Rbol = true;
		BCount = 1;
		for (int i = 1; i <= 5; i++) {
			if ((Acolumn + i) > 14) {// ������ӳ����������
				Rbol = false;
			}
			if ((Acolumn - i) < 0) {// ������ӳ�����С����
				Lbol = false;
			}
			if (Rbol == true) {
				if (note[Arow][Acolumn + i] == n
						|| note[Arow][Acolumn + i] == n3) {// ���������������ͬ������
					++BCount;
					note[Arow][Acolumn + i] = n8;
				} else {
					Rbol = false;
				}
			}
			if (Lbol == true) {
				if (note[Arow][Acolumn - i] == n
						|| note[Arow][Acolumn - i] == n3) {// ���������������ͬ������
					++BCount;
					note[Arow][Acolumn - i] = n8;
				} else {
					Lbol = false;
				}
			}
			if (BCount >= 5) {// ���ͬ���͵����������ڵ���5��
				note[Arow][Acolumn] = n8;
				gobangModel1.updateChessmanArray(note);
				repaint();
				return n; // ����ʤ��һ��������
			}
		}

		// �������
		note = gobangModel1.getChessmanArrayCopy();
		boolean Ubol = true;
		boolean Dbol = true;
		BCount = 1;
		for (int i = 1; i <= 5; i++) {
			if ((Arow + i) > 14) {// ����������̵��������
				Dbol = false;
			}
			if ((Arow - i) < 0) {// ����������̵���С����
				Ubol = false;
			}
			if (Dbol == true) {
				if (note[Arow + i][Acolumn] == n
						|| note[Arow + i][Acolumn] == n3) { // ���������ͬ���͵�����
					++BCount;
					note[Arow + i][Acolumn] = n8;
				} else {
					Dbol = false;
				}
			}
			if (Ubol == true) {
				if (note[Arow - i][Acolumn] == n
						|| note[Arow - i][Acolumn] == n3) { // ���������ͬ���͵�����
					++BCount;
					note[Arow - i][Acolumn] = n8;
				} else {
					Ubol = false;
				}
			}
			if (BCount >= 5) { // ���ͬ���͵����Ӵ��ڵ���5��
				note[Arow][Acolumn] = n8;
				gobangModel1.updateChessmanArray(note);
				repaint();
				return n; // ����ʤ��һ��������
			}
		}

		// ��б����
		note = gobangModel1.getChessmanArrayCopy();
		boolean LUbol = true;
		boolean RDbol = true;
		BCount = 1;
		for (int i = 1; i <= 5; i++) {
			if ((Arow - i) < 0 || (Acolumn - i < 0)) {// ������������б��
				LUbol = false;
			}
			if ((Arow + i) > 14 || (Acolumn + i > 14)) {// ������������б��
				RDbol = false;
			}
			if (LUbol == true) {
				if (note[Arow - i][Acolumn - i] == n
						|| note[Arow - i][Acolumn - i] == n3) {// �������б��������ͬ���͵�����
					++BCount;
					note[Arow - i][Acolumn - i] = n8;
				} else {
					LUbol = false;
				}
			}
			if (RDbol == true) {
				if (note[Arow + i][Acolumn + i] == n
						|| note[Arow + i][Acolumn + i] == n3) {// �������б��������ͬ���͵�����
					++BCount;
					note[Arow + i][Acolumn + i] = n8;
				} else {
					RDbol = false;
				}
			}
			if (BCount >= 5) {// ���ͬ���͵����Ӵ��ڵ���5��
				note[Arow][Acolumn] = n8;
				gobangModel1.updateChessmanArray(note);
				repaint();
				return n; // ����ʤ��һ��������
			}
		}
		// ��б����
		note = gobangModel1.getChessmanArrayCopy();
		boolean RUbol = true;
		boolean LDbol = true;
		BCount = 1;
		for (int i = 1; i <= 5; i++) {
			if ((Arow - i) < 0 || (Acolumn + i > 14)) {
				RUbol = false;
			}
			if ((Arow + i) > 14 || (Acolumn - i < 0)) {
				LDbol = false;
			}
			if (RUbol == true) {
				if (note[Arow - i][Acolumn + i] == n
						|| note[Arow - i][Acolumn + i] == n3) {// �������б��������ͬ���͵�����
					++BCount;
					note[Arow - i][Acolumn + i] = n8;
				} else {
					RUbol = false;
				}
			}
			if (LDbol == true) {
				if (note[Arow + i][Acolumn - i] == n
						|| note[Arow + i][Acolumn - i] == n3) {// �������б��������ͬ���͵�����
					++BCount;
					note[Arow + i][Acolumn - i] = n8;
				} else {
					LDbol = false;
				}
			}
			if (BCount >= 5) {// ���ͬ���͵����Ӵ��ڵ���5��
				note[Arow][Acolumn] = n8;
				gobangModel1.updateChessmanArray(note);
				repaint();
				return n;// ����ʤ��һ��������
			}
		}
		return 0;
	}

	/**
	 * ��������ģ�͵��¼�������
	 * 
	 * @param evt
	 */
	private void gobangModel1PropertyChange(java.beans.PropertyChangeEvent evt) {
		chessQueue.push(gobangModel1.getChessmanArrayCopy()); // ���µ����̲���ѹ�����
		repaint(); // �ػ����̽���
	}

	public byte getMyColor() {
		return myColor;
	}

	public void setMyColor(byte myColor) {
		this.myColor = myColor;
	}

	public byte myColor = -2;
	private GobangModel gobangModel1;
}
