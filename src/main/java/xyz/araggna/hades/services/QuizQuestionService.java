package xyz.araggna.hades.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.araggna.hades.entity.QuizQuestion;
import xyz.araggna.hades.repository.QuizQuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;

    public List<QuizQuestion> getAll(String code) {
        return quizQuestionRepository.findAllByQuiz_Code(code);
    }

}
