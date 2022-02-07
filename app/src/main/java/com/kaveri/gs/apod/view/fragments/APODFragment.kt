package com.kaveri.gs.apod.view.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.databinding.FragmentAPODBinding
import com.kaveri.gs.apod.model.pojo.APOD
import com.kaveri.gs.apod.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_a_p_o_d.*


/**
 * A simple [Fragment] subclass.
 * Use the [APODFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class APODFragment : Fragment(), APODFragmentActionListener {

    private var adsLoader: ImaAdsLoader? = null
    private var player: ExoPlayer? = null
    private var binding: FragmentAPODBinding? = null

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("onCreateView")
        return inflater.inflate(R.layout.fragment_a_p_o_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("onViewCreated")
        init(view)
        initObservers()
    }

    private fun initObservers() {
        viewModel.selectedDate.observe(viewLifecycleOwner, {
            viewModel.getAPOD()
        })
        viewModel.todaysApod.observe(viewLifecycleOwner, { apod ->
            apod?.let {
                initMedia(it)
                if (apod.fav) binding?.addToFavImg?.setBackgroundResource(R.drawable.ic_fav_selected)
                else binding?.addToFavImg?.setBackgroundResource(R.drawable.ic_fav_unselected)
            }
        })
        /*     val imageLoader = ImageLoader(context = requireContext())
             runBlocking {
                 withContext(Dispatchers.IO) {
                     val request = ImageRequest.Builder(requireContext())
                         .data("https://www.example.com/image.jpg")
                         .target { drawable ->
                             // Handle the result.
                         }
                         .build()
                     val imageResult = imageLoader.execute(request)
                     withContext(Dispatchers.Main) {
                         mApodImage.load(imageResult.drawable)
                     }
                 }
             }*/
    }

    private fun initMedia(apod: APOD) {
        if (apod.mediaType.equals("image")) {
            binding?.playerView?.visibility = View.GONE
            binding?.mApodImage?.apply {
                visibility = View.VISIBLE
                load(apod.url)
            }
        } else {
            binding?.mApodImage?.visibility = View.GONE
            binding?.playerView?.let { playerView ->
                playerView.visibility = View.VISIBLE
                initPlayer(apod.url ?: "")
             /*   val vidHolder = it.holder
                vidHolder.addCallback( object : SurfaceHolder.Callback {
                    override fun surfaceCreated(p0: SurfaceHolder) {
                        val mediaPlayer = MediaPlayer()
                        mediaPlayer.setDisplay(vidHolder)
                        //mediaPlayer.setDataSource(getEncodedUrl(apod.url))
                        mediaPlayer.setDataSource(requireContext(), Uri.parse(apod.url))
                        mediaPlayer.prepareAsync()
                        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                            override fun onPrepared(p0: MediaPlayer?) {
                                mediaPlayer.start()
                            }
                        })
                    }

                    private fun getEncodedUrl(urlStr: String?): String {
                        val url = URL(urlStr)
                        val uri = URI(url.protocol, url.userInfo, url.host, url.port, url.path, url.query, url.ref)
                        return uri.toASCIIString()
                    }

                    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun surfaceDestroyed(p0: SurfaceHolder) {
                    }

                })*/
            }
        }
    }


    private fun initPlayer(url: String) {
        adsLoader = ImaAdsLoader.Builder(requireContext()).build()
        val dataSrcFactory = DefaultDataSource.Factory(requireContext())

        val mediaSrcFactory = DefaultMediaSourceFactory(dataSrcFactory)
           /* .setAdsLoaderProvider(object : DefaultMediaSourceFactory.AdsLoaderProvider{
                override fun getAdsLoader(adsConfiguration: MediaItem.AdsConfiguration): AdsLoader? {
                    return adsLoader
                }
            })*/
            .setAdsLoaderProvider(){ adsLoader }
            .setAdViewProvider(playerView)

        player = ExoPlayer.Builder(requireContext())
            .setRenderersFactory(DefaultRenderersFactory(requireContext()))
            .setTrackSelector(DefaultTrackSelector())
            .setLoadControl(DefaultLoadControl())
            .setMediaSourceFactory(mediaSrcFactory).build()
        binding?.playerView?.player = player
        adsLoader?.setPlayer(player)

        val contentUri = Uri.parse(url)
        val mediaItem = MediaItem.Builder().setUri(contentUri).build()
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = false
    }

    private fun init(view: View) {
        println("onInit")
        binding = DataBindingUtil.bind(view)
        binding?.viewModel = viewModel
        binding?.actionListener = this
        binding?.setLifecycleOwner(requireActivity())
    }

    companion object {
        fun newInstance() = APODFragment()
    }

    override fun onDateSelectionClick() {
        viewModel.openDatePicker(this.parentFragmentManager)
    }

    override fun addToFav() {
        Toast.makeText(requireContext(), "Adding to fav", Toast.LENGTH_SHORT).show()
        println("addTofav clicked")
        viewModel.todaysApod.value?.let { apod ->
            println("adding to fav apod : ${apod.toString()}")
            apod.date?.let { date ->
                if (apod.fav == false)
                    viewModel.addToFav(date)
                else
                    viewModel.removeFromFav(date)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            if (binding != null && binding?.playerView != null) {
                binding?.playerView?.onPause();
            }
            releasePlayer();
        }
    }

    override fun onStop() {
        super.onStop()
        if(adsLoader != null) {
            adsLoader?.release()
        }
    }
    private fun releasePlayer() {
        adsLoader?.setPlayer(null)
        playerView.setPlayer(null)
        player?.release()
        player = null
    }

}