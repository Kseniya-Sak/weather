package weather.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ComponentScan(basePackages = "weather")
@PropertySource("application.properties")
public class Config {

    @Value("${site.weather}")
    String page;
}
