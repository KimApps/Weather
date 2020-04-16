package com.apps.kim.weather.tools.base

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
interface ItemViewHolder<T> {

    fun bind(item: T)

}

/**
 * Base adapter for recycler view
 */
abstract class BaseRecyclerViewAdapter<TData, TViewHolder>(
    context: Context,
    data: List<TData> = listOf()
) :
    RecyclerView.Adapter<TViewHolder>() where TViewHolder : RecyclerView.ViewHolder, TViewHolder : ItemViewHolder<TData> {

    protected val appContext: Context = context
    protected val inflater: LayoutInflater = LayoutInflater.from(appContext)
    protected val data: MutableList<TData> = data.toMutableList()

    companion object {
        private const val FIRST_POSITION = 0
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TViewHolder, position: Int) =
        getItem(position).let { holder.bind(it) }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getItem(position: Int) = data[position]

    fun add(element: TData) = data.add(element)

    fun add(oldPosition: Int, newPosition: Int) = data.add(newPosition, remove(oldPosition))

    operator fun set(position: Int, element: TData) = data.set(position, element)

    fun remove(element: TData) = data.remove(element)

    fun remove(position: Int) = data.removeAt(position)

    fun updateListItems(newObjects: List<TData>, callback: DiffUtil.Callback) {
        DiffUtil.calculateDiff(callback)
            .dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newObjects)
    }

    fun updateAllNotify(newObjects: List<TData>) {
        clear()
        addAll(newObjects)
        notifyDataSetChanged()
    }

    val all: List<TData>
        get() = data

    fun clear() {
        data.clear()
    }

    fun addAll(collection: Collection<TData>) = data.addAll(collection)

    val snapshot: List<TData>
        get() = data.toMutableList()

    fun getItemPosition(element: TData) = data.indexOf(element)

    fun insert(element: TData, position: Int) = data.add(position, element)

    fun insertFirstWithNotify(element: TData) {
        data.add(FIRST_POSITION, element)
        notifyItemInserted(FIRST_POSITION)
    }

    fun insertAll(element: Collection<TData>, position: Int) = data.addAll(position, element)

    fun isEmpty() = data.isEmpty()

    fun isNotEmpty() = data.isNotEmpty()
}
