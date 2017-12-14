package com.sinnowa.middlewareweb.model.down;

/**
 * Created by ZingBug on 2017/12/9.
 */
public class DownConnectClient {

    private String identifier;
    private String remoteAddress;
    private int downNum;

    public DownConnectClient(String name,String remoteAddress)
    {
        this(name,remoteAddress,0);
    }

    public DownConnectClient(String identifier,String remoteAddress,int downNum)
    {
        this.identifier=identifier;
        this.remoteAddress=remoteAddress;
        this.downNum=downNum;
    }

    @Override
    public String toString() {
        return "Client[identifier="+identifier+",address="+remoteAddress+"]";
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public int getDownNum() {
        return downNum;
    }

    public void setDownNum(int downNum) {
        this.downNum = downNum;
    }
}
