package com.zxsm.wsc.repository.doctor;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zxsm.wsc.entity.doctor.DjDoctor;


public interface DjDoctorRepo extends PagingAndSortingRepository<DjDoctor, Long>, JpaSpecificationExecutor<DjDoctor> 
{
	
//	@Query("SELECT c.id FROM DjActivity c WHERE c.beginTime < ?1 AND c.endTime > ?2 ORDER BY c.endTime ASC")
//	List<DjDoctor> findIdBybeginTimeBeforeAndEndTimeAfter(Date date1,Date date2);
	
	DjDoctor findByUsernameAndPassword(String username,String password);
}
