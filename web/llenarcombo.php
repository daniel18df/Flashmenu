
<?php

/*
 * Following code will list all the products
 */
//$v1=$_GET['var'];
// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
?>
<?php 


////////reserva
$result = mysql_query("SELECT  Producto_nombre FROM Producto") or die(mysql_error());

if (mysql_num_rows($result) > 0) {

   
    while ($row = mysql_fetch_array($result)) {

        $Platos_nombre = $row['Producto_nombre'];
        $combobit .=" <option value='".$row['Producto_nombre']."'>".$row['Producto_nombre']."</option>";
    }

} 
else {

    echo "No hay productos";
}
?>
<html>
<head>
<title>Llenar un Combobox/Select con registros de una tabla</title>

</head>
<body>
   <select name="Producto">
       <?php echo $combobit; ?>
   </select>
</body>
</html>