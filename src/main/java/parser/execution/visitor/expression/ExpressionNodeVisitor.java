package parser.execution.visitor.expression;

import parser.syntax.nodes.expression.comparation.*;
import parser.syntax.nodes.expression.logical.NodeExpressionAnd;
import parser.syntax.nodes.expression.logical.NodeExpressionNot;
import parser.syntax.nodes.expression.logical.NodeExpressionOr;
import parser.syntax.nodes.expression.operations.*;
import parser.syntax.nodes.expression.other.NodeExpressionUnReference;
import parser.syntax.nodes.expression.value.left.NodeArray;
import parser.syntax.nodes.expression.value.left.NodeArrayIndexing;
import parser.syntax.nodes.expression.value.left.NodeMatrix;
import parser.syntax.nodes.expression.value.left.NodeVariable;
import parser.syntax.nodes.expression.value.right.NodeConstant;
import parser.syntax.nodes.expression.value.right.NodeFunction;
import parser.syntax.nodes.expression.operations.NodeExpressionDot;

/**
 * Created by gregor on 14.08.16..
 */
public interface ExpressionNodeVisitor {

    // OPERATIONS
    void visit(NodeExpressionAdd nea);

    void visit(NodeExpressionSub nes);

    void visit(NodeExpressionMult nem);

    void visit(NodeExpressionDiv ned);

    // VALUE HOLDERS
    void visit(NodeConstant nc);

    void visit(NodeVariable nv);

    void visit(NodeFunction nf);

    // LOGICAL
    void visit(NodeExpressionAnd nea);

    void visit(NodeExpressionOr neo);

    // RELATIONS
    void visit(NodeExpressionEquality nee);

    void visit(NodeExpressionNoEquality nen);

    void visit(NodeExpressionLTRelation ner);

    void visit(NodeExpressionLERelation ner);

    void visit(NodeExpressionGERelation ner);

    void visit(NodeExpressionGTRelation ner);

    void visit(NodeExpressionUnMinus neu);

    void visit(NodeExpressionUnReference neu);

    void visit(NodeArrayIndexing nodeArrayIndexing);

    void visit(NodeArray nodeArray);

    void visit(NodeExpressionNot nodeExpNot);

    void visit(NodeMatrix nodeMatrix);

    void visit(NodeExpressionDot nodeFunctionChain);
}
