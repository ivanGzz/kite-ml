package controllers

import models.{AuditLog, ClassProject}
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
 * due_date: Date
 * project_id: long (points to Project)
 * group_class_id: long (points to Group Class)
 * rubric_id: long (points to Rubric)
 *
 */
object ClassProjectController extends Controller {

    implicit val classProjectRead = Json.reads[ClassProject]
    implicit val classProjectWrite = Json.writes[ClassProject]

    def get = Action.async {
        ClassProject.getClassProjects.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(classProject, _) =>
                AuditLog.addToLog(request.uri, classProject.toString).flatMap(res =>
                    ClassProject.addToClassProjects(classProject).map(res =>
                        Ok("Class Project added")
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
