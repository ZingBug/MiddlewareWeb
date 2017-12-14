package com.sinnowa.middlewareweb.model.down;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.message.DSR_Q03;
import ca.uhn.hl7v2.model.v231.segment.*;
import ca.uhn.hl7v2.parser.PipeParser;
import com.sinnowa.middlewareweb.util.Utils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by ZingBug on 2017/12/8.
 */
public class DownSampleInfo {
    private static final Logger logger=Logger.getLogger(DownSampleInfo.class);

    //private String admissionNumber;
    //private String bedNumber;
    //private Date dateOfBrith;
    //private String patientAlias;
    //private String race;
    //private String patientAddress;
    //private String homePhoneNumber;
    //private String businessPhoneNumber;
    //private String primaryLanguage;
    //private String maritalStatus;
    //private String religion;
    //private String patientAccoutNumber;
    //private String socialLicenseNumber;
    //private String driverLicenseNumber;
    //private String ethnicGroup;
    //private String birthPlace;
    //private String nationality;
    //private String barCode;
    //private String isEmrgency;
    //private String collcetionVolume;
    //private String sampleType;
    //private String fetchDoctor;
    //private String fetchDepartment;

    private String sex;
    private String sampleId;
    private String patientId;
    private String firstName;
    private String age;
    private String emergency;
    private String kind;
    private Date sendTime;
    private int isSend;
    private String device;
    private List<DownSampleTaskInfo> items;

    public DownSampleInfo()
    {
        this.sampleId="";
        this.patientId="";
        this.device="";
        this.kind="";
        this.sendTime=new Date();
        this.firstName="";
        this.sex="";
        this.age="0";
        this.items=new LinkedList<>();
        this.isSend=0;
    }

    public DownSampleInfo(String sampleId,String patientId,String device,String kind,Date sendTime,String firstName,String sex,String age,List<DownSampleTaskInfo> items)
    {
        this(sampleId,patientId,device,kind,sendTime,firstName,sex,age,items,0);
    }

    public DownSampleInfo(String sampleId,String patientId,String device,String kind,Date sendTime,String firstName,String sex,String age,List<DownSampleTaskInfo> items,int isSend)
    {
        this.sampleId=sampleId;
        this.patientId=patientId;
        this.device=device;
        this.kind=kind;
        this.sendTime=sendTime;
        this.firstName=firstName;
        this.sex=sex;
        this.age=age;
        this.items=items;
        this.isSend=isSend;
    }

