
<?php
if (!isset($_POST['Rest_email'])) {

            }else{
              $mensaje="---**** CLIENTE LLEGO AL RESTAURANT ****---";
              //$mensaje.= "\nNombre: ". $_POST['nombre'];
              $mensaje.= "\nNro reserva: ".$_POST['idreserva'];
              $mensaje.= "\nCliente: ".$_POST['Cliente_email'];
              $mensaje.= "\nHora: ".$_POST['Hora'];
              $mensaje.= "\nFecha: ".$_POST['Fecha'];
              $mensaje.= "\nNumero de mesa: ".$_POST['Nro_mesa'];
              $mensaje.= "\nProductos comprados: ".$_POST['productos'];


              $destino= $_POST['Rest_email'];
              $remitente = "daniel@flashmenu.cl";
              $asunto = "CLIENTE HA LLEGADO" ;

              mail($destino,$asunto,$mensaje,$remitente);
                $response["success"] = 1;

              echo json_encode($response);
            }
            ?>