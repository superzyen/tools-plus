package datauploadtool.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommMapper {
    boolean insert(@Param("sql") String sql);
}
