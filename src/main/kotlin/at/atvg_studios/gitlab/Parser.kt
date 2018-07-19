package at.atvg_studios.gitlab

class Parser
{
    fun Parse(line: String): Instruction
    {
        if(line.startsWith("#") || line.startsWith("//"))
        {
            return Instruction(InstructionSet.IGN)
        }

        var set = line.split(",")
        println(set)

        if(set.size == 1)
        {
            if(Utils.equal(set[0], InstructionSet.HLT.name)|| Utils.equal(set[0], InstructionSet.HLT.hex, true))
                return Instruction(InstructionSet.HLT)
            if(Utils.equal(set[0], InstructionSet.IGN.name)|| Utils.equal(set[0], InstructionSet.IGN.hex, true))
                return Instruction(InstructionSet.IGN)
        }
        else if (set.size == 2)
        {
            if(Utils.equal(set[0], InstructionSet.HLT.name)|| Utils.equal(set[0], InstructionSet.HLT.hex, true))
                return Instruction(InstructionSet.HLT, Array(1){set[1]})
        }

        return Instruction(InstructionSet.IGN)
    }
}