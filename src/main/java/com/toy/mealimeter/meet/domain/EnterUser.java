package com.toy.mealimeter.meet.domain;

import com.toy.mealimeter.common.domain.BaseTimeEntity;
import com.toy.mealimeter.user.domain.User;
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
public class EnterUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "meet_id_fk")
    private Meet meet;

    @OneToOne
    @JoinColumn(name = "uid_fk")
    private User user;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private final EnterStatus enterStatus = EnterStatus.Enter;
}