    public String getHl7Message()
    {
        String message="";
        DSR_Q03 dsrQ03=new DSR_Q03();
        try
        {
            MSH msh=dsrQ03.getMSH();
            msh.getMessageType().getMsg1_MessageType().setValue("DSR");
            msh.getMessageType().getMsg2_TriggerEvent().setValue("Q03");

            msh.getFieldSeparator().setValue("|");
            msh.getReceivingApplication().getNamespaceID().setValue("Sinnowa");
            msh.getReceivingFacility().getNamespaceID().setValue(device);
            msh.getEncodingCharacters().setValue("^~\\&");
            msh.getVersionID().getVersionID().setValue("2.3.1");
            msh.getDateTimeOfMessage().getTimeOfAnEvent().setValueToSecond(new Date());
            msh.getProcessingID().getProcessingID().setValue("P");

            msh.getAcceptAcknowledgmentType().setValue("P");

            msh.getCountryCode().setValue("ASCII");

            MSA msa=dsrQ03.getMSA();
            msa.getAcknowledgementCode().setValue("AA");
            msh.getMessageControlID().setValue("1");
            msa.getTextMessage().setValue("Message accepted");
            msa.getErrorCondition().getIdentifier().setValue("0");

            ERR err=dsrQ03.getERR();
            err.getErrorCodeAndLocation(0).getSegmentID().setValue("0");

            QAK qak=dsrQ03.getQAK();
            qak.getQueryTag().setValue("SR");
            qak.getQueryResponseStatus().setValue("OK");

            QRD qrd=dsrQ03.getQRD();
            qrd.getQueryDateTime().getTimeOfAnEvent().setValueToSecond(new Date());
            qrd.getQueryFormatCode().setValue("R");
            qrd.getQueryPriority().setValue("D");
            qrd.getQueryID().setValue("1");
            qrd.getQuantityLimitedRequest().getQuantity().setValue("1");
            qrd.getWhoSubjectFilter(0).getIDNumber().setValue("123456789");
            qrd.getWhatSubjectFilter(0).getIdentifier().setValue("OTH");
            qrd.getQueryResultsLevel().setValue("T");

            QRF qrf=dsrQ03.getQRF();
            qrf.getWhereSubjectFilter(0).setValue(device);
            qrf.getWhenDataEndDateTime().getTimeOfAnEvent().setValueToSecond(new Date());
            qrf.getWhenDataStartDateTime().getTimeOfAnEvent().setValueToSecond(new Date());
            qrf.getWhichDateTimeQualifier(0).setValue("RCT");
            qrf.getWhichDateTimeStatusQualifier(0).setValue("COR");
            qrf.getDateTimeSelectionQualifier(0).setValue("ALL");

            SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");

            dsrQ03.getDSP(0).getDataLine().setValue(patientId);
            dsrQ03.getDSP(1).getSetIDDSP().setValue("1");
            dsrQ03.getDSP(2).getDataLine().setValue(firstName);
            dsrQ03.getDSP(3).getDataLine().setValue(df.format(Utils.AgeToDate(age)));
            dsrQ03.getDSP(4).getDataLine().setValue(sex);
            dsrQ03.getDSP(5).getSetIDDSP().setValue("5");
            dsrQ03.getDSP(6).getSetIDDSP().setValue("6");
            dsrQ03.getDSP(7).getSetIDDSP().setValue("7");
            dsrQ03.getDSP(8).getSetIDDSP().setValue("8");
            dsrQ03.getDSP(9).getSetIDDSP().setValue("9");
            dsrQ03.getDSP(10).getSetIDDSP().setValue("10");
            dsrQ03.getDSP(11).getSetIDDSP().setValue("11");
            dsrQ03.getDSP(12).getSetIDDSP().setValue("12");
            dsrQ03.getDSP(13).getSetIDDSP().setValue("13");
            dsrQ03.getDSP(14).getSetIDDSP().setValue("14");
            dsrQ03.getDSP(15).getSetIDDSP().setValue("15");
            dsrQ03.getDSP(16).getSetIDDSP().setValue("16");
            dsrQ03.getDSP(17).getSetIDDSP().setValue("17");
            dsrQ03.getDSP(18).getSetIDDSP().setValue("18");
            dsrQ03.getDSP(19).getSetIDDSP().setValue("19");
            dsrQ03.getDSP(20).getSetIDDSP().setValue("20");
            dsrQ03.getDSP(21).getDataLine().setValue(sampleId);
            dsrQ03.getDSP(22).getDataLine().setValue(df.format(sendTime));
            dsrQ03.getDSP(23).getDataLine().setValue(emergency);
            dsrQ03.getDSP(24).getSetIDDSP().setValue("24");
            dsrQ03.getDSP(25).getDataLine().setValue(kind);
            dsrQ03.getDSP(26).getSetIDDSP().setValue("26");
            dsrQ03.getDSP(27).getSetIDDSP().setValue("27");

            int i=28;
            for(DownSampleTaskInfo taskInfo:items)
            {
                String info=taskInfo.getIndex()+"^"+taskInfo.getItem()+"^^";
                dsrQ03.getDSP(i).getDataLine().setValue(info);
                i++;
            }
            PipeParser parser=new PipeParser();
            message=parser.encode(dsrQ03);
        }catch (HL7Exception e)
        {
            logger.error(e.getMessage());
        }
        return message;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<DownSampleTaskInfo> getItems() {
        return items;
    }

    public void setItems(List<DownSampleTaskInfo> items) {
        this.items = items;
    }
}
