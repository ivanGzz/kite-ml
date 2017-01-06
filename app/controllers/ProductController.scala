package controllers

import models.{AuditLog, Product}
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
 * product_type: String
 * project_id: long (points to Project)
 * product_order: long
 * points: long
 *
 */
object ProductController extends Controller {

    implicit val productRead = Json.reads[Product]
    implicit val productWrite = Json.writes[Product]

    def get = Action.async {
        Product.getProducts.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(product, _) =>
                AuditLog.addToLog(request.uri, product.toString).flatMap(res =>
                    Product.addToProducts(product).map(res =>
                        Ok("Product added")
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
