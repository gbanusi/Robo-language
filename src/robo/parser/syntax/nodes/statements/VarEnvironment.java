package robo.parser.syntax.nodes.statements;

import robo.parser.lexical.Token;

import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by gregor on 06.08.16..
 */
public class VarEnvironment {
    private Hashtable table;

    protected static LinkedList<VarEnvironment> prev = new LinkedList<>();

    public VarEnvironment(){
        table = new Hashtable();
        prev.addFirst(this);
    }

    public void removeEnvironment(){
        prev.remove(this);
    }

    public void put(Token var, String nv){
        table.put(var, nv);
    }

    public static String get(Token w){
        for(VarEnvironment e : prev){
            String found = (String)(e.table.get(w));
            if(found != null){
                return found;
            }
        }
        return null;
    }
}
