<xsl:stylesheet 
  xmlns="http://www.w3.org/2000/svg"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:adl="http://www.kite9.org/schema/adl"
	version="2.0">
	
  <xsl:import href="../adl/adl-template.xsl" />
  
  <xsl:template match="/" mode="diagram-defs">
   	<xsl:next-match />
    <clipPath id="iconCircle-100pt">
      <circle r="50pt" cx="50pt" cy="50pt" />
    </clipPath>
   </xsl:template>
   
   <xsl:template match="/" mode="diagram-script">
  	 import '/public/templates/admin/admin.js';
   </xsl:template>
   
   <xsl:template match="/" mode="diagram-element-css">
     <xsl:next-match />
     @import url('/public/templates/admin/admin-elements.css');     
   </xsl:template>
  
   <xsl:template match="/" mode="diagram-palettes">
   </xsl:template>
   
   <xsl:template match="/" mode="diagram-texture-css">
     @import url('/public/templates/admin/admin-textures.css');
   </xsl:template>
   
   
   <xsl:template match="adl:diagram">
   	<xsl:call-template name="formats-container">
   		<xsl:with-param name="k9-elem">diagram</xsl:with-param>
   		<xsl:with-param name="content">
		   	<!--  trail -->
		    <xsl:call-template name="formats-container">
		    	<xsl:with-param name="class">trail</xsl:with-param>
		   		<xsl:with-param name="k9-elem">container</xsl:with-param>
		    	<xsl:with-param name="content">
		      		<xsl:apply-templates select="adl:parents" mode="pill" />
		      		<xsl:apply-templates select="." mode="pill" />
		      	</xsl:with-param>
		     </xsl:call-template>
		        
		     <!-- main -->  
		     <xsl:call-template name="formats-container">
		     	<xsl:with-param name="class">main</xsl:with-param>
		   		<xsl:with-param name="k9-elem">container</xsl:with-param>
		        <xsl:with-param name="content">
		        
		        	<xsl:if test="adl:type='user' or adl:type='organisation'">
					  <xsl:call-template name="formats-container">
					  	<xsl:with-param name="class">grid</xsl:with-param>
		   				<xsl:with-param name="k9-elem">container</xsl:with-param>
		   				<xsl:with-param name="id">repositories</xsl:with-param>
		   				<xsl:with-param name="k9-ui">NewDocument</xsl:with-param>
		   				<xsl:with-param name="content">
		   					<xsl:apply-templates select="adl:repositories" mode="entity" />
		   					<xsl:call-template name="labels-basic">
		   						<xsl:with-param name="text">Repositories</xsl:with-param>
		   					</xsl:call-template>
		   				</xsl:with-param>
					  </xsl:call-template>
			        </xsl:if> 
		    
	    		    <xsl:if test="adl:type='user'">
	    		    	<xsl:call-template name="formats-container">
						  	<xsl:with-param name="class">grid</xsl:with-param>
			   				<xsl:with-param name="k9-elem">container</xsl:with-param>
			   				<xsl:with-param name="id">organisations</xsl:with-param>
			   				<xsl:with-param name="k9-ui"></xsl:with-param>
			   				<xsl:with-param name="content">
			   					<xsl:apply-templates select="adl:organisations" mode="entity" />
			   					<!--  label -->
			   				</xsl:with-param>
						</xsl:call-template>
					</xsl:if>


					<xsl:if test="adl:type='repository' or adl:type='directory'">
					  <xsl:call-template name="formats-container">
					  	<xsl:with-param name="class">grid</xsl:with-param>
		   				<xsl:with-param name="k9-elem">container</xsl:with-param>
		   				<xsl:with-param name="id">documents</xsl:with-param>
		   				<xsl:with-param name="k9-ui">NewDocument</xsl:with-param>
		   				<xsl:with-param name="content">
		   					<xsl:apply-templates select="adl:documents" mode="entity" />
		   					<!--  label -->
		   				</xsl:with-param>
					  </xsl:call-template>
			        </xsl:if> 



				</xsl:with-param> 	 	
		     </xsl:call-template>
   		</xsl:with-param>
   	</xsl:call-template>
   	
   </xsl:template>
   
   <xsl:template match="*" mode="entity">
   	<xsl:call-template name="formats-text-image-portrait">
   	 	<xsl:with-param name="k9-elem">entity</xsl:with-param>
		<xsl:with-param name="id" select="adl:links[adl:rel='self']/adl:href" />
		<xsl:with-param name="k9-ui" select="adl:commands" />
		<xsl:with-param name="href" select="adl:icon/text()" />
 		<xsl:with-param name="text"><text><xsl:value-of select="adl:title/text()" /></text></xsl:with-param>
   	</xsl:call-template>
   </xsl:template>
  
  	<xsl:template match="*" mode="pill">
  		<xsl:call-template name="formats-text-image-portrait">
  			<xsl:with-param name="k9-elem">pill</xsl:with-param>
  			<xsl:with-param name="id" select="adl:links[adl:rel='self']/adl:href" />
  			<xsl:with-param name="k9-ui" select="adl:commands" />
  			<xsl:with-param name="href" select="adl:icon/text()" />
  			<xsl:with-param name="text"><text><xsl:value-of select="adl:title/text()" /></text></xsl:with-param>
  		</xsl:call-template>
	</xsl:template>
  
  
</xsl:stylesheet>