/**
 * 
 */
package fraction;

import javax.swing.JFrame;

/**
 * @author ����
 *
 */
public class FractionMain {

	/**
	 * @param args
	 */
	public static void run() {
		// TODO Auto-generated method stub
        FractionFrame FractionFrame=new FractionFrame();
        FractionFrame.pack();
        FractionFrame.setVisible(true);
        FractionFrame.setResizable(false);// ���ô����Ƿ�ɸı��С
        FractionFrame.setLocation(300, 200);
        FractionFrame.setSize(600,350);
	}
}
