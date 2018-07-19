package at.atvg_studios.gitlab

enum class InstructionSet(var hex: Int) {
    HLT(0x00),
    MTD(0x01),
    FEX(0x02),
    DEX(0x03),
    FRM(0x04),
    DRM(0x05),
    IGN(0xFF)
}