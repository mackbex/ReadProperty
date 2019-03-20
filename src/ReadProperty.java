


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Load ini file to parse WAS configuration.
 */

public class ReadProperty 
{

	private char chCommentSign 	= '#';
	private HashMap<String, HashMap<String, String>> m_mapItems = new HashMap<>();
	private 	String[]	m_arrStrInput;			// 파일 내용

	/**
	 *  load configuration file.
	 *  @param strConfPath : conf file path
	 */
	
	public ReadProperty(String strConfPath) 
	{
		m_mapItems = readIni(strConfPath);
	}
	
	/**
	 * store ini values to member variable.
	 * @param iniFile
	 * @return parsed ini values
	 */
	private HashMap<String, HashMap<String, String>> readIni(String strFilePath) {
		
		HashMap<String, HashMap<String, String>> mapRes = null;
		
		BufferedReader br = null;
		
		try
		{
			br = new BufferedReader(new FileReader(strFilePath));
			if(br != null)
			{
				mapRes 			= new HashMap<>();
				String line 		= br.readLine();
				
				HashMap<String, String> mapValue 	= new HashMap<>();
				String strHeader 							= null;
				
				while(line != null)
				{
					line = line.trim();
					//Check null line.
					if(line.length() == 0) 
					{
						line = br.readLine();
						continue;
					}
					
					/**
					 * When new header detected, Save below of current header items.
					 */
					int nStartHeaderIdx = line.indexOf('[');
					if(nStartHeaderIdx != -1)
					{
						/**
						 * Save stacked value item to header.
						 */
						
						if(mapValue.size() > 0)
						{
							mapRes.put(strHeader, mapValue);
						}
						
						/**
						 * Read next header.
						 */
						
						strHeader = getHeaderValue(line);
						
						mapValue = new HashMap<>();
						line = br.readLine();
						continue;
					}
					
					/**
					 * save value item
					 */
					if(!isBlank(strHeader)) putValueItem(mapValue, line);
					
					//read next line.
					line = br.readLine();
				}
				
				/**
				 * Save last header info
				 */
				if(!isBlank(strHeader) && !mapValue.isEmpty())
				{
					mapRes.put(strHeader, mapValue);
				}
				
			}
		}
		catch(Exception e)
		{
			mapRes = null;
		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				return null;
			}
		}
		
		return mapRes;
	}
	
	/**
	 * put value item into received map object.
	 * @param mapValue
	 * @param strLine
	 */
	private void putValueItem(HashMap<String, String> mapValue, String strLine)
	{
		/**
		 * do nothing if receive map is null
		 */
		
		if(mapValue == null)
		{
			return;
		}
		
		/**
		 * do nothing if comment sign is detected.
		 */
		if(strLine.indexOf(chCommentSign) > -1)
		{
			return;
		}
		
		String[] arValueItem = strLine.split("=");
		
		
		/**
		 * return the value from is unexpected.
		 */
		if(arValueItem == null)
		{
			return;
		}
		
		/**
		 * get item key
		 */
		String strKey 		= null;
		try 
		{
			strKey = arValueItem[0];
		}
		catch(Exception e)
		{
			strKey = "";	
		}
		
		/**
		 * get item value
		 */
		String strValue 	= null;
		try 
		{
			strValue = arValueItem[1];
		}
		catch(Exception e)
		{
			strValue = "";	
		}
		
		
		/*put item*/
		mapValue.put(strKey.trim(), strValue.trim());
		
	}
	
	/**
	 * Get header value until end of line or commented sign 
	 * @param strLine
	 * @return
	 */
	private String getHeaderValue(String strLine) {
		
		/**
		 * do nothing if comment sign is detected.
		 */
		if(strLine.indexOf(chCommentSign) > -1)
		{
			return null;
		}
		
		StringBuffer sbRes = new StringBuffer();
		for(int i = 0; i < strLine.length(); i++)
		{
			char ch = strLine.charAt(i);
			
			/**
			 * Ignore header start sign
			 */
			if('[' == ch)
			{
				continue;
			}
			
			/**
			 * break at end of hear sign
			 */
			if(']' == ch)
			{
				break;
			}
			
			sbRes.append(ch);
		}
		
		return sbRes.toString();
	}
    
    /**
     * get all values in specific section
     * @param strSection
     * @return
     */
    public HashMap<String ,String> GetAllSectionValues(String strSection)
    {
    	return m_mapItems.get(strSection);
    }
    
    /**
     * Get value
     * @param strSection
     * @param strKey
     * @param strDefault
     * @return
     */
    
    public String getString(String strSection, String strKey, String strDefault)
    {
    	String strRes = null;
    	strRes = m_mapItems.get(strSection).get(strKey);
    	if(isBlank(strRes)) strRes = strDefault;
    	
    	return strRes;
    }
    
    /**
	 * Null Check
	 * @param str
	 * @return
	 */
	public boolean isBlank(String str)
	{
		if(str == null || str.replaceAll("\\p{Z}", "").length()<=0)
		{
			return true;
		}
		return false;
	}
}