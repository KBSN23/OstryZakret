package org.uoz.uwagaostryzakret;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;
import org.uoz.uwagaostryzakret.classes.*;


import static org.testfx.assertions.api.Assertions.assertThat;

public class OfflineGameTest extends ApplicationTest {

    private Button button;

    @Mock OfflineGame offlineGame;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(stage, "offline game", "offline-game.fxml");
        History.add(scene);
    }

    @Test
    void shouldResetSnakeScores() {
        Snake snake1 = new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake1");
        Snake snake2 = new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake1");

        snake1.setScore(100);
        snake1.setScore(101);

        Options.snakes.add(snake1);
        Options.snakes.add(snake2);

        offlineGame = new OfflineGame();

        assertThat(snake1.getScore()).isEqualTo(0);
        assertThat(snake2.getScore()).isEqualTo(0);
    }

    @Test
    void shouldRandomSnakePosition() {
        Snake snake = new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake1");
        Options.snakes.add(snake);

        offlineGame = new OfflineGame();

        assertThat(snake.coords.size()).isEqualTo(0);
        offlineGame.start();
        assertThat(snake.coords.size()).isEqualTo(1);
    }
}
