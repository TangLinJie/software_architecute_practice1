package com.aliyun.mns.sample.Topic;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.aliyun.mns.client.MNSClient;

public class UI {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TopicUIFrame frame=new TopicUIFrame();

		frame.show();
		
	}

}
class TopicUIFrame extends JFrame
{
	int queueid=0;
	String[] queues=new String[10];
	final JTextField createQueue=new JTextField();
	final JTextArea senter=new JTextArea();
	final JTextArea receiver=new JTextArea();
	
	JButton sent=new JButton("发送消息");
	JButton receive=new JButton("接收消息");
	JButton create=new JButton("创建新的消费者");
	
	JPanel jpanel=new JPanel();
	
	public TopicUIFrame()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		this.setSize(700, 700);			
		this.setTitle("阿里云消息服务");					
		this.setResizable(false);			
		this.setVisible(true);
		jpanel.setLayout(new GridLayout(3,3));
		
		receiver.setEditable(false);
		JScrollPane js = new JScrollPane(receiver);

		sent.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						pushMesage(senter.getText());
					}
			
				});
		receive.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						  costMessage();
					}
				});
		create.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						addQueue(createQueue.getText());
					}
			
				});
		jpanel.add(senter);
		jpanel.add(sent);
		jpanel.add(js);
		jpanel.add(receive);
		jpanel.add(createQueue);
		jpanel.add(create);
		
		this.add(jpanel);
	}
	void addQueue(String queueName)
	{
		if(queueid>=queues.length)
		{
			String[] newqueues=new String[queueid*2];
			for(int i=0;i<queueid;i++)
			{
				newqueues[i]=queues[i];
			}
			queues=newqueues;
		}
		queues[queueid]=queueName;
		queueid++;
		CreateNewComsumer cnc=new CreateNewComsumer();
		cnc.create(queues[queueid-1]);
	}
	void costMessage()
	{
		Comsumer c=new Comsumer();
		String result=c.costMessage(queues,queueid);
		receiver.setText(result);
	}
	void pushMesage(String s)
	{
		Producer p=new Producer();
		p.produce(s);
	}

}

