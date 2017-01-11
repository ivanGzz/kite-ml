package controllers

import models.{AuditLog, RubricDimension}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/15/16.
 *
 * Model:
 *
 * id: long
 * code: String
 * min_score: long
 * medium_score: long
 * max_score: long
 * rubric_id: long (points to Rubric)
 * author_id: long
 *
 */
object RubricDimensionController extends Controller {

    implicit val rubricDimensionRead = Json.reads[RubricDimension]
    implicit val rubricDimensionWrite = Json.writes[RubricDimension]

    def get = Action.async {
        RubricDimension.getRubricDimensions.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(rubricDimension, _) =>
                AuditLog.addToLog(request.uri, rubricDimension.toString).flatMap(res =>
                    RubricDimension.addToRubricDimension(rubricDimension).map(res =>
                        Ok("Rubric Dimension added")
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
