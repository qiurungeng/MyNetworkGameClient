package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Input implements KeyListener
{
	private static HashMap<Integer, Boolean> keys;
	public final static int KEY_COUNTS = 300;//��ŵİ�������
	private HeroWuYiFan heroZy;
	
	public void init()
	{
		keys = new HashMap<Integer, Boolean>(KEY_COUNTS);
		for(int i = 0; i < KEY_COUNTS; i++)
		{
			keys.put(i, false);
		}
	}
	
	public Input(HeroWuYiFan heroZy) {
		this.heroZy=heroZy;
	}
	
	@Override
	public void keyPressed(KeyEvent key)//�а�������ʱ
	{
		keys.put(key.getKeyCode(), true);//���ö�Ӧ����״̬Ϊtrue
	}

	@Override
	public void keyReleased(KeyEvent key)//�а����ɿ�ʱ
	{
		keys.put(key.getKeyCode(), false);//���ö�Ӧ����״̬Ϊfalse
	}

	@Override
	public void keyTyped(KeyEvent key)
	{
	}
	
	public static boolean getKeyDown(int keyCode)
	{
		return keys.get(keyCode);//���ض�Ӧ������״̬
	}

}
