package controllers

import models.{AuditLog, User}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/6/16.
 *
 * Model:
 *
 * id: long
 * birthday: Date
 * active: Boolean
 * role_id: long (Points to Role)
 *
 */
object UserController extends Controller {

    implicit val userRead = Json.reads[User]
    implicit val userWrite = Json.writes[User]

    def get = Action.async {
        User.getUsers.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(user, _) =>
                AuditLog.addToLog(request.uri, user.toString).flatMap(res =>
                    User.addToUsers(user)
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
