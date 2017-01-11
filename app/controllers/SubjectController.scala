package controllers

import models.{AuditLog, Subject}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 *
 * id: long
 * course_id: long (points to Course)
 *
 */
object SubjectController extends Controller {

    implicit val subjectRead = Json.reads[Subject]
    implicit val subjectWrite = Json.writes[Subject]

    def get = Action.async {
        Subject.getSubjects.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(subject, _) =>
                AuditLog.addToLog(request.uri, subject.toString).flatMap(res =>
                    Subject.addToSubjects(subject)
                ).map(res =>
                    Ok("Subject added")
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
