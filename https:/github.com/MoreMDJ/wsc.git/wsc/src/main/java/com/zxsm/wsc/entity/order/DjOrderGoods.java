package com.zxsm.wsc.entity.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 订单商品
 *
 * 记录了订单商品的相关信息
 * 
 * @author maeing
 *
 */

@Entity
public class DjOrderGoods
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 商品ID
    @Column
    private Long goodsId;
    
    // 商品名称
    @Column(length = 100)
    private String goodsTitle;
    
    // 商品封面
    @Column(length = 50)
    private String imgUrl;
    
    // 市场价 市场的参考价格，不参与计算
 	@Column(scale = 2)
 	private BigDecimal marketPrice;
    
    // 销售价 出售的实际交易价格
 	@Column(nullable = false, scale = 2)
 	private BigDecimal salePrice;

 	// 成本价 商品成本价格
 	@Column(nullable = false, scale = 2)
 	private BigDecimal costPrice;
 	
    // 商品销售方式
    @Column
    private Integer saleTypeId;
    
    // 订购数量
    @Column
    private Integer quantity;
    
    // 获得积分
    @Column
    private Integer points;
    
    // 是否已评价
    @Column
    private Boolean isCommented;
    
    // 评论ID
    @Column
    private Long commentId;
    
    // 邮费 买家需要支付的配送费用
 	@Column(nullable = false,scale = 2)
 	private BigDecimal postFee;
 	
 	// 提成比例
 	@Column(scale = 2)
 	private Double costRate;
 	
 	// 提成金额
 	@Column(scale = 2)
 	private Double costFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getSaleTypeId() {
		return saleTypeId;
	}

	public void setSaleTypeId(Integer saleTypeId) {
		this.saleTypeId = saleTypeId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Boolean getIsCommented() {
		return isCommented;
	}

	public void setIsCommented(Boolean isCommented) {
		this.isCommented = isCommented;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public BigDecimal getPostFee() {
		return postFee;
	}

	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}

	public Double getCostRate() {
		return costRate;
	}

	public void setCostRate(Double costRate) {
		this.costRate = costRate;
	}

	public Double getCostFee() {
		return costFee;
	}

	public void setCostFee(Double costFee) {
		this.costFee = costFee;
	}
}
