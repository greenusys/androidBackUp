package com.example.digitalforgeco.data

import android.content.Context
import android.util.Log
import android.widget.Toast

import java.util.ArrayList

/**
 * Created by mihir on 18-08-2018.
 */

class Calculations(private val ctx: Context) {

    private val unaryOperators = ArrayList<String>()
    private val binaryOperators = ArrayList<String>()
    private val constants = ArrayList<String>()

    var numbers = ArrayList<String>()
    //private ArrayList<String> operators = new ArrayList<>();
    //log base -> 10, ln base -> Euler's number = e ~ 2.718
    lateinit var answer: String

    var isExp = false

    /**
     * currentStatus = {const, unary, binary, num, po, pc};
     */
    var currentStatus = "null"


    val currentNumber: String
        get() {
            if (currentStatus != "num") {
                Log.d("calc", "current Number returned : 0")
                return "0"
            } else {
                Log.d("calc", "current Number returned : " + numbers[numbers.size - 1])
                return numbers[numbers.size - 1]
            }
        }


    init {
        unaryOperators.add("!")
        unaryOperators.add("%")
        unaryOperators.add("ln")
        unaryOperators.add("log")
        unaryOperators.add("dms")
        unaryOperators.add("deg")
        unaryOperators.add("exp")
        unaryOperators.add("sin")
        unaryOperators.add("cos")
        unaryOperators.add("tan")
        unaryOperators.add("sin-1")
        unaryOperators.add("tan-1")
        unaryOperators.add("cos-1")
        unaryOperators.add("sinh")
        unaryOperators.add("tanh")
        unaryOperators.add("cosh")
        unaryOperators.add("sinh-1")
        unaryOperators.add("tan-1")
        unaryOperators.add("cos-1")
        unaryOperators.add("1/x")
        unaryOperators.add("sq")
        unaryOperators.add("cb")
        unaryOperators.add("sqrt")
        unaryOperators.add("cbrt")
        unaryOperators.add("10^x")
        unaryOperators.add("e^x")

        /*
         * binaryOperators in order...
         */
        binaryOperators.add("rt")
        binaryOperators.add("^")
        binaryOperators.add("mod")
        binaryOperators.add("/")
        binaryOperators.add("*")
        binaryOperators.add("+")
        binaryOperators.add("-")

        constants.add("pi")
        constants.add("e")
    }

    fun clear(): Boolean {
        try {
            numbers.clear()
            answer = "0"
            calc(numbers)
        } catch (e: Exception) {
            calc(numbers)
            return false
        }

        currentStatus = "null"
        return true
    }

    fun ce(): Boolean {
        try {
            when (currentStatus) {
                "num" -> numbers.removeAt(numbers.size - 1)
                "const" -> numbers.removeAt(numbers.size - 1)
                "binary" -> numbers.removeAt(numbers.size - 1)
                "unary" -> if (unaryOperators.contains(numbers[numbers.size - 1])) {
                    numbers.removeAt(numbers.size - 1)
                    numbers.removeAt(numbers.size - 1)
                    var bracketOffset = -1
                    var i = numbers.size - 1
                    while (i == 0) {
                        if (numbers[i] == "]") {
                            bracketOffset--
                        }
                        if (numbers[i] == "[") {
                            bracketOffset++
                            if (bracketOffset == 0) {
                                numbers.removeAt(i)
                            }
                        }
                        i--
                    }
                } else {
                    numbers.removeAt(numbers.size - 1)
                    var bracketOffset = -1
                    var i = numbers.size - 1
                    while (i == 0) {
                        if (numbers[i] == "}") {
                            bracketOffset--
                        }
                        if (numbers[i] == "{") {
                            bracketOffset++
                            if (bracketOffset == 0) {
                                numbers.removeAt(i - 1)
                                numbers.removeAt(i - 1)
                            }
                        }
                        i--
                    }
                }
                "po" -> numbers.removeAt(numbers.size - 1)
                "pc" -> numbers.removeAt(numbers.size - 1)
            }
            if (numbers.size == 0) {
                currentStatus = "null"
                return true
            }
            currentStatus = getStatus(numbers.size - 1)
            calc(numbers)
            return true
        } catch (e: Exception) {
            return false
        }

    }

