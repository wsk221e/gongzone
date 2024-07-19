package com.gongzone.central.member.myInfo.profile.service;

import com.gongzone.central.member.myInfo.profile.domain.Profile;
import org.springframework.web.multipart.MultipartFile;


public interface ProfileService {
    Profile getProfile(String memberNo);
    void addProfilePicture(String memberNo, MultipartFile file);
    // 프로필 수정
    void updateProfilePicture(String memberNo, MultipartFile file);
}
