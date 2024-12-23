package xyz.araggna.hades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.araggna.hades.entity.QuizUser;

import java.util.List;

@Repository
public interface QuizUserRepository extends JpaRepository<QuizUser, Long> {

    List<QuizUser> findAllByUserId_Email(String email);

}
