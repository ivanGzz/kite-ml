package controllers

import models.{AuditLogs, Users, User}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/6/16.
 */
object UserController extends Controller {

    implicit val userRead = Json.reads[User]
    implicit val userWrite = Json.writes[User]

    def get = Action.async {
        Users.getUsers.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(user, _) =>
                AuditLogs.addToLog(request.uri, "POST", user.toString).flatMap(res =>
                    Users.addToUsers(user)
                ).map(res =>
                    Ok("User added")
                )
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
