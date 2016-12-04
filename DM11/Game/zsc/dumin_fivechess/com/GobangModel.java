package zsc.dumin_fivechess.com;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

/**
 * �����������ӵ�����ģ��JavaBean
 * 
 * @author ����
 */
public class GobangModel extends Object implements Serializable {
	private PropertyChangeSupport propertySupport; // �������Թ�����
	private static GobangModel model; // ��������ı���
	private byte[][] chessmanArray = new byte[15][15]; // ������������
	public static final String PROP_CHESSMANARRAY = "chessmanArray"; // ������������

	/**
	 * ��ȡ����ʵ���ķ���
	 * 
	 * @return
	 */
	public static GobangModel getInstance() {
		if (model == null) {
			model = new GobangModel();
		}
		return model;
	}

	/**
	 * ����ģ�͵Ĺ��췽��
	 */
	public GobangModel() {
		propertySupport = new PropertyChangeSupport(this);// ��ʼ�����Թ�����
		model = this;
	}

	/**
	 * ��ȡ���̵���������ķ���
	 * 
	 * @return - �������ӵ�����
	 */
	public byte[][] getChessmanArray() {
		return chessmanArray; // ������������
	}

	/**
	 * ������������ķ���
	 * 
	 * @param chessmanArray
	 *            - һ�������������ӵĶ�ά����
	 */
	public void setChessmanArray(byte[][] chessmanArray) {
		this.chessmanArray = chessmanArray;
		propertySupport.firePropertyChange(PROP_CHESSMANARRAY, null,
				chessmanArray); // ����������ע���������İ����Ը���
	}

	/**
	 * ������������ķ�����������������¼�
	 * 
	 * @param chessmanArray
	 */
	public synchronized void updateChessmanArray(byte[][] chessmanArray) {
		this.chessmanArray = chessmanArray;
	}

	/**
	 * ����¼��������ķ���
	 * 
	 * @param listener
	 *            - �¼�������
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener); // ����¼�������
	}

	/**
	 * �Ƴ��¼��������ķ���
	 * 
	 * @param listener
	 *            - �¼�������
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener); // �Ƴ��¼�������
	}

	/**
	 * ��ȡ��������������Ŀ���
	 * 
	 * @return - ��������
	 */
	byte[][] getChessmanArrayCopy() {
		byte[][] newArray = new byte[15][15]; // ����һ����ά����
		for (int i = 0; i < newArray.length; i++) {
			// ��������
			newArray[i] = Arrays.copyOf(chessmanArray[i], newArray[i].length);
		}
		return newArray;
	}
}
