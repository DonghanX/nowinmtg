package com.donghanx.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.donghanx.navigation.navkey.DialogNavKey
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

private data object TestStartTopLevelRoute : NavKey

private data object TestSecondTopLevelRoute : NavKey

private data object TestThirdTopLevelRoute : NavKey

private data object TestFirstChildRoute : NavKey

private data object TestSecondChildRoute : NavKey

private data class TestDetailRoute(val id: Int) : NavKey

private data object TestDialogRoute : DialogNavKey

class NavigationTest {

    private lateinit var navigationState: NavigationState
    private lateinit var navigator: Navigator

    @Before
    fun setup() {
        val topLevelStack = NavBackStack<NavKey>(TestStartTopLevelRoute)
        val topLevelRoutes =
            listOf(TestStartTopLevelRoute, TestSecondTopLevelRoute, TestThirdTopLevelRoute)
        val backStacks = topLevelRoutes.associateWith { key -> NavBackStack(key) }

        navigationState =
            NavigationState(
                startRoute = TestStartTopLevelRoute,
                topLevelRouteStack = topLevelStack,
                backStacks = backStacks,
            )

        navigator = Navigator(navigationState)
    }

    @Test
    fun topLevelRoute_onInit_isStartRoute() {
        navigationState.topLevelRoute shouldBe TestStartTopLevelRoute
    }

    @Test
    fun topLevelRouteAndCurrentRoute_afterNavigatingToChildRoute_updatesCorrectly() {
        navigator.navigate(TestFirstChildRoute)

        with(navigationState) {
            topLevelRoute shouldBe TestStartTopLevelRoute
            currentRoute shouldBe TestFirstChildRoute
        }
    }

    @Test
    fun currentRouteIgnoringDialog_afterNavigatingToDialogRoute_updatesCorrectly() {
        with(navigator) {
            navigate(TestFirstChildRoute)
            navigate(TestDialogRoute)
        }

        with(navigationState) {
            topLevelRoute shouldBe TestStartTopLevelRoute
            currentRoute shouldBe TestDialogRoute
            currentRouteIgnoringDialog shouldBe TestFirstChildRoute
        }
    }

    @Test
    fun backStacks_afterNavigatingToTopLevelRoute_updatesCorrectly() {
        val expectedBackStacks =
            mapOf(
                TestStartTopLevelRoute to NavBackStack(TestStartTopLevelRoute, TestFirstChildRoute),
                TestSecondTopLevelRoute to NavBackStack(TestSecondTopLevelRoute),
                TestThirdTopLevelRoute to NavBackStack(TestThirdTopLevelRoute),
            )

        with(navigationState) {
            navigator.navigate(TestFirstChildRoute)

            navigator.navigate(TestSecondTopLevelRoute)
            topLevelRoute shouldBe TestSecondTopLevelRoute
            currentRoute shouldBe TestSecondTopLevelRoute

            navigator.navigate(TestThirdTopLevelRoute)
            topLevelRoute shouldBe TestThirdTopLevelRoute
            currentRoute shouldBe TestThirdTopLevelRoute

            backStacks shouldBe expectedBackStacks
        }
    }

    @Test
    fun backStacks_afterNavigatingToCurrentTopLevelRoute_clearsAllRoutesExceptForTheTopLevelRoute() {
        with(navigator) {
            navigate(TestSecondTopLevelRoute)
            navigate(TestSecondChildRoute)
            navigate(TestSecondTopLevelRoute)
        }

        with(navigationState) {
            topLevelRoute shouldBe TestSecondTopLevelRoute
            currentStack shouldContainExactly listOf(TestSecondTopLevelRoute)
        }
    }

    @Test
    fun currentBackStack_afterNavigatingToRouteAlreadyInBackStack_maintainSingleTop() {
        with(navigator) {
            navigate(TestFirstChildRoute)
            navigate(TestFirstChildRoute)
            navigate(TestSecondChildRoute)
            navigate(TestFirstChildRoute)
        }

        navigationState.currentStack shouldContainExactly
            listOf(TestStartTopLevelRoute, TestSecondChildRoute, TestFirstChildRoute)
    }

