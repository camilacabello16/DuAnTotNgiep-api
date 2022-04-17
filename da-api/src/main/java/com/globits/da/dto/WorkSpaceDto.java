package com.globits.da.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Card;
import com.globits.da.domain.WorkSpace;
import com.globits.da.domain.WorkSpaceUser;

public class WorkSpaceDto extends BaseObjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer type;
	private Integer visibility;
	private String description;
	private List<WorkSpaceUserDto> workSpaceUsers;
	private List<WorkSpaceDto> childs;
	private WorkSpaceDto parent;
	private Long userIdHost;
	private UUID idTemplateWorkSpace;
	private List<CardDto>cards ;
	public WorkSpaceDto() {}
	public WorkSpaceDto(WorkSpace entity) {
		this(entity, true);
	}
	public WorkSpaceDto(WorkSpace entity,boolean check) {
		if(entity!=null) {
			this.id = entity.getId();
			this.createDate = entity.getCreateDate();
			this.createdBy = entity.getCreatedBy();
			this.modifyDate = entity.getModifyDate();
			this.modifiedBy = entity.getModifiedBy();
			this.name = entity.getName();
			this.type = entity.getType();
			this.visibility = entity.getVisibility();
			this.description = entity.getDescription();
			if(entity.getWorkSpaceUser()!=null&&entity.getWorkSpaceUser().size()>0&&check) {
				workSpaceUsers = new ArrayList<>();
				for(WorkSpaceUser workSpaceUser:entity.getWorkSpaceUser()) {
					this.workSpaceUsers.add(new WorkSpaceUserDto(workSpaceUser,false));
				}
			}
			if(entity.getChilds()!=null&&entity.getChilds().size()>0&&check) {
				childs = new ArrayList<>();
				for(WorkSpace workSpace:entity.getChilds()) {
					this.childs.add(new WorkSpaceDto(workSpace,false));
				}
			}
			if(entity.getParent()!=null&&entity.getParent().getId()!=null&&check) {
				this.parent = new WorkSpaceDto(entity.getParent(),false);
			}
			if(entity.getCards()!=null&&entity.getCards().size()>0) {
				cards =new ArrayList<CardDto>();
				for(Card card:entity.getCards()) {
					this.cards.add(new CardDto(card,false));
				}
			}
		}
	}
	
	
	public Long getUserIdHost() {
		return userIdHost;
	}
	public void setUserIdHost(Long userIdHost) {
		this.userIdHost = userIdHost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<WorkSpaceUserDto> getWorkSpaceUsers() {
		return workSpaceUsers;
	}
	public void setWorkSpaceUsers(List<WorkSpaceUserDto> workSpaceUsers) {
		this.workSpaceUsers = workSpaceUsers;
	}
	public List<WorkSpaceDto> getChilds() {
		return childs;
	}
	public void setChilds(List<WorkSpaceDto> childs) {
		this.childs = childs;
	}
	public WorkSpaceDto getParent() {
		return parent;
	}
	public void setParent(WorkSpaceDto parent) {
		this.parent = parent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UUID getIdTemplateWorkSpace() {
		return idTemplateWorkSpace;
	}
	public void setIdTemplateWorkSpace(UUID idTemplateWorkSpace) {
		this.idTemplateWorkSpace = idTemplateWorkSpace;
	}
	public List<CardDto> getCards() {
		return cards;
	}
	public void setCards(List<CardDto> cards) {
		this.cards = cards;
	}
	
	
	

}
