package cn.edu.cug.tanglinjie;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.aliyun.mns.client.MNSClient;

public class UI {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TopicUIFrame frame=new TopicUIFrame();

		//frame.show();
		
	}

}
class TopicUIFrame extends JFrame
{
	//成员变量声明
	int queueid=0;
	String[] queues=new String[10];
	final JTextArea createQueue=new JTextArea();
	final JTextArea senter=new JTextArea();
	final JTextArea receiver=new JTextArea();
	
	JButton sent=new JButton("发送消息");
	JButton receive=new JButton("接收消息");
	JButton create=new JButton("创建新的消费者");
	
	MNSUtils mnsutils=new MNSUtils();
	
	
	//JPanel jpanel=new JPanel();
	
	public TopicUIFrame()
	{	
		//界面一些基本信息
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		this.setSize(500, 500);			
		this.setTitle("阿里云消息服务");					
		this.setResizable(false);	
		//监听器注册
		listenerInitial();
		//添加控件
		initial();
	}
	void initial()
	{
		GridBagLayout gridBagLayout=new GridBagLayout();
		this.setLayout(gridBagLayout);
		receiver.setEditable(false);
		JScrollPane js = new JScrollPane(receiver);
		
		//创建GridBagConstraints
		GridBagConstraints gridBagConstraints=new GridBagConstraints();
		gridBagConstraints.fill=GridBagConstraints.BOTH;
		//设置senter的位置参数
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=0;
		gridBagConstraints.gridwidth=4;
		gridBagConstraints.gridheight=4;
		senter.setPreferredSize(new Dimension(200,100));
		gridBagLayout.setConstraints(senter, gridBagConstraints);
		//设置sent的位置参数
		JLabel label1=new JLabel();
		gridBagConstraints.gridx=4;
		gridBagConstraints.gridy=0;
		gridBagConstraints.gridheight=3;
		gridBagConstraints.gridwidth=4;
		label1.setPreferredSize(new Dimension(100,75));
		gridBagLayout.setConstraints(label1, gridBagConstraints);
		gridBagConstraints.gridx=4;
		gridBagConstraints.gridy=3;
		gridBagConstraints.gridheight=1;
		gridBagConstraints.gridwidth=4;
		sent.setPreferredSize(new Dimension(100,25));
		gridBagLayout.setConstraints(sent, gridBagConstraints);
		//设置receiver位置参数
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=4;
		gridBagConstraints.gridwidth=4;
		gridBagConstraints.gridheight=4;
		js.setPreferredSize(new Dimension(200,100));
		gridBagLayout.setConstraints(js, gridBagConstraints);
		//设置receive位置参数
		JLabel label2=new JLabel();
		gridBagConstraints.gridx=4;
		gridBagConstraints.gridy=4;
		gridBagConstraints.gridwidth=4;
		gridBagConstraints.gridheight=3;
		label2.setPreferredSize(new Dimension(100,75));
		gridBagLayout.setConstraints(label2, gridBagConstraints);
		gridBagConstraints.gridx=4;
		gridBagConstraints.gridy=7;
		gridBagConstraints.gridheight=1;
		gridBagConstraints.gridwidth=4;
		receive.setPreferredSize(new Dimension(100,25));
		gridBagLayout.setConstraints(receive, gridBagConstraints);
		//设置createQueue位置参数
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=8;
		gridBagConstraints.gridheight=1;
		gridBagConstraints.gridwidth=4;
		createQueue.setPreferredSize(new Dimension(200,25));
		gridBagLayout.setConstraints(createQueue, gridBagConstraints);
		//设置create位置参数
		gridBagConstraints.gridx=4;
		gridBagConstraints.gridy=8;
		gridBagConstraints.gridheight=1;
		gridBagConstraints.gridwidth=4;
		create.setPreferredSize(new Dimension(100,25));
		gridBagLayout.setConstraints(create, gridBagConstraints);
		
		this.add(senter);
		this.add(sent);
		this.add(js);
		this.add(receive);
		this.add(createQueue);
		this.add(create);
		this.add(label1);
		this.add(label2);
		this.setVisible(true);
		
	}
	void listenerInitial()
	{
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
		mnsutils.create(queues[queueid-1]);
	}
	void costMessage()
	{
		String result=mnsutils.costMessage(queues,queueid);
		receiver.setText(result);
	}
	void pushMesage(String s)
	{
		mnsutils.produce(s);
	}

}

