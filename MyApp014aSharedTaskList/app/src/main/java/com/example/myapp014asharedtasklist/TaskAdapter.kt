package com.example.myapp014asharedtasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp014asharedtasklist.databinding.ItemTaskBinding

// Adapter obsluhuje zobrazení seznamu úkolů v RecyclerView.
// Přijímá list úkolů a callbacky pro změnu stavu a smazání.
class TaskAdapter(
    private var tasks: List<Task>,
    private val onChecked: (Task) -> Unit,  // zavolá se při kliknutí na checkbox
    private val onDelete: (Task) -> Unit,    // zavolá se při kliknutí na ikonu smazání
    private val onEdit: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder drží jeden řádek seznamu (item_task.xml) a jeho view binding.
    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Vytvoření nového ViewHolderu – vytvoří se layout jednoho řádku.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TaskViewHolder(binding)
    }

    // Naplnění dat pro jeden řádek – nastavíme text, checkbox a tlačítko delete.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.binding.textTitle.text = task.title

        // 1) Nejdřív odpoj listener (aby se nespustil při nastavování isChecked)
        holder.binding.checkCompleted.setOnCheckedChangeListener(null)

        // 2) Nastav stav checkboxu z dat
        holder.binding.checkCompleted.isChecked = task.completed

        // 3) Připoj listener znovu a pošli aktualizovaný task (se správnou hodnotou)
        holder.binding.checkCompleted.setOnCheckedChangeListener { _, isChecked ->
            onChecked(task.copy(completed = isChecked))
        }

        holder.binding.imageEdit.setOnClickListener {
            onEdit(task)
        }

        holder.binding.imageDelete.setOnClickListener {
            onDelete(task)
        }
    }

    // Počet položek v seznamu
    override fun getItemCount() = tasks.size

    // Aktualizace seznamu – nahradí starý list novým a překreslí RecyclerView
    fun submitList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }
}
