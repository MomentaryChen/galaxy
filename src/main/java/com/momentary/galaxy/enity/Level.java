package com.momentary.galaxy.enity;
import java.time.LocalDateTime;

import org.hibernate.engine.internal.ForeignKeys;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentary.galaxy.constant.PlayerLevelEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name = "level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Level {

    @NonNull
    @Column
    @Id
    Integer level;

    @NonNull
    @Column
    String name;

    @Column
    String desc;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime updateDate = LocalDateTime.now();

}
