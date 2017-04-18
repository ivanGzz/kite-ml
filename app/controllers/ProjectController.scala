package controllers

import java.sql.Date

import models.{Project, Projects}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by Nelson on 4/18/2017.
 */
object ProjectController extends Controller {

    case class ProjectRequest(id: Long, competencies: List[Int])

    implicit val ProjectRequestRead = Json.reads[ProjectRequest]
    implicit val ProjectRequestWrite = Json.writes[Project]

    def get(id: Long) = Action.async {
        Projects.getProjectById(id).map {
            _ match {
                case Some(project) => Ok(project)
                case None => NotFound
            }
        }
    }

    def create = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(projectRequest, _) => {
                val today = new java.util.Date
                val project = Project(projectRequest.id, projectRequest.competencies.mkString(","), new Date(today.getTime))
                Projects.addToProjects(project).map(res => Ok)
            }
            case JsError(errors) => Future(BadRequest)
        }
    }

}
