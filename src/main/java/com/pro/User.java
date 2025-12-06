package com.pro;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String otp;
    private java.util.Date otpExpiry;
    private boolean isAdmin;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public java.util.Date getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(java.util.Date otpExpiry) { this.otpExpiry = otpExpiry; }

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
}
