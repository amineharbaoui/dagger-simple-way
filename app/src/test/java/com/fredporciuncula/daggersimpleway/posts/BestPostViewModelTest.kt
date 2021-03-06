package com.fredporciuncula.daggersimpleway.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fredporciuncula.daggersimpleway.model.newPost
import com.fredporciuncula.daggersimpleway.service.PostsService
import com.google.common.truth.Truth.assertThat
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BestPostViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var postsService: PostsService

    @Mock private lateinit var bestPostFinder: BestPostFinder

    @InjectMocks private lateinit var viewModel: BestPostViewModel

    @Test fun `should emit whatever post is returned by the best post finder`() {
        val posts = listOf(newPost(id = 10), newPost(id = 20))
        given(postsService.posts()).willReturn(Single.just(posts))
        given(bestPostFinder.findBestPost(posts)).willReturn(posts.first())

        with(viewModel) {
            loadPosts()
            bestPost().observeForever {
                assertThat(it).isEqualTo(posts.first())
            }
        }
    }
}
