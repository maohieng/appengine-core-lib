/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.model;

/**
 * Created by MAO Hieng on 6/14/18.
 * <p>
 * Embedded field for contact channel–website, phone1, phone2, twitter, etc.
 * </p>
 */
public class Contact {

    public static class Builder {
        private String phone1;
        private String phone2;
        private String phone3;
        private String twitter;
        private String facebook;
        private String website;
        private String youtube;
        private String email;

        /**
         * Default constructor.
         **/
        public Builder() {
        }

        public Builder phone1(String phone1) {
            this.phone1 = phone1;
            return this;
        }

        public Builder phone2(String phone2) {
            this.phone2 = phone2;
            return this;
        }

        public Builder phone3(String phone3) {
            this.phone3 = phone3;
            return this;
        }

        public Builder phone(String phone1, String phone2) {
            this.phone1 = phone1;
            this.phone2 = phone2;
            return this;
        }

        public Builder phone(String phone1, String phone2, String phone3) {
            this.phone1 = phone1;
            this.phone2 = phone2;
            this.phone3 = phone3;
            return this;
        }

        public Builder twitter(String twitter) {
            this.twitter = twitter;
            return this;
        }

        public Builder facebook(String facebook) {
            this.facebook = facebook;
            return this;
        }

        public Builder website(String website) {
            this.website = website;
            return this;
        }

        public Builder youtube(String youtube) {
            this.youtube = youtube;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    String phone1;
    String phone2;
    String phone3;
    String twitter;
    String facebook;
    String website;
    String youtube;
    String email;

    /**
     * Default constructor.
     **/
    public Contact() {
    }

    private Contact(Builder b) {
        this.phone1 = b.phone1;
        this.phone2 = b.phone2;
        this.phone3 = b.phone3;
        this.twitter = b.twitter;
        this.facebook = b.facebook;
        this.website = b.website;
        this.youtube = b.youtube;
        this.email = b.email;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getWebsite() {
        return website;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getEmail() {
        return email;
    }
}
