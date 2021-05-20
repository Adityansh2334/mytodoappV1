package com.note.indiatodo.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_notes")
public class Notes implements Serializable {
    private static final Long serId=83947839938492893L;

    @Id
    @GenericGenerator(name = "gen_id",strategy = "increment")
    @GeneratedValue(generator = "gen_id")
    @Column(name = "id")
    private Long id;
    @Column(name = "user_note", columnDefinition = "TEXT")
    private String user_note;
    @Column(name = "create_date")
    private String create_date;
    @Column(name = "target_date")
    private String target_date;
    @Column(name = "user_preference")
    private Integer user_preference;
    @ManyToOne(cascade = CascadeType.ALL)
    private Users users;

    public static Long getSerId() {
        return serId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTarget_date() {
        return target_date;
    }

    public void setTarget_date(String target_date) {
        this.target_date = target_date;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getUser_preference() {
        return user_preference;
    }

    public void setUser_preference(Integer user_preference) {
        this.user_preference = user_preference;
    }
}
