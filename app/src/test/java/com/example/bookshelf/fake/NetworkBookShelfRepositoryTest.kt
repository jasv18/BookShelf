package com.example.bookshelf.fake

import com.example.bookshelf.data.repository.NetworkBookShelfRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkBookShelfRepositoryTest {
    @Test
    fun networkBookShelfRepository_getPhotos_verifyPhotoList() = runTest {
        val repository = NetworkBookShelfRepository(
            service = FakeBookShelfApiService()
        )
        assertEquals(
            FakeDataSource.bookshelfResponse.docs,
            repository.getBookShelf("","").docs
        )
    }
}