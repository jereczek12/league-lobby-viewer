package view;

import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class LobbyViewerUI extends JFrame {
    private JButton getNamesButton;
    private JButton opggButton;
    private JTextField summoner1;
    private JTextField summoner2;
    private JTextField summoner3;
    private JTextField summoner5;
    private JTextField summoner4;
    private List<JTextField> summonersTextFields;
    private JPanel mainPanel;
    private JButton copyNamesButton;


    public LobbyViewerUI() {
        this.setContentPane(mainPanel);
        this.setSize(300, 350);
        this.setLocationRelativeTo(null);
        initiateSummonerNamesList();
        setNoTextFieldBorder();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    void initiateSummonerNamesList() {
        summonersTextFields = new ArrayList<>();
        summonersTextFields.add(summoner1);
        summonersTextFields.add(summoner2);
        summonersTextFields.add(summoner3);
        summonersTextFields.add(summoner5);
        summonersTextFields.add(summoner4);
    }

    void setNoTextFieldBorder() {
        for (JTextField summonersTextField : summonersTextFields) {
            summonersTextField.setBorder(BorderFactory.createEmptyBorder());
        }
    }


}
