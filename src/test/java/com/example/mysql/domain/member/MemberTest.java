package com.example.mysql.domain.member;

import com.example.mysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다,")
    @Test
    public void testChangeName() {
        var member = MemberFixtureFactory.create();
        var expected = "kim";

        member.changeNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overMaxLengthName = "kimjisukimjisu";

        Assertions.assertThrows(IllegalArgumentException.class, () -> member.changeNickname(overMaxLengthName));
    }
}
