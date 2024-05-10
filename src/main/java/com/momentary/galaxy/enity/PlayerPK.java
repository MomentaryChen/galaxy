package com.momentary.galaxy.enity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// @Embeddable
public class PlayerPK implements Serializable {

    @NonNull
    @Column
    private String name;

    @NonNull
    @Column
    private String team;

    // Getters and setters
}