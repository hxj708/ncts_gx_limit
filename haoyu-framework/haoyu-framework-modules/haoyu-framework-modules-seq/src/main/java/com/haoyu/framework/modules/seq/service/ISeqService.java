/**
 * 
 */
package com.haoyu.framework.modules.seq.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.seq.entity.Seq;
import com.haoyu.framework.modules.seq.mapper.SeqMapper;

import java.math.BigDecimal;


/**
 * @author Chaos
 * @date 2013-3-21
 * @time 下午5:52:01
 *
 */
public interface ISeqService extends BaseService<Seq, SeqMapper> {

	/**
	 * @param seqCode
	 * @param userId
	 * @return
	 */
	public BigDecimal generatSeq(String seqCode, String userId);

}
