package controllers

import models.{AuditLog, Role}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * name: String
 *
 */
object RoleController extends Controller {

    implicit val roleRead = Json.reads[Role]
    implicit val roleWrite = Json.writes[Role]

    def get = Action.async {
        Role.getRoles.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(role, _) =>
                AuditLog.addToLog(request.uri, role.toString).flatMap(res =>
                    Role.addToRoles(role).map(res =>
                        Ok("Role added")
                    )
                )
            case JsError(errors) => {
                Future(BadRequest)
            }
        }
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
