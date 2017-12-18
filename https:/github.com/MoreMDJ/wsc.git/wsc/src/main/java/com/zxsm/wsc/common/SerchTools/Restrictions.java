package com.zxsm.wsc.common.SerchTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.util.StringUtils;

import com.zxsm.wsc.common.SerchTools.Criterion.Operator;

/**
 * 条件构造器
 * 用于创建条件表达式
 * @Class Name Restrictions
 * @Author maeing
 */
public class Restrictions
{
	
	public static List<Criterion> initCriterion(List<Search> searchs)
	{
		List<Criterion> cris = new ArrayList<>();
		
		if(searchs == null || searchs.size() < 1)
			return null;
		List<Criterion> orCris = new ArrayList<>();
		
		Iterator<Search> it = searchs.iterator();
		//移除Or 添加到OrSearch
	    while (it.hasNext())
	    {
	    	Search search = (Search) it.next();
	    	
	    	if (search.getOperator() == Criterion.Operator.OR)
	    	{
	    		orCris.add(Restrictions.initCriterion(search));
	    		it.remove();
	    	}
	    }
	    if(orCris != null && orCris.size() > 0)
	    	cris.add(
	    			Restrictions.or(
	    					orCris.toArray(
	    							new Criterion[orCris.size()]
	    							)
	    					)
	    			);
	    
	    Iterator<Search> it1 = searchs.iterator();
	    
	    while (it1.hasNext())
	    {
	    	Search search = (Search) it1.next();
	    	cris.add(Restrictions.initCriterion(search));
	    }
		return cris;
	}
	

		/**
		 * 等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))
				return null;
			return new SimpleExpression (fieldName, value, Operator.EQ);
		}
		
		/**
		 * 不等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.NE);
		}

		/**
		 * 模糊匹配
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression like(String fieldName, String value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.LIKE);
		}

		/**
		 * 
		 * @param fieldName
		 * @param value
		 * @param matchMode
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression like(String fieldName, String value, MatchMode matchMode, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return null;
		}

		/**
		 * 大于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.GT);
		}

		/**
		 * 小于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.LT);
		}

		/**
		 * 大于等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.GTE);
		}

		/**
		 * 小于等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.LTE);
		}
		
		/**
		 * 并且
		 * @param criterions
		 * @return
		 */
		public static LogicalExpression and(Criterion... criterions)
		{
			return new LogicalExpression(criterions, Operator.AND);
		}
		/**
		 * 或者
		 * @param criterions
		 * @return
		 */
		public static LogicalExpression or(Criterion... criterions)
		{
			return new LogicalExpression(criterions, Operator.OR);
		}
		/**
		 * 包含于
		 * @param fieldName
		 * @param value
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull)
		{
			if(ignoreNull&&(value==null||value.isEmpty()))
			{
				return null;
			}
			SimpleExpression[] ses = new SimpleExpression[value.size()];
			int i=0;
			for(Object obj : value)
			{
				ses[i]=new SimpleExpression(fieldName,obj,Operator.EQ);
				i++;
			}
			return new LogicalExpression(ses,Operator.OR);
		}
		/**
		 * 为空
		 * @param fieldName 字段名
		 * @return
		 */
		public static SimpleExpression isNull(String fieldName)
		{
			return new SimpleExpression (fieldName, null, Operator.NULL);
		}
		/**
		 * 不为空
		 * @param fieldName 字段名
		 * @return
		 */
		public static SimpleExpression isNotNull(String fieldName)
		{
			return new SimpleExpression (fieldName, null, Operator.NOTNULL);
		}
		
		/**
		 * 模糊匹配不等
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression notLike(String fieldName, String value, boolean ignoreNull)
		{
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.NLIKE);
		}
		
		public static Criterion initCriterion(Search search)
		{
			switch (search.getExpression())
			{
			case EQ:
				return Restrictions.eq(search.getParameter(), search.getSearchValue(), true);
			case NE:
				return Restrictions.ne(search.getParameter(), search.getSearchValue(), true);
			case LIKE:
				return Restrictions.like(search.getParameter(), (String)search.getSearchValue(), true);
			case LT:
				return Restrictions.lt(search.getParameter(), search.getSearchValue(), true);
			case GT:
				return Restrictions.gt(search.getParameter(), search.getSearchValue(), true);
			case LTE:
				return Restrictions.lte(search.getParameter(), search.getSearchValue(), true);
			case GTE:
				return Restrictions.gte(search.getParameter(), search.getSearchValue(), true);
			case NULL:
				return Restrictions.isNull(search.getParameter());
			case NOTNULL:
				return Restrictions.isNotNull(search.getParameter());
			case NLIKE:
				return Restrictions.notLike(search.getParameter(), (String)search.getSearchValue(), true);
			default:
				return null;
			}
		}
}
