package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class JProgressBarDemo extends JFrame {
	private static final long serialVersionUID = 1L;

	//static JProgressBarDemo frame;
    public JProgressBarDemo() {
        setTitle("ʹ�ý�����");
        JLabel label=new JLabel("��ӭʹ�������������ܣ�");
        //����һ��������
        JProgressBar progressBar=new JProgressBar();
        JButton button=new JButton("���");
        button.setEnabled(false);
        Container container=getContentPane();
        container.setLayout(new GridLayout(3,1));
        JPanel panel1=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1.add(label);    //��ӱ�ǩ
        panel2.add(progressBar);    //��ӽ�����
        panel3.add(button);    //��Ӱ�ť
        container.add(panel1);
        container.add(panel2);
        container.add(panel3);
        progressBar.setStringPainted(true);
        //�������Ҫ��������ʾ������������...������ע�ʹ���
        progressBar.setString("����������...");
        //�����Ҫʹ�ò�ȷ��ģʽ����ʹ�ô���
        //progressBar.setIndeterminate(true);
        //����һ���̴߳������
        new Progress(progressBar, button).start();
        //��������ɡ���ť��������
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                System.exit(0);
            }
        });
    }
    private class Progress extends Thread
    {
        JProgressBar progressBar;
        JButton button;
        //�������ϵ�����
        int[] progressValues={6,18,27,39,51,66,81,100};
        Progress(JProgressBar progressBar,JButton button)
        {
            this.progressBar=progressBar;
            this.button=button;
        }
        public void run()
        {
            for(int i=0;i<progressValues.length;i++)
            {
                try
                {
                    Thread.sleep(3000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                //���ý�������ֵ
                progressBar.setValue(progressValues[i]);
            }
            progressBar.setIndeterminate(false);
            progressBar.setString("������ɣ�");
            button.setEnabled(true);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JProgressBarDemo frame=new JProgressBarDemo();
        //frame.setBounds(300,200,300,150);    //���������Ĵ�С
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

}
