package com.springboot.likelion01.dao;

import com.springboot.likelion01.domain.Hospital;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HospitalDao {

    private final JdbcTemplate jdbcTemplate;

    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Hospital hospital) {
        String sql = "INSERT INTO `likelion-db`.`hospitals`(`id`, `open_service_name`, `open_local_government_code`, `management_number`, `license_date`, `business_status`, `business_status_code`, `phone`, `full_address`, `road_name_address`, `hospital_name`, `business_type_name`, `healthcare_provider_count`, `patient_room_count`, `total_number_of_beds`, `total_area_size`)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; // 16개
        this.jdbcTemplate.update(sql,
                hospital.getId(), hospital.getOpenServiceName(), hospital.getOpenLocalGovernmentCode(),
                hospital.getManagementNumber(), hospital.getLicenseDate(), hospital.getBusinessStatus(),
                hospital.getBusinessStatusCode(), hospital.getPhone(), hospital.getFullAddress(),
                hospital.getRoadNameAddress(), hospital.getHospitalName(), hospital.getBusinessTypeName(),
                hospital.getHealthcareProviderCount(), hospital.getPatientRoomCount(), hospital.getTotalNumberOfBeds(),
                hospital.getTotalAreaSize());
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM hospitals");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM hospitals", Integer.class);
    }

    RowMapper<Hospital> rowMapper = new RowMapper() {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hospital hospital = new Hospital(rs.getInt("id"), rs.getString("open_service_name"), rs.getInt("open_local_government_code"),
                    rs.getString("management_number"),rs.getObject("license_date", LocalDateTime.class),rs.getInt("business_status"),
                    rs.getInt("business_status_code"),rs.getString("phone"),rs.getString("full_address"),
                    rs.getString("road_name_address"),rs.getString("hospital_name"),rs.getString("business_type_name"),
                    rs.getInt("healthcare_provider_count"),rs.getInt("patient_room_count"),rs.getInt("total_number_of_beds"),
                    rs.getFloat("total_area_size"));
            return hospital;
        }
    };

    public Hospital findById(int id) {
        // 한개의 결과를 조회하므로 queryForObject 사용, Hospital 리턴
        return this.jdbcTemplate.queryForObject("SELECT * FROM hospitals where id =?" , rowMapper, id );
    }

    // 한번에 모두 조회 하기에는 데이터가 너무 많아 limit을 사용하여 개수 조절
    // 여러개의 결과를 조회하므로 query 사용, List<Hospital> 리턴
    public List<Hospital> findLimit(int cnt) {
        return this.jdbcTemplate.query("SELECT * FROM hospitals limit ?", rowMapper, cnt);
    }

    // address와 limit 개수를 통해 해당 address에 병원 목록 조회하기
    // ex) 경기도 하남시에 병원 목록 최대 50개
    public List<Hospital> findAddressLimit(String address,int cnt) {
        return this.jdbcTemplate.query("SELECT * FROM hospitals where full_address like ? limit ?", rowMapper, address + "%", cnt);
    }
}
