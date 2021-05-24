package org.spring.springboot.ao.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.spring.springboot.ao.BookAO;
import org.spring.springboot.util.DbBook;
import org.spring.springboot.util.DdBook;
import org.spring.springboot.util.GtBook;
import org.spring.springboot.util.HttpUtils;
import org.spring.springboot.util.JdBook;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amall.admin.commons.constant.PublicConstants;
import com.amall.admin.commons.util.ArithUtils;
import com.amall.admin.commons.util.Q;
import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.NewBookService;
import com.amall.books.commons.dointerface.ZzBookLogService;
import com.amall.books.commons.domain.NewBookVO;
import com.amall.commons.page.Page;
import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultBookAO extends AbstractAO implements BookAO {

	@Resource
	private NewBookService newBookService;
	
	@Resource
	private ZzBookLogService zzBookLogService;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Override
	public Result getByBookDetail(String userId, String isbn) {
		return newBookService.getByBookDetail(userId, isbn);
	}
	
	@Override
	public Result bookUpDown(String gradeCode, String isbn, int type, int udType, String location) {
		log.info("API-上下架操作：{}, {}, {}, {}, {}", new Object[] { gradeCode, isbn, type, udType, location });
		
		Result result = new DefaultResult();
		if (Q.isEmpty(gradeCode) || Q.isEmpty(isbn) || type == 0 || 
				udType == 0 || Q.isEmpty(location)) {
			result.setErrors("A0002", getMsg("A0002"));
			return result;
		}
		if (isbn.length() < 11) {
			result.setErrors("A0004", getMsg("A0004"));
			return result;
		}
		List<Integer> typeList = Arrays.asList(PublicConstants.TypeV1, PublicConstants.TypeV2);
		if (!typeList.contains(type) || !typeList.contains(udType)) {
			result.setErrors("A0003", getMsg("A0003"));
			return result;
		}
		
		return newBookService.bookUpDown(gradeCode, isbn, type, udType, location);
	}
	
	@Override
	public Result operate(String phcode, String isbn, int type, String gmtOperate) {
		log.info("API-借还书操作：{}, {}, {}, {}", new Object[] { phcode, isbn, type, gmtOperate });
		
		Result result = new DefaultResult();
		if (Q.isEmpty(phcode) || Q.isEmpty(isbn) || type == 0 || Q.isEmpty(gmtOperate)) {
			result.setErrors("A0002", getMsg("A0002"));
			return result;
		}
		if (isbn.length() < 11) {
			result.setErrors("A0004", getMsg("A0004"));
			return result;
		}
		List<Integer> typeList = Arrays.asList(PublicConstants.Borrow, PublicConstants.Still);
		if (!typeList.contains(type)) {
			result.setErrors("A0003", getMsg("A0003"));
			return result;
		}
		if (!Q.dateBetween(gmtOperate)) {
			result.setErrors("A0005", getMsg("A0005"));
			return result;
		}
		
		return newBookService.operate(phcode, isbn, type, gmtOperate);
	}
	
	@Override
	public Result bookLogList(String userId, Page page) {
		return zzBookLogService.bookLogList(userId, page);
	}
	
	@Override
	public Result qrcodeKeep(String userId, String isbn) {
		return newBookService.addUserKeep(userId, isbn);
	}
	
	@Override
	public Result mybook(String userId, Page page) {
		return newBookService.mybook(userId, page);
	}
	
	@Override
	public Result getByBookName(String name) {
		return newBookService.getByBookName(name);
	}
	
	@Override
	public Result getByIsbn(String isbn) {
		return newBookService.getByBookIsbn(isbn);
	}
	
	@Override
	public Result getByBookFullName(String name) {
		return newBookService.getByBookFullName(name);
	}
	
	@Override
	public Result gradeBookUd(String gradeCode, Page page) {
		return newBookService.gradeBookUd(gradeCode, page);
	}

	@Override
	public Result gradeDriftUd(String gradeCode, Page page) {
		return newBookService.gradeDriftUd(gradeCode, page);
	}
	
	@Override
	public Result pageBook(int type) {
		return newBookService.getByPageBook(type);
	}
	
	@Override
	public Result bookHotOrNew(int type, Page page) {
		return newBookService.getByPageBookList(type, page);
	}
	
	@Override
	public Result getByIsbn(String schoolCode, String isbn) {
		Result result = new DefaultResult();
		result = newBookService.getByIsbn(schoolCode, isbn);
		NewBookVO bookVO = (NewBookVO) result.get("newBookVO");
		if (null != bookVO) {
			return result;
		}
		// 如库里不存在书籍，则爬取
		NewBookVO bVO = jsoupBook(isbn);
		// 爬取失败，则返回错误提示
		if (null == bVO) {
			result.setErrors("A0001", getMsg("A0001", new String[] { isbn }));
			return result;
		}
		// 爬取成功，则返回及异步插入库
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				newBookService.add(schoolCode, bVO);
			}
		});
		result.setModel("bookVO", bVO);
		return result;
	}
	
	/**
	 * 根据条件爬取数据
	 * 
	 * @param isbn
	 * @param result
	 * @return
	 */
	private NewBookVO jsoupBook(String isbn) {
		// 优先级： 豆瓣 > 当当 > 国图 > 京东
		// 豆瓣读书
		NewBookVO dbBook = DbBook.db(isbn);
		if (null != dbBook) {
			return dbBook;
		}
		// 当当网
		NewBookVO ddBook = DdBook.dd(isbn);
		if (null != ddBook) {
			return ddBook;
		}
		// 国家图书馆
		NewBookVO gtBook = GtBook.gjtsg(isbn);
		if (null != gtBook) {
			return gtBook;
		}
		// 京东网
		NewBookVO jdBook = JdBook.jd(isbn);
		if (null != jdBook) {
			return jdBook;
		}
		// 备用地址1,暂不用，太慢了
//		NewBookVO byOneBook = useSite1(isbn);
		// 备用地址2
		NewBookVO byTwoBook = useSite2(isbn);
		if (null != byTwoBook) {
			return byTwoBook;
		}
		
		return null;
	}
	
	/**
	 * 备用地址1
	 * 
	 * @param isbn
	 * @return
	 */
	private NewBookVO useSite1(String isbn) {
		try {
			String url = "http://139.196.57.196:10087/getBookInfo?isbn="+isbn;
			String json = HttpUtils.httpGet(url);
			System.out.println(json);
			/**
			 {
				"status": 0,
				"msg": "success",
				"data": {
					"title": "马戏团",
					"author": "[美]彼得·史比尔",
					"publishingHouse": "光明日报出版社",
					"translate": "李一慢",
					"publishTime": "2015-10",
					"pageSize": "47",
					"pricing": "39.80元",
					"ZTclassify": "",
					"bookbinding": "精装",
					"classifyMark": "",
					"ISBN": "9787511289117",
					"coverPicture": "https://img3.doubanio.com/view/subject/s/public/s28302820.jpg",
					"grade": " 9.2 ",
					"contentIntroduction": "\n    麦克纳尔蒂马戏团要来小镇上演出啦！长龙般的演出车队正有序驶来……看啊，精彩的马术表演，可爱的大熊骑车",
					"aboutAuthor": ""
				}
			}
			 */
		} catch(Exception e) {
		}
		return null;
	}
	
	/**
	 * 备用地址2
	 * 
	 * @param isbn
	 * @return
	 */
	private NewBookVO useSite2(String isbn) {
		try {
			// 实质上也是豆瓣读书
			String url = "https://book.feelyou.top/isbn/"+isbn;
			String json = HttpUtils.httpGet(url);
			if (json.contains("error")) {
				log.info(json);
				return null;
			}
			log.info("备用地址抓取成功：{}", json);
			NewBookVO bookVO = new NewBookVO();
			bookVO.setIsbn(isbn);
			JSONObject obj = JSON.parseObject(json);
			String name = obj.getString("title");
			bookVO.setName(name);
			String other = obj.getString("abstract");
			String intro = obj.getString("book_intro");
			bookVO.setIntro(intro);
			if (!Q.isEmpty(other)) {
				String [] arr  = other.split(" / ");
				String publisher = null, price = null;
				int count = arr.length;
				if (count == 1) {
					price = arr[0];
					return null;
				}
				String author = arr[0];
				bookVO.setAuthor(author);
				if (count > 4) {
					publisher = arr[3];
					price = arr[5];
				} else {
					publisher = arr[2];
					price = arr[4];
				}
				bookVO.setPublisher(publisher);
				Double priceVal = Double.parseDouble(price);
				bookVO.setPrice(ArithUtils.yuanToCent(priceVal));
			}
			return bookVO;
		} catch (Exception e) {
		}
		return null;
	}

}
