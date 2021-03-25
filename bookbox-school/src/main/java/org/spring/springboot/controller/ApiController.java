package org.spring.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.spring.springboot.ao.ActivityAO;
import org.spring.springboot.ao.AppVersionAO;
import org.spring.springboot.ao.BookAO;
import org.spring.springboot.ao.CardAO;
import org.spring.springboot.ao.ChildrenAO;
import org.spring.springboot.ao.DeviceAO;
import org.spring.springboot.ao.SchoolAO;
import org.spring.springboot.util.WebUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amall.books.commons.domain.ZzChildrenVO;
import com.amall.books.commons.domain.ZzUsersVO;
import com.amall.commons.page.Page;
import com.amall.commons.result.Result;

@RestController
@RequestMapping("api")
public class ApiController {

	@Resource
	private BookAO bookAO;
	@Resource
	private DeviceAO deviceAO;
	@Resource
	private CardAO cardAO;
	@Resource
	private AppVersionAO appVersionAO;
	@Resource
	private ChildrenAO childrenAO;
	@Resource
	private ActivityAO activityAO;
	@Resource
	private SchoolAO schoolAO;
	
	@Resource
	private HttpServletRequest request;
	
	private Page page = null;
	
	// 根据班级编号获取基本信息
	@RequestMapping(value = "device/message/{gradeCode}", method = { RequestMethod.GET })
	public Result devMessage(@PathVariable String gradeCode) {
		return deviceAO.devMessage(gradeCode);
	}
	
	// 基本配置
	@RequestMapping(value = "device/register", method = { RequestMethod.POST })
	public Result deviceRegister(String serial, String gradeCode) {
		return deviceAO.register(serial, gradeCode);
	}
	
	// 上下架书籍（type:1-上架，2-下架, udType:1-图书，2-漂流）
	@RequestMapping(value = "book/ud", method = RequestMethod.POST)
	public Result bookUpDown(String gradeCode, String isbn, int type, int udType, String location) {
		return bookAO.bookUpDown(gradeCode, isbn, type, udType, location);
	}
	
	// 借还书操作
	@RequestMapping(value = "book/operate", method = RequestMethod.POST)
	public Result bookOperate(String phcode, String isbn, int type, String gmtOperate) {
		return bookAO.operate(phcode, isbn, type, gmtOperate);
	}
	
	// 数据同步
	@RequestMapping(value = "card/syn/{gradeCode}", method = RequestMethod.GET)
	public Result synCard(@PathVariable String gradeCode) {
		return cardAO.synCard(gradeCode);
	}
	
	// 版本查询
	@RequestMapping(value = "app/version/{identification}", method = { RequestMethod.GET, RequestMethod.POST })
	public Result productDetail(@PathVariable String identification) {
		return appVersionAO.getByIdentification(identification);
	}
	
	// 根据不同类型查询首页书籍列表（热门、最新）
	@RequestMapping(value = "page/book/{type}", method = { RequestMethod.GET, RequestMethod.POST })
	public Result pageBook(@PathVariable int type) {
		return bookAO.pageBook(type);
	}
	
	// 点击热门书籍   或  最新书籍菜单获取列表
	@RequestMapping(value = "bookHotOrNew", method = { RequestMethod.GET, RequestMethod.POST })
	public Result bookHotOrNew(int type, Integer curPage, Integer pageSize) {
		page = new Page(curPage,pageSize);
		return bookAO.bookHotOrNew(type, page);
	}
	
