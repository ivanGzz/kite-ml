package controllers

import models.{AuditLog, Course}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/15/16.
 *
 * Model:
 * id: long
 * code: String
 * multidisciplinary: bool
 * grade: String
 * school_id: long (points to School)
 * active: bool
 * tech: bool
 *
 */
object CourseController extends Controller {

    implicit val courseRead = Json.reads[Course]
    implicit val courseWrite = Json.writes[Course]

    def get = Action.async {
        Course.getCourses.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(course, _) =>
                AuditLog.addToLog(request.uri, course.toString).flatMap(res =>
                    Course.addToCourses(course).map(res =>
                        Ok("Course added")
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
