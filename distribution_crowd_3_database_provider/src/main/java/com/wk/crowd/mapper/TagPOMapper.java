package com.wk.crowd.mapper;

import com.wk.crowd.pojo.po.TagPO;
import com.wk.crowd.pojo.po.TagPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagPOMapper {
    int countByExample(TagPOExample example);

    int deleteByExample(TagPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TagPO record);

    int insertSelective(TagPO record);

    List<TagPO> selectByExample(TagPOExample example);

    TagPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TagPO record, @Param("example") TagPOExample example);

    int updateByExample(@Param("record") TagPO record, @Param("example") TagPOExample example);

    int updateByPrimaryKeySelective(TagPO record);

    int updateByPrimaryKey(TagPO record);

	void insertRelationBatch(@Param("projectPOId")Integer projectPOId, @Param("tagIdList")List<Integer> tagIdList);
}