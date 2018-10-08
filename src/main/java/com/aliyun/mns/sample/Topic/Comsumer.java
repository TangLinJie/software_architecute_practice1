package com.aliyun.mns.sample.Topic;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.TopicMessage;

public class Comsumer {
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
}
