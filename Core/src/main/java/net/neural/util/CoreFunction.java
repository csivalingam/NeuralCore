package net.zfp.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.entity.User;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;

@Resource
public class CoreFunction {
	
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Segment> segmentDao;
	
}
