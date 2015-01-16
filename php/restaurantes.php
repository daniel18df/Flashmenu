<?php
//header('Content-Type: text/html; charset=UTF-8'); 
$buscar=$_POST["buscar"];
/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();
//$response->mysql_set_charset('utf8');

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();


// get all products from products table
//$result->mysql_set_charset('utf8');
$result = mysql_query("SELECT * FROM Restaurant WHERE Rest_nombre = '$buscar'") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
   
    $response["Restaurant"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $restaurantes = array();

        $restaurantes["idRestaurant"] = $row["idRestaurant"];
        $restaurantes["Rest_nombre"] = $row["Rest_nombre"];
        $restaurantes["Rest_tipo"] = $row["Rest_tipo"];
        $restaurantes["Rest_descripcion"] = $row["Rest_descripcion"];
        $restaurantes["Rest_email"] = $row["Rest_email"];
        $restaurantes["Rest_direccion"] = $row["Rest_direccion"];
        //$restaurantes["caracteristicas"] = $row["Rest_caracteristicas"];

        //$product["created_at"] = $row["created_at"];
        //$product["updated_at"] = $row["updated_at"];



        // push single product into final response array
        array_push($response["Restaurant"], $restaurantes);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No restaurantes found";

    // echo no users JSON
    echo json_encode($response);
}
?>
