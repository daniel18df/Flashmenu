<!DOCTYPE html>
<html lang="es">
<head>
    <title>Contacto</title>
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
<body id="page6">
	<!--==============================header=================================-->
    <header>
    	<div class="row-top">
        	<div class="main">
            	<div class="wrapper">
                	<h1><a href="index.html">Flashmenu<span>.cl</span></a></h1>
                    <nav>
                        <ul class="menu">
                            <li><a class="active" href="index.html">Home</a></li>
                            <li><a href="login.php">Ingresar</a></li>
                            <li><a href="contact.php">Contacto</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="row-bot">
        	<div class="row-bot-bg">
            	<div class="main">
                	<h2>Si eres dueño <span>de un restaurant, contactanos</span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="wrapper">
            	<article class="col-1">
                	<div class="indent-left">
                    	<h3 class="p1">Nuestros contactos</h3>
                        <figure class="indent-bot">
                            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d107041.12268656628!2d-71.51552895000002!3d-33.02920614999998!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9689ddadff741787%3A0x43504a924df71ed4!2zVmnDsWEgZGVsIE1hciwgVmFscGFyYcOtc28!5e0!3m2!1ses!2scl!4v1417436372021" width="300" height="200" frameborder="0" style="border:0"></iframe>
                        </figure>
                        <dl>
                            <dt class="p1">Viña del mar, Chile</dt>
                            <dd><span>Telefono:</span>  +569 56629103 - +569 89068681</dd>
                            <dd><span>Email:</span><a class="color-2" href="#">daniel18.df@gmail.com - octavio.valencia.v@gmail.com</a></dd>
                        </dl>
                    </div>
                </article>
                <article class="col-2">
                	<h3 class="p1">Contacto</h3>
                    <?
                            if (!isset($_POST['email'])) {?>
                    <form id="contact-form" method="post" enctype="multipart/form-data" action="contact.php">                    
                        <fieldset>
                              <label><span class="text-form">Tu nombre:</span><input id="nombre" name="nombre" type="text" /></label>
                              <label><span class="text-form">Tu Email:</span><input id="email" name="email" type="text" /></label>                              
                              <div class="wrapper">
                                <div class="text-form">Mensaje:</div>
                                <div class="extra-wrap">
                                    <textarea id="mensaje" name="mensaje"></textarea>
                                    <div class="clear"></div>
                                    <div class="buttons">
                                        <a class="button-2" href="#" onClick="document.getElementById('contact-form').reset()">Borrar</a>
                                        <a class="button-2" href="#" onClick="document.getElementById('contact-form').submit()">Enviar</a>
                                    </div> 
                                </div>
                              </div>                            
                        </fieldset>						
                    </form>
                    <?php
                                }else{
                                  $mensaje="Mensaje del formulario de www.flashmenu.cl";
                                  $mensaje.= "\nNombre: ". $_POST['nombre'];
                                  $mensaje.= "\nEmail: ".$_POST['email'];
                                  $mensaje.= "\nMensaje: \n".$_POST['mensaje'];
                                  $destino= "daniel@flashmenu.cl";
                                  $remitente = $_POST['email'];
                                  $asunto = "Mensaje enviado por: ".$_POST['nombre'];
                                  mail($destino,$asunto,$mensaje,$remitente);
                                ?>
                                  <p><strong>Mensaje enviado.</strong></p>
                                <?php
                                }
                                ?>
                </article>
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
