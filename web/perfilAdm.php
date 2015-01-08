<!DOCTYPE html>
<html lang="es">
<head>
    <title>Perfil Adm</title>
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

<?php $var1=$_GET['var1']; 
        $admnombre=$_GET['admnombre']; 
?>

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
                	<h2>Bienvenido <span><?php echo "$admnombre" ?></span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="container">
            	<h3 class="prev-indent-bot">Perfil</h3>
               
               <?php
/*
 * Following code will list all the products
 */

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
$result = mysql_query("SELECT *FROM Administrador_restaurant WHERE idAdministrador_restaurant = $var1") or die(mysql_error());

if (mysql_num_rows($result) > 0) {
   
    while ($row = mysql_fetch_array($result)) {
       
        $idAdministrador_restaurant = $row["idAdministrador_restaurant"];
        $Adm_nombre = $row["Adm_nombre"];
        $Adm_email = $row["Adm_email"];
        $Adm_direccion = $row["Adm_direccion"];
        $Adm_apellidoPaterno = $row["Adm_apellidoPaterno"];
        $Adm_apellidoMaterno = $row["Adm_apellidoMaterno"];
        $Adm_rut = $row["Adm_rut"];
    }

 ?>
        </br>
   

          
                      
            <a href='misRestaurantes.php?var1=<?php echo "$idAdministrador_restaurant"?>' class="button-2" onClick="document.getElementById('contact-form').submit()" >Mis restaurantes</a>
            <a href='modificarAdm.php?idAdministrador_restaurant=<?php echo "$idAdministrador_restaurant"?>' class="button-2" onClick="document.getElementById('contact-form').submit()" >Modificar mis datos</a>
            <a href='ingresarRest.php?var1=<?php echo "$idAdministrador_restaurant"?>' class="button-2" onClick="document.getElementById('contact-form').submit()" >Agregar restaurant</a>

    
 
<!-- -->


        


<?php
} 
else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No restaurant found";

    // echo no users JSON
    echo json_encode($response);
}
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
