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


/**
    The VM executes Instructions and Handles the application Storage
*/
class VM {

    // DEBUG enables or disables debuging prints
    private var DEBUG:Boolean=true
    private var applicationStackBegin:Int = 0

    /**
     * ApplicationMemory defines how many Instructions can be stored and execute
     */
    private var applicationMemoryMax: Int = 0
    private var applicationMemory: MutableList<Instruction> = ArrayList<Instruction>()

    /**
     * DataMemory defines how many Variabls can be stored
     */
    private var dataMemoryMax: Int = 0
    private var dataMemory: MutableMap<String, Any> = HashMap<String, Any>()

    constructor(application_memory_max: Int, register_a_max: Int) {
        applicationMemoryMax = application_memory_max
        dataMemoryMax = register_a_max
    }

    /**
     * Push adds a Instruction to the applciationMemory
     */
    fun push(inst: Instruction) {
        if (applicationMemory.size >= applicationMemoryMax)
            throw Vm_OutOfRam("Application Memory '$applicationMemoryMax'")
        applicationMemory.add(applicationMemory.size, inst)
    }

    /**
     * Pop removes the top of the applicationMemory
     */
    fun pop() {
        applicationMemory.removeAt(applicationMemory.size - 1)
    }

    /**
     * Execute runns through all the Instructions in applicationMemory, and executes them
     */
    fun execute() {
        var inst: Instruction?
        /*
            For each Instruction in the applicationMemory
        */
        for (i in applicationStackBegin until applicationMemory.size) {
            inst = applicationMemory[i]
            /*
                When the Instruction is HLT, STOP!
            */
            if (inst.getCmd() == InstructionSet.HLT) {
                // Check if a error code in the args is set
                if (inst.getArgs().size == 1 && inst.getArgs()[0].isNotEmpty()) {
                    throw Vm_Halted("Instruction; Halt Code " + inst.getArgs()[0])
                } else {
                    throw Vm_Halted("Instruction")
                }
            }

            /*
                When the Instruction is IGN, Do NOTHING!
            */
            if (inst.getCmd() == InstructionSet.IGN)
            {
                // DO NOTHING
            }

            /*
                When the Instruction is POP, either take the top or a index from dataMemory
            */
            if(inst.getCmd() == InstructionSet.POP)
            {
                // Check if an argument is given
                if(inst.getArgs().size == 1 && inst.getArgs()[0].isNotEmpty())
                {
                    if(DEBUG)
                        println("Poping ${inst.getArgs()[0]}")
                    try {
                        // Try to POP the index
                        dataMemory.remove(inst.getArgs()[0])
                        if(DEBUG)
                            println(dataMemory)
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
                else if (inst.getArgs().size == 1)
                {
                    if(DEBUG)
                        println("Poping a.${dataMemory.size-1}")
                    try {
                        dataMemory.remove("a.${dataMemory.size}")
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
                else
                    throw Vm_Halted("POP Underload")
            }

            /*
                When the Instruction is PUT, take a register and value
            */
            if(inst.getCmd() == InstructionSet.PUT)
            {
                // Check if a register and value is set
                if(inst.getArgs().size == 2 && inst.getArgs()[0].isNotEmpty() && inst.getArgs()[1].isNotEmpty())
                {
                    if(DEBUG)
                        println("Puting ${inst.getArgs()[1]} in ${inst.getArgs()[0]}")
                    // Check if dataMemory is full
                    if(dataMemory.size >= dataMemoryMax)
                    {
                        dataMemory.clear()
                        applicationStackBegin=0
                        throw Vm_OutOfRam("Data Memory '$dataMemoryMax'")
                    }
                    try {
                        // When a index is provided
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

            /*
                When the Instruction is JMP, take a index
             */
            if(inst.getCmd() == InstructionSet.JMP)
            {
                // Check if index is set
                if(inst.getArgs().size == 1 && inst.getArgs()[0].isNotEmpty())
                {
                    if(DEBUG)
                        println("Jumping to '${inst.getArgs()[0]}'")
                    try{
                        applicationStackBegin = inst.getArgs()[0].toInt()
                        execute()
                        break
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
            }

            println(dataMemory)
        }
    }

    /*
     * The following functions allow loading and clearing of the VM internal storages
     */
    /**
        Vm_LoadApplicationMemory allows to load a full stack of instructions into applicationMemory
    */
    fun Vm_LoadApplicationMemory(application_memory_max: Int, application_memory: MutableList<Instruction>) {
        applicationMemoryMax = application_memory_max
        applicationMemory = application_memory
    }

    /**
        Vm_LoadRegisterA allows to load a full stack of values into dataMemory's A stack
    */
    fun Vm_LoadRegisterA(data_memory_max:Int, data_memory:MutableMap<String, Any>)
    {
        dataMemoryMax=data_memory_max
        dataMemory=data_memory
    }
}