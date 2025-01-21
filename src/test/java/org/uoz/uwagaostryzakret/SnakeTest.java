package org.uoz.uwagaostryzakret;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uoz.uwagaostryzakret.classes.Snake;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(Color.RED, KeyCode.A, KeyCode.D, "Snake1");
    }

    @Test
    void testInitialScore() {
        assertEquals(0, snake.getScore());
    }

    @Test
    void testSetScore() {
        snake.setScore(100);
        assertEquals(100, snake.getScore());
    }

    @Test
    void testIncreaseScore() {
        snake.increaseScore();
        assertEquals(1, snake.getScore());
    }

    @Test
    void testKill() {
        snake.kill();
        assertFalse(snake.isAlive);
    }

    @Test
    void testGetColor() {
        assertEquals(Color.RED, snake.getColor());
    }

    @Test
    void testSetColor() {
        snake.setColor(Color.BLUE);
        assertEquals(Color.BLUE, snake.getColor());
    }

    @Test
    void testGetLeftKey() {
        assertEquals(KeyCode.A, snake.getLeftKey());
    }

    @Test
    void testSetLeftKey() {
        snake.setLeftKey(KeyCode.LEFT);
        assertEquals(KeyCode.LEFT, snake.getLeftKey());
    }

    @Test
    void testGetRightKey() {
        assertEquals(KeyCode.D, snake.getRightKey());
    }

    @Test
    void testSetRightKey() {
        snake.setRightKey(KeyCode.RIGHT);
        assertEquals(KeyCode.RIGHT, snake.getRightKey());
    }
}
