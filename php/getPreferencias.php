<?php
$buscar=$_POST["buscar"];

//$buscar= "q";

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysql_query("SELECT Preferencia_tipo_idPreferencia_tipo FROM Preferencia WHERE Cliente_idCliente = '$buscar'") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["Preferencia"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $Preferencia = array();
        $Preferencia["Preferencia_tipo_idPreferencia_tipo"] = $row["Preferencia_tipo_idPreferencia_tipo"];

        // push single product into final response array
        array_push($response["Preferencia"], $Preferencia);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No Preferencia_tipo_idPreferencia_tipo found";

    // echo no users JSON
    echo json_encode($response);
}
?>
