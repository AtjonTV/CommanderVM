package at.atvg_studios.gitlab

class Vm_Halted(message: String?) : Exception("CommanderVM was Halted: $message")
class Vm_OutOfRam(message: String?) : Exception("CommanderVM Out of Ram: $message")