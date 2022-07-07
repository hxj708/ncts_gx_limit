package com.haoyu.framework.modules.seq.entity;

import com.haoyu.framework.core.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Seq extends BaseEntity {
	
	private static final long serialVersionUID = 1677682297622430505L;

	private String id;

	private String seqName;

	private String seqCode;

	private BigDecimal currentNum=BigDecimal.ZERO;

	private BigDecimal stepNum=BigDecimal.ONE;
	
	private BigDecimal nextNum=currentNum.add(stepNum);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName == null ? null : seqName.trim();
	}

	public String getSeqCode() {
		return seqCode;
	}

	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode == null ? null : seqCode.trim();
	}

	public BigDecimal getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(BigDecimal currentNum) {
		this.currentNum = currentNum;
	}

	public BigDecimal getNextNum() {
		return nextNum;
	}

	public void setNextNum(BigDecimal nextNum) {
		this.nextNum = nextNum;
	}

	public BigDecimal getStepNum() {
		return stepNum;
	}

	public void setStepNum(BigDecimal stepNum) {
		this.stepNum = stepNum;
	}

}