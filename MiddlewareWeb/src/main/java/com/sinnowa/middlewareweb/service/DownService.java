package com.sinnowa.middlewareweb.service;

import com.sinnowa.middlewareweb.dao.DownSampleDAO;
import com.sinnowa.middlewareweb.dao.DownTaskDAO;
import com.sinnowa.middlewareweb.model.down.DownConnectClient;
import com.sinnowa.middlewareweb.model.down.DownMessage;
import com.sinnowa.middlewareweb.model.down.DownSampleInfo;
import com.sinnowa.middlewareweb.model.down.DownSampleTaskInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by ZingBug on 2017/12/10.
 */
@Service
public class DownService {
    private static final Logger logger=Logger.getLogger(DownService.class);

    @Autowired
    private DownSampleDAO downSampleDAO;

    @Autowired
    private DownTaskDAO downTaskDAO;

    //后续考虑锁
    //定义消息优先队列
    private Queue<DownMessage> downMessageQueue=new PriorityBlockingQueue<DownMessage>();
    //定义当前连接客户端队列
    private Map<String,DownConnectClient> connectClientHashMap=new ConcurrentHashMap<>();

    /*消息优先队列操作*/
    /**
     * 向优先队列中添加数据
     * @param message
     */
    public void addMessage(DownMessage message)
    {
        if(message==null)
        {
            logger.error("argument to addMessage() is null");
            throw new IllegalArgumentException("argument to addMessage() is null");
        }
        downMessageQueue.add(message);
    }

    /**
     * 从队列中取出优先级最高的值，但不删除
     * @return
     */
    public DownMessage getMessage()
    {
        if(messageIsEmpty())
        {
            return null;
        }
        return downMessageQueue.peek();
    }

    /**
     * 从队列中取出优先级最高的值并将其删除
     * @return
     */
    public DownMessage deleteMessage()
    {
        if(messageIsEmpty())
        {
            return null;
        }
        return downMessageQueue.poll();
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean messageIsEmpty()
    {
        return downMessageQueue.isEmpty();
    }

    /*连接客户端哈希表操作*/

    /**
     * 添加新连接的客户端信息
     * @param client
     */
    public void addCient(DownConnectClient client)
    {
        if(client==null)
        {
            throw new IllegalArgumentException("argument to addClient() is null");
        }
        if(!connectClientHashMap.containsKey(client.getRemoteAddress()))
        {
            connectClientHashMap.put(client.getRemoteAddress(),client);
        }
    }

    /**
     * 通过地址删除断开连接的客户端信息
     * @param remoteAddress
     * @return
     */
    public DownConnectClient removeClientByAddress(String remoteAddress)
    {
        if(remoteAddress==null)
        {
            throw new IllegalArgumentException("argument to removeClientByAddress() is null");
        }
        if(connectClientHashMap.containsKey(remoteAddress))
        {
            return connectClientHashMap.remove(remoteAddress);
        }
        return null;
    }

    /**
     * 通过标识号删除断开连接的客户端信息
     * @param identifier
     * @return
     */
    public DownConnectClient removeClientByIdentifier(String identifier)
    {
        DownConnectClient client=getClientByIdentifier(identifier);
        if(client!=null)
        {
            return removeClientByAddress(client.getRemoteAddress());
        }
        return null;
    }

    /**
     * 返回指定地址客户端信息
     * @param remoteAddress
     * @return
     */
    public DownConnectClient getClientByAddress(String remoteAddress)
    {
        if(remoteAddress==null)
        {
            throw new IllegalArgumentException("argument to getClientByAddress() is null");
        }
        if(connectClientHashMap.containsKey(remoteAddress))
        {
            return connectClientHashMap.get(remoteAddress);
        }
        return null;
    }

    /**
     * 返回指定标识客户端信息
     * @param identifier
     * @return
     */
    public DownConnectClient getClientByIdentifier(String identifier)
    {
        for(DownConnectClient client:connectClientHashMap.values())
        {
            if(identifier.equals(client.getIdentifier()))
            {
                return client;
            }
        }
        return null;
    }

    /**
     * 返回所有已连接的客户端名字
     * @return
     */
    public List<String> getClientsIdentifier()
    {
        List<String> list=new LinkedList<>();
        for(DownConnectClient client:connectClientHashMap.values())
        {
            list.add(client.getIdentifier());
        }
        return list;
    }

    /**
     * 判断当前连接客户端是否为空
     * @return
     */
    public boolean clientIsEmpty()
    {
        return connectClientHashMap.isEmpty();
    }

    /**
     * 往数据库内添加一个完整的DownSampleInfo信息
     * @param info
     */
    public void addDownMessageToDB(DownSampleInfo info)
    {
        if(info==null)
        {
            logger.error("argument to addDownMessage() is null");
            throw new IllegalArgumentException("argument to addDownMessage() is null");
        }
        downSampleDAO.addDownSample(info);
        for(DownSampleTaskInfo taskInfo:info.getItems())
        {
            downTaskDAO.addDownTask(taskInfo);
        }
    }
}
