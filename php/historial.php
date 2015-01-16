<?php
$buscar=$_POST["buscar"];

function repeatedElements($array, $returnWithNonRepeatedItems = false)
{
    $repeated = array();
 
    foreach( (array)$array as $value )
    {
        $inArray = false;
 
        foreach( $repeated as $i => $rItem )
        {
            if( $rItem['value'] === $value )
            {
                $inArray = true;
                ++$repeated[$i]['count'];
            }
        }
 
        if( false === $inArray )
        {
            $i = count($repeated);
            $repeated[$i] = array();
            $repeated[$i]['value'] = $value;
            $repeated[$i]['count'] = 1;
        }
    }
 
    if( ! $returnWithNonRepeatedItems )
    {
        foreach( $repeated as $i => $rItem )
        {
            if($rItem['count'] === 1)
            {
                unset($repeated[$i]);
            }
        }
    }
 
    sort($repeated);
 
    return $repeated;
}

  function OrdenamientoBurbuja($var){
        
        $temp;
        
        for($i=1; $i<15; $i++){
            for($j = 0; $j<15-1; $j++)
              if($arreglo[$j] > $arreglo[$j+1]){
                $temp =  $arreglo[$j];
                $arreglo[$j] = $arreglo[j+1];
                $arreglo[$j+1] = $temp;
              }
            
        }
  }

  function cmp($a, $b)
{
    return strcmp($a["fruta"], $b["fruta"]);
}




// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
$j = 0;
// get all products from products table
//$result = mysql_query("SELECT *FROM platos WHERE Restaurant_idRestaurant = idRestaurant") or die(mysql_error());
$result = mysql_query("SELECT Reserva_detalleProductos_2 FROM Reserva WHERE Cliente_idCliente = 2") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["reserva"] = array();
    $reserva = array();
        $detalle_pro = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        
      /*  $reserva["idReserva"] = $row["idReserva"];
        $reserva["Reserva_fecha"] = $row["Reserva_fecha"];
        $reserva["Reserva_hora"] = $row["Reserva_hora"];
        $reserva["Reserva_total"] = $row["Reserva_total"];
        $reserva["Reserva_direccion"] = $row["Reserva_direccion"];
        $reserva["Reserva_detalleProductos"] = $row["Reserva_detalleProductos"];*/
        //$reserva["Reserva_detalleProductos_2"] = $row["Reserva_detalleProductos_2"];
       /* $reserva["Reserva_email"] = $row["Reserva_email"];
        $reserva["Mesa_Nro_mesa"] = $row["Mesa_Nro_mesa"];
        $reserva["Cliente_idCliente"] = $row["Cliente_idCliente"];*/


       $detalleProductos.= $row["Reserva_detalleProductos_2"]." ";
      //  $detalle_pro[$j] = explode(" ", $detalleProductos);
       // array_push($response["reserva"], $detalle_pro[$i]);


        
        $j++;

    }
        for($n=0; $n<$j; $n++){
        $detalle_pro[$n] = explode(" ", $detalleProductos);
        }

      //  $reserva["historial"] = repeatedElements($detalle_pro[$j-1], true);
    //    $b = OrdenamientoBurbuja($a);
    //    $reserva["historial"] = $a;
    //for($j=0; $j<$i; $j++){



        usort($detalle_pro, "cmp");
        $detalle_pro = repeatedElements($detalle_pro[$j-1], true);

        while (list($clave, $valor) = each($detalle_pro)) {
           echo "\$detalle_pro[$clave]: "  . $valor["fruta"] . "\n";
        }


        $reserva["historial"] = $detalle_pro;
         array_push($response["reserva"], $reserva);

    //}


    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No reserva found";

    // echo no users JSON
    echo json_encode($response);
}


?>
