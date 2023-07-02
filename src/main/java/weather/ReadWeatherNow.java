package weather;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import weather.config.Config;
import weather.util.ReadWeatherDataFromPage;

public class ReadWeatherNow {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        ReadWeatherDataFromPage readWeatherDataFromPage = context.getBean(ReadWeatherDataFromPage.class);
        readWeatherDataFromPage.printWeather();

    }


}
