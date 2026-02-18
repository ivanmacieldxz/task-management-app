package org.konge.taskmanagementapp.api.model.boardlist;

import jakarta.persistence.*;
import lombok.*;
import org.konge.taskmanagementapp.api.model.common.Positionable;
import org.konge.taskmanagementapp.api.model.task.Task;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "board_list")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardList implements Positionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double positionInBoard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;


    @Override
    public Double getPosition() {
        return getPositionInBoard();
    }

    @Override
    public void setPosition(Double position) {
        setPositionInBoard(position);
    }
}
