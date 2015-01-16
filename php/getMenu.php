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
$result = mysql_query("SELECT * FROM Menu a, Producto_has_Menu b, Producto c 
                        WHERE a.idMenu = b.Menu_idMenu AND b.Producto_idProducto = c.idProducto") or die(mysql_error());
// check for empty result


   if (mysql_num_rows($result) > 0) {
                            // looping through all results
                            // products node
            $response["Producto_has_Menu"] = array();
           /*  idMenu 
             Menu_nombre
             Menu_descripcion 
             Menu_precio 
             Restaurant_idRestaurant
             Producto_idProducto
             Menu_idMenu 
             idProducto 
             Producto_nombre 
             Producto_descripcion
             Producto_precio 
             Producto_tipo 
             Producto_tipo_preferencia  
             Restaurant_idRestaurant*/
            while ($row = mysql_fetch_array($result)) {
                // temp user array
                $reserva = array();

             //   $reserva["idMenu"] = $row["idMenu"];
                $reserva["Menu_nombre"] = $row["Menu_nombre"];
            //    $reserva["Menu_descripcion"] = $row["Menu_descripcion"];
                $reserva["Menu_precio"] = $row["Menu_precio"];
                $reserva["Restaurant_idRestaurant"] = $row["Restaurant_idRestaurant"];
             //   $reserva["Menu_idMenu"] = $row["Menu_idMenu"];
            //    $reserva["idProducto"] = $row["idProducto"];
                $reserva["Producto_nombre"] = $row["Producto_nombre"];
            //    $reserva["Producto_descripcion"] = $row["Producto_descripcion"];
            //    $reserva["Producto_precio"] = $row["Producto_precio"];
            //    $reserva["Producto_tipo"] = $row["Producto_tipo"];
            //    $reserva["Producto_tipo_preferencia"] = $row["Producto_tipo_preferencia"];
             //   $reserva["Restaurant_idRestaurant"] = $row["Restaurant_idRestaurant"];


                array_push($response["Producto_has_Menu"], $reserva);
            }


            // success
            $response["success"] = 1;

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no products found
            $response["success"] = 0;
            $response["message"] = "No Producto_has_Menu found";

            // echo no users JSON
            echo json_encode($response);
        }
?>
