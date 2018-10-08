package com.aliyun.mns.sample.Topic;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.SubscriptionMeta;

public class CreateNewComsumer {

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
