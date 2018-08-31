<?php

require "../Classes/Account.php";
require "../Classes/DatabaseHandler.php";

$account = new Account();
$account->setFirstname(filter_input(INPUT_POST, "first_name", FILTER_SANITIZE_STRING));
$account->setLastname(filter_input(INPUT_POST, "last_name", FILTER_SANITIZE_STRING));
$account->setUsername(filter_input(INPUT_POST, "username", FILTER_SANITIZE_STRING));
$account->setPassword(filter_input(INPUT_POST, "password", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();
if(!empty($account->getUsername() && !empty($account->getPassword())) && !empty($account->getFirstname()) && !empty($account->getLastname())){
    if(!$handler->checkExistUser($account)){
        $data = $handler->addAccount($account);
        echo json_encode($data);
    }else{
        echo json_encode(array("status" => "failed", "message" => "Username Already Taken!"));
    }
}else{
    echo json_encode(array("status" => "failed", "message" => "Please Fill All The Fields"));
}
?>