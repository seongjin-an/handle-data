package com.ansj.domain.member.repository;


import com.ansj.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final private String TABLE = "Member";

    // RowMapper 는 빈으로 등록하고 사용할 수 있는데, 그렇게 하려면 Generic 으로 들어오는 객체가 모든 필드에 대해 Setter를 만들어야 함.
    // Setter를 개발하는 것은 주의해야 함. 프로젝트가 큰 곳에서는 필드 하나 바꾸는 것에 대해 사이드이펙트가 커져버림
    RowMapper<Member> rowMapper = (ResultSet rs, int rowNums) ->
            Member.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .nickname(rs.getString("nickname"))
                    .birthday(rs.getObject("birthday", LocalDate.class)) // generic type을 이용하여 컬럼 라벨과 추출하기 원하는 클래스 타입 레퍼런스를 넣어주면, 그것으로 변환
                    .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                    .build();

    public Optional<Member> findById(Long id) {
        /*
            select *
            from Member
            where id = :id
         */
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);


        var member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        if (ids.isEmpty())
            return List.of();
        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public Member save(Member member) {
        /*
            member id 를 보고 갱신 또는삽입을 정함
            반환값은 id 를 담아서 반환한다.
         */
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql,params);
        return member;
    }

}
