
<?php
if (!isset($_POST['Cliente_email'])) {

            }else{
              $mensaje="---** DATOS DE LA RESERVA **---";
              //$mensaje.= "\nNombre: ". $_POST['nombre'];
              $mensaje.= "\nNro reserva: ".$_POST['idreserva'];
              $mensaje.= "\nHora: ".$_POST['Hora'];
              $mensaje.= "\nFecha: ".$_POST['Fecha'];
              $mensaje.= "\nNumero de mesa: ".$_POST['Nro_mesa'];
              $mensaje.= "\nDireccion restaurant: ".$_POST['Rest_direccion'];              
              $mensaje.= "\nProductos comprados: ".$_POST['productos'];


              $destino= $_POST['Cliente_email'];
              $remitente = "daniel@flashmenu.cl";
              $asunto = "Mensaje enviado por: Flashmenu";

              mail($destino,$asunto,$mensaje,$remitente);
            }

if (!isset($_POST['Rest_email'])) {

            }else{
              $mensaje="---** NUEVA RESERVA **---  www.flashmenu.cl";
              //$mensaje.= "\nNombre: ". $_POST['nombre'];
              $mensaje.= "\nNro reserva: ".$_POST['idreserva'];
              $mensaje.= "\nHora: ".$_POST['Hora'];
              $mensaje.= "\nFecha: ".$_POST['Fecha'];
              $mensaje.= "\nNumero de mesa: ".$_POST['Nro_mesa'];
              $mensaje.= "\nCliente: ".$_POST['Cliente_email'];
              $mensaje.= "\nEmail Cliente: ".$_POST['Cliente_email'];
              $mensaje.= "\nProductos comprados: ".$_POST['productos'];


              $destino= $_POST['Rest_email'];
              $remitente = "daniel@flashmenu.cl";
              $asunto = "Mensaje enviado por: Flashmenu";

              mail($destino,$asunto,$mensaje,$remitente);
            }
            ?>