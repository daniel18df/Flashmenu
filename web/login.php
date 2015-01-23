<!DOCTYPE html>
<html lang = 'esp'>
<head>
    <title>Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
    <link rel="stylesheet" href="css/layout.css" type="text/css" media="screen"> 
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="js/cufon-yui.js" type="text/javascript"></script>
    <script src="js/cufon-replace.js" type="text/javascript"></script> 
    <script src="js/Dynalight_400.font.js" type="text/javascript"></script>
    <script src="js/FF-cash.js" type="text/javascript"></script>  
    <script src="js/jquery.equalheights.js" type="text/javascript"></script>    
    <script src="js/jquery.bxSlider.js" type="text/javascript"></script> 
    <script type="text/javascript">
		$(document).ready(function() {
			$('#slider').bxSlider({
				pager: true,
				controls: false,
				moveSlideQty: 1,
				displaySlideQty: 3
			});
		}); 
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
<body id="page2">
	<!--==============================header=================================-->
    <header>
    	<div class="row-top">
        	<div class="main">
            	<div class="wrapper">
                	<h1><a href="index.html">Flashmenu<span>.cl</span></a></h1>
                    <nav>
                        <ul class="menu">
                           <!-- <li><a href="index.html">Home</a></li>-->
                            <li><a class="active" href="login.php">Ingresar</a></li>
                            <li><a href="contact.php">Contacto</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="row-bot">
        	<div class="row-bot-bg">
            	<div class="main">
                	<h2>Inicio sesion <span>Administrador</span></h2>
                </div>
            </div>
        </div>
    </header>
    
	<!--==============================content================================-->
    <section id="content"><div class="ic"></div>
       <div class="main">
            <div class="wrapper">
    
                    <form class="basic-grey" id="contact-form" method="post" enctype="multipart/form-data" action="login.php">                    
                      <h3 class="p1">Ingresar datos</h3>
                        <fieldset>

                            
                        <select name="tipo">
                            <optgroup>
                                <option value="Admin rest">Administrador Restaurant</option>
                                <option value="Admin sist">Administrador sistema</option>
                        </select>    

                              <label><span class="text-form">Email:</span><input name="username" type="email" id="username"></label>
                               <br/><br/>
                              <label><span class="text-form">Contraseña:</span><input name="password" type="password" id ="password"/></label>  
                              <br/>                            
                              <div class="wrapper">
                               
                                <div class="extra-wrap">
                                  
                                    <div class="clear"></div>
                                    <div class="buttons">
                                      
                                        <a class="button-2" href="#" onClick="document.getElementById('contact-form').submit()" type ="submit" name="submit2">Enviar</a>
                                        <a class="button-2" href="ingresarAdmRest.php" onClick="document.getElementById('contact-form').submit()" type ="submit" >Registrar</a>
        
                                    </div> 
                                </div>
                              </div>                            
                        </fieldset>  

  <?php   //echo $POST['Admin rest'];
echo "<a href='login.php'</a>";

if (!isset($_POST['submit2'])) {

    require_once __DIR__ . '/db_connect.php';

    $db = new DB_CONNECT();
     
    // sent from form
    $username = $_POST['username'];
    $password = $_POST['password'];

    if(strcmp($_POST['tipo'], "Admin rest") ==0){
         
        $sql= ("SELECT * FROM Administrador_restaurant WHERE Adm_email = '$username' and Adm_direccion='$password'") or die(mysql_error());
         
        $result=mysql_query($sql);
         
        // counting table row
        $count = mysql_num_rows($result);
        // Si es correcto el $username y $password
        while ($row = mysql_fetch_array($result)) {
           
            $idAdministrador_restaurant = $row["idAdministrador_restaurant"];
            $Adm_nombre = $row["Adm_nombre"];
            $Adm_email = $row["Adm_email"];
            $Adm_direccion = $row["Adm_direccion"];
            $Adm_apellidoPaterno = $row["Adm_apellidoPaterno"];
            $Adm_apellidoMaterno = $row["Adm_apellidoMaterno"];
            $Adm_rut = $row["Adm_rut"];
        }

         
        if($count == 1){
         
             $_SESSION['loggedin'] = true;
             $_SESSION['username'] = $username;
             $_SESSION['start'] = time();
             $_SESSION['expire'] = $_SESSION['start'] + (5 * 60) ;        
?>
<!-- -->

    <meta http-equiv="REFRESH" content="0;url=http://localhost:8888/web/perfilAdm.php?var1=<?php echo "$idAdministrador_restaurant"?><?php echo "&admnombre=$Adm_nombre"?>">
    
 <?php
        }
        else {
?>

        <script>
        alert('Username o Password estan incorrectos. Vuelva a intentarlo');
        </script>

<?php
        }
    }

   
    if(strcmp($_POST['tipo'], "Admin sist") ==0){

     
    $sql2= ("SELECT * FROM Administrador_sistema WHERE Adm_user = '$username' and Adm_pass ='$password'") or die(mysql_error());
     
    $result2=mysql_query($sql2);
     
    // counting table row
    $count = mysql_num_rows($result2);
    // If result matched $username and $password
    while ($row = mysql_fetch_array($result2)) {
       
        $Adm_user = $row["Adm_user"];
        $Adm_pass = $row["Adm_pass"];
    }

    if($count == 1){
         
         $_SESSION['loggedin'] = true;
         $_SESSION['username'] = $username;
         $_SESSION['start'] = time();
         $_SESSION['expire'] = $_SESSION['start'] + (5 * 60) ;
     
?>
<!-- -->
    <meta http-equiv="REFRESH" content="0;url=http://localhost:8888/web/perfilAdmSist.php">
    
<?php
    }
     else {
?>
        <script language='javascript' type="text/javascript">
        alert('Username o Password estan incorrectos. Vuelva a intentarlo');
        </script>
<?php
        }
    }
}
    
     
?>
<!--//////////////////////////////////// -->

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
