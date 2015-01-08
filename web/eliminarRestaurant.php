<?php

$idPlatos=$_GET['idPlatos'];
$idRestaurant=$_GET['idRestaurant'];
$perfilAdm=$_GET['perfilAdm'];


 // include db connect class
    require_once __DIR__ . '/db_connect.php';
    // connecting to db
    $db = new DB_CONNECT();

  $query = "DELETE FROM Restaurant WHERE idRestaurant = $idRestaurant";
     if (!mysql_query($query))
         {
     
         die('Error: ' . mysql_error());
         echo "Error al borrar el plato." . "<br />";
         }
         else{
         echo "<html><head><meta http-equiv='REFRESH' content='0;url=http://www.flashmenu.cl/web/misRestaurantes.php?var1=$perfilAdm'></head></html>";
         }
         //}
         mysql_close($conexion);
     
        ?>