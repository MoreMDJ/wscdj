package com.zxsm.wsc.entity.doctor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.zxsm.wsc.entity.common.DjBaseEntity;

@Entity
public class DjDoctor extends DjBaseEntity
{
	public static final String sUsername="username";
	public static final String sStatus = "status";
	public static final String sName = "name";
	public static final String sDiseaseKwd= "diseaseKwd";
	public static final String sIsOnline="isOnline";
	public static final String sCat = "cat";
	public static final String sType = "type";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 账号
	@Column(length = 20)
	private String username;
	
	// 密码
	@Column(length = 50)
	private String password;
	
	// 状态：禁用，启用
	@Column
	private Integer status;
	
	// 姓名
	@Column(length = 20)
	private String name;
	
	// 医院
	@Column(length = 100)
	private String hospital;
	
	// 科室分类
	@Column
	private String cat;
	
	// 是否在线
	@Column
	private Boolean isOnline;
	
	// 是否首页推荐
	@Column
	private Integer type;
	
	// 头像
	@Column(length = 100)
	private String headImgUrl;
	
	// 工作qq
	@Column(length = 20)
	private String qq;
	
	// 以服务人数
	@Column
	private Integer servedNo;
	
	// 当前服务人数
	@Column
	private Integer serveNo;
	
	// 疾病关键字
	@Column
	private String diseaseKwd;
	
	// 擅长
	@Column(length = 200)
	private String speciality;
	
	// 医学教育背景
	@Column(length = 200)
	private String school;
	
	// 学术研究成果
	@Column(length = 200)
	private String research;
	
	// 医生寄语
	@Column(length = 200)
	private String motto;
	
	// 证书
	@Column(length = 200)
	@Lob
	private String cer;
	
	// 排序号
	@Column
	private Long sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getServedNo() {
		return servedNo;
	}

	public void setServedNo(Integer servedNo) {
		this.servedNo = servedNo;
	}

	public Integer getServeNo() {
		return serveNo;
	}

	public void setServeNo(Integer serveNo) {
		this.serveNo = serveNo;
	}

	public String getDiseaseKwd() {
		return diseaseKwd;
	}

	public void setDiseaseKwd(String diseaseKwd) {
		this.diseaseKwd = diseaseKwd;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getResearch() {
		return research;
	}

	public void setResearch(String research) {
		this.research = research;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getCer() {
		return cer;
	}

	public void setCer(String cer) {
		this.cer = cer;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

}
