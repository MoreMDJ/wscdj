package com.zxsm.wsc.repository.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.order.DjShop;

/**
 * BmBrand 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface DjShopRepo extends
		PagingAndSortingRepository<DjShop, Long>,
		JpaSpecificationExecutor<DjShop> 
{
	Page<DjShop> findByProvinceContainingAndCityContainingAndDistrictContaining(String province, String city, String district, Pageable page);
	Page<DjShop> findByTitleContainingAndProvinceContainingAndCityContainingAndDistrictContaining(String keywords, String province, String city, String district, Pageable page);
	Page<DjShop> findById(Long id, Pageable page);
	List<DjShop> findByStatus(Integer status);
	
	List<DjShop> findByUsernameOrderByIdDesc(String username);

	List<DjShop> findByProvinceContainingAndCityContainingAndDistrictContaining(String province, String city, String district, Sort sort);
}
