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
// get all products from products table
//$result = mysql_query("SELECT *FROM platos WHERE Restaurant_idRestaurant = idRestaurant") or die(mysql_error());
$result = mysql_query("SELECT DISTINCT Producto_nombre, cantidad, idMenu, Menu_nombre, Menu_precio, Restaurant_idRestaurant FROM Producto a, Producto_has_Cliente b, Menu c, Producto_has_Menu d WHERE c.idMenu = d.Menu_idMenu AND d.Producto_idProducto = a.idProducto AND b.Cliente_idCliente =2 AND b.Producto_idProducto = a.idProducto ORDER BY cantidad ASC ") or die(mysql_error());
/*
SELECT * FROM Producto a, Producto_has_Cliente b, Menu c, Producto_has_Menu d WHERE 
2 = b.Cliente_idCliente AND b.Producto_idProducto = a.idProducto AND c.idMenu = d.Menu_idMenu 
AND d.Producto_idProducto = 20  ORDER BY cantidad ASC
*/
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["SugerenciasMenu"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $SugerenciasMenu = array();
        
        $SugerenciasMenu["idProducto"] = $row["idProducto"];
        $SugerenciasMenu["Producto_nombre"] = $row["Producto_nombre"];
        $SugerenciasMenu["Producto_descripcion"] = $row["Producto_descripcion"];
        $SugerenciasMenu["Producto_precio"] = $row["Producto_precio"];
        $SugerenciasMenu["Producto_tipo"] = $row["Producto_tipo"];
        $SugerenciasMenu["Producto_tipo_preferencia"] = $row["Producto_tipo_preferencia"];
        $SugerenciasMenu["Restaurant_idRestaurant"] = $row["Restaurant_idRestaurant"];
        //Producto_has_cliente
        $SugerenciasMenu["Producto_idProducto"] = $row["Producto_idProducto"];
        $SugerenciasMenu["Cliente_idCliente"] = $row["Cliente_idCliente"];
        $SugerenciasMenu["cantidad"] = $row["cantidad"];
        //Menu
        $SugerenciasMenu["idMenu"] = $row["idMenu"];
        $SugerenciasMenu["Menu_nombre"] = $row["Menu_nombre"];
        $SugerenciasMenu["Menu_precio"] = $row["Menu_precio"];


        $SugerenciasMenu["Menu_idMenu"] = $row["Menu_idMenu"];

          //$product["created_at"] = $row["created_at"];
        //$product["updated_at"] = $row["updated_at"];



        // push single product into final response array
        array_push($response["SugerenciasMenu"], $SugerenciasMenu);
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
