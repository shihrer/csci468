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
        IRNodes.add(new IRNode("LABEL", ctx.ID().toString(), "", ""));
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
            IRNodes.add(new IRNode("VAR", id, "", ""));
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
        IRNodes.add(new IRNode("LABEL", "label" + curLabel, "", ""));
        destroyScope();
    }

    @Override
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx) {
        IRNodes.add(new IRNode("LABEL", "label" + labelCount, "", ""));
        labelStack.push(labelCount);
        labelCount++;
        createBlockScope();
    }

    @Override
    public void exitWhileStmt(MicroParser.WhileStmtContext ctx) {
        int curLabel = labelStack.pop();
        int jumpLabel = labelStack.pop();
        IRNodes.add(new IRNode("JUMP", "label" + jumpLabel, "", ""));
        IRNodes.add(new IRNode("LABEL", "label" + curLabel, "", ""));
    }

    @Override
    public void enterElse(MicroParser.ElseContext ctx) {
        createBlockScope();

        int curLabel = labelStack.pop();
        IRNodes.add(new IRNode("JUMP", "label" + labelCount, "", ""));
        IRNodes.add(new IRNode("LABEL", "label" + curLabel, "", ""));
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
            Symbol readSymbol = microSymbolTable.getSymbol(id);
            if (readSymbol.getType().equals("INT"))
                IRNodes.add(new IRNode("READI", readSymbol.getName(), "", ""));
            else if (readSymbol.getType().equals("FLOAT"))
                IRNodes.add(new IRNode("READF", readSymbol.getName(), "", ""));
        }

    }

    private void createBlockScope() {
        //Create new scope
        String name = "BLOCK " + scopeCount;
        scopeCount++;
        microSymbolTable.createScope(scopeCount);
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

        String OPCode;
        String ID = ctx.ID().toString();
        String type = microSymbolTable.getSymbol(ID).getType();

        switch (type) {
            case "INT":
                OPCode = "STOREI";
                break;
            case "FLOAT":
                OPCode = "STORER";
                break;
            default:
                OPCode = "STORES";
                break;
        }

        String tempReg = "$T" + variableCount;
        variableCount++;

        // Avoid popping if the stack is empty
        if (expressionStack.size() > 0) {
            Expression OP1 = expressionStack.pop();
            IRNodes.add(new IRNode(OPCode, OP1.getName(), ID, ""));
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
            String temp = "$T" + variableCount;
            StringBuilder OPCode = new StringBuilder();
            if (op1.getType().equals("FLOAT") || op2.getType().equals("FLOAT")) {
                switch (ctx.COMPOP().toString()) {
                    case ">":
                        OPCode.append("LER");
                        break;
                    case ">=":
                        OPCode.append("LTR");
                        break;
                    case "<":
                        OPCode.append("GER");
                        break;
                    case "<=":
                        OPCode.append("GTR");
                        break;
                    case "!=":
                        OPCode.append("EQR");
                        break;
                    case "=":
                        OPCode.append("NER");
                        break;
                }

            } else {
                switch (ctx.COMPOP().toString()) {
                    case ">":
                        OPCode.append("LEI");
                        break;
                    case ">=":
                        OPCode.append("LTI");
                        break;
                    case "<":
                        OPCode.append("GEI");
                        break;
                    case "<=":
                        OPCode.append("GTI");
                        break;
                    case "!=":
                        OPCode.append("EQI");
                        break;
                    case "=":
                        OPCode.append("NEI");
                        break;
                }
            }
            IRNodes.add(new IRNode("STOREI", op1.getName(), temp, "test"));
            IRNodes.add(new IRNode(OPCode.toString(), op2.getName(), temp, "label" + labelCount));
            labelStack.push(labelCount);
            labelCount++;
            variableCount++;
        }
    }

    @Override
    public void exitWriteStmt(MicroParser.WriteStmtContext ctx) {
        //Create an entry in the current scope
        String names = ctx.idList().getText();

        for (String s : names.split(",")) {
            Symbol writeSymbol = microSymbolTable.getSymbol(s);
            switch (writeSymbol.getType()) {
                case "INT":
                    IRNodes.add(new IRNode("WRITEI", writeSymbol.getName(), "", ""));
                    break;
                case "FLOAT":
                    IRNodes.add(new IRNode("WRITER", writeSymbol.getName(), "", ""));
                    break;
                case "STRING":
                    IRNodes.add(new IRNode("WRITES", writeSymbol.getName(), "", ""));
                    break;
            }
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
        IRNodes.add(new IRNode("STR", ctx.ID().toString(), ctx.STRINGLITERAL().toString(), ""));
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

            if (op1.getType().equals("INT") && op2.getType().equals("INT")) {
                Expression result = new Expression("$T" + variableCount, "INT");
                expressionStack.push(result);
                variableCount++;
                switch (operator.getName()) {
                    case "+":
                        exprNode = new IRNode("ADDI", op1.getName(), op2.getName(), result.getName());
                        break;
                    case "-":
                        exprNode = new IRNode("SUBI", op1.getName(), op2.getName(), result.getName());
                        break;
                    case "*":
                        exprNode = new IRNode("MULI", op1.getName(), op2.getName(), result.getName());
                        break;
                    default:
                        exprNode = new IRNode("DIVI", op1.getName(), op2.getName(), result.getName());
                        break;
                }
            } else {
                Expression result = new Expression("$T" + variableCount, "FLOAT");
                expressionStack.push(result);
                variableCount++;
                switch (operator.getName()) {
                    case "+":
                        exprNode = new IRNode("ADDR", op1.getName(), op2.getName(), result.getName());
                        break;
                    case "-":
                        exprNode = new IRNode("SUBR", op1.getName(), op2.getName(), result.getName());
                        break;
                    case "*":
                        exprNode = new IRNode("MULR", op1.getName(), op2.getName(), result.getName());
                        break;
                    default:
                        exprNode = new IRNode("DIVR", op1.getName(), op2.getName(), result.getName());
                        break;
                }

            }
            IRNodes.add(exprNode);
        }
    }
}
