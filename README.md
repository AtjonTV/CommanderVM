# CommanderVM
CommanderVM is a Kotlin/JVM written VM that executes "ComCode".

# Disclaimer
This is the first VM we wrote and the first Kotlin/JVM project we do.
Please don't expect this VM to work as intended!

This VM is not make to run huge things or to be high performand!

The VM got a Assembly like language called ComCode 

# ComCode

This is the ComCode, the with `✔` marked commands are already part of the VM.  
The commands marked with `✕` are not implemented yet.

```
✔ HLT(0x00), // HLT: 0x00; Stops the VM
✔ PUT(0x01), // PUT: 0x01,$a,0x02; Puts 2 at the top of register a
✕ COM(0x02), // COM: 0x02,$a.2,$a.3,$a; Compares register a pos 2 with register a pos 3 and stores 0 or 1 at top of register a
✔ POP(0x03), // POP: 0x03,$a; Removes the top of register a
✕ JMP(0x04), // JMP: 0x04,0x00; Jumps top position 0 of applicationMemory
✕ JIF(0x05), // JIF: 0x05,0x00,$a; Jumps to position 0 of applicationMemory if top of register a is 0 or 1
✔ IGN(0xFF)  // IGN: 0xFF; Does nothing
```

# ComCode Compiler / Commander Compiler
The latest version of CommanderVM has a build in Compiler and Decompiler for ComCode.

All instructions of ComCode have a Hex value that represents the instruction.

The Compiler will replace the Text (e.g. `hlt`) with its Hex value (e.g. `0x00`).

The Decompiler does the same thing just in reverse, it replaces the Hex wit the Text.

This:

```
put,$a,1
put,$a,5
pop,$a.0
hlt,0
```

will become this:

```
0x01,$a,1
0x01,$a,5
0x03,$a.0
0x00,0
```

on Compilation, and the reverse on Decompilation

# License
OSPL v1 (Open Source Project License Version 1.3 by ATVG-Studios)

Copyright (c) 2015-2018 Thomas Obernosterer (ATVG-Studios)

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish,
distribute and sublicense the Software
(Selling the Software is punishable for the Person selling. This may result in a lawsuit!),
and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[This License was Copied from its original website: http://ospl.atvg-studios.at]