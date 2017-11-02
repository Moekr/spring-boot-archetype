package ${groupId}.${subPackage}.data.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SAMPLE_ENTITY")
@Where(clause = "deleted_at IS NULL")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Basic
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Basic
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
