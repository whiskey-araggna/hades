package xyz.araggna.hades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.araggna.hades.entity.QuizQuestion;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findAllByQuiz_Code(String code);

}
