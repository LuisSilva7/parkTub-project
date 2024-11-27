package org.acsi.dto;

public class BonusDto {
    public Long id;
    public String description;
    public Double discountPercentage;
    public int pointsRequired;
    public boolean isActive;
    public int userPoints;

    public BonusDto(Long id, String description, Double discountPercentage, int pointsRequired, boolean isActive,
                    int userPoints) {
        this.id = id;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.pointsRequired = pointsRequired;
        this.isActive = isActive;
        this.userPoints = userPoints;
    }
}
