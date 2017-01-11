package controllers

import models.{AuditLog, School}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 *
 * id: Long
 * town: String
 * state: String
 * country: String
 * active: Boolean
 *
 */
object SchoolController extends Controller {

    implicit val schoolRead = Json.reads[School]
    implicit val schoolWrite = Json.writes[School]

    def get = Action.async {
        School.getSchools.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(school, _) =>
                AuditLog.addToLog(request.uri, school.toString).flatMap(res =>
                    School.addToSchool(school).map(res =>
                        Ok("School added")
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
