package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class that represents the roles of the user
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {

  /**
   * The individual number of the role
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long roleId;

  /**
   * The role name
   * For example: Admin, client
   */
  @Column
  private String roleName;

  public Role(String roleName) {
    this.roleName = roleName;
  }
}
