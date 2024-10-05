package com.yofujitsu.rksp_pr5.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.security.cert.CertPathBuilder;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class File {
    @Id
    @UuidGenerator
    private UUID id;

    private String fileName;

    private LocalDateTime createdAt;

    private Long fileSizeInMB;

    @Lob
    private byte[] fileData;
}
