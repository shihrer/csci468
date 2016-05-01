package com.csci468.micro;

import java.util.LinkedList;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {
    private int scopeCount = 1;

    private SymbolTable microSymbolTable;

    private LinkedList<IRNode> IRNodes;

    Listener(){
        microSymbolTable = new SymbolTable();
        IRNodes = new LinkedList<>();
    }

    @Override
    public void enterFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Create new scope
        microSymbolTable.createScope(ctx.id().getText());
    }

    @Override
    public void exitFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Pop scope
        destroyScope();
    }
    @Override public void enterParamDecl(MicroParser.ParamDeclContext ctx){
        //Add parameter to scope
        microSymbolTable.createSymbol(ctx.id().getText(), ctx.varType().getText());
    }
    @Override
    public void enterIfStmt(MicroParser.IfStmtContext ctx){
        createBlockScope();
    }

    @Override
    public void exitIfStmt(MicroParser.IfStmtContext ctx){
        destroyScope();
    }

    @Override
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx){
        createBlockScope();
    }

    @Override
    public void exitWhileStmt(MicroParser.WhileStmtContext ctx){
        destroyScope();
    }

    @Override
    public void enterElsePart(MicroParser.ElsePartContext ctx){
        if(!ctx.getText().isEmpty())
            createBlockScope();
    }

    @Override
    public void exitElsePart(MicroParser.ElsePartContext ctx){
        if(!ctx.getText().isEmpty())
            destroyScope();
    }

//    @Override
//    public void enterStmtList(MicroParser.StmtListContext ctx){
//        createBlockScope();
//    }
//
//    @Override
//    public void exitStmtList(MicroParser.StmtListContext ctx){
//        destroyScope();
//    }

    @Override
    public void enterReadStmt(MicroParser.ReadStmtContext ctx){
        Symbol readSymbol = microSymbolTable.getSymbol(ctx.idList().id().getText());

        IRNodes.add(new IRNode("READ",readSymbol.getName(), null, null));
    }

    private void createBlockScope() {
        //Create new scope
        String name = "BLOCK " + scopeCount;
        scopeCount++;
        microSymbolTable.createScope(name);
    }

    private void destroyScope(){
        microSymbolTable.destroyScope();
    }

    @Override
    public void enterStringDecl(MicroParser.StringDeclContext ctx){
        //Create an entry in the current scope
        String name = ctx.id().getText();
        String value = ctx.str().getText();
        String type = ctx.STRING().getText();
        microSymbolTable.createSymbol(name, type, value);
    }

    @Override
    public void enterVarDecl(MicroParser.VarDeclContext ctx){
        String varType = ctx.varType().getText();
        //Create an entry in the current scope
        String input = ctx.getText();
        String names;

        //String test = ctx.idList().idTail().

        //TODO: Refactor
        if(varType.equals("FLOAT")){
            names = input.substring(5, input.length());
        }
        else {
            names = input.substring(3, input.length());
        }

        String[] tokens = names.split(",");
        for (String s : tokens){
            s = s.replace(";","");
            microSymbolTable.createSymbol(s, varType);
        }
    }

    @Override
    public void enterIdList(MicroParser.IdListContext ctx){
        //String id = ctx.id().getText();
        //microSymbolTable.createSymbol(c)
    }

    @Override
    public String toString()
    {
        return microSymbolTable.toString();
    }
}
