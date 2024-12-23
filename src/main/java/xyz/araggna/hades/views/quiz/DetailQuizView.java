package xyz.araggna.hades.views.quiz;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import xyz.araggna.hades.entity.QuizAnswer;
import xyz.araggna.hades.entity.QuizQuestion;
import xyz.araggna.hades.services.QuizAnswerService;
import xyz.araggna.hades.services.QuizQuestionService;

import java.util.List;

@PageTitle("Empty")
@Route("/detail-quiz")
@PermitAll
public class DetailQuizView extends VerticalLayout implements HasUrlParameter<String> {

    private final QuizQuestionService quizQuestionService;
    private final QuizAnswerService quizAnswerService;

    private List<QuizQuestion> quizQuestions;

    public DetailQuizView(QuizQuestionService quizQuestionService, QuizAnswerService quizAnswerService) {
        this.quizQuestionService = quizQuestionService;
        this.quizAnswerService = quizAnswerService;
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
                Notification.show("Correct").open();
            }
        });

        quizLayout.add(radioGroup);

        return quizLayout;
    }

    private void radioButtonAction() {

    }

}
