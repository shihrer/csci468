package com.csci468.micro;

/**
 * Michael Shihrer
 * Matthew Johnerson
 * 26 March 2016
 */

class Listener extends MicroBaseListener {
    private int count = 1;

    private SymbolTable microSymbolTable;

    Listener(){
        microSymbolTable = new SymbolTable();
    }

    @Override
    public void enterFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Create new scope
        microSymbolTable.pushScope(ctx.id().getText());
    }

    @Override
    public void exitFuncDecl(MicroParser.FuncDeclContext ctx) {
        //Pop scope
        microSymbolTable.popScope();
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
    public void enterWhileStmt(MicroParser.WhileStmtContext ctx){
        createBlockScope();
    }

    @Override
    public void enterElsePart(MicroParser.ElsePartContext ctx){
        if(!ctx.getText().equals("")){
            createBlockScope();
        }
    }

    private void createBlockScope() {
        //Create new scope
        String name = "BLOCK " + count;
        count++;
        microSymbolTable.pushScope(name);
    }

    @Override
    public void enterStringDecl(MicroParser.StringDeclContext ctx){
        //Create an entry in the current scope
        String name = ctx.id().getText();
        String value = ctx.str().getText();
        String type = ctx.STRING().getText();
        microSymbolTable.createSymbol(name, type,value);
    }

    @Override
    public void enterVarDecl(MicroParser.VarDeclContext ctx){
        String varType = ctx.varType().getText();
        //Create an entry in the current scope
        String input = ctx.getText();
        String names;

        if(varType.equals("FLOAT")){
            names = input.substring(5,input.length());
        }
        else {
            names = input.substring(3,input.length());
        }

        String[] tokens = names.split(",");
        for (String s : tokens){
            s = s.replace(";","");
            microSymbolTable.createSymbol(s,varType);
        }
    }

    @Override
    public String toString()
    {
        return microSymbolTable.toString();
    }
}
