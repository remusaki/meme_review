<?php

include_once '../Classes/Config.php';
include_once '../Classes/Account.php';
include_once '../Classes/Meme.php';
include_once '../Classes/Rating.php';

class DatabaseHandler {
    Private $config;

    function __construct() {
        $this->config = new Config();
    }
    
    public function checkExistUser($account) {
        $query = "CALL checkExistUser('" . $account->getUsername() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        $row = mysqli_num_rows($result);
        $find = ($row > 0 ? true : false);
        mysqli_free_result($result);
        mysqli_next_result($this->config->getLink());
        if($find){
            return true;
        }else{
            return false;
        }
    }
    
    public function checkAccountValid($account){
        $query = "CALL getUser('" . $account->getUsername() . "','"
                . $account->getPassword() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query); 
        
        $row = mysqli_num_rows($result);
        $find = ($row > 0 ? true : false);
        mysqli_free_result($result);
        mysqli_next_result($this->config->getLink());
        if($find){
            return true;
        }else{
            return false;
        }
    }
    
    public function addAccount($account){
        $query = "CALL addAccount('" . $account->getLastname() . "','"
                . $account->getFirstname() . "','"
                . $account->getUsername() . "','"
                . $account->getPassword() . "')"
                or die(mysqli_error($this->config->getLink()));

        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Registration Successful");
        } else {
            return array("status" => "failed", "message" => "Registration Failed, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    public function loginAccount($account){
        $query = "CALL getUser('" . $account->getUsername() . "','"
                . $account->getPassword() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query); 

        $arraylist = array();
        while($row = mysqli_fetch_array($result)){
            $account = new Account();  
            $account->setUser_id($row["user_id"]);
            $account->setLastname($row["lastname"]);
            $account->setFirstname($row["firstname"]);
            $account->setAccount_type($row["account_type"]);
            $arraylist[] = $account;
        }
        return $arraylist; 
    }
    
    public function addMeme($meme){
        $query = "CALL addMeme('" . $meme->getName() . "','"
                . $meme->getDescription() . "','"
                . $meme->getFullpath() ."','"
                . $meme->getCategory() ."')"
                or die(mysqli_error($this->config->getLink()));

        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Meme Added for admin approval");
        } else {
            return array("status" => "failed", "message" => "Adding Meme Failed, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    public function deleteMeme($meme){
        $query = "CALL deleteMeme('" . $meme->getMemes_id() ."')"
                or die(mysqli_error($this->config->getLink()));

        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Meme Deleted.");
        } else {
            return array("status" => "failed", "message" => "Failed to Delete Meme, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    public function getAllMemes(){
        $query = "CALL getAllMemes()"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $memes = array();
        while($row = mysqli_fetch_array($result)){
            $meme = new Meme();
            $meme->setMemes_id($row["memes_id"]);
            $meme->setName($row["name"]);
            $meme->setDescription($row["description"]);
            $meme->setFullpath($row["fullpath"]);
            $meme->setTimestamp($row["timestamp"]);
            $meme->setIsApproved($row["isApproved"]);
            $meme->setCategory($row["category"]);
            $memes[] = $meme;
        }
        return $memes;
    }
    
    public function getAllMemeRating($rating){
        $query = "CALL getAllMemeRating('" . $rating->getMeme_id() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $ratings = array();
        while($row = mysqli_fetch_array($result)){
            $rating = new Rating();
            $rating->setLastname($row["lastname"]);
            $rating->setFirstname($row["firstname"]);
            $rating->setRating_id($row["rating_id"]);
            $rating->setRating_comment($row["rating_comment"]);
            $ratings[] = $rating;
        }
        return $ratings;
    }
    
    public function getAllUserRatings($account){
        $query = "CALL getAllUserRatings('" . $account->getUser_id() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $memes = array();
        while($row = mysqli_fetch_array($result)){
            $meme = new Meme();
            $meme->setMemes_id($row["memes_id"]);
            $meme->setName($row["name"]);
            $meme->setDescription($row["description"]);
            $meme->setFullpath($row["fullpath"]);
            $meme->setTimestamp($row["timestamp"]);
            $meme->setIsApproved($row["isApproved"]);
            $meme->setCategory($row["category"]);
            $meme->setRating($row["rating_id"]);
            $memes[] = $meme;
        }
        return $memes;
    }
    
    public function getAllUserRatingsASC($account){
        $query = "CALL getAllUserRatingsASC('" . $account->getUser_id() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $memes = array();
        while($row = mysqli_fetch_array($result)){
            $meme = new Meme();
            $meme->setMemes_id($row["memes_id"]);
            $meme->setName($row["name"]);
            $meme->setDescription($row["description"]);
            $meme->setFullpath($row["fullpath"]);
            $meme->setTimestamp($row["timestamp"]);
            $meme->setIsApproved($row["isApproved"]);
            $meme->setCategory($row["category"]);
            $meme->setRating($row["rating_id"]);
            $memes[] = $meme;
        }
        return $memes;
    }
    
    public function getAllUserRatingsDESC($account){
        $query = "CALL getAllUserRatingsDESC('" . $account->getUser_id() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $memes = array();
        while($row = mysqli_fetch_array($result)){
            $meme = new Meme();
            $meme->setMemes_id($row["memes_id"]);
            $meme->setName($row["name"]);
            $meme->setDescription($row["description"]);
            $meme->setFullpath($row["fullpath"]);
            $meme->setTimestamp($row["timestamp"]);
            $meme->setIsApproved($row["isApproved"]);
            $meme->setCategory($row["category"]);
            $meme->setRating($row["rating_id"]);
            $memes[] = $meme;
        }
        return $memes;
    }
    // add rating
    
    public function checkExistRating($rating){
        $query = "CALL checkExistRating('" . $rating->getUser_id() . "','"
                . $rating->getMeme_id(). "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        $row = mysqli_num_rows($result);
        $find = ($row > 0 ? true : false);
        mysqli_free_result($result);
        mysqli_next_result($this->config->getLink());
        if($find){
            return true;
        }else{
            return false;
        }
    }
    
    public function addRating($rating){
        $query = "CALL addRating('" . $rating->getUser_id() . "','"
                . $rating->getMeme_id(). "','"
                . $rating->getRating_id(). "','"
                . $rating->getRating_comment(). "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Meme Rated");
        } else {
            return array("status" => "failed", "message" => "Failed to Rate MEME, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    public function updateRating($rating){
        $query = "CALL updateRating('" . $rating->getUser_id() . "','"
                . $rating->getMeme_id(). "','"
                . $rating->getRating_id(). "','"
                . $rating->getRating_comment(). "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Meme Rating Updated");
        } else {
            return array("status" => "failed", "message" => "Failed to Update MEME Rating, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    public function getOneUserRating($rating){
        $query = "CALL getOneUserRatings('" . $rating->getUser_id() . "','"
                . $rating->getMeme_id(). "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $rates = array();
        while($row = mysqli_fetch_array($result)){
            $rate = new Rating();
            $rate->setRating_id($row["rating_id"]);
            $rate->setRating_comment($row["rating_comment"]);
            $rates[] = $rate;
        }
        return $rates;
    }
    
    public function updateMemeApproval($meme){
        $query = "CALL updateMemeApproval('" . $meme->getMemes_id() . "','"
                . $meme->getIsApproved(). "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Meme Approval Updated");
        } else {
            return array("status" => "failed", "message" => "Failed to Update Meme Approval, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    //forgot account / update
    public function forgotPassword($account){
        $query = "CALL forgotPassword('" . $account->getUsername() . "','"
                . $account->getPassword().  "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Password Reset");
        } else {
            return array("status" => "failed", "message" => "Failed to Reset Password, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
    
    public function getUserDetails($account){
        $query = "CALL getUserDetails('" . $account->getUser_id() . "')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        
        $accounts = array();
        while($row = mysqli_fetch_array($result)){
            $account = new Account();
            $account->setLastname($row["lastname"]);
            $account->setFirstname($row["firstname"]);
            $account->setUsername($row["username"]);
            $accounts[] = $account;
        }
        return $accounts;
    }
    
    public function updateAccount($account){
        $query = "CALL updateAccount('" . $account->getLastname() . "','"
                . $account->getFirstname(). "','"
                . $account->getUsername(). "','"
                . $account->getPassword()."')"
                or die(mysqli_error($this->config->getLink()));
        $result = mysqli_query($this->config->getLink(), $query);
        if ($result) {
            return array("status" => "success", "message" => "Account Updated");
        } else {
            return array("status" => "failed", "message" => "Failed to Update Account, Error: ".  mysqli_error($this->config->getLink()));
        }
    }
}
