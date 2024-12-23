package xyz.araggna.hades.views.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.araggna.hades.entity.Quiz;
import xyz.araggna.hades.entity.QuizUser;
import xyz.araggna.hades.entity.User;
import xyz.araggna.hades.security.AuthenticatedUser;
import xyz.araggna.hades.services.QuizUserService;

import java.util.List;
import java.util.Optional;

@PageTitle("Empty")
@Route("")
@PermitAll
public class HomeView extends VerticalLayout {

    private final QuizUserService quizUserService;
    private final AuthenticatedUser authenticatedUser;

    public HomeView(QuizUserService quizUserService, AuthenticatedUser authenticatedUser) {
        this.quizUserService = quizUserService;
        this.authenticatedUser = authenticatedUser;

        setSpacing(false);

        H1 titleLabel = new H1("Your Quiz Channel");

        titleLabel.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(titleLabel);

        VirtualList<Quiz> quizzes = new VirtualList<>();
        quizzes.setItems(setDataQuizzes());
        quizzes.setRenderer(quizComponentRenderer);
        quizzes.addClassNames("mt-xl");

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        add(quizzes);
    }

    private final ComponentRenderer<Component, Quiz> quizComponentRenderer = new ComponentRenderer<>(
        quiz -> {

            Anchor anchor = new Anchor("detail-quiz/" + quiz.getCode());

            Div quizLayout = new Div();
            quizLayout.getStyle().set("display", "flex");
            quizLayout.getStyle().set("flex-direction", "row");
            quizLayout.getStyle().set("justify-content", "center");
            quizLayout.getStyle().set("align-items", "center");
            quizLayout.getStyle().set("margin-bottom", "12px");
            quizLayout.getStyle().set("gap", "10px");

            Avatar avatarTitle = new Avatar(quiz.getName());
            avatarTitle.setWidth("64px");
            avatarTitle.setHeight("64px");

            Div infoLayout = new Div();
            infoLayout.getStyle().set("display", "flex");
            infoLayout.getStyle().set("flex-direction", "column");
            infoLayout.getStyle().set("gap", "5px");

            H2 titleQuiz = new H2(quiz.getName());
            NativeLabel descriptionQuiz = new NativeLabel(quiz.getDescription());
            NativeLabel createdDateLabel = new NativeLabel(quiz.getCreatedDate().toString());

            infoLayout.add(titleQuiz, descriptionQuiz, createdDateLabel);

            quizLayout.add(avatarTitle, infoLayout);

            anchor.add(quizLayout);

            return anchor;
        }
    );

    public List<Quiz> setDataQuizzes() {
        return authenticatedUser.get()
                .map(user -> quizUserService.getQuizUsers(user.getEmail()).stream()
                        .map(this::mappingQuizUser)
                        .toList())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private Quiz mappingQuizUser(QuizUser quizUser) {
        Quiz quiz = new Quiz();
        quiz.setId(quizUser.getId());
        quiz.setName(quizUser.getQuizId().getName());
        quiz.setDescription(quizUser.getQuizId().getDescription());
        quiz.setCreatedDate(quizUser.getQuizId().getCreatedDate());
        quiz.setCode(quizUser.getQuizId().getCode());

        return quiz;
    }

}
