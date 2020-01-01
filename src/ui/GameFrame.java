
package ui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * 
 * @author Winston Smith
 * 
 * 	��˵�� ��Ϸ����
 * 
 */
// �̳д�����
public class GameFrame extends JFrame {
	// ������
	public GameFrame() {
		// ���ô���ߴ�
		setSize(1067, 600);
		// ���þ�����ʾ
		setLocationRelativeTo(null);
		// ���ùرմ���ʱ�ر���Ϸ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ñ���
		setTitle("��������ս���ෲ");

		// ����Logo����
		try {
			setIconImage(ImageIO.read(GameFrame.class.getResource("")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		// �Ѵ��崫��ȥ����ȡ���̼���
		GamePanel gPanel = new GamePanel(gameFrame);
		// ��ʼ��Ϸִ�д�������
		gPanel.hero_CaiXuKun.action();
		gPanel.hero_WuYiFan.action();
		// ���һ������
		gameFrame.add(gPanel);
		// ��ʾ����
		gameFrame.setVisible(true);// ��ʾ���� false���ر���

	}
}
