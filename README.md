# ReadProperty
Read and parse ini file.

Sample ini form (conf.ini)

[AGENT_INFO]<br />
SERVER=DEV<br />
CHARSET=UTF-8<br />
EDMS=WDMS<br />
<br />
[WAS_INFO]<br />
DEBUG=TRUE<br />
LOG_LEVEL=9<br />
MOBILE_USE=T<br />
#MOBILE_USE=F <- # started line will be ignored.<br />
MOBILE_MODE=EDIT<br />
<br />
*Sample usage*

- read ini
 ReadProperty(str ini file path)
 
 - get ini value 
 getString(str section, str key, str default value)
 


PS. save section and value will be updated soon.
