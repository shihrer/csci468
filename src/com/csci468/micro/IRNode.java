package com.csci468.micro;

class IRNode {

    //Data fields
    private String _opcode;
    private Expression _operand1;
    private Expression _operand2;
    private Expression _result;

    IRNode(String OPCode, Expression OP1, Expression OP2, Expression Result) {
        if(OP1 != null && !OPCode.equals("STR")){
            switch (OP1.getType()) {
                case "INT":
                    this._opcode = OPCode + "I";
                    break;
                case "FLOAT":
                    this._opcode = OPCode + "R";
                    break;
                default:
                    this._opcode = OPCode + "S";
                    break;
            }
        }else {
            this._opcode = OPCode;
        }

        this._operand1 = OP1;
        this._operand2 = OP2;
        this._result = Result;
    }

    String getIRCode() {
        StringBuilder irCode = new StringBuilder();
        irCode.append("; ");
        irCode.append(_opcode).append(" ");

        if(_operand1 != null)
            irCode.append(_operand1).append(" ");
        if(_operand2 != null)
            irCode.append(_operand2).append(" ");
        if(_result != null)
            irCode.append(_result).append(" ");

        return irCode.toString();
    }

    String getOpcode() {
        return _opcode;
    }

    Expression getOP1() {
        return _operand1;
    }

    Expression getOP2() {
        return _operand2;
    }

    Expression getResult() {
        return _result;
    }

    @Override
    public String toString(){
        return getIRCode();
    }
}
