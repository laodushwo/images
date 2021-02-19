package org.spring.springboot.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.amall.admin.commons.util.ArithUtils;
import com.amall.books.commons.domain.NewBookVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 爬取豆瓣
 * 
 * @author DonnyDu
 * @date 20210119
 */
@Slf4j
public class DbBook {

	private static String DOUBAN_BOOK_URL = "https://book.douban.com/isbn/%s";
	
	public static NewBookVO db(String isbn) {
        try {
        	String bookUrl = String.format(DOUBAN_BOOK_URL, isbn);
        	Document doc = Jsoup.connect(bookUrl).timeout(10000).headers(HeadersUtils.getHeaders()).get();
			NewBookVO book = new NewBookVO();
            Elements vo = doc.select("#wrapper");
            book.setName(vo.select("h1>span").first().text());
            Element info = vo.select("#info").first();

            Matcher mAuthor = Pattern.compile("<span.*?作者.*?</span>[\\s\\S]*?<br.*?>").matcher(info.toString());
            if (mAuthor.find()){
                book.setAuthor(mAuthor.group(0)
                        .replaceAll("<span.*?.*[\\s\\S].*<a.*?>", "")
                        .replaceAll("<.*?[\\s\\S]>", "")
                        .replaceAll("\n", "")
                        .trim());
            }

            Matcher mPress = Pattern.compile("<span.*?出版社:.*?</span>[\\s\\S]*?<.*?>").matcher(info.toString());
            if (mPress.find()){
                book.setPublisher(mPress.group(0)
                        .replaceAll("<span.*?出版社:.*?>", "")
                        .replaceAll("<.*?>", "")
                        .trim());
            }
            
            Matcher mPrice = Pattern.compile("<span.*?定价.*?</span>[\\s\\S]*?<br.*?>").matcher(info.toString());
            if (mPrice.find()){
                Matcher m = Pattern.compile("[0-9\\.]+").matcher(mPrice.group(0));
                if (m.find()) {
                	Double price = Double.parseDouble(m.group(0));
                	book.setPrice(ArithUtils.yuanToCent(price));
                } else book.setPrice(0);
            }else book.setPrice(0);

            Matcher mISBN = Pattern.compile("<span.*?ISBN.*?</span>[\\s\\S]*?<br.*?>").matcher(info.toString());
            if (mISBN.find()){
                book.setIsbn(mISBN.group(0)
                        .replaceAll("<span.*?.*span>", "")
                        .replaceAll("<.*?>", "")
                        .trim());
            }

            Elements report = vo.select("#link-report").first().select("div.intro p");
            StringBuffer content = new StringBuffer();
            report.forEach(it -> {
                content.append(it.text().trim() + "\n");
            });
            if (content.length() > 0) {
            	String c = content.toString();
                // 这里做字符串过滤，只提取中文、大小写字母、数字、符号<-,{}':;'!>、空格，后期如果有其它需求，可以加进去
                c = c.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9-,{}':;'! ]", "");
                book.setIntro(c);
            }
            
            log.info("豆瓣抓取成功：{}", isbn);
            return book;
		} catch (IOException e) {
			log.info("豆瓣读书未抓取到书：{}", isbn);
		}
        return null;
	}
	
}
