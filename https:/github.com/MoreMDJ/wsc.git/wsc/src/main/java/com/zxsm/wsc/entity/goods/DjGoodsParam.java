package com.zxsm.wsc.entity.goods;

import com.zxsm.wsc.entity.common.DjBaseRequestParam;

public class DjGoodsParam extends DjBaseRequestParam
{
	public Long cId;
	
	public Long gId;
	
	public String sort;

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public Long getgId() {
		return gId;
	}

	public void setgId(Long gId) {
		this.gId = gId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}