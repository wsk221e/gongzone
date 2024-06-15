package com.gongzone.central.member.login.mapper;

import com.gongzone.central.member.domain.Member;
import com.gongzone.central.member.login.domain.LoginRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    Member getMemberFromId(String loginId);
}