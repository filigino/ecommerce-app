public class Audio extends Item {
    protected final String artist;
    protected final String info;

    public Audio(String info) {
        this.info = info;
        String[] infoArr = info.split(",");
        
        this.sNo = Integer.parseInt(infoArr[0]);
        this.name = infoArr[1];
        this.artist = infoArr[2];
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
