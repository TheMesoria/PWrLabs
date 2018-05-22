import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("nashorn");

        Bindings ret = (Bindings) engine.eval("var obj = {value: 1}; obj;");
        System.out.println((Integer)ret.get("value"));
//        engine.eval(new FileReader("src/main/js/demo.js"));
    }
}
