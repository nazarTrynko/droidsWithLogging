package droids;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Nazar on 21.02.2015.
 */
public class Battle {
    int myDroidTurn = -1;
    int enemyDroidTurn = -1;

    int turnNumber = 1;
    int target;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger LOG = Logger.getLogger(Battle.class);

    public Battle(Team myTeam, Team enemyTeam) {
        while (!myTeam.getDroidList().isEmpty() && !enemyTeam.getDroidList().isEmpty()) {
            System.out.println("Enemy team: ");
            enemyTeam.showInfo();

            myTurn(myTeam, enemyTeam);

            enemyTurn(myTeam, enemyTeam);
            if (!myTeam.getDroidList().isEmpty()) {
                System.out.println("My team: ");
                myTeam.showInfo();
            }
            else {
                System.out.println("Enemy team: ");
                enemyTeam.showInfo();
            }

        }
        if (myTeam.getDroidList().size() >= 1) {
            System.out.println("YOU WIN!!!");
        }
        else System.out.println("You lose!");
    }


    private void chooseRandomTarget(Team myTeam) {
        Random random = new Random();
        if (myTeam.getDroidList().size() == 1)   {
            target = 0;
        }
        else
        target = random.nextInt(myTeam.getDroidList().size() - 1) + 1;

    }

    private void enemyTurn(Team myTeam, Team enemyTeam)  {
        if (enemyTeam.getDroidList().isEmpty()) return;
        int checkMyTeamSize;

        if (turnNumber <=2){
            chooseRandomTarget(myTeam);
        }

        turnEnemy(enemyDroidTurn, enemyTeam);
        System.out.println("    Turn number " + turnNumber);
        System.out.println("Enemy's " + enemyTeam.getDroidList().get(enemyDroidTurn) + " turn");
        System.out.println("Enemy is attacking your " + myTeam.getDroidList().get(target));

        checkMyTeamSize = myTeam.getDroidList().size();
        shootEnemyDroid(enemyTeam, myTeam, enemyDroidTurn, target);
        if (myTeam.getDroidList().isEmpty()) return;
        turnNumber++;
        if (myTeam.getDroidList().size() < checkMyTeamSize) {
            chooseRandomTarget(myTeam);
        }
    }

    private void myTurn(Team myTeam, Team enemyTeam) {
        LOG.info("    Turn number " + turnNumber);
        turn(myDroidTurn, myTeam);
        String enemyIndex;
        int enemyDroidIndex;

        LOG.info("Your " + myTeam.getDroidList().get(myDroidTurn) + " turn");
        LOG.info("Choose your enemy!\n");

        try {
            do {
                int maximumIndexOfDroid = enemyTeam.getDroidList().size();
                enemyIndex = reader.readLine();
                if (!enemyIndex.matches("[1-5]")){
                    LOG.warn("Invalid request. Please enter number: 1-" + enemyTeam.getDroidList().size());
                }
                else if (Integer.parseInt(enemyIndex) > enemyTeam.getDroidList().size()) {
                    enemyIndex = "";
                    LOG.warn("Choose existing enemy!");
                }
            }
            while (!enemyIndex.matches("[1-5]"));
            enemyDroidIndex = Integer.parseInt(enemyIndex) - 1;
            shootEnemyDroid(myTeam, enemyTeam, myDroidTurn, enemyDroidIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }

        turnNumber++;
    }

    private void turn(int turn, Team Team) {

        if (turn < Team.getDroidList().size() - 1) {
            myDroidTurn++;
        }
        else if(turn >= Team.getDroidList().size() - 1) {
            myDroidTurn = 0;
        }

    }
    private void turnEnemy(int turn, Team Team) {

        if (turn < Team.getDroidList().size() - 1) {
            enemyDroidTurn++;
        }
        else if(turn >= Team.getDroidList().size() - 1) {
            enemyDroidTurn = 0;
        }
    }

    private static void shootEnemyDroid(Team myTeam, Team enemyTeam, int myDroidIndex, int enemyDroidIndex) {
        myTeam.getDroidList().get(myDroidIndex).shoot(enemyTeam.getDroidList().get(enemyDroidIndex));

        // remove droid if it has 0 HP
        if (enemyTeam.getDroidList().get(enemyDroidIndex).getHealth() <= 0) {
            enemyTeam.getDroidList().remove(enemyDroidIndex);
        }
    }

    

}
