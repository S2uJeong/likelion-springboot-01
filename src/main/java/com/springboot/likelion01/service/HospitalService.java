package com.springboot.likelion01.service;


import com.springboot.likelion01.dao.HospitalDao;
import com.springboot.likelion01.domain.Hospital;
import com.springboot.likelion01.domain.parser.ReadLineContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// 데이터 insert
@Service
public class HospitalService {
    private final ReadLineContext<Hospital> hospitalReadLineContext;
    private final HospitalDao hospitalDao;

    public HospitalService(ReadLineContext<Hospital> hospitalReadLineContext, HospitalDao hospitalDao) {
        this.hospitalReadLineContext = hospitalReadLineContext;
        this.hospitalDao = hospitalDao;
    }

    public int insertLargeVolumeHospitalData(String filename) {
        List<Hospital> hospitalList;
        try {
            hospitalList = hospitalReadLineContext.readByLine(filename);
            System.out.println("파싱이 끝났습니다.");
            hospitalList.stream()
                    .parallel()
                    .forEach(hospital -> {
                        try {
                            this.hospitalDao.add(hospital); // db에 insert하는 구간
                        } catch (Exception e) {
                            System.out.printf("id:%d 레코드에 문제가 있습니다.\n", hospital.getId());
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (isNotEmpty(hospitalList)) {  // 리스트가 비어있지 않다면
            return hospitalList.size(); // 리스트 갯수 출력
        } else {
            return 0;
        }
    }

    private static boolean isNotEmpty(List<Hospital> hospitalList) {
        return !Optional.of(hospitalList).isEmpty();
    }
}
