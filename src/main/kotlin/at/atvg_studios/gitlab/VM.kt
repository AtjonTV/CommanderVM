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

    private var DEBUG:Boolean=false

    /**
     * ApplicationMemory defines how many Instructions can store and execute
     */
    private var applicationMemoryMax: Int = 0
    private var applicationMemory: MutableList<Instruction> = ArrayList<Instruction>()

    private var dataMemoryMax: Int = 0
    private var dataMemory: MutableMap<String, Any> = HashMap<String, Any>()

    constructor(application_memory_max: Int, register_a_max: Int) {
        applicationMemoryMax = application_memory_max
        dataMemoryMax = register_a_max
    }

    fun push(inst: Instruction) {
        if (applicationMemory.size < applicationMemoryMax) {
            applicationMemory.add(applicationMemory.size, inst)
        } else
            throw Vm_OutOfRam("Bound on Limit '$applicationMemoryMax'")
    }

    fun pop() {
        applicationMemory.removeAt(applicationMemory.size - 1)
    }

    fun execute() {
        var inst: Instruction?
        for (i in 0 until applicationMemory.size) {
            inst = applicationMemory[i]
            if (inst.getCmd() == InstructionSet.HLT) {
                if (inst.getArgs().size == 1 && inst.getArgs()[0].isNotEmpty()) {
                    throw Vm_Halted("Instruction; Halt Code " + inst.getArgs()[0])
                } else {
                    throw Vm_Halted("Instruction")
                }
            }
            if (inst.getCmd() == InstructionSet.IGN)
            {

            }
            if(inst.getCmd() == InstructionSet.POP)
            {
                if(inst.getArgs().size == 1 && inst.getArgs()[0].isNotEmpty())
                {
                    if(DEBUG)
                        println("Poping ${inst.getArgs()[0]}")
                    try {
                        dataMemory.remove(inst.getArgs()[0])
                        if(DEBUG)
                            println(dataMemory)
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
                else
                    throw Vm_Halted("POP Underload")
            }
            if(inst.getCmd() == InstructionSet.PUT)
            {
                if(inst.getArgs().size == 2 && inst.getArgs()[0].isNotEmpty() && inst.getArgs()[1].isNotEmpty())
                {
                    if(DEBUG)
                        println("Puting ${inst.getArgs()[1]} in ${inst.getArgs()[0]}")
                    try {
                        if(!inst.getArgs()[0].contains("."))
                        {
                            dataMemory["a."+dataMemory.size.toString()] = inst.getArgs()[1]
                        }
                        else
                        {
                            dataMemory[inst.getArgs()[0]] = inst.getArgs()[1]
                        }
                        if(DEBUG)
                            println(dataMemory)
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
                else
                    throw Vm_Halted("PUT Underload")
            }
        }
    }

    /*
     * The following functions allow loading and clearing of the VM internal storages
     */
    fun Vm_LoadApplicationMemory(application_memory_max: Int, application_memory: MutableList<Instruction>) {
        applicationMemoryMax = application_memory_max
        applicationMemory = application_memory
    }

    fun Vm_LoadRegisterA(data_memory_max:Int, data_memory:MutableMap<String, Any>)
    {
        dataMemoryMax=data_memory_max
        dataMemory=data_memory
    }
}