package masterchef;

public abstract class BaseRecipe {
    protected String dishName, country;
    protected String[] ingredients;
    protected int step = 0;
    protected int timeLimit = 45;
    protected boolean potIsReady = false;

    public String getTarget() {
        if (!potIsReady) return "Pot"; // เปลี่ยนจาก "หม้อ" เป็น "Pot"
        return (step < ingredients.length) ? ingredients[step] : "DONE";
    }
    public void next() {
        if (!potIsReady) potIsReady = true;
        else step++;
    }
    public boolean isFinished() { return potIsReady && step >= ingredients.length; }
    public boolean needsPot() { return !potIsReady; }
    public String getDishName() { return dishName; }
    public String getCountry() { return country; }
    public int getTimeLimit() { return timeLimit; }
    public String[] getIngredients() { return ingredients; }
}