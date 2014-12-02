package models

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db._

case class HaUser(email: String, fullname: Option[String], isadmin: Boolean, password: String, fbid: Option[BigInt])

object HaUser {
  val simple = {
    get[String]("HaUser.email") ~
    get[Option[String]]("HaUser.fullname") ~
    get[Boolean]("HaUser.isadmin") ~
    get[String]("HaUser.password") ~
    get[Option[BigInt]]("HaUser.fbid") map {
      case email ~ fullname ~ isadmin ~ password ~ fbid => HaUser(email, fullname, isadmin, password, fbid)
    }
  }

  def findById(id: Long): Option[HaUser] = {
    DB.withConnection { implicit c =>
      SQL("select * from HaUser where id = {id}").on(
        'id -> id
      ).as(HaUser.simple.singleOpt)
    }
  }

  def findByEmail(email: String): Option[HaUser] = {
    DB.withConnection { implicit c =>
      SQL("select * from HaUser where email = {email}").on(
        'email -> email
      ).as(HaUser.simple.singleOpt)
    }
  }

  def findAll: Seq[HaUser] = {
    DB.withConnection { implicit c =>
      SQL("select * from HaUser").as(HaUser.simple *)
    }
  }

  def authenticate(email: String, password: String): Option[HaUser] = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select * from HaUser where
          email = {email} and password = {password}
        """
      ).on(
          'email -> email,
          'password -> password
        ).as(HaUser.simple.singleOpt)
    }
  }

  def create(user: HaUser): HaUser = {
    DB.withConnection { implicit c =>
      SQL(
        """
          insert into HaUser (
            email, fullname, isadmin, password, fbid
          )
          values (
            {email}, {fullname}, {isadmin}, {password}, {fbid}
          )
        """
      ).on(
          'email -> user.email,
          'fullname -> Option(user.fullname),
          'isadmin -> user.isadmin,
          'password -> user.password,
          'fbid -> Option(user.fbid)
        ).executeUpdate()

      user
    }
  }
}