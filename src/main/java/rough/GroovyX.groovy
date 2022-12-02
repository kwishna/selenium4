package rough

class GroovyX {
    static void main(String[] args) {
        def numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

        println("numbers.any() :: " + numbers.any())

        def isAnyEvenNo = numbers.any { it -> it % 2 == 0 }
        println("isAnyEvenNo - numbers.any {it -> it % 2 == 0 } :: " + isAnyEvenNo)

        def avg = numbers.average()
        println(avg)

        def triple = numbers*.multiply(3)
        println(triple)

        def plus = numbers*.plus(3)
        println(plus)

        def div = numbers*.div(3)
        println(div)

        def minus = numbers*.minus(3)
        println(minus)

        println(1.minus(5))
//        println(numbers*.iterator().toList().average()) // Error
        println(numbers.eachWithIndex { val, idx -> idx == 2 })

        [].unique()

        2.1.toLong()

        def map = [1: 20, a: 30, 2: 42, 4: 34, ba: 67, 6: 39, 7: 49]
        map.removeAll { it -> it.key == 1 }
        println(map)

//        def avgOfOdds = numbers.average { it -> it % 2 != 0 }
//        println(avgOfOdds)
    }
}
