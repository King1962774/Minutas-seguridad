package com.minutas;

import com.minutas.config.Database;
import com.minutas.config.AdminServer;
import com.minutas.model.*;
import com.minutas.service.*;
import com.minutas.repository.local.SqliteUsuarioRepository;
import com.minutas.repository.local.SqliteConjuntoRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class App extends Application {
    private Stage primaryStage;
    private final AutenticacionService authService = new AutenticacionService();
    private final TurnoService turnoService = new TurnoService();
    private final VisitanteService visitanteService = new VisitanteService();
    private final NovedadService novedadService = new NovedadService();
    private final IncidenteService incidenteService = new IncidenteService();
    private final ReporteService reporteService = new ReporteService();
    private final SqliteUsuarioRepository usuarioRepo = new SqliteUsuarioRepository();
    private final SqliteConjuntoRepository conjuntoRepo = new SqliteConjuntoRepository();

    private Turno turnoActual;
    private int currentConjuntoId = 1;
    private String currentConjuntoNombre = "Altos de San Juan";
    private String tipoConjuntoActual = "TORRES";

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        try {
            Database.initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoginView();
    }

    private Label createClockLabel() {
        Label clockLabel = new Label();
        clockLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> 
            clockLabel.setText("🕒 " + LocalTime.now().format(formatter))
        ), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
        return clockLabel;
    }

    private void showLoginView() {
        primaryStage.setTitle("Minutas Seguridad - Iniciar Sesión");

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Minutas Seguridad");
        title.getStyleClass().add("title-label");
        Label subtitle = new Label("Control de Portería & Vigilancia Residencial");

        TextField txtUser = new TextField();
        txtUser.setPromptText("Usuario (ej. admin, vigilante)");
        txtUser.setMaxWidth(300);

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Contraseña");
        txtPass.setMaxWidth(300);

        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.getStyleClass().add("button-primary");
        btnLogin.setPrefWidth(300);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");

        btnLogin.setOnAction(e -> {
            Optional<Usuario> userOpt = authService.login(txtUser.getText(), txtPass.getText());
            if (userOpt.isPresent()) {
                Usuario u = userOpt.get();
                if ("ADMIN_CONJUNTO".equals(u.getRol()) || "SUPERVISOR".equals(u.getRol())) {
                    AdminServer.startServer(8080);
                    showMisConjuntosView();
                } else {
                    currentConjuntoId = u.getIdConjunto();
                    showAperturaTurnoView(u);
                }
            } else {
                lblError.setText("Usuario o contraseña incorrectos");
            }
        });

        root.getChildren().addAll(title, subtitle, new Separator(), txtUser, txtPass, btnLogin, lblError);
        Scene scene = new Scene(root, 450, 420);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMisConjuntosView() {
        primaryStage.setTitle("Minutas Seguridad - Mis Conjuntos");

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Gestión Multi-Conjunto");
        title.getStyleClass().add("title-label");
        Label subtitle = new Label("Seleccione el conjunto residencial a administrar:");

        ListView<Conjunto> listView = new ListView<>();
        Runnable refreshList = () -> {
            listView.getItems().setAll(conjuntoRepo.findAll());
        };
        refreshList.run();

        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Conjunto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " — Dir: " + item.getDireccion() + " (Tel: " + item.getTelefono() + ")");
                }
            }
        });

        Button btnSeleccionar = new Button("Ingresar al Conjunto Seleccionado");
        btnSeleccionar.getStyleClass().add("button-primary");
        btnSeleccionar.setOnAction(e -> {
            Conjunto seleccionado = listView.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                currentConjuntoId = seleccionado.getId();
                currentConjuntoNombre = seleccionado.getNombre();
                showAdminDashboard();
            }
        });

        Button btnAgregar = new Button("+ Agregar Nuevo Conjunto");
        btnAgregar.getStyleClass().add("button-success");
        btnAgregar.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Nuevo Conjunto");
            VBox box = new VBox(15);
            box.setPadding(new Insets(20));

            TextField txtNom = new TextField(); txtNom.setPromptText("Nombre del conjunto");
            TextField txtNit = new TextField(); txtNit.setPromptText("NIT");
            TextField txtDir = new TextField(); txtDir.setPromptText("Dirección");
            TextField txtTel = new TextField(); txtTel.setPromptText("Teléfono");

            Button btnSave = new Button("Guardar");
            btnSave.getStyleClass().add("button-primary");
            btnSave.setOnAction(ev -> {
                Conjunto c = new Conjunto();
                c.setNombre(txtNom.getText());
                c.setNit(txtNit.getText());
                c.setDireccion(txtDir.getText());
                c.setTelefono(txtTel.getText());
                conjuntoRepo.save(c);
                refreshList.run();
                stage.close();
            });

            box.getChildren().addAll(new Label("Registrar Conjunto"), txtNom, txtNit, txtDir, txtTel, btnSave);
            stage.setScene(new Scene(box, 350, 320));
            stage.show();
        });

        Button btnLogout = new Button("Cerrar Sesión");
        btnLogout.setOnAction(e -> showLoginView());

        HBox actions = new HBox(15, btnSeleccionar, btnAgregar);
        actions.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, subtitle, listView, actions, new Separator(), btnLogout);
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showAperturaTurnoView(Usuario usuario) {
        primaryStage.setTitle("Minutas Seguridad - Apertura de Turno (" + currentConjuntoNombre + ")");

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Apertura de Turno (12 Horas)");
        title.getStyleClass().add("title-label");

        ComboBox<String> cbPuesto = new ComboBox<>(FXCollections.observableArrayList("PRINCIPAL", "VEHICULAR", "PEATONAL"));
        cbPuesto.setValue("PRINCIPAL");
        cbPuesto.setPrefWidth(300);

        ComboBox<String> cbTipo = new ComboBox<>(FXCollections.observableArrayList("DIA", "NOCHE"));
        cbTipo.setValue("DIA");
        cbTipo.setPrefWidth(300);

        Button btnIniciar = new Button("Confirmar Apertura de Turno");
        btnIniciar.getStyleClass().add("button-success");
        btnIniciar.setPrefWidth(300);

        btnIniciar.setOnAction(e -> {
            turnoActual = turnoService.abrirTurno(currentConjuntoId, usuario.getId(), cbPuesto.getValue(), cbTipo.getValue());
            showMainKioscoView();
        });

        root.getChildren().addAll(title, new Label("Conjunto: " + currentConjuntoNombre), cbPuesto, cbTipo, btnIniciar);
        Scene scene = new Scene(root, 450, 380);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showMainKioscoView() {
        primaryStage.setTitle("Minutas Seguridad - Kiosco (" + currentConjuntoNombre + ")");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        Label lblConjunto = new Label("🏢 " + currentConjuntoNombre);
        lblConjunto.setStyle("-fx-font-weight: bold;");
        Label clock = createClockLabel();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnPanico = new Button("🚨 BOTÓN DE PÁNICO");
        btnPanico.getStyleClass().add("button-danger");
        btnPanico.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        btnPanico.setOnAction(e -> {
            incidenteService.registrarPanico(currentConjuntoId, turnoActual.getId(), AutenticacionService.getUsuarioActual().getId(), "¡Alarma de pánico activada!");
            Alert alert = new Alert(Alert.AlertType.ERROR, "¡ALERTA DE PÁNICO REGISTRADA!", ButtonType.OK);
            alert.showAndWait();
        });
        topBar.getChildren().addAll(lblConjunto, clock, spacer, btnPanico);
        root.setTop(topBar);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        Button btnVisitante = new Button("👤 Registrar Visitante");
        btnVisitante.setPrefSize(220, 120);
        btnVisitante.getStyleClass().add("button-primary");
        btnVisitante.setOnAction(e -> showRegistroVisitanteView());

        Button btnNovedad = new Button("📝 Agregar Novedad");
        btnNovedad.setPrefSize(220, 120);
        btnNovedad.getStyleClass().add("button-primary");
        btnNovedad.setOnAction(e -> showAgregarNovedadView());

        Button btnCerrarTurno = new Button("🔒 Cierre de Turno");
        btnCerrarTurno.setPrefSize(220, 120);
        btnCerrarTurno.getStyleClass().add("button-danger");
        btnCerrarTurno.setOnAction(e -> showCierreTurnoView());

        grid.add(btnVisitante, 0, 0);
        grid.add(btnNovedad, 1, 0);
        grid.add(btnCerrarTurno, 0, 1);

        root.setCenter(grid);

        Scene scene = new Scene(root, 650, 550);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void showRegistroVisitanteView() {
        Stage stage = new Stage();
        stage.setTitle("Registrar Visitante");
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));

        TextField txtNombre = new TextField(); txtNombre.setPromptText("Nombre completo");
        TextField txtDoc = new TextField(); txtDoc.setPromptText("Documento de identidad");
        TextField txtTel = new TextField(); txtTel.setPromptText("Teléfono");
        TextField txtPlaca = new TextField(); txtPlaca.setPromptText("Placa Vehículo (opcional)");
        TextField txtUnidad = new TextField(); txtUnidad.setPromptText("Torre / Casa / Apto");
        TextArea txtObs = new TextArea(); txtObs.setPromptText("Motivo de visita");
        txtObs.setPrefHeight(60);

        Label lblMsg = new Label();

        Button btnGuardar = new Button("Registrar Ingreso");
        btnGuardar.getStyleClass().add("button-success");
        btnGuardar.setOnAction(e -> {
            try {
                Visitante v = new Visitante();
                v.setIdConjunto(currentConjuntoId);
                v.setNombre(txtNombre.getText());
                v.setDocumento(txtDoc.getText());
                v.setTelefono(txtTel.getText());
                v.setObservaciones(txtObs.getText());
                v.setListaNegra(0);

                RegistroVisita rv = new RegistroVisita();
                rv.setIdConjunto(currentConjuntoId);
                rv.setIdTurno(turnoActual != null ? turnoActual.getId() : 1);
                rv.setVehiculoPlaca(txtPlaca.getText());
                rv.setObservacion(txtUnidad.getText() + " - " + txtObs.getText());

                visitanteService.registrarVisita(v, rv);
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Visitante registrado exitosamente.", ButtonType.OK);
                alert.showAndWait();
            } catch (SecurityException ex) {
                lblMsg.setText(ex.getMessage());
                lblMsg.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            } catch (Exception ex) {
                lblMsg.setText("Error: " + ex.getMessage());
            }
        });

        box.getChildren().addAll(new Label("Nuevo Ingreso de Visitante"), txtNombre, txtDoc, txtTel, txtPlaca, txtUnidad, txtObs, btnGuardar, lblMsg);
        Scene scene = new Scene(box, 400, 520);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void showAgregarNovedadView() {
        Stage stage = new Stage();
        stage.setTitle("Agregar Novedad");
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));

        ComboBox<String> cbCat = new ComboBox<>(FXCollections.observableArrayList("GENERAL", "SEGURIDAD", "MANTENIMIENTO", "QUEJA"));
        cbCat.setValue("GENERAL");
        TextArea txtDesc = new TextArea(); txtDesc.setPromptText("Descripción...");

        Button btnGuardar = new Button("Guardar Novedad");
        btnGuardar.getStyleClass().add("button-primary");
        btnGuardar.setOnAction(e -> {
            if (turnoActual != null) {
                novedadService.agregarNovedad(currentConjuntoId, turnoActual.getId(), cbCat.getValue(), txtDesc.getText());
                stage.close();
            }
        });

        box.getChildren().addAll(new Label("Nueva Novedad de Turno"), cbCat, txtDesc, btnGuardar);
        Scene scene = new Scene(box, 400, 320);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void showCierreTurnoView() {
        Stage stage = new Stage();
        stage.setTitle("Cierre de Turno");
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));

        Label lblReq = new Label("El informe de pendientes es OBLIGATORIO.");
        lblReq.setStyle("-fx-font-weight: bold; -fx-text-fill: #dc2626;");

        TextArea txtPendientes = new TextArea();
        txtPendientes.setPromptText("Describa los pendientes...");
        txtPendientes.setPrefHeight(120);

        Label lblError = new Label();

        Button btnCerrar = new Button("Cerrar Turno & Generar PDF");
        btnCerrar.getStyleClass().add("button-danger");
        btnCerrar.setOnAction(e -> {
            try {
                InformeTurno informe = new InformeTurno();
                informe.setPendientes(txtPendientes.getText());
                informe.setResumenVisitantes(5);
                informe.setResumenVehiculos(3);
                informe.setResumenPaquetes(2);

                turnoService.cerrarTurno(turnoActual.getId(), informe);

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar Informe PDF");
                fileChooser.setInitialFileName("informe_" + turnoActual.getId() + ".pdf");
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    reporteService.generarPdfInformeTurno(turnoActual, informe, file.getAbsolutePath());
                }

                stage.close();
                showLoginView();
            } catch (Exception ex) {
                lblError.setText(ex.getMessage());
            }
        });

        box.getChildren().addAll(lblReq, txtPendientes, btnCerrar, lblError);
        Scene scene = new Scene(box, 450, 380);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void showAdminDashboard() {
        primaryStage.setTitle("Minutas Seguridad - Dashboard (" + currentConjuntoNombre + ")");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        VBox top = new VBox(10);
        HBox headerBar = new HBox(15);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Dashboard: " + currentConjuntoNombre);
        title.getStyleClass().add("title-label");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label clock = createClockLabel();

        Button btnVolverConjuntos = new Button("← Cambiar Conjunto");
        btnVolverConjuntos.setOnAction(e -> showMisConjuntosView());

        headerBar.getChildren().addAll(btnVolverConjuntos, title, spacer, clock);
        top.getChildren().add(headerBar);

        TabPane tabPane = new TabPane();

        // Tab 1: Indicadores
        Tab tabDashboard = new Tab("Indicadores Operativos");
        tabDashboard.setClosable(false);
        VBox dashBox = new VBox(15);
        dashBox.setPadding(new Insets(15));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Estadísticas del Conjunto");
        xAxis.setLabel("Módulo");
        yAxis.setLabel("Cantidad");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Registros");
        series.getData().add(new XYChart.Data<>("Visitantes", 12));
        series.getData().add(new XYChart.Data<>("Vehículos", 8));
        series.getData().add(new XYChart.Data<>("Paquetes", 5));
        barChart.getData().add(series);

        dashBox.getChildren().add(barChart);
        tabDashboard.setContent(dashBox);

        // Tab 2: Gestión de Guardias (Scoped to currentConjuntoId)
        Tab tabGuardias = new Tab("Gestión de Guardias");
        tabGuardias.setClosable(false);
        VBox guardBox = new VBox(15);
        guardBox.setPadding(new Insets(15));

        TextField txtGNombre = new TextField(); txtGNombre.setPromptText("Nombre del vigilante");
        TextField txtGUser = new TextField(); txtGUser.setPromptText("Usuario login");
        PasswordField txtGPass = new PasswordField(); txtGPass.setPromptText("Contraseña");

        Button btnCrearGuardi = new Button("Crear Cuenta de Guardia");
        btnCrearGuardi.getStyleClass().add("button-primary");

        ListView<String> listaGuardias = new ListView<>();
        Runnable refreshGuardias = () -> {
            listaGuardias.getItems().clear();
            var usuarios = usuarioRepo.findAll(currentConjuntoId);
            for (var u : usuarios) {
                listaGuardias.getItems().add(u.getNombre() + " (" + u.getUsername() + ") - Rol: " + u.getRol());
            }
        };
        refreshGuardias.run();

        btnCrearGuardi.setOnAction(e -> {
            Usuario nuevo = new Usuario();
            nuevo.setIdConjunto(currentConjuntoId);
            nuevo.setNombre(txtGNombre.getText());
            nuevo.setUsername(txtGUser.getText());
            nuevo.setPassword(txtGPass.getText());
            nuevo.setRol("VIGILANTE");
            nuevo.setActivo(1);
            usuarioRepo.save(nuevo);
            refreshGuardias.run();
            txtGNombre.clear(); txtGUser.clear(); txtGPass.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Guardia creado para " + currentConjuntoNombre, ButtonType.OK);
            alert.showAndWait();
        });

        guardBox.getChildren().addAll(new Label("Vigilantes Asignados a " + currentConjuntoNombre), txtGNombre, txtGUser, txtGPass, btnCrearGuardi, listaGuardias);
        tabGuardias.setContent(guardBox);

        tabPane.getTabs().addAll(tabDashboard, tabGuardias);
        root.setTop(top);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 750, 600);
        scene.getStylesheets().add(getClass().getResource("/com/minutas/css/main.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        if (args.length > 0 && "--mode=server".equals(args[0])) {
            System.out.println("Iniciando Servidor Headless...");
            Database.initializeDatabase();
            int port = 8080;
            if (args.length > 1 && args[1].startsWith("--port=")) {
                port = Integer.parseInt(args[1].substring(7));
            }
            AdminServer.startServer(port);
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            launch(args);
        }
    }
}
