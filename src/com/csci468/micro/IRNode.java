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

    private String _branch;

    IRNode(String OPCode, String OP1, String OP2, String Result){
        this._opcode = OPCode;
        this._operand1 = OP1;
        this._operand2 = OP2;
        this._result = Result;
    }

    String getIRCode(){
        return String.format("; %s %s %s %s",_opcode, _operand1, _operand2, _result);
    }

    public String getBranch(){
        return _branch;
    }

    String get_opcode() { return _opcode; }
    String get_first() { return _operand1; }
    String get_second() { return _operand2; }
    String get_third() { return _result; }
}
