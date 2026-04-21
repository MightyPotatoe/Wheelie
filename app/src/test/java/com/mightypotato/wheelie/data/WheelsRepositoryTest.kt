package com.mightypotato.wheelie.data

import com.mightypotato.wheelie.data.local.Wheel
import com.mightypotato.wheelie.data.local.WheelDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Unit test for [WheelsRepository].
 *
 * This test uses Mockito to mock the [WheelDao], allowing us to test the repository's
 * logic in isolation without needing a real database.
 */
class WheelsRepositoryTest {

    private lateinit var wheelDao: WheelDao
    private lateinit var repository: WheelsRepository

    @Before
    fun setUp() {
        wheelDao = mock(WheelDao::class.java)
        repository = WheelsRepository(wheelDao)
    }

    @Test
    fun getWheels_mapsWheelEntitiesToNames() = runTest {
        // Arrange
        val wheels = listOf(
            Wheel(id = 1, name = "Front Wheel", owner = "Me"),
            Wheel(id = 2, name = "Rear Wheel", owner = "You")
        )
        `when`(wheelDao.getAllWheels()).thenReturn(flowOf(wheels))

        // Act
        val result = repository.getWheels().first()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Front Wheel", result[0])
        assertEquals("Rear Wheel", result[1])
    }

    @Test
    fun insertWheel_callsDao() = runTest {
        // Arrange
        val wheel = Wheel(name = "New Wheel", owner = "Owner")
        `when`(wheelDao.insertWheel(wheel)).thenReturn(1L)

        // Act
        val result = repository.insertWheel(wheel)

        // Assert
        verify(wheelDao).insertWheel(wheel)
        assertEquals(1L, result)
    }

    @Test
    fun deleteWheelByName_callsDao() = runTest {
        // Arrange
        val name = "Target Wheel"

        // Act
        repository.deleteWheelByName(name)

        // Assert
        verify(wheelDao).deleteWheelByName(name)
    }
}
