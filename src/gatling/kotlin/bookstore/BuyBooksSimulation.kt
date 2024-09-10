package bookstore

import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*

/**
 * Simulation for the bookstore application which simulates the use case of
 * the user logging in and purchasing 4 books
 */
class BuyBooksSimulation : Simulation() {

  val users = Integer.getInteger("users", 1)
  val duration: Long = java.lang.Long.getLong("duration", 5L)
  val csvFileName: String = System.getProperty("csvFileName", "all_users.csv")
  val feeder = csv(csvFileName).queue()
  val baseUrl = "http://145.108.225.7:8765"

  private val authToken = exec(
    feed(feeder),
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

  private val getProduct1 = exec(
    http("Get Product 1")
      .get("/api/catalog/product/1")
      .check(
        status().`is`(200)
      )
  )

  private val getReviewProduct1 = exec(
    http("Get Review Product 1")
      .get("/api/catalog/review?productId=1")
      .check(
        status().`is`(200)
      )
  )

  private val addProduct1ToCart = exec(
    http("Add Product 1 to Cart")
      .post("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
            "productId": "1",
            "quantity": 1
          }"""
      ))
      .check(
        status().`is`(200)
      )
  )

  private val addProduct1ToCartOptionsRequest = exec(
    http("Add Product 1 to Cart OPTIONS")
      .options("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val getCart1 = exec(
    http("Get Cart 1")
      .get("/api/order/cart")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val optionsGetCart1= exec(
    http("Options Cart 1")
      .options("/api/order/cart")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val getProduct10 = exec(
    http("Get Product 10")
      .get("/api/catalog/product/10")
      .check(
        status().`is`(200)
      )
  )

  private val getReviewProduct10 = exec(
    http("Get Review Product 10")
      .get("/api/catalog/review?productId=10")
      .check(
        status().`is`(200)
      )
  )

  private val addProduct10ToCart = exec(
    http("Add Product 10 to Cart")
      .post("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
            "productId": "10",
            "quantity": 1
          }"""
      ))
      .check(
        status().`is`(200)
      )
  )

  private val addProduct10ToCartOptionsRequest = exec(
    http("Add Product 10 to Cart OPTIONS")
      .options("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val getProduct15 = exec(
    http("Get Product 15")
      .get("/api/catalog/product/15")
      .check(
        status().`is`(200)
      )
  )

  private val getReviewProduct15 = exec(
    http("Get Review Product 15")
      .get("/api/catalog/review?productId=15")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val addProduct15ToCart = exec(
    http("Add Product 15 to Cart")
      .post("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
            "productId": "15",
            "quantity": 1
          }"""
      ))
      .check(
        status().`is`(200)
      )
  )

  private val addProduct15ToCartOptionsRequest = exec(
    http("Add Product 15 to Cart OPTIONS")
      .options("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val getProduct12 = exec(
    http("Get Product 12")
      .get("/api/catalog/product/12")
      .check(
        status().`is`(200)
      )
  )

  private val reviewGetProduct12 = exec(
    http("Get Review Product 12")
      .get("/api/catalog/review?productId=12")
      .check(
        status().`is`(200)
      )
  )

  private val addProduct12ToCart = exec(
    http("Add Product 12 to Cart")
      .post("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
            "productId": "12",
            "quantity": 1
          }"""
      ))
      .check(
        status().`is`(200)
      )
  )

  private val addProduct12ToCartOptionsRequest = exec(
    http("Add Product 12 to Cart OPTIONS")
      .options("/api/order/cart/cartItem")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val getBillingAddress = exec(
    http("Get Billing Address")
      .get("/api/billing/address")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val optionsGetBillingAddress = exec(
    http("Options Get Billing Address")
      .options("/api/billing/address")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val postBillingAddress = exec(
    http("Post Billing Address")
      .post("/api/billing/address")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
          "addressLine1": "De Boelelaan 1105",
          "addressLine2": "VU Amsterdam",
          "city": "Amsterdam",
          "state": "North Holland",
          "postalCode": "1081 HV",
          "country": "NL",
          "phone": "123456789"
        }"""
      ))
      .check(
        status().`is`(201)
      )
  )

  private val getBillingAddressExtractAddressIds = exec(
    http("Get Billing Address")
      .get("/api/billing/address")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200),
        jsonPath("$[0].addressId").saveAs("billingAddressId")
      )
  )

  private val getPaymentMethod = exec(
    http("Get Payment Method")
      .get("/api/payment/paymentMethod")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val optionsGetPaymentMethod = exec(
    http("Options Get Payment Method")
      .options("/api/payment/paymentMethod")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val postPaymentMethod = exec(
    http("Post Payment Method")
      .post("/api/payment/paymentMethod")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
          "card": {
            "cardNumber": "4111111111111111",
            "expirationMonth": "10",
            "expirationYear": "28",
            "cvv": "123"
          }
        }"""
      ))
      .check(
        status().`is`(201)
      )
  )

  private val getPaymentMethodExtractPaymentId = exec(
    http("Get Payment Method")
      .get("/api/payment/paymentMethod")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200),
        jsonPath("$[0].paymentMethodId").saveAs("paymentMethodId")
      )
  )

  private val postOrderPreview = exec { session ->
    val billingAddressId = session.getString("billingAddressId")
    val paymentMethodId = session.getString("paymentMethodId")
    session.set("billingAddressId", billingAddressId)
    session.set("paymentMethodId", paymentMethodId)
  }.exec(
    http("Post Preview Order")
      .post("/api/order/previewOrder")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
          "billingAddressId":"#{billingAddressId}",
          "shippingAddressId": "#{billingAddressId}",
          "paymentMethodId": "#{paymentMethodId}"
        }"""
      ))
      .check(
        status().`is`(200)
      )
  )

  private val optionsOrderPreview = exec(
    http("Options Preview Order")
      .options("/api/order/previewOrder")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200) // Adjust if you need a different status code check
      )
  )

  private val postOrder = exec(
    http("Post Order")
      .post("/api/order/order")
      .header("Authorization", "Bearer #{userAuthToken}")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """{
          "billingAddressId":"#{billingAddressId}",
          "shippingAddressId": "#{billingAddressId}",
          "paymentMethodId": "#{paymentMethodId}"
        }"""
      ))
      .check(
        status().`is`(200),
        jsonPath("$.orderId").saveAs("orderId")
      )
  )

  private val optionsOrder = exec(
    http("Options Order")
      .options("/api/order/order")
      .header("Authorization", "Bearer #{userAuthToken}") // Include bearer token if required
      .check(
        status().`is`(200) // Check if the response status is 200 OK
      )
  )

  private val getOrder = exec { session ->
    val orderId = session.getString("orderId")
    session.set("orderId", orderId) // Ensure orderId is available in the session
  }.exec(
    http("Get Order")
      .get("/api/order/order/#{orderId}")
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200)
      )
  )

  private val optionsGetOrder = exec(
    http("Options Get Order")
      .options("/api/order/order/#{orderId}") // Use the saved orderId in the URL
      .header("Authorization", "Bearer #{userAuthToken}")
      .check(
        status().`is`(200) // Adjust the expected status code as needed
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
    authToken,
    pause(1),
    optionsAuthRequest,
    userInfoRequest,
    pause(1),
    optionsUserInfoRequest,
    productsRequest,
    pause(1),
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    getProduct1,
    pause(1),
    getProduct1,
    getReviewProduct1,
    pause(1),
    getReviewProduct1,
    imageRequest1,
    imageRequest1,
    imageRequest1,
    addProduct1ToCart,
    pause(1),
    addProduct1ToCartOptionsRequest,
    getCart1,
    optionsGetCart1,
    getProduct1,
    pause(1),
    imageRequest1,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    productsRequest,
    pause(1),
    imageRequest1,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    getProduct10,
    getProduct10,
    getReviewProduct10,
    getReviewProduct10,
    imageRequest3,
    imageRequest3,
    imageRequest3,
    getProduct10,
    addProduct10ToCart,
    addProduct10ToCartOptionsRequest,
    imageRequest1,
    getCart1,
    optionsGetCart1,
    getProduct1,
    getProduct10,
    imageRequest1,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    productsRequest,
    imageRequest1,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    getProduct15,
    getProduct15,
    getReviewProduct15,
    getReviewProduct15,
    imageRequest7,
    imageRequest7,
    imageRequest7,
    getProduct10,
    getProduct1,
    pause(1),
    addProduct15ToCart,
    pause(1),
    addProduct15ToCartOptionsRequest,
    imageRequest2,
    imageRequest1,
    getCart1,
    pause(1),
    optionsGetCart1,
    getProduct10,
    getProduct1,
    getProduct15,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    productsRequest,
    imageRequest1,
    imageRequest2,
    imageRequest3,
    imageRequest4,
    imageRequest5,
    imageRequest6,
    imageRequest7,
    imageRequest8,
    getProduct12,
    getProduct12,
    reviewGetProduct12,
    pause(1),
    reviewGetProduct12,
    imageRequest4,
    imageRequest4,
    imageRequest4,
    getProduct10,
    getProduct1,
    getProduct12,
    addProduct12ToCart,
    pause(1),
    addProduct12ToCartOptionsRequest,
    imageRequest1,
    imageRequest2,
    imageRequest7,
    getCart1,
    optionsGetCart1,
    getProduct12,
    getProduct10,
    getProduct1,
    getProduct15,
    imageRequest4,
    getBillingAddress,
    optionsGetBillingAddress,
    postBillingAddress,
    pause(1),
    getBillingAddressExtractAddressIds,
    optionsGetBillingAddress,
    getBillingAddress,
    optionsGetBillingAddress,
    getPaymentMethod,
    optionsGetPaymentMethod,
    postPaymentMethod,
    pause(1),
    optionsGetPaymentMethod,
    getPaymentMethodExtractPaymentId,
    optionsGetPaymentMethod,
    postOrderPreview,
    pause(1),
    optionsOrderPreview,
    getProduct12,
    getProduct10,
    getProduct1,
    getProduct15,
    imageRequest2,
    imageRequest4,
    imageRequest7,
    imageRequest1,
    postOrder,
    pause(1),
    optionsOrder,
    postOrderPreview,
    pause(1),
    getOrder,
    optionsOrderPreview,
    optionsGetOrder,
    getProduct12,
    getProduct10,
    getProduct1,
    getProduct15,
    imageRequest2,
    imageRequest4,
    imageRequest7,
    imageRequest1
  )

  init {
    setUp(
      sign_up.injectOpen(rampUsers(users).during(duration))
    ).protocols(httpProtocol)
  }
}