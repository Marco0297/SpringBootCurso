package service.impl;

import dto.RandomUserDTO;
import entity.Student;
import repository.StudentRepository;
import service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final WebClient webClient;

    @Override
    public int registerStudents(int n) {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            RandomUserDTO response = webClient
                    .get()
                    .uri("https://randomuser.me/api/?gender=female")
                    .retrieve()
                    .bodyToMono(RandomUserDTO.class)
                    .block();

            RandomUserDTO.Result user = response.getResults().get(0);

            Student student = Student.builder()
                    .firstName(user.getName().getFirst())
                    .lastName(user.getName().getLast())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .cell(user.getCell())
                    .build();

            students.add(student);
        }

        repository.saveAll(students);
        return students.size();
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Student> updateByName(String name) {
        List<Student> students = repository.findByFirstName(name);

        students.forEach(s -> s.setEmail("updated@email.com"));
        repository.saveAll(students);

        return students;
    }

    @Override
    public List<Student> deleteById(Long id) {
        repository.deleteById(id);
        return repository.findAll();
    }
}
