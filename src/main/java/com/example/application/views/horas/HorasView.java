package com.example.application.views.horas;

import com.example.application.data.ControlHoras;
import com.example.application.data.Estudiante;
import com.example.application.services.ControlHorasService;
import com.example.application.services.EstudianteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@PageTitle("Control de Horas")
@Route(value = "horas", layout = MainLayout.class)
@PermitAll
public class HorasView extends VerticalLayout {

    private final ControlHorasService horasService;
    private final EstudianteService estudianteService;

    private final Grid<ControlHoras> grid = new Grid<>(ControlHoras.class, false);
    private final ComboBox<Estudiante> estudianteCombo = new ComboBox<>("Estudiante");
    private final DateTimePicker ingreso = new DateTimePicker("Ingreso");
    private final DateTimePicker salida = new DateTimePicker("Salida");
    private final NumberField horasTrabajadas = new NumberField("Horas trabajadas");
    private final TextArea actividades = new TextArea("Actividades realizadas");

    private ControlHoras registroActual = null;

    public HorasView(@Autowired ControlHorasService horasService,
                     @Autowired EstudianteService estudianteService) {
        this.horasService = horasService;
        this.estudianteService = estudianteService;

        configurarFormulario();
        configurarGrid();

        add(new HorizontalLayout(formulario(), grid));
        actualizarGrid();
    }

    private void configurarGrid() {
        grid.addColumn(control -> control.getEstudiante().getCarnet()).setHeader("Carnet");
        grid.addColumn(ControlHoras::getFechaHoraIngreso).setHeader("Ingreso");
        grid.addColumn(ControlHoras::getFechaHoraSalida).setHeader("Salida");
        grid.addColumn(ControlHoras::getHorasTrabajadas).setHeader("Horas");
        grid.addColumn(ControlHoras::getActividadesRealizadas).setHeader("Actividades");
        grid.addColumn(ControlHoras::getFechaHoraIngreso).setHeader("Ingreso");
        grid.addColumn(ControlHoras::getFechaHoraSalida).setHeader("Salida");
        grid.addColumn(ControlHoras::getHorasTrabajadas).setHeader("Horas");
        grid.addComponentColumn(registro -> {
            Button editar = new Button("Editar", e -> cargarFormulario(registro));
            Button eliminar = new Button("Eliminar", e -> {
                horasService.delete(registro);
                actualizarGrid();
                limpiarFormulario();
            });
            return new HorizontalLayout(editar, eliminar);
        }).setHeader("Acciones");
        grid.setAllRowsVisible(true);
    }

    private FormLayout formulario() {
        Button guardar = new Button("Guardar", e -> {
            if (registroActual == null) {
                registroActual = new ControlHoras();
            }

            registroActual.setEstudiante(estudianteCombo.getValue());
            registroActual.setFechaHoraIngreso(ingreso.getValue());
            registroActual.setFechaHoraSalida(salida.getValue());
            registroActual.setHorasTrabajadas(
                horasTrabajadas.getValue() != null ? BigDecimal.valueOf(horasTrabajadas.getValue()) : BigDecimal.ZERO
            );
            registroActual.setActividadesRealizadas(actividades.getValue());

            horasService.save(registroActual);
            Notification.show("Registro guardado");
            actualizarGrid();
            limpiarFormulario();
        });

        return new FormLayout(
            estudianteCombo, ingreso, salida,
            horasTrabajadas, actividades, guardar
        );
    }

    private void cargarFormulario(ControlHoras registro) {
        this.registroActual = registro;
        estudianteCombo.setValue(registro.getEstudiante());
        ingreso.setValue(registro.getFechaHoraIngreso());
        salida.setValue(registro.getFechaHoraSalida());
        horasTrabajadas.setValue(registro.getHorasTrabajadas().doubleValue());
        actividades.setValue(registro.getActividadesRealizadas());
    }

    private void limpiarFormulario() {
        registroActual = null;
        estudianteCombo.clear();
        ingreso.clear();
        salida.clear();
        horasTrabajadas.clear();
        actividades.clear();
    }

    private void actualizarGrid() {
        grid.setItems(horasService.findAll());
    }

    private void configurarFormulario() {
        estudianteCombo.setItems(estudianteService.findAll());
        estudianteCombo.setItemLabelGenerator(e -> e.getCarnet());
    }
}