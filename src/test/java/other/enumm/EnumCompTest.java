package other.enumm;

public enum EnumCompTest {

    MAN(1),WOMAN(2);
    
    private int val;
    
    private EnumCompTest(int val) {
	this.val = val;
    }

    public int getVal() {
        return val;
    }
}
