package controllers

import models.HaUser
import anorm._
import play.api.Play.current
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.DB
import play.api.i18n.Messages
import play.api.mvc._

import views._

object Application extends Controller with Secured {

//  def index = Action {
////    selectTest
//    Ok(views.html.index("Your new application is ready."))
//  }

  def selectTest = {
    DB.withConnection { implicit c =>
      val result: Boolean = SQL("Select 1").execute()
      Logger.debug("a")
    }
  }

  // ログインフォーム
  val loginForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => HaUser.authenticate(email, password).isDefined
    })
  )

  // トップページ
//  def index = IsAuthenticated { email => _ =>
//    Ok(views.html.index("Your new application is ready.", email))
//  }
  def index = Action { implicit request =>
    Ok(html.index("Your new application is ready."))
  }

  // ログインページ
  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  // ユーザ認証
  //
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.Application.index).withSession("email" -> user._1)
    )
  }

  // ログアウト
  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> Messages("views.logout.logged_out")
    )
  }

  // 登録フォーム
  val signupForm = Form(
    tuple(
      "email" -> nonEmptyText.verifying(
        "This email address is already registered.",
        email => HaUser.findByEmail(email).isEmpty
      ),
      "name" -> nonEmptyText,
      "password" -> tuple(
        "main" -> nonEmptyText,
        "confirm" -> nonEmptyText
      ).verifying(
        "Password is not match.",
        password => password._1 == password._2
      )
    )
  )

  // ユーザ登録ページ
  def signup = Action { implicit request =>
    Ok(html.signup(signupForm))
  }

  // ユーザ登録
  def register = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      errors => BadRequest(html.signup(errors)),
      form => {
        val user = HaUser(form._1, form._2, form._3._1)
        HaUser.create(user)
        Ok(html.registered(user)).withSession("email" -> user.email)
      }
    )
  }
}