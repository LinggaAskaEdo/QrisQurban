package com.qris.qurban.model;

public class DataQR
{
    private String name;
    private String email;
    private String phone;
    private String year;

    public DataQR(String name, String email, String phone, String year)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.year = year;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }
}
