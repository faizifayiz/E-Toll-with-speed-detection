<?php
$dbhost							= "localhost";
$dbuser							= "root";
$dbpass							= "";
$dbname							= "budget_tracker";

$conn = mysql_connect($dbhost, $dbuser, $dbpass) or die ("Error connecting to database");
mysql_select_db($dbname);
?>