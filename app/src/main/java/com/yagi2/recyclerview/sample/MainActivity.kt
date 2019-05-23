package com.yagi2.recyclerview.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// アクティビティのクラス
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // アダプターのインスタンスをつくる
        val adapter = MyRecyclerViewAdapter()

        // RecyclerViewをひっぱってくる
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // おまじないのようなもの（直線上に並ぶRecyclerViewですよとセットしてる）
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RecyclerViewにアダプターをセット
        recyclerView.adapter = adapter

        // 画像のリソースをとりあえず20個データとして作る
        @DrawableRes
        val data = mutableListOf<Int>()
        repeat(20) {
            data.add(R.mipmap.ic_launcher)
        }

        // 20個の画像リソース（正しくいうと画像の持っているID）をアダプターにセット
        adapter.setData(data)
    }
}

// アダプターのクラス
class MyRecyclerViewAdapter : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    // RecyclerViewに表示するデータのリスト（今回は画像のID）
    @DrawableRes
    private val data = mutableListOf<Int>()

    // ViewHolderを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // おまじないのようなもの（Viewを生成するための道具を用意している）
        val inflater = LayoutInflater.from(parent.context)

        // R.layout.アイテムのレイアウト を渡してリストアイテムのViewHolderにする
        // inflater.inflateというのは上で用意したViewを生成するためのツールを使って、MyViewHolderのコンストラクタにViewを生成して渡している
        return MyViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    // データのサイズを返す
    override fun getItemCount() = data.size

    // リスト1つ1つとデータ1つ1つを紐付ける
    // 今回はViewHolderに紐づけするbindという名前のメソッドを作ったのでそれに対して、表示したい画像のIDを渡している
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // アクティビティからデータをセットするためのメソッド
    fun setData(@DrawableRes data: List<Int>) {
        // 受け取ったデータをアダプターのデータリストへつっこむ
        this.data.clear()
        this.data.addAll(data)

        // これもおまじない、データが変更されたよというのを教えてあげている（これでRecyclerViewに更新が走る）
        notifyDataSetChanged()
    }

    // ViewHolderのクラス
    // コンストラクタでアイテムのViewを受け取っている
    // 実際にはアダプタークラスのonCreateViewHolderのreturn文でアイテムのViewを生成して渡している
    class MyViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

        // データとViewの紐づけするためのメソッド
        // アダプタークラスのonBindViewHolderで呼んでいる
        fun bind(@DrawableRes imageResId: Int) {
            // アイテムのViewからImageViewをさがす
            val imageView = item.findViewById<ImageView>(R.id.image_view)

            // それに対して画像をセットする（今回は固定の最初から存在している画像なので、IDからDrawable（画像リソース）を取得してImageViewにセットしている
            imageView.setImageDrawable(ContextCompat.getDrawable(item.context, imageResId))
        }
    }
}