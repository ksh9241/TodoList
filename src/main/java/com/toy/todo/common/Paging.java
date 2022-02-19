package com.toy.todo.common;

public class Paging {
	private static final int PAGE_SIZE = 5;
	
	// Between Value
	private int startNum;
	private int endNum;
	
	// input PageValue
	private int pageVal;
	
	// totalPageSize
	private int pageLen;
	
	public Paging(int pageVal) {
		endNum = pageVal * PAGE_SIZE;
		startNum = endNum - (PAGE_SIZE - 1);
		this.pageVal = pageVal;
	}
	
	public void setPageLen(int totalSize) {
		this.pageLen = (int)Math.ceil(PAGE_SIZE / totalSize);
	}
	
	
}
