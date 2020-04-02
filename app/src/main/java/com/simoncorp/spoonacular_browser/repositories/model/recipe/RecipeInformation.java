
package com.simoncorp.spoonacular_browser.repositories.model.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeInformation implements Parcelable {

    @SerializedName("vegetarian")
    @Expose
    private Boolean vegetarian;
    @SerializedName("vegan")
    @Expose
    private Boolean vegan;
    @SerializedName("glutenFree")
    @Expose
    private Boolean glutenFree;
    @SerializedName("dairyFree")
    @Expose
    private Boolean dairyFree;
    @SerializedName("veryHealthy")
    @Expose
    private Boolean veryHealthy;
    @SerializedName("cheap")
    @Expose
    private Boolean cheap;
    @SerializedName("veryPopular")
    @Expose
    private Boolean veryPopular;
    @SerializedName("sustainable")
    @Expose
    private Boolean sustainable;
    @SerializedName("weightWatcherSmartPoints")
    @Expose
    private Integer weightWatcherSmartPoints;
    @SerializedName("gaps")
    @Expose
    private String gaps;
    @SerializedName("lowFodmap")
    @Expose
    private Boolean lowFodmap;
    @SerializedName("preparationMinutes")
    @Expose
    private Integer preparationMinutes;
    @SerializedName("cookingMinutes")
    @Expose
    private Integer cookingMinutes;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("spoonacularSourceUrl")
    @Expose
    private String spoonacularSourceUrl;
    @SerializedName("aggregateLikes")
    @Expose
    private Integer aggregateLikes;
    @SerializedName("spoonacularScore")
    @Expose
    private Double spoonacularScore;
    @SerializedName("healthScore")
    @Expose
    private Double healthScore;
    @SerializedName("creditsText")
    @Expose
    private String creditsText;
    @SerializedName("sourceName")
    @Expose
    private String sourceName;
    @SerializedName("pricePerServing")
    @Expose
    private Double pricePerServing;
    @SerializedName("extendedIngredients")
    @Expose
    private List<ExtendedIngredient> extendedIngredients = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("readyInMinutes")
    @Expose
    private Integer readyInMinutes;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageType")
    @Expose
    private String imageType;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("cuisines")
    @Expose
    private List<Object> cuisines = null;
    @SerializedName("dishTypes")
    @Expose
    private List<String> dishTypes = null;
    @SerializedName("diets")
    @Expose
    private List<String> diets = null;
    @SerializedName("occasions")
    @Expose
    private List<Object> occasions = null;
    @SerializedName("winePairing")
    @Expose
    private WinePairing winePairing;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("analyzedInstructions")
    @Expose
    private List<AnalyzedInstruction> analyzedInstructions = null;
    @SerializedName("originalId")
    @Expose
    private Object originalId;

    protected RecipeInformation(Parcel in) {
        byte tmpVegetarian = in.readByte();
        vegetarian = tmpVegetarian == 0 ? null : tmpVegetarian == 1;
        byte tmpVegan = in.readByte();
        vegan = tmpVegan == 0 ? null : tmpVegan == 1;
        byte tmpGlutenFree = in.readByte();
        glutenFree = tmpGlutenFree == 0 ? null : tmpGlutenFree == 1;
        byte tmpDairyFree = in.readByte();
        dairyFree = tmpDairyFree == 0 ? null : tmpDairyFree == 1;
        byte tmpVeryHealthy = in.readByte();
        veryHealthy = tmpVeryHealthy == 0 ? null : tmpVeryHealthy == 1;
        byte tmpCheap = in.readByte();
        cheap = tmpCheap == 0 ? null : tmpCheap == 1;
        byte tmpVeryPopular = in.readByte();
        veryPopular = tmpVeryPopular == 0 ? null : tmpVeryPopular == 1;
        byte tmpSustainable = in.readByte();
        sustainable = tmpSustainable == 0 ? null : tmpSustainable == 1;
        if (in.readByte() == 0) {
            weightWatcherSmartPoints = null;
        } else {
            weightWatcherSmartPoints = in.readInt();
        }
        gaps = in.readString();
        byte tmpLowFodmap = in.readByte();
        lowFodmap = tmpLowFodmap == 0 ? null : tmpLowFodmap == 1;
        if (in.readByte() == 0) {
            preparationMinutes = null;
        } else {
            preparationMinutes = in.readInt();
        }
        if (in.readByte() == 0) {
            cookingMinutes = null;
        } else {
            cookingMinutes = in.readInt();
        }
        sourceUrl = in.readString();
        spoonacularSourceUrl = in.readString();
        if (in.readByte() == 0) {
            aggregateLikes = null;
        } else {
            aggregateLikes = in.readInt();
        }
        if (in.readByte() == 0) {
            spoonacularScore = null;
        } else {
            spoonacularScore = in.readDouble();
        }
        if (in.readByte() == 0) {
            healthScore = null;
        } else {
            healthScore = in.readDouble();
        }
        creditsText = in.readString();
        sourceName = in.readString();
        if (in.readByte() == 0) {
            pricePerServing = null;
        } else {
            pricePerServing = in.readDouble();
        }
        extendedIngredients = in.createTypedArrayList(ExtendedIngredient.CREATOR);
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            readyInMinutes = null;
        } else {
            readyInMinutes = in.readInt();
        }
        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }
        image = in.readString();
        imageType = in.readString();
        summary = in.readString();
        dishTypes = in.createStringArrayList();
        diets = in.createStringArrayList();
        instructions = in.readString();
        analyzedInstructions = in.createTypedArrayList(AnalyzedInstruction.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (vegetarian == null ? 0 : vegetarian ? 1 : 2));
        dest.writeByte((byte) (vegan == null ? 0 : vegan ? 1 : 2));
        dest.writeByte((byte) (glutenFree == null ? 0 : glutenFree ? 1 : 2));
        dest.writeByte((byte) (dairyFree == null ? 0 : dairyFree ? 1 : 2));
        dest.writeByte((byte) (veryHealthy == null ? 0 : veryHealthy ? 1 : 2));
        dest.writeByte((byte) (cheap == null ? 0 : cheap ? 1 : 2));
        dest.writeByte((byte) (veryPopular == null ? 0 : veryPopular ? 1 : 2));
        dest.writeByte((byte) (sustainable == null ? 0 : sustainable ? 1 : 2));
        if (weightWatcherSmartPoints == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weightWatcherSmartPoints);
        }
        dest.writeString(gaps);
        dest.writeByte((byte) (lowFodmap == null ? 0 : lowFodmap ? 1 : 2));
        if (preparationMinutes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(preparationMinutes);
        }
        if (cookingMinutes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cookingMinutes);
        }
        dest.writeString(sourceUrl);
        dest.writeString(spoonacularSourceUrl);
        if (aggregateLikes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(aggregateLikes);
        }
        if (spoonacularScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(spoonacularScore);
        }
        if (healthScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(healthScore);
        }
        dest.writeString(creditsText);
        dest.writeString(sourceName);
        if (pricePerServing == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(pricePerServing);
        }
        dest.writeTypedList(extendedIngredients);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        if (readyInMinutes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(readyInMinutes);
        }
        if (servings == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(servings);
        }
        dest.writeString(image);
        dest.writeString(imageType);
        dest.writeString(summary);
        dest.writeStringList(dishTypes);
        dest.writeStringList(diets);
        dest.writeString(instructions);
        dest.writeTypedList(analyzedInstructions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeInformation> CREATOR = new Creator<RecipeInformation>() {
        @Override
        public RecipeInformation createFromParcel(Parcel in) {
            return new RecipeInformation(in);
        }

        @Override
        public RecipeInformation[] newArray(int size) {
            return new RecipeInformation[size];
        }
    };

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public Boolean getDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(Boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public Boolean getVeryHealthy() {
        return veryHealthy;
    }

    public void setVeryHealthy(Boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    public Boolean getCheap() {
        return cheap;
    }

    public void setCheap(Boolean cheap) {
        this.cheap = cheap;
    }

    public Boolean getVeryPopular() {
        return veryPopular;
    }

    public void setVeryPopular(Boolean veryPopular) {
        this.veryPopular = veryPopular;
    }

    public Boolean getSustainable() {
        return sustainable;
    }

    public void setSustainable(Boolean sustainable) {
        this.sustainable = sustainable;
    }

    public Integer getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public void setWeightWatcherSmartPoints(Integer weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    public Boolean getLowFodmap() {
        return lowFodmap;
    }

    public void setLowFodmap(Boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    public Integer getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(Integer preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    public Integer getCookingMinutes() {
        return cookingMinutes;
    }

    public void setCookingMinutes(Integer cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }

    public Integer getAggregateLikes() {
        return aggregateLikes;
    }

    public void setAggregateLikes(Integer aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    public Double getSpoonacularScore() {
        return spoonacularScore;
    }

    public void setSpoonacularScore(Double spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    public Double getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Double healthScore) {
        this.healthScore = healthScore;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public void setCreditsText(String creditsText) {
        this.creditsText = creditsText;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Double getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(Double pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Object> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Object> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(List<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public List<Object> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<Object> occasions) {
        this.occasions = occasions;
    }

    public WinePairing getWinePairing() {
        return winePairing;
    }

    public void setWinePairing(WinePairing winePairing) {
        this.winePairing = winePairing;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(List<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public Object getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Object originalId) {
        this.originalId = originalId;
    }

}
