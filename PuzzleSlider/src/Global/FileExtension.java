package Global;

import java.util.ArrayList;
import java.util.List;

public class FileExtension {

	public String description;
	public List<String> extensions;
	
	public FileExtension( String description, String ... extensions ) {
		this.description = description;
		this.extensions = new ArrayList<String>();
		for ( String ext : extensions ) {
			this.extensions.add( ext );
		}
	}
	
}
