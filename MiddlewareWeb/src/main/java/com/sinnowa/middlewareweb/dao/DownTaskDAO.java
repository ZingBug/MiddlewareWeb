package com.sinnowa.middlewareweb.dao;

import com.sinnowa.middlewareweb.model.down.DownSampleTaskInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by ZingBug on 2017/12/8.
 */
@Mapper
public interface DownTaskDAO {
    String TABLE_NAME="downtask";
    String INSERT_FIELDS=" sampleId, item, kind, device, sendTime";
    String SELECT_FIELDS=" sampleId, item, kind, device, sendTime";

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") value (#{sampleId},#{item},#{kind},#{device},#{sendTime})"})
    int addDownTask(DownSampleTaskInfo downSampleTaskInfo);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where sampleId=#{param1} and device=#{param2}"})
    DownSampleTaskInfo selectBySampleIdAndDevice(String param1,String param2);
}
