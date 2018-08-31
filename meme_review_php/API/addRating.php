<?php

require "../Classes/Rating.php";
require "../Classes/DatabaseHandler.php";

$rating = new Rating();
$rating->setUser_id(filter_input(INPUT_POST, "user_id", FILTER_SANITIZE_STRING));
$rating->setMeme_id(filter_input(INPUT_POST, "meme_id", FILTER_SANITIZE_STRING));
$rating->setRating_id(filter_input(INPUT_POST, "rating_id", FILTER_SANITIZE_STRING));
$rating->setRating_comment(filter_input(INPUT_POST, "rating_comment", FILTER_SANITIZE_STRING));


$handler = new DatabaseHandler();
if(!empty($rating->getUser_id()) && !empty($rating->getMeme_id()) && !empty($rating->getRating_id()) && !empty($rating->getRating_comment())){
    if($handler->checkExistRating($rating)){ // user already rated the meme
        $status = $handler->updateRating($rating);
    }else{ // no rating yet
        $status = $handler->addRating($rating);
    }
    echo json_encode($status);
}else{
    echo json_encode(array("status" => "failed", "message" => "Please Fill All The Fields"));
}
?>