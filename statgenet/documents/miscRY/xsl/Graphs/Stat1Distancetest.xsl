<?xml version="1.0" encoding="Shift_jis"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
    <body>
      <xsl:apply-templates/>
    </body>
  </html>
</xsl:template>
<xsl:template match="Stat1">
  <xsl:apply-templates select="title"/>
  <xsl:apply-templates select="tbl"/>
</xsl:template>
<xsl:template match="title">
  <h3><xsl:value-of select="."/></h3>
</xsl:template>
<xsl:template match="tbl">
  <table border="3">
   


    <xsl:variable name="p" select="document(@href)"/>

       <tr>
        <th colspan="7">Haploype 1</th>
       
        
        
        <xsl:for-each select="$p//Graph/DistanceMatrix/ID">
        <th><xsl:value-of select="StartHp1"/></th>
        </xsl:for-each>
        
      </tr>
      <tr>
         <th colspan="7">Haploype 2</th>

        <xsl:for-each select="$p//Graph/DistanceMatrix/ID">
        <th><xsl:value-of select="StartHp2"/></th>
        </xsl:for-each>
      </tr>
 <tr>
      <th>Iteration</th>
      <th>No.Node</th>
      <th>No.Root</th>
      <th>No.Mut</th>
      <th>No.Rec</th>
      <th>No.Edge</th>
      <th>No.Event</th>

    </tr>
    <xsl:variable name="t" select="document(@href)"/>
    <xsl:for-each select="$t//Graph">

      <tr>
        <td><xsl:value-of select="id"/></td>
        <td><xsl:value-of select="TotalNode"/></td>
        <td><xsl:value-of select="RootNode"/></td>
        <td><xsl:value-of select="MutNode"/></td>
        <td><xsl:value-of select="RecNode"/></td>

        <td><xsl:value-of select="Edge"/></td>
        <td><xsl:value-of select="RetroEvent"/></td>

        
        <xsl:for-each select="DistanceMatrix/ID">
        <td><xsl:value-of select="Distance1"/></td>
        </xsl:for-each>
        <xsl:for-each select="DistanceMatrix/ID">
        <td><xsl:value-of select="Distance2"/></td>
        </xsl:for-each>
      </tr>
    </xsl:for-each>
  </table>
</xsl:template>
</xsl:stylesheet>