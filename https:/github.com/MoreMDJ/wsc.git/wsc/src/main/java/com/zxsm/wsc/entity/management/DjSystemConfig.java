package com.zxsm.wsc.entity.management;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zxsm.wsc.entity.common.DjBaseEntity;

@Entity
public class DjSystemConfig extends DjBaseEntity
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	// 1 微信 2 分销
	@Column
    private Integer configType;

	@Column(length = 50)
    private String configKey;

	@Column(length = 512)
    private String configValue;

	@Column
    private Byte dataType;

	@Column
    private Date updateTime;

	@Column
    private Long updateBy;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public Byte getDataType() {
        return dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public DjSystemConfig() {
		super();
	}

	public DjSystemConfig(Integer configType, String configKey, String configValue, Byte dataType, Date updateTime,
			Long updateBy) {
		super();
		this.configType = configType;
		this.configKey = configKey;
		this.configValue = configValue;
		this.dataType = dataType;
		this.updateTime = updateTime;
		this.updateBy = updateBy;
	}
	
}