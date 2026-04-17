package app.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import app.model.Task;
import app.repository.TaskRepository;
import app.repository.CategoryRepository;
import java.util.Optional;
@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    // Liste toutes les tâches
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks"; // template tasks.html
    }
    // Formulaire pour créer une nouvelle tâche
    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryRepository.findAll());
        return "task_form"; // template task_form.html
    }
    // Créer une nouvelle tâche
    @PostMapping
    public String createTask(@ModelAttribute("task") Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }
    // Formulaire pour modifier une tâche existante
    @GetMapping("/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            model.addAttribute("task", taskOpt.get());
            model.addAttribute("categories", categoryRepository.findAll());
            return "task_form"; // on peut réutiliser le même template
        } else {
            return "redirect:/tasks";
        }
    }
    // Modifier une tâche
    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task task) {
        task.setId(id); // s'assurer que l'ID est correct
        taskRepository.save(task);
        return "redirect:/tasks";
    }
    // Supprimer une tâche
    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
    // Afficher les détails d'une tâche
    @GetMapping("/{id}")
    public String showTask(@PathVariable Long id, Model model) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            model.addAttribute("task", taskOpt.get());
            return "task_detail"; // template task_detail.html à créer si nécessaire
        } else {
            return "redirect:/tasks";
        }
    }
}