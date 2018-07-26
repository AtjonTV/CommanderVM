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

import java.io.File
import java.security.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.jar.JarFile
import java.util.jar.Manifest

private var vm: VM = VM(50,100)
private var version:String = "18.7.21"
private var build: Long = 0
private var date:String= Date().toString()

fun loadVersion()
{
    var files = File(System.getProperty("user.dir")).listFiles()
    for(i in 0 until files.size)
    {
        if(files[i].name == System.getProperty("java.class.path"))
        {
            var jar = JarFile(files[i])
            version = jar.manifest.mainAttributes.getValue("Implementation-Version")
            build = jar.manifest.mainAttributes.getValue("Implementation-Build").toLong()
            var iDate = Instant.ofEpochMilli(jar.manifest.mainAttributes.getValue("Implementation-Date").toLong())
            date = DateTimeFormatter
                    .ofPattern("dd.MM.yyyy")
                    .withZone(ZoneOffset.UTC)
                    .format(iDate)
        }
    }
}

fun main(args: Array<String>) {

    loadVersion()

    var p = Parser()
    if (args.isNotEmpty()) {
        for(i in 0 until args.size)
        {
            if (args[i] != "") {
                if(args[i].startsWith("-"))
                {
                    if (args[i] == "-instructions") {
                        println(InstructionSet.HLT.name + " | 0x0" + Integer.toHexString(InstructionSet.HLT.hex))
                        println(InstructionSet.PUT.name + " | 0x0" + Integer.toHexString(InstructionSet.PUT.hex))
                        println(InstructionSet.COM.name + " | 0x0" + Integer.toHexString(InstructionSet.COM.hex))
                        println(InstructionSet.POP.name + " | 0x0" + Integer.toHexString(InstructionSet.POP.hex))
                        println(InstructionSet.JMP.name + " | 0x0" + Integer.toHexString(InstructionSet.JMP.hex))
                        println(InstructionSet.JIF.name + " | 0x0" + Integer.toHexString(InstructionSet.JIF.hex))
                        println(InstructionSet.IGN.name + " | 0x" + Integer.toHexString(InstructionSet.IGN.hex))
                        return
                    }

                    if(args[i] == "-help")
                    {
                        println("+--------------------------------------------------+")
                        println("|   CommanderVM v$version by Thomas Obernosterer  |")
                        println("+--------------------------------------------------+")
                        println("|  -instructions => Show Instructions              |")
                        println("|  -help => Show this help                         |")
                        println("|  -version => Show current version                |")
                        println("|  -decompile <hexFile> -o <txtFile> => Hex to Txt |")
                        println("|  -compile <txtFile> -o <hexFile> => Txt to Hex   |")
                        println("|  -error <code> => Show information about errors  |")
                        println("+--------------------------------------------------+")
                        return
                    }

                    if(args[i] == "-version")
                    {
                        println("+---------------------------------+")
                        println("|      CommanderVM v$version     |")
                        println("+---------------------------------+")
                        println("|   Version: $version            |")
                        if(build in 1..9)
                            println("|   Build:   $build                    |")
                        else if(build in 10..99)
                            println("|   Build:   $build                   |")
                        else if(build in 100..999)
                            println("|   Build:   $build                  |")
                        else if(build in 1000..9999)
                            println("|   Build:   $build                 |")
                        else if(build in 10000..99999)
                            println("|   Build:   $build                |")
                        else if(build in 100000..999999)
                            println("|   Build:   $build               |")
                        else if(build in 1000000..9999999)
                            println("|   Build:   $build              |")
                        else if(build in 10000000..99999999)
                            println("|   Build:   $build             |")
                        else if(build in 100000000..999999999)
                            println("|   Build:   $build            |")
                        else if(build in 1000000000..9999999999)
                            println("|   Build:   $build           |")
                        else if(build in 10000000000..99999999999)
                            println("|   Build:   $build          |")
                        else if(build in 100000000000..999999999999)
                            println("|   Build:   $build         |")
                        else if(build in 1000000000000..9999999999999)
                            println("|   Build:   $build        |")
                        else if(build in 10000000000000..99999999999999)
                            println("|   Build:   $build       |")
                        else if(build in 100000000000000..999999999999999)
                            println("|   Build:   $build      |")
                        else if(build in 1000000000000000..9999999999999999)
                            println("|   Build:   $build     |")
                        else
                            println("|   Build:   $build    |")
                        println("|   Date:    $date           |")
                        println("+---------------------------------+")
                        println("|   Author: Thomas Obernosterer   |")
                        println("|   Copyright: 2018 ATVG-Studios  |")
                        println("+---------------------------------+")
                        println("|   Java: ${System.getProperties().getProperty("java.version")}               |")
                        println("+---------------------------------+")
                    }

                    if(args[i] == "-decompile")
                    {
                        if(args[i+1] != "" && args[i+2] == "-o" && args[i+3] != "")
                        {
                            var inFile = File(args[i+1])
                            var outFile = File(args[i+3])

                            var out = inFile.readText()

                            out = out.replace("0x00", "hlt")
                            out = out.replace("0x01", "put")
                            out = out.replace("0x02", "com")
                            out = out.replace("0x03", "pop")
                            out = out.replace("0x04", "jmp")
                            out = out.replace("0x05", "jif")
                            out = out.replace("0xFF", "ign")

                            outFile.createNewFile()
                            outFile.writeText(out)
                        }
                        return
                    }

                    if(args[i] == "-compile")
                    {
                        if(args[i+1] != "" && args[i+2] == "-o" && args[i+3] != "")
                        {
                            var inFile = File(args[i+1])
                            var outFile = File(args[i+3])

                            var out = inFile.readText()

                            out = out.replace("hlt", "0x00")
                            out = out.replace("put", "0x01")
                            out = out.replace("com", "0x02")
                            out = out.replace("pop", "0x03")
                            out = out.replace("jmp", "0x04")
                            out = out.replace("jif", "0x05")
                            out = out.replace("ign", "0xFF")

                            outFile.createNewFile()
                            outFile.writeText(out)
                        }
                        return
                    }

                    if(args[i] == "-error")
                    {
                        if(args.size > 1 && args[i+1].isNotEmpty())
                        {
                            when(args[i+1])
                            {
                                "P02" -> {
                                    println("+---------------------------------+")
                                    println("|    CommanderVM's Error Codes    |")
                                    println("+---------------------------------+")
                                    println("| P02 => Could not find register  |")
                                    println("+---------------------------------+")
                                }
                                "P03" -> {
                                    println("+---------------------------------+")
                                    println("|    CommanderVM's Error Codes    |")
                                    println("+---------------------------------+")
                                    println("| P03 => Could not parse index    |")
                                    println("+---------------------------------+")
                                }
                                else -> {
                                    println("+---------------------------------+")
                                    println("|    CommanderVM's Error Codes    |")
                                    println("+---------------------------------+")
                                    println("| '${args[i+1]}' => Error code not found!  |")
                                    println("+---------------------------------+")
                                }
                            }

                            Utils.exit(0,"")
                        }
                        else
                        {
                            println("+---------------------------------+")
                            println("|    CommanderVM's Error Codes    |")
                            println("+---------------------------------+")
                            println("| P02 => Could not find register  |")
                            println("| P03 => Could not parse index    |")
                            println("+---------------------------------+")
                            Utils.exit(0,"")
                        }
                    }
                }
                else
                {
                    var file = File(args[i])
                    if (file.exists()) {
                        var f = file.readLines()
                        for (line in f) {
                            var i = p.Parse(line)
                            vm.push(i)
                        }
                        vm.execute()
                    } else
                        throw Vm_FileNotFound(file.name)
                }
            }
        }
    } else {
        println("Usage: java -jar commander.jar <file>")
    }
}