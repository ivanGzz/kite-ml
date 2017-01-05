package controllers

import models.{AuditLog, GroupClass}
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
 * group_id: long (points to Group)
 * course_id: long (points to Course)
 *
 */
object GroupClassController extends Controller {

    implicit val groupClassRead = Json.reads[GroupClass]
    implicit val groupClassWrite = Json.writes[GroupClass]

    def get = Action.async {
        GroupClass.getGroupClasses.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(groupClass, _) =>
                AuditLog.addToLog(request.uri, groupClass.toString).flatMap(res =>
                    GroupClass.addToGroupClasses(groupClass).map(res =>
                        Ok("Group Class added")
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
