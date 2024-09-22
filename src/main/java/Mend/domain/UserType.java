package Mend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "UserTypes")
public class UserType{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserTypeId")
    private int userTypeId;

    @Column(name = "UserTypeDesc", length = 100)
    private String userTypeDesc;

    // Getters and Setters
    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeDesc() {
        return userTypeDesc;
    }

    public void setUserTypeDesc(String userTypeDesc) {
        this.userTypeDesc = userTypeDesc;
    }
}
