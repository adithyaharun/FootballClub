package com.adithyaharun.footballclub

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.ApiRepository
import com.adithyaharun.footballclub.UI.DetailView
import com.adithyaharun.footballclub.UI.Presenter.DetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var presenter: DetailPresenter
    private lateinit var mainLayout: LinearLayout
    private lateinit var eventDate: TextView
    private lateinit var homeName: TextView
    private lateinit var homeImage: ImageView
    private lateinit var homeScore: TextView
    private lateinit var awayName: TextView
    private lateinit var awayImage: ImageView
    private lateinit var awayScore: TextView
    private lateinit var homeYellowCard: TextView
    private lateinit var awayYellowCard: TextView
    private lateinit var homeRedCard: TextView
    private lateinit var awayRedCard: TextView
    private lateinit var homeGoal: TextView
    private lateinit var awayGoal: TextView

    private var event: Event? = null
    private var homeTeam: Team? = null
    private var awayTeam: Team? = null

    private val sqlFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val humanFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get event ID.
        val idEvent = intent.getStringExtra("id")

        // Set title bar and back button.
        supportActionBar?.setTitle(R.string.match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup layout.
        setupLayout()

        // Load data.
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getEvent(idEvent)
    }

    private fun setupLayout() {
        mainLayout = linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            eventDate = textView {
                textColor = R.color.colorAccent
                textSize = 14f
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }.lparams(width = matchParent, height = wrapContent)
            eventDate.setPadding(dip(16), dip(16), dip(16), dip(4))

            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                // Team
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 3f)
                        orientation = LinearLayout.VERTICAL

                        homeImage = imageView {
                            adjustViewBounds = true
                        }.lparams(width = matchParent, height = 160)

                        homeName = textView {
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                        }.lparams(width = matchParent, height = wrapContent)
                    }

                    homeScore = textView {
                        gravity = Gravity.CENTER_VERTICAL
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textSize = 18f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams(width = 0, height = matchParent, weight = 2f)

                    textView {
                        text = context.getString(R.string.versus)
                        gravity = Gravity.CENTER_VERTICAL
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    awayScore = textView {
                        gravity = Gravity.CENTER_VERTICAL
                        textSize = 18f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams(width = 0, height = matchParent, weight = 2f)

                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 3f)
                        orientation = LinearLayout.VERTICAL

                        awayImage = imageView {
                            adjustViewBounds = true
                        }.lparams(width = matchParent, height = 160)

                        awayName = textView{
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                        }.lparams(width = matchParent, height = wrapContent)
                    }
                }.setPadding(dip(16), dip(4), dip(16), dip(16))

                // Border
                view {
                    backgroundColor = Color.LTGRAY
                }.lparams(matchParent, 1)
            }

            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                // Yellow Cards
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(16)

                    homeGoal = textView {
                        textColor = Color.GRAY
                        textSize = 10f
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    textView {
                        textColor = R.color.colorAccent
                        text = context.getString(R.string.goals)
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    awayGoal = textView {
                        textColor = Color.GRAY
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams(width = 0, height = matchParent, weight = 1f)
                }

                // Border
                view {
                    backgroundColor = Color.LTGRAY
                }.lparams(matchParent, 1)
            }

            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                // Yellow Cards
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(16)

                    homeYellowCard = textView {
                        textColor = Color.GRAY
                        textSize = 10f
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    textView {
                        textColor = R.color.colorAccent
                        text = context.getString(R.string.yellow_cards)
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    awayYellowCard = textView {
                        textColor = Color.GRAY
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams(width = 0, height = matchParent, weight = 1f)
                }

                // Border
                view {
                    backgroundColor = Color.LTGRAY
                }.lparams(matchParent, 1)
            }

            linearLayout {
                // Red Cards
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(16)

                    homeRedCard = textView {
                        textColor = Color.GRAY
                        textSize = 10f
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    textView {
                        textColor = R.color.colorAccent
                        text = context.getString(R.string.red_cards)
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    awayRedCard = textView {
                        textColor = Color.GRAY
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams(width = 0, height = matchParent, weight = 1f)
                }

                // Border
                view {
                    backgroundColor = Color.LTGRAY
                }.lparams(matchParent, 1)
            }
        }
    }

    private fun bindData() {
        presenter.getTeam(event?.idAwayTeam, "home")
        presenter.getTeam(event?.idHomeTeam, "away")

        val matchDate = sqlFormatter.parse(event?.dateEvent)
        eventDate.text = humanFormatter.format(matchDate)

        homeName.text = event?.strHomeTeam
        homeRedCard.text = event?.strHomeRedCards?.replace(";", "\n")?.replace("':", "': ")
        homeYellowCard.text = event?.strHomeYellowCards?.replace(";", "\n")?.replace("':", "': ")
        homeGoal.text = event?.strHomeGoalDetails?.replace(";", "\n")?.replace("':", "': ")

        if (event?.intHomeScore == null) {
            homeScore.visibility = View.GONE
        } else {
            homeScore.text = event?.intHomeScore.toString()
        }

        awayName.text = event?.strAwayTeam
        awayRedCard.text = event?.strAwayRedCards?.replace(";", "\n")?.replace("':", "': ")
        awayYellowCard.text = event?.strAwayYellowCards?.replace(";", "\n")?.replace("':", "': ")
        awayGoal.text = event?.strAwayGoalDetails?.replace(";", "\n")?.replace("':", "': ")

        if (event?.intAwayScore == null) {
            awayScore.visibility = View.GONE
        } else {
            awayScore.text = event?.intAwayScore.toString()
        }
    }

    private fun bindTeam(type: String) {
        if (type == "home") {
            Picasso.get().load(homeTeam?.teamBadge).into(homeImage)
        } else {
            Picasso.get().load(awayTeam?.teamBadge).into(awayImage)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun showEvent(data: Event) {
        if (data.idEvent == null) {
            mainLayout.visibility = View.GONE
            toast(getString(R.string.event_not_found))

            return
        }

        event = data
        bindData()
    }

    override fun showTeam(data: Team, type: String) {
        if (type == "home") {
            homeTeam = data
        } else {
            awayTeam = data
        }

        bindTeam(type)
    }
}
