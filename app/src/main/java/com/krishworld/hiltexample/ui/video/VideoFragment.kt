package com.krishworld.hiltexample.ui.video

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentVideoBinding
import com.krishworld.hiltexample.ui.video.adapter.VideoListAdapter
import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import com.krishworld.hiltexample.ui.video.viewholder.VideosViewHolder
import com.krishworld.hiltexample.ui.video.viewmodel.VideoViewModel
import com.krishworld.hiltexample.utils.Extensions.delayedGone
import com.krishworld.hiltexample.utils.Extensions.gone
import com.krishworld.hiltexample.utils.Extensions.visible
import com.krishworld.hiltexample.utils.Utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appContext: Context
    private lateinit var videoViewModel: VideoViewModel
    private var _binding: FragmentVideoBinding? = null
    private lateinit var videoListAdapter: VideoListAdapter
    private var videoMovie: MainVideoUiModel.VideosUiModel? = null
    private var prevVideoHolder: VideosViewHolder? = null
    private var prevVideoHolderPos = -1
    private var videoPlayer: ExoPlayer? = null
    private var playWhenReady = true
    private var playbackPosition = 0L
    private var currentItem = 0

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewModel = ViewModelProvider(this, viewModelFactory)[VideoViewModel::class.java]
        binding.viewmodel = videoViewModel
        videoListAdapter = VideoListAdapter(videoViewModel)
        binding.videoRv.adapter = videoListAdapter
        //Initialize Video Player
        videoPlayer = ExoPlayer.Builder(appContext).build()
        //------------Handle UI Content here------------
        videoViewModel.videoContentLiveData.observe(viewLifecycleOwner) {
            videoListAdapter.submitList(it)
        }

        //-----------Handle ViewPager events here----------
        videoViewModel.launchEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is VideoEvent.PlayVideo -> playVideo(navigation.video)
                    is VideoEvent.PauseVideo -> pauseVideo()
                    is VideoEvent.PlayPauseVideo -> playPauseVideo()
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

    private fun playVideo(video: MainVideoUiModel.VideosUiModel) {
        val position = videoListAdapter.currentList.indexOf(video)
        if (position != -1 && prevVideoHolderPos != position) {
            playVideo(position)
        } else {
            with(prevVideoHolder?.binding) {
                prevVideoHolder?.binding?.exoPlay?.gone()
                prevVideoHolder?.binding?.exoPause?.delayedGone()
                videoPlayer?.play()
            }
        }
    }

    private fun playVideo(position: Int) {
        val viewHolder =
            binding.videoRv.findViewHolderForAdapterPosition(position) as VideosViewHolder?
        with(viewHolder?.binding) {
            this?.videoItemViewGroup?.animation =
                AnimationUtils.loadAnimation(viewHolder?.itemView?.context, R.anim.alpha)
            prevVideoHolder?.let {
                it.binding.videoView.player = null
                it.binding.imgVideo.visible()
                it.binding.exoPlay.visible()
                it.binding.videoView.gone()
            }
            prevVideoHolder = viewHolder
            prevVideoHolderPos = position
            prevVideoHolder?.binding?.exoPlay?.gone()
            prevVideoHolder?.binding?.imgVideo?.gone()
            prevVideoHolder?.binding?.videoView?.visible()
            videoMovie = videoListAdapter.currentList[position] as MainVideoUiModel.VideosUiModel
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

    private fun pauseVideo() {
        with(prevVideoHolder?.binding) {
            prevVideoHolder?.binding?.exoPlay?.delayedGone()
            prevVideoHolder?.binding?.exoPause?.gone()
            videoPlayer?.pause()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        prevVideoHolder?.let { it.binding.videoView.player = null }
        prevVideoHolderPos = -1
        videoPlayer?.pause()
        videoPlayer = null
        _binding = null
    }
}