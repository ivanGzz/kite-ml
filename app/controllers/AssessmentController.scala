package controllers

import models.{Assessment, AuditLog}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 * id: long
 * score: long
 * team_id: long (points to Team)
 * rubric_id: long (points to Rubric)
 * rubric_dimension_id: long (points to Rubric Dimension)
 * user_id: long (points to User)
 * user_type: String
 *
 */
object AssessmentController extends Controller {

    implicit val assessmentRead = Json.reads[Assessment]
    implicit val assessmentWrite = Json.writes[Assessment]

    def get = Action.async {
        Assessment.getAssessments.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(assessment, _) =>
                AuditLog.addToLog(request.uri, assessment.toString).flatMap(res =>
                    Assessment.addToAssessment(assessment).map(res =>
                        Ok("Assessment added")
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
