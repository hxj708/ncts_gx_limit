/**
 * 
 */
package com.haoyu.framework.modules.seq.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;

import com.haoyu.framework.core.base.BaseServiceImpl;
import com.haoyu.framework.modules.seq.entity.Seq;
import com.haoyu.framework.modules.seq.mapper.SeqMapper;
import com.haoyu.framework.modules.seq.service.ISeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Chaos
 * @date 2013-3-21
 * @time 下午5:53:26
 *
 */
@Service("SeqService")
public class SeqServiceImpl extends BaseServiceImpl<SeqMapper,Seq> implements ISeqService {

	@Autowired
	private SeqMapper seqMapper;
	
	/**
	 * 取一次就更新一次
	 * 更新逻辑为必须id一致，旧数据的next_num=新数据的current_num，version一致
	 * 如果不一致，则表明此数据已被更新过，要重新递归调用取
	 * @param seqCode 代码
	 * @param userId 更新人
	 */
	@Override
	public BigDecimal generatSeq(String seqCode,String userId){
//		int seqRetryTime_Max=Integer.parseInt(ConfigUtils.getProperty(AuthConstants.SEQ_RETRYTIME));
//		int seqRetryTime=0;
		BigDecimal seqNum=this.generatSeq(seqCode, userId, 10, 0);
		return seqNum;
	}
	
	public BigDecimal generatSeq(String seqCode,String userId,int seqRetryTime_Max,int seqRetryTime){
		Seq seq= seqMapper.selectBySeqCode(seqCode);
		BigDecimal seqNum=seq.getNextNum();
		seq.setCurrentNum(seq.getCurrentNum().add(seq.getStepNum()));
		seq.setNextNum(seq.getNextNum().add(seq.getStepNum()));
		seq.setUpdateUser(userId);
		seq.setUpdateTime(Calendar.getInstance().getTime());
		int updateCount= seqMapper.updateByPrimaryKeyAndNumSelective(seq);
		if(seqRetryTime<seqRetryTime_Max){
			if(updateCount==0){
				seqRetryTime=seqRetryTime+1;//计算重试次数
				seqNum=this.generatSeq(seqCode, userId,seqRetryTime_Max,seqRetryTime);
			}
		}
		else{
			seqNum=null;
		}
		return seqNum;
	}

}
