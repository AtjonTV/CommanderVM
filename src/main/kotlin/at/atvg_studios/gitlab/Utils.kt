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

class Utils {
    companion object {
        fun equal(a: Any, b: Any): Boolean {
            if (a is String && b is String) {
                if (a.equals(b, true))
                    return true
            }

            if (a == b)
                return true

            return false
        }

        fun equal(a: Any, b: Int, isHex: Boolean): Boolean {
            return if (isHex)
                equal(a, "0x" + Integer.toHexString(b))
            else
                equal(a, b)
        }

        fun compareWithInstruction(a:Any, i:InstructionSet): Boolean
        {
            if(a.toString().equals(i.name,ignoreCase = true)
                    || (a.toString().equals("0x0" + Integer.toHexString(i.hex),ignoreCase = true)
                            || a.toString().equals("0x" + Integer.toHexString(i.hex),ignoreCase = true)))
                return true
            return false
        }

        fun exit(type: Int, msg: String)
        {
            when(type)
            {
                0 -> {
                    print("")
                }
                1 -> {
                    println("CommanderVM was Halted: Instruction;")
                }
                2 -> {
                    println("CommanderVM was Halted: Instruction; Halt Code $msg")
                }
                3 -> {
                    println("CommanderVM Out of Ram: $msg")
                    println("CommanderVM was Halted!")
                }
                4 -> {
                    println("CommanderVM File not Found: $msg")
                    println("CommanderVM was Halted!")
                }
                5 -> {
                    println("CommanderVM JMP Loop: $msg")
                    println("CommanderVM was Halted!")
                }
                else -> {
                    println("CommanderVM was Halted: Unknown;")
                }
            }
            System.exit(1)
        }
    }
}