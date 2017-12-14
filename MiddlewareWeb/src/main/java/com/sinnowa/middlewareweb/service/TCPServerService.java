package com.sinnowa.middlewareweb.service;

import com.sinnowa.middlewareweb.model.down.DownConnectClient;
import com.sinnowa.middlewareweb.model.down.DownMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.IllegalFormatPrecisionException;
import java.util.Iterator;

/**
 * Created by ZingBug on 2017/12/8.
 */
@Service
public class TCPServerService {
    private static final Logger logger=Logger.getLogger(TCPServerService.class);
    //服务器IP
    public static final String SERVER_IP="127.0.0.1";

    //服务器端口号
    public static final int SERVER_PORT=2001;

    @Autowired
    private DownService downService;

    public void startServe() {
        Thread serve=new Thread(new Serve());
        logger.info("打开下发线程，开启TCP服务器");
        serve.start();//开启线程
    }

    class Serve implements Runnable{
        @Override
        public void run() {
            try
            {
                //使用NIO需要用到ServerSocketChannel
                //其中包含一个ServerSocket对象
                ServerSocketChannel serverChannel=ServerSocketChannel.open();

                //创建地址对象
                InetSocketAddress localAddr=new InetSocketAddress(SERVER_IP,SERVER_PORT);

                //服务器绑定地址
                serverChannel.bind(localAddr);

                //设置为非阻塞
                serverChannel.configureBlocking(false);

                //注册到selector，会调用ServerSocket的accept
                //我们用selector监听accept能否返回
                //当调用accept可以放回时，会得到通知
                //注意，是可以返回，还需要调用accept
                Selector selector=Selector.open();
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (true)
                {
                    //调用select，阻塞在这里，知道有注册的channel满足条件
                    selector.select();

                    //如果走到这里，说明有符合条件的channel
                    //可以通过selector.selectedKeys().iterator()拿到符合条件的迭代器
                    Iterator<SelectionKey> keys=selector.selectedKeys().iterator();

                    //处理满足条件的keys
                    while (keys.hasNext())
                    {
                        //取出一个key并移除
                        SelectionKey key=keys.next();
                        keys.remove();
                        try
                        {
                            if(key.isAcceptable())
                            {
                                //有accept可以返回
                                //取得可以操作的channel
                                ServerSocketChannel server=(ServerSocketChannel)key.channel();

                                //调用accept完成三次握手，返回与客户端可以通信的channel
                                SocketChannel channel=server.accept();

                                //将该channel置非阻塞
                                channel.configureBlocking(false);

                                //注册selector，当可读或可写时将得到通知，select返回
                                channel.register(selector,SelectionKey.OP_READ);
                            }else if(key.isReadable())
                            {
                                //有channel可读，取出可读的channel
                                SocketChannel channel=(SocketChannel)key.channel();
                                //创建读取缓冲区，一次读取1024字节
                                ByteBuffer buffer=ByteBuffer.allocate(1024);
                                //channel.read(buffer);

                                int bytesReaded=channel.read(buffer);

                                String recvStr=new String(buffer.array(),0,bytesReaded);
                                /*后续处理*/

                                //添加一个新的连接客户端
                                DownConnectClient client=new DownConnectClient(recvStr,channel.getRemoteAddress().toString());
                                downService.addCient(client);
                                logger.info("New Client to connect: "+client.toString());

                                /*测试代码*/
                                //DownMessage downMessage=new DownMessage(client.getIdentifier(),client.getRemoteAddress(),"This is a message");
                                //downService.addMessage(downMessage);
                                //DownMessage downMessage1=new DownMessage(client.getIdentifier(),client.getRemoteAddress(),"This is also a message");
                                //downService.addMessage(downMessage1);

                                //注册selector为可读可写
                                key.interestOps(SelectionKey.OP_WRITE|SelectionKey.OP_READ);

                            }else if(key.isWritable())
                            {
                                //有channel可写，取出可写的channel
                                SocketChannel channel=(SocketChannel)key.channel();
                                /*进行后续写操作*/

                                if(!downService.messageIsEmpty())
                                {
                                    DownMessage downMessage=downService.getMessage();
                                    if(downMessage.getAddress().equals(channel.getRemoteAddress().toString()))
                                    {
                                        //如果客户端地址匹配一致，则向客户端发送消息
                                        byte[] sendBytes=downMessage.getMessage().getBytes("UTF-8");
                                        channel.write(ByteBuffer.wrap(sendBytes));
                                        downService.deleteMessage();//发送结束后删除此条消息
                                        try
                                        {
                                            Thread.sleep(500);
                                        }catch (InterruptedException e)
                                        {
                                            logger.error(e.getMessage());
                                        }
                                    }
                                }
                            }
                        }catch (IOException e)
                        {
                            //当客户端Socket关闭时，会走到这，清理资源
                            if(!key.isAcceptable())
                            {
                                //说明此时是在连接状态中，远程客户端突然断开
                                String remoteAddress=((SocketChannel)key.channel()).getRemoteAddress().toString();
                                logger.info(e.getMessage()+"  "+remoteAddress);
                                //删除此连接客户端
                                DownConnectClient removeClient=downService.removeClientByAddress(remoteAddress);
                                if(removeClient!=null)
                                {
                                    logger.info("Old Client to disconnect: "+removeClient);
                                }else
                                {
                                    logger.info("Old Client have disconnect");
                                }

                            }
                            key.cancel();
                            try{
                                key.channel().close();
                            }catch (IOException e1)
                            {
                                logger.error(e1.getMessage());
                            }
                        }
                    }
                }
            }catch (IOException e)
            {
                logger.error(e.getMessage());
            }
        }
    }
}
