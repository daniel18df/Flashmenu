<!DOCTYPE html>
<html lang="es">
<head>
    <title>Reporte</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen"> 
    <link rel="stylesheet" href="css/prettyPhoto.css" type="text/css" media="screen">
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="js/cufon-yui.js" type="text/javascript"></script>
    <script src="js/cufon-replace.js" type="text/javascript"></script> 
    <script src="js/Dynalight_400.font.js" type="text/javascript"></script>
    <script src="js/FF-cash.js" type="text/javascript"></script>  
    <script src="js/jquery.prettyPhoto.js" type="text/javascript"></script> 
    <script src="js/hover-image.js" type="text/javascript"></script>
    <script src="js/jquery.easing.1.3.js" type="text/javascript"></script>  
    <script src="js/jquery.bxSlider.js" type="text/javascript"></script> 
    <script type="text/javascript">
		$(document).ready(function() {
			$('#slider-2').bxSlider({
				pager: true,
				controls: false,
				moveSlideQty: 1,
				displaySlideQty: 4
			});
			$("a[data-gal^='prettyPhoto']").prettyPhoto({theme:'facebook'});
		}); 
	</script>

<style>
#button {
padding: 0;
}
#button li {
display: inline;
}
#button li a {
font-family: Arial;
font-size:20px;
text-decoration: none;
float:left;
padding: 10px;
background-color: #0DC03D;
color: #fff;
}

#button li a:hover {
background-color: #0DC03D;
margin-top:-2px;
padding-bottom:12px;
}

</style>
</head>



<body id="page3">
	<!--==============================header=================================-->
    <header>
    	<div class="row-top">
        	<div class="main">
            	<div class="wrapper">
                	<h1><a href="index.html">Flashmenu<span>.cl</span></a></h1>
                    <nav>
                        <ul class="menu">
                            <!-- <li><a href="index.html">Home</a></li>-->
                            <li><a href="perfilAdm.php?var1=<?php echo "$var1"?>">Perfil</a></li>
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
                	<h2>Reporte de recomendador <span><?php echo "$admnombre" ?></span></h2>
                </div>
            </div>
        </div>
    </header>
    
<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="container">
            	<h3 class="prev-indent-bot"></h3>
               
<?php
/*
 * Following code will list all the products
 */

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

 ?>

<?php 
////////Muestra recomendación
$result = mysql_query("SELECT DISTINCT Producto_nombre, Reserva_detalleProductos_2, Rest_nombre, Cliente_nombre, Reserva_fecha, cantidad 
FROM Producto a, Reserva b, Producto_has_Cliente c, Cliente d, Restaurant e 
WHERE a.idProducto = c.Producto_idProducto AND d.idCliente = c.Cliente_idCliente AND e.idRestaurant = a.Restaurant_idRestaurant
ORDER BY cantidad DESC 
LIMIT 3;") or die(mysql_error());

if (mysql_num_rows($result) > 0) {

    echo "<table id='t01' border = '2' color = white bgcolor = '#c5c5c5'> \n"; 
    echo "<tr><td>Nombre Producto</td><td>Detalle de compra</td><td>Restaurant</td><td>Cliente</td><td>Fecha</td></tr> \n"; 
   
        while ($row = mysql_fetch_array($result)) {
       
            $Producto_nombre = $row["Producto_nombre"];
            $Reserva_detalleProductos_2 = $row['Reserva_detalleProductos_2'];
            $Rest_nombre = $row["Rest_nombre"];
            $Cliente_nombre = $row["Cliente_nombre"];
            $Reserva_fecha = $row["Reserva_fecha"];

        echo "<tr><td>$row[0]</td><td>$row[1]</td><td>$row[2]</td><td>$row[3]</td><td>$row[4]</td></tr> \n"; 
 
        }

    echo "</table> \n"; 
    /////////reserva
} 
else {
    echo "No hay menús";
}

?>

</br>
   
<!-- -->

<?php
?>
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
