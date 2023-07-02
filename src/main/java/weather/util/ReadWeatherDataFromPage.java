package weather.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import weather.config.Config;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReadWeatherDataFromPage {

    private Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private final Config config;

    public ReadWeatherDataFromPage(Config config) {
        this.config = config;
    }

    // вывести в консоль актуальную погоду Санкт-Петербурга
    public void printWeather() {
        Document page = getPage();
        addValuesFromPage(page).forEach((k, v) -> System.out.println(k + ": " + v));
    }

    // получить актуальную погоду Санкт-Петербурга
    public Map<String, String> addValuesFromPage(Document page) {
        Map<String, String> weather = new LinkedHashMap<>();
        Element tableWeather = page.select("table[class=wt]").first();
        Elements names = tableWeather.select("tr[class=wth]");
        Elements values = tableWeather.select("tr[valign=top]");
        String dateString = names.get(0).select("th[id=dt]").text();
        String date = getDateFromString(dateString);

        String[] array = values.get(0).select("td").text().split(" ");

        weather.put(date, array[0]);
        String cloud = "";
        for (int i = 1; i < array.length - 6; i++) {
            cloud += array[i] + " ";
        }
        weather.put("Явление", cloud);
        weather.put("Температура", array[array.length-6]);
        weather.put("Давление", array[array.length-5]);
        weather.put("Влажность", array[array.length-4]);
        weather.put("Ветер", array[array.length-3] + " " + array[array.length-2] + " " + array[array.length-1]);

        return weather;
    }

    // чтение страницы
    private Document getPage() {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(config.getPage()), 3000);
        } catch (IOException e) {
            System.err.println("Не удалось прочитать страницу по адресу " + config.getPage());
        }
        return page;
    }

    // извлеч дату из строки
    public String getDateFromString(String str) {
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            return matcher.group();
        } else {
            System.err.println("Не получилось извлечь дату из строки: " + str);
        }
        return "";
    }

}
