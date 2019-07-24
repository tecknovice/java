package mycrawler;

import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.*;

public class WebCrawler {

	static Queue<String> q = new LinkedList<String>();

	public static void main(String[] args) {
		String intitialUrl = "https://news.google.com/";
		scraping(intitialUrl);
	}

	static void scraping(String initialUrl) {
		StringBuffer html;
		Document doc;
		String str;
		URL url;
		BufferedReader in;
		String inputLine;
		Elements links;
		String title;
		q.add(initialUrl);

		for (;;) {
			try {
				html = new StringBuffer();
				str = q.remove();
				if (str == "") {
					continue;
				}
				url = new URL(str);
				in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((inputLine = in.readLine()) != null)
					html.append(inputLine);
				in.close();
				doc = Jsoup.parse(html.toString());
				title = doc.title();
				System.out.println(title);
				links = doc.select("a[href]");
				for (Element link : links) {
					String linkHref = link.attr("abs:href").trim();
					String linkText = link.text();
					// check exists
					if (q.contains(linkHref)) {
						continue;
					}
					// add to queue
					else {
						q.add(linkHref);
					}
				}
				//save to files
				PrintWriter out = new PrintWriter("google_news/"+title.hashCode()+".html");
				out.println(html);
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
