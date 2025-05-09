package com.odiga.user.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.review.entity.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String profileImageUrl;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
}
