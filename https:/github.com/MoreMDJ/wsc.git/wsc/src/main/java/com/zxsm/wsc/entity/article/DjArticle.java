package com.zxsm.wsc.entity.article;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.format.annotation.DateTimeFormat;

import com.zxsm.wsc.entity.common.DjBaseEntity;


/**
 * 文章
 * 
 * @author maeing
 *
 */

@Entity
public class DjArticle extends DjBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 标题
    @Column(length = 100)
    private String title;

    // 类型ID
    @Column
    private Long categoryId;
    
    // 类型名称
    @Column(length = 100)
    private String categoryTitle;
    
    // 显示状态 1:正常 2:待审核 3:显示
    @Column
    private Integer statusId;
    
    // 类型
    @Column
    private Integer type;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 浏览次数
    @Column
    private Integer viewCount;
    
    // 浏览次数(真实)
    @Column
    private Integer click;
    
    // 标签
    @Column
    private String tags;
    
    // 封面图片
    @Column
    private String coverImgUri;
    
    // 内容摘要
    @Column(length = 250)
    private String brief;
    
    // 详细内容
    @Column
    @Lob
    private String content;
    
    // 信息来源
    @Column(length = 100)
    private String source;
    
    // 作者
    @Column(length = 50)
    private String author;
    
    // 发布时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    // 调整地址，填写后直接跳转该地址
    @Column
    private String linkUri;

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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCoverImgUri() {
		return coverImgUri;
	}

	public void setCoverImgUri(String coverImgUri) {
		this.coverImgUri = coverImgUri;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLinkUri() {
		return linkUri;
	}

	public void setLinkUri(String linkUri) {
		this.linkUri = linkUri;
	}
    
}
