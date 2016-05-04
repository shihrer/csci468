package com.csci468.micro;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {
    // Count variables
    private int scopeCount = 1;
    private int labelCount = 0;
    private int variableCount = 0;

    private SymbolTable microSymbolTable;

    // Code generation objects
    private LinkedList<IRNode> IRNodes;
    private Stack<Integer> labelStack;
    private Stack<Expression> expressionStack = new Stack<>();
    private Stack<Stack<Expression>> stackStack = new Stack<>();

    // Constructor requires IRNodes list
    Listener(LinkedList<IRNode> IRNodes) {
        microSymbolTable = new SymbolTable();
        this.IRNodes = IRNodes;
        this.labelStack = new Stack<>();
    }

    @Override
    public void enterFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Create new scope
        microSymbolTable.createScope(ctx.ID().toString());

        Expression result = new Expression(ctx.ID().getText(), "STR");
        IRNodes.add(new IRNode("LABEL", null, null, result));
    }

    @Override
    public void exitFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Pop scope
        destroyScope();
    }

    @Override
    public void exitVarDecl(MicroParser.VarDeclContext ctx) {

        String ids = ctx.idList().getText();

        for (String id : ids.split(",")) {
            Expression result = new Expression(id, ctx.varType().toString());
            IRNodes.add(new IRNode("VAR", null, null, result));
        }
    }

    @Override
    public void enterParamDecl(MicroParser.ParamDeclContext ctx) {
        //Add parameter to scope
        microSymbolTable.createSymbol(ctx.ID().toString(), ctx.varType().getText());
    }

    @Override
    public void enterIfStmt(MicroParser.IfStmtContext ctx) {
        createBlockScope();
    }

    @Override
    public void exitIfStmt(MicroParser.IfStmtContext ctx) {
        int curLabel = labelStack.pop();
        Expression result = new Expression("label" + curLabel, "STR");
        IRNodes.add(new IRNode("LABEL", null, null, result));
        destroyScope();
    }

    @Override
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx) {
        Expression result = new Expression("label" + labelCount, "STR");
        IRNodes.add(new IRNode("LABEL", null, null, result));
        labelStack.push(labelCount);
        labelCount++;
        createBlockScope();
    }

    @Override
    public void exitWhileStmt(MicroParser.WhileStmtContext ctx) {
        int curLabel = labelStack.pop();
        int jumpLabel = labelStack.pop();
        Expression jumpResult = new Expression("label" + jumpLabel, "STR");
        Expression labelResult = new Expression("label" + curLabel, "STR");

        IRNodes.add(new IRNode("JUMP", null, null, jumpResult));
        IRNodes.add(new IRNode("LABEL", null, null, labelResult));
    }

    @Override
    public void enterElse(MicroParser.ElseContext ctx) {
        createBlockScope();
        int curLabel = labelStack.pop();

        Expression jumpResult = new Expression("label" + labelCount, "STR");
        Expression labelResult = new Expression("label" + curLabel, "STR");
        IRNodes.add(new IRNode("JUMP", null, null, jumpResult));
        IRNodes.add(new IRNode("LABEL", null, null, labelResult));
        labelStack.push(labelCount);
        labelCount++;
    }

    @Override
    public void exitElse(MicroParser.ElseContext ctx) {
        destroyScope();
    }

    @Override
    public void exitReadStmt(MicroParser.ReadStmtContext ctx) {

        String ids = ctx.idList().getText();

        for (String id : ids.split(",")) {
            Expression readResult = new Expression(id, microSymbolTable.getSymbol(id).getType());
            IRNodes.add(new IRNode("READ", readResult, null, null));
        }
    }

    private void createBlockScope() {
        //Create new scope
        microSymbolTable.createScope(scopeCount);
        scopeCount++;
    }

    private Scope destroyScope() {
        return microSymbolTable.destroyScope();
    }

    @Override
    public void enterStringDecl(MicroParser.StringDeclContext ctx) {
        //Create an entry in the current scope
        String name = ctx.ID().toString();
        String value = ctx.STRINGLITERAL().toString();
        String type = ctx.STRING().toString();
        microSymbolTable.createSymbol(name, type, value);
    }

    @Override
    public void enterVarDecl(MicroParser.VarDeclContext ctx) {
        String varType = ctx.varType().getText();
        // Create an entry in the current scope
        String names = ctx.idList().getText();

        String[] tokens = names.split(",");
        for (String s : tokens) {
            microSymbolTable.createSymbol(s, varType);
        }
    }

    @Override
    public void exitAssignExpr(MicroParser.AssignExprContext ctx) {

        //Clear out expression stack, just make sure there's nothing extra to evaluate.
        while (expressionStack.size() > 1) {
            buildExpression();
        }

        String OPCode = "STORE";
        String ID = ctx.ID().toString();
        Expression storeResult = new Expression(ID, microSymbolTable.getSymbol(ID).getType());


        // Avoid popping if the stack is empty
        if (expressionStack.size() > 0) {
            Expression OP1 = expressionStack.pop();
            IRNodes.add(new IRNode(OPCode, OP1, null, storeResult));
        }
    }

    @Override
    public void exitCond(MicroParser.CondContext ctx) {
        //Compare the results of the expressions
        Expression op1;
        Expression op2;
        if (expressionStack.size() > 1) {
            op1 = expressionStack.pop();
            op2 = expressionStack.pop();
            Expression compareResult = new Expression("label" + labelCount, "STR");
            StringBuilder OPCode = new StringBuilder();

            switch (ctx.COMPOP().toString()) {
                case ">":
                    OPCode.append("LE");
                    break;
                case ">=":
                    OPCode.append("LT");
                    break;
                case "<":
                    OPCode.append("GE");
                    break;
                case "<=":
                    OPCode.append("GT");
                    break;
                case "!=":
                    OPCode.append("EQ");
                    break;
                case "=":
                    OPCode.append("NE");
                    break;
            }
            IRNodes.add(new IRNode(OPCode.toString(), op2, op1, compareResult));
            labelStack.push(labelCount);
            labelCount++;
            //variableCount++;
        }
    }

    @Override
    public void exitWriteStmt(MicroParser.WriteStmtContext ctx) {
        //Create an entry in the current scope
        String names = ctx.idList().getText();

        for (String s : names.split(",")) {
            Expression writeOP = new Expression(s, microSymbolTable.getSymbol(s).getType());
            IRNodes.add(new IRNode("WRITE", writeOP, null, null));
        }

    }

    @Override
    public void exitADDOP(MicroParser.ADDOPContext ctx) {
        Expression newExpr = new Expression(ctx.ADDOP().toString(), "OPERATOR");
        expressionStack.push(newExpr);
    }

    @Override
    public void exitMULOP(MicroParser.MULOPContext ctx) {
        Expression newExpr = new Expression(ctx.MULOP().toString(), "OPERATOR");
        expressionStack.push(newExpr);
    }

    @Override
    public void exitPrimaryID(MicroParser.PrimaryIDContext ctx) {
        expressionStack.add(new Expression(ctx.getText(), microSymbolTable.getSymbol(ctx.ID().toString()).getType()));
    }

    @Override
    public void exitPrimaryINT(MicroParser.PrimaryINTContext ctx) {
        expressionStack.add(new Expression(ctx.getText(), "INT"));
    }

    @Override
    public void exitPrimaryFLOAT(MicroParser.PrimaryFLOATContext ctx) {
        expressionStack.add(new Expression(ctx.getText(), "FLOAT"));
    }

    @Override
    public void exitParanths(MicroParser.ParanthsContext ctx) {
        //Restore operations stack
        buildExpression();
        Stack<Expression> newStack = stackStack.pop();
        newStack.addAll(expressionStack);
        expressionStack = newStack;
    }

    @Override
    public void enterParanths(MicroParser.ParanthsContext ctx) {
        //Save the current stack and start over
        stackStack.push((Stack<Expression>) expressionStack.clone());
        expressionStack.clear();
    }

    @Override
    public void exitFactor(MicroParser.FactorContext ctx) {
        buildExpression();
    }

    @Override
    public void exitStringDecl(MicroParser.StringDeclContext ctx) {
        Expression op1 = new Expression(ctx.ID().getText(), "STR");
        Expression result = new Expression(ctx.STRINGLITERAL().toString(), "STR");

        IRNodes.add(new IRNode("STR", op1, null, result));
    }

    @Override
    public String toString() {
        return microSymbolTable.toString();
    }

    private void buildExpression() {
        if (expressionStack.size() > 2) {
            //Need to determine type
            IRNode exprNode;
            Expression op2 = expressionStack.pop();
            Expression operator = expressionStack.pop();
            Expression op1 = expressionStack.pop();

            Expression result = new Expression("$T" + variableCount, op1.getType());
            expressionStack.push(result);
            variableCount++;

            switch (operator.getName()) {
                case "+":
                    exprNode = new IRNode("ADD", op1, op2, result);
                    break;
                case "-":
                    exprNode = new IRNode("SUB", op1, op2, result);
                    break;
                case "*":
                    exprNode = new IRNode("MUL", op1, op2, result);
                    break;
                default:
                    exprNode = new IRNode("DIV", op1, op2, result);
                    break;
            }
            IRNodes.add(exprNode);
        }
    }
}
