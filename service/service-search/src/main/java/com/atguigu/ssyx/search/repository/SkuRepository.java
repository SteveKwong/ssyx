package com.atguigu.ssyx.search.repository;

import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author ASUS
 */
public interface SkuRepository extends ElasticsearchRepository<SkuEs,Long> {

    /**
     * 获取爆款商品
     * @param pageable
     * @return
     */
    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageable);

    /**
     * 判断keyword是否为空，如果为空 ，根据仓库id + 分类id查询
     * @param categoryId
     * @param wareId
     * @param pageable
     * @return
     */
    Page<SkuEs> findByCategoryIdAndWareId(Long categoryId, Long wareId, Pageable pageable);

    /**
     * 如果keyword不为空根据仓库id + keyword进行查询
     * @param keyword
     * @param wareId
     * @param pageable
     * @return
     */
    Page<SkuEs> findByKeywordAndWareId(String keyword, Long wareId, Pageable pageable);

    //List<SkuEs> findByOrderByHotScoreDesc();
}
