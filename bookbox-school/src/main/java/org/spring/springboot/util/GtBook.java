package org.spring.springboot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.amall.books.commons.domain.NewBookVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 爬取国家图书馆
 * 
 * @author DonnyDu
 * @date 20210119
 */
@Slf4j
public class GtBook {
	
    public static final String GT_ISBN_SEARCH = "?func=find-b&find_code=ISB&request=%s&local_base=NLC01";
    public static final String GT_MARC = "?func=full-set-set_body&set_number=%s&set_entry=000001&format=001";
    public static Pattern pattern = Pattern.compile("[0-9]{3}");

    public static void main(String[] args) {
    	gjtsg("sdfafasdf");
	}
    
	public static NewBookVO gjtsg(String isbn) {
		try {
			Map<String, List<String>> out_map = new ConcurrentHashMap<>();
			List<String> outList = new ArrayList<>();
			String prefix =getPrefix();
			isbn = StringUtils.trim(isbn);
			String url = String.format(prefix + GT_ISBN_SEARCH, isbn);
			String page1 = HttpUtils.httpGet(url);
            String setNumStr = "id=set_number";
            int i = page1.indexOf(setNumStr);
            if (i == -1) {
            	log.info("国图未抓取到书：{}", isbn);
            	return null;
            }
            NewBookVO bVO = new NewBookVO();
            if (i > -1) {
                String setnum = page1.substring(i + setNumStr.length() + 8, i + setNumStr.length() + 14);
                String url1 = String.format(prefix + GT_MARC, setnum);
                String page2 = HttpUtils.httpGet(url1);
//                log.info("返回结果：{}", page2);
                page2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><table>" + page2 + "</table>";
                page2 = page2.replaceAll("<td[^>]*>", "<td>");
                page2 = page2.replaceAll("&nbsp;", " ");
                Document document = DocumentHelper.parseText(page2);
                Element rootElement = document.getRootElement();// 获取根节点
                List<Element> elements = rootElement.elements();
                tr:
                for (Element element : elements) {
                    List<Element> tds = element.elements();
                    String temp = "";
                    boolean isFirst = true;
                    for (Element td : tds) {
                        String text = ((Node) td.content().get(0)).getText();
                        if (isFirst) {
                            Matcher matcher = pattern.matcher(text.substring(0, 3));
                            if (!text.contains("LDR") && !matcher.matches()) {
                                continue tr;
                            }
                            isFirst = false;
                            temp = text;
                            continue;
                        }
                        String p = "=" + temp.substring(0, 3) + "  ";
                        if (temp.length() == 3) {
                            if (text.contains("|")) {
                                temp = p + "\\\\";
                            } else {
                                temp = p;
                            }
                        } else if (temp.length() == 4) {
                            temp = p + temp.substring(3) + "\\";
                        } else {
                            if (temp.substring(3, 4).equals(" ")) {
                                temp = p + "\\" + temp.substring(4);
                            } else {
                                temp = p + temp.substring(3, 5);
                            }
                        }
                        temp += text.trim();
                    }
                    temp = StringUtils.replace(temp, "|", "$");
                    temp = temp.replaceAll("(?<=\\$[a-z0-9])\\s", "");
                    temp = StringUtils.replace(temp, " $", "$");
                    outList.add(temp);
                }
                outList.add("");
                if (CollectionUtils.isNotEmpty(outList)) out_map.put(isbn, outList);
                
                // 解析
                HashMap<String, List<String>> map = new HashMap<>();
                TreeMap<String, Integer> fieldCountMap = new TreeMap<>();
                for (String isbns : out_map.keySet()) {
                    List<String> list = out_map.get(isbns);
                    TreeMap<String, Integer> tempFieldMap = new TreeMap<>();
                    for (String str : list) {
                        if (StringUtils.isBlank(str)) continue;
                        String num = str.substring(1, 4);  //   040
                        String mid = str.substring(6, 8);    //  \\
                        String after = str.substring(8);
                        if (after.contains("$")) {
                            String[] split = after.split("\\$");
                            for (String s : split) {
                                if (StringUtils.isBlank(s)) continue;
                                String key = isbns + "|" + num + " " + mid + " $" + s.substring(0, 1);
                                map.putIfAbsent(key, new ArrayList<>());
                                map.get(key).add(s.substring(1));
                                String fieldKey = num + " " + mid + " $" + s.substring(0, 1);
                                tempFieldMap.putIfAbsent(fieldKey, 0);
                                tempFieldMap.put(fieldKey, tempFieldMap.get(fieldKey) + 1);
                            }
                        } else {
                            String key = isbns + "|" + num;
                            map.putIfAbsent(key, new ArrayList<>());
                            map.get(key).add(after);
                            tempFieldMap.putIfAbsent(num, 0);
                            tempFieldMap.put(num, tempFieldMap.get(num) + 1);
                        }
                        tempFieldMap.forEach((key, value) -> {
                            Integer old = fieldCountMap.get(key);
                            if (old == null || old < value) {
                                fieldCountMap.put(key, value);
                            }
                        });
                    }
                }
                for (String isbns : out_map.keySet()) {
                    String[] strs = new String[fieldCountMap.values().stream().reduce((a, b) -> a + b).get()];
                    int i2 = 0;
                    for (String key : fieldCountMap.keySet()) {
                        List<String> valList = map.get(isbns + "|" + key);
                        if (CollectionUtils.isNotEmpty(valList)) {
                            for (String val : valList) {
                                if (StringUtils.isNotBlank(val)) {
                                    strs[i2] = val;
                                }
                                i2++;
                            }
                        } else {
                            i2++;
                        }
                    }
                    bVO.setIsbn(strs[2].replaceAll("-", ""));
                    bVO.setName(strs[20]);
                    bVO.setAuthor(strs[22]);
                    bVO.setPublisher(strs[25]);
                    bVO.setCategory(strs[8]);
                }
            }
            log.info("国图抓取成功：{}", isbn);
			return bVO;
		} catch(Exception e) {
			log.info("国图未抓取到书：{}", isbn);
		}
		return null;
	}
	
	private static String getPrefix() throws Exception {
        System.out.println("搜索国图动态码...");
        long session = Math.round(Math.random() * 1000000000);
        String url = "http://opac.nlc.cn:80/F?RN=" + session;
        String page = HttpUtils.httpGet(url);
        int index = page.indexOf("URL=http://opac.nlc.cn:80");
        String substring = page.substring(index + 4, index + 84);
        System.out.println("动态码:  " + substring);
        return substring;
    }
	
}
