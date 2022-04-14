package com.globits.da.dto.search;

import java.util.UUID;

public class TaskSearchDto extends SearchDto {
	private UUID cardId;
	private UUID workSpaceId;
	private UUID userId;
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
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
	
}
