package com.haoyu.framework.modules.auth.utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.setting.dialect.PropsUtil;
import com.haoyu.framework.core.base.BaseConstants;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Chaos
 * @date 2013-3-19
 * @time 上午11:10:16
 */
public class AuthConstants implements BaseConstants {

    /** 启用 */
    public static final Integer ENABLE = 1;
    /** 禁用 */
    public static final Integer DISABLE = 0;

    public static final String USER_STATUS_ENABLE="01";//用户状态，有效，已存入数据字典

    public static final String USER_STAUTS_DISABLE="02";//用户状态，无效，已存入数据字典


    /** 菜单类型-页面 */
    public static final Integer RESCOURCE_PAGE = 1;
    /** 菜单类型-按钮 */
    public static final Integer RESCOURCE_BUTTON = 2;
    /** 菜单-显示 */
    public static final String RESCOURCE_SHOW_YES = "1";
    /** 菜单-不显示 */
    public static final String RESCOURCE_SHOW_NO = "0";

    /** 星号 */
    public static final String SYMBOL_STAR = "*";
    /** 邮箱符号 */
    public static final String SYMBOL_EMAIL = "@";

    /** 匿名用户 用户名 */
    public static final String ANONYMOUS_NAME = "匿名用户";

    public static final String COMMON_KEY = "Go#Up!@2Ky";


    /** 超级管理员的角色代码 */
    public static final String ROLE_CODE_ADMIN = "admin";
    /** 超级管理员的角色ID */
    public static final String ROLE_ID_ADMIN = "admin";

    /** 管理员角色Role_Code */
    public static final Integer USER_ROLECODE_ADMIN = 1;
    /** 教师角色Role_Code */
    public static final Integer USER_ROLECODE_TEACHER = 2;
    /** 专家角色Role_Code */
    public static final Integer USER_ROLECODE_PROFESSIONAL = 3;
    /** 工作室入室角色Role_Code */
    public static final Integer USER_ROLECODE_STUDIO_ROOM_MATE = 4;
    /** 既是教师也是专家角色Role_Code */
    public static final Integer USER_ROLECODE_TEACHER_PROFESSIONAL = 23;


//	public static final String CACHE_KEY_LIST_ALL_USER = StringUtils.defaultIfBlank(ConfigUtils.getAppCode(), "mis") + ":listAllUser";


    public final static String LOGIN_MESSAGE = "login_message";

    public final static String LOGIN_USER = "loginUser";

    public final static String LOGIN_TYPE_TEACHER = "teacher";
    public final static String LOGIN_TYPE_NORMAL = "normal";

    public final static String USER_ROLE_LIMIT = "loginUser.role.limit";

    /** 图片验证码 登陆     存放的Session Key */
    public static final String LOGIN_CAPTCHA_KEY = "LOGIN_CAPTCHA_KEY";
    /** 图片验证码 找回密码 存放的Session Key */
    public static final String RETRIEVE_CAPTCHA_KEY = "RETRIEVE_CAPTCHA_KEY";


    public final static String USER_DEFAULT_PASSWORD = "user.defaultPassword";

    public final static String CONTEXT_URL = "contextUrl";
    public final static String INDEX_URL = "indexUrl";
    public final static String LOGIN_URL = "loginUrl";
    public final static String LOGIN_SUBMIT_URL = "loginSubmitUrl";
    public final static String LOGOUT_URL = "logoutUrl";

    public final static String IGNORE_URL = "ignoreUrl";

    //申请人
    public static final String USER_DEFAULTUSERNAME_USER = "user.defaultUserName.user";
    //专家
    public static final String USER_DEFAULTUSERNAME_EXPERT = "user.defaultUserName.expert";
    //运维管理员
    public static final String USER_DEFAULTUSERNAME_MANAGER = "user.defaultUserName.manager";
    //管理员
    public static final String USER_DEFAULTUSERNAME_ADMIN = "user.defaultUserName.admin";
    //序列重复取值的最大次数限制，0为不限制
    public static final String SEQ_RETRYTIME = "seq.RetryTime";

    //机构类型为行政主管部门 DepartmentConstants.DEPT_TYPE_ADMIN_COMPETENT
    public final static String USER_DEFAULTUSERNAME_ADMINCOMPETENT = "user.defaultUserName.AdminCompetent";
    //机构类型为其他行政部门 DepartmentConstants.DEPT_TYPE_ADMIN_MINOR
    public final static String USER_DEFAULTUSERNAME_ADMINMINOR = "user.defaultUserName.AdminMinor";
    //机构类型为课程供应商
    public final static String USER_DEFAULTUSERNAME_SUPPLIER = "user.defaultUserName.Supplier";
    //机构类型为培训机构 DepartmentConstants.DEPT_TYPE_TRAIN_ORG
    public final static String USER_DEFAULTUSERNAME_TRAINORG = "user.defaultUserName.TrainOrg";
    //机构类型为学校 DepartmentConstants.DEPT_TYPE_SCHOOL
    public final static String USER_DEFAULTUSERNAME_SCHOOL = "user.defaultUserName.School";

