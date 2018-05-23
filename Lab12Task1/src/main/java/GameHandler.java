import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameHandler {
    List<Integer> playerCards, aiCards, baseCards = new ArrayList<>();
    ScriptEngine engine;
    Invocable invocable;

    enum Tactic {
        POWER,
        OBEDIENCE,
        SMART
    }

    Tactic tactic = Tactic.POWER;

    public GameHandler(int amountOfCards) throws Exception {
        for (int i = 0; i < amountOfCards; i++) {
            baseCards.add(ThreadLocalRandom.current().nextInt(0, 100));
        }
        playerCards = new ArrayList<Integer>(baseCards);
        aiCards = new ArrayList<Integer>(baseCards);

        ScriptEngineManager sem = new ScriptEngineManager();
        engine = sem.getEngineByName("nashorn");
        invocable = (Invocable) engine;
        engine.eval(new FileReader("src/main/js/demo.js"));

        System.out.println("Deal: " + baseCards);
    }

    public void start() throws Exception {
        Integer result = 0;
        while (playerCards.size() != 0 && aiCards.size() != 0) {
            Scanner scanner = new Scanner(System.in);
            int yourPlay = 0, enemyPlay = 0;
            System.out.println("\n\n\n\nYour cards: " + playerCards);
            System.out.println("AI cards: " + aiCards);
            System.out.println("Current Tactic: " + tactic);
            System.out.println("Current result: " + result);
            System.out.println("-1) full power play\n-2) obedient play\n-3) 'smart' play\n-4)Load File.");
            Integer choice = scanner.nextInt();
            if (choice == -1) {
                tactic = Tactic.POWER;
                continue;
            } else if (choice == -2) {
                tactic = Tactic.OBEDIENCE;
                continue;
            } else if (choice == -3) {
                tactic = Tactic.SMART;
                continue;
            } else if (choice == -4) {
                System.out.println("File name (no ext, in js folder!):");
                String name = scanner.next();
                reloadScript(name);
                continue;
            }


            if (choice != '\n') {
                if(choice>playerCards.size()) continue;
                yourPlay = playTheCard(choice);
                System.out.println("Player: Plays -> " + yourPlay);
                if (tactic == Tactic.POWER) {
                    enemyPlay = rawPowerSolvesAllProblems();
                    System.out.println("AI: Plays -> " + enemyPlay);
                    Thread.sleep(1000);
                } else if (tactic == Tactic.OBEDIENCE) {
                    enemyPlay = someKindOfEdgyFunctionName();
                    System.out.println("AI: Plays -> " + enemyPlay);
                    Thread.sleep(1000);
                } else if (tactic == Tactic.SMART) {
                    enemyPlay = supposlySmartAlghorythm();
                    System.out.println("AI: Plays -> " + enemyPlay);
                    Thread.sleep(1000);
                }

                if(yourPlay>enemyPlay)
                    result+=1;
                else if(yourPlay<enemyPlay)
                    result-=1;

                Thread.sleep(2000);
            }
        }
    }

    int playTheCard(int number) {
        int res = playerCards.get(number);
        playerCards.remove(number);
        return res;
    }

    int rawPowerSolvesAllProblems() throws Exception {
        Object choosenCard = invocable.invokeFunction("dropMostPowerfulOne", Collections.singletonList(aiCards));
        aiCards.remove(choosenCard);

        return (Integer) choosenCard;
    }

    int someKindOfEdgyFunctionName() throws Exception {
        Object choosenCard = invocable.invokeFunction("makeABiasedMove", Collections.singletonList(aiCards), Collections.singletonList(playerCards));
        aiCards.remove(choosenCard);

        return (Integer) choosenCard;
    }

    int supposlySmartAlghorythm() throws Exception {
        Object choosenCard = invocable.invokeFunction("makeItRain", Collections.singletonList(aiCards), Collections.singletonList(playerCards));
        aiCards.remove(choosenCard);

        return (Integer) choosenCard;
    }

    void reloadScript(String name){
        try{
            ScriptEngineManager sem = new ScriptEngineManager();
            engine = sem.getEngineByName("nashorn");
            invocable = (Invocable) engine;
            engine.eval(new FileReader("src/main/js/" + name + ".js"));
        }catch(Exception e){
            System.out.println("File does not exist, or is not placed in js folder!");
            e.printStackTrace();
        }
    }
}
