package models

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db._

case class HaUser(email: String, name: String, password: String)

object HaUser {
  val simple = {
    get[String]("HaUser.email") ~
    get[String]("HaUser.name") ~
    get[String]("HaUser.password") map {
      case email ~ name ~ password => HaUser(email, name, password)
    }
  }

  def findById(id: Long): Option[HaUser] = {
    DB.withConnection { implicit c =>
      SQL("select * from HaUser where hauser_id = {id}").on(
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
            email, name, password
          )
          values (
            {email}, {name}, {password}
          )
        """
      ).on(
          'email -> user.email,
          'name -> user.name,
          'password -> user.password
        ).executeUpdate()

      user
    }
  }
}