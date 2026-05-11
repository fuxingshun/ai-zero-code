package com.fxs.aizerocode.ai;

import com.fxs.aizerocode.ai.model.HtmlCodeResult;
import com.fxs.aizerocode.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做个fxs的工作记录小工具，代码不超过20行");
        Assertions.assertNotNull(result);
    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult multiFileCode = aiCodeGeneratorService.generateMultiFileCode("做个fxs的留言板，代码不超过50行");
        Assertions.assertNotNull(multiFileCode);
    }

    @Test
    void testChatMemory() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做个傅星顺的工具网站，总代码量不超过 20 行");
        Assertions.assertNotNull(result);
        result = aiCodeGeneratorService.generateHtmlCode("不要生成网站，告诉我你刚刚做了什么？");
        Assertions.assertNotNull(result);
        result = aiCodeGeneratorService.generateHtmlCode("做个傅星顺的工具网站，总代码量不超过 20 行");
        Assertions.assertNotNull(result);
        result = aiCodeGeneratorService.generateHtmlCode("不要生成网站，告诉我你刚刚做了什么？");
        Assertions.assertNotNull(result);
    }



}