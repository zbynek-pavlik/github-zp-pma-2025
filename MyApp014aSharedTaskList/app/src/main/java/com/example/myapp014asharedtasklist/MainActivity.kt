package com.example.myapp014asharedtasklist

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp014asharedtasklist.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore

        // Nastavení adapteru
        adapter = TaskAdapter(
            tasks = emptyList(),
            onChecked = { updatedTask -> toggleCompleted(updatedTask) },
            onDelete = { task -> deleteTask(task) },
            onEdit = { task -> showEditDialog(task) }     // ✅ nově
        )
        binding.recyclerViewTasks.adapter = adapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        // Přidání úkolu
        binding.buttonAdd.setOnClickListener {
            val title = binding.inputTask.text.toString()
            if (title.isNotEmpty()) {
                addTask(title)
                binding.inputTask.text.clear()
            }
        }

        // Realtime sledování Firestore
        listenForTasks()
    }

    private fun addTask(title: String) {
        println("DEBUG: addTask called with title = $title")
        val task = Task(title = title, completed = false)
        db.collection("tasks").add(task)
    }

    private fun toggleCompleted(task: Task) {
        if (task.id.isBlank()) return

        db.collection("tasks")
            .document(task.id)
            .update("completed", task.completed)
    }

    private fun deleteTask(task: Task) {
        if (task.id.isBlank()) return

        db.collection("tasks")
            .document(task.id)
            .delete()
    }


    private fun listenForTasks() {
        db.collection("tasks")
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener

                val tasks = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Task::class.java)?.apply { id = doc.id }
                }

                adapter.submitList(tasks)
            }
    }

    private fun showEditDialog(task: Task) {
        if (task.id.isBlank()) return

        val input = EditText(this).apply {
            setText(task.title)
            setSelection(text.length)
        }

        AlertDialog.Builder(this)
            .setTitle("Upravit úkol")
            .setView(input)
            .setPositiveButton("Uložit") { _, _ ->
                val newTitle = input.text.toString().trim()
                if (newTitle.isNotEmpty() && newTitle != task.title) {
                    updateTaskTitle(task, newTitle)
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }

    private fun updateTaskTitle(task: Task, newTitle: String) {
        db.collection("tasks")
            .document(task.id)
            .update("title", newTitle)
    }

}