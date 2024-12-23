package xyz.araggna.hades.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "hadesquizanswer")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer extends AbstractEntity {

    @JoinColumn(name = "quizid")
    @ManyToOne
    private Quiz quiz;

    @JoinColumn(name = "questionid")
    @ManyToOne
    private QuizQuestion question;

    private String option;

    private boolean correct;

}
