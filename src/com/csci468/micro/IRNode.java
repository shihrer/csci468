package com.csci468.micro;

class IRNode {
    //Opcode
    private String _opcode;
    //Operand
    private String _operand1;
    //Operand
    private String _operand2;
    //Result
    private String _result;

    IRNode(String OPCode, String OP1, String OP2, String Result){
        this._opcode = OPCode;
        this._operand1 = OP1;
        this._operand2 = OP2;
        this._result = Result;
    }

    public String getIRCode(){
        return String.format("%s %s %s %s",_opcode, _operand1, _operand2, _result);
    }
}
