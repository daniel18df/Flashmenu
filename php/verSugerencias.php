<?php
$buscar=$_POST["buscar"];
$preferencias_tipo=$_POST["preferencias_tipo"];
/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
$p = "platos";
// get all products from products table
//$result = mysql_query("SELECT *FROM platos WHERE Restaurant_idRestaurant = idRestaurant") or die(mysql_error());
$result = mysql_query("SELECT * FROM Producto WHERE Producto_tipo = 'Platos' ORDER BY $preferencias_tipo DESC") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["Producto"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $platos = array();
        $platos["idProducto"] = $row["idProducto"];
        $platos["Producto_nombre"] = $row["Producto_nombre"];
        $platos["Producto_descripcion"] = $row["Producto_descripcion"];
        $platos["Producto_precio"] = $row["Producto_precio"];
        $platos["Producto_tipo"] = $row["Producto_tipo"];
        $platos["Producto_tipo_preferencia"] = $row["Producto_tipo_preferencia"];
        $platos["Restaurant_idRestaurant"] = $row["Restaurant_idRestaurant"];


          //$product["created_at"] = $row["created_at"];
        //$product["updated_at"] = $row["updated_at"];



        // push single product into final response array
        array_push($response["Producto"], $platos);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No productos found";

    // echo no users JSON
    echo json_encode($response);
}
?>
