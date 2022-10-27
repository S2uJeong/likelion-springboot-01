package com.springboot.likelion01.controller;

import com.springboot.likelion01.domain.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    // http://localhost:8081/api/v1/get-api/hello
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "Hello World";
    }

    // http://localhost:8081/api/v1/get-api/name
    @GetMapping(value = "/name")
    public String getName() {
        return "sujeong";
    }

    // http://localhost:8081/api/v1/get-api/variable1/{String 값}
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable(@PathVariable String variable) {
        return variable;
    }

    // http://localhost:8081/api/v1/get-api/variable2/{String 값}
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable")  String var) {
        return var;
    }

    // http://localhost:8081/api/v1/get-api/request1?name=sujeong&email=google&organization=likelion
    // URI ? 뒤쪽에서 key:value 형식으로 받은 데이터를 변수(name, email,,)에 넣고 화면에 띄운다.
    @GetMapping(value = "/request1")
    public String getRequestParam1(
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String organization
    ) {
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
