package com.adithyaharun.footballclub

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.adithyaharun.footballclub.Misc.database
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.ApiRepository
import com.adithyaharun.footballclub.UI.DetailView
import com.adithyaharun.footballclub.UI.Presenter.DetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
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

    private var detailMenu: Menu? = null
    private var event: Event? = null
    private var homeTeam: Team? = null
    private var awayTeam: Team? = null
    private var addedToFavorites: Boolean = false

    private val sqlFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val humanFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get event ID.
        event = intent.getParcelableExtra("event")

        // Set title bar and back button.
        supportActionBar?.setTitle(R.string.match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup layout.
        setupLayout()

        // Initialize presenter.
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        // Bind event data.
        bindData()
        setFavoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_match, menu)

        val menuFavorites = menu.findItem(R.id.navigation_add_to_favorites)

        if (addedToFavorites) {
            menuFavorites.setIcon(R.drawable.ic_star_black_24dp)
        } else {
            menuFavorites.setIcon(R.drawable.ic_star_border_black_24dp)
        }

        detailMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun addToFavorites(): Boolean {
        try {
            database.use {
                insert(Event.TABLE_EVENT_FAVORITES,
                        Event.EVENT_ID to event?.idEvent,
                        Event.EVENT_DATE to event?.dateEvent,
                        Event.EVENT_HOME_TEAM_ID to event?.idHomeTeam,
                        Event.EVENT_AWAY_TEAM_ID to event?.idAwayTeam,
                        Event.EVENT_HOME_TEAM to event?.strHomeTeam,
                        Event.EVENT_AWAY_TEAM to event?.strAwayTeam,
                        Event.EVENT_HOME_SCORE to event?.intHomeScore,
                        Event.EVENT_AWAY_SCORE to event?.intAwayScore,
                        Event.EVENT_HOME_GOALS to event?.strHomeGoalDetails,
                        Event.EVENT_AWAY_GOALS to event?.strAwayGoalDetails,
                        Event.EVENT_HOME_YELLOW_CARDS to event?.strHomeYellowCards,
                        Event.EVENT_AWAY_YELLOW_CARDS to event?.strAwayYellowCards,
                        Event.EVENT_HOME_RED_CARDS to event?.strHomeRedCards,
                        Event.EVENT_AWAY_RED_CARDS to event?.strAwayRedCards
                )
            }

            toast(R.string.added_to_favorites)
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }

        return true
    }

    private fun removeFromFavorites(): Boolean {
        try {
            database.use {
                delete(Event.TABLE_EVENT_FAVORITES,
                        "EVENT_ID = {id}",
                        "id" to event?.idEvent!!
                )
            }
            toast(R.string.removed_from_favorites)
        } catch (e: SQLiteConstraintException) {
            toast(R.string.removed_from_favorites)
        }
        return true
    }

    private fun setFavoriteState() {
        database.use {
            select("FAVORITE_EVENTS", "EVENT_ID")
                    .whereArgs("EVENT_ID = {id}", "id" to event?.idEvent.toString())
                    .exec {
                        val parsed = parseOpt(StringParser)

                        addedToFavorites = (parsed != null)
                        onPrepareOptionsMenu(detailMenu)
                    }
        }
    }

    private fun setupLayout() {
        mainLayout = linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            eventDate = textView {
                textColor = R.color.colorAccent
                textSize = 14f
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }.lparams(width = matchParent, height = wrapContent)
            eventDate.setPadding(dip(16), dip(16), dip(16), dip(4))

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
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

                        awayName = textView {
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
                lparams(width = matchParent, height = wrapContent)
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
                lparams(width = matchParent, height = wrapContent)
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
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.navigation_add_to_favorites -> {
                if (addedToFavorites) {
                    item.setIcon(R.drawable.ic_star_border_black_24dp)
                    removeFromFavorites()
                } else {
                    item.setIcon(R.drawable.ic_star_black_24dp)
                    addToFavorites()
                }

                addedToFavorites = !addedToFavorites

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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
