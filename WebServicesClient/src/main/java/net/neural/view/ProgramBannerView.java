package net.zfp.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.zfp.entity.banner.ProgramBanner;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;

@XmlRootElement(name="ProgramBannerView")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramBannerView {
	
	@XmlAttribute(name="leftImage")
	private String leftImage;
	
	@XmlAttribute(name="leftTitle")
	private String leftTitle;
	
	@XmlAttribute(name="leftDescription")
	private String leftDescription;
	
	@XmlAttribute(name="rightImage")
	private String rightImage;
	
	@XmlAttribute(name="rightTitle")
	private String rightTitle;
	
	@XmlAttribute(name="rightDescription")
	private String rightDescription;
	
	public ProgramBannerView(){}
	
	public ProgramBannerView(ProgramBanner pb){
		this.leftImage = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pb.getLeftImageUrl());
		this.leftTitle = pb.getLeftTitle();
		this.leftDescription = pb.getLeftDescription();
		this.rightImage = AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pb.getRightImageUrl());
		this.rightTitle = pb.getRightTitle();
		this.rightDescription = pb.getRightDescription();
		
	}
}
