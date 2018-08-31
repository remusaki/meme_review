<?php

require "../Classes/Account.php";
require "../Classes/DatabaseHandler.php";

$account = new Account();
$account->setUsername(filter_input(INPUT_POST, "username", FILTER_SANITIZE_STRING));
$account->setPassword(filter_input(INPUT_POST, "password", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();
if(!empty($account->getUsername() && !empty($account->getPassword()))){
    if($handler->checkAccountValid($account)){
        $data = $handler->loginAccount($account);
        echo json_encode(array("status" => "success", "message" => $data));
    }else{
        echo json_encode(array("status" => "failed", "message" => "Incorrect Username/Password!"));
    }
}else{
    echo json_encode(array("status" => "failed", "message" => "Please Fill All The Fields"));
}
?>