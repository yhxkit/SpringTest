package com.example.springtest191226;

import com.example.springtest191226.model.SampleTestBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@JsonTest
public class SampleJsonTest {

    @Before
    public void setup(){
        // 이렇게 하면 어플리케이션 컨텍스트를 띄우지 않아서 빠르다..
        // 굳이 빈이 필요한게 아니고 json만 테스트할거면 이렇게 하면 빠를듯
        JacksonTester.initFields(this, new ObjectMapper());
    }


    @Autowired
    JacksonTester json;



    @Test
    public void testJson() throws Exception{
        assertThat(json).isNotNull();

        SampleTestBean sample = new SampleTestBean(200, "JsonTestSample");
        JsonContent<SampleTestBean> result = json.write(sample);
        assertThat(result).hasJsonPathStringValue("name").extractingJsonPathStringValue("name").isEqualTo("JsonTestSample");
        assertThat(result).hasJsonPathNumberValue("idx").extractingJsonPathNumberValue("idx").isEqualTo(200);
    }

}
