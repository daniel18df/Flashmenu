<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['Producto_idProducto'])){
    
    //$idAdm = $_POST['idAdministrador_restaurant'];
    $Producto_idProducto = $_POST['Producto_idProducto'];
    $Cliente_idCliente = $_POST['Cliente_idCliente'];
    $cantidad = $_POST['cantidad'];


    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();
    $cant=0;

   $query = mysql_query("SELECT * FROM Producto_has_Cliente WHERE Cliente_idCliente = $Cliente_idCliente");
   //if ($query) {


    if (mysql_num_rows($query) > 0) {
    $presente = false;
    while ($row = mysql_fetch_array($query)) {
        if($row["Producto_idProducto"] === $Producto_idProducto){
            $presente = true;
            $cant = $row["cantidad"];
        }
    }
    // mysql inserting a new row
    if($presente === false){
         $result = mysql_query("INSERT INTO Producto_has_Cliente(Producto_idProducto, Cliente_idCliente, cantidad) VALUES('$Producto_idProducto' ,'$Cliente_idCliente','$cantidad')");

        // check if row inserted or not
        if ($result) {
            // successfully inserted into database
            $response["success"] = 1;
            $response["message"] = "Producto_idProducto successfully created.";

            // echoing JSON response
            echo json_encode($response);
        } else {
            // failed to insert row
            $response["success"] = 0;
            $response["message"] = "Oops! An error occurred Producto_idProducto.";
            
            // echoing JSON response
            echo json_encode($response);
        }
    }else{ 
       // $query2 = mysql_query("SELECT cantidad FROM Producto_has_Cliente");
   //     if (mysql_num_rows($query2) > 0) {
            // while ($row = mysql_fetch_array($query2)) {

                $cant += $cantidad;
              

              //  }
                $result2 = mysql_query("UPDATE Producto_has_Cliente SET cantidad = '$cant' WHERE Producto_idProducto = $Producto_idProducto AND Cliente_idCliente = $Cliente_idCliente");
                // check if row inserted or not
                if ($result2) {
                    // successfully updated
                    $response["success"] = 1;
                    $response["message"] = "Producto_has_Cliente successfully updated.";
                    
                    // echoing JSON response
                    echo json_encode($response);
                    
        
                    } else {
                // required field is missing
                $response["success"] = 0;
                $response["message"] = "Required field(s) is missing, Producto_idProducto";

                // echoing JSON response
                echo json_encode($response);
            }
  

    }




    } else {
        // required field is missing
        $response["success"] = 0;
        $response["message"] = "Required field(s) is missing, Producto_idProducto";

        // echoing JSON response
        echo json_encode($response);
    }
    
 }else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}

    
?>