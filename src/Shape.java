
public enum Shape {
	LIVETWO(0), DEADTWO(1), LIVETHREE(2), DEADTHREE(3), LIVEFOUR(4), DEADFOUR(5), FIVE(6);
	
	private Shape(int num){
		this.num = num;
	}

	private final int num;
}