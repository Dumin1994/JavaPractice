/**
 * 
 */
package calculator;

import java.awt.Dialog;
import java.math.BigInteger;

import javax.swing.JOptionPane;

/**
 * @author ����
 *
 */
class MathCal {
    //����
	public static double daoShu(double num) {
		return 1.0 / num;
	}
    //�׳�
	public static BigInteger jieCheng(int num) {
		BigInteger bigInteger = BigInteger.ONE;
		if (num == 0) {
			return bigInteger = BigInteger.ZERO;
		} else {
			for (long i = 1; i <= num; i++) {
				bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
			}
			return bigInteger;
		}
		
	}
    //sin
	public static double sin(double num) {
		return Math.sin(num);
	}
    //cos
	public static double cos(double num) {
		return Math.cos(num);
	}
    //tan
	public static double tan(double num) {
		return Math.tan(num);
	}
	//log����
	public static double log(double num) {
		return Math.log(num);
	}
	//����
	public static double sqrt(double num) {
		return Math.sqrt(num);
	}
	//n�η�
	public static double pow (double num1,double num2) {
		return Math.pow(num1, num2);
	}
	//�ٷ���
	public static double percent(double num)
	{
		return num/100;
	}
	//����Ƕ�
	public static double angle(double num)
	{
		return num / 180 * Math.PI;
	}
}
