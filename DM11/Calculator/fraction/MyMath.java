/**
 * 
 */
package fraction;

/**
 * @author ����
 *
 */
public class MyMath {
	// �������
	public static Fraction fracAdd(int first_numerator, int first_denominator, int second_numrator,
			int second_denominator) {
		// ���´����ܹ��ڿ���̨����ʾ���
		// ��Ҫ���������Լ���ĺ���
		// ��Ҫ��������С�������ĺ���
		int a = lcm(first_denominator, second_denominator);
		int numerator, denominator;
		numerator = first_numerator * (a / first_denominator) + second_numrator * (a / second_denominator);
		denominator = a;
		int b = gcd(numerator, denominator);
		numerator = numerator / b;
		denominator = denominator / b;
		Fraction fraction=new Fraction();
		fraction.setFezi(numerator);
		fraction.setFenmu(denominator);
		return fraction;
	}

	// �������
	public static Fraction fracSub(int first_numerator, int first_denominator, int second_numrator,
			int second_denominator) {
		int a = lcm(first_denominator, second_denominator);
		int numerator, denominator;
		numerator = first_numerator * (a / first_denominator) - second_numrator * (a / second_denominator);
		denominator = a;
		int b = gcd(numerator, denominator);
		numerator = numerator / b;
		denominator = denominator / b;
		Fraction fraction=new Fraction();
		fraction.setFezi(numerator);
		fraction.setFenmu(denominator);
		return fraction;
	}

	// �������
	public static Fraction fracMul(int first_numerator, int first_denominator, int second_numrator,
			int second_denominator) {
		int numerator, denominator;
		numerator = first_numerator * second_numrator;
		denominator = first_denominator * second_denominator;
		int b = gcd(numerator, denominator);
		numerator = numerator / b;
		denominator = denominator / b;
		Fraction fraction=new Fraction();
		fraction.setFezi(numerator);
		fraction.setFenmu(denominator);
		return fraction;
	}

	// �������
	public static Fraction fractDiv(int first_numerator, int first_denominator, int second_numrator,
			int second_denominator) {
		int numerator, denominator;
		numerator = first_numerator * second_denominator;
		denominator = first_denominator * second_numrator;
		int b = gcd(numerator, denominator);
		numerator = numerator / b;
		denominator = denominator / b;
		Fraction fraction=new Fraction();
		fraction.setFezi(numerator);
		fraction.setFenmu(denominator);
		return fraction;
	}

	// ���Լ��
	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	// ��С������
	public static int lcm(int m, int n) {
		int b = gcd(m, n) * (m / gcd(m, n)) * (n / gcd(m, n));
		return b;
	}
}
