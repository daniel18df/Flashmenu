<?php $var1=$_GET['var1'];
       $perfilAdm=$_GET['perfilAdm'];?> 
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Reservas</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">  
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="js/cufon-yui.js" type="text/javascript"></script>
    <script src="js/cufon-replace.js" type="text/javascript"></script> 
    <script src="js/Dynalight_400.font.js" type="text/javascript"></script>
    <script src="js/FF-cash.js" type="text/javascript"></script>  
	<!--[if lt IE 8]>
    <div style=' clear: both; text-align:center; position: relative;'>
        <a href="http://windows.microsoft.com/en-US/internet-explorer/products/ie/home?ocid=ie6_countdown_bannercode">
        	<img src="http://storage.ie6countdown.com/assets/100/images/banners/warning_bar_0000_us.jpg" border="0" height="42" width="820" alt="You are using an outdated browser. For a faster, safer browsing experience, upgrade for free today." />
        </a>
    </div>
	<![endif]-->
    <!--[if lt IE 9]>
   		<script type="text/javascript" src="js/html5.js"></script>
	<![endif]-->


      <style>
table {
    width:100%;
}
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: left;
}
table#t01 tr:nth-child(even) {
    background-color: #eee;
}
table#t01 tr:nth-child(odd) {
   background-color:#fff;
}
table#t01 th    {
    background-color: black;
    color: white;
}
</style>
</head>
<body id="page5">
	<!--==============================header=================================-->
    <header>
    	<div class="row-top">
        	<div class="main">
            	<div class="wrapper">
                	<h1><a href="index.html">Flashmenu<span>.cl</span></a></h1>
                    <nav>
                        <ul class="menu">
                            <!-- <li><a href="index.html">Home</a></li>-->
                            <li><a href="perfilAdm.php?var1=<?php echo "$perfilAdm"?>">Perfil</a></li>
                            <li><a href="index.html">Cerrar sesion </a></li>
                            <li><a href="contact.php">Contacto</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="row-bot">
        	<div class="row-bot-bg">
            	<div class="main">
                	<h2>Reservas <span>Restaurant</span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="container">
            	<h3>Reservas</h3>
              
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


////////mesa
$resultado = mysql_query("SELECT *FROM Mesa WHERE Restaurant_idRestaurant = $var1") or die(mysql_error());

if (mysql_num_rows($resultado) > 0) {

  while ($row = mysql_fetch_array($resultado)) {
       
        $Nro_mesa = $row["Nro_mesa"];
        $Mesa_nro = $row['Mesa_nro'];
        $Mesa_descripcion = $row["Mesa_descripcion"];
        $Mesa_cantPersonas = $row["Mesa_cantPersonas"];
        $Restaurant_idRestaurant = $row["Restaurant_idRestaurant"];
    }
   
}
 else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No restaurant found";

    // echo no users JSON
    echo json_encode($response);
}
/////////mesa


////////reserva
$result = mysql_query("SELECT *FROM Reserva WHERE Mesa_Nro_mesa = $Nro_mesa") or die(mysql_error());

if (mysql_num_rows($result) > 0) {

echo "<table id='t01' border = '2' color = white bgcolor = '#C5C5C5'> \n"; 
echo "<tr><td>Id Reserva</td><td>Fecha Reserva</td><td>Hora reserva</td><td>Nro mesa</td><td>Detalle de productos</td><td>Total</td><td>Id cliente</td></tr> \n"; 
   
    while ($row = mysql_fetch_array($result)) {
       
        $idReserva = $row["idReserva"];
        $Reserva_fecha = $row["Reserva_fecha"];
        $Reserva_hora = $row["Reserva_hora"];
        $Reserva_total = $row["Reserva_total"];
        $Reserva_direccion = $row["Reserva_direccion"];
        $Reserva_detalleProductos = $row["Reserva_detalleProductos"];
        $Reserva_emailRest = $row["Reserva_emailRest"];
        $Mesa_Nro_mesa = $row["Mesa_Nro_mesa"];
        $Cliente_idCliente = $row["Cliente_idCliente"];


 echo "<tr><td>$row[0]</td><td>$row[1]</td><td>$row[2]</td><td>$row[7]</td><td>$row[5]</td><td>$row[3]</td><td>$row[8]</td></tr> \n"; 

 
    }



echo "</table> \n"; 
/////////reseeva

} 
else {
    echo "No hay reservas";
}
?>



<!-- JS  -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.js"></script>
    <script>window.jQuery || document.write("<script src='js/jquery-1.5.1.min.js'>\x3C/script>")</script>
    <script src="js/app.js"></script>


</body>
</html>


            </div>
        </div>
    </section>
    
	<!--==============================footer=================================-->
    <footer>
        <div class="main">
        	<div class="aligncenter">
            	<span>Flashmenu.cl &copy; 2014</span>
            </div>
        </div>
    </footer>
    <script type="text/javascript"> Cufon.now(); </script>
</body>
</html>