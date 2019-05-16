package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    Announcement findAnnouncementById(Long id);



}
