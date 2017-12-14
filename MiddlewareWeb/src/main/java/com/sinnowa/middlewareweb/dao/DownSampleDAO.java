package com.sinnowa.middlewareweb.dao;

import com.sinnowa.middlewareweb.model.down.DownSampleInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by ZingBug on 2017/12/8.
 */
@Mapper
public interface DownSampleDAO {
    String TABLE_NAME="downsample";
    String INSERT_FIELDS=" sampleId, patientId, firstName, sex, age, device, sendTime";
    String SELECT_FIELDS=" sampleId, patientId, firstName, sex, age, device, sendTime";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") value (#{sampleId},#{patientId},#{firstName},#{sex},#{age},#{device},#{sendTime})"})
    int addDownSample(DownSampleInfo downSampleInfo);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where sampleId=#{param1} and device=#{param2}"})
    DownSampleInfo selectBySampleIdAndDevice(String param1,String param2);
}
