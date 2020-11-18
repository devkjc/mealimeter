package com.toy.mealimeter.user.domain;

import com.toy.mealimeter.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    private Boolean mainStatus = true;

    public Area stateChange(Boolean state) {
        this.mainStatus = state;
        return this;
    }

}
