<cfsilent>
	<cfapplication name="HTTPCaching" sessionmanagement="no" clientmanagement="no" applicationtimeout="#createtimespan(1,0,0,0)#" />
	<cfif not structKeyExists(application, "oHTTPCaching")>
		<cfset application.oHTTPCaching = createObject("component", "../HTTPCaching") />
	</cfif>
	
	<!--- the string to be used as an Etag - in the response header --->
	<cfset etag = "8797BF9AE0AD24CF1FC35D18CEDA1849" />
	<cfset mimetype = "image/png" />
	
	<!--- check if the content was cached on the browser, and set the ETag header. --->
	<cfif application.oHTTPCaching.handleResponseWhenCached(fileEtag=etag, mimetype=mimetype, expireDays=100)>
		<cfexit method="exittemplate" />
	</cfif>
</cfsilent>

<!--- file was not cached; send the data --->
<cfcontent reset="yes" type="#mimetype#"
	variable="#toBinary('iVBORw0KGgoAAAANSUhEUgAAABYAAAAWCAYAAADEtGw7AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAsZJREFUeNq0Vc9vEkEU3lmW5YdibZFCLG0P9WDAUJQj0Sve9OZ/5B/h2ZM344kTBw03ElJaQhMPxEICRWgNlJZlF/yGfFNHQqw/4iRfdmZ253vvfe/NWzGfz4VhGD7AD9iAxbXc/50xBzzABRxgKteKJAiEgQjnct8EZjeQChJL0itgCIzl3KKnknQDSHAe4CGHBDMSKA8V/NyTXl4CPa4XHtv0NFEsFkvGP4xCofCMRiYWw1ZSyJevobs05BdCjDRvLoAzPseMZo3nbDj1Bs+QktKkxhbDlyPSarX2y+Xyq36//xjrPWD74ODgBQxaJB0AXaBPXZVkfpV8k1oKrQqsZrO5PxqN1kGWHwwGe0dHR887nc5TROBnkiRxB/gKnNOYnlBhaglRVi9SqVQlEAhcOY4TrFQqWUSwbZqmx0oZMUldzetvWhIXSTZX5GAajUZ72Wz20OfzebPZzKe92wRiRBRYB26xAAy99i3tkKrZS2g5bLfbm57nXZPKiwQjMXi+yy2ZuNtMWPhXxKpOL2q12oNut3sfHk/Vx5i7IL2L6UN6fEpnHM2plcRqnGUymbd4vqNUcXh7T16gUqn0ElFYP4KYi3g8/gnff1gmWekxs6xqN4Zq2IUMadd1/brmyWTyCxL9mTrfKIWq0wGzf77IsmluwoD6RkrjbW1tnWIvQp1/KgZzBbGj3bI+yXvU1KARTya2Wq0+wiWKaT3DWCYW2ss1llKciDH7MykDwj/J5XJV27avJpNJsF6v57SqsJUcltap1AizvyqDUr8gEjVNJBIf0+n0MdZ3kLDzRqPxZGdnp6Z9f935LK1JTzWrEa2HyHUYWroge09DIVyik3w+f8zzQ56dkGtROqpJyyow2KX+dozJ5QqEGKKHG9QztNTAl5uU0HJja5dkzMYkq2lo8fBYI1K/JrGk/Z/8mqbif/1MvwswACYFKHHKPeOmAAAAAElFTkSuQmCC')#" />