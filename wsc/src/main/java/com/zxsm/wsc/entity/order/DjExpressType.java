package com.zxsm.wsc.entity.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 配送方式
 *
 * 记录了支付方式各字段
 * 
 * @author maeing
 *
 */

@Entity
public class DjExpressType extends DjBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 配送名称
    @Column(length = 100)
    private String title;
    
    // 物流编码
    @Column(length = 50)
    private String code;
    
    // 物流官网
    @Column(length = 250)
    private String uri;
    
    // 是否启用
    @Column
    private Boolean isEnable;
    
    // 配送费用
    @Column(scale=2)
    private BigDecimal fee;
    
    // 排序数字
    @Column
    private Double sortId;
    
    // 描述说明
    @Column(length = 250)
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
