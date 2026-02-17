package org.konge.taskmanagementapp.api.model.workspace;

import jakarta.persistence.*;
import lombok.*;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.model.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workspace")
@EntityListeners(AuditingEntityListener.class)  //enables auto audit for createdAt and lastModified columns
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    //a workspace has many lists and a list knows its workspace (bidirectional)
    //mappedBy: indicates that the relationship owner is TaskList
    //cascade: delete on cascade when deleting workspace
    //orphanRemoval: if a list is deleted from the collection, remove from DB
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardList> lists;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

}
