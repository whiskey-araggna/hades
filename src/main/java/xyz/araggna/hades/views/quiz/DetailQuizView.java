package xyz.araggna.hades.views.quiz;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.shared.communication.PushMode;
import jakarta.annotation.security.PermitAll;
import xyz.araggna.hades.entity.QuizAnswer;
import xyz.araggna.hades.entity.QuizQuestion;
import xyz.araggna.hades.security.AuthenticatedUser;
import xyz.araggna.hades.services.QuizAnswerService;
import xyz.araggna.hades.services.QuizQuestionService;
import xyz.araggna.hades.services.ScoreService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@PageTitle("Empty")
@Route("/detail-quiz")
@PermitAll
public class DetailQuizView extends VerticalLayout implements HasUrlParameter<String> {

    private final QuizQuestionService quizQuestionService;
    private final QuizAnswerService quizAnswerService;
    private final AuthenticatedUser authenticatedUser;
    private final ScoreService scoreService;

    private Registration scoreListenerRegistration;

    private ScheduledExecutorService scheduler;

    private List<QuizQuestion> quizQuestions;
    private Integer score;

    public DetailQuizView(QuizQuestionService quizQuestionService, QuizAnswerService quizAnswerService, AuthenticatedUser authenticatedUser, ScoreService scoreService) {
        this.quizQuestionService = quizQuestionService;
        this.quizAnswerService = quizAnswerService;
        this.authenticatedUser = authenticatedUser;
        this.scoreService = scoreService;

        score = 0;

        scoreListenerRegistration = scoreService.addListener(this::handleScoreUpdate);

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateScore ,0, 1, TimeUnit.SECONDS);
     }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {

        setQuestions(s);
        quizQuestions.forEach(quizQuestion -> add(quizLayout(quizQuestion)));

    }

    private void setQuestions(String code) {
        quizQuestions = quizQuestionService.getAll(code);
    }

    private VerticalLayout quizLayout(QuizQuestion quizQuestion) {

        VerticalLayout quizLayout = new VerticalLayout();
        quizLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        quizLayout.setAlignItems(Alignment.CENTER);

        H2 quiz = new H2(quizQuestion.getQuestion());
        quizLayout.add(quiz);

        List<QuizAnswer> quizAnswerList = quizAnswerService.getQuizAnswersByQuestionId(quizQuestion.getId());

        RadioButtonGroup<QuizAnswer> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setItems(quizAnswerList);
        radioGroup.setItemLabelGenerator(QuizAnswer::getOption);
        radioGroup.addValueChangeListener(event -> {
            if (event.getValue().isCorrect()) {
                updateScore();
            }
        });

        quizLayout.add(radioGroup);

        return quizLayout;
    }

    private void handleScoreUpdate(String username, int score) {
        // This method will be called for all users
        UI.getCurrent().access(() -> {
            // Optional: Show notification
            Notification.show(
                    username + " has scored " + score + " points!",
                    3000,
                    Notification.Position.TOP_END
            );
        });
    }

    private void updateScore() {
        authenticatedUser.get().ifPresent(user -> {
            score += 10;

            // Notify all listeners about the score update
            scoreService.notifyScoreUpdate(user.getEmail(), score);
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        // Unregister the listener and shutdown scheduler
        if (scoreListenerRegistration != null) {
            scoreListenerRegistration.remove();
        }

        scheduler.shutdown();
        super.onDetach(detachEvent);
    }

}
