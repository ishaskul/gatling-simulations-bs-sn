package bookstore

import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*

/**
 * Simulation for the bookstore application which simulates the process of
 * registraion of about 10k users onto the bookstore application
 */
class UserRegistrationSimulation : Simulation() {

  val feeder = csv("all_users.csv").queue()
  val baseUrl = "http://145.108.225.7:8765"

  private val userSignUp = exec(
    feed(feeder),
    http("Sign up request")
      .post("http://145.108.225.7:8765/api/account/signup") // URL from the curl command
      .header("Content-Type", "application/json") // Setting the content type as JSON
      .body(StringBody(
        """{
            "grant_type": "password",
            "userName": "#{username}",
            "password": "#{password}",
            "firstName": "#{firstname}",
            "email": "#{firstname}.#{lastname}@example.com"
            }"""
      ))
      .check(
        status().`is`(201)
      )
  )

  private val optionsSignup = exec(
    http("signup - options")
      .options("http://145.108.225.7:8765/api/account/signup") // Specify the URL
      .header("Content-Type", "application/json") // Set headers if needed
      .check(
        status().`is`(200) // Optionally, check if the response status is 200
      )
  )

  private val authToken = exec(
    http("Auth Token")
      .post("http://145.108.225.7:8765/api/account/oauth/token")
      .header("Content-Type", "application/x-www-form-urlencoded")
      .basicAuth("93ed453e-b7ac-4192-a6d4-c45fae0d99ac", "client.devd123")
      .formParam("grant_type", "password")
      .formParam("username", "#{username}")
      .formParam("password", "#{password}")
      .check(
        status().`is`(200),
        jsonPath("$.access_token").saveAs("userAuthToken")
      )
  ).exec { session ->
    val token = session.getString("userAuthToken")
    session
  }

  private val optionsAuthRequest = exec(
    http("OPTIONS request oauth/token")
      .options("http://145.108.225.7:8765/api/account/oauth/token") // OPTIONS request URL
      .check(
        status().`is`(200)
      )
  )

  private val userInfoRequest = exec(
    http("Get User Info")
      .get("http://145.108.225.7:8765/api/account/userInfo")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val optionsUserInfoRequest = exec(
    http("OPTIONS request Get User Info")
      .options("http://145.108.225.7:8765/api/account/userInfo")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )
  private val productsRequest = exec(
    http("Get Products")
      .get("http://145.108.225.7:8765/api/catalog/products?page=0&size=8")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200) // Check if the response status is 200 OK
      )
  )

  private val imageRequest1 = exec(
    http("Get Image 1")
      .get("/api/catalog/image/c16108e1-8276-41ad-bf12-4aee4c0a7e65__IndModernLiterature.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest2 = exec(
    http("Get Image 2")
      .get("/api/catalog/image/27ba1db0-a5a3-477c-927c-7ceb36cf7ffa__theTimeMachine.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest3 = exec(
    http("Get Image 3")
      .get("/api/catalog/image/ad5b1926-05ad-4aae-852d-f152ea838648__programmingInJava.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest4 = exec(
    http("Get Image 4")
      .get("/api/catalog/image/af75f363-fdb9-4d8e-8066-6215f0520e9b__corePythonProg.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest5 = exec(
    http("Get Image 5")
      .get("/api/catalog/image/25550dae-a893-4aa5-8b8f-967a91548fa5__oopWithCpp.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest6 = exec(
    http("Get Image 6")
      .get("/api/catalog/image/6a290abe-338f-4ece-8793-ccdd35a7745f__linux.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest7 = exec(
    http("Get Image 7")
      .get("/api/catalog/image/cad39d84-60aa-496d-9295-c3f47ec957fa__programmingInC.jpg")
      .check(
        status().`is`(200)
      )
  )

  private val imageRequest8 = exec(
    http("Get Image 8")
      .get("/api/catalog/image/e9b319cc-e501-4f90-b608-74052d770e2d__theBusinessBook.jpg")
      .check(
        status().`is`(200)
      )
  )

  val httpProtocol =
    http.baseUrl(baseUrl)
      .acceptHeader("text/plain, application/x-www-form-urlencoded, application/json")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader(
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
      )

  val sign_up = scenario("Sign up on bookstore").exec(
    userSignUp,
    optionsSignup,
    authToken,
    optionsAuthRequest,
    userInfoRequest,
    optionsUserInfoRequest,
    productsRequest,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8
  )

  init {
    setUp(
      sign_up.injectOpen(rampUsers(1).during(5))
    ).protocols(httpProtocol)
  }
}