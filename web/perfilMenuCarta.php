<?php 
$var1=$_GET['var1'];
$idMenu=$_GET['idMenu'];
$perfilAdm=$_GET['perfilAdm'];?>
 
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Perfil carta</title>
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
                	<h2>Perfil <span>Productos</span></h2>
                </div>
            </div>
        </div>
    </header>
    

                


    <!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
          <div class="wrapper img-indent-bot">
 <div class="wrapper">
                <article class="col-1">
                <h3 class="p1">Perfil</h3>
                    <ul>
                        <li><a href='ingresarRestaurant.php?<?php echo "var1=$var1"?><?php echo "&perfilAdm=$perfilAdm"?>' class="button-2" href="#" onClick="document.getElementById('contact-form').submit()" >Modificar producto</a></li>
                        <li><a href='eliminarMenu.php?<?php echo "idMenu=$idMenu"?><?php echo "&perfilAdm=$perfilAdm"?><?php echo "&var1=$var1"?>' class="button-2" href="#" onClick="document.getElementById('contact-form').submit()" >Eliminar producto</a></li>
                    </ul>
                </article>
                
                
 </div>
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
