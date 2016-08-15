package robo.parser.execution;

import robo.parser.execution.visitor.node.ProgramExecutorVisitor;
import robo.parser.syntax.nodes.ProgramNode;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboCompile {

    private ProgramNode programNode;

    public RoboCompile(ProgramNode programNode) {
        this.programNode = programNode;
    }

    public void execute(){
        ProgramExecutorVisitor exec = new ProgramExecutorVisitor();
        programNode.accept(exec);
    }
}
