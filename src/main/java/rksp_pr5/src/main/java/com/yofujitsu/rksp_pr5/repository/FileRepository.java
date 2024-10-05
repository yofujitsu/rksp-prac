package com.yofujitsu.rksp_pr5.repository;

import com.yofujitsu.rksp_pr5.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
}
