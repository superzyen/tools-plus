package test;

import datauploadtool.DataUploadToolApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DataUploadToolApplication.class)
public class DemoTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        System.out.println(String.format("系统的测试端口号：%s", this.port));
    }

    @Test
    public void testMain(){
        System.out.println("测试开始");
    }
}
