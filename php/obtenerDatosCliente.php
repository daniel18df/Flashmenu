<?php
//$buscar=$_GET["buscar"];
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

if (isset($_GET["buscar"])) {
    $b =$_GET['buscar'];
// get all products from products table
$result = mysql_query("SELECT * FROM Cliente WHERE idCliente = $b");

if (!empty($result)) {

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
   $result = mysql_fetch_array($result);
    
        // temp user array
        $cliente = array();
        //$cliente["idCliente"] = $row["idCliente"];
        //$cliente["Cliente_nombre"] = $row["Cliente_nombre"];
        //$cliente["Cliente_apellidoPaterno"] = $row["Cliente_apellidoPaterno"];
        //$cliente["Cliente_apellidoMaterno"] = $row["Cliente_apellidoMaterno"];
        //$cliente["Cliente_rut"] = $row["Cliente_rut"];
        $cliente["Cliente_email"] = $result["Cliente_email"];
        $cliente["Cliente_direccion"] = $result["Cliente_direccion"];

        

// success
    $response["success"] = 1;
    $response["Cliente"] = array();
        // push single product into final response array
        array_push($response["Cliente"], $cliente);
    
    

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No Cliente found";

    // echo no users JSON
    echo json_encode($response);
}

 } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No empleado found";

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
