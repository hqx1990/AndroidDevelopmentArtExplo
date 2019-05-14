package com.bestvike.androiddevelopmentartexploration.kotlin


class KotlinTest1 (val name : String){

    fun greet(){
        println("Hello $name")
    }
    fun sum (a : Int , b :Int) : Int{
        return a + b
    }
}
//可变变量定义：var 关键字
//不可变变量定义：val 关键字，只能赋值一次的变量(类似Java中final修饰的变量)
val d = 100;
var a = 1
var b = 22
var  c = "测试"
fun  main(args: Array<String>) {

//    println(sum(c))
//    println(KotlinTest1("").sum(a,b))
//    println("表达式："+sum(a,b))
//    printSum(a,b)
//    vars(1,2,3,4,5,6)

    //ambda表达式使用实例：
    val sumLambda : (Int,Int) -> Int = {x,y -> x * y}
    println("表达式："+sumLambda(2,3))


}
fun sum(a : String) : String{
    return a
}

fun sum (a:Int , b : Int) = a + b//表达式

fun  printSum(a : Int , b : Int) : Unit{
    //无返回值的函数(类似Java中的void)：
    println("无返回值的函数1:"+a + b)
}

//fun printSum(a : Int , b : Int){
//    //无返回值的函数(类似Java中的void)：
//    println("无返回值的函数:"+a + b)
//}
/**
 * 可变长参数函数
 */
fun vars(vararg v : Int){
    for (vr in v){
        println(vr)
    }
}



