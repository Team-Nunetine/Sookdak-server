package server.sookdak.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import server.sookdak.domain.Lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebDriverUtil {

    private WebDriver driver;
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
    public static String WEB_DRIVER_PATH = "/usr/local/bin/chromedriver"; // WebDriver 경로

    public WebDriverUtil() {
        chrome();
    }

    private void chrome() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // webDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);

        // weDriver 생성
        driver = new ChromeDriver(options);
//        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public List<Lecture> useDriver(String url, String id, String password) throws InterruptedException {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());

        driver.findElement(By.xpath("//*[@id=\"container\"]/form/p[1]/input")).sendKeys(id);
        driver.findElement(By.xpath("//*[@id=\"container\"]/form/p[2]/input")).sendKeys(password);

        driver.findElement(By.xpath("//*[@id=\"container\"]/form/p[3]/input")).click();

        driver.get("https://everytime.kr/timetable");

        //수업 목록에서 검색 클릭
        driver.findElement(By.xpath("//*[@id=\"container\"]/ul/li[1]")).click();

        Thread.sleep(2000);

        int pre_count = 0;
        //스크롤 맨아래로 내리기
        while (true) {
            //tr 요소 접근
            Thread.sleep(1000);
            List<WebElement> elements = driver.findElements(By.cssSelector("#subjects > div.list > table > tbody > tr"));
            System.out.println(elements.size());

            //tr 마지막 요소 접근
            WebElement result = elements.get(elements.size() - 1);

            //마지막요소에 focus
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", result);
            Thread.sleep(3000);

            //현재 접근한 요소의 갯수
            int current_count = elements.size();
            if (pre_count == current_count) break;
            pre_count = current_count;
        }


        String html = driver.getPageSource();
        Document doc = Jsoup.parse(html);

        Elements trs = doc.select("#subjects > div.list > table > tbody > tr");

        List<Lecture> lectures = new ArrayList<>();
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            String name = tds.get(0).text();
            String classNum = tds.get(1).text();
            String type = tds.get(2).text();
            String timeAndPlace = tds.get(3).text();
            String datetime = "", place = "";
            if (timeAndPlace.contains("(")) {
                datetime = timeAndPlace.substring(0, timeAndPlace.indexOf("("));
                place = timeAndPlace.substring(timeAndPlace.indexOf("(") + 1, timeAndPlace.indexOf(")"));
            }
            String professor = tds.get(4).text();
            int credit = Integer.parseInt(tds.get(5).text());
            int studentNum = Integer.parseInt(tds.get(9).text());
            String info = tds.get(11).text();
            /*System.out.println("name = " + name);
            System.out.println("classNum = " + classNum);
            System.out.println("type = " + type);
            System.out.println("place = " + place);
            System.out.println("time = " + time);
            System.out.println("professor = " + professor);
            System.out.println("studentNum = " + studentNum);
            System.out.println("credit = " + credit);
            System.out.println("info = " + info);
            System.out.println();*/

            Lecture lecture = Lecture.createLecture(name, professor, classNum, datetime, place, type, credit, studentNum, info);
            lectures.add(lecture);
        }

        quitDriver();
        return lectures;
    }

    private void quitDriver() {
        driver.quit(); // webDriver 종료
    }
}
