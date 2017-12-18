package com.zxsm.wsc.entity.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 购物车商品
 * 
 * @author maeing
 *
 */
@Entity
public class DjCartGoods extends DjBaseEntity
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    // 商品ID
    private Long gid;
    
    // 用户ID
    private Long uid;
    
    // 商品标题
    @Column(length = 100)
    private String gTitle;
    
    // 商品封面
    @Column(length = 50)
    private String gImgUrl;
    
    // 商品数量
    @Column
    private Integer quantity;
    
    // 库存数量
    @Column
    private Integer leftQuantity;
    
    // 成交价 (暂时没用)
    @Column(scale = 2)
    private BigDecimal price;
    
    // 市场价 市场的参考价格，不参与计算
 	@Column(scale = 2)
 	private BigDecimal marketPrice;

 	// 销售价 正常销售的交易价格
 	@Column(nullable = false, scale = 2)
 	private BigDecimal salePrice;
 	
 	// 促销价价 活动期间的交易价格
 	@Column(scale = 2)
 	private BigDecimal specialPrice;
    
    // 销售类型
    @Column
    private Integer saleType;
    
	// 是否选中
    @Column
    private Boolean isSelected;
    
    // 商品类型:1 处方药，2 非处方药
    @Column
    private Integer type;
    
    // 邮费
    @Column
    private BigDecimal expressFee;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getgTitle() {
		return gTitle;
	}
	public void setgTitle(String gTitle) {
		this.gTitle = gTitle;
	}
	public String getgImgUrl() {
		return gImgUrl;
	}
	public void setgImgUrl(String gImgUrl) {
		this.gImgUrl = gImgUrl;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getLeftQuantity() {
		return leftQuantity;
	}
	public void setLeftQuantity(Integer leftQuantity) {
		this.leftQuantity = leftQuantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(BigDecimal specialPrice) {
		this.specialPrice = specialPrice;
	}
	public Integer getSaleType() {
		return saleType;
	}
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public BigDecimal getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}
	public DjCartGoods(Long gid, String gTitle, String gImgUrl, Integer quantity, Integer leftQuantity,
			BigDecimal marketPrice, BigDecimal salePrice, BigDecimal specialPrice, Integer saleType,
			Boolean isSelected, Integer type) {
		super();
		this.gid = gid;
		this.gTitle = gTitle;
		this.gImgUrl = gImgUrl;
		this.quantity = quantity;
		this.leftQuantity = leftQuantity;
		this.marketPrice = marketPrice;
		this.salePrice = salePrice;
		this.specialPrice = specialPrice;
		this.saleType = saleType;
		this.isSelected = isSelected;
		this.type = type;
	}
	
	public DjCartGoods(Long id, Long gid, String gTitle, String gImgUrl, Integer quantity, Integer leftQuantity,
			BigDecimal marketPrice, BigDecimal salePrice, BigDecimal specialPrice, Integer saleType,
			Boolean isSelected, Integer type,BigDecimal expressFee) {
		super();
		this.id = id;
		this.gid = gid;
		this.gTitle = gTitle;
		this.gImgUrl = gImgUrl;
		this.quantity = quantity;
		this.leftQuantity = leftQuantity;
		this.marketPrice = marketPrice;
		this.salePrice = salePrice;
		this.specialPrice = specialPrice;
		this.saleType = saleType;
		this.isSelected = isSelected;
		this.type = type;
		this.expressFee = expressFee;
	}
	public DjCartGoods()
	{
		super();
	}

}
