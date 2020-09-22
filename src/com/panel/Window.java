package com.panel;

import com.characters.Bushido;
import com.characters.Character;
import com.characters.Templar;
import com.characters.Viking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    private final JPanel container = new JPanel();
    private JPanel classContainer = new JPanel();
    private JPanel subtitleContainer = new JPanel();
    // Prendra les valeurs text des méthodes block/hit 
    private JPanel infoContainer = new JPanel();
    private JPanel buttonContainer = new JPanel();
    private JLabel labelTitle = new JLabel();
    private JLabel labelSubtitle = new JLabel();
    private JLabel action = new JLabel();
    private JLabel[] infoPlayer = new JLabel[2];
    private JLabel[] lifePlayer = new JLabel[2];

    private final String[] tabClass = {"Viking", "Templar", "Bushido"};
    private JButton[] tabButton = new JButton[tabClass.length];

    private Character[] charac = new Character[2];
    private boolean[] choiceCharacterPlayer = new boolean[2];
    /*
     * playerHit[i] = true if player attack | i = index player
     * i = 0 = player1 | i = 1 = player2
     */
    private boolean[] playerHit = new boolean[2];
    /*
    * playerHit[i] = true if player defence | i = index player
    * i = 0 = player1 | i = 1 = player2
    */
    private boolean[] playerDef = new boolean[2];
    // actionPlayer = false = turn player1 | true = turn player2
    private boolean actionPlayer = false;
    private boolean fightMode = false;
    private boolean gameEnd = false;


    public Window() {
        this.setTitle("Ancient Battle Game");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.setLayout(new GridLayout(4, 1));

        characterChoicePanel();
        
        this.setContentPane(container);
        this.setVisible(true);
    }

    private void characterChoicePanel() {
        labelTitle.setText("Choisir un personnage ! ");
        labelSubtitle.setText("Choix du joueur 1 !");

        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelSubtitle.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < tabClass.length; i++) {
            tabButton[i] = new JButton(tabClass[i]);

            // event pour le choix du perso
            switch (i) {
                case 0 -> tabButton[i].addActionListener(new VikingListener());
                case 1 -> tabButton[i].addActionListener(new TemplarListener());
                case 2 -> tabButton[i].addActionListener(new BushidoListener());
            }

            classContainer.setLayout(new GridLayout(2, 1));
            buttonContainer.setLayout(new GridLayout(1, 3));

            buttonContainer.add(tabButton[i]);
            classContainer.add(labelSubtitle);
            classContainer.add(buttonContainer);

            container.add(labelTitle);
            container.add(classContainer);
        }
    }

    private void fightingPanel() {
        /* call méthode pour remove panel 1*/
        // remove tous ce qui était unique à l'ancienne interface
        buttonContainer = new JPanel(new BorderLayout());

        container.remove(classContainer);
        fightMode = true;

        for(int i = 0; i < 2; i++) {
            infoPlayer[i] = new JLabel("Player " + (i + 1));
            lifePlayer[i] = new JLabel(charac[i].getLife() + " HP");
        }

        labelTitle.setText("Combattez !");
        labelSubtitle.setText("Tour player 1 !");

        JButton buttonAttack = new JButton("Attaquer");
        JButton buttonDefence = new JButton("Se défendre");
        buttonAttack.addActionListener(new AttackListener());
        buttonDefence.addActionListener(new DefenceListener());

        buttonContainer.setLayout(new GridLayout(1, 2));
        buttonContainer.add(buttonAttack);
        buttonContainer.add(buttonDefence);

        infoContainer.setLayout(new GridLayout(3, 1));
        for(int i = 0; i < 2; i++) {
            infoContainer.add(infoPlayer[i]);
            infoContainer.add(lifePlayer[i]);
        }
        infoContainer.add(action);
        
        subtitleContainer.add(labelSubtitle);

        container.add(subtitleContainer);
        container.add(infoContainer);
        container.add(buttonContainer);
    }

    /**
     *
     * @param c Character
     */
    private void instanciesClass(Character c) {

        // Si le J1 à choisi son premier perso et que le J2 n'a pas choisi son perso
        if(choiceCharacterPlayer[0] && !choiceCharacterPlayer[1]) {
            charac[1] = c;
            choiceCharacterPlayer[1] = true;
        }
        // Si le J1 n'a pas encore choisi son perso
        if(!choiceCharacterPlayer[0]) {
            charac[0] = c;
            choiceCharacterPlayer[0] = true;
            labelSubtitle.setText("Choix du joueur 2 !");
        }
    }

    /**
     *
     * @param c Character
     * @param i int
     */
    private void newLife(Character c, int i) {
        // player 1 attacked
        if(i == 0) { c.hpDecrease(false); }
        // player 2 attacked
        if(i == 1) { c.hpDecrease(true); }

        lifePlayer[i].setText(c.getLife() + " HP");
    }

    private void victory() {
        if(charac[0].getLife() <= 0 && charac[1].getLife() > 0) {
            labelSubtitle.setText("Le joueur " + 2 + " a gagné !");
            gameEnd = true;
        } else if(charac[1].getLife() <= 0 && charac[0].getLife() > 0) {
            labelSubtitle.setText("Le joueur " + 1 + " a gagné !");
            gameEnd = true;
        } else if (charac[0].getLife() <= 0 && charac[1].getLife() <= 0) {
            labelSubtitle.setText("Match nul !");
            gameEnd = true;
        }

        if(gameEnd) {
            container.remove(buttonContainer);
            buttonContainer = new JPanel(new BorderLayout());
            JButton button = new JButton("Return menu");
            button.addActionListener(new ChoiceMenuListener());
            buttonContainer.add(button);
            container.add(buttonContainer);

        }
    }

    /**
     *
     * @param c Character
     */
    private void getFightAction(Character c) {
        action.setText(c.fight());
    }

    /**
     *
     * @param c Character
     */
    private void getBlockAction(Character c) {
        action.setText(c.block());
    }

    // Effectue les actions des joueurs
    private void turnAction() {
        labelSubtitle.setText("Tour player 1 !");
        if(playerHit[0] && playerHit[1]) {
            newLife(charac[0], 0);
            newLife(charac[1], 1);

            action.setText("Vous attaquez tous les deux !");
        } else if(playerHit[0] && playerDef[1]) {
            newLife(charac[1], 1);

        } else if(playerDef[0] && playerHit[1]) {
            newLife(charac[0], 0);

        } else {
            action.setText("Personne n'a attaqué !");
        }
        victory();
    }

    private void resetTurnData() {
        playerHit = new boolean[2];
        playerDef = new boolean[2];
        actionPlayer = false;
    }

    private void resetAll() {
        resetTurnData();
        choiceCharacterPlayer = new boolean[2];
        fightMode = false;
        gameEnd = false;
        classContainer = new JPanel();
        infoContainer = new JPanel();
        subtitleContainer = new JPanel();
        buttonContainer = new JPanel();
        labelTitle = new JLabel();
        labelSubtitle = new JLabel();
        action = new JLabel();
        action = new JLabel();
        tabButton = new JButton[tabClass.length];
        infoPlayer = new JLabel[2];
        lifePlayer = new JLabel[2];
        charac = new Character[2];

    }

    class BushidoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(!choiceCharacterPlayer[0] || !choiceCharacterPlayer[1]) {
                instanciesClass(new Bushido());
            }
            if(!fightMode && choiceCharacterPlayer[1]) { fightingPanel();}
        }
    }
    class TemplarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!choiceCharacterPlayer[0] || !choiceCharacterPlayer[1]) {
                instanciesClass(new Templar());
            }
            if(!fightMode && choiceCharacterPlayer[1]) { fightingPanel();}
        }
    }
    class VikingListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!choiceCharacterPlayer[0] || !choiceCharacterPlayer[1]) {
                instanciesClass(new Viking());
            }
            if(!fightMode && choiceCharacterPlayer[1]) { fightingPanel();}
        }
    }

    class AttackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(!actionPlayer) {
                    actionPlayer = true;
                    playerHit[0] = true;
                    charac[0].calcCharacterAttack();
                    getFightAction(charac[0]);
                    labelSubtitle.setText("Tour player 2 !");
                } else {
                    playerHit[1] = true;
                    charac[1].calcCharacterAttack();
                    getFightAction(charac[1]);
                    turnAction();
                    resetTurnData();
                }

            } catch (NullPointerException ne) {
                System.out.println(ne.getMessage());
            }
        }
    }

    class DefenceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(!actionPlayer) {
                    actionPlayer = true;
                    playerDef[0] = true;
                    charac[0].calcCharacterDef();
                    getBlockAction(charac[0]);
                    labelSubtitle.setText("Tour player 2 !");
                } else {
                    playerDef[1] = true;
                    charac[1].calcCharacterDef();
                    getBlockAction(charac[1]);
                    turnAction();
                    resetTurnData();
                }

            } catch (NullPointerException ne) {
                System.out.println(ne.getMessage());
            }
        }
    }

    class ChoiceMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            labelTitle.setText("Choisir un personnage ! ");
            labelSubtitle.setText("Choix du joueur 1 !");

            container.removeAll();
            resetAll();
            Window.this.setContentPane(container);

            characterChoicePanel();
        }
    }
}
