<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Checkstyle Report</title>
                <style>
                    body {font-family: Arial, sans-serif; margin: 40px;}
                    table {width: 100%; border-collapse: collapse; margin-bottom: 40px;}
                    th, td {border: 1px solid #ddd; padding: 8px;}
                    th {background-color: #f2f2f2;}
                    tr:nth-child(even) {background-color: #f9f9f9;}
                    tr:hover {background-color: #f1f1f1;}
                    .severity-error {color: red; font-weight: bold;}
                    .severity-warning {color: orange; font-weight: bold;}
                    .severity-info {color: blue; font-weight: bold;}
                </style>
            </head>
            <body>
                <h1>Checkstyle Report</h1>
                <table>
                    <tr>
                        <th>File</th>
                        <th>Line</th>
                        <th>Severity</th>
                        <th>Message</th>
<!--                        <th>Source</th>-->
                    </tr>
                    <xsl:for-each select="//file">
                        <xsl:for-each select="error">
                            <tr>
                                <td><xsl:value-of select="../@name"/></td>
                                <td><xsl:value-of select="@line"/></td>
                                <td><xsl:value-of select="@severity"/></td>
                                <td><xsl:value-of select="@message"/></td>
<!--                                <td><xsl:value-of select="@source"/></td>-->
                            </tr>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
