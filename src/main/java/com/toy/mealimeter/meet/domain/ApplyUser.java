package com.toy.mealimeter.meet.domain;

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
public class ApplyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "meet_id_fk")
    private Meet meet;

    @OneToOne
    @JoinColumn(name = "uid_fk")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private ApplyStatus applyStatus = ApplyStatus.Apply;

    public void setApplyStatus(ApplyStatus applyStatus) {
        this.applyStatus = applyStatus;
    }

    public enum ApplyStatus {
        Apply,
        Accept,
        Reject
    }

}
