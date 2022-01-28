package com.example.sdk

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sdk.DocumentDetectionActivityRobot.assertActivityIsFinished
import com.example.sdk.DocumentDetectionActivityRobot.assertButtonBackContentDescriptionIsCorrect
import com.example.sdk.DocumentDetectionActivityRobot.assertButtonTextIsCorrect
import com.example.sdk.DocumentDetectionActivityRobot.assertCameraNotLoaded
import com.example.sdk.DocumentDetectionActivityRobot.assertCameraWasLoaded
import com.example.sdk.DocumentDetectionActivityRobot.assertErrorProcessingText
import com.example.sdk.DocumentDetectionActivityRobot.assertErrorTakingPicture
import com.example.sdk.DocumentDetectionActivityRobot.assertNoTextFound
import com.example.sdk.DocumentDetectionActivityRobot.assertPictureWasTaken
import com.example.sdk.DocumentDetectionActivityRobot.assertTextFound
import com.example.sdk.DocumentDetectionActivityRobot.assertTextViewDescriptionIsCorrect
import com.example.sdk.DocumentDetectionActivityRobot.clickBack
import com.example.sdk.DocumentDetectionActivityRobot.clickTakePicture
import com.example.sdk.DocumentDetectionActivityRobot.mockErrorLoadingCamera
import com.example.sdk.DocumentDetectionActivityRobot.mockErrorTakingPicture
import com.example.sdk.DocumentDetectionActivityRobot.mockDocumentListener
import com.example.sdk.DocumentDetectionActivityRobot.mockErrorProcessingText
import com.example.sdk.DocumentDetectionActivityRobot.mockProcessingNoTextFound
import com.example.sdk.DocumentDetectionActivityRobot.mockProcessingValidText
import com.example.sdk.DocumentDetectionActivityRobot.mockSuccessLoadingCamera
import com.example.sdk.DocumentDetectionActivityRobot.mockSuccessTakingPicture
import com.example.sdk.DocumentDetectionActivityRobot.mockUseCaseFactory
import com.example.sdk.presentation.document.DocumentDetectionActivity
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.R], instrumentedPackages = ["androidx.loader.content"])
class DocumentDetectionActivityTest {
    private lateinit var scenario: ActivityScenario<DocumentDetectionActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        mockUseCaseFactory()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun verifyTakePictureButtonWorks() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //mock text picture to be taken by camera
            mockSuccessTakingPicture(activity)
            //click on takePicture button
            clickTakePicture(activity)
            //assert listener was called
            assertPictureWasTaken()
        }
    }

    @Test
    fun verifyBackButtonWorks() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //click on back button
            clickBack(activity)
            //assert activity is finished
            assertActivityIsFinished(activity)
        }
    }

    @Test
    fun verifyIfTextsAreDisplayedCorrectly() {
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if texts are correct
            assertButtonBackContentDescriptionIsCorrect(activity)
            assertTextViewDescriptionIsCorrect(activity)
            assertButtonTextIsCorrect(activity)
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenCameraIsNotLoaded() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera error
        mockErrorLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if listener is called after camera fails
            assertCameraNotLoaded()
            //activity should finish after process picture (even throwing an error)
            assertActivityIsFinished(activity)
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenCameraIsLoaded() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingValidText()
            //try take a picture
            clickTakePicture(activity)
            //verify if picture was taken and correctly processed
            assertPictureWasTaken()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenTakePictureFails() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockErrorTakingPicture()
            //try take a picture
            clickTakePicture(activity)
            //verify if error was called on listener
            assertErrorTakingPicture()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenProcessTextFails() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockErrorProcessingText()
            //try take a picture
            clickTakePicture(activity)
            //verify if error was called on listener
            assertErrorProcessingText()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenProcessText() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingValidText()
            //try take a picture
            clickTakePicture(activity)
            //verify if text were caught
            assertTextFound()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenNoTextAreFound() {
        //set listener to activity companion object var
        mockDocumentListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingNoTextFound()
            //try take a picture
            clickTakePicture(activity)
            //verify if zero faces was caught
            assertNoTextFound()
        }
    }

}