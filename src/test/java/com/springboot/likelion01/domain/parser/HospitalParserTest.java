package com.springboot.likelion01.domain.parser;

import com.springboot.likelion01.dao.HospitalDao;
import com.springboot.likelion01.domain.Hospital;
import com.springboot.likelion01.service.HospitalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class HospitalParserTest {

    @Autowired
    ReadLineContext<Hospital> hospitalReadLineContext;

    @Autowired
    HospitalDao hospitalDao;

    @Autowired
    HospitalService hospitalService;

    String line1 = "\"1\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100004\",\"19990612\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-515-2875\",\"\",\"500881\",\"광주광역시 북구 풍향동 565번지 4호 3층\",\"광주광역시 북구 동문대로 24, 3층 (풍향동)\",\"61205\",\"효치과의원\",\"20211115113642\",\"U\",\"2021-11-17 02:40:00.0\",\"치과의원\",\"192630.735112\",\"185314.617632\",\"치과의원\",\"1\",\"0\",\"0\",\"52.29\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"\",";


    @Test
    @DisplayName("1line parsing ok ?")
    public void test() {

        HospitalParser hp = new HospitalParser();
        Hospital hospital = hp.parse(line1);

        assertEquals(1, hospital.getId()); //col 0
        assertEquals("의원", hospital.getOpenServiceName()); //col 1
        assertEquals(3620000,hospital.getOpenLocalGovernmentCode()); //col 3
        assertEquals("PHMA119993620020041100004",hospital.getManagementNumber()); //col 4
        assertEquals(LocalDateTime.of(1999,6,12,0,0), hospital.getLicenseDate()); //19990612 col 5
        assertEquals(1, hospital.getBusinessStatus()); //col 7
        assertEquals(13, hospital.getBusinessStatusCode()); //col 9
        assertEquals("062-515-2875", hospital.getPhone()); // col 15
        assertEquals("광주광역시 북구 풍향동 565번지 4호 3층", hospital.getFullAddress()); //col 18
        assertEquals("광주광역시 북구 동문대로 24, 3층 (풍향동)", hospital.getRoadNameAddress()); //col 19
        assertEquals("효치과의원", hospital.getHospitalName()); //col 21
        assertEquals("치과의원", hospital.getBusinessTypeName()); //col 25
        assertEquals(1, hospital.getHealthcareProviderCount()); //col 30
        assertEquals(0, hospital.getPatientRoomCount()); //col 31
        assertEquals(0, hospital.getTotalNumberOfBeds()); //col 32
        assertEquals(52.29f, hospital.getTotalAreaSize()); //col 33

    }

    @Test
    @DisplayName("all paring test")
    void ReadByLine() throws IOException {
        String filename = "C:\\Users\\sj980\\Downloads\\utf8_fulldata_01_01_02_P_의원.csv";
        List<Hospital> hospitals = hospitalReadLineContext.readByLine(filename);
        assertTrue(hospitals.size() > 10000);
    }

    @Test
    @DisplayName("insert line test")
    void insertByLine() {
        HospitalParser hp = new HospitalParser();
        Hospital hospital = hp.parse(line1);
        hospitalDao.add(hospital);
        assertEquals(1, hospitalDao.getCount());
    }

    @Test
    @DisplayName("insert all test")
    void insertAll() throws IOException {
        hospitalDao.deleteAll();
        String filename = "C:\\Users\\sj980\\Downloads\\utf8_fulldata_01_01_02_P_의원.csv";
        int cnt = this.hospitalService.insertLargeVolumeHospitalData(filename);
        assertTrue(cnt > 1000);
        assertTrue(cnt > 10000);
        System.out.printf("파싱된 데이터 개수: %d %n", cnt);
    }


}

