package ui;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hero {
	
	// 英雄的图片
	BufferedImage bg;
	// x坐标
	int x;
	// y坐标
	int y;
	// 高
	int h;
	// 宽
	int w;
	// 角色朝向的方向,向左为false,向右为true
	boolean faceToRight;
	
	// 前进及后退的图片数组
	List<BufferedImage> forwardImgs,backwardsImgs;
	// 向前向后攻击的图片数组
	List<BufferedImage>  attackForwardImgs, attackBackwardsImgs;
	// 初始化图片(面朝左方)
	BufferedImage initImgLeft;
	// 初始化图片(面朝右方)
		BufferedImage initImgRight;
	// 被打图片
	BufferedImage beHurtImgLeft;
	BufferedImage beHurtImgRight;
	// 死亡图片
	BufferedImage dieImg;
	// 向前及向后动作的图片数组索引
	private int index;
	// 向前及向后攻击的图片数组索引
	int attack_index = 0;
	
	// 所在画面窗口
	GamePanel panel;
	// 对手
	Hero opponent;
	// 被监听按键状态:上、下、左、右、攻击、被打
	boolean up,down,right,left,attack,behurt=false;
	// 线程时间片大小
	int time_slice=100;
	int step_length=10;
	
	//血
	int blood;
	//蓝
	int magic;

	String name;
	private ExecutorService exec=Executors.newFixedThreadPool(1);
	private boolean doing_attack;

	// 该英雄的键盘适配器
	KeyAdapter adapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			char c = e.getKeyChar();
			switch (c) {
			case 'a':
				left=false;
				break;
			case 'd':
				right=false;
				break;
			case 'w':
				up=false;
				break;
			case 's':
				down=false;
				break;
			case 'j':
				attack=false;
				break;
			default:
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar();

			switch (c) {
				case 'a':
					left=true;
					faceToRight=false;
					break;
				case 'd':
					right=true;
					faceToRight=true;
					break;
				case 'w':
					up=true;
					break;
				case 's':
					down=true;
					break;
				case 'j':
					attack=true;
					break;
				case 'p':
					/**
					 * DEBUG:调试用的按键
					 */
					System.out.println("2DEBUG:");
					System.out.println(Thread.activeCount());
					break;
				default:
					break;
			}	
		}
	};
	
	public Hero() {

	}
	
	public void init(BufferedImage bg,int x,int y,List<BufferedImage> forwardImgs,List<BufferedImage> backwardsImgs) {
		this.bg=bg;
		this.x=x;
		this.y=y;
		h=bg.getHeight();
		w=bg.getWidth();
		this.forwardImgs=forwardImgs;
		this.backwardsImgs=backwardsImgs;
		index=0;
		blood=100;
		magic=100;
		
	}
	
	// 初始图片(即无任何动作时的图片)
	public void initImg() {
		if (faceToRight) {
			bg=initImgRight;
		}else {
			bg=initImgLeft;
		}
	}
	
	// 移动
	public void move() {
		if (faceToRight) {
			moveForward();
		}else {
			moveBackwards();
		}
	}
	
	// 向前移动
	private void moveForward() {
		if (index == forwardImgs.size()) {
			index = 0;
		}
		bg = forwardImgs.get(index);
		index++;
	}
	
	// 向后移动
	private void moveBackwards() {
		if (index == backwardsImgs.size()) {
			index = 0;
		}
		bg = backwardsImgs.get(index);
		index++;
	}
	
	// 设置对手
	public void setOpponent(Hero hero) {
		opponent=hero;
	}
	
	// 攻击对手
	private void attackOpponent(Hero hero) {
		if (isInAttackArea(hero)) {
			hero.blood-=5;
			hero.beHurt();
			if (faceToRight) {
				hero.x+=10;
			}else {
				hero.x -= 10;
			}
			
		}
	}
	
	// 被击打后僵直
	protected void beHurt() {
		exec.execute(new BeHurt());
	}
	
	// 判断是否在攻击范围内
	protected boolean isInAttackArea(Hero opponent) {
		//若  敌人  在本英雄朝向方向  的  以攻击距离(设为10)为半径的半圆  内
		if ((Math.sqrt((x-opponent.x)*(x-opponent.x)+(y-opponent.y)*(y-opponent.y))<100)&&
				(((!faceToRight)&&(x>opponent.x))||(faceToRight&&x<opponent.x))) {
			System.out.println(Math.sqrt((x-opponent.x)*(x-opponent.x)+(y-opponent.y)*(y-opponent.y)));
			return true;
		}
		System.out.println(Math.sqrt((x-opponent.x)*(x-opponent.x)+(y-opponent.y)*(y-opponent.y)));
		return false;
	}
	
	// 回蓝
	private void magicRecovery() {
		if (magic<100) {
			this.magic+=1;
		}
	}
	
	// 判断死亡与否
	boolean isDead() {
		return blood<=0;
	}
	
	/**
	 * 英雄 主动作 线程，每个时间片判断状态 重绘一次
	 */
	public void action() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					
					// 线程休眠
					try {
						Thread.sleep(200);
						panel.repaint();// 刷新
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					//被击打时强制Continue循环，只保持被打图片并休眠，不能响应任何动作
					if (behurt) {
						bg=faceToRight?beHurtImgRight:beHurtImgLeft;
						continue;
					}
					
					//当进行攻击动作时，其他按键都应该失效
					if (!doing_attack) {
						if (up) {
							y -= 10;
							if (y <= 0) {
								y = 0;
							}
						}
						if (down) {
							y += 10;
							if (y >= 600 - h) {
								y = 600 - h;
							}
						}
						if (left) {
							x -= 10;
							if (x <= 0) {
								x = 0;
							}
						}
						if (right) {
							x += 10;
							if (x >= 1067 - w) {
								x = 1067 - w;
							}
						}
						
						if (up||down||left||right) {
							move();
						}else if(!attack||!behurt){
							initImg();
						}
						
						if (attack) {
							if (magic>10) {
								exec.execute(new Attack());
							}
						}
					}
					
					
					// 死亡时，跳出循环，结束线程
					if (isDead()) {
						bg=dieImg;
						break;
					}
					
					//每个时间片回1点蓝
					magicRecovery();
					
				}

			}
		}.start();
	}
	
	
	//攻击动作线程类
	class Attack implements Runnable{
		List<BufferedImage> attackImgs=faceToRight?attackForwardImgs:attackBackwardsImgs;
		String name;
		@Override
		public void run() {
			doing_attack=true;
			
			magic-=1;
			for (BufferedImage bufferedImage : attackImgs) {
				bg=bufferedImage;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			attackOpponent(opponent);
			
			doing_attack=false;
		}
	}
	
	//被打动作线程类
	class BeHurt implements Runnable{
		@Override
		public void run() {
			behurt=true;
			System.out.println(name+":"+behurt);
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			behurt=false;
		}
	}

}
