package models

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db._

case class HaUser(email: String, fullname: Option[String], password: String)

object HaUser {
  val simple = {
    get[String]("HaUser.email") ~
    get[Option[String]]("HaUser.fullname") ~
    get[String]("HaUser.password") map {
      case email ~ fullname ~ password => HaUser(email, fullname, password)
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
            email, fullname, password
          )
          values (
            {email}, {fullname}, {password}
          )
        """
      ).on(
          'email -> user.email,
          'fullname -> Option(user.fullname),
          'password -> user.password
        ).executeUpdate()

      user
    }
  }
}