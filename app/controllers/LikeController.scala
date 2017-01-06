package controllers

import models.{AuditLog, Like}
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
 * likeable_id: long
 * likeable_type: String
 * user_id: long (points to User)
 *
 */
object LikeController extends Controller {

    implicit val likeRead = Json.reads[Like]
    implicit val likeWrite = Json.writes[Like]

    def get = Action.async {
        Like.getLikes.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(like, _) =>
                AuditLog.addToLog(request.uri, like.toString).flatMap(res =>
                    Like.addToLikes(like).map(res =>
                        Ok("Like added")
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
