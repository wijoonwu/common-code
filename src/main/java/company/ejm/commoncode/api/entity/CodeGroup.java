package company.ejm.commoncode.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CodeGroup {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToMany(mappedBy = "codeGroup")
    private List<CommonCode> commonCodeList = new ArrayList<>();

    public void updateName(String groupName) {
        this.name = groupName;
    }
}