<?php
require "../Classes/Account.php";
require "../Classes/DatabaseHandler.php";


$account = new Account();
$account->setUsername(filter_input(INPUT_POST, "username", FILTER_SANITIZE_STRING));
$account->setPassword("1234");

$handler = new DatabaseHandler();
if($handler->checkExistUser($account)){
    $result = $handler->forgotPassword($account);
    echo json_encode($result);
}else{
    echo json_encode(array("status" => "failed", "message" => "Account Does Not Exist"));
}


?>

