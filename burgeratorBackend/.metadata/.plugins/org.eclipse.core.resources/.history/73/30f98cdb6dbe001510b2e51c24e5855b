package burgeratorBackend;

public class Burger {
	
	
	private String name;
	private  String imagePath;
	private BurgerRating rating;
	private BurgerOptions opts;
	
	
	public Burger(){
		
	}
	
	
	
	public void setName(String _name){
		this.name = _name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setImagePath(String _imagePath){
		this.imagePath = _imagePath;
	}
	public String getImagePath(){
		return this.imagePath;
	}
	
	public double getBurgerRating(){
		double rating = -888;
		if(this.rating instanceof BurgerRating){
			
			rating = this.rating.getRating();
			
			//Log.getInstance().addLog("Success, Burger rating exists; rating: " + rating);
			return rating;
		}
		return rating;
	}
	
	public void setBurgerRating(double _rating){
		this.rating = new BurgerRating(_rating);
	}
	
	public void setBurgerOpts(int _cheese, int _btb, int _prep){
		this.opts = new BurgerOptions(_cheese, _btb, _prep);
	}
	public Burger getBurger(){
		
		return this;
	}
	

}
