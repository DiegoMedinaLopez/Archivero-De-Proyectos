package archiverodeproyectos;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;

/**
 *
 * @author Diego Jesus Medina Lopez
 */
public class ArchiveroDeProyectos extends Application {
    private Connection connect;
    private Statement ejec;
    private ResultSet result;
    private GridPane GP, GPL, GPR, GPU, GPD;
    private Button Create, Read, Update, Delete;
    private Button Insertar, BuscarPorID, BuscarPorNombre, Subir, Borrar, MostrarTodo;
    private TableView TB;
    private TextField NomTF, FIniTF, FFinTF, EspecifTF;
    private TextField BuscarPorIDTF, BuscarPorNombreTF, ActuTF;
    private TextField BorrarTF;
    private Label NomL, FIniL, FFinL, EspecifL;
    private TableColumn TCID, TCNom, TCFIni, TCFFin, TCEspecif;
    @Override
    public void start(Stage primaryStage) {
        //GridPanes a agregar
        this.GP = new GridPane();
        GP.setAlignment(Pos.TOP_CENTER);
        GP.setHgap(50);
        GP.setVgap(50);
        GP.setPadding(new Insets(25, 25, 25, 25));
        this.GPL = new GridPane();
        GPL.setAlignment(Pos.BOTTOM_CENTER);
        GPL.setHgap(40);
        GPL.setVgap(40);
        GPL.setPadding(new Insets(25, 25, 25, 25));
        this.GPR = new GridPane();
        GPR.setAlignment(Pos.BOTTOM_CENTER);
        GPR.setHgap(40);
        GPR.setVgap(40);
        GPR.setPadding(new Insets(25, 25, 25, 25));
        this.GPU = new GridPane();
        GPU.setAlignment(Pos.BOTTOM_CENTER);
        GPU.setHgap(40);
        GPU.setVgap(40);
        GPU.setPadding(new Insets(25, 25, 25, 25));
        this.GPD = new GridPane();
        GPD.setAlignment(Pos.BOTTOM_CENTER);
        GPD.setHgap(40);
        GPD.setVgap(40);
        GPD.setPadding(new Insets(25, 25, 25, 25));
        this.iniciarBD();
        //Boton Crear
        this.Create = new Button();
        Create.setText("Crear");
        Create.setAlignment(Pos.CENTER); 
        Create.setPrefSize(100, 30);
        Create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Se insertarán datos");
                //Se agregan Labels
                GP.getChildren().remove(GPL);
                GP.getChildren().remove(GPR);
                GP.getChildren().remove(GPU);
                GP.getChildren().remove(GPD);
                
                NomL = new Label("Nombre Del Proyecto: ");
                GPL.add(NomL, 0, 0, 1, 1);
                FIniL = new Label("Fecha de Inicio: ");
                GPL.add(FIniL, 0, 1, 1, 1);
                FFinL = new Label("Fecha de Finalización: ");
                GPL.add(FFinL, 0, 2, 1, 1);
                EspecifL = new Label("Descripción Corta: ");
                GPL.add(EspecifL, 0, 3, 1, 1);
                
                //Se agregan TextFields
                NomTF = new TextField();
                NomTF.setPrefSize(200, 20);
                GPL.add(NomTF, 1, 0, 1, 1);
                FIniTF = new TextField();
                FIniTF.setPrefSize(200, 20);
                GPL.add(FIniTF, 1, 1, 1, 1);
                FFinTF = new TextField();
                FFinTF.setPrefSize(200, 20);
                GPL.add(FFinTF, 1, 2, 1, 1);
                EspecifTF = new TextField();
                EspecifTF.setPrefSize(200, 20);
                GPL.add(EspecifTF, 1, 3, 1, 1);
                
                Insertar = new Button();
                Insertar.setText("Insertar");
                Insertar.setAlignment(Pos.CENTER);
                Insertar.setPrefSize(100, 30);
                Insertar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!NomTF.getText().isEmpty()&&!FIniTF.getText().isEmpty()){
                            try{
                                String comando = "insert into proyectos (Nombre, FIni, FFin, Especif) value (?,?,?,?)";
                                PreparedStatement declaracion = connect.prepareStatement(comando);
                                declaracion.setString(1, NomTF.getText());
                                declaracion.setString(2, FIniTF.getText());
                                declaracion.setString(3, FFinTF.getText());
                                declaracion.setString(4, EspecifTF.getText());
                                int registro = declaracion.executeUpdate();
                                if (registro>=0){
                                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                                }
                            }catch(Exception ex){
                                JOptionPane.showMessageDialog(null,"No se pudo hacer registro: \n"+ex);
                                System.exit(0);
                            }
                            //Reinicia los campos
                            NomTF.setText("");
                            FIniTF.setText("");
                            FFinTF.setText("");
                            EspecifTF.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se puede ejecutar registro \nlos datos deben ser: \n Cadena\n Fecha en formato numerico\n Fecha en formato numerico\n Cadena");
                        }
                    }
                });
                GPL.add(Insertar, 2, 0, 1, 4);
                
                //Se Agregan todos los compnentes
                GP.add(GPL, 0, 1, 4, 1);
            }
        });
        GP.add(Create, 0, 0, 1, 1);
        //Boton Leer
        this.Read = new Button();
        Read.setText("Leer");
        Read.setPrefSize(100, 30);
        Read.setAlignment(Pos.CENTER);
        Read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GP.getChildren().remove(GPL);
                GP.getChildren().remove(GPR);
                GP.getChildren().remove(GPU);
                GP.getChildren().remove(GPD);
                System.out.println("Se hará una consulta");
                TB = new TableView();
                TB.setPrefSize(400, 200);
                TCID = new TableColumn("ProyectoID");
                TCNom = new TableColumn("NombreProyecto");
                TCFIni = new TableColumn("FechaDeInicio");
                TCFFin = new TableColumn("FechaDeFinalización");
                TCEspecif = new TableColumn("Descripción");
                TCID.setCellValueFactory(new PropertyValueFactory<>("proyectoID"));
                TCNom.setCellValueFactory(new PropertyValueFactory<>("nomProy"));
                TCFIni.setCellValueFactory(new PropertyValueFactory<>("fechaInic"));
                TCFFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
                TCEspecif.setCellValueFactory(new PropertyValueFactory<>("descrProy"));
                TB.getColumns().addAll(TCID, TCNom, TCFIni, TCFFin, TCEspecif);
                GPR.add(TB, 0, 2, 3, 1);
                
                BuscarPorIDTF = new TextField();
                BuscarPorIDTF.setPrefSize(200, 20);
                GPR.add(BuscarPorIDTF, 0, 1, 1, 1);
                
                BuscarPorID = new Button();
                BuscarPorID.setText("Buscar por ID");
                BuscarPorID.setPrefSize(200, 30);
                BuscarPorID.setAlignment(Pos.CENTER);
                BuscarPorID.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Se ejecutará una consulta");
                        try{
                            ejec = (Statement)connect.createStatement();
                            result = ejec.executeQuery("select * from proyectos where ProyID = "+BuscarPorIDTF.getText());
                            if (result.equals(""))
                                System.out.println("NO SE HIZO");
                            int i = 1;
                            while(result.next()){
                                ProyectoEntidad PE = new ProyectoEntidad(result.getInt("ProyID"), result.getString("Nombre"), result.getString("FIni"), result.getString("FFin"), result.getString("Especif"));
                                TB.getItems().add(PE);
                                i++;
                            }
                        }catch(Exception ex){
                                JOptionPane.showMessageDialog(null,"No se pudo hacer registro: \n"+ex);
                                //System.exit(0);
                        }
                    }
                });
                GPR.add(BuscarPorID, 0, 0, 1, 1);
                
                MostrarTodo = new Button();
                MostrarTodo.setText("Mostrar Todo");
                MostrarTodo.setPrefSize(200, 30);
                MostrarTodo.setAlignment(Pos.CENTER);
                MostrarTodo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println("Se ejecutará una consulta");
                        try{
                            ejec = (Statement)connect.createStatement();
                            
                            result = ejec.executeQuery("select * from proyectos");
                            int i = 1;
                            while(result.next()){
                                ProyectoEntidad PE = new ProyectoEntidad(result.getInt("ProyID"), result.getString("Nombre"), result.getString("FIni"), result.getString("FFin"), result.getString("Especif"));
                                
                                TB.getItems().add(PE);
                                i++;
                            }
                        }catch(Exception ex){
                                JOptionPane.showMessageDialog(null,"No se pudo hacer registro: \n"+ex);
                                //System.exit(0);
                        }
                    }
                });
                GPR.add(MostrarTodo, 2, 0, 1, 2);
                
                BuscarPorNombreTF = new TextField();
                BuscarPorNombreTF.setPrefSize(200, 20);
                GPR.add(BuscarPorNombreTF, 1, 1, 1, 1);
                
                BuscarPorNombre = new Button();
                BuscarPorNombre.setText("Buscar por Nombre");
                BuscarPorNombre.setPrefSize(200, 30);
                BuscarPorNombre.setAlignment(Pos.CENTER);
                BuscarPorNombre.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event){
                        try{
                            ejec = (Statement)connect.createStatement();
                            result = ejec.executeQuery("select * from proyectos where Nombre = '"+BuscarPorNombreTF.getText()+"'");
                            if (result.equals(""))
                                System.out.println("NO SE HIZO");
                            int i = 1;
                            while(result.next()){
                                ProyectoEntidad PE = new ProyectoEntidad(result.getInt("ProyID"), result.getString("Nombre"), result.getString("FIni"), result.getString("FFin"), result.getString("Especif"));
                                
                                TB.getItems().add(PE);
                                i++;
                            }
                        }catch(Exception ex){
                                JOptionPane.showMessageDialog(null,"No se pudo hacer registro: \n"+ex);
                                //System.exit(0);
                        }
                    }
                });
                GPR.add(BuscarPorNombre, 1, 0, 1, 1);
                
                GP.add(GPR, 0, 1, 4, 1);
            }
        });
        GP.add(Read, 1, 0, 1, 1);
        //Boton Actualizar
        this.Update = new Button();
        Update.setText("Actualizar");
        Update.setPrefSize(100, 30);
        Update.setAlignment(Pos.CENTER);
        Update.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Se hará una actualización");
                GP.getChildren().remove(GPL);
                GP.getChildren().remove(GPR);
                GP.getChildren().remove(GPU);
                GP.getChildren().remove(GPD);
                
                NomL = new Label("Nombre Del Proyecto: ");
                GPU.add(NomL, 0, 0, 1, 1);
                FIniL = new Label("Fecha de Inicio: ");
                GPU.add(FIniL, 0, 1, 1, 1);
                FFinL = new Label("Fecha de Finalización: ");
                GPU.add(FFinL, 0, 2, 1, 1);
                EspecifL = new Label("Descripción Corta: ");
                GPU.add(EspecifL, 0, 3, 1, 1);
                
                //Se agregan TextFields
                NomTF = new TextField();
                NomTF.setPrefSize(200, 20);
                GPU.add(NomTF, 1, 0, 1, 1);
                FIniTF = new TextField();
                FIniTF.setPrefSize(200, 20);
                GPU.add(FIniTF, 1, 1, 1, 1);
                FFinTF = new TextField();
                FFinTF.setPrefSize(200, 20);
                GPU.add(FFinTF, 1, 2, 1, 1);
                EspecifTF = new TextField();
                EspecifTF.setPrefSize(200, 20);
                GPU.add(EspecifTF, 1, 3, 1, 1);
                
                ActuTF = new TextField();
                ActuTF.setPrefSize(200, 20);
                ActuTF.setAlignment(Pos.CENTER);
                GPU.add(ActuTF, 2, 0, 1, 2);
                
                Subir = new Button();
                Subir.setText("Subir");
                Subir.setAlignment(Pos.CENTER);
                Subir.setPrefSize(100, 30);
                Subir.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!NomTF.getText().isEmpty()&&!FIniTF.getText().isEmpty()&&!ActuTF.getText().isEmpty()){
                            try{
                                String comando = "UPDATE proyectos SET Nombre = '"+NomTF.getText()+"', FIni = '"+FIniTF.getText()+"', FFin = '"+FFinTF.getText()+"', Especif = '"+EspecifTF.getText()+"' WHERE ProyId = "+ActuTF.getText();
                                PreparedStatement declaracion = connect.prepareStatement(comando);
                                int registro = declaracion.executeUpdate();
                                if (registro>=0){
                                    JOptionPane.showMessageDialog(null, "Actualización exitosa");
                                }
                            }catch(Exception ex){
                                JOptionPane.showMessageDialog(null,"No se pudo hacer registro: \n"+ex);
                                System.exit(0);
                            }
                            //Reinicia los campos
                            NomTF.setText("");
                            FIniTF.setText("");
                            FFinTF.setText("");
                            EspecifTF.setText("");
                            ActuTF.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se puede ejecutar registro \nlos datos deben ser: \n Cadena\n Fecha en formato numerico\n Fecha en formato numerico\n Cadena");
                        }
                    }
                });
                GPU.add(Subir, 2, 2, 1, 2);
                
                //Se Agregan todos los compnentes
                GP.add(GPU, 0, 1, 4, 1);
            }
        });
        GP.add(Update, 2, 0, 1, 1);
        //Boton Borrar
        this.Delete = new Button();
        Delete.setText("Borrar");
        Delete.setPrefSize(100, 30);
        Delete.setAlignment(Pos.CENTER);
        Delete.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Se borrarán datos");
                GP.getChildren().remove(GPL);
                GP.getChildren().remove(GPR);
                GP.getChildren().remove(GPU);
                GP.getChildren().remove(GPD);
                
                BorrarTF = new TextField();
                BorrarTF.setPrefSize(200, 20);
                BorrarTF.setAlignment(Pos.CENTER);
                GPD.add(BorrarTF, 0, 0, 1, 1);
                
                Borrar = new Button();
                Borrar.setText("Borrar");
                Borrar.setAlignment(Pos.CENTER);
                Borrar.setPrefSize(100, 30);
                Borrar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!BorrarTF.getText().isEmpty()){
                            try{
                                String comando = "DELETE FROM proyectos WHERE ProyID = "+BorrarTF.getText();
                                PreparedStatement declaracion = connect.prepareStatement(comando);
                                int registro = declaracion.executeUpdate();
                                if (registro>=0){
                                    JOptionPane.showMessageDialog(null, "Borrado exitoso");
                                }
                            }catch(Exception ex){
                                JOptionPane.showMessageDialog(null,"No se pudo hacer registro: \n"+ex);
                                System.exit(0);
                            }
                            //Reinicia los campos
                            BorrarTF.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se puede ejecutar registro \nlos datos deben ser: \n Cadena\n Fecha en formato numerico\n Fecha en formato numerico\n Cadena");
                        }
                    }
                });
                GPD.add(Borrar, 1, 0, 1, 1);
                
                GP.add(GPD, 0, 1, 4, 1);
            }
        });
        GP.add(Delete, 3, 0, 1, 1);
        
        Scene scene = new Scene(GP, 750, 600);
        
        primaryStage.setTitle("Administrador de Proyectos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Conector de la base de datos
    public void iniciarBD(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/controldeproyectos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            JOptionPane.showMessageDialog(null,"Conectado Exitosamente a la Base de Datos");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al conectar::: "+ e);
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
