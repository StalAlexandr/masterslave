package ru.alexandrstal.masterslave;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.alexandrstal.masterslave.view.AppView;

@SpringBootApplication
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(App.class).headless(false).run(args);
        AppView view = context.getBean(AppView.class);
        view.showMe();
        logger.info("Приложение успешно запущено!");
    }
}
