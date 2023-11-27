package com.moeabdel.assignment4;

import java.io.Serializable;

public class Officials implements Serializable {

        private String city;
        private String state;
        private String zip;
        private String officialsTitle;
        private String officialsName;
        private String officialsAddressLine1;
        private String officialsAddressLine2;
        private String officialsState;
        private String officialsCity;
        private String officialsZip;
        private String officialsParty;
        private String officialsPhone;
        private boolean hasFacebook = false;
        private boolean hasTwitter = false;
        private boolean hasYoutube = false;
        private String facebookUrl;
        private String twitterUrl;
        private String youtubeUrl;




        public String getCity() {
                return city;
        }
        public boolean getFacebookStatus(){
                return hasFacebook;
        }
        public boolean getTwitterStatus(){
                return hasTwitter;
        }
        public boolean getYoutubeStatus(){
                return hasYoutube;
        }
        public String getState() {
                return state;
        }

        public String getZip() {
                return zip;
        }

        public String getOfficialsTitle() {
                return officialsTitle;
        }

        public String getOfficialsName() {
                return officialsName;
        }

        public String getOfficialsAddressLine1() {
                return officialsAddressLine1;
        }

        public String getOfficialsAddressLine2() {
                return officialsAddressLine2;
        }

        public String getOfficialsState() {
                return officialsState;
        }

        public String getOfficialsCity() {
                return officialsCity;
        }

        public String getOfficialsZip() {
                return officialsZip;
        }

        public String getOfficialsParty() {
                return officialsParty;
        }

        public String getOfficialsPhone() {
                return officialsPhone;
        }

        public String getOfficialsUrl() {
                return officialsUrl;
        }

        public String getOfficialsEmail() {
                return officialsEmail;
        }

        public String getOfficialsPhotoUrl() {
                return officialsPhotoUrl;
        }

        public String getOfficialsFacebook() {
                return officialsFacebook;
        }

        public String getOfficialsTwitter() {
                return officialsTwitter;
        }

        public String getOfficialsYoutube() {
                return officialsYoutube;
        }
        public String getSocialMediaId(){
                return socialMediaId;
        }
        public String getFacebookUrl(){
                return  facebookUrl;
        }
        public String getTwitterUrl(){
                return twitterUrl;
        }
        public String getYoutubeUrl(){
                return youtubeUrl;
        }

        private String officialsUrl;
        private String officialsEmail;
        private String officialsPhotoUrl;
        private String officialsFacebook;
        private String officialsTwitter;
        private String officialsYoutube;
        private String socialMediaId;

        public Officials( String socialMediaId,String facebookUrl, String twitterUrl, String youtubeUrl,boolean hasFacebook, boolean hasTwitter, boolean hasYoutube, String title, String name, String addressLine1, String addressLine2, String city, String state, String zipcode, String party,
                         String phoneNum, String url, String email, String photoUrl, String facebookChannel){
                this.hasFacebook = hasFacebook;
                this.socialMediaId = socialMediaId;
                this.hasTwitter = hasTwitter;
                this.hasYoutube = hasYoutube;
                officialsTitle = title;
                officialsName = name;
                officialsAddressLine1 = addressLine1;
                officialsAddressLine2 = addressLine2;
                this.city = city;
                this.state = state;
                this.zip = zipcode;
                officialsParty = party;
                officialsPhone = phoneNum;
                officialsUrl = url;
                officialsEmail = email;
                officialsPhotoUrl = photoUrl;
                officialsFacebook = facebookChannel;
               // officialsTwitter = twitterChannel;
               // officialsYoutube = youtubeChannel;
                this.facebookUrl = facebookUrl;
                this.twitterUrl = twitterUrl;
                this.youtubeUrl = youtubeUrl;
        }
}
