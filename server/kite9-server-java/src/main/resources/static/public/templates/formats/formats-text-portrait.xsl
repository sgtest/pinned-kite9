<xsl:stylesheet xmlns="http://www.w3.org/2000/svg"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:adl="http://www.kite9.org/schema/adl"
	xmlns:pp="http://www.kite9.org/schema/post-processor" version="1.0">

	<xsl:import href="../back/back-template.xsl" />
  
  <xsl:template name="formats-text-portrait" match="*[@k9-format='text-portrait']">
    <xsl:param name="text"><g k9-elem="caption"><text><xsl:value-of select="text()" /></text></g></xsl:param>
    <xsl:param name="k9-elem" select="local-name()" />
    <xsl:param name="k9-format">text-portrait</xsl:param>
    <xsl:param name="k9-highlight">pulse</xsl:param>
    <xsl:param name="k9-rounding">5pt</xsl:param>
    <xsl:param name="k9-texture">solid</xsl:param>
    <xsl:param name="k9-ui">drag edit delete align connect insert autoconnect</xsl:param>
    <xsl:param name="k9-depiction">
      <xsl:call-template name="back-basic">
        <xsl:with-param name="k9-elem">depiction</xsl:with-param>
        <xsl:with-param name="highlight"></xsl:with-param>
      </xsl:call-template>
    </xsl:param>
    <xsl:param name="k9-shape">
      <xsl:call-template name="back-basic">
        <xsl:with-param name="highlight" select="$k9-highlight" />
        <xsl:with-param name="shape">
          <xsl:call-template name="back-round-rect">
            <xsl:with-param name="rounding" select="$k9-rounding" />
          </xsl:call-template>
        </xsl:with-param> 
      </xsl:call-template>  
    </xsl:param>
    <xsl:param name="k9-decoration"><xsl:apply-templates mode="text-decoration" /></xsl:param>
    <xsl:param name="class" select="@class"/>
    <xsl:param name="attributes" select="@*" />
    <xsl:param name="id" select="@id" />
    
    <g>      
      <xsl:copy-of select="$attributes" />      

      <xsl:attribute name="k9-elem"><xsl:value-of select="$k9-elem" /></xsl:attribute>
      <xsl:attribute name="k9-format"><xsl:value-of select="$k9-format" /></xsl:attribute>
      <xsl:attribute name="k9-texture"><xsl:value-of select="$k9-texture" /></xsl:attribute>
      <xsl:attribute name="k9-ui"><xsl:value-of select="$k9-ui" /></xsl:attribute>

      <xsl:if test="$class">
        <xsl:attribute name="class"><xsl:value-of select="$class" /></xsl:attribute>
      </xsl:if>
      
      <xsl:if test="$id">
        <xsl:attribute name="id"><xsl:value-of select="$id" /></xsl:attribute>
      </xsl:if>
   
      <xsl:comment>pre</xsl:comment> 
      <xsl:copy-of select="$k9-shape" />
      <xsl:comment>content</xsl:comment> 
      <xsl:copy-of select="$k9-depiction" />
      <xsl:copy-of select="$text"/>
      <xsl:comment>post</xsl:comment> 
      <xsl:copy-of select="$k9-decoration" />
    </g>
  </xsl:template>
  
  
  <!-- Editable text object with an icon, where the icon has the k9-shape of the tag of the element. You can edit the text. 
    Links connect to the icon. -->

 <!--  <xsl:template name="formats-captioned-text">
   k9-ui="drag delete align edit cascade" k9-palette="connected"
    k9-format="captioned-text" k9-texture="none">
    <back/>
    <depiction k9-shape="pre:#{local-name()}" k9-ui="connect autoconnect align 
		delete"
      name="pre:#{@id}@dep" k9-texture="solid">
      <back k9-highlight="pulse"/>
    </depiction>
    <caption id="pre:#{@id}@caption" k9-shape="rect" k9-texture="none">
      <back/>
      <textarea k9-ui="text">
        <contents optional="true"/>
      </textarea>
    </caption>
    <shape-align>
      <from reference="pre:#{@id}@dep"/>
      <to reference="pre:#{@id}@caption"/>
    </shape-align>
  </xsl:template>  -->

 <xsl:template name='formats-text-captioned' match="*[@k9-format='text-captioned']">
    <xsl:param name="k9-contains"></xsl:param>
    <xsl:param name="k9-elem" select="local-name()" />
    <xsl:param name="k9-format">text-captioned</xsl:param>
    <xsl:param name="k9-highlight">pulse</xsl:param>
    <xsl:param name="k9-palette">connected</xsl:param>
    <xsl:param name="k9-rounding">5pt</xsl:param>
    <xsl:param name="k9-texture">solid</xsl:param>
    <xsl:param name="k9-ui">drag delete align edit cascade</xsl:param>
    <xsl:param name="depiction">
      <xsl:call-template name="formats-depiction">
        <xsl:with-param name="id"><xsl:value-of select="@id" />@dep</xsl:with-param> 
        <xsl:with-param name="shape">
          <xsl:apply-templates mode="shape" />
        </xsl:with-param>
      </xsl:call-template>  
    </xsl:param>
    
    <xsl:param name="k9-decoration"><xsl:apply-templates mode="text-decoration" /></xsl:param>
    

    <xsl:param name="class" select="@class"/>
    <xsl:param name="attributes" select="@*" />
    <xsl:param name="id" select="@id" />
    
    
    <xsl:call-template name="formats-generic">
      <xsl:with-param name="pre">
        <xsl:call-template name="back-basic">
          <xsl:with-param name="shape">
            <xsl:call-template name="back-round-rect">
              <xsl:with-param name="rounding" select="$k9-rounding" />
            </xsl:call-template>
          </xsl:with-param>
        </xsl:call-template> 
      </xsl:with-param>
      <xsl:with-param name="content">
        <xsl:value-of select="$depiction" />
      </xsl:with-param>
      <xsl:with-param name="post" select="$k9-decoration" />
      
      <!-- standard params -->
      <xsl:with-param name="k9-contains" select="$k9-contains" />
      <xsl:with-param name="k9-elem" select="$k9-elem" />
      <xsl:with-param name="k9-format" select="$k9-format" />
      <xsl:with-param name="k9-palette" select="$k9-palette" />
      <xsl:with-param name="k9-texture" select="$k9-texture" />
      <xsl:with-param name="k9-ui" select="$k9-ui" />
      <xsl:with-param name="attributes" select="$attributes" />
      <xsl:with-param name="class" select="$class" />
    
    </xsl:call-template>
    
  </xsl:template>


	<!-- Icon with text below it. k9-shape of the icon defaults to the tag used. -->
  
