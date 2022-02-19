<?xml version="1.0" encoding="Shift_jis"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
    <body>
      <xsl:apply-templates/>
    </body>
  </html>
</xsl:template>
<xsl:template match="Article">
  <xsl:apply-templates select="title"/>
  <xsl:apply-templates select="tbl"/>
</xsl:template>
<xsl:template match="title">
  <h3><xsl:value-of select="."/></h3>
</xsl:template>
<xsl:template match="tbl">
  <table border="3">
    <tr>
      <th>Year</th>
      <th>Title</th>
      <th>Journal</th>
      <th>Volume</th>
      <th>Number</th>
      <th>Pages</th>
      <th>Authors</th>
    </tr>
    <xsl:variable name="t" select="document(@href)"/>
    <xsl:for-each select="$t//RECORD">
      <tr>
        <td><xsl:value-of select="YEAR"/></td>
        <td><a><xsl:attribute name="href"><xsl:value-of select="URL"/></xsl:attribute><xsl:value-of select="TITLE"/></a></td>
        <td><xsl:value-of select="SECONDARY_TITLE"/></td>
        <td><xsl:value-of select="VOLUME"/></td>
        <td><xsl:value-of select="NUMBER"/></td>
        <td><xsl:value-of select="PAGES"/></td>
        <td>
        <xsl:for-each select="AUTHORS//AUTHOR">
         <xsl:choose>
          <xsl:when test=".='Yamada, R.'">
           <b><xsl:value-of select="."/>,</b>
          </xsl:when>
          <xsl:otherwise>
           <xsl:value-of select="."/>,
          </xsl:otherwise>
         </xsl:choose>
        </xsl:for-each>
        </td>
      </tr>
    </xsl:for-each>
  </table>
</xsl:template>
</xsl:stylesheet>

