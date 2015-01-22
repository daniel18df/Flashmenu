      <!DOCTYPE html>
    <html lang="es">
    <head>
        <title>Ingresar menu</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen"> 
        <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
        <script src="js/cufon-yui.js" type="text/javascript"></script>
        <script src="js/cufon-replace.js" type="text/javascript"></script> 
        <script src="js/Dynalight_400.font.js" type="text/javascript"></script>
        <script src="js/FF-cash.js" type="text/javascript"></script>  

      
        <script type="text/javascript">

            function capturarNombre(x){

                var porid = document.getElementById(x).value;
                alert(porid);
            }

        </script>


    <?php

    $var1=$_GET['var1'];
    $perfilAdm=$_GET['perfilAdm'];


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$link = new DB_CONNECT();


    $result = mysql_query("SELECT idProducto, Producto_nombre, Producto_tipo, Producto_precio FROM Producto WHERE Restaurant_idRestaurant='$var1'") or die(mysql_error());


    if (mysql_num_rows($result) > 0) {

       
        while ($row = mysql_fetch_array($result)) { #llenado de los 3 combobox con los distintos tipos de tipos de platos
//   $Id_producto = $row['idProducto'];
    //        $Platos_nombre = $row['Producto_nombre'];
            $Platos_tipo = $row['Producto_tipo'];


            if(strcmp ('platos', $Platos_tipo) == 0){
            $combobit .= " <option value = '".$row['idProducto']."'>".$row['Producto_nombre']." = $".$row['Producto_precio']." ID = ".$row['idProducto']."</option>";
            }

            if(strcmp ('bebidas', $Platos_tipo) == 0){
            $combobit2 .=" <option value = '".$row['idProducto']."'>".$row['Producto_nombre']." = $".$row['Producto_precio']." ID = ".$row['idProducto']."</option>";
            }

            if(strcmp ('postre', $Platos_tipo) == 0){
            $combobit3 .=" <option value = '".$row['idProducto']."'>".$row['Producto_nombre']." = $".$row['Producto_precio']." ID = ".$row['idProducto']."</option>";
            }
        
        } 
       // $suma = $ProductoPrecio1 + $ProductoPrecio2 + $ProductoPrecio3;

    } 
    else {
        echo "No hay productos";
    }

    ?>

    <?php
    if(isset($_POST['submit'])){

        $nombre = $_POST['nombre'];
        $descripcion = $_POST['descripcion'];
        $precio = $_POST['precio'];

 //$nom = $Id_producto;
      $p = $_POST['c1'];
      $b = $_POST['c2'];
      $po = $_POST['c3'];
      
//echo $nom;
  
        $query = mysql_query("INSERT INTO Menu (Menu_nombre, Menu_descripcion, Menu_precio) VALUES ('$nombre', '$descripcion', '$precio')")or die(mysql_error()); #Se insertan datos en la tabla Menu


         $id=mysql_insert_id();
            
        if ($query) 
        $query2 = mysql_query("INSERT INTO Producto_has_Menu (Producto_idProducto, Menu_idMenu) VALUES ('$p', '$id')")or die(mysql_error()); #Se captura el Id del menu cargado y se inserta en la tabla Producto_has_Menu el Id de plato

        $query2 = mysql_query("INSERT INTO Producto_has_Menu (Producto_idProducto, Menu_idMenu) VALUES ('$b', '$id')")or die(mysql_error()); #Se captura el Id del menu cargado y se inserta en la tabla Producto_has_Menu el Id de bebida

        $query2 = mysql_query("INSERT INTO Producto_has_Menu (Producto_idProducto, Menu_idMenu) VALUES ('$po', '$id')")or die(mysql_error()); #Se captura el Id del menu cargado y se inserta en la tabla Producto_has_Menu el Id de postre


               

        mysql_close($conexion);

      }


      function sumando($v1, $v2, $v3){
        $suma =0;

        $precios = mysql_query("SELECT Producto_precio WHERE idProducto = '$p'")or die(mysql_error());  

                if (mysql_num_rows($precios) > 0) {

                    while ($row = mysql_fetch_array($precios)) { 

                        $suma1 = $row['Producto_precio'];
                           
                    }     
                } 
                        
        $precios2 = mysql_query("SELECT Producto_precio WHERE idProducto = '$b'")or die(mysql_error());  

                if (mysql_num_rows($precios2) > 0) {
                       
                    while ($row = mysql_fetch_array($precios2)) { 

                        $suma2 = $row['Producto_precio'];

                    }       
                }

        $precios3= mysql_query("SELECT Producto_precio WHERE idProducto = '$po'")or die(mysql_error());  

                if (mysql_num_rows($precios3) > 0) {

                    while ($row = mysql_fetch_array($precios3)) { 

                        $suma3 = $row['Producto_precio'];

                    }     
                }

        $suma = $v1 + $v2 + $v3; 

        return $suma;
      }
?>


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
                    	<h2>Ingresar <span>menus</span></h2>
                    </div>
                </div>
            </div>
        </header>
        
    	<!--==============================content================================-->
        <section id="content"><div class="ic"></div>
            <div class="main">
                <div class="wrapper">
                	 
                       <form class="basic-grey" action="ingresarMenus.php?var1=<?php echo $var1?><?php echo "&perfilAdm=$perfilAdm"?>">
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
                                 <span>Descripcion:</span>
                                 <input type="text" name="descripcion" maxlength="30" />
                                 </label>
                                 <br/><br/>

                                <label>
                                 <span>Platos:</span>
                                <select name="c1" id = "c1" onChange="capturarNombre(c1)">
                                    <?php echo $combobit; ?>
                                </select>

                                <label>
                                 <span>Bebida:</span>
                                <select name="c2" id = "c2" onChange="capturarNombre(c2)">
                                    <?php echo $combobit2; ?>
                                </select>

                                <label>
                                 <span>Postre:</span>
                                <select name="c3" id = "c3" onChange="capturarNombre(c3)">
                                    <?php echo $combobit3; ?>
                                </select>

                                <!--Suma de los precios de los platos almacenados en BD-->
                                <p> Total:  $ <?php ?></p>  

                               

                                    
                                <!--precio plato-->
                                <label>

                                 <span>Precio:</span>
                                 <input type="number" name="precio" maxlength="16" />
                                </label>
                                 <br/><br/>

                             
             <!--<input class="button-2" onclick="insertarMenu()" type="submit" name="submit" value="Ingresar" formmethod="post" />    -->                    

          <input class="button-2" onclick="alert('Menú creado exitosamente!')" type="submit" name="submit" value="Ingresar" formmethod="post" /> 
                          
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
