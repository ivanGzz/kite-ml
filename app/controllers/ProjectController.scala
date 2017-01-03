package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * author_id: long (points to User)
 * project_type_id: long (points to Project Type)
 * parent_project_id: long (points to Project)
 * teams_count: long
 * team_member_count: long
 *
 */
object ProjectController extends Controller {

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
