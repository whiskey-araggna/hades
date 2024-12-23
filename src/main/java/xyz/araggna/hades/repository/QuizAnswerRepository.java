package xyz.araggna.hades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.araggna.hades.entity.QuizAnswer;

import java.util.List;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

    List<QuizAnswer> findAllByQuestionId(Long questionId);

}
