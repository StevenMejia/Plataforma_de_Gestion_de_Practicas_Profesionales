
package com.example.application.views.estudiantes;

import com.example.application.data.Estudiante;
import com.example.application.services.EstudianteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Estudiantes")
@Route(value = "estudiantes", layout = MainLayout.class)
@PermitAll
public class EstudiantesView extends VerticalLayout {

    private final EstudianteService estudianteService;

    private final TextField carnet = new TextField("Carnet");
    private final TextField primerNombre = new TextField("Primer Nombre");
    private final TextField segundoNombre = new TextField("Segundo Nombre");
    private final TextField primerApellido = new TextField("Primer Apellido");
    private final TextField segundoApellido = new TextField("Segundo Apellido");
    private final TextField numeroTelefono = new TextField("Número Teléfono");
    private final EmailField correo = new EmailField("Correo Electrónico");
    private final IntegerField edad = new IntegerField("Edad");
    private final TextField direccion = new TextField("Dirección");
    private final TextField carrera = new TextField("Carrera");

    private final Button guardar = new Button("Guardar");

    private final Grid<Estudiante> grid = new Grid<>(Estudiante.class, false);
    private final BeanValidationBinder<Estudiante> binder = new BeanValidationBinder<>(Estudiante.class);

    private Estudiante estudianteActual;

    public EstudiantesView(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 titulo = new H2("Estudiantes");

        FormLayout formLayout = new FormLayout();
        formLayout.add(carnet, primerNombre, segundoNombre, primerApellido, segundoApellido,
                numeroTelefono, correo, edad, direccion, carrera, guardar);

        binder.bindInstanceFields(this);

        guardar.addClickListener(e -> guardarEstudiante());

        configurarGrid();
        actualizarGrid();

        add(titulo, formLayout, grid);
    }

    private void guardarEstudiante() {
        if (estudianteActual == null) {
            estudianteActual = new Estudiante();
        }

        if (binder.writeBeanIfValid(estudianteActual)) {
            estudianteService.save(estudianteActual);
            estudianteActual = null;
            binder.readBean(null);
            actualizarGrid();
        }
    }

    private void configurarGrid() {
        grid.addColumn(Estudiante::getCarnet).setHeader("Carnet");
        grid.addColumn(Estudiante::getPrimerNombre).setHeader("Nombre");
        grid.addColumn(Estudiante::getPrimerApellido).setHeader("Apellido");
        grid.addColumn(Estudiante::getCorreo).setHeader("Correo");
        grid.addColumn(Estudiante::getNumeroTelefono).setHeader("Teléfono");
        grid.addColumn(Estudiante::getCarrera).setHeader("Carrera");

        grid.addComponentColumn(estudiante -> {
            Button editar = new Button("Editar", click -> editarEstudiante(estudiante));
            return editar;
        }).setHeader("Editar");

        grid.addComponentColumn(estudiante -> {
            Button eliminar = new Button("Eliminar", click -> {
                estudianteService.delete(estudiante);
                actualizarGrid();
            });
            return eliminar;
        }).setHeader("Eliminar");
    }

    private void editarEstudiante(Estudiante estudiante) {
        this.estudianteActual = estudiante;
        binder.readBean(estudiante);
    }

    private void actualizarGrid() {
        grid.setItems(estudianteService.findAll());
    }
}
