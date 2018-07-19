package at.atvg_studios.gitlab

import java.io.File

private var vm: VM = VM(100)

fun main(args: Array<String>) {
    var p = Parser()
    if (args.isNotEmpty()) {
        if (args[0] != "") {
            var file = File(args[0])
            if (file.exists()) {
                var f = file.readLines()
                for (line in f) {
                    vm.push(p.Parse(line))
                }
            } else if (args[0] == "-instructions") {
                println(InstructionSet.HLT.name + " | 0x" + Integer.toHexString(InstructionSet.HLT.hex))
                println(InstructionSet.IGN.name + " | 0x" + Integer.toHexString(InstructionSet.IGN.hex))
            }
        }
    } else {
        println("Usage: java -jar commander.jar <file>")
    }
}