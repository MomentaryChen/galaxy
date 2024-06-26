package com.momentary.galaxy.enity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name = "galaxy_tb_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GalaxyRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column
    private String name;

    @Column
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Permission> permissions;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    @Builder.Default
    LocalDateTime updateDate = LocalDateTime.now();

}
