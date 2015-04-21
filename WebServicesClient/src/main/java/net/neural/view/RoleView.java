package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.zfp.entity.UserRole;


@XmlRootElement(name="roles")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleView {
	
	@XmlElement
	private long roleId;
	@XmlElement
	private String roleName;
	@XmlElement
	private String roleDescription;

	public RoleView() {}
	public RoleView(UserRole r) {
		this.roleId=r.getId();
		this.roleName=r.getAuthority();
	}

	public UserRole getModel() {
		UserRole r = new UserRole();
		r.setId(roleId);
		r.setAuthority(roleName);
		return r;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
