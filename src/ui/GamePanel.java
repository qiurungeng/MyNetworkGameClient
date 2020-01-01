
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * 
 * @author Winston Smith
 *	类说明 游戏画板类
 * 
 */

public class GamePanel extends JPanel {
	BufferedImage bg;
	HeroCaiXuKun hero_CaiXuKun = new HeroCaiXuKun();
	HeroWuYiFan hero_WuYiFan = new HeroWuYiFan();
	// 窗体
	GameFrame fr;

	// 构造器
	public GamePanel(GameFrame gameFrame) {
		setLayout(null);
		this.fr = gameFrame;
		// 声明一个用于放背景图片的窗体
		BufferedImage img;
		// 读取图片
		bg = ImageUtil.getImg("/img/bg4.jpg");
		keyListener();
		hero_CaiXuKun.panel=this;
		hero_WuYiFan.panel=this;
		hero_CaiXuKun.setOpponent(hero_WuYiFan);
		hero_WuYiFan.setOpponent(hero_CaiXuKun);
		

	}

	// 画图方法 paint虚拟机自动调用
	@Override
	public void paint(Graphics g) {
//		System.out.println("aaaa");
		super.paint(g);
		g.drawImage(bg, 0, 0, null);
		//血
		g.setColor(Color.red);
		g.fillRect(100, 0, (int)(((double)hero_WuYiFan.blood/100)*350), 30);
		g.fillRect(617+(int)(((double)(100-hero_CaiXuKun.blood)/100)*350), 0, (int)(((double)hero_CaiXuKun.blood/100)*350), 30);
		//蓝
		g.setColor(Color.blue);
		g.fillRect(100, 32, (int)(((double)hero_WuYiFan.magic/100)*350), 15);
		g.fillRect(617+(int)(((double)(100-hero_CaiXuKun.magic)/100)*350), 32, (int)(((double)hero_CaiXuKun.magic/100)*350), 15);

		//顶部角色头像
		g.drawImage(ImageUtil.getImg("/img/Cai_head.jpg"), 970, 2, 90, 45, null);
		g.drawImage(ImageUtil.getImg("/img/Cai_head.jpg"), 3, 2, 90, 45, null);
		g.setColor(Color.yellow);
		g.setFont(new Font("宋体", Font.BOLD, 50));
		g.drawString("VS", 505, 48);

		// 靠近屏幕一方不被遮挡
		if (hero_CaiXuKun.y>hero_WuYiFan.y) {			
			// 画出吴亦凡
			g.drawImage(hero_WuYiFan.bg, hero_WuYiFan.x, hero_WuYiFan.y, hero_WuYiFan.w, hero_WuYiFan.h, null);
			// 画出蔡徐坤
			g.drawImage(hero_CaiXuKun.bg, hero_CaiXuKun.x, hero_CaiXuKun.y, hero_CaiXuKun.w, hero_CaiXuKun.h, null);

		}else {
			// 画出蔡徐坤
			g.drawImage(hero_CaiXuKun.bg, hero_CaiXuKun.x, hero_CaiXuKun.y, hero_CaiXuKun.w, hero_CaiXuKun.h, null);
			// 画出吴亦凡
			g.drawImage(hero_WuYiFan.bg, hero_WuYiFan.x, hero_WuYiFan.y, hero_WuYiFan.w, hero_WuYiFan.h, null);
		}		
		
		g.setColor(Color.yellow);
		g.setFont(new Font("宋体", Font.BOLD, 100));
		if (hero_CaiXuKun.isDead()||hero_WuYiFan.isDead()) {
			if (hero_CaiXuKun.isDead()&&!hero_WuYiFan.isDead()) {
				g.drawString("吴亦凡KO了蔡徐坤", 100, 200);
			}else if (hero_WuYiFan.isDead()&&!hero_CaiXuKun.isDead()) {
				g.drawString("蔡徐坤KO了吴亦凡", 100, 200);
			}else if (hero_CaiXuKun.isDead()&&hero_WuYiFan.isDead()) {
				g.drawString("蔡徐坤吴亦凡同归于尽", 100, 200);
			}
		}
	}
	



	// 监听各英雄键盘
	public void keyListener() {
		
		hero_WuYiFan.panel=this;
		hero_CaiXuKun.panel=this;
		
		// 2.将键盘适配器加入到监听器中 键盘监听器必须加在窗体上面 问题：面板里控制移动
		
		fr.addKeyListener(hero_CaiXuKun.adapter);
		fr.addKeyListener(hero_WuYiFan.adapter);
	}

}
