package com.globits.da.Template.dto;

import java.util.ArrayList;
import java.util.List;
import com.globits.core.dto.BaseObjectDto;
import com.globits.da.Template.domain.TemplateWorkSpace;
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
	private List<TemplateWorkSpaceDto> childs; 
	private TemplateWorkSpaceDto parent;
	private UserDto user;
	private Long userIdHost;
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
			if(entity.getChilds()!=null&&entity.getChilds().size()>0&&check) {
				childs = new ArrayList<>();
				for(TemplateWorkSpace workSpace:entity.getChilds()) {
					this.childs.add(new TemplateWorkSpaceDto(workSpace,false));
				}
			}
			if(entity.getParent()!=null&&entity.getParent().getId()!=null&&check) {
				this.parent = new TemplateWorkSpaceDto(entity.getParent(),false);
			}
			if(entity.getUser()!=null&&entity.getUser().getId()!=null) {
				this.user = new UserDto(entity.getUser());
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
	public List<TemplateWorkSpaceDto> getChilds() {
		return childs;
	}
	public void setChilds(List<TemplateWorkSpaceDto> childs) {
		this.childs = childs;
	}
	public TemplateWorkSpaceDto getParent() {
		return parent;
	}
	public void setParent(TemplateWorkSpaceDto parent) {
		this.parent = parent;
	}
	
	
	
}
