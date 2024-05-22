package com.momentary.galaxy.enity;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "galaxy_tb_api_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApiClient {

    @Id
    private String id;

    @NonNull
    @Column
    private String username;

    @NonNull
    @Column
    private String userSecret;

    @NonNull
    @Column
    private Boolean user_enable;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GalaxyRole> roles;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    @Builder.Default
    LocalDateTime updateDate = LocalDateTime.now();
    
}
