package com.aliyun.mns.sample.Topic;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;

public class Producer {
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
}
