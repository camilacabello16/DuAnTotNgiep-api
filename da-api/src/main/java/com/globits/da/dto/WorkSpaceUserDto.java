package com.globits.da.dto;



import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.WorkSpace;
import com.globits.da.domain.WorkSpaceUser;
import com.globits.security.dto.UserDto;

public class WorkSpaceUserDto extends BaseObjectDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDto user;
	private WorkSpaceDto workSpace;
	private String role;
	private Integer status;
	
	public WorkSpaceUserDto() {}
	public WorkSpaceUserDto(WorkSpaceUser entity) {
		this(entity, true);
	}
	public WorkSpaceUserDto(WorkSpaceUser entity,boolean check) {
		if(entity!=null) {
			this.id = entity.getId();
			this.createDate = entity.getCreateDate();
			this.createdBy = entity.getCreatedBy();
			this.modifyDate = entity.getModifyDate();
			this.modifiedBy = entity.getModifiedBy();
			if(entity.getUser()!=null&&entity.getUser().getId()!=null) {
				this.user = new UserDto(entity.getUser());
			}
			if(entity.getWorkSpace()!=null&&entity.getWorkSpace().getId()!=null&&check) {
				this.workSpace = new WorkSpaceDto(entity.getWorkSpace(),true);
			}
			this.role = entity.getRole();
			this.status = entity.getStatus();
		}
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public WorkSpaceDto getWorkSpace() {
		return workSpace;
	}
	public void setWorkSpace(WorkSpaceDto workSpace) {
		this.workSpace = workSpace;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
