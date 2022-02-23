package it.unibg.studenti.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unibg.studenti.data.AbstractEntity;
import it.unibg.studenti.data.Role;
import it.unibg.studenti.generated.tables.records.UserRecord;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {

    private String username;
    private String name;
    @JsonIgnore
    private String hashedPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    private String profilePictureUrl;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public void setUserWithRecord(@NotNull UserRecord record) {
        this.setUsername(record.getUsername());
        this.setHashedPassword(record.getHashedpassword());
        this.setProfilePictureUrl(record.getProfilepictureurl());
        if(record.getRole().equals(Role.ADMIN.getRoleName())){
            this.setRoles(Stream.of(Role.USER, Role.ADMIN).collect(Collectors.toSet()));
        } else if(record.getRole().equals(Role.USER.getRoleName())) {
            this.setRoles(Stream.of(Role.USER).collect(Collectors.toSet()));
        }
    }
}
