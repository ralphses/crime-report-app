package com.clicks.crimereportapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReportedCrime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Crime crime;

    @ManyToOne
    private CrimeScene scene;
    private String reporter;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
    private boolean resolved;
}
