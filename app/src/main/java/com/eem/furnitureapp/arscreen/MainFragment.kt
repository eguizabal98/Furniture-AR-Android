package com.eem.furnitureapp.arscreen

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.*
import androidx.compose.ui.unit.dp
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.eem.furnitureapp.R
import com.eem.furnitureapp.loadViewRender
import com.eem.furnitureapp.model.Furniture
import com.eem.furnitureapp.ui.theme.FurnitureAppTheme
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider
import com.google.ar.core.Anchor
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.CursorNode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.utils.doOnApplyWindowInsets

class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var sceneView: ArSceneView
    lateinit var loadingView: View
    lateinit var actionButton: ExtendedFloatingActionButton
    lateinit var lessX: Slider
    lateinit var lessY: Slider
    lateinit var lessZ: Slider
    lateinit var composeView: ComposeView

    lateinit var cursorNode: CursorNode
    lateinit var modelNode: ArModelNode
    lateinit var layoutNode: ArModelNode

    private val furnitureList = listOf(
        Furniture("Sandy Chair", "4.5", "75.0", R.drawable.chair_a, "models/chair_a.glb"),
        Furniture("Big Chair", "5.0", "125.0", R.drawable.chair_b, "models/chair_b.glb"),
    )

    var isLoading = false
        set(value) {
            field = value
            loadingView.isGone = !value
            actionButton.isGone = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView = view.findViewById(R.id.compose_view)

        composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                FurnitureAppTheme {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize(), state = rememberLazyListState()
                    ) {
                        items(items = furnitureList) { furniture ->
                            FurnitureItem(
                                Modifier,
                                furniture
                            ) { passModel(furniture.modelPath) }
                        }
                    }
                }
            }
        }

        loadingView = view.findViewById(R.id.loadingView)

        lessX = view.findViewById<Slider>(R.id.pgb_x)
            .apply {
                addOnChangeListener { slider, value, fromUser ->
                    this@MainFragment.rotationX = value
                    lastAnchor?.let { anchor -> anchorOrMove(anchor) }
                }
            }
        lessY = view.findViewById<Slider>(R.id.pgb_y)
            .apply {
                addOnChangeListener { slider, value, fromUser ->
                    this@MainFragment.rotationY = value
                    lastAnchor?.let { anchor -> anchorOrMove(anchor) }
                }
            }
        lessZ = view.findViewById<Slider>(R.id.pgb_z)
            .apply {
                addOnChangeListener { slider, value, fromUser ->
                    this@MainFragment.rotationZ = value
                    lastAnchor?.let { anchor -> anchorOrMove(anchor) }
                }
            }

        actionButton = view.findViewById<ExtendedFloatingActionButton>(R.id.actionButton).apply {
            val bottomMargin = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
            doOnApplyWindowInsets { systemBarsInsets ->
                (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                    systemBarsInsets.bottom + bottomMargin
            }
            setOnClickListener { cursorNode.createAnchor()?.let { anchorOrMove(it) } }
        }

        sceneView = view.findViewById<ArSceneView?>(R.id.sceneView).apply {
            planeRenderer.isVisible = true
            // Handle a fallback in case of non AR usage. The exception contains the failure reason
            // e.g. SecurityException in case of camera permission denied
            onArSessionFailed = { _: Exception ->
                // If AR is not available or the camara permission has been denied, we add the model
                // directly to the scene for a fallback 3D only usage
                modelNode.centerModel(origin = Position(x = 0.0f, y = 0.0f, z = 0.0f))
                modelNode.scaleModel(units = 1.0f)
                sceneView.addChild(modelNode)
            }
//            onTouchAr = { hitResult, _ ->
//                anchorOrMove(hitResult.createAnchor())
//            }
        }

        cursorNode = CursorNode(context = requireContext(), lifecycle = lifecycle).apply {
            onTrackingChanged = { _, isTracking, _ ->
                if (!isLoading) {
                    actionButton.isGone = !isTracking
                }
            }
        }
        sceneView.addChild(cursorNode)
        isLoading = true
        modelNode = ArModelNode()
        layoutNode = ArModelNode()
    }

    private val modelList = listOf("models/chair_b.glb", "models/chair_a.glb")
    private var actualModel = 1

    private fun passModel(modelPath: String) {
        modelNode.loadModelAsync(context = requireContext(),
            lifecycle = lifecycle,
            glbFileLocation = modelPath,
            onLoaded = {
                actionButton.text = getString(R.string.move_object)
                actionButton.setIconResource(R.drawable.ic_target)
                isLoading = false
            })

        layoutNode.apply {
            position = Position(y = 1f)
            loadViewRender(
                context = requireContext(),
                lifecycle = lifecycle,
                layoutSrc = R.layout.view_renderable_infos
            )
        }

        modelNode.addChild(layoutNode)

        actualModel = if (actualModel + 1 >= modelList.size) 0 else (actualModel + 1)
    }

    private fun anchorOrMove(anchor: Anchor) {
        lastAnchor = anchor
        if (!sceneView.children.contains(modelNode)) {
            sceneView.addChild(modelNode)
        }
        modelNode.anchor = anchor
        modelNode.modelRotation = Rotation(x = rotationX, y = rotationY, z = rotationZ)
    }

    var rotationX = 0f
    var rotationY = 0f
    var rotationZ = 0f
    var lastAnchor: Anchor? = null
}