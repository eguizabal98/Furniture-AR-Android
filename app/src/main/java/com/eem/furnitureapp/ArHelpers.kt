package com.eem.furnitureapp

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.google.ar.sceneform.rendering.ViewRenderable
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.model.await

fun ArModelNode.loadViewRender(context: Context, lifecycle: Lifecycle, layoutSrc: Int) {
    lifecycle.coroutineScope.launchWhenCreated {
        val layoutModel = ViewRenderable.builder()
            .setView(context, R.layout.view_renderable_infos)
            .await(lifecycle)

        this@loadViewRender.setModel(layoutModel)
    }
}