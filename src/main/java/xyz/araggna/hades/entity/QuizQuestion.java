package xyz.araggna.hades.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "hadesquizquestion")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestion extends AbstractEntity {

    @JoinColumn(name = "quizid")
    @ManyToOne
    private Quiz quiz;

    @Column(name = "question")
    private String question;

}
