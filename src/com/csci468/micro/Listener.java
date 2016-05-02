package com.csci468.micro;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {
    private int scopeCount = 1;
    private int labelCount = 1;
    private int tempCount = 1;

    private SymbolTable microSymbolTable;

    private LinkedList<IRNode> IRNodes;
    private Stack<Integer> labelStack;
    private Stack<String> operatorStack = new Stack<>();
    private Stack<String> exprStack = new Stack<>();

    private ParseTreeProperty<String> irProperties = new ParseTreeProperty<>();

    String getProperty(ParseTree ctx) {
        return irProperties.get(ctx);
    }

    void setProperty(ParseTree ctx, String s) {
        irProperties.put(ctx, s);
    }

    Listener(LinkedList<IRNode> IRNodes) {
        microSymbolTable = new SymbolTable();
        this.IRNodes = IRNodes;
        this.labelStack = new Stack<>();
    }

    @Override
    public void enterFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Create new scope
        microSymbolTable.createScope(ctx.ID().toString());
        IRNodes.add(new IRNode("LABEL", ctx.ID().toString(), null, null));
    }

    @Override
    public void exitFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Pop scope
        destroyScope();
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
        IRNodes.add(new IRNode("LABEL", "label" + curLabel, null, null));
        destroyScope();
    }

    @Override
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx) {
        IRNodes.add(new IRNode("LABEL", "label" + labelCount, null, null));
        labelStack.push(labelCount);
        labelCount++;
        createBlockScope();
    }

    @Override
    public void exitWhileStmt(MicroParser.WhileStmtContext ctx) {
        int curLabel = labelStack.pop();
        int jumpLabel = labelStack.pop();
        IRNodes.add(new IRNode("JUMP", "label" + jumpLabel, null, null));
        IRNodes.add(new IRNode("LABEL", "label" + curLabel, null, null));
    }

    @Override
    public void enterElsePart(MicroParser.ElsePartContext ctx) {
        if (!ctx.getText().isEmpty()) {
            createBlockScope();

            int curLabel = labelStack.pop();
            IRNodes.add(new IRNode("JUMP", "label" + labelCount, null, null));
            IRNodes.add(new IRNode("LABEL", "label" + curLabel, null, null));
            labelStack.push(labelCount);
            labelCount++;
        }
    }

    @Override
    public void exitElsePart(MicroParser.ElsePartContext ctx) {
        if (!ctx.getText().isEmpty())
            destroyScope();
    }

    @Override
    public void exitReadStmt(MicroParser.ReadStmtContext ctx) {
        Symbol readSymbol = microSymbolTable.getSymbol(ctx.idList().ID().toString());
        if (readSymbol.getType().equals("INT"))
            IRNodes.add(new IRNode("READI", readSymbol.getName(), null, null));
        else if (readSymbol.getType().equals("FLOAT"))
            IRNodes.add(new IRNode("READF", readSymbol.getName(), null, null));

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
        //Create an entry in the current scope
        String input = ctx.getText();
        String names = ctx.idList().getText();

        String[] tokens = names.split(",");
        for (String s : tokens) {
            s = s.replace(";", "");
            microSymbolTable.createSymbol(s, varType);
        }
    }

    @Override
    public void exitAssignExpr(MicroParser.AssignExprContext ctx) {
        // Store results of whatever expression is evaluated to the context ID
        //id of what we're assigning to
        String OPCode;
        String ID = ctx.ID().toString();
        String type = microSymbolTable.getSymbol(ID).getType();

        switch (type) {
            case "INT":
                OPCode = "STOREI";
                break;
            case "FLOAT":
                OPCode = "STOREF";
                break;
            default:
                OPCode = "STORES";
                break;
        }

        String tempReg = "$T" + tempCount;
        tempCount++;

        // this is wrong...  I should build the expression elsewhere
        if (!exprStack.empty()) {
            String OP1 = exprStack.pop();
            IRNodes.add(new IRNode(OPCode, OP1, tempReg, null));
        }
        IRNodes.add(new IRNode(OPCode, tempReg, ID, null));
    }

    @Override
    public void exitCond(MicroParser.CondContext ctx) {
        //Compare the results of the expressions
        StringBuilder OPCode = new StringBuilder();

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
        String op1;
        String op2;
        if(exprStack.size() > 1){
            op1 = exprStack.pop();
            op2 = exprStack.pop();
            String temp = "$T" + tempCount;
            IRNodes.add(new IRNode("STOREI", op1, temp, ""));
            IRNodes.add(new IRNode(OPCode.toString(), op2, temp, "label" + labelCount));
            labelStack.push(labelCount);
            labelCount++;
            tempCount++;
        }
//        while (!exprStack.empty()) {
//            op1 = exprStack.pop();
//            op2 = exprStack.pop();
//            String temp = "$T" + tempCount;
//            IRNodes.add(new IRNode("STOREI", op1, temp, ""));
//            IRNodes.add(new IRNode(OPCode.toString(), op2, temp, "label" + labelCount));
//            labelStack.push(labelCount);
//            labelCount++;
//            tempCount++;
//        }
    }

    @Override
    public void exitWriteStmt(MicroParser.WriteStmtContext ctx) {
        //Create an entry in the current scope
        String input = ctx.getText();
        String names = ctx.idList().getText();

        String[] tokens = names.split(",");
        for (String s : tokens) {
            Symbol writeSymbol = microSymbolTable.getSymbol(s);
            switch (writeSymbol.getType()) {
                case "INT":
                    IRNodes.add(new IRNode("WRITEI", writeSymbol.getName(), null, null));
                    break;
                case "FLOAT":
                    IRNodes.add(new IRNode("WRITER", writeSymbol.getName(), null, null));
                    break;
                case "STRING":
                    IRNodes.add(new IRNode("WRITES", writeSymbol.getName(), null, null));
                    break;
            }
        }

    }

    @Override
    public void exitExpr(MicroParser.ExprContext ctx){
        // I need to determine the results of the expr and save that to the stack.
        if(exprStack.size() > 2) {
            IRNode exprNode;
            String op1 = exprStack.pop();
            String operator = exprStack.pop();
            String op2 = exprStack.pop();
            String result = "$T" + tempCount;
            exprStack.push(result);
            tempCount++;

            switch (operator) {
                case "+":
                    exprNode = new IRNode("ADDI", op1, op2, result);
                    break;
                case "-":
                    exprNode = new IRNode("SUBI", op1, op2, result);
                    break;
                case "*":
                    exprNode = new IRNode("MULI", op1, op2, result);
                    break;
                default:
                    exprNode = new IRNode("DIVI", op1, op2, result);
                    break;
            }
            IRNodes.add(exprNode);
            //push results back to expr stack so that they can be used later.
        }
    }

    @Override
    public void exitExprPrefix(MicroParser.ExprPrefixContext ctx) {
        if (ctx.ADDOP() != null){
            exprStack.push(ctx.ADDOP().toString());
        }
    }

    @Override
    public void exitFactorPrefix(MicroParser.FactorPrefixContext ctx){
        if(ctx.MULOP() != null){
            exprStack.push(ctx.MULOP().toString());
        }
    }

    @Override
    public void exitPrimary(MicroParser.PrimaryContext ctx) {
        exprStack.push(ctx.getText());
    }

    @Override
    public String toString() {
        return microSymbolTable.toString();
    }
}
