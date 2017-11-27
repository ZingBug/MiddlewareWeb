package com.sinnowa.middlewareweb.dao;

import com.sinnowa.middlewareweb.model.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by ZingBug on 2017/11/26.
 */
@Mapper
public interface DeviceDAO {
    String TABLE_NAME="device";
    String INSERT_FIELDS=" device, time, sampleCount0, sampleCount2, sampleCount4, sampleCount6, sampleCount8," +
            " sampleCount10, sampleCount12, sampleCount14, sampleCount16, sampleCount18, sampleCount20, sampleCount22";
    String SELECT_FIELDS=" time, device, sampleCount0, sampleCount2, sampleCount4, sampleCount6, sampleCount8," +
            " sampleCount10, sampleCount12, sampleCount14, sampleCount16, sampleCount18, sampleCount20, sampleCount22";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") value (#{device},#{time},#{sampleCount0},#{sampleCount2},#{sampleCount4}," +
            "#{sampleCount6},#{sampleCount8},#{sampleCount10},#{sampleCount12},#{sampleCount14},#{sampleCount16},#{sampleCount18},#{sampleCount20}," +
            "#{sampleCount22})"})
    int addDevice(Device deviceSampleCount);

    @Update({"update ",TABLE_NAME,"set sampleCount0=#{sampleCount0}, sampleCount2=#{sampleCount2}," +
            " sampleCount4=#{sampleCount4}, sampleCount6=#{sampleCount6}, sampleCount8=#{sampleCount8}, sampleCount10=#{sampleCount10}," +
            " sampleCount12=#{sampleCount12}, sampleCount14=#{sampleCount14}, sampleCount16=#{sampleCount16}, sampleCount18=#{sampleCount18}," +
            " sampleCount20=#{sampleCount20}, sampleCount22=#{sampleCount22} where TO_DAYS(`time`) = TO_DAYS(NOW()) AND device=#{device}"})
    void updateResultByDevice(Device deviceSampleCount);

    @Update({"update ",TABLE_NAME,"set sampleCount0=#{sampleCount0}, sampleCount2=#{sampleCount2}," +
            " sampleCount4=#{sampleCount4}, sampleCount6=#{sampleCount6}, sampleCount8=#{sampleCount8}, sampleCount10=#{sampleCount10}," +
            " sampleCount12=#{sampleCount12}, sampleCount14=#{sampleCount14}, sampleCount16=#{sampleCount16}, sampleCount18=#{sampleCount18}," +
            " sampleCount20=#{sampleCount20}, sampleCount22=#{sampleCount22} where date_format(`time`,'%Y-%m-%d') = date_format(#{time},'%Y-%m-%d') AND device=#{device}"})
    void updateResultByDeviceAndDate(Device device);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where TO_DAYS(`time`) = TO_DAYS(NOW()) AND device=#{device}"})
    Device selectByDevice(String device);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where TO_DAYS(`time`) = TO_DAYS(NOW())"})
    List<Device> selectTodayDevice();

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where date_format(`time`,'%Y-%m-%d') = #{date}"})
    List<Device> selectByDate(String date);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where date_format(`time`,'%Y-%m-%d') = #{param2} AND device=#{param1}"})
    Device selectByDeviceAndDate(String param1,String param2);
}
