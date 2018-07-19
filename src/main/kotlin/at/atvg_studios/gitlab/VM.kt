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

class VM {
    private var ram_size: Int = 0
    private var ram_max: Int = 0
    private var ram: MutableList<Instruction> = ArrayList<Instruction>()

    constructor(ram: Int) {
        ram_max = ram
    }

    constructor(ram_s: Int, ram: MutableList<Instruction>) {
        ram_max = ram_s
        ram_size = ram.size
        this.ram = ram
    }

    fun push(inst: Instruction) {
        if (ram_size < ram_max) {
            ram.add(ram.size, inst)
            ram_size = ram.size + 1 // Add one to be safe
            execute()
        } else
            throw Vm_OutOfRam("Bound on Limit '$ram_max'")
    }

    fun pop() {
        ram.removeAt(ram_size - 2) // Subtract two due to extra and indexing
    }

    fun execute() {
        var inst: Instruction?
        for (i in 0 until ram_size - 1) {
            inst = ram[i]
            if (inst.getCmd() == InstructionSet.HLT) {
                if (inst.getArgs().size == 1 && inst.getArgs()[0].isNotEmpty()) {
                    throw Vm_Halted("Instruction; Halt Code " + inst.getArgs()[0])
                } else {
                    throw Vm_Halted("Instruction")
                }
            }
            if (inst.getCmd() == InstructionSet.IGN)
                ram.removeAt(i)
        }
    }
}