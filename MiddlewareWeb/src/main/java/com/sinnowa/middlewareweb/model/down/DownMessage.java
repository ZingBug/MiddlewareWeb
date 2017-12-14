package com.sinnowa.middlewareweb.model.down;

import java.util.Date;

/**
 * Created by ZingBug on 2017/12/8.
 */
public class DownMessage implements Comparable<DownMessage> {
    private String address;//设备端口地址
    private String message;//设备信息
    private int priorityID;//优先级，数字越少表示优先级越高，日常在3
    private String identifier;//远程连接识别码
    private Date date;//设备连接时间

    //比较器
    @Override
    public int compareTo(DownMessage o) {
        return this.getPriorityID()-o.getPriorityID();
    }

    public DownMessage()
    {
        this("","","");
    }
    public DownMessage(String identifier,String address,String message)
    {
        this(identifier,address,message,3,new Date());
    }
    public DownMessage(String identifier,String address,String message,int priorityID,Date date)
    {
        this.identifier=identifier;
        this.address=address;
        this.message=message;
        this.priorityID=priorityID;
        this.date=date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(int priorityID) {
        this.priorityID = priorityID;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
