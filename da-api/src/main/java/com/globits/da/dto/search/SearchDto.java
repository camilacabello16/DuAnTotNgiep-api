package com.globits.da.dto.search;

import java.util.Date;
import java.util.UUID;

public class SearchDto {
	private UUID id;
	private int pageIndex;
	private int pageSize;
	private String keyword;
	private Boolean voided;
	private UUID cardId;
	private String orderBy;
	private String text;
	private UUID workSpaceId;
	private Date fromDate;
	private Date toDate;
	private Long userId;
	
	public UUID getCardId() {
		return cardId;
	}
	public void setCardId(UUID cardId) {
		this.cardId = cardId;
	}
	public UUID getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(UUID workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Boolean getVoided() {
		return voided;
	}
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
