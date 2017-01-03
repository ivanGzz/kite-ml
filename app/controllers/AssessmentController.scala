package controllers

import models.{Assessment, AuditLog}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global

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

    def get = Action {
        Ok
    }

    def post = Action.async { implicit request =>
        AuditLog.addToLog(request.uri, request.body.toString).flatMap(res =>
            Assessment.addToAssessment(1, 0, 0, 0, 0, 0, "").map(res =>
                Ok("Assessment added")
            )
        )
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
