package com.springboot.likelion01.controller;

import com.springboot.likelion01.domain.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
@Slf4j
public class GetController {

    // http://localhost:8081/api/v1/get-api/hello
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        log.info("hello로 요청이 들어왔습니다.");
        return "Hello World";
    }

    // http://localhost:8081/api/v1/get-api/name
    @GetMapping(value = "/name")
    public String getName() {
        log.info("getName으로 요청이 들어왔습니다.");
        return "sujeong";
    }

    // http://localhost:8081/api/v1/get-api/variable1/{String 값}
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable) {
        log.info("getVariable1으로 요청이 들어왔습니다. variable:{}", variable);
        return variable;
    }

    // http://localhost:8081/api/v1/get-api/variable2/{String 값}
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable")  String var) {
        return var;
    }

    // http://localhost:8081/api/v1/get-api/request1?name=sujeong&email=google&organization=likelion
    // URI의 key:value 형식으로 받은 데이터를 변수(name, email,,)에 넣고 화면에 띄운다.
    @ApiOperation(value = "Get 메서드 예제", notes = "@RequestParam을 활용한 GET 메서드" )
    @GetMapping(value = "/request1")
    public String getRequestParam1(
        @ApiParam(value = "이름", required = true) @RequestParam String name,
        @ApiParam(value = "이메일", required = true) @RequestParam String email,
        @ApiParam(value = "회사", required = true) @RequestParam String organization) {
        return name+ " " + email + " " + organization;
    }

    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        param.entrySet().forEach( map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });
        return sb.toString();
    }

    // MemberDto 객체 활용하여 코드 가독성 높이기
    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto memberDto) {
        return memberDto.toString();
    }


}
