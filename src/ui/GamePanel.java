
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * 
 * @author Winston Smith
 *	��˵�� ��Ϸ������
 * 
 */

public class GamePanel extends JPanel {
	BufferedImage bg;
	HeroCaiXuKun hero_CaiXuKun = new HeroCaiXuKun();
	HeroWuYiFan hero_WuYiFan = new HeroWuYiFan();
	// ����
	GameFrame fr;

	// ������
	public GamePanel(GameFrame gameFrame) {
		setLayout(null);
		this.fr = gameFrame;
		// ����һ�����ڷű���ͼƬ�Ĵ���
		BufferedImage img;
		// ��ȡͼƬ
		bg = ImageUtil.getImg("/img/bg4.jpg");
		keyListener();
		hero_CaiXuKun.panel=this;
		hero_WuYiFan.panel=this;
		hero_CaiXuKun.setOpponent(hero_WuYiFan);
		hero_WuYiFan.setOpponent(hero_CaiXuKun);
		

	}

	// ��ͼ���� paint������Զ�����
	@Override
	public void paint(Graphics g) {
//		System.out.println("aaaa");
		super.paint(g);
		g.drawImage(bg, 0, 0, null);
		//Ѫ
		g.setColor(Color.red);
		g.fillRect(100, 0, (int)(((double)hero_WuYiFan.blood/100)*350), 30);
		g.fillRect(617+(int)(((double)(100-hero_CaiXuKun.blood)/100)*350), 0, (int)(((double)hero_CaiXuKun.blood/100)*350), 30);
		//��
		g.setColor(Color.blue);
		g.fillRect(100, 32, (int)(((double)hero_WuYiFan.magic/100)*350), 15);
		g.fillRect(617+(int)(((double)(100-hero_CaiXuKun.magic)/100)*350), 32, (int)(((double)hero_CaiXuKun.magic/100)*350), 15);

		//������ɫͷ��
		g.drawImage(ImageUtil.getImg("/img/Cai_head.jpg"), 970, 2, 90, 45, null);
		g.drawImage(ImageUtil.getImg("/img/Cai_head.jpg"), 3, 2, 90, 45, null);
		g.setColor(Color.yellow);
		g.setFont(new Font("����", Font.BOLD, 50));
		g.drawString("VS", 505, 48);

		// ������Ļһ�������ڵ�
		if (hero_CaiXuKun.y>hero_WuYiFan.y) {			
			// �������ෲ
			g.drawImage(hero_WuYiFan.bg, hero_WuYiFan.x, hero_WuYiFan.y, hero_WuYiFan.w, hero_WuYiFan.h, null);
			// ����������
			g.drawImage(hero_CaiXuKun.bg, hero_CaiXuKun.x, hero_CaiXuKun.y, hero_CaiXuKun.w, hero_CaiXuKun.h, null);

		}else {
			// ����������
			g.drawImage(hero_CaiXuKun.bg, hero_CaiXuKun.x, hero_CaiXuKun.y, hero_CaiXuKun.w, hero_CaiXuKun.h, null);
			// �������ෲ
			g.drawImage(hero_WuYiFan.bg, hero_WuYiFan.x, hero_WuYiFan.y, hero_WuYiFan.w, hero_WuYiFan.h, null);
		}		
		
		g.setColor(Color.yellow);
		g.setFont(new Font("����", Font.BOLD, 100));
		if (hero_CaiXuKun.isDead()||hero_WuYiFan.isDead()) {
			if (hero_CaiXuKun.isDead()&&!hero_WuYiFan.isDead()) {
				g.drawString("���ෲKO�˲�����", 100, 200);
			}else if (hero_WuYiFan.isDead()&&!hero_CaiXuKun.isDead()) {
				g.drawString("������KO�����ෲ", 100, 200);
			}else if (hero_CaiXuKun.isDead()&&hero_WuYiFan.isDead()) {
				g.drawString("���������ෲͬ���ھ�", 100, 200);
			}
		}
	}
	



	// ������Ӣ�ۼ���
	public void keyListener() {
		
		hero_WuYiFan.panel=this;
		hero_CaiXuKun.panel=this;
		
		// 2.���������������뵽�������� ���̼�����������ڴ������� ���⣺���������ƶ�
		
		fr.addKeyListener(hero_CaiXuKun.adapter);
		fr.addKeyListener(hero_WuYiFan.adapter);
	}

}
