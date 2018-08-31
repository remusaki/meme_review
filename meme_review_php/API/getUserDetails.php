<?php

require "../Classes/Account.php";
require "../Classes/DatabaseHandler.php";

$account = new Account();
$account->setUser_id(filter_input(INPUT_POST, "user_id", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();
$data = $handler->getUserDetails($account);
echo json_encode(array("status" => "success", "message" => $data));
?>