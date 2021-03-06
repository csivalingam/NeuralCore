package net.zfp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AppConstants {
	
	public static final int FAILURE = -1;
	public static final int SUCCESS = 0;
	public static final int NO_USER_FOUND = 1;
	public static final int WRONG_PASSWORD = 2;
	public static final int USER_ALREADY_EXISTS = 3;
	public static final int UNKNOWN_ERROR = 4;
	public static final int SUCCESS_ADD_NEW_USER = 5;

	public static final int FACEBOOK_ACCOUNT = 6;
	public static final int VELO_ACCOUNT = 7;
	
	public static final int NO_COMMUNITY_FOUND = 8;
	public static final int SEND_NOTIFY_EMAIL_FAILURE = 9;
	
	public static final int NO_SOURCE_FOUND = 10;
	
	public static final int RESET_PASSWORD_EXPIRE = 11;
	public static final int RESET_PASSWORD_WRONG_CODE = 12;

	public static final int MEDIA_STREAM_NOT_FOUNT = 22;
	public static final int MEDIA_STREAM_EMPTY_SCREEN_LIST = 23;
	
	public static final String COMMUNITY_TYPE_CONSUMER = "CONSUMER";
	public static final String COMMUNITY_TYPE_CORPORATE = "CORPORATE";
	
	public static final String MASK_SALT = "PfV3oQcCeOaU3VjGaZvO1gxPBBskP1kB";
	
	public static final String DEFAULTBACKGROUNDIMAGEURL = "/images/responsive_build/runner.jpg";
	public static final String DEFAULTBACKGROUNDBLURREDIMAGEURL = "/images/responsive_build/runner_blurred.jpg";
	public static final String MOBILEDEFAULTBACKGROUNDIMAGEURL = "/images/responsive_build/runner_mobile.jpg";
	public static final String MOBILEDEFAULTBACKGROUNDBLURREDIMAGEURL = "/images/responsive_build/runner_blurred_mobile.jpg";
	
	public static final String DEFAULT_SLOGAN = "Do good for yourself and your community. Get GOODcoins, and spend them on products that do good.";
	public static final String DEFAULT_SLOGAN_COLOR = "#FFFFFF";
	
	public static final String WEB_SERVICE_REPLY_MISSING_PARAMETER = "Missing parameter(s)";
	public static final String WEB_SERVICE_REPLY_MISSING_MEMBER = "Member does not exist";
	
	//public static final String APACHE_IMAGE_WEB_LINK = "http://192.154.29.70:9090";
	//public static final String APACHE_IMAGE_MOBILE_LINK = "http://192.154.29.70:9090";
	
	public static final String EXPERIAN_EMAIL_CHECK_URL = "https://api.experianmarketingservices.com/query/EmailValidate/1.0/";
	public static final String EXPERIAL_TOKEN = "919c39a4-8050-40e8-3028-9ee3ef12359e";
	
	public static final String APACHE_IMAGE_LINK = "https://resources.goodcoins.ca";
	
	public static final String ACTIVITY_TYPE_INVITE = "INVITE";
	
	public static final String NO_IMAGE_WHATS_UP = "/images/rewards/icons/alert.png";
	
	public static final String STATUS_CODE_PENDING_APPROVAL = "PENDING_APPROVAL";
	public static final String STATUS_CODE_FRIENDS_REQUESTED = "FRIENDS_REQUESTED";
	public static final String STATUS_CODE_FRIENDS_DECLINED = "FRIENDS_DECLINED";
	public static final String STATUS_CODE_APPROVED = "APPROVED";
	public static final String STATUS_CODE_ACTIVE = "ACTIVE";
	public static final String STATUS_CODE_DEACTIVATED = "DEACTIVATED";
	public static final String STATUS_CODE_SPONSORSHIP_REQUESTED = "SPONSORSHIP_REQUESTED";
	public static final String STATUS_CODE_SPONSORSHIP_REQUESTED_FAILED = "SPONSORSHIP_REQUESTED_FAILED";
	public static final String STATUS_CODE_SPONSORED = "SPONSORED";
	
	public static final String UNIT_COST = "COST";
	public static final String UNIT_DOLLAR = "DOLLAR";
	public static final String UNIT_CONSUMPTION = "CONSUMPTION";
	public static final String UNIT_STEPS = "STEPS";
	public static final String UNIT_MINUTE = "MINUTE";
	public static final String UNIT_METER = "METER";
	public static final String UNIT_CALORIES = "CALORIES";
	public static final String UNIT_KILOMETER = "KILOMETER";
	public static final String UNIT_FUEL_EFFICIENCY = "FUEL_EFFICIENCY";
	public static final String UNIT_DISTANCE = "DISTANCE";
	public static final String UNIT_HOUR = "HOUR";
	public static final String UNIT_LITER = "LITER";
	public static final String UNIT_PERCENTAGE = "PERCENTAGE";
	public static final String UNIT_KILOWATTHOUR = "KILOWATTHOUR";
	
	public static final int PERSONAL_FOOT_PRINT_STEP_1 = 51;
	public static final int PERSONAL_FOOT_PRINT_STEP_2 = 52;
	public static final int PERSONAL_FOOT_PRINT_STEP_3 = 53;
	
	public static final String ACTIVITY_TRACKER_TYPE_STEPS = "STEPS";
	public static final String ACTIVITY_TRACKER_TYPE_DURATION = "DURATION";
	public static final String ACTIVITY_TRACKER_TYPE_DISTANCE = "DISTANCE";
	public static final String ACTIVITY_TRACKER_TYPE_CALORIES = "CALORIES";

	public static final String BASELINE_TYPE_MEDIUM = "MEDIUM";
	public static final String BASELINE_TYPE_HIGH = "HIGH";
	
	public static final String BEHAVIOR_ACTION_BUY = "BUY";
	public static final String BEHAVIOR_ACTION_SHARE = "SHARE";
	public static final String BEHAVIOR_ACTION_CONNECT = "CONNECT";
	public static final String BEHAVIOR_ACTION_COMPLETE = "COMPLETE";
	public static final String BEHAVIOR_ACTION_COMPLETE_GOAL = "COMPLETE_GOAL";
	public static final String BEHAVIOR_ACTION_UPLOAD = "UPLOAD";
	public static final String BEHAVIOR_ACTION_DOWNLOAD = "DOWNLOAD";
	public static final String BEHAVIOR_ACTION_JOIN = "JOIN";
	public static final String BEHAVIOR_ACTION_WIN_MOST = "WIN_MOST";
	public static final String BEHAVIOR_ACTION_WIN_LEAST = "WIN_LEAST";
	public static final String BEHAVIOR_ACTION_INVITE = "INVITE";
	public static final String BEHAVIOR_ACTION_SIGN_IN = "SIGN_IN";
	public static final String BEHAVIOR_ACTION_DONATE = "DONATE";
	public static final String BEHAVIOR_ACTION_GOODWILL = "GOODWILL";
	public static final String BEHAVIOR_ACTION_PARTICIPATE = "PARTICIPATE";
	
	public static final String BEHAVIOR_TRACKERTYPE_UPC = "UPC";
	public static final String BEHAVIOR_TRACKERTYPE_RECEIPT_IMAGE = "RECEIPT_IMAGE";
	public static final String BEHAVIOR_TRACKERTYPE_MEMBERSHIP = "MEMBERSHIP";
	public static final String BEHAVIOR_TRACKERTYPE_CAMPAIGN = "CAMPAIGN";
	public static final String BEHAVIOR_TRACKERTYPE_CONNECTOR_CODE = "CONNECTOR_CODE";
	public static final String BEHAVIOR_TRACKERTYPE_OFFER = "OFFER";
	public static final String BEHAVIOR_TRACKERTYPE_PRODUCT = "PRODUCT";
	public static final String BEHAVIOR_TRACKERTYPE_SURVEY= "SURVEY";
	public static final String BEHAVIOR_TRACKERTYPE_SALES_ORDER = "SALES_ORDER";
	public static final String BEHAVIOR_TRACKERTYPE_GOODWILL = "GOODWILL";
	
	public static final String BUSINESS_PARTNER_TYPE_ISSUANCE_SPONSOR= "ISSUANCE_SPONSOR";
	
	public static final int CATEGORY_TYPE_PERSONAL_FOOTPRINT = 1;
	public static final int CATEGORY_TYPE_CORPORATE_FOOTPRINT = 12;
	public static final int CATEGORY_TYPE_HOME = 11;
	public static final int CATEGORY_TYPE_ELECTRICITY = 13;
	public static final int CATEGORY_TYPE_DRIVING = 14;
	public static final int CATEGORY_TYPE_HEALTH = 15;
	
	public static final String CATEGORY_CODE_HOME = "HOME";
	public static final String CATEGORY_CODE_GIFT_CARD = "GIFT_CARD";
	public static final String CATEGORY_CODE_PERSONAL = "PERSONAL";
	public static final String CATEGORY_CODE_CORPORTATE = "CORPORATE";
	public static final String CATEGORY_CODE_CAR = "CAR";
	public static final String CATEGORY_CODE_ELECTRICITY = "ELECTRICITY";
	public static final String CATEGORY_CODE_DRIVING = "DRIVING";
	public static final String CATEGORY_CODE_WALKING = "WALKING";
	public static final String CATEGORY_CODE_RUNNING = "RUNNING";
	public static final String CATEGORY_CODE_CYCLING = "CYCLING";
	public static final String CATEGORY_CODE_HOUSEHOLD = "HOUSEHOLD";
	public static final String CATEGORY_CODE_CARBON = "CARBON";
	
	public static final String DEVICEATTRIBUTE_MOBILE_APP= "MOBILE_APP";
	public static final String DEVICEATTRIBUTE_CONNECTOR= "CONNECTOR";
	public static final String DEVICEATTRIBUTE_PRODUCT= "PRODUCT";
	
	public static final String PARTNERACTIVITY_TYPE_CODE_ISSUANCE= "ISSUANCE";
	
	public static final String SOURCETYPE_CODE_NONE = "NONE";
	public static final String SOURCETYPE_CODE_ELECTRICITY = "ELECTRICITY";
	public static final String SOURCETYPE_CODE_WATER = "WATER";
	public static final String SOURCETYPE_CODE_NATURAL_GAS = "NATURAL_GAS";
	
	public static final String SOURCETYPE_CODE_ELECTRICITY_IN_PERCENTAGE = "ELECTRICITY_IN_PERCENTAGE";
	public static final String SOURCETYPE_CODE_ELECTRICITY_PERCENTAGE_USAGE_DURING_OFF_PEAK = "ELECTRICITY_PERCENTAGE_USAGE_DURING_OFF_PEAK";
	public static final String SOURCETYPE_CODE_WALKING = "WALKING";
	public static final String SOURCETYPE_CODE_RUNNING = "RUNNING";
	public static final String SOURCETYPE_CODE_CYCLING = "CYCLING";
	public static final String SOURCETYPE_CODE_TIMESPENT = "TIME_SPENT";
	
	public static final String SOURCETYPE_CODE_DRIVING = "DRIVING";
	public static final String SOURCETYPE_CODE_BUS = "BUS";
	public static final String SOURCETYPE_CODE_TRAIN = "TRAIN";
	
	public static final String SOURCETYPE_CODE_PERSONAL_FOOTPRINT = "PERSONAL_FOOTPRINT";
	public static final String SOURCETYPE_CODE_CORPORATE_FOOTPRINT = "CORPORATE_FOOTPRINT";
	
	public static final String SOURCEMODE_CODE_CLUSTER = "CLUSTER";
	public static final String SOURCEMODE_CODE_VIRTUAL = "VIRTUAL";
	public static final String SOURCEMODE_CODE_METER = "METER";
	public static final String SOURCEMODE_CODE_MASTER = "MASTER";
	
	public static final String DATATYPE_CODE_COST = "COST";
	public static final String DATATYPE_CODE_USAGE = "USAGE";
	
	public static final String CAMPAIGN_TARGET_TYPE_GOAL = "GOAL";
	public static final String CAMPAIGN_TARGET_TYPE_LIMIT = "LIMIT";
	
	public static final String CAMPAIGN_TARGET_MODE_DYNAMIC = "DYNAMIC";
	
	public static final String CAMPAIGN_TYPE_GROUP = "GROUP";
	
	public static final String BEHAVIOR_ATTRIBUTE_UPC = "UPC";
	
	public static final String POINTS_TRANSACTION_TYPE_ISSUANCE = "ISSUANCE";
	public static final String POINTS_TRANSACTION_TYPE_REDEMPTION = "REDEMPTION";
	
	public static final int CATEGORYTYPE_SOURCE = 1;
	public static final int CATEGORYTYPE_PRODUCT = 2;
	public static final int CATEGORYTYPE_OFFER = 3;
	public static final int CATEGORYTYPE_CAMPAIGN = 4;
	public static final int CATEGORYTYPE_SURVEY = 5;
	
	public static final String ELECTRICITY_CONSUMPTION = "kWh";
	public static final String DOLLAR_VALUE= "$";
	
	public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";
	public static final String ROLE_COMMUNITY_ADMIN = "COMMUNITY_ADMIN";
	public static final String ROLE_USER= "USER";
	public static final String ROLE_GROUP_GUEST= "GROUP_GUEST";
	
	public static final Long FOOTPRINT_CORPORATE = 1l;
	public static final Long FOOTPRINT_PERSONAL = 2l;
	
	public static final Long TIPS_CATEGORY_ALL = 1l;
	public static final Long TIPS_CATEGORY_LIFE_STYLE = 2l;
	public static final Long TIPS_CATEGORY_HOME = 3l;
	public static final Long TIPS_CATEGORY_CORPORATE = 4l;
	
	public static final String MEDIA_SCREEN_GAUGE = "CONSUMPTION_GAUGE_SCREEN";
	public static final String MEDIA_SCREEN_TWITTER = "TWITTER_SCREEN";
	public static final String MEDIA_SCREEN_ONEBYONE = "TEXT_AND_IMAGE_SCREEN";
	public static final String MEDIA_SCREEN_IMAGECONTENT = "IMAGE_SCREEN";
	public static final String MEDIA_SCREEN_TEXTCONTENT = "TEXT_SCREEN";
	public static final String MEDIA_SCREEN_USAGE = "USAGE_SCREEN";
	public static final String MEDIA_SCREEN_LEADERBOARD = "LEADERBOARD_SCREEN";
	
	public static final String MEDIA_ATTRIBUTE_SCREEN_HEADER = "SCREEN_HEADER";
	public static final String MEDIA_ATTRIBUTE_SCREEN_IMAGE = "SCREEN_IMAGE";
	public static final String MEDIA_ATTRIBUTE_SCREEN_TEXT = "SCREEN_TEXT";
	
	public static final String MEDIA_ATTRIBUTE_SOURCE_CODE = "SOURCE_CODE";
	public static final String MEDIA_ATTRIBUTE_SOURCE_TYPE = "SOURCE_TYPE";
	public static final String MEDIA_ATTRIBUTE_DATA_HISTORY = "DATA_HISTORY";
	public static final String MEDIA_ATTRIBUTE_DISPLAY_UNITS = "DISPLAY_UNITS";
	public static final String MEDIA_ATTRIBUTE_DISPLAY_UNITS_BOOELAN = "DISPLAY_UNITS_BOOLEAN";
	public static final String MEDIA_ATTRIBUTE_EQUIVALENCY_HEADER = "SCREEN_EQUIVALENCY_HEADER";
	public static final String MEDIA_ATTRIBUTE_USAGE_START_DATE = "USAGE_START_DATE";
	public static final String MEDIA_ATTRIBUTE_TWITTER_QUERY = "TWITTER_SEARCH_QUERY";
	public static final String MEDIA_ATTRIBUTE_TWITTER_WIDGET = "TWITTER_WIDGET_ID";
	public static final String MEDIA_ATTRIBUTE_INCLUDE_WEEKENDS = "INCLUDE_WEEKENDS";
	public static final String MEDIA_ATTRIBUTE_BUILDING_COMPLEX = "BUILDING_COMPLEX";
	public static final String MEDIA_ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
	public static final String MEDIA_ATTRIBUTE_COMPARISON_MODE = "COMPARISON_MODE";
	public static final String MEDIA_ATTRIBUTE_TOP_NUMBER = "TOP_NUMBER";
	public static final String MEDIA_ATTRIBUTE_SCREEN_SUBHEADER = "SCREEN_SUBHEADER";
	public static final String MEDIA_ATTRIBUTE_INCLUDE_LOW_PERFORMERS = "INCLUDE_LOW_PERFORMERS";
	public static final String MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_SIZE = "SCREEN_SUBHEADER_FONT_SIZE";
	public static final String MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_COLOR = "SCREEN_SUBHEADER_FONT_COLOR";
	public static final String MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_FONT_FAMILY = "SCREEN_SUBHEADER_FONT_FAMILY";
	public static final String MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_FAMILY = "SCREEN_HEADER_FONT_FAMILY";
	public static final String MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_COLOR = "SCREEN_HEADER_FONT_COLOR";
	public static final String MEDIA_ATTRIBUTE_SCREEN_HEADER_FONT_SIZE = "SCREEN_HEADER_FONT_SIZE";
	public static final String MEDIA_ATTRIBUTE_SCREEN_HEADER_JUSTIFICATION = "SCREEN_HEADER_JUSTIFICATION";
	public static final String MEDIA_ATTRIBUTE_SCREEN_SUBHEADER_JUSTIFICATION = "SCREEN_SUBHEADER_JUSTIFICATION";
	public static final String MEDIA_ATTRIBUTE_BOUNDARY_BOOLEAN = "BOUNDARY_BOOLEAN";
	public static final String MEDIA_ATTRIBUTE_BOUNDARY_PERCENTAGE = "BOUNDARY_PERCENTAGE";
	public static final String MEDIA_ATTRIBUTE_COMPARISON_TYPE = "COMPARISON_TYPE";
	public static final String MEDIA_ATTRIBUTE_PERIOD_HISTORY = "PERIOD_HISTORY";
	
	public static final String MEMBERACTIVITY_TYPE_SIGNUP = "SIGN_UP";
	public static final String MEMBERACTIVITY_TYPE_SIGNIN = "SIGN_IN";
	public static final String MEMBERACTIVITY_TYPE_REDEEM = "REDEEM";
	public static final String MEMBERACTIVITY_TYPE_PURCHASE = "PURCHASE";
	public static final String MEMBERACTIVITY_TYPE_BEHAVE = "BEHAVE";
	public static final String MEMBERACTIVITY_TYPE_COMPETE = "COMPETE";
	public static final String MEMBERACTIVITY_TYPE_WIN = "WIN";
	public static final String MEMBERACTIVITY_TYPE_COMPLETE = "COMPLETE";
	public static final String MEMBERACTIVITY_TYPE_JOIN = "JOIN";
	public static final String MEMBERACTIVITY_TYPE_UPLOAD = "UPLOAD";
	public static final String MEMBERACTIVITY_TYPE_SHARE = "SHARE";
	public static final String MEMBERACTIVITY_TYPE_INVITE = "INVITE";
	public static final String MEMBERACTIVITY_TYPE_DONATE = "DONATE";
	public static final String MEMBERACTIVITY_TYPE_GOODWILL = "GOODWILL";
	public static final String MEMBERACTIVITY_TYPE_PARTICIPATE = "PARTICIPATE";
	
	public static final String NEWSCONTENT_TYPE_SOCIAL = "SOCIAL";
	public static final String NEWSCONTENT_TYPE_PROGRAM = "PROGRAM";
	public static final String NEWSCONTENT_TYPE_ALERT = "ALERT";
	
	public static final String OFFER_TYPE_MASS = "MASS";
	public static final String OFFER_TYPE_COMPETITION = "COMPETITION";
	public static final String OFFER_TYPE_SURVEY = "SURVEY";
	public static final String OFFER_TYPE_MASS_SPECIAL = "MASS_SPECIAL";
	public static final String OFFER_TYPE_TARGETTED_SPECIAL = "TARGETTED_SPECIAL";
	public static final String OFFER_TYPE_SHARE_AND_GET = "SHARE_AND_GET";
	public static final String OFFER_TYPE_BUY_AND_GET = "BUY_AND_GET";
	public static final String OFFER_TYPE_BEHAVE_AND_GET = "BEHAVE_AND_GET";
	public static final String OFFER_TYPE_INVITE_AND_GET = "INVITE_AND_GET";
	public static final String OFFER_TYPE_CONTINUITY = "CONTINUITY";
	public static final String OFFER_TYPE_REDEMPTION= "REDEMPTION";
	public static final String OFFER_TYPE_CAMPAIGN= "CAMPAIGN";
	public static final String OFFER_TYPE_SIGNUP= "SIGNUP";
	public static final String OFFER_TYPE_SIGNIN= "SIGNIN";
	
	public static final Double PERCENT_ELECTRICITY_COOLING = 0.095;
	
	public static final String PERIOD_TYPE_HOURLY= "HOURLY";
	public static final String PERIOD_TYPE_DAILY= "DAILY";
	public static final String PERIOD_TYPE_MONTHLY= "MONTHLY";
	
	public static final String PRODUCT_CARBON_FOREST = "CAR-FOREST-01";
	public static final String PRODUCT_CARBON_LANDFILL = "CAR-LANDFI-01";
	public static final String PRODUCT_CARBON_TIRE = "CAR-TIRERE-01";
	public static final String PRODUCT_CARBON_BISON = "CAR-BISONT-01";
	public static final String PRODUCT_CARBON_ORGANIC = "CAR-AIMMUN-01";
	public static final String PRODUCT_CARBON_BISOMASS = "CAR-LESSER-01";
	public static final String PRODUCT_CARBON_MUSEUM = "CAR-MUSEUM-01";
	public static final String PRODUCT_CARBON_WASTEWATER = "CAR-SHARPM-01";
	
	public static final String PRODUCTTYPE_REWARDS = "REWARDS";
	public static final String PRODUCTTYPE_CHARITY = "CHARITY";
	public static final String PRODUCTTYPE_COMMERCE = "COMMERCE";
	
	public static final String CAMPAIGN_TEMPLATE_TYPE_COMPETITION= "COMPETITION";
	public static final String CAMPAIGN_TEMPLATE_TYPE_SPONSORED= "SPONSORED_COMPETITION";
	public static final String CAMPAIGN_TEMPLATE_TYPE_PERCENTAGE_REDUCTION= "PERCENTAGE_REDUCTION";
	public static final String CAMPAIGN_TEMPLATE_TYPE_PERCENTAGE_GOAL= "PERCENTAGE_GOAL";
	
	public static final String PROVIDER_ZFP = "ZFP";
	public static final String PROVIDER_EYEDRO = "EYEDRO";
	public static final String PROVIDER_NIKEBAND = "NIKEBAND";
	public static final String PROVIDER_MOJIO = "MOJIO";
	public static final String PROVIDER_MOVES = "MOVES";
	public static final String PROVIDER_GREENBUTTON = "LONDON_HYDRO_GREENBUTTON";
	public static final String PROVIDER_NEWMARKET_HYDRO = "NEWMARKET_HYDRO";
	public static final String PROVIDER_JAWBONE = "JAWBONE";
	
	public static final String PROVIDERTYPE_ELECTRICITY = "ELECTRICITY";
	public static final String PROVIDERTYPE_PERSONAL_MOTION = "PERSONAL_MOTION";
	public static final String PROVIDERTYPE_DRIVING = "DRIVING";
	public static final String PROVIDERTYPE_NATURAL_GAS = "NATURAL_GAS";
	public static final String PROVIDERTYPE_WATER = "WATER";
	
	public static final Long PRODUCT_TYPE_CARBON_PROJECTS = 1l;
	public static final Long PRODUCT_REWARDS = 2l;
	
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_SUSPENDED= "SUSPENDED";
	public static final String STATUS_INACTIVE = "INACTIVE";
	public static final String STATUS_COMPLETED = "COMPLETED";
	public static final String STATUS_CREATED = "CREATED";
	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_CAPTURED = "CAPTURED";
	public static final String STATUS_FAILED = "FAILED";
	
	public static final String TABLE_ELECTRICITY_DATA_HOUR = "ElectricityDataHour";
	public static final String TABLE_ELECTRICITY_DATA_DAY = "ElectricityDataDay";
	public static final String TABLE_ELECTRICITY_DATA_MONTH = "ElectricityDataMonth";
	public static final String TABLE_WALKING_HOUR = "WalkingHour";
	public static final String TABLE_WALKING_DAY = "WalkingDay";
	public static final String TABLE_WALKING_MONTH = "WalkingMonth";
	public static final String TABLE_RUNNING_HOUR = "RunningDataHour";
	public static final String TABLE_RUNNING_DAY = "RunningDataDay";
	public static final String TABLE_RUNNING_MONTH = "RunningDataMonth";
	public static final String TABLE_CYCLING_HOUR = "CyclingDataHour";
	public static final String TABLE_CYCLING_DAY = "RunningDataDay";
	public static final String TABLE_CYCLING_MONTH = "RunningDataMonth";
	public static final String TABLE_DRIVING_DAY = "DrivingDay";
	public static final String TABLE_DRIVING_HOUR = "DrivingHour";
	
	public static final String REFERENCECODE_RECEIPT_UPLOAD = "RECEIPT_UPLOAD";
	public static final String REFERENCECODE_SALES_ORDER = "SALES_ORDER";
	public static final String REFERENCECODE_SIGN_UP = "SIGN_UP";
	public static final String REFERENCECODE_REDEEM = "REDEEM";
	public static final String REFERENCECODE_JOIN_CAMPAIGN = "JOIN_CAMPAIGN";
	public static final String REFERENCECODE_SHARE_LINK = "SHARE_LINK";
	public static final String REFERENCECODE_SHARE_CAMPAIGN = "SHARE_CAMPAIGN";
	public static final String REFERENCECODE_SHARE_OFFER = "SHARE_OFFER";
	public static final String REFERENCECODE_SHARE_PRODUCT = "SHARE_PRODUCT";
	public static final String REFERENCECODE_COMPLETE_CAMPAIGN = "COMPLETE_CAMPAIGN";
	public static final String REFERENCECODE_WIN_MOST_CAMPAIGN = "WIN_MOST_CAMPAIGN";
	public static final String REFERENCECODE_WIN_LEAST_CAMPAIGN = "WIN_LEAST_CAMPAIGN";
	public static final String REFERENCECODE_MEMBERSHIP = "MEMBERSHIP";
	public static final String REFERENCECODE_GOODWILL = "GOODWILL";
	public static final String REFERENCECODE_CAMPAIGN = "CAMPAIGN";
	
	public static final String REFERENCECODE_SURVEY = "SURVEY";
	
	public static final String MEMBER_PROFILE_ROOMS = "NUMBER_OF_ROOMS";
	public static final String MEMBER_PROFILE_OCCUPANTS = "NUMBER_OF_OCCUPANTS";
	
	public static final String SURVEY_TYPE_QUESTION_ONLY = "QUESTION_ONLY";
	public static final String SURVEY_TYPE_RESULTS_BASED = "RESULTS_BASED";
	
	public static final String SURVEY_ANSWER_VALUE_TYPE_TEXT = "TEXT";
	public static final String SURVEY_ANSWER_VALUE_TYPE_RANGE = "RANGE";
	public static final String SURVEY_ANSWER_VALUE_TYPE_CHOICE = "CHOICE";
	
	public static final String SURVEY_QUESTION_TYPE_TEXT = "TEXT";
	public static final String SURVEY_QUESTION_TYPE_FORMULA = "FORMULA";
	
	public static final String ELECTRICITY_HOUR_TABLE = "ElectricityDataHour";
	public static final String ELECTRICITY_DAY_TABLE = "ElectricityDataDay";
	public static final String ELECTRICITY_MONTH_TABLE = "ElectricityDataMonth";
	
	public static final String SEGMENT_ALL_MEMBERS = "ALL_MEMBERS";
	public static final String SEGMENTTYPE_ALL_COMMUNITIES = "ALL_COMMUNITIES";
	public static final String SEGMENTTYPE_COMMUNITY_DEFAULT = "COMMUNITY_DEFAULT";
	public static final String SEGMENTTYPE_COMMUNITY_CORPORATE = "CORPORATE_COMMUNITY";
	
	public static final String AUTHORITY_SUPER_ADMIN = "SUPER_ADMIN";
	public static final String AUTHORITY_COMMUNITY_ADMIN = "COMMUNITY_ADMIN";
	public static final String AUTHORITY_USER = "USER";
	public static final String AUTHORITY_COMMUNITY_GROUP_ADMIN = "COMMUNITY_GROUP_ADMIN";
	public static final String AUTHORITY_SOURCE_ADMIN = "SOURCE_ADMIN";
	public static final String AUTHORITY_GROUP_GUEST = "GROUP_GUEST";
	
	public static final String GROUPSTYPE_CAMPAIGN = "CAMPAIGN";
	public static final String GROUPSTYPE_DEFAULT = "DEFAULT";
	
	public static final String PRODUCT_JAWBONE_OAUTH = "https://jawbone.com/auth/oauth2/auth?response_type=code&client_id=CLIENTID&scope=move_read&redirect_uri=SITEURL/JawboneConnector?accountId=ACCOUNTID";
	public static final String PRODUCT_JAWBONE_CLIENT_ID = "rbVp6K4eORA";
	
	public static final String MOBILE_MOVES_EXTERNAL = "https://api.moves-app.com/oauth/v1/authorize?response_type=code&client_id=CLIENTID&scope=activity+location&redirect_uri=SITEURL/MovesConnector?accountId=ACCOUNTID";
	public static final String MOBILE_MOVES_CLIENT_ID = "9z4lZg7H7HOt5FIM020cXUkS1xctcPBU";
	public static final String TRANSACTION_REFERENCE_TYPE_CAMPAIGN = "CAMPAIGN_ID";
	public static final String TRANSACTION_REFERENCE_TYPE_ORDER = "ORDER_ID";
	
	public static final String LONDON_GREENBUTTON_URL = "https://londonhydro-espi-dev.appspot.com";
	public static final String LONDON_GREENBUTTON_CLIENT_ID = "goodcoins";
	
	public static final String TRANSACTION_TYPE_CARBON = "CARBON";
	public static final String TRANSACTION_TYPE_REDEMPTION = "REDEMPTION";
	public static final String TRANSACTION_TYPE_PURCHASE = "PURCHASE";
	public static final String TRANSACTION_TYPE_FUNDRAISING = "FUNDRAISING";
	
	public static final String CAMPAIGNTEMPLATE_MEASURE_ABSOLUTE = "ABSOLUTE";
	public static final String CAMPAIGNTEMPLATE_MEASURE_PERCENTAGE = "PERCENTAGE";
	
	public static final String OFFER_VALUE_TYPE_RATIO = "RATIO";
	public static final String OFFER_VALUE_TYPE_ABSOLUTE = "ABSOLUTE";
	
	public static final String CHANNEL_WEB = "WEB";
	public static final String CHANNEL_MOBILE = "MOBILE";
	
	public static final String MARKING_BANNER_TYPE_PRE = "PRE_LOGIN";
	public static final String MARKING_BANNER_TYPE_POST = "POST_LOGIN";
	
	public static final String POINTS_TYPE_DEFAULT = "DEFAULT";
	public static final String POINTS_TYPE_CHARITY = "CHARITY";
	
	public static final String WALLET_TYPE_ALL = "ALL";
	public static final String WALLET_TYPE_DEFAULT = "DEFAULT";
	public static final String WALLET_TYPE_CHARITY = "CHARITY";
	
	public static final String AVERAGE_TYPE_WEEKDAY = "WEEK_DAY";
	public static final String AVERAGE_TYPE_WEEKEND = "WEEKEND";
	public static final String AVERAGE_TYPE_ALL_WEEK = "ALL_WEEK";
	
	public static final String RULE_ATTRIBUTE_ATMOSPHERE_TEMPERATURE = "ATMOSPHERE_TEMPERATURE";
	public static final String RULE_ATTRIBUTE_WEEKEND = "WEEKEND";
	public static final String RULE_ATTRIBUTE_BOUNDARIES = "BOUNDARIES";
	public static final String RULE_ATTRIBUTE_START_TIME = "START_TIME";
	public static final String RULE_ATTRIBUTE_END_TIME = "END_TIME";
	
	public static final String CAMPAIGN_TEMPLATE_TYPE_DEFAULT = "Target";
	
	public static final long DAY_IN_MILLISECONDS = 24*60*60*1000l;
	public static final List<String> PRODUCT_CONNECTOR_ELECTRICITY = new ArrayList<String>(Arrays.asList("183968000012", "183968000036"));
	public static final List<String> PRODUCT_CONNECTOR_NATURAL_GAS = new ArrayList<String>(Arrays.asList("CON-BIXIBI-01"));
	public static final List<String> PRODUCT_CONNECTOR_WATER = new ArrayList<String>(Arrays.asList("CON-GLDGYM-01"));
	public static final List<String> PRODUCT_CONNECTOR_PERSONAL_MOTION = new ArrayList<String>(Arrays.asList("10004", "10005"));
	public static final List<String> PRODUCT_CONNECTOR_DRIVING = new ArrayList<String>(Arrays.asList("10003"));
}
