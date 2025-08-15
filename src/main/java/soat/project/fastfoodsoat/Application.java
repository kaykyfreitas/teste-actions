package soat.project.fastfoodsoat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;

@SpringBootApplication(
		exclude = {
				MailSenderAutoConfiguration.class,
				JmxAutoConfiguration.class,
				SpringApplicationAdminJmxAutoConfiguration.class,
				WebSocketServletAutoConfiguration.class,
				WebSocketReactiveAutoConfiguration.class,
				FreeMarkerAutoConfiguration.class,
				GroovyTemplateAutoConfiguration.class,
				MustacheAutoConfiguration.class,
				ThymeleafAutoConfiguration.class,
				GsonAutoConfiguration.class,
				TaskExecutionAutoConfiguration.class,
				TaskSchedulingAutoConfiguration.class,
				BatchAutoConfiguration.class
		}
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
