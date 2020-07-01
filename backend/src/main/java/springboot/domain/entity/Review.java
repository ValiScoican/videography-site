package springboot.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Basic(optional = false)
    @Column(name = "content", length = 440, nullable = false, unique = false)
    private String content;

    @Basic(optional = false)
    @Column(name = "user_id", nullable = false, unique = false)
    private Integer userId;

    public Review(String content, Integer userId){
        this.content = content;
        this.userId = userId;
    }
}


