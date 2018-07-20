package at.atvg_studios.gitlab

/**
 * OSPL v1 (Open Source Project License Version 1.3 by ATVG-Studios)
 *
 * Copyright (c) 2015-2018 Thomas Obernosterer (ATVG-Studios)
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute and sublicense the Software
 * (Selling the Software is punishable for the Person selling. This may result in a lawsuit!),
 * and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * [This License was Copied from its original website: http://ospl.atvg-studios.at]
 */

class Parser {
    fun Parse(line: String): Instruction {
        if (line.startsWith("#") || line.startsWith("//")) {
            return Instruction(InstructionSet.IGN)
        }

        var set = line.split(",")

        if (set.size == 1) {
            if (Utils.compareWithInstruction(set[0], InstructionSet.HLT))
                return Instruction(InstructionSet.HLT)
            if (Utils.compareWithInstruction(set[0], InstructionSet.IGN))
                return Instruction(InstructionSet.IGN)
        } else if (set.size == 2) {
            if (Utils.compareWithInstruction(set[0], InstructionSet.HLT))
                return Instruction(InstructionSet.HLT, Array(1) { set[1] })
            if (Utils.compareWithInstruction(set[0], InstructionSet.POP))
            {
                if(set[1].startsWith("\$a."))
                {
                    return Instruction(InstructionSet.POP, Array(1){set[1].replace("\$", "")})
                }
                else if(set[1].startsWith("\$a"))
                {
                    return Instruction(InstructionSet.POP, Array(1){""})
                }
                return Instruction(InstructionSet.HLT, Array(1){"P02"}) // Could not find register
            }
            if(Utils.compareWithInstruction(set[0], InstructionSet.JMP))
            {
                if(set[1].startsWith("0x"))
                {
                    var index = set[1].replace("0x", "").toInt()
                    return Instruction(InstructionSet.JMP, Array(1){index.toString()})
                }
                return Instruction(InstructionSet.HLT, Array(1){"P03"}) // Could not parse index
            }
        } else if (set.size == 3)
        {
            if(Utils.compareWithInstruction(set[0], InstructionSet.PUT))
            {
                if(set[1].startsWith("\$a"))
                {
                    return Instruction(InstructionSet.PUT, arrayOf(set[1].replace("\$",""), set[2]))
                }
                return Instruction(InstructionSet.HLT, Array(1){"P02"}) // Could not find register
            }
        }

        return Instruction(InstructionSet.IGN)
    }
}