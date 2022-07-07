/**
 * 
 */
package com.haoyu.framework.modules.seq.mapper;


import com.haoyu.framework.core.base.BaseMapper;
import com.haoyu.framework.modules.seq.entity.Seq;
import org.springframework.stereotype.Repository;

/**
 * @author Chaos
 * @date 2013-3-8
 * @time 下午5:38:37
 *
 */
@Repository("SeqMapper")
public interface SeqMapper extends BaseMapper<Seq> {

	Seq selectBySeqCode(String seqCode);
	
	/**
	 * 更新逻辑为必须id一致，旧数据的next_num=新数据的current_num，version一致
	 * @param seq
	 * @return
	 */
	int updateByPrimaryKeyAndNumSelective(Seq seq);

}
