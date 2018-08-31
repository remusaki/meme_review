<?php

require "../Classes/Rating.php";
require "../Classes/DatabaseHandler.php";

$rating = new Rating();
$rating->setUser_id(filter_input(INPUT_POST, "user_id", FILTER_SANITIZE_STRING));
$rating->setMeme_id(filter_input(INPUT_POST, "meme_id", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();
if(!empty($rating->getUser_id() && !empty($rating->getMeme_id()))){
    $data = $handler->getOneUserRating($rating);
    echo json_encode(array("status" => "success", "result" => $data));
}else{
    echo json_encode(array("status" => "failed", "message" => "Please Fill All The Fields"));
}
?>