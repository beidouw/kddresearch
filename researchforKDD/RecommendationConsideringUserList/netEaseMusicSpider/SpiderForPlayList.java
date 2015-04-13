package netEaseMusicSpider;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SpiderForPlayList {
	
	public void GetURL(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient web=new WebClient();
		HtmlPage page=web.getPage(url);
		
		String pagecontent=page.asText();
		System.out.println(page);
	}

}