<!--   <xsl:template name='portrait-text' k9-ui="drag delete edit align cascade 
		connect autoconnect"
    k9-palette="connected" k9-format="portrait-text">
    <back/>
    <depiction k9-shape="pre:#{local-name()}" k9-texture="solid">
      <back k9-highlight="pulse"/>
    </depiction>
    <caption k9-shape="rect" k9-texture="none">
      <back/>
      <textarea k9-ui="text">
        <contents optional="true"/>
      </textarea>
    </caption>
  </xsl:template> -->


  <!-- default no-match rules for back/decoration modes -->
    
    
      

  <!-- Editable text object with an icon, where the icon has the k9-shape of the tag of the element. You can edit the text. 
    Links connect to the icon. -->

 <!--  <xsl:template name="formats-captioned-text">
   k9-ui="drag delete align edit cascade" k9-palette="connected"
    k9-format="captioned-text" k9-texture="none">
    <back/>
    <depiction k9-shape="pre:#{local-name()}" k9-ui="connect autoconnect align 
    delete"
      name="pre:#{@id}@dep" k9-texture="solid">
      <back k9-highlight="pulse"/>
    </depiction>
    <caption id="pre:#{@id}@caption" k9-shape="rect" k9-texture="none">
      <back/>
      <textarea k9-ui="text">
        <contents optional="true"/>
      </textarea>
    </caption>
    <shape-align>
      <from reference="pre:#{@id}@dep"/>
      <to reference="pre:#{@id}@caption"/>
    </shape-align>
  </xsl:template>  -->

  <!-- Icon with text below it. k9-shape of the icon defaults to the tag used. -->
  
<!--   <xsl:template name='portrait-text' k9-ui="drag delete edit align cascade 
    connect autoconnect"
    k9-palette="connected" k9-format="portrait-text">
    <back/>
    <depiction k9-shape="pre:#{local-name()}" k9-texture="solid">
      <back k9-highlight="pulse"/>
    </depiction>
    <caption k9-shape="rect" k9-texture="none">
      <back/>
      <textarea k9-ui="text">
        <contents optional="true"/>
      </textarea>
    </caption>
  </xsl:template> -->
  

</xsl:stylesheet>
