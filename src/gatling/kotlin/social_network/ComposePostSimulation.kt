package social_network

import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*

/**
 * Simulation for the social network application which simulates the process
 * of posting tweets with mentions of 10 users in the tweet
 */
class ComposePostSimulation : Simulation() {

    val feeder = csv("all_users.csv").random()
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
                status().`is`(200)
            )
    )

    private val getMainHtml = exec { session ->
        val username = session.getString("username")
        println("currrent_user: " + username)
        session
    }.exec(
        http("Check Main Page")
            .get("/main.html?username=#{username}")
            .check(
                status().`is`(304)
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
                status().`is`(200)
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
                status().`is`(200)
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
                status().`is`(200)
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
                status().`is`(200)
            )
    )

    private val indexHtml1 = exec(
        http("read indexHtml_mark")
            .get("/index.html")
            .check(
                status().`is`(304)
            )
    )

    private val indexHtml2 = exec(
        http("read indexHtml_elon")
            .get("/index.html")
            .check(
                status().`is`(304)
            )
    )

    private val indexHtml3 = exec(
        http("read indexHtml_bill")
            .get("/index.html")
            .check(
                status().`is`(304)
            )
    )

    private val profileHtml = exec(
        http("read profileHtml")
            .get("/profile.html")
            .check(
                status().`is`(200)
            )
    )

    private val mainCss1 = exec(
        http("read mainCss")
            .get("/style/main.css")
            .check(
                status().`is`(304)
            )
    )

    private val createPostJs2 = exec(
        http("read createPostJs 2")
            .get("/javascript/create-post.js")
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

    private val userTimelineRead2 = exec(
        http("read userTimeline 2")
            .get("/api/user-timeline/read?start=0&stop=100")
            .check(
                status().`is`(200)
            )
    )

    private val getMediaFile1 = exec(
        http("get media file")
            .get("http://145.108.225.7:9234/get-media/?filename=546626863406298496.jpg")
            .check(
                status().`is`(200)
            )
    )

    private val getMediaFile2 = exec(
        http("get media file 2")
            .get("http://145.108.225.7:9234/get-media/?filename=444882374174501888.jpg")
            .check(
                status().`is`(200)
            )
    )

    private val uploadMedia = exec(
        http("upload media")
            .post("http://145.108.225.7:9234/upload-media")
            .bodyPart(RawFileBodyPart("file", "/Users/ishas.kulkarni/Downloads/jmeter2.jpg").fileName("jmeter2.jpg"))
            .check(
                status().`is`(200)
            )
    )

    val composePose = exec(
        http("Post Compose")
            .post("/api/post/compose")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("post_type", "0")
            .formParam("text", "@ajpcv @mhkmq @ngavs @htgix @ikqdw @yhkkf @benpt @rbzup @zbkta @velex @kwuyy @nnlkh @aikoc @qkwtl @dbrdb @czurm @rndly @kdtjz @gklbu Blorpwizzle drangleflomp, skizzlequark womblefizz yinglebot snoodlefrap drabblefloop. Crinklestop flibberwomp zibblefop wogglewump, splindleflap tazzlewomp gribbleplunk snoodlepuff. Glonkyplum snafflewhisk jibbletronk flizzlewhirp, wibblegorf cranglepuff zizzlefrap skrodlebunk. Drunglewarp crimpleflamp dinklestop snizzlequint, scrumblefizz wibblewhap trogglebump womblewarp snozzledonk. Pifflewump skizzleflorp blorpwibble, cringleflump glonkyplum zifflecronk dronklewarp wibbleglomp. Flibblefump drangleboop wibblewhirp, zizzlefrap gromplequink crinklestop snorfle trazzlequack. Splindleflap yarp quibbledonk womp snizzleplop, glorp tazzlewomp snozzleplunk drangleflorp crunkleflimp. Zizzlewhirp womplequack dribblefump snoodlewarp cringleflump, yinglebot glonkyplunk snozzledonk blorp snorfleflimp. Wibblewhomp dronkleplunk flibblefump snoodlewarp tazzlewomp, snizzleplonk womplefizz drabblefloop zizzlefrap skizzleflomp. Blorp cranglefizz drunglewarp, splindleflap womplequack trogglebump zizzleplorp snoodleflap skrodlebunk.")
            .formParam("media_ids", """["166928897285494368"]""")
            .formParam("media_types", """["jpg"]""")
            .check(
                status().`is`(500)
            )
    )

    private val profileHtml2 = exec(
        http("read profileHtml 2")
            .get("/profile.html")
            .check(
                status().`is`(304)
            )
    )

    val getMainCss2 = exec(
        http("Read main.css 2")
            .get("/style/main.css")
            .check(
                status().`is`(304)
            )
    )

    private val createPostJs3 = exec(
        http("read createPostJs 3")
            .get("/javascript/create-post.js")
            .check(
                status().`is`(304)
            )
    )

    private val timelineJs3 = exec(
        http("read timelineJs 3")
            .get("/javascript/timeline.js")
            .check(
                status().`is`(304)
            )
    )

    private val userTimelineRead3 = exec(
        http("read userTimeline 3")
            .get("/api/user-timeline/read?start=0&stop=100")
            .check(
                status().`is`(200)
            )
    )

    private val getMediaFile3 = exec(
        http("get media file 3")
            .get("http://145.108.225.7:9234/get-media/?filename=546626863406298496.jpg")
            .check(
                status().`is`(200)
            )
    )

    private val getMediaFile4 = exec(
        http("get media file 4")
            .get("http://145.108.225.7:9234/get-media/?filename=444882374174501888.jpg")
            .check(
                status().`is`(200)
            )
    )

    private val getMediaFile5 = exec(
        http("get media file 5")
            .get("http://145.108.225.7:9234/get-media/?filename=864003499383025792.jpg")
            .check(
                status().`is`(200)
            )
    )

    private val profileHtml3 = exec(
        http("read profileHtml 3")
            .get("/profile.html")
            .check(
                status().`is`(304)
            )
    )

    val getMainCss3 = exec(
        http("Read main.css 3")
            .get("/style/main.css")
            .check(
                status().`is`(304)
            )
    )

    private val createPostJs4 = exec(
        http("read createPostJs 4")
            .get("/javascript/create-post.js")
            .check(
                status().`is`(304)
            )
    )

    private val timelineJs4 = exec(
        http("read timelineJs 4")
            .get("/javascript/timeline.js")
            .check(
                status().`is`(304)
            )
    )

    private val userTimelineRead4 = exec(
        http("read userTimeline 4")
            .get("/api/user-timeline/read?start=0&stop=100")
            .check(
                status().`is`(200)
            )
    )

    private val indexHtml4 = exec(
        http("read indexHtml_mark")
            .get("/index.html")
            .check(
                status().`is`(304)
            )
    )

    val getLoginCss = exec(
        http("Read login.css")
            .get("/style/login.css")
            .check(
                status().`is`(200)
            )
    )

    val httpProtocol =
        http.baseUrl(baseUrl)
            .acceptHeader("text/plain, application/x-www-form-urlencoded")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
            )

    val compose_post = scenario("Compose Image Post").exec(userLogin,
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
        profileHtml,
        mainCss1,
        createPostJs2,
        timelineJs2,
        userTimelineRead2,
        getMediaFile1,
        getMediaFile2,
        uploadMedia,
        composePose,
        profileHtml2,
        getMainCss2,
        createPostJs3,
        timelineJs3,
        userTimelineRead3,
        getMediaFile3,
        getMediaFile4,
        getMediaFile5,
        profileHtml3,
        getMainCss3,
        createPostJs4,
        timelineJs4,
        userTimelineRead4,
        indexHtml4,
        getLoginCss)

    init {
        setUp(
            compose_post.injectOpen(rampUsers(28000).during(300))
        ).protocols(httpProtocol)
    }
}