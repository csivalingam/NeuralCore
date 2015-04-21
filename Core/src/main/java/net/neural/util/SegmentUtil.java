package net.zfp.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.zfp.dao.EntityDao;
import net.zfp.entity.User;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;

public class SegmentUtil {

	@Resource
	private static EntityDao<Segment> segmentDao;
	@Resource
	private static EntityDao<User> userDao;
	
}
