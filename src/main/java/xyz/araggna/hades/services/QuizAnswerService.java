package xyz.araggna.hades.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.araggna.hades.entity.QuizAnswer;
import xyz.araggna.hades.repository.QuizAnswerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizAnswerService {

    private final QuizAnswerRepository quizAnswerRepository;

    public List<QuizAnswer> getQuizAnswersByQuestionId(Long questionId) {
        return quizAnswerRepository.findAllByQuestionId(questionId);
    }

}
