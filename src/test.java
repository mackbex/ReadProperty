import java.io.File;

public class test {

	public static void main(String[] args) {
		
		//read conf.ini
		ReadProperty rp = new ReadProperty(System.getProperty("user.dir") + File.separator + "conf.ini");
		
		//print parsed ini file value.
		System.out.println(rp.getString("WAS_INFO", "MOBILE_USE", "F"));
	}

}
