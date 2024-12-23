package xyz.araggna.hades.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "hadesquiz")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz extends AbstractEntity {

    @Column(name = "quizname")
    private String name;

    @Column(name = "quizdescription")
    private String description;

    @Column(name = "codequiz")
    private String code;

}
