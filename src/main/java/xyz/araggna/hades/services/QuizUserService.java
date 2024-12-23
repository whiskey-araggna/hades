package xyz.araggna.hades.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.araggna.hades.entity.QuizUser;
import xyz.araggna.hades.repository.QuizUserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizUserService {

    private QuizUserRepository quizUserRepository;

    public List<QuizUser> getQuizUsers(String userId) {
        return quizUserRepository.findAllByUserId_Email(userId);
    }

}
