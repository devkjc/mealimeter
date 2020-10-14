package com.toy.mealimeter.user.domain;

import com.toy.mealimeter.common.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Area extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name="uid_fk", nullable = false)
    private String uid;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String guGun;

    private Boolean mainStatus;

}
