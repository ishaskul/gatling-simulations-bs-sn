package social_network

import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*

/**
 * Simulation for the social network application which simulates 10k users, each following a set of 10 users
 */
class FollowUsersSimulation : Simulation() {

    val users = Integer.getInteger("users", 1)
    val duration: Long = java.lang.Long.getLong("duration", 5L)
    val csvFileName: String = System.getProperty("csvFileName", "all_users_25k.csv")
    val feeder = csv(csvFileName).random()
    val baseUrl = "http://145.108.225.7:8080"

    private val userLogin = exec(
        feed(feeder),
        http("Login Request")
            .post("/api/user/login")
            .header("Content-Type", "text/plain") // Set the content type for the request
            .header("Cookie", "login_token=")
            .formParam("username", "#{username}")
            .formParam("password", "#{password}")
            .formParam("login", "Login")
            .check(
                status().`in`(200, 302)
            )
    )

    private val getMainHtml = exec { session ->
        val username = session.getString("username")
        session
    }.exec(
        http("Check Main Page")
            .get("/main.html?username=#{username}")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getMainCss = exec(
        http("Check Main Css")
            .get("/style/main.css")
            .check(
                status().`is`(200)
            )
    )

    private val getCreatePostJs = exec(
        http("create post.js")
            .get("/javascript/create-post.js")
            .check(
                status().`is`(200)
            )
    )

    private val getTimelineJs = exec(
        http("get-timeline.js")
            .get("/javascript/timeline.js")
            .check(
                status().`is`(200)
            )
    )

    private val getContactJs = exec(
        http("get-contact.js")
            .get("/javascript/contact.js")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getDefaultUserJs = exec(
        http("get-default-user.js")
            .get("/javascript/default_user.js")
            .check(
                status().`is`(200)
            )
    )

    private val readHomeTimeLine = exec(
        http("read homeTimeline")
            .get("/api/home-timeline/read?start=0&stop=100")
            .check(
                status().`is`(200)
            )
    )

    private val registerMarkZuckerberg = exec(
        http("Register Mark Zuckerberg")
            .post("/api/user/register")
            .header("Content-Type", "text/plain") // Set the content type for the request
            .formParam("username", "mark")
            .formParam("first_name", "Mark")
            .formParam("last_name", "Zuckerberg")
            .formParam("password", "123")
            .check(
                status().`in`(200, 302)
            )
    )

    private val registerElonMusk  = exec(
        http("Register Elon Musk")
            .post("/api/user/register")
            .header("Content-Type", "text/plain") // Set the content type for the request
            .formParam("username", "elon")
            .formParam("first_name", "Elon")
            .formParam("last_name", "Musk")
            .formParam("password", "123")
            .check(
                status().`in`(200, 302)
            )
    )

    private val registerBillGates  = exec(
        http("Register Bill Gates")
            .post("/api/user/register")
            .header("Content-Type", "text/plain") // Set the content type for the request
            .formParam("username", "bill")
            .formParam("first_name", "Bill")
            .formParam("last_name", "Gates")
            .formParam("password", "123")
            .check(
                status().`in`(200, 302)
            )
    )

    private val indexHtml1 = exec(
        http("read indexHtml_mark")
            .get("/index.html")
            .check(
                status().`in`(200, 304)
            )
    )

    private val indexHtml2 = exec(
        http("read indexHtml_elon")
            .get("/index.html")
            .check(
                status().`in`(200, 304)
            )
    )

    private val indexHtml3 = exec(
        http("read indexHtml_bill")
            .get("/index.html")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getContactHtml = exec(
        http("get-contact.html")
            .get("/contact.html")
            .check(
                status().`in`(200, 304)
            )
    )

    private val mainCss1 = exec(
        http("read mainCss")
            .get("/style/main.css")
            .check(
                status().`is`(304)
            )
    )

    private val timelineJs2 = exec(
        http("read timelineJs 2")
            .get("/javascript/timeline.js")
            .check(
                status().`is`(304)
            )
    )

    private val getFollowerJs = exec(
        http("get-follower.js")
            .get("/javascript/get-follower.js")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getFolloweeJs = exec(
        http("get-followee.js")
            .get("/javascript/get-followee.js")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getContactJs2 = exec(
        http("get-contact.js")
            .get("/javascript/contact.js")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getButtonClickJs = exec(
        http("get-button-click.js")
            .get("/javascript/button-click.js")
            .check(
                status().`in`(200, 304)
            )
    )

    private val getFollowerApi = exec(
        http("get-follower-api")
            .get("/api/user/get_follower")
            .check(
                status().`in`(200, 500)
            )
    )

    private val getFolloweeApi = exec(
        http("get-followee-api")
            .get("/api/user/get_followee")
            .check(
                status().`in`(200, 500)
            )
    )

    private val followUser1  = exec(
        http("follow user")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "bqqbf")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser2  = exec(
        http("follow user 2")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "xfvio")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser3  = exec(
        http("follow user 3")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "jldiz")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser4  = exec(
        http("follow user 4")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "toeoo")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser5  = exec(
        http("follow user 5")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "pecny")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser6  = exec(
        http("follow user 6")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "cueql")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser7  = exec(
        http("follow user 7")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "eerko")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser8  = exec(
        http("follow user 8")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "ahddk")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser9  = exec(
        http("follow user 9")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "bqaho")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )

    private val followUser10  = exec(
        http("follow user 10")
            .post("/api/user/follow")
            .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type for the request
            .formParam("followee_name", "nuroh")
            .formParam("user_name", "#{username}")
            .check(
                status().`is`(302)
            )
    )


    private val unfollowUser1 = exec(
        http("unfollow user 1")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "bqqbf")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser2 = exec(
        http("unfollow user 2")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "xfvio")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser3 = exec(
        http("unfollow user 3")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "jldiz")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser4 = exec(
        http("unfollow user 4")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "toeoo")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser5 = exec(
        http("unfollow user 5")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "pecny")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser6 = exec(
        http("unfollow user 6")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "cueql")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser7 = exec(
        http("unfollow user 7")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "eerko")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser8 = exec(
        http("unfollow user 8")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "ahddk")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser9 = exec(
        http("unfollow user 9")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "bqaho")
            .check(
                status().`is`(200)
            )
    )

    private val unfollowUser10 = exec(
        http("unfollow user 10")
            .post("/api/user/unfollow")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("user_name", "#{username}")
            .formParam("followee_name", "nuroh")
            .check(
                status().`is`(200)
            )
    )


    val httpProtocol =
        http.baseUrl(baseUrl)
            .disableFollowRedirect()
            .acceptHeader("text/plain, application/x-www-form-urlencoded")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
            )

    val follow_10_users = scenario("Compose Image Post").exec(userLogin,
        getMainHtml,
        getMainCss,
        getCreatePostJs,
        getTimelineJs,
        getContactJs,
        getDefaultUserJs,
        readHomeTimeLine,
        registerMarkZuckerberg,
        registerElonMusk,
        registerBillGates,
        indexHtml1,
        indexHtml2,
        indexHtml3,
        getContactHtml,
        mainCss1,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,
        followUser1,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser2,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser3,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser4,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser5,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser6,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser7,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser8,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser9,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,

        followUser10,
        getContactHtml,
        timelineJs2,
        getFollowerJs,
        getFolloweeJs,
        getContactJs2,
        getButtonClickJs,
        getFollowerApi,
        getFolloweeApi,
        unfollowUser1,
        unfollowUser2,
        unfollowUser3,
        unfollowUser4,
        unfollowUser5,
        unfollowUser6,
        unfollowUser7,
        unfollowUser8,
        unfollowUser9,
        unfollowUser10
    )

    init {
        setUp(
            follow_10_users.injectOpen(rampUsers(users).during(duration))
        ).protocols(httpProtocol)
    }
}
