package controllers

import models.{AuditLog, GroupClassTeacher}
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
 * group_class_id: long (points to Group Class)
 * teacher_id: long (points to Teacher)
 *
 */
object GroupClassTeacherController extends Controller {

    implicit val groupClassTeacherRead = Json.reads[GroupClassTeacher]
    implicit val groupClassTeacherWrite = Json.writes[GroupClassTeacher]

    def get = Action.async {
        GroupClassTeacher.getGroupClassTeachers.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(groupClassTeacher, _) =>
                AuditLog.addToLog(request.uri, groupClassTeacher.toString).flatMap(res =>
                    GroupClassTeacher.addToGroupClassTeachers(groupClassTeacher).map(res =>
                        Ok("Group Class Teacher added")
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