    fun bs(): Boolean {
        if (currentStatus == "num") {
            try {
                numbers[numbers.size - 1] = formatNumber(numbers[numbers.size - 1].substring(0, numbers[numbers.size - 1].length - 1))
            } catch (e: Exception) {
                numbers[numbers.size - 1] = "0"
                return false
            }

        }
        calc(numbers)
        return true
    }

    fun numberClicked(number: String): Boolean {
        when (currentStatus) {
            "null" -> numbers.add(number)
            "num" -> numbers[numbers.size - 1] = formatNumber(numbers[numbers.size - 1] + number)
            "const" -> {
                numbers.add("*")
                numbers.add(number)
            }
            "unary" -> {
                numbers.add("*")
                numbers.add(number)
            }
            "binary" -> numbers.add(number)
            "po" -> numbers.add(number)
            "pc" -> {
                numbers.add("*")
                numbers.add(number)
            }
            else -> Toast.makeText(ctx, "Error while numberClicked(), currentStatus = $currentStatus", Toast.LENGTH_SHORT).show()
        }
        currentStatus = "num"
        calc(numbers)
        return true
    }

    fun decimalClicked(): Boolean {
        if (currentStatus == "num") {
            if (numbers[numbers.size - 1].contains(".")) {
                return false
            } else {
                numbers[numbers.size - 1] = numbers[numbers.size - 1] + "."
                return true
            }
        } else {
            return false
        }
    }

