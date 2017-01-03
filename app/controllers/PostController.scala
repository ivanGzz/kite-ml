package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 *
 * id: long
 * user_id: long (points to User)
 * team_id: long (points to Team)
 * product_id = long (points to Product)
 * post_type: String
 * comment: String
 *
 */
object PostController extends Controller {

    def get = Action {
        Ok
    }

    def post = Action {
        Ok
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
