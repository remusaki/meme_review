<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Meme
 *
 * @author djmurusaki
 */
class Meme implements JsonSerializable{
    
    private $memes_id, $name, $description, $fullpath, $timestamp, $isApproved, $category, $rating;

    function __construct() {
        $this->memes_id = "";
        $this->name = "";
        $this->description = "";
        $this->fullpath = "";
        $this->timestamp = "";
        $this->isApproved = "";
        $this->category = "";
        $this->rating = "0";
    }

    public function getMemes_id() {
        return $this->memes_id;
    }

    public function getName() {
        return $this->name;
    }

    public function getDescription() {
        return $this->description;
    }

    public function getFullpath() {
        return $this->fullpath;
    }

    public function getTimestamp() {
        return $this->timestamp;
    }

    public function getIsApproved() {
        return $this->isApproved;
    }

    public function getCategory() {
        return $this->category;
    }

    public function setMemes_id($memes_id) {
        $this->memes_id = $memes_id;
    }

    public function setName($name) {
        $this->name = $name;
    }

    public function setDescription($description) {
        $this->description = $description;
    }

    public function setFullpath($fullpath) {
        $this->fullpath = $fullpath;
    }

    public function setTimestamp($timestamp) {
        $this->timestamp = $timestamp;
    }

    public function setIsApproved($isApproved) {
        $this->isApproved = $isApproved;
    }

    public function setCategory($category) {
        $this->category = $category;
    }

    public function getRating() {
        return $this->rating;
    }

    public function setRating($rating) {
        $this->rating = $rating;
    }

            
    
    public function jsonSerialize() {
        return get_object_vars($this);
    }
}
