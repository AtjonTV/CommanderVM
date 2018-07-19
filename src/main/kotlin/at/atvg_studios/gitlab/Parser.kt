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
            if (Utils.equal(set[0], InstructionSet.HLT.name) || Utils.equal(set[0], InstructionSet.HLT.hex, true))
                return Instruction(InstructionSet.HLT)
            if (Utils.equal(set[0], InstructionSet.IGN.name) || Utils.equal(set[0], InstructionSet.IGN.hex, true))
                return Instruction(InstructionSet.IGN)
        } else if (set.size == 2) {
            if (Utils.equal(set[0], InstructionSet.HLT.name) || Utils.equal(set[0], InstructionSet.HLT.hex, true))
                return Instruction(InstructionSet.HLT, Array(1) { set[1] })
        }

        return Instruction(InstructionSet.IGN)
    }
}