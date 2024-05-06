package com.mybankapp.mybank.model;


import java.util.List;
import java.util.Set;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueUsername", columnNames = "username")})
public class Customer implements UserDetails {

	@Id
	@UuidGenerator
	private String id;
	private String username;
	private String password;
	private String name;
	private Integer dateOfBirth;
	private City city;
	private String address;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Account> accountList;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Role> authorities;

	private boolean isEnabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;

	
}
