package com.atlassian.springbootreact.persistence.repositoryimpl;

import com.atlassian.springbootreact.domain.filterandpagination.ElementPage;
import com.atlassian.springbootreact.domain.filterandpagination.TaskFilter;
import com.atlassian.springbootreact.domain.model.Task;
import com.atlassian.springbootreact.domain.repository.TaskRepository;
import com.atlassian.springbootreact.persistence.entity.TaskEntity;
import com.atlassian.springbootreact.persistence.jpa.JpaTaskRepository;
import com.atlassian.springbootreact.persistence.mapper.TaskMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private JpaTaskRepository jpaTaskRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private TaskMapper taskMapper;

    public TaskRepositoryImpl(JpaTaskRepository jpaTaskRepository, TaskMapper taskMapper) {
        this.jpaTaskRepository = jpaTaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task save(Task task) {
        return taskMapper.convertEntityToDomain(
                jpaTaskRepository.save(taskMapper.convertDomainToEntity(task))
        );
    }

    @Override
    public Optional<Task> retrieveById(Long taskId) {
        Optional<TaskEntity> optionalTaskEntity = jpaTaskRepository.findById(taskId);

        if(optionalTaskEntity.isPresent()){
            return Optional.of(taskMapper.convertEntityToDomain(optionalTaskEntity.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long taskId) {
        jpaTaskRepository.deleteById(taskId);
    }

    @Override
    public ElementPage<Task> retrieveTasks(TaskFilter taskFilter) {
        Pageable pageable = getOf(taskFilter.getPage(), taskFilter.getLimit(), taskFilter.getSortDirection(),
                taskFilter.getSortColumn());
        Page<TaskEntity> taskEntities = jpaTaskRepository.findAll(getSpec(taskFilter), pageable);
        return taskMapper.convertPageToElementPage(taskEntities);
    }

    private PageRequest getOf(String page, String limit, String sortDirection, String sortColumn) {
        return PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit),
                Sort.by(Sort.Direction.valueOf(sortDirection), sortColumn));
    }

    private Specification<TaskEntity> getSpec(TaskFilter taskFilter) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicateFilters(root, criteriaBuilder, predicates, taskFilter);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void predicateFilters(Root<TaskEntity> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates,
                                  TaskFilter taskFilter) {
        if (taskFilter.getDashboardId() != null) {
            predicates
                    .add(criteriaBuilder.equal(root.get("dashboardEntity").get("id"), taskFilter.getDashboardId()));
        }
    }
}