    fun operatorClicked(operator: String): Boolean {
        Log.d("calc", "Operator : $operator")
        try {
            if (operator == "±") {
                numbers[numbers.size - 1] = formatNumber((java.lang.Double.parseDouble(numbers[numbers.size - 1]) * -1).toString())
                calc(numbers)
                return true
            }
        } catch (e: Exception) {
            calc(numbers)
            return false
        }

        if (constants.contains(operator)) {
            when (currentStatus) {
                "null" -> numbers.add(operator)
                "num" -> {
                    numbers.add("*")
                    numbers.add(operator)
                }
                "const" -> {
                    numbers.add("*")
                    numbers.add(operator)
                }
                "unary" -> {
                    numbers.add("*")
                    numbers.add(operator)
                }
                "binary" -> numbers.add(operator)
                "po" -> numbers.add(operator)
                "pc" -> {
                    numbers.add("*")
                    numbers.add(operator)
                }
                else -> Toast.makeText(ctx, "Error while constants, currentStatus = $currentStatus", Toast.LENGTH_SHORT).show()
            }
            currentStatus = "const"
        } else if (unaryOperators.contains(operator)) {
            when (currentStatus) {
                "null" -> {
                    numbers.add("0")
                    if (operator == "!" || operator == "%" || operator == "sq" || operator == "cb") {
                        numbers.add(numbers.size - 1, "[")
                        numbers.add("]")
                        numbers.add(operator)
                    } else {
                        numbers.add(numbers.size - 1, operator)
                        numbers.add(numbers.size - 1, "{")
                        numbers.add("}")
                    }
                    currentStatus = "unary"
                }
                "num" -> {
                    if (operator == "!" || operator == "%" || operator == "sq" || operator == "cb") {
                        numbers.add(numbers.size - 1, "[")
                        numbers.add("]")
                        numbers.add(operator)
                    } else {
                        numbers.add(numbers.size - 1, operator)
                        numbers.add(numbers.size - 1, "{")
                        numbers.add("}")
                    }
                    currentStatus = "unary"
                }
                "const" -> {
                    if (operator == "!" || operator == "%" || operator == "sq" || operator == "cb") {
                        numbers.add(numbers.size - 1, "[")
                        numbers.add("]")
                        numbers.add(operator)
                    } else {
                        numbers.add(numbers.size - 1, operator)
                        numbers.add(numbers.size - 1, "{")
                        numbers.add("}")
                    }
                    currentStatus = "unary"
                }
                "unary" -> if (numbers[numbers.size - 1] == "!" || numbers[numbers.size - 1] == "%" || numbers[numbers.size - 1] == "sq" || numbers[numbers.size - 1] == "cb") {
                    if (operator == "!" || operator == "%" || operator == "sq" || operator == "cb") {
                        var bracketOffset = 0
                        var i = numbers.size - 1
                        while (i == 0) {
                            if (numbers[i] == "]") {
                                bracketOffset--
                            }
                            if (numbers[i] == "[") {
                                bracketOffset++
                                if (bracketOffset == 0) {
                                    numbers.add(i, "[")
                                    numbers.add("]")
                                    numbers.add(operator)
                                    break
                                }
                            }
                            i--
                        }
                    } else {
                        var bracketOffset = 0
                        var i = numbers.size - 1
                        while (i == 0) {
                            if (numbers[i] == "]") {
                                bracketOffset--
                            }
                            if (numbers[i] == "[") {
                                bracketOffset++
                                if (bracketOffset == 0) {
                                    numbers.add(i, operator)
                                    numbers.add(i, "{")
                                    numbers.add("}")
                                    break
                                }
                            }
                            i--
                        }
                    }
                    currentStatus = "unary"
                } else {
                    if (operator == "!" || operator == "%" || operator == "sq" || operator == "cb") {
                        var bracketOffset = 0
                        var i = numbers.size - 1
                        while (i == 0) {
                            if (numbers[i] == "}") {
                                bracketOffset--
                            }
                            if (numbers[i] == "{") {
                                bracketOffset++
                                if (bracketOffset == 0) {
                                    numbers.add(i - 1, "[")
                                    numbers.add("]")
                                    numbers.add(operator)
                                    break
                                }
                            }
                            i--
                        }
                    } else {
                        var bracketOffset = 0
                        var i = numbers.size - 1
                        while (i == 0) {
                            if (numbers[i] == "}") {
                                bracketOffset--
                            }
                            if (numbers[i] == "{") {
                                bracketOffset++
                                if (bracketOffset == 0) {
                                    numbers.add(i, operator)
                                    numbers.add(i, "{")
                                    numbers.add("}")
                                    break
                                }
                            }
                            i--
                        }
                    }
                    currentStatus = "unary"
                }
                "binary" -> {
                }
                "po" -> {
                }
                "pc" -> {
                }
                else -> Toast.makeText(ctx, "Error while unary, currentStatus = $currentStatus", Toast.LENGTH_SHORT).show()
            }
        } else if (binaryOperators.contains(operator)) {
            when (currentStatus) {
                "null" -> {
                    numbers.add("0")
                    numbers.add(operator)
                    currentStatus = "binary"
                }
                "num" -> {
                    numbers.add(operator)
                    currentStatus = "binary"
                }
                "const" -> {
                    numbers.add(operator)
                    currentStatus = "binary"
                }
                "unary" -> {
                    numbers.add(operator)
                    currentStatus = "binary"
                }
                "binary" -> {
                    numbers[numbers.size - 1] = operator
                    currentStatus = "binary"
                }
                "po" -> {
                }
                "pc" -> {
                    numbers.add(operator)
                    currentStatus = "binary"
                }
                else -> Toast.makeText(ctx, "Error while binary, currentStatus = $currentStatus", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    fun parent_openClicked(): Boolean {
        when (currentStatus) {
            "null" -> numbers.add("(")
            "num" -> {
                numbers.add("*")
                numbers.add("(")
            }
            "const" -> {
                numbers.add("*")
                numbers.add("(")
            }
            "unary" -> {
                numbers.add("*")
                numbers.add("(")
            }
            "binary" -> numbers.add("(")
            "po" -> numbers.add("(")
            "pc" -> {
                numbers.add("*")
                numbers.add("(")
            }
            else -> Toast.makeText(ctx, "Error while numberClicked(), currentStatus = $currentStatus", Toast.LENGTH_SHORT).show()
        }
        currentStatus = "po"
        calc(numbers)
        return true
    }

    fun parent_closeClicked(): Boolean {
        if (!canCloseParent()) {
            Toast.makeText(ctx, "No open parenthesis to compensate", Toast.LENGTH_SHORT).show()
            return false
        }
        when (currentStatus) {
            "num" -> {
                numbers.add(")")
                currentStatus = "pc"
            }
            "const" -> {
                numbers.add(")")
                currentStatus = "pc"
            }
            "unary" -> {
                numbers.add(")")
                currentStatus = "pc"
            }
            "binary" -> {
            }
            "po" -> {
            }
            "pc" -> numbers.add(")")
            else -> Toast.makeText(ctx, "Error while numberClicked(), currentStatus = $currentStatus", Toast.LENGTH_SHORT).show()
        }
        calc(numbers)
        return true
    }

    private fun canCloseParent(): Boolean {
        var bracketOffset = 0
        for (number in numbers) {
            if (number == "(") {
                bracketOffset++
            }
            if (number == ")") {
                bracketOffset--
            }
        }
        return bracketOffset > 0
    }

    fun evaluateAnswer(): Boolean {
        if (numbers.contains("(")) {
            if (canCloseParent()) {
                var bracketOffset = 0
                for (number in numbers) {
                    if (number == "(") {
                        bracketOffset++
                    }
                    if (number == ")") {
                        bracketOffset--
                    }
                }
                for (i in 0 until bracketOffset) {
                    numbers.add(")")
                }
            }
        }
        answer = doBODMAS(numbers)
        calc(numbers)
        return true
    }

    fun calc(numbers: ArrayList<String>): String {
        var num = ""
        for (number in numbers) {
            num += "$number "
        }
        Log.d("calc", num)
        return num
    }

    private fun doBODMAS(numbers: ArrayList<String>): String {
        if (contains(constants, numbers)) {
            for (i in numbers.indices) {
                if (constants.contains(numbers[i])) {
                    numbers[i] = evaluateConstant(numbers[i])
                }
            }
        }
        while (numbers.size != 1) {
            Log.d("calc", "doBodmas:")
            calc(numbers)
            Log.d("calc", "contains ( = " + numbers.contains("("))
            if (numbers.contains("(")) {
                var bracketOffset = 0
                for (i in numbers.indices.reversed()) {
                    Log.d("calc", "at i = $i")
                    if (numbers[i] == ")") {
                        bracketOffset--
                        for (j in i - 1 downTo 0) {
                            Log.d("calc", "at i = $j")
                            if (numbers[j] == ")") {
                                bracketOffset--
                            }
                            if (numbers[j] == "(") {
                                bracketOffset++
                                if (bracketOffset == 0) {
                                    Log.d("calc", "satisfied at j = $j")
                                    val nums = ArrayList<String>()
                                    for (k in j + 1 until i) {
                                        nums.add(numbers[k])
                                    }
                                    calc(nums)
                                    for (k in j until i) {
                                        numbers.removeAt(j)
                                    }
                                    numbers[j] = doBODMAS(nums)
                                    break
                                }
                            }
                        }
                        break
                    }
                }
            } else {
                if (contains(unaryOperators, numbers)) {
                    var i = 0
                    while (i < numbers.size) {
                        if (unaryOperators.contains(numbers[i])) {
                            if (numbers[i] == "!" || numbers[i] == "%" || numbers[i] == "sq" || numbers[i] == "cb") {
                                var bracketOffset = 0
                                for (j in i - 1 downTo 0) {
                                    if (numbers[j] == "]") {
                                        bracketOffset--
                                    }
                                    if (numbers[j] == "[") {
                                        bracketOffset++
                                        if (bracketOffset == 0) {
                                            val nums = ArrayList<String>()
                                            Log.d("calc", "i:$i")
                                            Log.d("calc", "j:$j")
                                            run {
                                                var k = j + 1
                                                while (k == i - 2) {
                                                    nums.add(numbers[k])
                                                    k++
                                                }
                                            }
                                            if (nums.size == 1) {
                                                val ans = evaluateUnary(numbers[i], nums[0])
                                                for (k in j until i) {
                                                    numbers.removeAt(j)
                                                }
                                                numbers[j] = ans
                                            } else {
                                                for (k in j until i) {
                                                    numbers.removeAt(j)
                                                }
                                                numbers[j] = doBODMAS(nums)
                                            }
                                            i = 0
                                            break
                                        }
                                    }
                                }
                            } else {
                                var bracketOffset = 0
                                for (j in i + 1 until numbers.size) {
                                    if (numbers[j] == "{") {
                                        bracketOffset++
                                    }
                                    if (numbers[j] == "}") {
                                        bracketOffset--
                                        if (bracketOffset == 0) {
                                            val nums = ArrayList<String>()
                                            Log.d("calc", "in Binary eval:")
                                            for (k in i + 2 until j) {
                                                nums.add(numbers[k])
                                            }
                                            Log.d("calc", "i:$i")
                                            Log.d("calc", "j:$j")
                                            calc(nums)
                                            if (nums.size == 1) {
                                                val ans = evaluateUnary(numbers[i], nums[0])
                                                for (k in i until j) {
                                                    numbers.removeAt(i)
                                                }
                                                numbers[i] = ans
                                            } else {
                                                for (k in i until j) {
                                                    numbers.removeAt(i)
                                                }
                                                numbers[i] = doBODMAS(nums)
                                            }
                                            break
                                        }
                                    }
                                }
                            }
                        }
                        i++
                    }
                }
                if (contains(binaryOperators, numbers)) {
                    var i = 0
                    while (i < binaryOperators.size) {
                        if (numbers.contains(binaryOperators[i])) {
                            for (j in numbers.indices) {
                                if (numbers[j] == binaryOperators[i]) {
                                    val ans = evaluateBinary(numbers[j - 1], numbers[j], numbers[j + 1])
                                    numbers.removeAt(j - 1)
                                    numbers.removeAt(j - 1)
                                    numbers[j - 1] = ans
                                    i = 0
                                    break
                                }
                            }
                        }
                        i++
                    }
                }
            }
        }
        currentStatus = "num"
        return formatNumber(numbers[0])
    }

    private fun contains(source: ArrayList<String>, target: ArrayList<String>): Boolean {
        for (`val` in target) {
            if (source.contains(`val`)) {
                return true
            }
        }
        return false
    }

    private fun getStatus(index: Int): String {
        if (constants.contains(numbers[index])) {
            return "const"
        } else if (unaryOperators.contains(numbers[index]) || numbers[index].contains("{") || numbers[index].contains("}") ||
                numbers[index].contains("[") || numbers[index].contains("]")) {
            return "unary"
        } else if (binaryOperators.contains(numbers[index])) {
            return "binary"
        } else if (numbers[index] == "(") {
            return "po"
        } else if (numbers[index] == ")") {
            return "pc"
        } else if (numbers[index] == "0") {
            if (numbers.size - 1 == index) {
                numbers.removeAt(index)
            }
            return "null"
        } else {
            return "num"
        }
    }


    private fun formatNumber(number: String?): String {
        var number = number
        Log.d("calc", "Format Number : " + number!!)
        if (number == null || number == "" || number.isEmpty()) {
            return "0"
        }
        try {
            return if (java.lang.Double.parseDouble(number) == java.lang.Long.parseLong(number.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]).toDouble()) {
                java.lang.Long.parseLong(number.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]).toString()
            } else {
                java.lang.Double.parseDouble(number).toString()
            }
        } catch (nfe: NumberFormatException) {
            try {
                return java.lang.Double.parseDouble(number).toString()
            } catch (nfe2: NumberFormatException) {
                try {
                    number = number.substring(0, number.length - 1)
                    return java.lang.Double.parseDouble(number).toString()
                } catch (e: Exception) {
                    return number
                }

            }

        }

    }


    private fun evaluateConstant(notation: String): String {
        Log.d("calc", "constant : $notation")
        var value = "error"
        when (notation) {
            "pi" -> value = pi()
            "e" -> value = e()
            else -> Toast.makeText(ctx, "Error while evaluating constants : $notation", Toast.LENGTH_SHORT).show()
        }
        return value
    }

    private fun evaluateUnary(operation: String, value: String): String {
        Log.d("calc", "Unary : $operation, $value")
        var answer = "error"
        when (operation) {
            "!" -> answer = factorial(value)
            "%" -> answer = percent(value)
            "ln" -> answer = ln(value)
            "log" -> answer = log(value)
            "dms" -> answer = dms(value)
            "deg" -> answer = deg(value)
            "exp" -> answer = exp(value)
            "sin" -> answer = sin(value)
            "cos" -> answer = cos(value)
            "tan" -> answer = tan(value)
            "sin-1" -> answer = asin(value)
            "cos-1" -> answer = acos(value)
            "tan-1" -> answer = atan(value)
            "sinh" -> answer = sinh(value)
            "cosh" -> answer = cosh(value)
            "tanh" -> answer = tanh(value)
            "sinh-1" -> answer = asinh(value)
            "cosh-1" -> answer = acosh(value)
            "tanh-1" -> answer = atanh(value)
            "sq" -> answer = indices(value, "2")
            "cb" -> answer = indices(value, "3")
            "1/x" -> answer = division("1", value)
            "sqrt" -> answer = root(value, "2")
            "cbrt" -> answer = root(value, "3")
            "10^x" -> answer = indices("10", value)
            "e^x" -> answer = indices(e(), value)
            else -> Toast.makeText(ctx, "Error in evaluating Unary operation : $operation", Toast.LENGTH_SHORT).show()
        }
        Log.d("calc", "returned : $answer")
        try {
            return formatNumber(answer)
        } catch (e: Exception) {
            return answer
        }

    }

    private fun evaluateBinary(number1: String, operation: String, number2: String): String {
        Log.d("calc", "binary : $number1, $operation, $number2")
        var answer = "error"
        when (operation) {
            "rt" -> answer = root(number1, number2)
            "^" -> answer = indices(number1, number2)
            "mod" -> answer = mod(number1, number2)
            "/" -> answer = division(number1, number2)
            "*" -> answer = multiplication(number1, number2)
            "+" -> answer = addition(number1, number2)
            "-" -> answer = subtraction(number1, number2)
            else -> Toast.makeText(ctx, "error in evaluating Binary operation : $operation", Toast.LENGTH_SHORT).show()
        }
        try {
            return formatNumber(answer)
        } catch (e: Exception) {
            return answer
        }

    }

    private fun pi(): String {
        return Math.PI.toString()
    }

    private fun e(): String {
        return Math.E.toString()
    }


    private fun factorial(num: String): String {
        try {
            val intNum = Integer.valueOf(num)
            val doubleNum = java.lang.Double.valueOf(num)
            return if (intNum.toDouble() == doubleNum) {
                fact(intNum).toString()
            } else {
                gammaFunction(doubleNum + 1).toString()
            }
        } catch (nfe: NumberFormatException) {
            return gammaFunction(java.lang.Double.valueOf(num) + 1).toString()
        }

    }

    private fun fact(num: Int): Int {
        return if (num == 0) {
            1
        } else {
            num * fact(num - 1)
        }
    }

    private fun gammaFunction(x: Double): Double {
        val tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5)
        val ser = 1.0 + 76.18009173 / (x + 0) - 86.50532033 / (x + 1) + 24.01409822 / (x + 2) - 1.231739516 / (x + 3) + 0.00120858003 / (x + 4) - 0.00000536382 / (x + 5)
        return Math.exp(tmp + Math.log(ser * Math.sqrt(2 * Math.PI)))
    }

    private fun percent(num: String): String {
        return (java.lang.Double.parseDouble(num) / 100).toString()
    }

    private fun ln(num: String): String {
        return Math.log(java.lang.Double.parseDouble(num)).toString()
    }

    private fun log(num: String): String {
        return Math.log10(java.lang.Double.parseDouble(num)).toString()
    }

    private fun dms(num: String): String {
        // 23.34 -> 23.2024
        val dd = java.lang.Double.parseDouble(num)
        val d = dd.toInt()
        val m = ((dd - d) * 60).toInt()
        val s = Math.round((dd - d.toDouble() - m.toDouble() / 60) * 3600).toInt()
        val y = m.toString() + s.toString()
        return "$d.$y"
    }

    private fun deg(num: String): String {
        //23.34 -> 23.566666666667
        val dms = num.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val d = Integer.valueOf(dms[0])
        var m = "0"
        var s = "0"
        var i = 0
        while (i != dms[1].length) {
            if (i < 2) {
                m += dms[1][i]
            } else if (i < 4) {
                s += dms[1][i]
            } else {
                if (!s.contains(".")) {
                    s += '.'.toString()
                }
                s += dms[1][i]
            }
            i++
        }
        val ms = java.lang.Double.parseDouble(m) / 60 + java.lang.Double.parseDouble(s) / 3600
        return (d + ms).toString()
    }

    private fun exp(value: String): String {
        isExp = true
        return value + "E0"
    }

    private fun sin(value: String): String {
        return Math.sin(java.lang.Double.parseDouble(value)).toString()
    }

    private fun cos(value: String): String {
        return Math.cos(java.lang.Double.parseDouble(value)).toString()
    }

    private fun tan(value: String): String {
        return Math.tan(java.lang.Double.parseDouble(value)).toString()
    }

    private fun asin(value: String): String {
        return Math.asin(java.lang.Double.parseDouble(value)).toString()
    }

    private fun acos(value: String): String {
        return Math.acos(java.lang.Double.parseDouble(value)).toString()
    }

    private fun atan(value: String): String {
        return Math.atan(java.lang.Double.parseDouble(value)).toString()
    }

    private fun sinh(value: String): String {
        return Math.sinh(java.lang.Double.parseDouble(value)).toString()
    }

    private fun cosh(value: String): String {
        return Math.cosh(java.lang.Double.parseDouble(value)).toString()
    }

    private fun tanh(value: String): String {
        return Math.tanh(java.lang.Double.parseDouble(value)).toString()
    }

    private fun asinh(value: String): String {
        val x = java.lang.Double.parseDouble(value)
        return if (x == java.lang.Double.NEGATIVE_INFINITY) {
            value
        } else {
            Math.log(x + Math.sqrt(x * x + 1)).toString()
        }
    }

    private fun acosh(value: String): String {
        val x = java.lang.Double.parseDouble(value)
        return Math.log(x + Math.sqrt(x * x - 1)).toString()
    }

    private fun atanh(value: String): String {
        val x = java.lang.Double.parseDouble(value)
        return (Math.log((1 + x) / (1 - x)) / 2).toString()
    }


    private fun root(num1: String, num2: String): String {
        val num = java.lang.Double.parseDouble(num1)
        val root = java.lang.Double.parseDouble(num2)
        return Math.pow(Math.exp(1 / root), Math.log(num)).toString()
    }

    private fun indices(num1: String, num2: String): String {
        return Math.pow(java.lang.Double.parseDouble(num1), java.lang.Double.parseDouble(num2)).toString()
    }

    private fun mod(num1: String, num2: String): String {
        return (java.lang.Double.parseDouble(num1) % java.lang.Double.parseDouble(num2)).toString()
    }

    private fun division(num1: String, num2: String): String {
        return (java.lang.Double.parseDouble(num1) / java.lang.Double.parseDouble(num2)).toString()
    }

    private fun multiplication(num1: String, num2: String): String {
        return (java.lang.Double.parseDouble(num1) * java.lang.Double.parseDouble(num2)).toString()
    }

    private fun addition(num1: String, num2: String): String {
        return (java.lang.Double.parseDouble(num1) + java.lang.Double.parseDouble(num2)).toString()
    }

    private fun subtraction(num1: String, num2: String): String {
        return (java.lang.Double.parseDouble(num1) - java.lang.Double.parseDouble(num2)).toString()
    }
}