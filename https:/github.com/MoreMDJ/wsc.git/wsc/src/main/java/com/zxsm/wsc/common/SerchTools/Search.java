package com.zxsm.wsc.common.SerchTools;

import com.zxsm.wsc.common.SerchTools.Criterion.Operator;

public class Search
{
	private Operator operator;//条件:and or
	
	private String parameter;//匹配的参数
	
	private Object searchValue;//参数对应的值
	
	private Operator expression;//表达式

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Object getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(Object searchValue) {
		this.searchValue = searchValue;
	}

	public Operator getExpression() {
		return expression;
	}

	public void setExpression(Operator expression) {
		this.expression = expression;
	}

	public Search(Operator operator, String parameter, Object searchValue, Operator expression) {
		super();
		this.operator = operator;
		this.parameter = parameter;
		this.searchValue = searchValue;
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Search [operator=" + operator + ", parameter=" + parameter + ", searchValue=" + searchValue
				+ ", expression=" + expression + "]";
	}
	
}
