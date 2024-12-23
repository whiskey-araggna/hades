package xyz.araggna.hades.services;

import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

@Service
public class ScoreService {
    private final List<BiConsumer<String, Integer>> listeners = new CopyOnWriteArrayList<>();

    public Registration addListener(BiConsumer<String, Integer> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public void notifyScoreUpdate(String username, int score) {
        // Notify all registered listeners
        listeners.forEach(listener -> listener.accept(username, score));
    }

}