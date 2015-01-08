<?php

/*
 * Following code will delete a product from table
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['buscar1']) && isset($_POST['buscar2']) && isset($_POST['buscar3'])) {


    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

                $buscar1 = $_POST['buscar1'];
                $buscar2 = $_POST['buscar2'];
                $buscar3 = $_POST['buscar3'];


    // mysql update row with matched pid
    $result = mysql_query("DELETE FROM Horarios_mesa WHERE Horarios_mesa_fecha = '$buscar1' AND Horarios_mesa_hora = '$buscar2' AND Mesa_Nro_mesa = '$buscar3'");
    
           


    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "horario successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["Query"] = "DELETE FROM Horario_mesa WHERE Horarios_mesa_fecha = '$buscar1' AND Horarios_mesa_hora = '$buscar2' AND Mesa_Nro_mesa = '$buscar3'";
        $response["message"] = "No horarios found";

        // echo no users JSON
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