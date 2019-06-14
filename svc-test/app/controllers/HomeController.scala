package controllers

import exceptions.SomeException
import javax.inject._
import play.api.libs.concurrent.Futures
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import service.LongTasks

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import play.api.libs.concurrent.Futures._


@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  ws: WSClient,
  implicit val f: Futures
) extends AbstractController(cc) {

  def a() = Action.async { implicit request: Request[AnyContent] =>
    val task100 = LongTasks.task100().withTimeout(1.second)
    for {
      i <- task100
    } yield {
      Ok(Json.obj("value" -> i))
    }
  }

  def ae() = Action.async { implicit request: Request[AnyContent] =>
    val taskEx = LongTasks.taskEx().withTimeout(1.second)

    (for {
      value <- taskEx
    } yield {
      Ok(Json.obj("value" -> value))
    }) recover {
      case e: SomeException => InternalServerError(Json.obj("error" -> e.getMessage))
    }
  }

  def b() = Action.async { implicit request: Request[AnyContent] =>
    val i = LongTasks.task100().withTimeout(1.second)
    i map { value =>
      Ok(Json.obj("value" -> value))
    }
  }

  def be() = Action.async { implicit request: Request[AnyContent] =>
    val i = LongTasks.taskEx.withTimeout(1.second)
    i map { value =>
      Ok(Json.obj("value" -> value))
    } recover {
      case e: Exception => InternalServerError(Json.obj("error" -> e.getMessage))
    }
  }

  def c() = Action.async { implicit request: Request[AnyContent] =>
    val task200 = LongTasks.task200().withTimeout(1.second)
    val task100 = LongTasks.task100().withTimeout(1.second)
    for {
      i <- task200
      j <- task100
      value = i + j
    } yield {
      Ok(Json.obj("value" -> value))
    }
  }

  def ce() = Action.async { implicit request: Request[AnyContent] =>
    val task200 = LongTasks.task200().withTimeout(1.second)
    val taskEx = LongTasks.taskEx().withTimeout(1.second)
    (for {
      i <- task200
      j <- taskEx
      value = i + j
    } yield {
      Ok(Json.obj("value" -> value))
    }) recover {
      case e: SomeException => InternalServerError(Json.obj("error" -> e.getMessage))
    }
  }

  def d() = Action.async { implicit request: Request[AnyContent] =>
    ws.url("https://www.google.com").withRequestTimeout(5.seconds).get().map { response =>
      if (response.status == 200) Ok(Json.obj("status" -> response.statusText))
      else NotFound(Json.obj("error" -> response.statusText))
    }
  }

  def de() = Action.async { implicit request: Request[AnyContent] =>
    ws.url("https://some.non-existing.domain-name").withRequestTimeout(5.seconds).get().map { response =>
      if (response.status == 200) Ok(Json.obj("status" -> response.statusText))
      else NotFound(Json.obj("error" -> response.statusText))
    } recover {
      case e: Exception => InternalServerError(Json.obj("error" -> e.getMessage))
    }
  }

}
