package org.konge.taskmanagementapp.api.model.workspaces;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean completed = false;

}
