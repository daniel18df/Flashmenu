<?php



if(isset($_POST['submit'])){
  // include db connect class
    require_once __DIR__ . '/db_connect.php';
    // connecting to db
    $db = new DB_CONNECT();
    $nombre = $_POST['nombre'];
    $apeP = $_POST['apeP'];
    $apeM = $_POST['apeM'];
    $rut = $_POST['rut'];
    $email = $_POST['email'];
    $contrasena = $_POST['contrasena'];
  $query = "INSERT INTO Administrador_restaurant (Adm_nombre, Adm_apellidoPaterno, Adm_apellidoMaterno, Adm_rut, Adm_email, Adm_direccion) VALUES ('$nombre', '$apeP', '$apeM', '$rut', '$email', '$contrasena')";
 
?>


 <meta http-equiv="REFRESH" content="0;url=http://localhost:8888/web/login.php">
 
         <?php  
     if (!mysql_query($query))
         {
     
         die('Error: ' . mysql_error());    
         //echo "Error al crear el plato." . "<br />";
         }
         else{
         //echo "<br />" . "<h2>" . "Producto Creado Exitosamente!" . "</h2>";
         //echo "<h4>" . "Nombre Producto: " . $_POST['nombre'] . "</h4>";
         }
         //}
         mysql_close($conexion);
     }
        ?>

<!DOCTYPE html>
<html lang="es">
<head>
    <title>Registro</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen"> 
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="js/cufon-yui.js" type="text/javascript"></script>
    <script src="js/cufon-replace.js" type="text/javascript"></script> 
    <script src="js/Dynalight_400.font.js" type="text/javascript"></script>
    <script src="js/FF-cash.js" type="text/javascript"></script>  


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
                            <li><a class="active" href="loginAdmSist.php">Ingresar</a></li>
                            <li><a href="contact.php">Contacto</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="row-bot">
          <div class="row-bot-bg">
              <div class="main">
                  <h2>Registrar <span>Administrador sistema</span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
        <div class="main">
            <div class="wrapper">
            	 
                   <form class="basic-grey" action="ingresarAdmSist.php">
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
                             <span>Apellido Paterno:</span>
                             <input type="text" name="apeP" maxlength="30" />
                             </label>
                             <br/><br/>

                            <!--precio plato-->
                             <label>
                             <span>Apellido Materno:</span>
                             <input type="text" name="apeM" maxlength="30" />
                             </label>
                             <br/><br/>

                                 <!--Nombre plato-->
                             <label>
                             <span>Rut:</span>
                             <input type="number" name="rut" maxlength="9" />
                             </label>
                            <br/><br/>

                             <!--descripcion plato--> 
                             <label>
                             <span>Email:</span>
                             <input type="email" name="email" maxlength="30" />
                             </label>
                             <br/><br/>

                            <!--precio plato-->
                             <label>
                             <span>Password:</span>
                             <input type="password" name="contrasena" maxlength="16" />
                             </label>
                             <br/><br/>


                                 
<input class="button-2" onclick="alert('Creado exitosamente!')" type="submit" name="submit" value="Ingresar" formmethod="post" />
                      
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
