<?php

include("connection.php");

$uid = $_POST['uid'];
$fineamount = $_POST['amount'];
$fineid=$_POST['fid'];

//$uid =2;
//$fineamount =100;

// Check if wallet amount is greater than or equal to toll amount
$sql = "SELECT amount FROM wallet_tbl WHERE user_id='$uid'";
$result = mysqli_query($con, $sql);
if ($result && mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $walletAmount = $row['amount'];
    if ($walletAmount >= $fineamount) {
        // Wallet has sufficient amount, deduct toll amount from wallet
        $newWalletAmount = $walletAmount - $fineamount;
        $updateSql = "UPDATE wallet_tbl SET amount='$newWalletAmount' WHERE user_id='$uid'";
        if (mysqli_query($con, $updateSql)) {
			
			 $sql3 = "UPDATE fine SET status='paid successfully' WHERE id='$fineid'";
			 mysqli_query($con,$sql3);
			 
            echo "success";
        } else {
            echo "failed";
        }
    } else {
        // Wallet has insufficient amount
        echo "insufficient";
    }
} else {
    echo "failed";
}

?>
