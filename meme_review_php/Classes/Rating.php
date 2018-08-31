<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Rating
 *
 * @author djmurusaki
 */
class Rating implements JsonSerializable{
    
    private $user_id, $meme_id, $rating_id, $rating_comment, $firstname, $lastname; 
    
    function __construct() {
        $this->user_id = "";
        $this->meme_id = "";
        $this->rating_id = "";
        $this->rating_comment = "";
        $this->firstname = "";
        $this->lastname = "";
    }

    public function getUser_id() {
        return $this->user_id;
    }

    public function getMeme_id() {
        return $this->meme_id;
    }

    public function getRating_id() {
        return $this->rating_id;
    }

    public function getRating_comment() {
        return $this->rating_comment;
    }

    public function setUser_id($user_id) {
        $this->user_id = $user_id;
    }

    public function setMeme_id($meme_id) {
        $this->meme_id = $meme_id;
    }

    public function setRating_id($rating_id) {
        $this->rating_id = $rating_id;
    }

    public function setRating_comment($rating_comment) {
        $this->rating_comment = $rating_comment;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }
    
    public function getFirstname() {
        return $this->firstname;
    }

    public function getLastname() {
        return $this->lastname;
    }

    public function setFirstname($firstname) {
        $this->firstname = $firstname;
    }

    public function setLastname($lastname) {
        $this->lastname = $lastname;
    }   

}
