package search

import java.io.File


fun main(args: Array<String>) {

    if (args[0] == "--data") {

        val lines = File(args[1]).readLines().toTypedArray()
        val peoples = initFromFile(args[1])
        menu(peoples, lines)
    }
}

fun menu(peoples: Map<String, List<Int>>, lines: Array<String>) {
    println("""
        |=== Menu ===
        |1. Search information.
        |2. Print all data.
        |0. Exit.
    """.trimMargin())

    when (readLine()!!) {
        "1" -> findPersonChange(peoples, lines)
        "2" -> printAllPeople(peoples, lines)
        "0" -> println("\nBye!")
        else -> {
            println("\nIncorrect option! Try again.")
            menu(peoples, lines)
        }
    }

}

fun allPeople(peoples: Map<String, List<Int>>, lines: Array<String>) {
    println("Enter a name or email to search all suitable people.")

    val query = readLine()!!.toUpperCase().split(" ")

    var changeLines = mutableSetOf<String>()

    for (value in query) {
        val indexList = peoples[value]
        if (indexList != null) {
            for (i in indexList) {
                var containsInString = true
                for (word in query) {
                    if (!lines[i].toUpperCase().contains(word)) {
                        containsInString = false
                        break
                    }
                }

                if (containsInString) {
                    changeLines.add(lines[i])
                }
            }
        }
    }

    if (changeLines.size == 0) {
        println("No matching people found.")
    } else {
        for (people in changeLines) {
            println(people)
        }
    }

    menu(peoples, lines)

}

fun nonePeople(peoples: Map<String, List<Int>>, lines: Array<String>) {
    println("Enter a name or email to search all suitable people.")
    val query = readLine()!!.toUpperCase().split(" ")

    val changeLines = lines.toMutableList()

    for (value in query) {
        val indexList = peoples[value]
        if (indexList != null) {
            for (i in indexList) {
                changeLines.remove(lines[i])
            }
        }
    }

    if (changeLines.size == 0) {
        println("No matching people found.")
    } else {
        for (people in changeLines) {
            println(people)
        }
    }

    menu(peoples, lines)
}

fun findPersonChange(peoples: Map<String, List<Int>>, lines: Array<String>) {

    println("Select a matching strategy: ALL, ANY, NONE")

    when (readLine()!!) {
        "ALL" -> allPeople(peoples, lines)
        "ANY" -> anyPeople(peoples, lines)
        "NONE" -> nonePeople(peoples, lines)
    }
}

fun anyPeople(peoples: Map<String, List<Int>>, lines: Array<String>) {
    println("Enter a name or email to search all suitable people.")
    val query = readLine()!!.toUpperCase().split(" ")


    var changeLines = mutableSetOf<String>()
    for (value in query) {
        val indexList = peoples[value]
        if (indexList != null) {
            for (i in indexList) {
                changeLines.add(lines[i])
            }
        }
    }

    if (changeLines.size == 0) {
        println("No matching people found.")
    } else {
        println("${changeLines.size} persons found:")
        for (line in changeLines) {
            println(line)
        }
    }
    menu(peoples, lines)

}

fun initFromFile(path: String): Map<String, List<Int>> {
    val file = File(path)
    val peoples = mutableMapOf<String, MutableList<Int>>()
    for ((str, lines) in file.readLines().withIndex()) {
        for (word in lines.toUpperCase().split(" ")) {
            var listStr = peoples.getOrDefault(word, mutableListOf<Int>())
            listStr.add(str)
            peoples[word] = listStr
        }
    }

    return peoples
}

fun printAllPeople(peoples: Map<String, List<Int>>, lines: Array<String>) {
    println("=== List of people ===")
    lines.forEach { println(it) }
    menu(peoples, lines)
}

