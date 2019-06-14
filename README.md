# Scala Futures and Exceptions

This project provides examples on how to manage futures and exceptions from PlayFramework Actions.

The controller file includes several examples. Those functions that end with "e" have explicit exception management. For example in function `a` and function `ae`.

See the routes file for a list of working endpoints. All of them are `GET` requests and don't take any parameters.

Timeouts are added using Play's `Futures` class as described in [Handling time outs](https://www.playframework.com/documentation/2.7.x/ScalaAsync#Handling-time-outs)
