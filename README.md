# ReadProperty
Read and parse ini file.

Sample ini form

[AGENT_INFO]
SERVER=DEV
CHARSET=UTF-8
EDMS=WDMS


[WAS_INFO]
DEBUG=TRUE
LOG_LEVEL=9
MOBILE_USE=T
#MOBILE_USE=F
MOBILE_MODE=EDIT


Sample usage

- read ini
 ReadProperty(str ini file path)
 
 - get ini value 
 getString(str section, str key, str default value)
 

