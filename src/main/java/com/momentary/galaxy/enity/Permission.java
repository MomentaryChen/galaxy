package com.momentary.galaxy.enity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name = "galaxy_tb_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column
    // unique=true
    private String name;

    @NonNull
    @Column
    // unique=true
    private String desc;

    @NonNull
    @Column
    // unique=true
    private String operator;

    @NonNull
    @Column
    // unique=true
    private String url;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    @Builder.Default
    LocalDateTime updateDate = LocalDateTime.now();
}
