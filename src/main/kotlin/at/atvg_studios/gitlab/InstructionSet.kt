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

enum class InstructionSet(var hex: Int) {
    HLT(0x00), // HLT: 0x00; Stops the VM
    PUT(0x01), // PUT: 0x01,$a,0x02; Puts 2 at the top of register a
    COM(0x02), // COM: 0x02,$a.2,$a.3,$a; Compares register a pos 2 with register a pos 3 and stores 0 or 1 at top of register a
    POP(0x03), // POP: 0x03,$a; Removes the top of register a
    JMP(0x04), // JMP: 0x04,0x00; Jumps top position 0 of applicationMemory
    JIF(0x05), // JIF: 0x05,0x00,$a; Jumps to position 0 of applicationMemory if top of register a is 1
    IGN(0xFF)  // IGN: 0xFF; Does nothing
}