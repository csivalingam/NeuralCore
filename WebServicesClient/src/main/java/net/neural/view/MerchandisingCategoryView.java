package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.category.MerchandisingCategory;

@XmlRootElement(name="MerchandisingCategory")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class MerchandisingCategoryView {
	
	@XmlElement
	private Integer id;
	@XmlElement
	private String code;
	@XmlElement
	private String name;
	
	public MerchandisingCategoryView(){}
	
	public MerchandisingCategoryView(MerchandisingCategory merchandisingCategory)
	{
		if (merchandisingCategory != null){
			this.id = merchandisingCategory.getId();
			this.code = merchandisingCategory.getCode();
			this.name = merchandisingCategory.getName();
		}
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
