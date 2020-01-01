package ui;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hero {
	
	// Ӣ�۵�ͼƬ
	BufferedImage bg;
	// x����
	int x;
	// y����
	int y;
	// ��
	int h;
	// ��
	int w;
	// ��ɫ����ķ���,����Ϊfalse,����Ϊtrue
	boolean faceToRight;
	
	// ǰ�������˵�ͼƬ����
	List<BufferedImage> forwardImgs,backwardsImgs;
	// ��ǰ��󹥻���ͼƬ����
	List<BufferedImage>  attackForwardImgs, attackBackwardsImgs;
	// ��ʼ��ͼƬ(�泯��)
	BufferedImage initImgLeft;
	// ��ʼ��ͼƬ(�泯�ҷ�)
		BufferedImage initImgRight;
	// ����ͼƬ
	BufferedImage beHurtImgLeft;
	BufferedImage beHurtImgRight;
	// ����ͼƬ
	BufferedImage dieImg;
	// ��ǰ���������ͼƬ��������
	private int index;
	// ��ǰ����󹥻���ͼƬ��������
	int attack_index = 0;
	
	// ���ڻ��洰��
	GamePanel panel;
	// ����
	Hero opponent;
	// ����������״̬:�ϡ��¡����ҡ�����������
	boolean up,down,right,left,attack,behurt=false;
	// �߳�ʱ��Ƭ��С
	int time_slice=100;
	int step_length=10;
	
	//Ѫ
	int blood;
	//��
	int magic;

	String name;
	private ExecutorService exec=Executors.newFixedThreadPool(1);
	private boolean doing_attack;

	// ��Ӣ�۵ļ���������
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
					 * DEBUG:�����õİ���
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
	
	// ��ʼͼƬ(�����κζ���ʱ��ͼƬ)
	public void initImg() {
		if (faceToRight) {
			bg=initImgRight;
		}else {
			bg=initImgLeft;
		}
	}
	
	// �ƶ�
	public void move() {
		if (faceToRight) {
			moveForward();
		}else {
			moveBackwards();
		}
	}
	
	// ��ǰ�ƶ�
	private void moveForward() {
		if (index == forwardImgs.size()) {
			index = 0;
		}
		bg = forwardImgs.get(index);
		index++;
	}
	
	// ����ƶ�
	private void moveBackwards() {
		if (index == backwardsImgs.size()) {
			index = 0;
		}
		bg = backwardsImgs.get(index);
		index++;
	}
	
	// ���ö���
	public void setOpponent(Hero hero) {
		opponent=hero;
	}
	
	// ��������
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
	
	// �������ֱ
	protected void beHurt() {
		exec.execute(new BeHurt());
	}
	
	// �ж��Ƿ��ڹ�����Χ��
	protected boolean isInAttackArea(Hero opponent) {
		//��  ����  �ڱ�Ӣ�۳�����  ��  �Թ�������(��Ϊ10)Ϊ�뾶�İ�Բ  ��
		if ((Math.sqrt((x-opponent.x)*(x-opponent.x)+(y-opponent.y)*(y-opponent.y))<100)&&
				(((!faceToRight)&&(x>opponent.x))||(faceToRight&&x<opponent.x))) {
			System.out.println(Math.sqrt((x-opponent.x)*(x-opponent.x)+(y-opponent.y)*(y-opponent.y)));
			return true;
		}
		System.out.println(Math.sqrt((x-opponent.x)*(x-opponent.x)+(y-opponent.y)*(y-opponent.y)));
		return false;
	}
	
	// ����
	private void magicRecovery() {
		if (magic<100) {
			this.magic+=1;
		}
	}
	
	// �ж��������
	boolean isDead() {
		return blood<=0;
	}
	
	/**
	 * Ӣ�� ������ �̣߳�ÿ��ʱ��Ƭ�ж�״̬ �ػ�һ��
	 */
	public void action() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					
					// �߳�����
					try {
						Thread.sleep(200);
						panel.repaint();// ˢ��
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					//������ʱǿ��Continueѭ����ֻ���ֱ���ͼƬ�����ߣ�������Ӧ�κζ���
					if (behurt) {
						bg=faceToRight?beHurtImgRight:beHurtImgLeft;
						continue;
					}
					
					//�����й�������ʱ������������Ӧ��ʧЧ
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
					
					
					// ����ʱ������ѭ���������߳�
					if (isDead()) {
						bg=dieImg;
						break;
					}
					
					//ÿ��ʱ��Ƭ��1����
					magicRecovery();
					
				}

			}
		}.start();
	}
	
	
	//���������߳���
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
	
	//�������߳���
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
