package controllers

import models.{AuditLog, ProjectType}
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
 * description: String
 * code: String
 *
 */
object ProjectTypeController extends Controller {

    implicit val projectTypeRead = Json.reads[ProjectType]
    implicit val projectTypeWrite = Json.writes[ProjectType]

    def get = Action.async {
        ProjectType.getProjectTypes.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(projectType, _) =>
                AuditLog.addToLog(request.uri, projectType.toString).flatMap(res =>
                    ProjectType.addToProjectTypes(projectType).map(res =>
                        Ok("Project Type added")
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
