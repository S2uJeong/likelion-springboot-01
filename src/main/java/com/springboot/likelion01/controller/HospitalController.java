package com.springboot.likelion01.controller;

import com.springboot.likelion01.dao.HospitalDao;
import com.springboot.likelion01.domain.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vi/hospital")
public class HospitalController {
    private HospitalDao hospitalDao;

    @Autowired
    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    // http://localhost:8080/hospital/1
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> selectById(@PathVariable int id) {
        Hospital hospital = hospitalDao.findById(id);
        Optional<Hospital> opt = Optional.of(hospital); // hospital이 존재하면 그대로 리턴, 아니면 null 리턴
        if (isNotEmpty(opt)) {
            return ResponseEntity.ok().body(hospital); // 상태코드 200 리턴
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Hospital()); // 상태코드 400 리턴
        }
    }
    // 가독성을 위해 메서드로 분리
    private static boolean isNotEmpty(Optional<Hospital> opt) {
        return !opt.isEmpty();
    }

    //http://localhost:8080/hospital/?cnt=100
    @GetMapping("/")
    public ResponseEntity<List<Hospital>> selectByAddress(@RequestParam int cnt) {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalDao.findLimit(cnt));
        // 응답 바디에 결과값 담은 후 상태코드 200 리턴
    }

    @GetMapping("/{address}/{cnt}")
    public ResponseEntity<List<Hospital>> selectByAddress(@PathVariable String address, @PathVariable int cnt) {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalDao.findAddressLimit(address,cnt));
    }
}
