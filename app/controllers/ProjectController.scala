package controllers

import models.{AuditLog, Project}
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
 * author_id: long (points to User)
 * project_type_id: long (points to Project Type)
 * parent_project_id: long (points to Project)
 * teams_count: long
 * team_member_count: long
 *
 */
object ProjectController extends Controller {

    implicit val projectRead = Json.reads[Project]
    implicit val projectWrite = Json.writes[Project]

    def get = Action.async {
        Project.getProjects.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(project, _) =>
                AuditLog.addToLog(request.uri, project.toString).flatMap(res =>
                    Project.addToProjects(project).map(res =>
                        Ok("Project added")
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
