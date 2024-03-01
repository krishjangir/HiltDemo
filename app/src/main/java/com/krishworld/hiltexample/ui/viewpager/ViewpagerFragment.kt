package com.krishworld.hiltexample.ui.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentViewpagerBinding
import com.krishworld.hiltexample.ui.viewpager.adapter.ViewPagerAdapter
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel
import com.krishworld.hiltexample.ui.viewpager.viewholder.ViewPagerVideosViewHolder
import com.krishworld.hiltexample.ui.viewpager.viewmodel.ViewPagerViewModel
import com.krishworld.hiltexample.utils.Extensions.delayedGone
import com.krishworld.hiltexample.utils.Extensions.gone
import com.krishworld.hiltexample.utils.Utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ViewpagerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appContext: Context
    private lateinit var viewPagerViewModel: ViewPagerViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var videoMovie: ViewPagerUiModel.VideosUiModel? = null
    var prevVideoHolder: ViewPagerVideosViewHolder? = null
    private var videoPlayer: ExoPlayer? = null
    private var playWhenReady = true
    private var playbackPosition = 0L
    private var currentItem = 0

    private var _binding: FragmentViewpagerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize viewPagerViewModel
        viewPagerViewModel =
            ViewModelProvider(this, viewModelFactory)[ViewPagerViewModel::class.java]
        //Initialize viewPagerAdapter
        viewPagerAdapter = ViewPagerAdapter(viewPagerViewModel)
        binding.viewPager.adapter = viewPagerAdapter
        //Initialize Video Player
        videoPlayer = ExoPlayer.Builder(appContext).build()
        //------------Handle UI Content here------------
        viewPagerViewModel.videoContentLiveData.observe(viewLifecycleOwner) {
            viewPagerAdapter.submitList(it)
        }

        binding.viewPager.offscreenPageLimit = 3
        // Set up OnPageChangeCallback for handling videos
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val holder =
                    (binding.viewPager[0] as RecyclerView).findViewHolderForAdapterPosition(position)
                playVideo(holder, position)
            }
        })

        //-----------Handle ViewPager events here----------
        viewPagerViewModel.launchEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is ViewPagerEvent.PlayVideo -> playVideo()
                    is ViewPagerEvent.PauseVideo -> pauseVideo()
                    is ViewPagerEvent.PlayPauseVideo -> playPauseVideo()
                }.exhaustive
            }
        }
    }

    private fun playPauseVideo() {
        with(prevVideoHolder?.binding) {
            videoPlayer?.let {
                if (it.isPlaying) {
                    prevVideoHolder?.binding?.exoPause?.delayedGone()
                } else {
                    prevVideoHolder?.binding?.exoPlay?.delayedGone()
                }
            }
        }
    }

    private fun playVideo() {
        with(prevVideoHolder?.binding) {
            prevVideoHolder?.binding?.exoPlay?.gone()
            prevVideoHolder?.binding?.exoPause?.delayedGone()
            videoPlayer?.play()
        }
    }

    private fun pauseVideo() {
        with(prevVideoHolder?.binding) {
            prevVideoHolder?.binding?.exoPlay?.delayedGone()
            prevVideoHolder?.binding?.exoPause?.gone()
            videoPlayer?.pause()
        }
    }

    private fun playVideo(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        viewHolder as ViewPagerVideosViewHolder?
        with(viewHolder?.binding) {
            this?.videoItemViewGroup?.animation =
                AnimationUtils.loadAnimation(viewHolder?.itemView?.context, R.anim.alpha)
            prevVideoHolder?.let { it.binding.videoView.player = null }
            prevVideoHolder = viewHolder
            prevVideoHolder?.binding?.exoPlay?.gone()
            videoMovie = viewPagerAdapter.currentList[position] as ViewPagerUiModel.VideosUiModel
            videoPlayer?.let { exoPlayer ->
                val mediaItem = videoMovie?.videos?.videoUrl?.let { MediaItem.fromUri(it) }
                if (mediaItem != null) {
                    exoPlayer.setMediaItem(mediaItem)
                    this?.videoView?.player = exoPlayer
                    exoPlayer.seekTo(currentItem, playbackPosition)
                    exoPlayer.playWhenReady = playWhenReady
                    exoPlayer.prepare()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        prevVideoHolder?.let { it.binding.videoView.player = null }
        videoPlayer?.pause()
        videoPlayer = null
        _binding = null
    }
}