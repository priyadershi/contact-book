package com.contacts.model;

public class Contact {
  private String name, phone, email, address, dob;
  int id;

  public String getName() {
    return name;
  }

  public Contact() {
    name = phone = email = address = null;
    id = -1;
  }

  public Contact(int id, String name, String phone, String email, String address, String dob) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.dob = dob;
    this.id = id;
  }

  public Contact(String name, String phone, String email, String address, String dob) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.dob = dob;
    this.id = -1;
  }

  @Override
  public String toString() {
    return "Contact [id = " + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", address=" + address
        + ", dob=" + dob
        + "]";
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public int getId() {
    return id;
  }

}
