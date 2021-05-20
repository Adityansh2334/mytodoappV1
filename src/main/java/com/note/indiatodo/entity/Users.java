package com.note.indiatodo.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.net.URL;

@Entity
@Table(name = "user_data")
public class Users implements Serializable {
    private static final Long serUID=8392938859583L;

    @Id
    @GenericGenerator(name = "gen_id", strategy = "increment")
    @GeneratedValue(generator = "gen_id")
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "User Name is Required")
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "user_emai_ph")
    private String user_emai_ph;
    @NotBlank(message = "Password is Required")
    @Column(name = "user_password")
    private String user_password;
    @Column(name = "user_image")
    private URL user_image;
    @Transient
    private MultipartFile image;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public URL getUser_image() {
        return user_image;
    }

    public void setUser_image(URL user_image) {
        this.user_image = user_image;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_emai_ph() {
        return user_emai_ph;
    }

    public void setUser_emai_ph(String user_emai_ph) {
        this.user_emai_ph = user_emai_ph;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", user_emai_ph='" + user_emai_ph + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_image=" + user_image +
                ", image=" + image +
                '}';
    }
}