	// 小孩新增
	@RequestMapping(value = "addChildren", method = { RequestMethod.GET, RequestMethod.POST })
	public Result addChildren(ZzChildrenVO childrenVO) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		childrenVO.setUserId(usersVO.getId());
		return childrenAO.add(childrenVO);
	}
	
	// 小孩修改
	@RequestMapping(value = "editChildren", method = { RequestMethod.GET, RequestMethod.POST })
	public Result editChildren(ZzChildrenVO childrenVO) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		childrenVO.setUserId(usersVO.getId());
		return childrenAO.update(childrenVO);
	}
	
	// 借还记录
	@RequestMapping(value = "bookLogList", method = { RequestMethod.GET, RequestMethod.POST })
	public Result bookLogList(Integer curPage, Integer pageSize) {
		page = new Page(curPage,pageSize);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		Result result = bookAO.bookLogList(usersVO.getId(), page);
		return result;
	}
	
	// 读书存档
	@RequestMapping(value = "qrcode/keep/{isbn}", method = { RequestMethod.GET, RequestMethod.POST })
	public Result qrcodeKeep(@PathVariable String isbn) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return bookAO.qrcodeKeep(usersVO.getId(), isbn);
	}
	
	// 我的书架
	@RequestMapping(value = "mybook", method = { RequestMethod.GET, RequestMethod.POST })
	public Result mybook(Integer curPage, Integer pageSize) {
		page = new Page(curPage, pageSize);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		Result result = bookAO.mybook(usersVO.getId(), page);
		return result;
	}
	
	// 首页书籍搜索(模糊)
	@RequestMapping(value = "search/book", method = { RequestMethod.GET, RequestMethod.POST })
	public Result searchBook(String name) {
		return bookAO.getByBookName(name);
	}
	
	// 首页书籍搜索(非模糊)
	@RequestMapping(value = "search/fullbook", method = { RequestMethod.GET, RequestMethod.POST })
	public Result searchFullBook(String name) {
		return bookAO.getByBookFullName(name);
	}
	
	// 新增书籍思维导图
	@RequestMapping(value = "addMindMap", method = { RequestMethod.GET, RequestMethod.POST })
	public Result addMindMap(String data, String bookId) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return childrenAO.addMindMap(usersVO, data, bookId);
	}
	
	// 查询书籍在这个班级的思维导图数据
	@RequestMapping(value = "findUserByClassMindMap", method = { RequestMethod.GET, RequestMethod.POST })
	public Result findUserByClassMindMap(String bookId) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return childrenAO.findUserByClassMindMap(usersVO, bookId);
	}
	
	// 新增书籍读后感
	@RequestMapping(value = "addReadFeel", method = { RequestMethod.GET, RequestMethod.POST })
	public Result addReadFeel(String bookId, String content) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return childrenAO.addReadFeel(usersVO, content, bookId);
	}
	
	// 查询书籍在这个班级的所有读后感
	@RequestMapping(value = "readFeelList", method = { RequestMethod.GET, RequestMethod.POST })
	public Result readFeelList(String bookId) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return childrenAO.readFeelList(usersVO, bookId);
	}
	
	// 删除自己的读后感
	@RequestMapping(value = "myReadFeelDel", method = { RequestMethod.GET, RequestMethod.POST })
	public Result myReadFeelDel(String id) {
		return childrenAO.myReadFeelDel(id);
	}
	
	// 统计班级借阅排行榜
	@RequestMapping(value = "getByGradeBorrowBook", method = { RequestMethod.GET, RequestMethod.POST })
	public Result getByGradeBorrowBook(String gradeId) {
		return childrenAO.getByGradeBorrowBook(gradeId);
	}
	
	// 创建班级共读一本书活动
	@RequestMapping(value = "addTogeter", method = { RequestMethod.GET, RequestMethod.POST })
	public Result addTogeter(String id, String name, String author, String intro) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return activityAO.addTogeter(usersVO.getId(), usersVO.getChildrenVO().getGradeId(), id, name, author, intro);
	}
	
	// 关闭班级共读一本书活动
	@RequestMapping(value = "updateTogeter", method = { RequestMethod.GET, RequestMethod.POST })
	public Result updateTogeter() {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return activityAO.updateTogether(usersVO.getChildrenVO().getGradeId());
	}
	
	// 普通用户 - 查询班级共读一本书活动
	@RequestMapping(value = "findTogether", method = { RequestMethod.GET, RequestMethod.POST })
	public Result findTogeter(String gradeId) {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return activityAO.findTogether(usersVO.getChildrenVO().getGradeId());
	}
	
	// 管理员 - 查询班级共读一本书活动
	@RequestMapping(value = "adminFindTogether", method = { RequestMethod.GET, RequestMethod.POST })
	public Result adminFindTogeter(String gradeId) {
		return activityAO.findTogether(gradeId);
	}
	
	// 生成二维码卡，用于设备扫码登陆
	@RequestMapping(value = "device/login/qrcode", method = { RequestMethod.POST })
	public Result deviceLoginQrcode() {
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		return deviceAO.appQrcode(usersVO.getChildrenVO().getGradeCode());
	} 
	
	// 上下架书籍图书清单
	@RequestMapping(value = "grade/book/ud", method = { RequestMethod.GET, RequestMethod.POST })
	public Result gradeBookUd(Integer curPage, Integer pageSize) {
		page = new Page(curPage, pageSize);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		Result result = bookAO.gradeBookUd(usersVO.getChildrenVO().getGradeCode(), page);
		return result;
	}
	
	// 上下架书籍漂流清单
	@RequestMapping(value = "grade/drift/ud", method = { RequestMethod.GET, RequestMethod.POST })
	public Result gradeDriftUd(Integer curPage, Integer pageSize) {
		page = new Page(curPage, pageSize);
		ZzUsersVO usersVO = WebUtils.getUsers(request);
		Result result = bookAO.gradeDriftUd(usersVO.getChildrenVO().getGradeCode(), page);
		return result;
	}
	
	@RequestMapping(value = "school/admin/grade", method = { RequestMethod.GET, RequestMethod.POST })
	public Result schoolAdminGrade(String schoolId) {
		return schoolAO.getByGradeList(schoolId);
	}
	
	// 管理界面登录（输入密码）
//	@RequestMapping(value = "app/login", method = { RequestMethod.POST })
//	public Result appLogin(String serial, String pwd) {
//		return deviceAO.appLogin(serial, pwd);
//	}
	
	// 密码配置修改
//	@RequestMapping(value = "app/pwd/modify", method = { RequestMethod.POST })
//	public Result pwdModify(String serial, String pwd) {
//		return deviceAO.appLogin(serial, pwd);
//	}
	
	// 获取微信登陆二维码地址
//	@RequestMapping(value = "device/getQRCodeUrl", method = RequestMethod.POST)
//	public Result getQRCodeUrl() {
//		return deviceAO.getQRCodeUrl();
//	}
	
	// 根据查询书籍
//	@RequestMapping(value = "book/{schoolCode}/{isbn}", method = RequestMethod.GET)
//	public Result index(@PathVariable String schoolCode, @PathVariable String isbn) {
//		return bookAO.getByIsbn(schoolCode, isbn);
//	}
	
}
