package net.zfp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.User;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.tips.Tips;
import net.zfp.entity.upload.UploadImage;
import net.zfp.service.TipService;
import net.zfp.util.AppConstants;
import net.zfp.view.ResultView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class UploadServiceImpl implements UploadService {

	private static Log LOG = LogFactory.getLog(UserServiceImpl.class.getName());
	
	@Resource
	private EntityDao<Tips> tipDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<UploadImage> uploadImageDao;
	
	
	
}
