package calculator;//����

import javax.swing.JFrame;

public class Calucator{

	public static void main(String[] args) {
		CalFrame calFrame=new CalFrame();
		calFrame.pack();
		calFrame.setVisible(true);
		calFrame.setResizable(false);// ���ô����Ƿ�ɸı��С
		calFrame.setLocation(300, 200);
		calFrame.setSize(600,300);
		calFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
