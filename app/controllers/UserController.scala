package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/6/16.
 *
 * Model:
 *
 * id: long
 * role_id: long (Points to Role)
 *
 */
object UserController extends Controller {

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
