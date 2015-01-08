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



$result = mysql_query("SELECT *FROM Mesa WHERE Restaurant_idRestaurant = $buscar") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["mesa"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $mesa = array();
        $mesa["Nro_mesa"] = $row["Nro_mesa"];
        $mesa["Mesa_nro"] = $row["Mesa_nro"];
        $mesa["Mesa_descripcion"] = $row["Mesa_descripcion"];
        $mesa["Mesa_cantPersonas"] = $row["Mesa_cantPersonas"];
        $mesa["Restaurant_idRestaurant"] = $row["Restaurant_idRestaurant"];
        //$product["created_at"] = $row["created_at"];
        //$product["updated_at"] = $row["updated_at"];



        // push single product into final response array
        array_push($response["mesa"], $mesa);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No mesa found";

    // echo no users JSON
    echo json_encode($response);
}
?>
