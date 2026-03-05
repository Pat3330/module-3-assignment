package com.example.module3assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {

    private int[] cardValues = new int[4];
    @FXML
    private Button clearButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button verifyButton;

    @FXML
    private TextField topTextField;
    @FXML
    private TextField bottomTextField;


    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;

    @FXML
    public void initialize() {
        showCards();
    }

    @FXML
    private void onRefreshButtonPress() {
        showCards();
    }

    @FXML
    private void onClearButtonPress() {
        topTextField.setText("");
        bottomTextField.setText("");
    }

    @FXML
    private void onVerifyButtonPress() {

        String input = bottomTextField.getText();

        int[] usedNumbers = extractNumbers(input);

        if (!usesCorrectNumbers(usedNumbers)){
            topTextField.setText("Invalid input");
            return;
        }

        double result = evaluateSolution(input);
        if (Double.isNaN(result)){
            topTextField.setText("Invalid input");
            return;
        }
        if (result == 24){
            topTextField.setText("Correct");
        } else{
            topTextField.setText("Incorrect");
        }
    }


    private Image loadCards(String fileName){
        return new Image(getClass().getResourceAsStream("/com/example/module3assignment/PlayingCards/png/" + fileName + ".png"));
    }

    private void showCards(){
        for (int i = 0; i < 4; i++){
            cardValues[i] = getRandomValue();
        }
        card1.setImage(loadCards(getRandomCardFIleName(cardValues[0])));
        card2.setImage(loadCards(getRandomCardFIleName(cardValues[1])));
        card3.setImage(loadCards(getRandomCardFIleName(cardValues[2])));
        card4.setImage(loadCards(getRandomCardFIleName(cardValues[3])));
    }

    private String getRandomCardFIleName(int value){
        String[] suits = {"hearts", "clubs", "spades", "diamonds"};
        int suit = (int)(Math.random() * 4);
        return valueToName(value) + "_of_" + suits[suit];
    }

    private int getRandomValue() {
        return (int)(Math.random() * 13) + 1; // 1–13
    }

    private String valueToName(int value) {
        return switch (value) {
            case 1 -> "ace";
            case 11 -> "jack";
            case 12 -> "queen";
            case 13 -> "king";
            default -> String.valueOf(value);
        };
    }

    private int[] extractNumbers(String expr) {
        return java.util.Arrays.stream(expr.split("[^0-9]+"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    private boolean usesCorrectNumbers(int[] used) {
        if (used.length != 4) return false;
        int[] sortedUsed = used.clone();
        int[] sortedCards = cardValues.clone();
        java.util.Arrays.sort(sortedUsed);
        java.util.Arrays.sort(sortedCards);
        return java.util.Arrays.equals(sortedUsed, sortedCards);
    }




    private double evaluateSolution(String expr) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expr.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;

                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expr.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }


}
