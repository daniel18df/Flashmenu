<?php

$var1=$_GET['var1'];

$perfilAdm=$_GET['perfilAdm'];

if(isset($_POST['submit'])){
  // include db connect class
    require_once __DIR__ . '/db_connect.php';
    // connecting to db
    $db = new DB_CONNECT();
    $nombre = $_POST['nombre'];
    $tipo = $_POST['tipo'];
    $descripcion = $_POST['descripcion'];
    $email = $_POST['email'];
    $direccion = $_POST['address'];
    $lat = $_POST['lat'];
    $long = $_POST['long'];
  $query = "INSERT INTO Restaurant (Rest_nombre, Rest_tipo, Rest_descripcion, Rest_email, Rest_direccion, Rest_lat, Rest_long, Administrador_restaurant_idAdministrador_restaurant) VALUES ('$nombre', '$tipo', '$descripcion', '$email', '$direccion', '$lat', '$long', '$var1')";
     if (!mysql_query($query))
         {
     
         die('Error: ' . mysql_error());
        // echo "Error al crear el plato." . "<br />";
         }
         else{
       // echo "<br />" . "<h2>" . "Producto Creado Exitosamente!" . "</h2>";
       // echo "<h4>" . "Nombre Producto: " . $_POST['nombre'] . "</h4>";
         }
         //}
         mysql_close($conexion);
     }
        ?>

<!DOCTYPE html>
<html lang="es">
<head>
    <title>Ingresar producto</title>
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
    <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" title="Normal" />
    <script src="js/map.class.min.js" type="text/javascript"></script> 
    
<script type="text/javascript"> 



var map; var geocoder; var errMSG; var fldAddr; var fldLng; var fldLat; var mapCntr; var geocoder; 
function initialize() { mapDIV = document.getElementById("map"); fldAddr = document.getElementById("address"); errMSG = document.getElementById("err"); fldLng = document.getElementById("long"); fldLat = document.getElementById("lat"); geocoder = new google.maps.Geocoder(); var latlng = new google.maps.LatLng(-33.024527, -71.55234000000002); var myOptions = { zoom: 10, center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP }; mapDIV.innerHTML = ""; map = new google.maps.Map(mapDIV, myOptions); } 

function marcarDireccion() { 
    fldAddr.value = fldAddr.value.trim(); 
    if (fldAddr.value) { 
        fldLat.value = ""; fldLng.value = ""; geocoder.geocode({'address': fldAddr.value}, 

            function(results, status) { 
                if (status == google.maps.GeocoderStatus.OK) { 
                    errMSG.innerHTML = ""; map.setCenter(results[0].geometry.location); 
                    fldLat.value = results[0].geometry.location.lat(); 
                    fldLng.value = results[0].geometry.location.lng(); 
                    var txt = fldAddr.value = results[0].formatted_address; 
                    if (txt) { var calleCiudad = txt.split(',', 2); 
                    txt = calleCiudad[0].trim() + "\n" + calleCiudad[1].trim() + "\n"; } 
                    txt += "lat: " + fldLat.value + "\nlng: " + fldLng.value + "\n"; 
                    new google.maps.Marker({ 
                        position: new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng()), map: map }); map.setZoom(15); } 
                    else { errMSG.innerHTML = "Error " + status; } }); } } 
google.maps.event.addDomListener(window, 'load', initialize); 
</script> 


    <!--ESTILO FORM-->
    <style type="text/css">
/* Basic Grey */
.basic-grey {
    margin-left:auto;
    margin-right:auto;
    max-width: 500px;
    background: #F7F7F7;
    padding: 25px 15px 25px 10px;
    font: 12px Georgia, "Times New Roman", Times, serif;
    color: #888;
    text-shadow: 1px 1px 1px #FFF;
    border:1px solid #E4E4E4;
}
.basic-grey h1 {
    font-size: 25px;
    padding: 0px 0px 10px 40px;
    display: block;
    border-bottom:1px solid #E4E4E4;
    margin: -10px -15px 30px -10px;;
    color: #888;
}
.basic-grey h1>span {
    display: block;
    font-size: 11px;
}
.basic-grey label {
    display: block;
    margin: 0px;
}
.basic-grey label>span {
    float: left;
    width: 20%;
    text-align: right;
    padding-right: 10px;
    margin-top: 10px;
    color: #888;
}
.basic-grey input[type="text"], .basic-grey input[type="email"], .basic-grey textarea, .basic-grey select {
    border: 1px solid #DADADA;
    color: #888;
    height: 30px;
    margin-bottom: 16px;
    margin-right: 6px;
    margin-top: 2px;
    outline: 0 none;
    padding: 3px 3px 3px 5px;
    width: 70%;
    font-size: 12px;
    line-height:15px;
    box-shadow: inset 0px 1px 4px #ECECEC;
    -moz-box-shadow: inset 0px 1px 4px #ECECEC;
    -webkit-box-shadow: inset 0px 1px 4px #ECECEC;
}
.basic-grey textarea{
    padding: 5px 3px 3px 5px;
}
.basic-grey select {
    background: #FFF url('down-arrow.png') no-repeat right;
    background: #FFF url('down-arrow.png') no-repeat right);
    appearance:none;
    -webkit-appearance:none; 
    -moz-appearance: none;
    text-indent: 0.01px;
    text-overflow: '';
    width: 70%;
    height: 35px;
    line-height: 25px;
}
.basic-grey textarea{
    height:100px;
}
.basic-grey .button {
    background: #E27575;
    border: none;
    padding: 10px 25px 10px 25px;
    color: #FFF;
    box-shadow: 1px 1px 5px #B6B6B6;
    border-radius: 3px;
    text-shadow: 1px 1px 1px #9E3F3F;
    cursor: pointer;
}
.basic-grey .button:hover {
    background: #CF7A7A
}
</style>
    <!--ESTILO FORM-->
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
                	<h2>Ingresar <span>restaurant</span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="wrapper">
            	 
                   <form class="basic-grey" action="ingresarRest.php?var1=<?php echo $var1?>">
                    <h3 class="p1">Ingresar datos</h3>
                        <center>
                            <hr/>
                            <!--Nombre plato-->
                             <label>
                             <span>Nombre:</span>
                             <input type="text" name="nombre" maxlength="30" />
                             </label>
                            <br/><br/>

                             <!--descripcion plato--> 
                             <label>
                             <span>Tipo:</span>
                             <input type="text" name="tipo" maxlength="30" />
                             </label>
                             <br/><br/>

                            <!--precio plato-->
                             <label>
                             <span>Descripcion:</span>
                             <input type="text" name="descripcion" maxlength="16" />
                             </label>
                             <br/><br/>

                              <!--precio plato-->
                             <label>
                             <span>Email:</span>
                             <input type="email" name="email" maxlength="16" />
                             </label>
                             <br/><br/>

                            <label>
                              <!--precio plato-->
                    <div>         <div style="margin-bottom: 10px;"> 
                            <span>Direccion:</span>
                            <input type="text" name="address" id="address" style="width:300px;" onchange="marcarDireccion()"> 
                            <p class="button-2"> Ver direccion</p>
       
                            </div> 
                        </label>

                            <div style="height: 400px; width: 500px;" id="map"></div> 


                <span id="err" style="color:red"></span> 
                    <input type="text" name="lat" id="lat" style="width:150px;">
                    <input type="text" name="long" id="long" style="width:150px;"> 

                       </div>  
                                 

      <input class="button-2" onclick="alert('Restaurant creado exitosamente!')" type="submit" name="submit" value="Ingresar" formmethod="post" />
                      
                            </center>
                    </form>

                  

              
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
