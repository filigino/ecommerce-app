public class Readable extends Item {
    protected final String author;
    protected final String info;

    public Readable(String info) {
        this.info = info;
        String[] infoArr = info.split(",");
        
        this.sNo = Integer.parseInt(infoArr[0]);
        this.name = infoArr[1];
        this.author = infoArr[2];
        this.price = Integer.parseInt(infoArr[3]);
        this.quantity = Integer.parseInt(infoArr[4]);
	}

    public String getInfo() {
        return this.info;
    }

    public int getPrice() {
        return this.price;
    }
}
