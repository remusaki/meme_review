<?php
require "../Classes/Meme.php";
require "../Classes/DatabaseHandler.php";


$meme = new Meme();
$meme->setMemes_id(filter_input(INPUT_POST, "meme_id", FILTER_SANITIZE_STRING));
$meme->setIsApproved(filter_input(INPUT_POST, "approval", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();

$result = $handler->updateMemeApproval($meme);
echo json_encode($result);
?>

