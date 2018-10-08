package cn.edu.cug.tanglinjie;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.model.TopicMessage;

public class MNSUtils {
	public String costMessage(String[] queues,int size)
	{
		String accessKeyId = ServiceSettings.getMNSAccessKeyId();
	    String accessKeySecret = ServiceSettings.getMNSAccessKeySecret();
	    String endpoint = ServiceSettings.getMNSAccountEndpoint();
	    CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, endpoint);
	    MNSClient client = account.getMNSClient();
	    StringBuffer result=new StringBuffer();
	    
	    for(int i=0;i<size;i++)
	    {
	    	result.append("Comsumer Name "+queues[i]+"     recerive:   \n");
	    	CloudQueue queueForConsumer = client.getQueueRef(queues[i]);
	    	Message m=queueForConsumer.popMessage(30);
	    	if(m==null)
	    		continue;
	    	result.append("The Enqueue Time:  "+m.getEnqueueTime()+"\n");
	    	result.append("content:  ");
	    	result.append(m.getMessageBodyAsRawString());
	    	
	    	result.append('\n');
	    	result.append("\n");
	    }
	    client.close();
	    return result.toString();
	}
	public void produce(String s)
	{
		CloudAccount account = new CloudAccount(
	            ServiceSettings.getMNSAccessKeyId(),
	            ServiceSettings.getMNSAccessKeySecret(),
	            ServiceSettings.getMNSAccountEndpoint());
	        MNSClient client = account.getMNSClient(); //this client need only initialize once

	        try
	        {   
	          	//利用topic发送消息
	            CloudTopic topic=client.getTopicRef("mytopic");
	            TopicMessage tMessage = new RawTopicMessage(); 
	            tMessage.setMessageBody(s);
	            topic.publishMessage(tMessage);
	        }
	        catch(Exception e)
	        {
	        	
	        }
	        client.close();
	}
	public int create(String queueName)
	{
		CloudAccount account = new CloudAccount(
	            ServiceSettings.getMNSAccessKeyId(),
	            ServiceSettings.getMNSAccessKeySecret(),
	            ServiceSettings.getMNSAccountEndpoint());
	        MNSClient client = account.getMNSClient(); //this client need only initialize once

	        try
	        {   //Create Queue
	            QueueMeta qMeta = new QueueMeta();
	            qMeta.setQueueName(queueName);
	            qMeta.setPollingWaitSeconds(30);//use long polling when queue is empty.
	            CloudQueue cQueue = client.createQueue(qMeta);
	            
	            CloudTopic cTopic=client.getTopicRef("mytopic");
	         // 使用队列作为endpoint进行订阅
	            String queueEndpoint = cTopic.generateQueueEndpoint(queueName);
	            SubscriptionMeta subMeta = new SubscriptionMeta();
	            subMeta.setSubscriptionName(queueName);
	            subMeta.setNotifyStrategy(SubscriptionMeta.NotifyStrategy.EXPONENTIAL_DECAY_RETRY);
	            subMeta.setNotifyContentFormat(SubscriptionMeta.NotifyContentFormat.SIMPLIFIED);
	            subMeta.setEndpoint(queueEndpoint);
	            String subUrl = cTopic.subscribe(subMeta);
	        }
	        catch(Exception e)
	        {
	        	return 1;
	        }
	        client.close();
	        return 0;
	}
}
