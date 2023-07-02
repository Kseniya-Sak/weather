package weather.junit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import weather.config.Config;
import weather.util.ReadWeatherDataFromPage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        MockitoExtension.class
})
class ReadWeatherDataFromPageTest {
    private static Map<String, String> weather;

    @Mock
    Config config;

    @InjectMocks
    ReadWeatherDataFromPage readWeatherDataFromPage;

    @BeforeAll
    static void getWeather() {
        weather = new LinkedHashMap<>();
        weather.put("02.07", "Вечер");
        weather.put("Явление", "Облачно. (80%) Без существенных осадков. (0.2 мм.) ");
        weather.put("Температура", "+15..+17");
        weather.put("Давление", "742");
        weather.put("Влажность", "82%");
        weather.put("Ветер", "[Ю] 2-4 м/с");
    }

    @Test
    void getDateFromString_getSuccessful() {
        assertThat(readWeatherDataFromPage.getDateFromString("22.04 погода")).isEqualTo("22.04");

    }
    @Test
    void getDateFromString_getUnsuccessful() {
        assertThat(readWeatherDataFromPage.getDateFromString("22 погода")).isEqualTo("");
    }

    @Test
    void addValuesFromPage_addSuccessful() {
        File file = new File("src/test/page.html");
        Document document = null;
        try {
            document = Jsoup.parse(file, "UTF-8", "hh.ru");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(readWeatherDataFromPage.addValuesFromPage(document)).isEqualTo(weather);
    }
}