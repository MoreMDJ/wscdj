package com.zxsm.wsc.entity.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;

/**
 * 商品信息表
 * 
 * @author maeing
 *
 */

@Entity
public class DjGoods extends DjBaseEntity
{
	public static final String sTitle = "title";
	public static final String sSubTitle= "subTitle";
	public static final String sStatus= "status";
	public static final String sLeftNumber= "leftNumber";
	public static final String sTags= "tags";
	public static final String sCategoryIdTree = "categoryIdTree";
	public static final String sCategoryId = "categoryId";
	public static final String sSaleType= "saleType";
	public static final String sSortAsc= "sortAsc";
	public static final String sSortDesc= "sortDesc";
	public static final String sSku		= "sku";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 商品标题
	@Column(length = 100)
	private String title;

	// 副标题
	@Column(length = 255)
	private String subTitle;

	// 封面图片
	@Column(length = 50)
	private String imgUrl;

	// 轮播展示图片，多张图片以,隔开
	@Column(length = 200)
	private String showPictures;

	// 商品详情
	@Column
	@Lob
	private String contentDetail;

	// 商品售后服务
	@Column
	@Lob
	private String serveDetail;

	// 包装与售后
	@Column
	@Lob
	private String packDetail;
	
	// 是否是处方药
	@Column
	private Boolean otc;

	// 商品类型:1 处方药，2 非处方药
	@Column
	private Integer type;

	// 商品状态:1 上架，2 待审核，3 下架
	@Column
	private Integer status;
	
	// 销售方式 :0 正常销售，1秒杀，2好货推荐
	@Column
	private Integer saleType;

	// 是否热销
	@Column
	private Boolean isHot;

	// 是否特价
	@Column
	private Boolean isSpecialPrice;

	// 商品类别
	@Column
	private Long categoryId;

	// 商品类型名称
	@Column(length = 50)
	private String categoryTitle;

	// 商品所有类型
	@Column(length = 50)
	private String categoryIdTree;
	
	// 商品参数
    @OneToMany
    @JoinColumn(name="gid")
    private List<DjGoodsParameter> paramList;
	
	// 商品编码
	@Column(length = 50)
	private String goodsNo;
	
	// 商品库存量单位(商品货号)
	@Column(length = 50)
	private String sku;
	
	// 批准文号
	@Column(length = 160)
	private String approvalNumber;
	
	// 商品规格
	@Column(length = 40)
	private String specification;
	
	// 厂商
	@Column(length = 90)
	private String manufacturer;
	
	// 参数值，用于搜索
	@Column(length = 250)
	private String paramValueCollect;

	// 市场价 市场的参考价格，不参与计算
	@Column(scale = 2)
	private BigDecimal marketPrice;

	// 销售价 正常销售的交易价格
	@Column(nullable = false, scale = 2)
	private BigDecimal salePrice;
	
	// 促销价价 活动期间的交易价格
	@Column(scale = 2)
	private BigDecimal specialPrice;

	// 成本价 商品成本价格
	@Column(nullable = false, scale = 2)
	private BigDecimal costPrice;
	
	// 邮费 买家需要支付的配送费用
	@Column(nullable = false,scale = 2)
	private BigDecimal postFee;
	
	// 提成比例
	@Column(scale = 2)
	private Double costRate;

	// 库存数量
	@Column
	private Integer leftNumber;

	// 已售数量
	@Column
	private Integer soldNumber;

	// 排序号
	@Column
	private Double sortId;

	// 是否支持限时抢购
	@Column
	private Boolean isFlashSale;

	// 品牌名
	@Column(length = 20)
	private String brandTitle;

	// 品牌ID
	@Column
	private Long brandId;

	// 评论数
	@Column
	private Long commentCount;
	
	// 浏览次数
	@Column
	private Long viewCount;
	
	// 真实浏览次数
	@Column
	private Long click;
	
	// 关键字 多个可用英文逗号分隔开
	@Column(length = 500)
	private String tags;

	// 上架时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	// 结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getShowPictures() {
		return showPictures;
	}

	public void setShowPictures(String showPictures) {
		this.showPictures = showPictures;
	}

	public String getContentDetail() {
		return contentDetail;
	}

	public void setContentDetail(String contentDetail) {
		this.contentDetail = contentDetail;
	}

	public String getServeDetail() {
		return serveDetail;
	}

	public void setServeDetail(String serveDetail) {
		this.serveDetail = serveDetail;
	}

	public String getPackDetail() {
		return packDetail;
	}

	public void setPackDetail(String packDetail) {
		this.packDetail = packDetail;
	}

	public Boolean getOtc() {
		return otc;
	}

	public void setOtc(Boolean otc) {
		this.otc = otc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSaleType() {
		return saleType;
	}

	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Boolean getIsSpecialPrice() {
		return isSpecialPrice;
	}

	public void setIsSpecialPrice(Boolean isSpecialPrice) {
		this.isSpecialPrice = isSpecialPrice;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryIdTree() {
		return categoryIdTree;
	}

	public void setCategoryIdTree(String categoryIdTree) {
		this.categoryIdTree = categoryIdTree;
	}

	public List<DjGoodsParameter> getParamList() {
		return paramList;
	}

	public void setParamList(List<DjGoodsParameter> paramList) {
		this.paramList = paramList;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getParamValueCollect() {
		return paramValueCollect;
	}

	public void setParamValueCollect(String paramValueCollect) {
		this.paramValueCollect = paramValueCollect;
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

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
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

	public Integer getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Integer leftNumber) {
		this.leftNumber = leftNumber;
	}

	public Integer getSoldNumber() {
		return soldNumber;
	}

	public void setSoldNumber(Integer soldNumber) {
		this.soldNumber = soldNumber;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Boolean getIsFlashSale() {
		return isFlashSale;
	}

	public void setIsFlashSale(Boolean isFlashSale) {
		this.isFlashSale = isFlashSale;
	}

	public String getBrandTitle() {
		return brandTitle;
	}

	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getClick() {
		return click;
	}

	public void setClick(Long click) {
		this.click = click;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
