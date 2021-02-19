package org.spring.springboot.ao.impl;

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
import com.amall.admin.commons.util.ArithUtils;
import com.amall.admin.commons.util.Q;
import com.amall.app.abstacts.AbstractAO;
import com.amall.books.commons.dointerface.NewBookService;
import com.amall.books.commons.domain.NewBookVO;
import com.amall.commons.result.DefaultResult;
import com.amall.commons.result.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultBookAO extends AbstractAO implements BookAO {

	@Resource
	private NewBookService newBookService;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
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
	
	public static void main(String[] args) {
		
	}
	
	
	

}
