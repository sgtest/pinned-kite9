<xsl:stylesheet xmlns="http://www.w3.org/2000/svg"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:adl="http://www.kite9.org/schema/adl"
	xmlns:pp="http://www.kite9.org/schema/post-processor" version="1.0">

	<xsl:template match="adl:box">
		<xsl:call-template name="formats-container">
	     <xsl:with-param name="k9-rounding">5pt</xsl:with-param>	
		</xsl:call-template>
	</xsl:template>
 
</xsl:stylesheet>
        
        
        
