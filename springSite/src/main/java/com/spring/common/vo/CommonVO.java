package com.spring.common.vo;

public class CommonVO {
	// ���ǰ˻��� ����� �ʵ�
	private String search = "";
	private String keyword = "";
	// ���� Ŭ���� ������ ���� �ʵ�
	private String order_by;
	private String order_sc;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrder_by() {
		return order_by;
	}

	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}

	public String getOrder_sc() {
		return order_sc;
	}

	public void setOrder_sc(String order_sc) {
		this.order_sc = order_sc;
	}
}