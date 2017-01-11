package controllers

import models.{AuditLog, Rubric}
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
 * author_id: long
 *
 */
object RubricController extends Controller {

    implicit val rubricRead = Json.reads[Rubric]
    implicit val rubricWrite = Json.writes[Rubric]

    def get = Action.async {
        Rubric.getRubrics.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(rubric, _) =>
                AuditLog.addToLog(request.uri, rubric.toString).flatMap(res =>
                    Rubric.addToRubrics(rubric).map(res =>
                        Ok("Rubric added")
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
