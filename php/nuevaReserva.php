<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['Reserva_fecha']) && isset($_POST['Reserva_hora']) && isset($_POST['Cliente_idCliente'])) {
    
    //$idAdm = $_POST['idAdministrador_restaurant'];
    $Reserva_fecha = $_POST['Reserva_fecha'];
    $Reserva_hora = $_POST['Reserva_hora'];
    $Reserva_total = $_POST['Reserva_total'];
    $Reserva_direccion = $_POST['Reserva_direccion'];
    $Reserva_detalleProductos = $_POST['Reserva_detalleProductos'];
    $Reserva_emailRest = $_POST['Reserva_emailRest'];
    $Mesa_Nro_mesa = $_POST['Mesa_Nro_mesa'];
    $Cliente_idCliente = $_POST['Cliente_idCliente'];


    // include db connect class
    require_once __DIR__ . '/db_connect.php';

        // import database connection variables
        require_once __DIR__ . '/db_config.php';

        // Connecting to mysql database
        $link = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD,DB_DATABASE) or die(mysql_error());

        // Selecing database
        //$db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());

        // returing connection cursor
    // connecting to db
    //$db = new DB_CONNECT();
//Reserva_fecha   Reserva_hora    Reserva_total   Reserva_direccion   Reserva_detalleProductos    Mesa_Nro_mesa   Cliente_idCliente
    // mysql inserting a new row
    $result = mysqli_query($link, "INSERT INTO Reserva(Reserva_fecha, Reserva_hora, Reserva_total, Reserva_direccion, Reserva_detalleProductos, Reserva_emailRest, Mesa_Nro_mesa, Cliente_idCliente) VALUES('$Reserva_fecha' ,'$Reserva_hora','$Reserva_total','$Reserva_direccion','$Reserva_detalleProductos', '$Reserva_emailRest', '$Mesa_Nro_mesa', '$Cliente_idCliente')");
    $idReserva = mysqli_insert_id($link);
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["LastId"] = $idReserva;
        $response["message"] = "Reserva successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>