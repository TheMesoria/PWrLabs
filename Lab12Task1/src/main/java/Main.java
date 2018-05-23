import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try
        {
            GameHandler gh = new GameHandler(10);
            gh.start();
//            ScriptEngineManager sem = new ScriptEngineManager();
//            ScriptEngine engine = sem.getEngineByName("nashorn");
//            Invocable invocable = (Invocable)engine;
//            engine.eval(new FileReader("src/main/js/demo.js"));
//            Integer[] arr = {10,11,12,13,14};
////            Object res = invocable.invokeFunction("dropMostPowerfulOne", new int[]{10,11,12,13,14});
//            invocable.invokeFunction("dropMostPowerfulOne", Arrays.asList(arr));
//            System.out.println("wat");
////            System.out.println(res);
//
//            engine.eval(new FileReader("src/main/js/demo.js"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
