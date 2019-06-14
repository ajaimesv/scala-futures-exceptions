package service

import exceptions.SomeException

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object LongTasks {

  def task100(): Future[Int] = {
    Future {
      Thread.sleep(100)
      100
    }
  }

  def task200(): Future[Int] = {
    Future {
      Thread.sleep(200)
      200
    }
  }

  def taskEx() : Future[Int] = {
    Future {
      throw new SomeException("Cannot get value")
    }
  }

}
