<?php

include("connection.php");

$did = $_REQUEST['uid'];

$sql = "Select * from payment_tbl WHERE user_id='$did'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result))
		$data["data"][] = $row;
	echo json_encode($data);
}
else{
	echo "failed";
}

?>