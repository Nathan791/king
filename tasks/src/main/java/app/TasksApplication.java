package app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import app.model.Category;
import app.model.Task;
import app.repository.CategoryRepository;
import app.repository.TaskRepository;
import java.time.LocalDate;
@SpringBootApplication
public class TasksApplication implements CommandLineRunner {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private TaskRepository taskRepository;
	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
// Vérifie si la base est vide
		if (categoryRepository.count() == 0) {
// Création de catégories
			Category cat1 = new Category("Travail");
			Category cat2 = new Category("Personnel");
			Category cat3 = new Category("Urgent");
			categoryRepository.save(cat1);
			categoryRepository.save(cat2);
			categoryRepository.save(cat3);
// Création de tâches de test
			Task t1 = new Task(cat1, "Préparer le rapport annuel", LocalDate.now(),
					LocalDate.now().plusDays(5), "À faire");
			Task t2 = new Task(cat2, "Acheter du lait", LocalDate.now(), LocalDate.now().plusDays(1),
					"À faire");
			Task t3 = new Task(cat3, "Réparer la fuite", LocalDate.now(),
					LocalDate.now().plusDays(2), "En cours");
			taskRepository.save(t1);
			taskRepository.save(t2);
			taskRepository.save(t3);
			System.out.println("Base initialisée avec des catégories et tâches de test.");
		}
	}
}