<?php
 
class funciones_BD { 
    private $db;
    // constructor
    function __construct() {
        require_once 'db_connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
 
    }
  
    public function login($user,$passw){

        $consulta = "SELECT COUNT(*) FROM Cliente WHERE Cliente_email = '$user' AND Cliente_direccion = '$passw'";
        $result = mysql_query($consulta); 
        $count = mysql_fetch_row($result);
       
        if ($count[0]==0)
        {
            return true;
        }else
        {
            return false;
        }
        }//login
  
}//funciones bd
 
 
?>