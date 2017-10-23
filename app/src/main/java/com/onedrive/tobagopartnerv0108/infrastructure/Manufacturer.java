package com.onedrive.tobagopartnerv0108.infrastructure;

/**
 * Created on 12-Sep-17.
 */

public class Manufacturer {
    private String accessToken, id, branchName, slug, taxCode, idCity, shop, link, fullName, sex, email, fone, address, logo, info;

    public Manufacturer() {}

    public Manufacturer(String id, String accessToken, String branchName, String slug, String taxCode, String idCity, String shop, String link, String fullName, String sex, String email, String fone, String address, String logo, String info) {
        this.accessToken = accessToken;
        this.id = id;
        this.branchName = branchName;
        this.slug = slug;
        this.taxCode = taxCode;
        this.idCity = idCity;
        this.shop = shop;
        this.link = link;
        this.fullName = fullName;
        this.sex = sex;
        this.email = email;
        this.fone = fone;
        this.address = address;
        this.logo = logo;
        this.info = info;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
