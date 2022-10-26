package com.springboot.likelion01.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/get-api")
public class HelloController {

    // http://localhost:8080/api/v1/get-api/hello
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "Hello World";
    }

    // http://localhost:8080/api/v1/get-api/name
    @GetMapping(value = "/name")
    public String getName() {
        return "sujeong";
    }

    // http://localhost:8080/api/v1/get-api/variable1/{String 값}
    // url 뒤에 / 치고 바로 오는 데이터를 넘기는 역할을 한다.
    @GetMapping(value = "/variable1/{variable}") // {} 안에 있는 값을 받아 요청한다
    public String getVariable(@PathVariable String variable) {
        return variable;
    }

    /*
    원래는 pathVariable 하려면 @GetMapping 애노테이션이 지정한
    변수이름과 매서드 매개변수의 이름을 동일하게 맞춰야 한다. 맞출 수 없는 상황이라면,
    @PathVariable 뒤에 괄호를 열어 @GetMapping 어노테이션의 변수명을 지정한다.
     */
    // http://localhost:8080/api/v1/get-api/variable2/{String 값}
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable")  String var) {
        return var;
    }

}
