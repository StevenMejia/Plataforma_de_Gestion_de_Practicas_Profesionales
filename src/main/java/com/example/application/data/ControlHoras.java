package com.example.application.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "control_horas")
public class ControlHoras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;    private LocalDateTime fechaHoraIngreso;    private LocalDateTime fechaHoraSalida;    @DecimalMin("0.01")
    private BigDecimal horasTrabajadas;    private String actividadesRealizadas;

    public ControlHoras() {}

    public Long getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public LocalDateTime getFechaHoraIngreso() {        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public LocalDateTime getFechaHoraSalida() {        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {        this.fechaHoraSalida = fechaHoraSalida;
    }

    public BigDecimal getHorasTrabajadas() {        return horasTrabajadas;
    }

    public void setHorasTrabajadas(BigDecimal horasTrabajadas) {        this.horasTrabajadas = horasTrabajadas;
    }

    public String getActividadesRealizadas() {        return actividadesRealizadas;
    }

    public void setActividadesRealizadas(String actividadesRealizadas) {        this.actividadesRealizadas = actividadesRealizadas;
    }
}