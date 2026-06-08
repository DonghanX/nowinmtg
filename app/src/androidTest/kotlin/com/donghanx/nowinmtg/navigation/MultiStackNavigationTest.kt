package com.donghanx.nowinmtg.navigation

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.donghanx.navigation.NavigationState
import com.donghanx.navigation.Navigator
import com.donghanx.navigation.toDecoratedEntries
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// TODO: use Robolectric to run Android tests inside a simulated Android environment inside a JVM
class MultiStackNavigationTest {

    @get:Rule val composeTestRule = createComposeRule()

    private fun <T> runOnIdle(action: () -> T): T = composeTestRule.runOnIdle(action)

    private lateinit var navigationState: NavigationState
    private lateinit var navigator: Navigator

    @Before
    fun setup() {
        val topLevelRoutes = listOf(TestStartTopLevelRoute, TestSecondTopLevelRoute)
        val backStacks = topLevelRoutes.associateWith { key -> NavBackStack(key) }

        navigationState =
            NavigationState(
                startRoute = TestStartTopLevelRoute,
                topLevelRouteStack = NavBackStack(TestStartTopLevelRoute),
                backStacks = backStacks,
            )

        navigator = Navigator(state = navigationState)
    }

    @Test
    fun scopedViewModel_afterTopLevelRouteSwap_areIsolatedAndRetained() {
        var firstDetailsViewModel: TestScopedViewModel? = null
        var secondDetailsViewModel: TestScopedViewModel? = null

        composeTestRule.setContent {
            val entryProvider = entryProvider {
                entry<TestStartTopLevelRoute> {}
                entry<TestSecondTopLevelRoute> {}
                entry<TestDetailsRoute> { route ->
                    val viewModel = viewModel<TestScopedViewModel>()
                    when (route.parentRoute) {
                        is TestStartTopLevelRoute -> {
                            firstDetailsViewModel = viewModel
                        }
                        is TestSecondTopLevelRoute -> {
                            secondDetailsViewModel = viewModel
                        }
                    }
                }
            }

            NavDisplay(
                entries = navigationState.toDecoratedEntries(entryProvider),
                onBack = { navigator.goBack() },
            )
        }

        runOnIdle { navigator.navigate(TestStartTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRoute(TestStartTopLevelRoute)) }

        val firstDetailsViewModelNotNull = runOnIdle { firstDetailsViewModel.shouldNotBeNull() }
        runOnIdle { firstDetailsViewModelNotNull.isCleared shouldBe false }

        runOnIdle { navigator.navigate(TestSecondTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRoute(TestSecondTopLevelRoute)) }

        val secondDetailsViewModelNotNull = runOnIdle { secondDetailsViewModel.shouldNotBeNull() }

        firstDetailsViewModelNotNull shouldNotBeEqual secondDetailsViewModelNotNull
        runOnIdle { firstDetailsViewModelNotNull.isCleared shouldBe false }

        runOnIdle { navigator.navigate(TestStartTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRoute(TestStartTopLevelRoute)) }

        runOnIdle { secondDetailsViewModelNotNull.isCleared shouldBe false }
        firstDetailsViewModel shouldBe firstDetailsViewModelNotNull
    }

    @Test
    fun scopedViewModel_afterTopLevelRouteSwapWithoutUniqueKey_areIsolatedAndPersist() {
        var detailsViewModel: TestScopedViewModel? = null

        composeTestRule.setContent {
            val entryProvider = entryProvider {
                entry<TestStartTopLevelRoute> {}
                entry<TestSecondTopLevelRoute> {}
                entry<TestDetailsRouteWithoutUniqueKey> {
                    detailsViewModel = viewModel<TestScopedViewModel>()
                }
            }

            NavDisplay(
                entries = navigationState.toDecoratedEntries(entryProvider),
                onBack = { navigator.goBack() },
            )
        }

        runOnIdle { navigator.navigate(TestStartTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRouteWithoutUniqueKey) }

        val firstDetailsViewModelNotNull = runOnIdle { detailsViewModel.shouldNotBeNull() }

        runOnIdle { navigator.navigate(TestSecondTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRouteWithoutUniqueKey) }

        val secondDetailsViewModelNotNull = runOnIdle { detailsViewModel.shouldNotBeNull() }

        firstDetailsViewModelNotNull shouldNotBeEqual secondDetailsViewModelNotNull
    }

    @Test
    fun scopedViewModel_afterGoBackFromDetailsRoute_isCleared() {
        var detailsViewModel: TestScopedViewModel? = null
        composeTestRule.setContent {
            val entryProvider = entryProvider {
                entry<TestStartTopLevelRoute> {}
                entry<TestSecondTopLevelRoute> {}
                entry<TestDetailsRoute> { detailsViewModel = viewModel<TestScopedViewModel>() }
            }

            NavDisplay(
                entries = navigationState.toDecoratedEntries(entryProvider),
                onBack = { navigator.goBack() },
            )
        }

        runOnIdle { navigator.navigate(TestStartTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRoute(TestStartTopLevelRoute)) }

        val firstDetailsViewModelNotNull = runOnIdle { detailsViewModel.shouldNotBeNull() }

        runOnIdle { navigator.goBack() }
        runOnIdle { firstDetailsViewModelNotNull.isCleared shouldBe true }

        runOnIdle { navigator.navigate(TestSecondTopLevelRoute) }
        runOnIdle { navigator.navigate(TestDetailsRoute(TestSecondTopLevelRoute)) }

        val secondDetailsViewModelNotNull = runOnIdle { detailsViewModel.shouldNotBeNull() }

        runOnIdle { navigator.goBack() }
        runOnIdle { secondDetailsViewModelNotNull.isCleared shouldBe true }
    }
}

private data object TestStartTopLevelRoute : NavKey

private data object TestSecondTopLevelRoute : NavKey

private data class TestDetailsRoute(val parentRoute: NavKey) : NavKey

private data object TestDetailsRouteWithoutUniqueKey : NavKey

internal class TestScopedViewModel : ViewModel() {
    var isCleared = false

    override fun onCleared() {
        isCleared = true
    }
}
