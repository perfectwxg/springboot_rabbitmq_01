package com.xianguang.learn.mapper;


import com.xianguang.learn.domain.ProductRobbingRecord;

/**
 * @author kongchengguying
 * 商品抢购
 */
public interface ProductRobbingRecordMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(ProductRobbingRecord record);

    /**
     * 条件插入
     * @param record
     * @return
     */
    int insertSelective(ProductRobbingRecord record);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    ProductRobbingRecord selectByPrimaryKey(Integer id);

    /**
     * 主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ProductRobbingRecord record);

    /**
     * 主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(ProductRobbingRecord record);
}