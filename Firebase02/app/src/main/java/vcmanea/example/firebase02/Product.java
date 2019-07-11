package vcmanea.example.firebase02;

public class Product {

    public String name;
    public String description;
    public String category;
    public String extraDetails;

    public Product(){
        //Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product(String name, String description, String category, String extraDetails) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.extraDetails = extraDetails;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }
}
