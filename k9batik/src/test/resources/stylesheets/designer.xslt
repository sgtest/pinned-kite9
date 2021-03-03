<svg:svg xmlns:svg='http://www.w3.org/2000/svg' xmlns='http://www.kite9.org/schema/adl'>
  <svg:defs>
    <svg:linearGradient id='glyph-background' x1='0%' x2='0%' y1='0%' y2='100%'>
      <svg:stop offset='0%' stop-color='#FFF' />
      <svg:stop offset='100%' stop-color='#DDD' />
    </svg:linearGradient>
    
    <svg:filter id="dropshadow" height="130%">
      <svg:feGaussianBlur in="SourceAlpha" stdDeviation="1"/> 
      <svg:feOffset dx="2pt" dy="2pt" /> 
      <svg:feColorMatrix
        type="matrix"
        values="0 0 0 0 .5
                0 0 0 0 .5
                0 0 0 0 .5
                0 0 0 1 0" />
      <svg:feMerge> 
        <svg:feMergeNode/>
        <svg:feMergeNode in="SourceGraphic"/> 
      </svg:feMerge>
    </svg:filter>

    <svg:marker id="circle-marker" markerWidth="6" markerHeight="6" refX="3" refY="3">
      <svg:circle cx="3" cy="3" r="2" class="circle-marker"></svg:circle>
    </svg:marker>
    
    <svg:marker id="diamond-start-marker" markerWidth="8" markerHeight="6" refX="1" refY="3" orient="auto">
      <svg:polygon points="1,3 4,1 7,3 4,5" class="diamond-marker"></svg:polygon>
    </svg:marker>
    
     <svg:marker id="diamond-end-marker" markerWidth="8" markerHeight="6" refX="7" refY="3" orient="auto">
      <svg:polygon points="1,3 4,1 7,3 4,5" class="diamond-marker"></svg:polygon>
    </svg:marker>
    
    <svg:marker id="open-diamond-start-marker" markerWidth="8" markerHeight="6" refX="1" refY="3" orient="auto">
      <svg:polygon points="1,3 4,1 7,3 4,5" class="open-diamond-marker"></svg:polygon>
    </svg:marker>
    
     <svg:marker id="open-diamond-end-marker" markerWidth="8" markerHeight="6" refX="7" refY="3" orient="auto">
      <svg:polygon points="1,3 4,1 7,3 4,5" class="open-diamond-marker"></svg:polygon>
    </svg:marker>

    <svg:marker id="barbed-arrow-end-marker" markerWidth="7" markerHeight="7" refX="6" refY="4" orient="auto">
      <svg:path d="M2,2 L6,4 L2,6" class="barbed-arrow-marker"></svg:path>
    </svg:marker>

    <svg:marker id="barbed-arrow-start-marker" markerWidth="7" markerHeight="7" refX="2" refY="4" orient="auto">
      <svg:path d="M6,2 L2,4 L6,6" class="barbed-arrow-marker"></svg:path>
    </svg:marker>
 
    <svg:marker id="open-arrow-end-marker" markerWidth="7" markerHeight="7" refX="6" refY="4" orient="auto">
      <svg:polygon points="6,4 2,2 2,6" class="open-arrow-marker"></svg:polygon>
    </svg:marker>

    <svg:marker id="open-arrow-start-marker" markerWidth="7" markerHeight="7" refX="2" refY="4" orient="auto">
      <svg:polygon points="2,4 6,2 6,6" class="open-arrow-marker"></svg:polygon>
    </svg:marker>
  
    <svg:marker id="arrow-start-marker" markerWidth="7" markerHeight="7" refX="2" refY="4" orient="auto">
      <svg:polygon points="2,4 6,2 6,6" class="arrow-marker"></svg:polygon>
    </svg:marker>

    <svg:marker id="arrow-end-marker" markerWidth="7" markerHeight="7" refX="6" refY="4" orient="auto">
       <svg:polygon points="6,4 2,2 2,6" class="arrow-marker"></svg:polygon>
   </svg:marker>
    
  </svg:defs>

  <template id='glyph'>
    <back style='--kite9-usage: decal; --kite9-type: svg; '>
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='8' ry='8' style='fill: url(#glyph-background); ' class="glyph-back" />
    </back>
    <svg:g class="glyph">
      <contents optional="true" />
    </svg:g>
  </template>
  
  <template id='glyph-parameterized'>
    <back style='--kite9-usage: decal; --kite9-type: svg; '>
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='8' ry='8' style='fill: url(#glyph-background); ' class="glyph-back" />
    </back>
    <svg:g class="glyph">
      <svg:g class="glyph-label-text">
        <text-label style="--kite9-type: text;" class="glyph-label-text">
      	  <contents xpath="$template-1" type="string" />
        </text-label>
      </svg:g>
      
      <svg:g id="separator" />
      
      <contents xpath="adl:text-lines" optional="true" />
      
    </svg:g>
  </template>
  
  <template id='key'>
    <body>
      <back style='--kite9-usage: decal; --kite9-type: svg;'>
        <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='0' ry='0' class="key-back" />
      </back>
      <contents optional="true" />
    </body>
  </template>

  <template id='arrow'>
    <back style='--kite9-usage: decal; --kite9-type: svg; ' class="arrow-back">
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='4' ry='4' style='fill: black; ' />
    </back>
    <contents optional="true" />
  </template>

  <template id='connection-label'>
    <back style='--kite9-usage: decal; --kite9-type: svg;  '>
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='4' ry='4' class="connection-label-back" />
    </back>
    <svg:g class="connection-label-front">
      <front style="--kite9-type: text; ">
        <contents optional="true" />
      </front>
    </svg:g>
  </template>

  <template id='container-label'>
    <back style="--kite9-usage: decal; --kite9-type: svg;">
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='4' ry='4' class="container-label-back" />
    </back>
    <svg:g class="container-label-front">
      <front style="--kite9-type: text; ">
        <contents optional="true" />
      </front>
    </svg:g>
  </template>
  
  <!-- would be better to use a decal here, and make it the same size as the defined terminator -->
  <template id='empty-terminator'>
      <svg:rect x='0' y='0' width='10.0' height='10.0' style='fill: none; stroke: none ' />
  </template>

  <template id='context'>
    <contents optional="true" />
    <back style='--kite9-usage: decal; --kite9-type: svg;  ' >
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='12' ry='12' class="context-back" />
    </back>
  </template>

  <template id='grid'>
    <back style='--kite9-usage: decal; --kite9-type: svg;  ' >
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' class="grid-back" />
    </back>
    <contents optional="true" />
    <back style='--kite9-usage: decal; --kite9-type: svg;  ' >
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' class="grid-frame" />
    </back>
  </template>
  
  <template id='grid-triangles'>
    <back style='--kite9-usage: decal; --kite9-type: svg;  ' >
      <svg:path d="#{$x0}, #{$y0} #{$x2} #{$y1} #{$x0},#{$y0} z" stroke="red" />
    </back>
    <contents optional="true" />
    <back style='--kite9-usage: decal; --kite9-type: svg;  ' >
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' class="grid-frame" />
    </back>
  </template>
  
  
  <template id='cell'>
    <back style='--kite9-usage: decal; --kite9-type: svg;  '>
      <svg:path d='M 0,#{$height} L0,0 L#{$width},0' class="cell-edge" />
    </back>
    <contents optional="true"  />
  </template>
  
  <template id='show-box'>
    <!-- <back style="- -kite9-usage: decal; - -kite9-type: svg;" >
      <svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='12' ry='12' style="stroke: black; fill: none; stroke-width: 1px;" />
    </back>
    <contents />  -->
  </template>

  <template id="generic-text">
    <!--  svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='2' ry='2' stroke="black" fill="none" stroke-width="1" / -->
    <svg:g class="generic-text">
      <text-label style="--kite9-type: text;">
        <contents optional="true" />
      </text-label>
    </svg:g>
  </template>

  <template id="glyph-stereotype-text">
    <show-box />
    <svg:g class="glyph-stereotype-text">
      <text-label style="--kite9-type: text;">
        <contents optional="true" />
      </text-label>
    </svg:g>
  </template>
  

  <template id="glyph-label-text">
    <show-box />
    <svg:g class="glyph-label-text">
      <text-label style="--kite9-type: text;" class="glyph-label-text">
        <contents optional="true"  />
      </text-label>
    </svg:g>
  </template>

  <template id="arrow-label-text">
    <!-- svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='2' ry='2' stroke="black" fill="none" stroke-width="1" / -->
    <svg:g class="arrow-label-text">
      <text-label style="--kite9-type: text;" class="arrow-label-text">
        <contents optional="true" />
      </text-label>
    </svg:g>
  </template>
  
  <template id="bold-text">
    <!-- svg:rect x='0' y='0' width='#{$width}' height='#{$height}' rx='2' ry='2' stroke="black" fill="none" stroke-width="1" / -->
    <svg:g class="bold-text">
      <text-label style="--kite9-type: text;">
        <contents optional="true" />
      </text-label>
    </svg:g>
  </template>
  
 
  <!-- symbol -->
  <template id="circle">
    <svg:circle style="fill: #00A070;  stroke: none" cx="6" cy="6" r="6" />
    <svg:text x="6" y="9" class="symbol-text"><contents xpath="substring(@theChar,1, 1)" type="string"/></svg:text>
  </template>

  <template id="hexagon">
    <svg:polygon points="0,3 6,0 12,3 12,9 6,12 0,9" style="fill: #AA2030;  stroke: none"/>
    <svg:text x="6" y="9" class="symbol-text"><contents xpath="substring(@theChar,1, 1)"  type="string"/></svg:text>   
  </template>

  <template id="diamond">
    <svg:polygon points="6,0 12,6 6,12 0,6"  style="fill: #BB8040;  stroke: none" />
    <svg:text x="6" y="9" class="symbol-text"><contents xpath="substring(@theChar,1, 1)"  type="string" /></svg:text>
  </template>
  
  <template id="square">
    <svg:rect width="12" height="12" x="0" y="0"  style="fill: #CC8050; stroke: none" />
    <svg:text x="6" y="9" class="symbol-text"><contents xpath="substring(@theChar,1, 1)"  type="string"/></svg:text>
  </template>
  
  <template id="link">
    <contents />
    <link-body style="--kite9-type: svg; --kite9-usage: decal;">
      <svg:path d="#{$path}" class="link" 
        marker-start="pre:url(##{concat(adl:from/@class,'-start-marker')})"
        marker-end="pre:url(##{concat(adl:to/@class,'-end-marker')})" />
    </link-body>
  </template>
  
  <template id="diagram">
    <svg:g filter="url(#dropshadow)">
      <contents optional="true" />
    </svg:g>
  </template>
  

</svg:svg>