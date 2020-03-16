package com.swordfish.lemuroid.app.tv

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.squareup.picasso.Picasso
import com.swordfish.lemuroid.R
import com.swordfish.lemuroid.lib.library.db.entity.Game

class GamePresenter(private val cardSize: Int) : Presenter() {

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        if (item == null || viewHolder !is ViewHolder) return
        val game = item as Game
        viewHolder.mCardView.titleText = game.title
        viewHolder.mCardView.contentText = game.developer // TODO FILIPPO Fix description
        viewHolder.mCardView.setMainImageDimensions(cardSize, cardSize)
        viewHolder.updateCardViewImage(game.coverFrontUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        val cardView = ImageCardView(parent.context)
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        (cardView.findViewById<View>(R.id.content_text) as TextView).setTextColor(Color.LTGRAY)
        return ViewHolder(cardView, cardSize)
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder?) {
        (viewHolder as ViewHolder).let {

        }
        viewHolder.mCardView.mainImage = null
        Picasso.get().cancelRequest(viewHolder.mCardView.mainImageView)
    }

    class ViewHolder(view: ImageCardView, private val cardSize: Int) : Presenter.ViewHolder(view) {
        val mCardView: ImageCardView = view

        fun updateCardViewImage(url: String?) {
            Picasso.get()
                .load(url)
                .resize(cardSize, cardSize)
                .centerCrop()
                .placeholder(R.drawable.ic_image_paceholder)
                .into(mCardView.mainImageView)
        }
    }
}
