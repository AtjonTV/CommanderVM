package at.atvg_studios.gitlab

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