package org.spring.springboot.util;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.amall.admin.commons.util.ArithUtils;
import com.amall.books.commons.domain.NewBookVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 爬取京东
 * 
 * @author DonnyDu
 * @date 20210119
 */
@Slf4j
public class JdBook {
	
    private static String JD_URL_PREFIX = "https://search.jd.com/Search?keyword=%s&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wtype=1&click=1";

	public static NewBookVO jd(String isbn) {
		try {
            String bookUrl = String.format(JD_URL_PREFIX, isbn);
            Document parse1 = Jsoup.parse(new URL(bookUrl), 3000);
            Element element1 = parse1.select("#J_goodsList").get(0);
            NewBookVO bVO = new NewBookVO();
            for (Element ele : element1.select(".gl-item")) {
            	String bookID=ele.attr("data-sku");
            	String bookPrice=ele.select("div[class=p-price]").select("strong").select("i").text();
            	bVO.setPrice(ArithUtils.yuanToCent(Double.parseDouble(bookPrice)));
     	       	String bookName=ele.select("div[class=p-name]").select("em").text();
     	       	// 对象的值
     	       	bVO.setName(bookName);
     	       
     	       	// 根据书籍ID，查询书籍详情并获取相应的值
     	       	String detailUrl = "https://item.jd.com/"+bookID+".html";
     	       	Document parse = Jsoup.parse(new URL(detailUrl), 30000);
     	       
     	       	// ISBN
     	       	String isbn1 = parse.select("#parameter2").select("li").get(2).attr("title");
     	       	int k = 1;
     	       	if (isbn1.length() < 13) {
     	       		isbn1 = parse.select("#parameter2").select("li").get(1).attr("title");
     	       		k = 0;
     	       	}
     	       	bVO.setIsbn(isbn1);
     	       
     	       	// 出版社
     	       	String bookPress = parse.select("#parameter2").select("li").get(k).attr("title");
     	       	bVO.setPublisher(bookPress);
     	       
     	       	// 书籍作者
     	       	String bookAuthor = parse.select("#p-author").select("a").attr("data-name");
     	       	bVO.setAuthor(bookAuthor);
     	       
     	       	// 简介
     	       	String summary = parse.select("div[class=book-detail-content]").select("p").text();
     	       	// 这里做字符串过滤，只提取中文、大小写字母、数字、符号<-,{}':;'!>、空格，后期如果有其它需求，可以加进去
     	       	if (!"".equals(summary)) {
     	       		summary = summary.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9-,{}':;'! ]", "");
     	       		bVO.setIntro(summary);
     	       	}
     	       	
     	       	// 只做一次查询
     	       log.info("京东抓取成功：{}", isbn);
     	       	break ;
            }
            return bVO;
        } catch (Exception e) {
        	log.info("京东未抓取到书：{}", isbn);
        }
		return null;
	}
	
}
