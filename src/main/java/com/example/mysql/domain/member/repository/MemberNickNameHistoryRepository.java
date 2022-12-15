package com.example.mysql.domain.member.repository;

import com.example.mysql.domain.member.entity.Member;
import com.example.mysql.domain.member.entity.MemberNicknameHistory;
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
public class MemberNickNameHistoryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory
            .builder()
            .id(resultSet.getLong("id"))
            .nickname(resultSet.getString("nickname"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();
    private final static String TABLE = "MemberNicknameHistory";

    public Optional<MemberNicknameHistory> findById(Long id) {
        var sql = String.format("SELECT * FROM %s WHERE id =:id", TABLE);
        var param = new MapSqlParameterSource().addValue("id", id);
        var member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);
    }

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
        var params = new MapSqlParameterSource().addValue("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
    public MemberNicknameHistory save(MemberNicknameHistory member) {
        if (member.getId() == null) {
            return insert(member);
        }
        throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다.");
    }

    private MemberNicknameHistory insert(MemberNicknameHistory history) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(history);
        var id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return MemberNicknameHistory
                .builder()
                .id(id)
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createdAt(history.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday =:birthday WHERE id = :id", TABLE);
        SqlParameterSource parmas = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, parmas);
        return member;
    }
}
