package org.spring.springboot.util;

import java.util.HashMap;

public class HeadersUtils {
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36 Edg/79.0.309.54";
    private static String COOKIE = "bid=DC5khpS4LE0; douban-fav-remind=1; __yadk_uid=BgsjR4ZXik7TeMReIzJrb4j9fGGT0Fye; trc_cookie_storage=taboola%2520global%253Auser-id%3Dc20b17f9-a0a7-40a0-8899-0a2746062351-tuct4d9515e; gr_user_id=08aec6d9-57f6-4636-884b-a93999dc160a; _vwo_uuid_v2=DB431D524FE08F4B30D8C133C7345C16C|9689c97a3137bd5aea46da51d447743c; viewed=\"1477390_4822685_26829016_1770782_25862578_26642866\"; push_noty_num=0; push_doumail_num=0; td_cookie=18446744072528491387; __gads=ID=49d3841b4fb07c00:T=1575563166:S=ALNI_MbdaTk2mpCgrNl44jaHTDIFwueRGA; ll=\"118188\"; __utmc=30149280; __utmv=30149280.20826; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1576841344%2C%22https%3A%2F%2Faccounts.douban.com%2Fpassport%2Fregister%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.1906114953.1574947812.1576836358.1576841349.38; __utmz=30149280.1576841349.38.9.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/passport/register; __utmt=1; __utmb=30149280.1.10.1576841349; _pk_id.100001.8cb4=f0a3711cbbb5b4a2.1574947802.8.1576841389.1576836460.; dbcl2=\"208268304:fOZ6R4g2lgo\"; ck=B6Cv";

    private static HashMap<String, String> headers = new HashMap<String, String>();

    public static HashMap<String, String> getHeaders(){
        headers.put("User-Agent", USER_AGENT);
//        headers.put("Cookie", COOKIE);
        return headers;
    }
}
