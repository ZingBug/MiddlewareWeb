package com.sinnowa.middlewareweb.configure;

import com.sinnowa.middlewareweb.service.TCPServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by ZingBug on 2017/12/14.
 */
@Component
public class ServerStart implements CommandLineRunner {

    @Autowired
    private TCPServerService tcpServerService;

    @Override
    public void run(String... args) throws Exception {
        //启动TCP连接
        tcpServerService.startServe();
    }
}
