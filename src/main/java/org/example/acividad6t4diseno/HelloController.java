package org.example.acividad6t4diseno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.synedra.validatorfx.Validator;


public class HelloController {



    @FXML
    private TextField labelCodigo;
    @FXML
    private TextField labeISBN;
    @FXML
    private RadioButton buttHombre;
    @FXML
    private TextField labelElectronico;
    @FXML
    private RadioButton butMujer;
    @FXML
    private TextField labelDNI;
    @FXML
    private DatePicker selecFecha;
    @FXML
    private TextField labelNombre;
    @FXML
    private TextField labelCredito;
    @FXML
    private ToggleGroup selecionGenero;


    private Validator validator;
    @FXML
    private Button guardar;

    @FXML
    private void initialize() {
        validator = new Validator();

        //Validacion: El nombre no puede estar vacio.
        validator.createCheck().dependsOn("nombre", labelNombre.textProperty()).withMethod(c -> {
            String nombre = c.get("nombre");
            if (nombre == null || nombre.trim().isEmpty()) {
                c.error("El nombre no puede estar vacio");
            }
        }).decorates(labelNombre);

        //validacion: Fecha de nacimiento que no sea nula.
        validator.createCheck().dependsOn("fecha", selecFecha.valueProperty()).withMethod(c -> {
            if (c.get("fecha") == null) {
                c.error("Debe selecionar una fecha de nacimiento");
            }
        }).decorates(selecFecha);

        //Validacion: Dni que tenga formato valido
        validator.createCheck().dependsOn("dni", labelDNI.textProperty()).withMethod(c -> {
            String dni = c.get("dni");
            if (!dni.matches("\\d{8}[A-Z]")) {
                c.error("El dni debe tener 8 digitos y una letra");
            }
        }).decorates(labelDNI);

        //Validacion: Correo electronico que tenga buen formato
        validator.createCheck().dependsOn("correo", labelElectronico.textProperty()).withMethod(c -> {
            String correo = c.get("correo");
            if (!correo.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                c.error("El correo no es valido");
            }
        }).decorates(labelElectronico);

        //validacion: codigo ISBN 10 o 13 digitos
        validator.createCheck().dependsOn("isbn", labeISBN.textProperty()).withMethod(c -> {
            String isbn = c.get("isbn");
            if (!isbn.matches("\\d{10}|\\d{13}")) {
                c.error("El isbn no es valido debe tener 10 o 13 digitos");
            }
        }).decorates(labeISBN);

        //Validacion: Tarjeta de credito
        validator.createCheck().dependsOn("credito", labelCredito.textProperty()).withMethod(c -> {
            String credito = c.get("credito");
            if (!validadTarjetaCredito(credito)) {
                c.error("El credito no es valido");
            }
        }).decorates(labelCredito);



        //Validacion: IBAN minimo 15 caracteres
        validator.createCheck().dependsOn("iban", labelCodigo.textProperty()).withMethod(c -> {
            String iban = c.get("iban");
            if(iban.trim().length() < 15){
                c.error("El codigo no es valido");
            }
        }).decorates(labelCodigo);

        //Validacion: de selecion de genero
        validator.createCheck().dependsOn("genero", selecionGenero.selectedToggleProperty()).withMethod(c -> {
            if (c.get("genero") == null) {
                c.error("Debe seleccionar un género");
            }
        }).decorates(buttHombre).decorates(butMujer);

    }
    @FXML
    private void guardarButtonClick(ActionEvent event) {
        if(validator.validate()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Formulario guardado correctamente.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, corrija los errores en el formulario.");
            alert.showAndWait();
        }
    }
    private boolean validadTarjetaCredito(String numeroTarjetaCredito){
        if(numeroTarjetaCredito == null || !numeroTarjetaCredito.matches("\\d+")){
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        for (int i = numeroTarjetaCredito.length() - 1; i >= 0; i--){
            int n = Integer.parseInt(numeroTarjetaCredito.substring(i, i + 1));
            if(alternate){
                n *= 2;
                if(n > 9){
                    n-= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

}