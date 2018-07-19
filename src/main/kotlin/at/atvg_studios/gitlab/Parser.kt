package at.atvg_studios.gitlab

class Parser
{
    fun Parse(line: String): Instruction
    {
        if(line.startsWith("#") || line.startsWith("//"))
        {
            return Instruction(InstructionSet.IGN)
        }

        var set = line.split(" ")

        if(set.size == 1)
        {
            if(set[0] == InstructionSet.HLT.name ||
                    set[0].equals("0x"+Integer.toHexString(InstructionSet.HLT.hex), true))
                return Instruction(InstructionSet.HLT)
            if(set[0] == InstructionSet.IGN.name ||
                    set[0].equals("0x"+Integer.toHexString(InstructionSet.IGN.hex), true))
                return Instruction(InstructionSet.IGN)
        }

        return Instruction(InstructionSet.IGN)
    }
}