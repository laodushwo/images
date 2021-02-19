package org.spring.springboot.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import com.amall.admin.commons.util.ArithUtils;
import com.amall.books.commons.domain.NewBookVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 爬取当当网
 * 
 * @author DonnyDu
 * @date 20210119
 */
@Slf4j
public class DdBook {

	private static String BASE_FORMAT_URL_PREFIX = "http://search.dangdang.com/?key=%s&act=input&SearchFromTop=1#J_tab";
	
	public static NewBookVO dd(String isbn) {
		try {
			URL detailUrl = new URL(String.format(BASE_FORMAT_URL_PREFIX, isbn));
	        Document doc = Jsoup.parse(detailUrl, 30000);
	        Elements select = doc.select("a[name=itemlist-picture]");
	        for (Element element : select) {
                NewBookVO bVO = new NewBookVO();

                String detail = element.attr("href");
                Document parse = Jsoup.parse(new URL(detail), 50000);

                // 书名
                String bookName = parse.select(".name_info").get(0).select("h1").get(0).attr("title");
                bVO.setName(bookName.split(" ")[0]);
                
                // 价格
                Elements e1 = parse.select("#original-price");
                String price = e1.html();
                int n = price.lastIndexOf(">");
                price = price.substring(n+1);
                bVO.setPrice(ArithUtils.yuanToCent(Double.parseDouble(price)));

                Elements select1 = parse.select(".messbox_info");
                if(CollectionUtils.isEmpty(select1))continue;
                Elements elements = select1.get(0).select(".t1");

                // 作者
                List<String> author = elements.get(0).select("a").stream().map(x -> x.html()).collect(Collectors.toList());
                bVO.setAuthor(StringUtils.join(author,","));

                // 出版社
                List<String> publisher = elements.get(1).select("a").stream().map(x -> x.html()).collect(Collectors.toList());
                bVO.setPublisher(StringUtils.join(publisher,","));

                // ISBN
                String ISBN = parse.select("#detail_describe").get(0).child(0).child(4).html();
                String newIsbn = ISBN.split("：")[1];
                // 因为当当模糊查询，有时候可能不一致
                if (!newIsbn.equals(isbn)) {
                	log.info("当当抓取到书ISBN有问题：传：{}, 爬：{}", isbn, newIsbn);
                	return null;
                }
                bVO.setIsbn(ISBN.substring(13));

                // 分类
                List<String> typeList = parse.select("#detail-category-path").get(0)
                        .child(1).select("a").stream().map(x -> x.html()).collect(Collectors.toList());
                bVO.setCategory(StringUtils.join(typeList,","));

                // 这里只获取一个就返回
                log.info("当当抓取成功：{}", isbn);
                return bVO;
            }
		} catch (IOException e) {
			log.info("当当网未抓取到书：{}", isbn);
		}
        return null;
	}
	
}
