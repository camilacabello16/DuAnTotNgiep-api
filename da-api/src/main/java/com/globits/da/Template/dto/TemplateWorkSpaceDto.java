package com.globits.da.Template.dto;

import java.util.ArrayList;
import java.util.List;
import com.globits.core.dto.BaseObjectDto;
import com.globits.da.Template.domain.TemplateCard;
import com.globits.da.Template.domain.TemplateWorkSpace;
import com.globits.da.domain.Card;
import com.globits.da.domain.WorkSpace;
import com.globits.da.dto.WorkSpaceDto;
import com.globits.security.dto.UserDto;

public class TemplateWorkSpaceDto extends BaseObjectDto {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer type;
	private Integer visibility;
	private String description;
	private UserDto user;
	private Long userIdHost;
	private List<TemplateCardDto> cards;
	public TemplateWorkSpaceDto() {
		super();
	}
	
	public TemplateWorkSpaceDto(TemplateWorkSpace entity,Boolean check) {
		if(entity!=null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.type = entity.getType();
			this.visibility = entity.getVisibility();
			this.description = entity.getDescription();
			if(entity.getUser()!=null&&entity.getUser().getId()!=null) {
				this.user = new UserDto(entity.getUser());
			}
			if(entity.getTemplateCards()!=null&&entity.getTemplateCards().size()>0) {
				cards = new ArrayList<TemplateCardDto>();
				for(TemplateCard templateCard:entity.getTemplateCards()) {
					this.cards.add(new TemplateCardDto(templateCard));
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

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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


	public List<TemplateCardDto> getCards() {
		return cards;
	}

	public void setCards(List<TemplateCardDto> cards) {
		this.cards = cards;
	}

	
	
	
	
}
