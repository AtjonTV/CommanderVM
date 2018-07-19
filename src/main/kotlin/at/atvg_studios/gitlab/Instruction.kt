package at.atvg_studios.gitlab

class Instruction {

    private var cmd: InstructionSet = InstructionSet.IGN
    private var args: Array<String> = Array(0) {""}

    constructor(cmd: InstructionSet)
    {
        this.cmd = cmd
    }

    constructor(cmd: InstructionSet, args:Array<String>)
    {
        this.cmd = cmd; this.args = args
    }

    fun getCmd(): InstructionSet{
        return cmd
    }

    fun getArgs(): Array<String>
    {
        return args
    }
}