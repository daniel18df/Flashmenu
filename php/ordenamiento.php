<?php
$buscar=$_POST["buscar"];


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

  int aux, j;   
        
    for(int i=1; i < 15; i++){
        
     aux = arreglo[i].edad;
     j = i-1;
     while((j>=0) && (aux<arreglo[j].edad)){
            arreglo[j+1].edad = arreglo[j].edad;
            j--;
     }
     arreglo[j+1].edad = aux;
    }
?>