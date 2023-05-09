import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler implements Runnable {
    private static final int MAX_DEPTH = 2;
    private Thread thread;
    private String first_link;
    private static List<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private int ID;
    private static ArrayList<String> user_agents_list = new ArrayList<String>(); 
    private String location;
    private String selector;
    private Element element;
    private String suiteType, price;
    private int rentalPrice;
    private Database db;
    private JTextArea textarea;
    public WebCrawler(String link, int num, String location, JTextArea textarea) {
        first_link = link;
        ID = num;
        this.location = location;
        db = new Database();
        this.textarea = textarea;
        
        user_agents_list.add("Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
        user_agents_list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.83 Safari/537.36");
        user_agents_list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        user_agents_list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");
        
//        thread = new Thread(this);
//        thread.start();
    }
    @Override
    public void run() {
    	textarea.append("Web Crawler Created! \n");
        crawl(1, first_link, false);
        textarea.append("Web Crawler Complete \n");
    }

    private void crawl(int level, String url, boolean isTarget) {
    	
        if (level <= MAX_DEPTH) {
            Document doc = request(url);
            if (doc == null)
            	textarea.append("NULL" + "\n");
            
            if (doc != null) {
            	
            	if (isTarget && level == MAX_DEPTH) {
            		textarea.append(url+ "\n");
//            		
            		selector = "#descriptionSection > h2";
            		String title = doc.select(selector).text();
            		if (title.equals("About This Property")) {
            			// layout 1
                		parseLayout1(doc, url);            			
            		} else {
                		// layout 2
            			selector = "#pricingView > div.tab-section.active";
                		Element ele = doc.select(selector).first();
                		if (ele != null)
                			parseLayout2(doc, ele, url);
            		}
            		
            	} else { // Crawl the page, Find next link
	            	Elements placards = doc.select("li.mortar-wrapper");
	            	if (placards.size() > 0) {
	            		for (Element li : placards) {
	            			String next_link = li.select("article").attr("data-url");
	                        if (isMatch(next_link) && !visitedLinks.contains(next_link)) {
	//                        	textarea.append("H: " + next_link);
	                        	crawl(level + 1, next_link, true);
	                        }
	            		}
	            	}
            	}
            }
        }
    }
    
    private void parseLayout2(Document doc, Element ele, String url) {
    	textarea.append("layout 2"+ "\n");
    	reset();
		int length = ele.children().size();
		for (int i = 1; i <= length; i++) {
			String suiteSeletor = "#pricingView > div.tab-section.active > div:nth-child(" + i + ") > div.priceGridModelWrapper.js-unitContainer.mortar-wrapper > div.row > div.column1 > div > h3 > span.modelName";
			Element suiteElement = doc.select(suiteSeletor).first();
			if (suiteElement == null)
				continue;
			
			this.suiteType = convertSuite(suiteElement.text());
//			textarea.append(suiteType);
			
			String SelectListing = "#pricingView > div.tab-section.active > div:nth-child(" + i + ") > div.unitGridContainer.mortar-wrapper > div > ul";
			Element ele2 = doc.select(SelectListing).first();
			int size = ele2 == null ? 0 : ele2.children().size();
			for (int j = 1; j <= size; j++) {
				String priceSelector = "#pricingView > div.tab-section.active > div:nth-child(" + i + ") > div.unitGridContainer.mortar-wrapper > div > ul > li:nth-child(" + j + ") > div.grid-container.js-unitExtension > div.pricingColumn.column > span:nth-child(2)";
				Element priceElement = doc.select(priceSelector).first();
				price = priceElement.text();
				if (price == null)
    				continue;
				rentalPrice = convertPrice(price);
				if (rentalPrice == -1)
					continue;
//				textarea.append(rentalPrice);
				// insert to database
				if (this.suiteType.length() > 0 && this.rentalPrice != 0) {
					db.insertRental(url, this.suiteType, this.rentalPrice, this.location);
				}
			}
		}
		
    }
    
    private void parseLayout1(Document doc, String url) {
    	textarea.append("layout 1"+ "\n");
    	reset();
    	String str;
    	selector = "#priceBedBathAreaInfoWrapper > div > div > ul > li:nth-child(1) > div > p.rentInfoDetail";
		element = doc.select(selector).first();
		if (element == null)
			return;
		this.rentalPrice = convertPrice(element.text());
		
		selector = "#priceBedBathAreaInfoWrapper > div > div > ul > li:nth-child(2) > div > p.rentInfoDetail";
		element = doc.select(selector).first();
		if (element == null)
			return;
		str = element.text();
		int numBed = 0, numBath = 0;
		if (str.charAt(0) == '1')
			numBed = 1;
		else if (str.charAt(0) == '2')
			numBed = 2;
		else if (str.charAt(0) == '3')
			numBed = 3;
		else if (str.charAt(0) == '4')
			numBed = 4;
		selector = "#priceBedBathAreaInfoWrapper > div > div > ul > li:nth-child(3) > div > p.rentInfoDetail";
		element = doc.select(selector).first();
		str = element.text();
		if (str.charAt(0) == '1')
			numBath = 1;
		else if (str.charAt(0) == '2')
			numBath = 2;
		else if (str.charAt(0) == '3')
			numBath = 3;
		else if (str.charAt(0) == '4')
			numBath = 4;
		
		if (numBed + numBath > 0)
    		suiteType = numBed + "B" + numBath + "B";
		
//		textarea.append(suiteType);
//		textarea.append(rentalPrice);
		// insert to database
		if (suiteType.length() > 0 && rentalPrice != 0) {
    		db.insertRental(url, suiteType, rentalPrice, this.location);
		}
    }
    
    private void reset() {
    	price = "";
    	suiteType = "";
    	rentalPrice = 0;
    }
     private int convertPrice(String s) {
    	int price = 0;
    	for (int i = 0; i < s.length(); i++) {
    		if (Character.isDigit(s.charAt(i)))
    			price = price * 10 + (s.charAt(i) - '0');
    	}
    	return price == 0 ? -1 : price;
    }
    
    private String convertSuite(String suiteType) {
    	String str = suiteType.toLowerCase();
    	if (str.contains("studio"))
    		return "studio";
    	if (str.contains("townhome"))
    		return "townhome";
    	
    	int numBed = 0, numBath = 0;
    	if (str.contains("one bedroom") || str.contains("1 bedroom") || str.contains("1 bed"))
    		numBed = 1;
    	else if (str.contains("two bedroom") || str.contains("2 bedroom") || str.contains("2 bed"))
    		numBed = 2;
    	else if (str.contains("three bedroom") || str.contains("3 bedroom") || str.contains("3 bed"))
    		numBed = 3;
    	else if (str.contains("four bedroom") || str.contains("4 bedroom") || str.contains("4 bed"))
    		numBed = 4;
    	
    	if (str.contains("one bathroom") || str.contains("1 bathroom") || str.contains("1 bath"))
    		numBath = 1;
    	else if (str.contains("two bathroom") || str.contains("2 bathroom") || str.contains("2 bath"))
    		numBath = 2;
    	else if (str.contains("three bathroom") || str.contains("3 bathroom") || str.contains("3 bath"))
    		numBath = 3;
    	else if (str.contains("four bathroom") || str.contains("4 bathroom") || str.contains("4 bath"))
    		numBath = 4;
    	
    	if (numBed + numBath > 0)
    		return numBed + "B" + numBath + "B";
    	return "N/A";
    }
    
    private boolean isMatch(String link) {
        String target = "https://www.apartments.com/";
        if (link.length() >= target.length() && target.equals(link.substring(0, target.length())))
            return true;
        return false;
    }

    private Document request(String url) {
        try {
        	Random rn = new Random();
        	int max = user_agents_list.size() - 1, min = 0;
        	int rid = rn.nextInt(max - min + 1) + min;
            Connection conn = Jsoup.connect(url).header("Content-Type","application/x-www-form-urlencoded")
//            		.header("Accept", "*/*")
//            		.referrer("http://www.google.com");
            		.userAgent(user_agents_list.get(rid));
            
            Document doc = conn.get();
            if (conn.response().statusCode() == 200) {
                textarea.append("Bot ID: " + ID + " Received Webpage at " + url + "\n");
                visitedLinks.add(url);
                return  doc;
            }
            return null;
        } catch (IOException e) {
        	System.out.println(e);
            return null;
        }
    }

    public Thread getThread() {
        return thread;
    }
}
