<?php

include("connection.php");

//$bookId = $_POST['bookId'];
$user_id = $_POST['userid'];
$amount = $_POST['amount'];
$cardname = $_POST['cardname'];
$cardnumber = $_POST['cardnumber'];
$cardtype = $_POST['cardtype'];
$cardmonth = $_POST['cardmonth'];
$cardyear = $_POST['cardyear'];
$cvv = $_POST['cvv'];

$date = date('d-m-y');

$sql = "INSERT INTO payment_tbl(user_id, date, amount, card_name, cardnumber, cardtype, cardmonth, cardyear, cvv) Values('$user_id', '$date', '$amount', '$cardname', '$cardnumber', '$cardtype', '$cardmonth', '$cardyear', '$cvv')";

$res = mysqli_query($con,$sql);

if ($res) {
    $update_sql = "UPDATE wallet_tbl SET amount = amount + '$amount' WHERE user_id = '$user_id'";
    $update_res = mysqli_query($con, $update_sql);

    if ($update_res) {
        echo "success";
    } else {
        echo "failed";
    }
} else {
    echo "failed";
}

?>






