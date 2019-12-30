package com.example.springtest191226.controller;

import com.example.springtest191226.model.SampleTestBean;

import com.example.springtest191226.service.SampleService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT) //Mock : mvc. 서블릿 컨텍스트 없음. random_port : 서블릿 컨텍스트 사용
public class SampleControllerTest {


    @TestConfiguration //테스트용 컨픽 설정도 가능
    static class TestConfig{

        @Bean
        public String myBean(){
            return "myBean";
        }

        @Bean
        public SampleTestBean getSampleTestBean(){
            return new SampleTestBean(100, "테스트용 빈을 생성");
        }

    }

    @Autowired
    String myBean;

    @Autowired
    SampleTestBean sampleTestBean;

    @Autowired //왜 이거 MOCK env 에서도 NULL이 아니지?
    MockMvc mockMvc;


    ///아래로 서블릿 사용 = MOCK 아닌 RANDOMPORT 등의 환경에서 사용
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean //mock빈으로 만들어버리면 원래 "sample"을 리턴해야하는 빈도 null을 리턴함
    SampleService sampleService;

    @Test
    public void HelloTest() throws Exception{

        Assert.assertEquals("hello", "hello");
        Assert.assertNotNull(mockMvc);

        Assert.assertEquals("myBean", myBean);
        Assert.assertEquals(sampleTestBean.idx, 100);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello!!!"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void TestWithTestRestTemplate(){

        System.out.println("Localport : "+port);
        String body = this.testRestTemplate.getForObject("/hello", String.class);
        Assert.assertEquals(body, "hello!!!");

    }

    @Test
    public void TestWithMockBean() throws Exception{
        given(this.sampleService.getSample()).willReturn("MockResult"); //실제 getSample 은 "sample"을 리턴하지만 목으로 결과값 변경

        String sampleTest = this.testRestTemplate.getForObject("/getSample", String.class);
        Assert.assertEquals(sampleTest, "MockResult");


        mockMvc.perform(MockMvcRequestBuilders.get("/getSample"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("MockResult"))
                .andDo(MockMvcResultHandlers.print());


    }

}
