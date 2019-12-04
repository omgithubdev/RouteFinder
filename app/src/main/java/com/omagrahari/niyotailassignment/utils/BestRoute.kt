package com.omagrahari.niyotailassignment.utils

import java.util.*

class BestRoute {
    private var stack: Stack<Int>? = null
    private var numberOfNodes = 0

    init {
        stack = Stack()
    }

    fun calculateRoute(distanceArray: ArrayList<ArrayList<Float>>): ArrayList<Int> {
        val path: ArrayList<Int> = ArrayList()
        numberOfNodes = distanceArray[1].size - 1
        val visited = IntArray(numberOfNodes + 1)
        visited[1] = 0
        stack!!.push(0)
        var element: Int
        var dst = 0
        var i: Int
        var min = Float.MAX_VALUE
        var minFlag = false
        path.add(0)
        while (!stack!!.isEmpty()) {
            element = stack!!.peek()
            i = 1
            min = Float.MAX_VALUE
            while (i <= numberOfNodes) {
                if (distanceArray[element][i] > 1 && visited[i] == 0) {
                    if (min > distanceArray[element][i]) {
                        min = distanceArray[element][i]
                        dst = i
                        minFlag = true
                    }
                }
                i++
            }
            if (minFlag) {
                visited[dst] = 1
                stack!!.push(dst)
                path.add(dst)
                minFlag = false
                continue
            }
            stack!!.pop()
        }
        return path
    }
}