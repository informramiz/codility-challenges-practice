package payevertest

/**
 * Created by Ramiz Raja on 2020-04-06
 */

fun crop(message: String, K: Int): String {
    if (message.length <= K) {
        return message
    }

    //we are only interested in words so in case message exceeds we will drop words
    val words = message.split(" ")
    var currentMsgLength = message.length
    var currentWordsEndIndex = words.lastIndex

    //keep dropping words until we reach under defined limit
    while (currentMsgLength > K && currentWordsEndIndex >= 0) {
        currentMsgLength -= words[currentWordsEndIndex].length + 1
        currentWordsEndIndex--
    }

    val remainingWords = words.subList(0, currentWordsEndIndex+1)
    print(remainingWords)
    return remainingWords.joinToString(" ")
}


fun calculateCost(S: String): Int {
    val lines = S.split("\n")
    val numbers = mutableMapOf<String, String>()
    for (line in lines) {
        val splits = line.split(",")
        numbers[splits[1]] = splits[0]
    }

    val promoNumber = findPromoNumber(numbers)
    var cost = 0
    for (number in numbers.keys) {
        if (number != promoNumber) {
            cost += callCost(numbers[number]!!)
        }
    }

    return cost
}

fun findPromoNumber(numbers: Map<String, String>): String {
    val duration = mutableMapOf<String, Double>()
//    for (item in numbers.entries) {
//        if (duration.get(item.key) != null) {
//            duration[item.key] = duration[item.key]?.plus(timeToDuration(item.value))
//        }
//    }

    return ""
}

fun timeToDuration(time: String): Double {
    val splits = time.split(":")
    val hour = splits[0].toInt()
    val totalMins = splits[1].toInt() + hour * 60
    val sec = splits[2].toInt()
    return totalMins + sec/60.0
}


fun callCost(time: String): Int {
    val splits = time.split(":")
    val hour = splits[0].toInt()
    val totalMins = splits[1].toInt() + hour * 60
    val sec = splits[2].toInt()

    return when {
        totalMins < 5 -> {
            (totalMins*60 + sec) * 3
        }
        else -> {
            var cost = totalMins * 150
            if (sec > 0) {
                cost += 150
            }
            cost
        }
    }
}

fun main() {
    val array = arrayOf(1, 3)
    val newArray = Array<Boolean>(4) {false}
    for (n in array) {
        newArray[n] = true
    }
    //sum using arithmetic - sum(array) = number
}