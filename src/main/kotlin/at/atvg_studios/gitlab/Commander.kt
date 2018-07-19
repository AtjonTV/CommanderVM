package at.atvg_studios.gitlab

import java.io.File

fun main(args: Array<String>) {
    var p = Parser()
    if(args.isNotEmpty())
    {
        if(args[0] != "")
        {
            if(File(args[0]).exists())
            {
                var file = File(args[0]).readLines()
                for (line in file)
                {
                    p.Parse(line)
                }
            }else if (args[0] == "-instructions")
            {
                println(InstructionSet.HLT.name+" | 0x"+Integer.toHexString(InstructionSet.HLT.hex))
                println(InstructionSet.IGN.name+" | 0x"+Integer.toHexString(InstructionSet.IGN.hex))
            }
        }
    }
    else
    {
        println("Usage: java -jar commander.jar <file>")
    }
}