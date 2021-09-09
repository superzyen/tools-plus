package datauploadtool;

import datauploadtool.mysql.UploadTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DataUploadToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataUploadToolApplication.class, args);
		new UploadTool().toolMain();
    }

}
