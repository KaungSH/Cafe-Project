package cafe.project.repository.entity;

import java.time.LocalDateTime;

public class PayMethod {

    private String methodId;
    private String name;
    private String description;
    private String logoPath;
    private boolean isActive;
    private boolean isEdited;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private String employeeId;

    public PayMethod() {
    }

    public PayMethod(String methodId, String name, String description, String logoPath,
                     boolean isActive, boolean isEdited, boolean isDeleted,
                     LocalDateTime createdAt, String employeeId) {
        this.methodId = methodId;
        this.name = name;
        this.description = description;
        this.logoPath = logoPath;
        this.isActive = isActive;
        this.isEdited = isEdited;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.employeeId = employeeId;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}