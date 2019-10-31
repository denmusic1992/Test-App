package ru.meteor.chornyy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("AddressIndex")
    @Expose
    private String addressIndex;
    @SerializedName("AddressCity")
    @Expose
    private String addressCity;
    @SerializedName("AddressStreet")
    @Expose
    private String addressStreet;
    @SerializedName("AddressHouse")
    @Expose
    private String addressHouse;
    @SerializedName("AddressOffice")
    @Expose
    private String addressOffice;
    @SerializedName("AddressComment")
    @Expose
    private String addressComment;

    public String getFullAddress() {
        StringBuilder builder = new StringBuilder();
        // Если индекс непустой
        if (!addressIndex.isEmpty()) {
            builder.append(String.format("%s", addressIndex));
        }
        // Если город непустой
        if (!addressCity.isEmpty()) {
            builder.append(String.format(", г. %s", addressCity));
        }
        // Если улица непустая
        if (!addressStreet.isEmpty()) {
            builder.append(String.format(", ул. %s", addressStreet));
        }
        // Если дом непустой
        if (!addressHouse.isEmpty()) {
            builder.append(String.format(", д. %s", addressHouse));
        }
        // Если офис непустой
        if (!addressOffice.isEmpty()) {
            builder.append(String.format(", офис %s", addressOffice));
        }
        // Если примечания непустые
        if (!addressComment.isEmpty()) {
            builder.append(String.format(" (%s)", addressComment));
        }

        return builder.toString();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Contact withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddressIndex() {
        return addressIndex;
    }

    public void setAddressIndex(String addressIndex) {
        this.addressIndex = addressIndex;
    }

    public Contact withAddressIndex(String addressIndex) {
        this.addressIndex = addressIndex;
        return this;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public Contact withAddressCity(String addressCity) {
        this.addressCity = addressCity;
        return this;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Contact withAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
        return this;
    }

    public String getAddressHouse() {
        return addressHouse;
    }

    public void setAddressHouse(String addressHouse) {
        this.addressHouse = addressHouse;
    }

    public Contact withAddressHouse(String addressHouse) {
        this.addressHouse = addressHouse;
        return this;
    }

    public String getAddressOffice() {
        return addressOffice;
    }

    public void setAddressOffice(String addressOffice) {
        this.addressOffice = addressOffice;
    }

    public Contact withAddressOffice(String addressOffice) {
        this.addressOffice = addressOffice;
        return this;
    }

    public String getAddressComment() {
        return addressComment;
    }

    public void setAddressComment(String addressComment) {
        this.addressComment = addressComment;
    }

    public Contact withAddressComment(String addressComment) {
        this.addressComment = addressComment;
        return this;
    }

}