    /** Redis Key JWT 在 Redis 中保存的key前缀 */
    public static final String REDIS_JWT_KEY_PREFIX = CONFIG_APP_KEY_VALUE + ":security:jwt:";
    /** Redis Key JWT 在 Redis 中保存的key前缀 */
    public static final String REDIS_LOGIN_USER_KEY_PREFIX = CONFIG_APP_KEY_VALUE + ":security:loginUser:";
    /** Redis Key 在线人员 */
    public static final String KEY_ONLINE_USER_MAP = CONFIG_APP_KEY_VALUE + ":online_user_map";
    /** Redis Key 在线人员计数 */
    public static final String KEY_ONLINE_USER_COUNT_MAP = CONFIG_APP_KEY_VALUE + ":online_user_count_map";
    /** Redis Key 在线人员Session 由于keys查询消耗太大，所以定在每分钟统计一次 */
    public static final String KEY_ONLINE_SESSION_PREFIX = CONFIG_APP_KEY_VALUE + ":online_session:";
    /** Redis Key 在线人员Session */
    public static final String KEY_ONLINE_SESSION_COUNT = CONFIG_APP_KEY_VALUE + ":online_session_count";
    /** Redis Key 用户登陆成功后 Role List */
    public static final String KEY_USER_ROLE_LIST = CONFIG_APP_KEY_VALUE + ":auth:user_role:";
    /** Redis Key 用户登陆成功后 Resource List */
    public static final String KEY_USER_RESOURCE_LIST = CONFIG_APP_KEY_VALUE + ":auth:user_resource:";
    /** Redis Key 用户登陆成功后 Menu Map */
    public static final String KEY_USER_MENU_MAP = CONFIG_APP_KEY_VALUE + ":auth:user_menu:";

    /** Redis Key 所有 Resource List */
    public static final String KEY_ALL_RESOURCE_LIST = CONFIG_APP_KEY_VALUE + ":resourceList";

    /** 系统应设置登录失败锁定账号策略，即系统账号连续输入错误口令超过3次，该账号自动锁定15分钟或以上 */
    /** 登陆失败的缓存 key-value 类型，key为userId，value为错误次数 */
    public static final String LOGIN_FAILED_KEY_PREFIX = CONFIG_APP_KEY_VALUE + ":login:failed:";
    /** 登陆失败限制次数 */
    public static final Integer LOGIN_FAILED_LIMIT = PropsUtil.get(BaseConstants.CONFIG_APP).getInt("login.failed.limit");
    /** 登陆失败后禁止登陆的时间，单位：秒 */
    public static final Integer LOGIN_FAILED_BAN_TIME = PropsUtil.get(BaseConstants.CONFIG_APP).getInt("login.failed.ban.time");
    /** 超过多久未登录系统时要锁定账号，单位：月 */
    public static final Integer LOGIN_LOCK_NOLOGIN_MONTH = PropsUtil.get(BaseConstants.CONFIG_APP).getInt("login.lock.noLogin.month");

    /** 单点登陆用的配置前缀 读取的时候需要加上后缀 */
    public static final Integer SSO_CONFIG_TIMEOUT = PropsUtil.get(BaseConstants.CONFIG_APP).getInt("sso.config.timeout");
    public static final String SSO_SYSTEM_ID_PREFIX = "sso.systemId.";
    public static final String SSO_SYSTEM_PASSWORD_PREFIX = "sso.systemPassword.";
    public static final String SSO_SYSTEM_KEY_PREFIX = "sso.systemKey.";

    public static final String PASSWORD_MD5_PREFIX = "MD5-";
    /** 数据字典KEY，存放黑名单 */
    public static final String DICT_KEY_LOGIN_ITLMS_BLACKLIST="LOGIN_ITLMS_BLACKLIST";

    /** JWT中角色代码存放KEY */
    public static final String JWT_CLAIMS_KEY_ROLE_CODE_LIST= "roleCodeList";
    /** JWT中权限代码存放KEY */
    public static final String JWT_CLAIMS_KEY_RESOURCE_CODE_LIST ="resourceCodeList";


    /** 行政机构主管（市） */
    public static final String ORGAN_ADMIN_CITY = "OrganAdminCity";
    /** 行政机构主管（区） */
    public static final String ORGAN_ADMIN_COUNTIES = "OrganAdminCounties";
    /** 行政机构主管（省） */
    public static final String ORGAN_ADMIN_PROVINCE = "OrganAdminProvince";
    /** 学校管理员*/
    public static final String ORGAN_ADMIN_SCHOOL = "SchoolAdmin";
    /** 课程机构管理员*/
    public static final String ORGAN_ADMIN_COURSE = "CourseMaker";
    /** 专家*/
    public static final String TOPIC_PROFESSIONAL = "TopicProfessional";

    public static final Map<Integer,String> ORGAN_ADMIN_LEVEL = new HashMap<Integer,String>(){{
        put(1,ORGAN_ADMIN_PROVINCE);
        put(2,ORGAN_ADMIN_CITY);
        put(3,ORGAN_ADMIN_COUNTIES);
    }};
    /** 培训机构管理员（省） */
    public static final String TRAINING_ADMIN = "TrainingAdmin";

    /** 行政机构 */
    public static final String ORG_EXECUTIVE = "01";
    /** 培训机构 */
    public static final String ORG_TRAIN = "03";
    /** 学校 */
    public static final String ORG_SCHOOL = "04";
    /** 课程机构 */
    public static final String ORG_COURSE = "06";

    /** 用户登录时验证图片宽度 */
    public static final int LOGIN_USER_CAPTCHA_IMG_WIDTH = 130;
    /** 用户登录时验证图片高度 */
    public static final int LOGIN_USER_CAPTCHA_IMG_HEIGTH = 48;
    /** 用户登录时验证图片字数数量 */
    public static final int LOGIN_USER_CAPTCHA_IMG_LENGTH = 4;
    /** 用户登录时验证图片过期时长 */
    public static final long LOGIN_USER_CAPTCHA_IMG_EXPIRE = 3;
    /** Redis Key 用户登录时验证图片在 Redis 中保存的key前缀 */
    public static final String REDIS_LOGIN_CAPTCHA_KEY_PREFIX = CONFIG_APP_KEY_VALUE + ":security:captcha:";
}
