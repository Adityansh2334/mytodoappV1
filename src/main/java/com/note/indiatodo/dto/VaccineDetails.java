package com.note.indiatodo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class VaccineDetails {
    private Long center_id;
    private Long lat;
    private String name;
    private String address;
    private String state_name;
    private String district_name;
    private String block_name;
    private Long pincode;
    private String from;
    private String to;
    private String fee_type;
    private String date;
    private Long available_capacity;
    private Long available_capacity_dose1;
    private Long available_capacity_dose2;
    private String fee;
    private Integer min_age_limit;
    private String vaccine;
    private List<String> slots;
    private String session_id;

    public Long getCenter_id() {
        return center_id;
    }

    public void setCenter_id(Long center_id) {
        this.center_id = center_id;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(Long available_capacity) {
        this.available_capacity = available_capacity;
    }

    public Long getAvailable_capacity_dose1() {
        return available_capacity_dose1;
    }

    public void setAvailable_capacity_dose1(Long available_capacity_dose1) {
        this.available_capacity_dose1 = available_capacity_dose1;
    }

    public Long getAvailable_capacity_dose2() {
        return available_capacity_dose2;
    }

    public void setAvailable_capacity_dose2(Long available_capacity_dose2) {
        this.available_capacity_dose2 = available_capacity_dose2;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Integer getMin_age_limit() {
        return min_age_limit;
    }

    public void setMin_age_limit(Integer min_age_limit) {
        this.min_age_limit = min_age_limit;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public List<String> getSlots() {
        return slots;
    }

    public void setSlots(List<String> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return "VaccineDetails{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", state_name='" + state_name + '\'' +
                ", district_name='" + district_name + '\'' +
                ", block_name='" + block_name + '\'' +
                ", pincode=" + pincode +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", date='" + date + '\'' +
                ", available_capacity=" + available_capacity +
                ", available_capacity_dose1=" + available_capacity_dose1 +
                ", available_capacity_dose2=" + available_capacity_dose2 +
                ", fee='" + fee + '\'' +
                ", min_age_limit=" + min_age_limit +
                ", vaccine='" + vaccine + '\'' +
                ", slots=" + slots +
                '}';
    }
}
