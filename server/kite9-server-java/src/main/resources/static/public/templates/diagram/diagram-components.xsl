
<xsl:stylesheet
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:adl="http://www.kite9.org/schema/adl"
        xmlns:pp="http://www.kite9.org/schema/post-processor"
        version="2.0">

  <xsl:import href="../formats/formats-components.xsl"/>
  
  <xsl:template name="unique">
  	<xsl:param name="tags"/>
	  	
  	 <xsl:for-each-group select="$tags/*" group-by="text()">
		     <xsl:text>
		     </xsl:text><xsl:value-of select="current-grouping-key()"/>
	 </xsl:for-each-group>
  </xsl:template>

  <xsl:template name="diagram-root-svg" match="/">
     
      <xsl:param name="css">
      	<xsl:call-template name="unique">
      		<xsl:with-param name="tags">
	      		<xsl:apply-templates select="/" mode="diagram-element-css" />
	        	<xsl:apply-templates select="/" mode="diagram-texture-css" />
        	</xsl:with-param>
        </xsl:call-template>
      </xsl:param>
      
      <xsl:param name="script"><xsl:apply-templates select="/" mode="diagram-script" /></xsl:param>
      <xsl:param name="constants"><xsl:apply-templates select="/" mode="diagram-constants" /></xsl:param>
      <xsl:param name="palettes"><xsl:apply-templates select="/" mode="diagram-palettes" /></xsl:param>
      <xsl:param name="defs"><xsl:apply-templates select="/" mode="diagram-defs" /></xsl:param>
      <xsl:param name="content"><xsl:apply-templates /></xsl:param>
      <svg>
          <xsl:attribute name="pp:width">$width</xsl:attribute>
          <xsl:attribute name="pp:height">$height</xsl:attribute>
          <defs>
              <style type="text/css">
                  <xsl:copy-of select="$css"/>
              </style>
              <xsl:copy-of select="$defs"/>
          </defs>

          <xsl:copy-of select="$content" />

          <defs>
              <script>
                 document.params = {
                 "palettes" : [
                    <xsl:for-each select="$palettes/*">
                        "<xsl:value-of select="@url"/>",  "<xsl:value-of select="@contains"/>",                  
                    </xsl:for-each>
                   ],
                 <xsl:for-each select="$constants/*">
                  "<xsl:value-of select="@name" />": "<xsl:value-of select="@url"/>",
                 </xsl:for-each>};
              </script>
              <script type="module">
                  <xsl:copy-of select="$script"/>
              </script>
          </defs>
      </svg>
  </xsl:template>

  <xsl:template name="diagram-basic">
    <xsl:call-template name="formats-container">
      <xsl:with-param name="k9-highlight"></xsl:with-param>
      <xsl:with-param name="k9-texture">none</xsl:with-param>
      <xsl:with-param name="k9-ui">layout label</xsl:with-param>
      <xsl:with-param name="k9-rounding">0</xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="/" mode="diagram-element-css">
    <xsl:next-match />
    <adl:css>@import url('/public/templates/diagram/diagram-elements.css');</adl:css>
  </xsl:template>

  <xsl:template match="adl:diagram">
    <xsl:call-template name="diagram-basic" />
  </xsl:template>

  <xsl:template match="text()" mode="diagram-defs" />
  <xsl:template match="text()" mode="diagram-script" />
  <xsl:template match="text()" mode="diagram-element-css" />
  <xsl:template match="text()" mode="diagram-texture-css" />
  <xsl:template match="text()" mode="diagram-constants" />
  <xsl:template match="text()" mode="diagram-palettes" />
   

</xsl:stylesheet>