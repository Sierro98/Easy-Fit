package ies.infantaelena.easy_fit_01.views

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.twotone.Cookie
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ies.infantaelena.easy_fit_01.MainActivity
import ies.infantaelena.easy_fit_01.R
import ies.infantaelena.easy_fit_01.model.Challenge
import ies.infantaelena.easy_fit_01.viewmodel.ChallengesViewModel

//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun ChallengeScreenPreview(challengesViewModel: ChallengesViewModel = viewModel()) {
//    val context = LocalContext.current
//    ChallengeBackground(context = context, challengesViewModel = challengesViewModel, mainActivity = main())
//}

@Composable
fun ChallengeScreen(
    navController: NavController,
    challengesViewModel: ChallengesViewModel = viewModel(),
    mainActivity: MainActivity
) {
    val context = LocalContext.current
    ChallengeBackground(
        context = context, challengesViewModel = challengesViewModel, mainActivity = mainActivity
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeBackground(
    context: Context,
    challengesViewModel: ChallengesViewModel,
    mainActivity: MainActivity
) {
    val list = mainActivity.user.challenges
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            val grouped = list?.groupBy { it.challengeType[0] }
            grouped?.forEach { (initial, challenge) ->
                stickyHeader {
                    Text(
                        text = "$initial",
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(start = 20.dp, top = 10.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp
                    )
                }
                items(challenge) { chall ->
                    ChallengeItems(
                        challenge = chall,
                        context = context,
                        challengesViewModel = challengesViewModel
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeItems(
    challenge: Challenge,
    context: Context,
    challengesViewModel: ChallengesViewModel
) {
    val iconoChallenge: Int = when (challenge.challengeType) {
        "RUN" -> R.drawable.running_man
        "WALK" -> R.drawable.walk_man
        "HIKING" -> R.drawable.hikin_man
        "CICLING" -> R.drawable.bicycle_man
        "CALISTHENICS" -> R.drawable.calisthenics
        "TEAM_SPORTS" -> R.drawable.team_sports
        else -> R.drawable.running_man
    }
    when (challenge.challengeType) {
        "RUN" -> challengesViewModel.tipoActividad =
            context.getString(R.string.activityRun)

        "WALK" -> challengesViewModel.tipoActividad =
            context.getString(R.string.activityWalk)

        "HIKING" -> challengesViewModel.tipoActividad =
            context.getString(R.string.activityHiking)

        "CICLING" -> challengesViewModel.tipoActividad =
            context.getString(R.string.activityCiclism)

        "CALISTHENICS" -> challengesViewModel.tipoActividad =
            context.getString(R.string.activityCalisthenics)

        "TEAM_SPORTS" -> challengesViewModel.tipoActividad =
            context.getString(R.string.activityTeamSport)
    }
    ListItem(icon = {
        Icon(
            painter = painterResource(id = iconoChallenge),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    },
        text = {
            Text(
                text = challengesViewModel.tipoActividad
            )
        },
        secondaryText = { Text(text = challenge.challengeContent) },
        trailing = {
            if (challenge.challengeComplete) {
                Icon(painter = painterResource(id = R.drawable.checked), contentDescription = null)
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.unchecked),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        modifier = Modifier.clickable(
            onClick = {
             challengesViewModel.completeChallenge(context = context)
            }
        )
    )
    Divider()
}

