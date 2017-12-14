package com.sinnowa.middlewareweb.model.down;

import java.util.Date;

/**
 * Created by ZingBug on 2017/12/8.
 */
public class DownSampleTaskInfo {
    private String sampleId;
    private int index;
    private String item;
    private String kind;
    private String device;
    private Date sendTime;

    public DownSampleTaskInfo()
    {
        this("",0,"","");
    }

    public DownSampleTaskInfo(String sampleId,int index,String kind,String device)
    {
        this(sampleId,index,"",kind,device);
    }
    public DownSampleTaskInfo(String sampleId,int index,String item,String kind,String device)
    {
        this(sampleId,index,item,kind,device,new Date());
    }

    public DownSampleTaskInfo(String sampleId,int index,String item,String kind,String device,Date sendTime)
    {
        this.sampleId=sampleId;
        this.index=index;
        this.item=item;
        this.kind=kind;
        this.device=device;
        this.sendTime=sendTime;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
