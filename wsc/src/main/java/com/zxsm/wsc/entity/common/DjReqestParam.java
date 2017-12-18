package com.zxsm.wsc.entity.common;

import com.zxsm.wsc.common.DjKeys;

public class DjReqestParam
{
	public Integer size = DjKeys.pageSize;//翻页 一页的内容数量
	
	public Integer page = 0;//翻页 第几页
	
	public String EVENTTARGET;// 事件 类型
	
	public String EVENTARGUMENT;// 事件 参数
	
	public String VIEWSTATE;//视图 ：图文列表 文字列表

	public Integer[] listChkIdx;// 选中CheckBox的Id
	
	public Long[] listId;// 列的Id
	
	public Double[] sortId;//排序的Id

	public String keywords;//搜索关键字
	
	public Long categoryId;//类别Id
	
	public Integer statusId;//状态Id
	
	public String orderNo; //订单号
	
	public String typeId; //订单类型

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getEVENTTARGET() {
		return EVENTTARGET;
	}

	public void setEVENTTARGET(String eVENTTARGET) {
		EVENTTARGET = eVENTTARGET;
	}

	public String getEVENTARGUMENT() {
		return EVENTARGUMENT;
	}

	public void setEVENTARGUMENT(String eVENTARGUMENT) {
		EVENTARGUMENT = eVENTARGUMENT;
	}

	public String getVIEWSTATE() {
		return VIEWSTATE;
	}

	public void setVIEWSTATE(String vIEWSTATE) {
		VIEWSTATE = vIEWSTATE;
	}

	public Integer[] getListChkIdx() {
		return listChkIdx;
	}

	public void setListChkIdx(Integer[] listChkIdx) {
		this.listChkIdx = listChkIdx;
	}

	public Long[] getListId() {
		return listId;
	}

	public void setListId(Long[] listId) {
		this.listId = listId;
	}

	public Double[] getSortId() {
		return sortId;
	}

	public void setSortId(Double[] sortId) {
		this.sortId = sortId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
