package controllers

import models.{AuditLog, ProductSkill}
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
 * product_id: long (points to Product)
 * skill_id: long (points to Skill)
 * value: long
 *
 */
object ProductSkillController extends Controller {

    implicit val productSkillRead = Json.reads[ProductSkill]
    implicit val productSkillWrite = Json.writes[ProductSkill]

    def get = Action.async {
        ProductSkill.getProductSkills.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(productSkill, _) =>
                AuditLog.addToLog(request.uri, productSkill.toString).flatMap(res =>
                    ProductSkill.addToProductSkill(productSkill).map(res =>
                        Ok("Product Skill added")
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
