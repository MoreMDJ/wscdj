package com.zxsm.wsc.common.SerchTools;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 
 * 定义一个查询条件容器
 * @author maeing
 *
 * @param <T>
 */
public class Criteria<T> implements Specification<T>
{
		private List<Criterion> criterions = new ArrayList<Criterion>();
		private String orderByAsc="";
		private String orderByDesc="";
		
		private List<String> orderByAscList = new ArrayList<String>();
		private List<String> orderByDescList = new ArrayList<String>();
		
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
		{
			if(!"".equals(orderByAsc))
			{
				query.orderBy(builder.asc(root.get(orderByAsc)));
			}
			if(!"".equals(orderByDesc))
			{
				query.orderBy(builder.desc(root.get(orderByDesc)));
			}
			for ( String orderAsc : orderByAscList)
			{
				query.orderBy(builder.asc(root.get(orderAsc)));
			}
			for (String orderDesc : orderByDescList)
			{
				query.orderBy(builder.desc(root.get(orderDesc)));
			}
			
			
			if (!criterions.isEmpty())
			{
				List<Predicate> predicates = new ArrayList<Predicate>();
				for(Criterion c : criterions)
				{
					predicates.add(c.toPredicate(root, query,builder));
				}
				// 将所有条件用 and 联合起来
				if (predicates.size() > 0)
				{
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
//					return builder.or(restrictions);
				}
			}
			return builder.conjunction();
		}
		
		/**
		 * 增加简单条件表达式
		 * 
		 */
		public void add(Criterion criterion)
		{
			if(criterion!=null)
			{
				criterions.add(criterion);
			}
		}
		
		public void add(List<Criterion> criterions)
		{
			if(criterions != null && criterions.size() > 0)
				this.criterions.addAll(criterions);
		}
		
		public void setOrderByAsc(String orderByAsc)
		{
			this.orderByAsc = orderByAsc;
		}
		public void setOrderByDesc(String orderByDesc)
		{
			this.orderByDesc = orderByDesc;
		}
		public void setOrderByAscList(List<String> orderByAscList) {
			this.orderByAscList = orderByAscList;
		}
		public void setOrderByDescList(List<String> orderByDescList) {
			this.orderByDescList = orderByDescList;
		}
		

}
