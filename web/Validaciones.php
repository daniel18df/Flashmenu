<?php 
 function validaRequerido($valor){
    if(trim($valor) == ''){
       return false;
    }else{
       return true;
    }
 }
 function validarEntero($valor, $opciones=null){
    if(filter_var($precio, FILTER_VALIDATE_INT, $opciones) === FALSE){
       return false;
    }else{
       return true;
    }
 }
 function validaEmail($valor){
    if(filter_var($valor, FILTER_VALIDATE_EMAIL) === FALSE){
       return false;
    }else{
       return true;
    }
 }


function validaUnico($valor, $valor2, $valor3){

$sql = "SELECT COUNT(*)  FROM Prodcuto WHERE Producto_nombre = '$valor'";
$consulta = mysql_query($sql) or die( mysql_error() );
$dato = mysql_result($consulta, 0);



  if ($dato >= 1) { 
  echo "Campo cedula ya esta registrado";
  } else {

  $sql = "INSERT INTO Producto VALUES
  ('','".$valor."','".$valor2."','".$valor3."')";

  $consulta = mysql_query($sql, $conexion) or die( mysql_error() );
  }
  if(!mysql_error()) {echo "Bien";}
  else {echo "Intente mas tarde";}

  mysql_close($conexion);
  }

//VALIDACION DE UN CAMPO QUE NO SE DEBE REPETIR
<?php
if($_POST['nombre'])

{
include "conexion.php";

$cedula = $_POST['cedula'];

$sql = "SELECT COUNT(*)  FROM diplomados WHERE cedula = '$cedula'";
$consulta = mysql_query($sql, $conexion) or die( mysql_error() );
$dato = mysql_result($consulta, 0);

if ($dato >= 1) { 
echo "Campo cedula ya esta registrado";
} else {

$sql = "INSERT INTO diplomados VALUES
('','".$_POST['nombre']."','".$_POST['apellido']."','".$_POST['cedula']."','".$_POST['sexo']."','".$_POST['nacionalidad']."','".$_POST['inscripcion']."','".$_POST['email']."','".$_POST['telefono']."')";

$consulta = mysql_query($sql, $conexion) or die( mysql_error() );
}
if(!mysql_error()) {echo "Bien";}
else {echo "Intente mas tarde";}

mysql_close($conexion);
}
?>


?>