
package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Winston Smith
 * 	Ӣ�ۣ�������
 */
public class HeroCaiXuKun extends Hero{




	public HeroCaiXuKun() {
		super();
		name="������";
		// ��ȡ��������ʼͼƬ
		initImgLeft=ImageUtil.getImg("/img/����������1.png");
		initImgRight=ImageUtil.getImg("/img/����������1.png");
		bg = initImgRight;
		// �����������ͼƬ
		beHurtImgLeft=ImageUtil.getImg("/img/����������L.png");
		beHurtImgRight=ImageUtil.getImg("/img/����������R.png");
		// ���������˵�ͼƬ
		dieImg=ImageUtil.getImg("/img/����������.png");
		// �ճ���ʱ������
		x = 591;
		y = 220;
		forwardImgs = new ArrayList<BufferedImage>();
		for (int i = 1; i <= 8; i++) {
			forwardImgs.add(ImageUtil.getImg("/img/����������" + i + ".png"));

		}
		backwardsImgs = new ArrayList<BufferedImage>();
		for (int i = 1; i <= 8; i++) {
			backwardsImgs.add(ImageUtil.getImg("/img/����������" + i + ".png"));

		}
		
		init(bg, x, y, forwardImgs, backwardsImgs);
//		super.setBloodBarLocation(100, 0, 30, 350);
		
		
		attackForwardImgs = new ArrayList<BufferedImage>();
		for (int i = 1; i <= 8; i++) {
			attackForwardImgs.add(ImageUtil.getImg("/img/����������" + i + ".png"));

		}
		attackBackwardsImgs = new ArrayList<BufferedImage>();
		for (int i = 1; i <= 8; i++) {
			attackBackwardsImgs.add(ImageUtil.getImg("/img/����������" + i + "L.png"));
		}
		
		adapter = new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_LEFT:
					left=false;
					break;
				case KeyEvent.VK_RIGHT:
					right=false;
					break;
				case KeyEvent.VK_UP:
					up=false;
					break;
				case KeyEvent.VK_DOWN:
					down=false;
					break;
				case KeyEvent.VK_C:
					attack=false;
					break;
				default:
					break;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
//				moveForward();
				switch (key) {
					case KeyEvent.VK_LEFT:
						faceToRight=false;
						left=true;
						break;
					case KeyEvent.VK_RIGHT:
						faceToRight=true;
						right=true;
						break;
					case KeyEvent.VK_UP:
						up=true;
						break;
					case KeyEvent.VK_DOWN:
						down=true;
						break;
					case KeyEvent.VK_C:
						attack=true;
						break;
					default:
						break;
				}	
			}
		};
		
	}
	


}
