package controllers

import play.api.mvc.{Controller, Action}

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 * id: long
 * published: boolean
 * class_project_id: long (points to Class Project)
 * project_id: long (points to Project)
 * avg_score: double
 * views_count: long
 * likes_count: long
 *
 */
object TeamController extends Controller {

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
