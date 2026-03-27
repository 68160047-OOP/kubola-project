package masterchef;

public class RecipeBook extends BaseRecipe {

    private static final String[][] DISH_NAMES = {
        {"Pad Thai", "Tom Yum", "Green Curry", "Mango Rice", "Thai Tea"},
        {"Sushi", "Ramen", "Tempura", "Mochi", "Matcha Latte"},
        {"Pizza", "Pasta", "Lasagna", "Tiramisu", "Espresso"},
        {"Steak", "Escargot", "Onion Soup", "Macaron", "Red Wine"},
        {"Peking Duck", "Dim Sum", "Char Siu", "Tangyuan", "Oolong Tea"}
    };

    private static final String[][][] INGREDIENTS = {
        // Thailand
        {
            {"noodle", "shrimp", "egg"},          // Pad Thai
            {"shrimp", "veg_mix", "lime"},         // Tom Yum
            {"veg_mix", "coconut", "sauce_dark"},  // Green Curry
            {"rice", "mango", "coconut"},          // Mango Rice
            {"tea", "milk", "honey"}               // Thai Tea
        },
        // Japan
        {
            {"rice", "fish", "veg_mix"},           // Sushi
            {"noodle", "egg", "pork"},             // Ramen
            {"shrimp", "dough", "egg"},            // Tempura
            {"rice", "honey", "fruit_mix"},        // Mochi
            {"tea", "milk", "honey"}               // Matcha Latte
        },
        // Italy
        {
            {"dough", "sauce_red", "cheese"},      // Pizza
            {"noodle", "sauce_red", "veg_mix"},    // Pasta
            {"noodle", "beef", "cheese"},          // Lasagna
            {"cheese", "egg", "coffee"},           // Tiramisu
            {"coffee", "milk", "cream"}            // Espresso
        },
        // France
        {
            {"beef", "butter", "garlic"},          // Steak
            {"escargot", "butter", "garlic"},      // Escargot
            {"onion", "bread", "cheese"},          // Onion Soup
            {"dough", "cream", "fruit_mix"},       // Macaron
            {"fruit_mix", "honey", "tea"}          // Red Wine
        },
        // China
        {
            {"duck", "sauce_dark", "onion"},       // Peking Duck
            {"dough", "shrimp", "pork"},           // Dim Sum
            {"pork", "honey", "sauce_dark"},       // Char Siu
            {"rice", "honey", "ginger"},           // Tangyuan (บัวลอยน้ำขิง)
            {"tea", "honey", "milk"}               // Oolong Tea (ชาอู่หลง)
        }
    };

    public RecipeBook(String nation, int index) {
        this.country = nation;
        int nIdx = nationIndex(nation);
        int stage = Math.min(index, 4);
        this.dishName = DISH_NAMES[nIdx][stage];
        this.ingredients = INGREDIENTS[nIdx][stage];
        this.timeLimit = Math.max(25, 55 - stage * 5);
    }

    private int nationIndex(String n) {
        switch (n) {
            case "Japan":  return 1;
            case "Italy":  return 2;
            case "France": return 3;
            case "China":  return 4;
            default:       return 0;
        }
    }
}
