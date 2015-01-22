
<?php $var1=$_GET['var1'];
$perfilAdm=$_GET['perfilAdm'];?> 

<!DOCTYPE html>
<html lang="es">
<head>
    <title>Menus</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">  
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="js/cufon-yui.js" type="text/javascript"></script>
    <script src="js/cufon-replace.js" type="text/javascript"></script> 
    <script src="js/Dynalight_400.font.js" type="text/javascript"></script>
    <script src="js/FF-cash.js" type="text/javascript"></script>  

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
                	<h2>Menus <span>Restaurant</span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="container">
            	<h3>Menús</h3>
              
<?php

require_once __DIR__ . '/db_connect.php';
// connecting to db
$db = new DB_CONNECT();

?>

<?php 
////////reserva
$result = mysql_query("SELECT idMenu, Menu_nombre, Producto_nombre, Menu_precio FROM Menu a, Producto b, Producto_has_Menu c, Restaurant d  WHERE a.idmenu = c.Menu_idMenu AND b.idProducto = c.Producto_idProducto AND d.idrestaurant = b.Restaurant_idRestaurant") or die(mysql_error());

if (mysql_num_rows($result) > 0) {

    echo "<table id='t01' border = '2' color = white bgcolor = '#C5C5C5'> \n"; 
    echo "<tr><td>Nombre</td><td>Descripcion</td><td>Precio</td></tr> \n"; 
   
        while ($row = mysql_fetch_array($result)) {
       
            $idMenu = $row["idMenu"];
            $Menu_nombre = $row['Menu_nombre'];
            $Menu_descripcion = $row["Producto_nombre"];
            $Menu_precio = $row["Menu_precio"];
            $Restaurant_idRestaurant = $row["Restaurant_idRestaurant"];

        echo "<tr><td><a href='perfilMenuCarta.php?idMenu=$row[0]&perfilAdm=$perfilAdm&var1=$var1'>$row[1]</a></td><td>$row[2]</td><td>$row[3]</td></tr> \n"; 
 
        }

    echo "</table> \n"; 
    /////////reserva
} 
else {
    echo "No hay menús";
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