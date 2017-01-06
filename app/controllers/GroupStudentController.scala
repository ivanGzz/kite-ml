package controllers

import models.{AuditLog, GroupStudent}
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
 * student_id: long (points to Student)
 *
 */
object GroupStudentController extends Controller {

    implicit val groupStudentRead = Json.reads[GroupStudent]
    implicit val groupStudentWrite = Json.writes[GroupStudent]

    def get = Action.async {
        GroupStudent.getGroupStudents.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(groupStudent, _) =>
                AuditLog.addToLog(request.uri, groupStudent.toString).flatMap(res =>
                    GroupStudent.addToGroupStudents(groupStudent).map(res =>
                        Ok("Group Student added")
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
