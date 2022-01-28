package com.example.sdk

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sdk.FaceDetectionActivityRobot.assertActivityIsFinished
import com.example.sdk.FaceDetectionActivityRobot.assertButtonBackContentDescriptionIsCorrect
import com.example.sdk.FaceDetectionActivityRobot.assertButtonTextIsCorrect
import com.example.sdk.FaceDetectionActivityRobot.assertCameraNotLoaded
import com.example.sdk.FaceDetectionActivityRobot.assertCameraWasLoaded
import com.example.sdk.FaceDetectionActivityRobot.assertErrorProcessingFaces
import com.example.sdk.FaceDetectionActivityRobot.assertErrorTakingPicture
import com.example.sdk.FaceDetectionActivityRobot.assertMoreThaOneFaceFound
import com.example.sdk.FaceDetectionActivityRobot.assertNoFaceFound
import com.example.sdk.FaceDetectionActivityRobot.assertOnlyOneFaceFound
import com.example.sdk.FaceDetectionActivityRobot.assertPictureWasTaken
import com.example.sdk.FaceDetectionActivityRobot.assertTextViewDescriptionIsCorrect
import com.example.sdk.FaceDetectionActivityRobot.clickBack
import com.example.sdk.FaceDetectionActivityRobot.clickTakePicture
import com.example.sdk.FaceDetectionActivityRobot.mockErrorLoadingCamera
import com.example.sdk.FaceDetectionActivityRobot.mockErrorProcessingFaces
import com.example.sdk.FaceDetectionActivityRobot.mockErrorTakingPicture
import com.example.sdk.FaceDetectionActivityRobot.mockFaceListener
import com.example.sdk.FaceDetectionActivityRobot.mockProcessingManyFaces
import com.example.sdk.FaceDetectionActivityRobot.mockProcessingNoFaces
import com.example.sdk.FaceDetectionActivityRobot.mockProcessingOneFace
import com.example.sdk.FaceDetectionActivityRobot.mockSuccessLoadingCamera
import com.example.sdk.FaceDetectionActivityRobot.mockSuccessTakingPicture
import com.example.sdk.FaceDetectionActivityRobot.mockUseCaseFactory
import com.example.sdk.presentation.face.FaceDetectionActivity
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.R], instrumentedPackages = ["androidx.loader.content"])
class FaceDetectionActivityTest {
    private lateinit var scenario: ActivityScenario<FaceDetectionActivity>

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
        mockFaceListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //mock face picture to be taken by camera
            mockSuccessTakingPicture(activity)
            mockProcessingOneFace()
            //click on takePicture button
            clickTakePicture(activity)
            //assert listener was called
            assertPictureWasTaken()
        }
    }

    @Test
    fun verifyBackButtonWorks() {
        //set listener to activity companion object var
        mockFaceListener()
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
        mockFaceListener()
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
        mockFaceListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingOneFace()
            //try take a picture
            clickTakePicture(activity)
            //verify if picture was taken and correctly processed
            assertPictureWasTaken()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenTakePictureFails() {
        //set listener to activity companion object var
        mockFaceListener()
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
    fun verifyIfListenerIsCalledWhenProcessFaceFails() {
        //set listener to activity companion object var
        mockFaceListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockErrorProcessingFaces()
            //try take a picture
            clickTakePicture(activity)
            //verify if error was called on listener
            assertErrorProcessingFaces()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenProcessOneFace() {
        //set listener to activity companion object var
        mockFaceListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingOneFace()
            //try take a picture
            clickTakePicture(activity)
            //verify if only one face was caught
            assertOnlyOneFaceFound()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenProcessManyFaces() {
        //set listener to activity companion object var
        mockFaceListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingManyFaces()
            //try take a picture
            clickTakePicture(activity)
            //verify if more than one face was caught
            assertMoreThaOneFaceFound()
        }
    }

    @Test
    fun verifyIfListenerIsCalledWhenNoFacesAreFound() {
        //set listener to activity companion object var
        mockFaceListener()
        //prepare camera to be loaded successfully
        mockSuccessLoadingCamera()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            //verify if camera was loaded
            assertCameraWasLoaded()
            //mock result of camera
            mockSuccessTakingPicture(activity)
            mockProcessingNoFaces()
            //try take a picture
            clickTakePicture(activity)
            //verify if zero faces was caught
            assertNoFaceFound()
        }
    }

}