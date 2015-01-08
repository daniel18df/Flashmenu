<?php
$buscar=$_POST["buscar"];

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

////////mesa
$resultado = mysql_query("SELECT *FROM Mesa WHERE Restaurant_idRestaurant = $buscar") or die(mysql_error());

if (mysql_num_rows($resultado) > 0) {

  while ($row = mysql_fetch_array($resultado)) {
       
        $Nro_mesa = $row["Nro_mesa"];
        $Mesa_nro = $row["Mesa_nro"];
        $Mesa_descripcion = $row["Mesa_descripcion"];
        $Mesa_cantPersonas = $row["Mesa_cantPersonas"];
        $Restaurant_idRestaurant = $row["Restaurant_idRestaurant"];
    }
   
}
 else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No mesas found";

    // echo no users JSON
    echo json_encode($response);
}
/////////mesa


$result = mysql_query("SELECT *FROM Horarios_mesa WHERE Mesa_Nro_mesa = $Nro_mesa") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["horarios_mesa"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $horarios_mesa = array();
        $horarios_mesa["idHorarios_mesa"] = $row["idHorarios_mesa"];
        $horarios_mesa["Horarios_mesa_fecha"] = $row["Horarios_mesa_fecha"];
        $horarios_mesa["Horarios_mesa_hora"] = $row["Horarios_mesa_hora"];
        $horarios_mesa["Mesa_Nro_mesa"] = $row["Mesa_Nro_mesa"];
        //$product["created_at"] = $row["created_at"];
        //$product["updated_at"] = $row["updated_at"];



        // push single product into final response array
        array_push($response["horarios_mesa"], $horarios_mesa);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No horarios_mesa found";

    // echo no users JSON
    echo json_encode($response);
}
?>
