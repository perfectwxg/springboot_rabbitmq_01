package com.xianguang.learn.mapper;


import com.xianguang.learn.domain.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface ProductMapper {
    /**
     * 通过id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入商品
     * @param record
     * @return
     */
    int insert(Product record);

    /**
     * 插入商品
     * @param record
     * @return
     */
    int insertSelective(Product record);

    /**
     * 通过id获取
     * @param id
     * @return
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * 通过主键更新商品
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Product record);

    /**
     * 更新商品
     * @param record
     * @return
     */
    int updateByPrimaryKey(Product record);

    /**
     * 商品编号查询商品
     * @param productNo
     * @return
     */
    @Transactional(readOnly = true)
    Product selectByProductNo(@Param("productNo") String productNo);

    /**
     * 更新商品库存
     * @param record
     * @return
     */
    int updateTotal(Product record);
}