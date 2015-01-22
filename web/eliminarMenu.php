<?php

$idMenu=$_GET['idMenu'];
$var1=$_GET['var1'];
$perfilAdm=$_GET['perfilAdm'];


 // include db connect class
    require_once __DIR__ . '/db_connect.php';
    // connecting to db
    $db = new DB_CONNECT();

  $query = "DELETE FROM Menu WHERE idMenu = $idMenu";
     if (!mysql_query($query))
         {
     
         die('Error: ' . mysql_error());
         echo "Error al borrar el plato." . "<br />";
         }
         else{
         echo "<html><head><meta http-equiv='REFRESH' content='0;url=http://localhost:8888/web/verMenus.php?var1=$var1&perfilAdm=$perfilAdm'></head></html>";
         }
         //}
         mysql_close($conexion);
     
        ?>