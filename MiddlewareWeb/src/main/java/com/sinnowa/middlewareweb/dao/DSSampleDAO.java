package com.sinnowa.middlewareweb.dao;

import com.sinnowa.middlewareweb.model.DSSample;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ZingBug on 2017/11/15.
 */
@Mapper
public interface DSSampleDAO {
    String TABLE_NAME="dssample";
    String INSERT_FIELDS=" sampleId, patientId, item, type, sendTime, device, fullName, result, unit, " +
            "normalLow, normalHigh, time, indicate, firstName, sex, age, sampleKind, doctor, area, bed, department, isGet";
    String SELECT_FIELDS=" id, sampleId, patientId, item, type, sendTime, device, fullName, result, unit, " +
            "normalLow, normalHigh, time, indicate, firstName, sex, age, sampleKind, doctor, area, bed, department, isGet";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") value (#{sampleId},#{patientId},#{item},#{type},#{sendTime},#{device},#{fullName}," +
            "#{result},#{unit},#{normalLow},#{normalHigh},#{time},#{indicate},#{firstName},#{sex},#{age},#{sampleKind},#{doctor},#{area},#{bed}," +
            "#{department},#{isGet})"})
    int addDSSample(DSSample dsSample);

    @Update({"update ",TABLE_NAME," set result=#{result} AND time=#{time} AND isGet=#{isGet} where sampleId=#{sampleId} AND item=#{item}"})
    void updateResult(DSSample dsSample);

    @Update({"update ",TABLE_NAME," set isGet=#{isGet} where sampleId=#{sampleId} AND item=#{item}"})
    void updateIsGet(DSSample dsSample);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    void deleteById(int id);

    @Delete({"delete from ",TABLE_NAME," where sampleId=#{sampleId}"})
    void deleteBySampleId(String sampleId);

    @Delete({"delete from ",TABLE_NAME," where time BETWEEN #{param1} AND #{param2}"})
    void deleteByTime(Date param1,Date param2);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where sampleId=#{sampleId}"})
    List<DSSample> selectBySampleId(String sampleId);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where time BETWEEN #{param1} AND #{param2}"})
    List<DSSample> selectByTime(Date param1,Date param2);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where isGet = #{isGet}"})
    List<DSSample> selectByIsGet(int isGet);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where firstName=#{name}"})
    List<DSSample> selectByName(String name);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where device=#{device}"})
    List<DSSample> selectByDevice(String device);

    @Select({"select distinct device from ",TABLE_NAME})
    List<String> getDevices();
}