    @Test
    fun topLevelRouteStack_afterNavigatingToSameTopLevelRoute_maintainSingleTop() {
        with(navigator) {
            navigate(TestSecondTopLevelRoute)
            navigate(TestThirdTopLevelRoute)
            navigate(TestSecondTopLevelRoute)
        }

        navigationState.topLevelRouteStack shouldContainExactly
            listOf(TestStartTopLevelRoute, TestThirdTopLevelRoute, TestSecondTopLevelRoute)
    }

    @Test
    fun currentBackStack_afterNavigatingToStartRoute_clearsAllOtherTopLevelRoutes() {
        with(navigator) {
            navigate(TestSecondTopLevelRoute)
            navigate(TestStartTopLevelRoute)
        }

        navigationState.topLevelRouteStack shouldContainExactly listOf(TestStartTopLevelRoute)
    }

    @Test
    fun currentBackStack_afterGoingBackFromChildRoute_removesChildOnly() {
        with(navigator) {
            navigate(TestSecondTopLevelRoute)
            navigate(TestSecondChildRoute)
            goBack()
        }

        with(navigationState) {
            currentStack shouldContainExactly listOf(TestSecondTopLevelRoute)
            topLevelRoute shouldBe TestSecondTopLevelRoute
        }
    }

    @Test
    fun currentBackStack_afterGoingBackToPreviousTopLevel_returnsToPreviousTopLevelStack() {
        with(navigator) {
            navigate(TestSecondTopLevelRoute)
            navigate(TestSecondChildRoute)
            goBack()
            goBack()
        }

        with(navigationState) {
            currentStack shouldContainExactly listOf(TestStartTopLevelRoute)
            topLevelRouteStack shouldContainExactly listOf(TestStartTopLevelRoute)
        }
    }

    @Test
    fun currentRouteIgnoringDialog_afterGoingBackFromDialog_returnsToUnderlyingRoute() {
        with(navigator) {
            navigate(TestSecondTopLevelRoute)
            navigate(TestDialogRoute)
            navigationState.currentRouteIgnoringDialog shouldBe TestSecondTopLevelRoute

            goBack()
        }

        with(navigationState) {
            currentStack shouldContainExactly listOf(TestSecondTopLevelRoute)
            currentRoute shouldBe TestSecondTopLevelRoute
            currentRouteIgnoringDialog shouldBe TestSecondTopLevelRoute
        }
    }

    @Test
    fun currentRoute_afterSwitchingTopLevelWithSharedRoute_retrievesCorrectId() {
        with(navigator) {
            // The Start Top-Level route -> Details Route 1
            navigate(TestDetailRoute(id = 1))

            // The 2nd Top-Level route -> one intermediate route -> Details Route 2
            navigate(TestSecondTopLevelRoute)
            navigate(TestFirstChildRoute)
            navigate(TestDetailRoute(id = 2))

            // The 3rd Top-Level route -> two intermediate routes -> Details Route 3
            navigate(TestThirdTopLevelRoute)
            navigate(TestFirstChildRoute)
            navigate(TestSecondChildRoute)
            navigate(TestDetailRoute(id = 3))
        }

        val expectedBackStacks =
            mapOf(
                TestStartTopLevelRoute to
                    NavBackStack(TestStartTopLevelRoute, TestDetailRoute(id = 1)),
                TestSecondTopLevelRoute to
                    NavBackStack(
                        TestSecondTopLevelRoute,
                        TestFirstChildRoute,
                        TestDetailRoute(id = 2),
                    ),
                TestThirdTopLevelRoute to
                    NavBackStack(
                        TestThirdTopLevelRoute,
                        TestFirstChildRoute,
                        TestSecondChildRoute,
                        TestDetailRoute(id = 3),
                    ),
            )

        with(navigationState) {
            backStacks shouldBe expectedBackStacks

            navigator.navigate(TestStartTopLevelRoute)
            currentRoute shouldBe TestDetailRoute(1)

            navigator.navigate(TestSecondTopLevelRoute)
            currentRoute shouldBe TestDetailRoute(2)

            navigator.navigate(TestThirdTopLevelRoute)
            currentRoute shouldBe TestDetailRoute(3)
        }
    }
}
