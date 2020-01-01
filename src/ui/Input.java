package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Input implements KeyListener
{
	private static HashMap<Integer, Boolean> keys;
	public final static int KEY_COUNTS = 300;//存放的按键数量
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
	public void keyPressed(KeyEvent key)//有按键按下时
	{
		keys.put(key.getKeyCode(), true);//设置对应按键状态为true
	}

	@Override
	public void keyReleased(KeyEvent key)//有按键松开时
	{
		keys.put(key.getKeyCode(), false);//设置对应按键状态为false
	}

	@Override
	public void keyTyped(KeyEvent key)
	{
	}
	
	public static boolean getKeyDown(int keyCode)
	{
		return keys.get(keyCode);//返回对应按键的状态
	}

}
