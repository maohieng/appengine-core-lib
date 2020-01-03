/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.model;

/**
 * Currently, place is not a Kind (table) because of query performance.
 */
public class Place {

    public static class Builder {
        String address1;
        String street1;
        String address2;
        String street2;
        String postalCode;
        String city;
        double latitude;
        double longitude;
        String village;
        String commune;
        String district;
        String province;
        String country;

        public Builder location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            return this;
        }

        public Builder address1(String address1) {
            this.address1 = address1;
            return this;
        }

        public Builder street1(String street1) {
            this.street1 = street1;
            return this;
        }

        public Builder address2(String address2) {
            this.address2 = address2;
            return this;
        }

        public Builder street2(String street2) {
            this.street2 = street2;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder village(String village) {
            this.village = village;
            return this;
        }

        public Builder commune(String commune) {
            this.commune = commune;
            return this;
        }

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    String address1;
    String street1;
    String address2;
    String street2;
    String postalCode;

    String city;

    double latitude;
    double longitude;

    String village;
    String commune;
    String district;
    String province;

    String country;

    public Place() {
    }

    private Place(Builder b) {
        this.address1 = b.address1;
        this.street1 = b.street1;
        this.address2 = b.address2;
        this.street2 = b.street2;
        this.postalCode = b.postalCode;
        this.city = b.city;
        this.latitude = b.latitude;
        this.longitude = b.longitude;
        this.village = b.village;
        this.commune = b.commune;
        this.district = b.district;
        this.province = b.province;
        this.country = b.country;
    }

    public String getAddress1() {
        return address1;
    }

    public String getStreet1() {
        return street1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getStreet2() {
        return street2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getVillage() {
        return village;
    }

    public String getCommune() {
        return commune;
    }

    public String getDistrict() {
        return district;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }
}
