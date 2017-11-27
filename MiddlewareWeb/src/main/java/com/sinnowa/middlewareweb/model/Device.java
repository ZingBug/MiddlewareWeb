package com.sinnowa.middlewareweb.model;

import java.util.Date;

/**
 * Created by ZingBug on 2017/11/26.
 */
public class Device {
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getSampleCount0() {
        return sampleCount0;
    }

    public void setSampleCount0(int sampleCount0) {
        this.sampleCount0 = sampleCount0;
    }

    public int getSampleCount2() {
        return sampleCount2;
    }

    public void setSampleCount2(int sampleCount2) {
        this.sampleCount2 = sampleCount2;
    }

    public int getSampleCount4() {
        return sampleCount4;
    }

    public void setSampleCount4(int sampleCount4) {
        this.sampleCount4 = sampleCount4;
    }

    public int getSampleCount6() {
        return sampleCount6;
    }

    public void setSampleCount6(int sampleCount6) {
        this.sampleCount6 = sampleCount6;
    }

    public int getSampleCount8() {
        return sampleCount8;
    }

    public void setSampleCount8(int sampleCount8) {
        this.sampleCount8 = sampleCount8;
    }

    public int getSampleCount10() {
        return sampleCount10;
    }

    public void setSampleCount10(int sampleCount10) {
        this.sampleCount10 = sampleCount10;
    }

    public int getSampleCount12() {
        return sampleCount12;
    }

    public void setSampleCount12(int sampleCount12) {
        this.sampleCount12 = sampleCount12;
    }

    public int getSampleCount14() {
        return sampleCount14;
    }

    public void setSampleCount14(int sampleCount14) {
        this.sampleCount14 = sampleCount14;
    }

    public int getSampleCount16() {
        return sampleCount16;
    }

    public void setSampleCount16(int sampleCount16) {
        this.sampleCount16 = sampleCount16;
    }

    public int getSampleCount18() {
        return sampleCount18;
    }

    public void setSampleCount18(int sampleCount18) {
        this.sampleCount18 = sampleCount18;
    }

    public int getSampleCount20() {
        return sampleCount20;
    }

    public void setSampleCount20(int sampleCount20) {
        this.sampleCount20 = sampleCount20;
    }

    public int getSampleCount22() {
        return sampleCount22;
    }

    public void setSampleCount22(int sampleCount22) {
        this.sampleCount22 = sampleCount22;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Device()
    {
        this("");
    }
    public Device(String device)
    {
        this(device,new Date());
    }
    public Device(String device,Date time)
    {
        this.device=device;
        this.time=time;
        sampleCount0=0;
        sampleCount2=0;
        sampleCount4=0;
        sampleCount6=0;
        sampleCount8=0;
        sampleCount10=0;
        sampleCount12=0;
        sampleCount14=0;
        sampleCount16=0;
        sampleCount18=0;
        sampleCount20=0;
        sampleCount22=0;
    }
    private String device;
    private Date time;
    private int sampleCount0;
    private int sampleCount2;
    private int sampleCount4;
    private int sampleCount6;
    private int sampleCount8;
    private int sampleCount10;
    private int sampleCount12;
    private int sampleCount14;
    private int sampleCount16;
    private int sampleCount18;
    private int sampleCount20;
    private int sampleCount22;
}
