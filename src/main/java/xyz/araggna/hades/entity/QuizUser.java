package xyz.araggna.hades.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "hadesquizuser")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizUser extends AbstractEntity {

    @JoinColumn(name = "quizid")
    @ManyToOne
    private Quiz quizId;

    @JoinColumn(name = "userid")
    @ManyToOne
    private User userId;


}
