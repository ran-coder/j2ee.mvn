<#assign name>赋值给name变量</#assign>	${name}
<#assign name="赋值给name变量">	${name}
--------------------------------------------
<#global x=1,y=2>	${x},${y}
<#assign x=2>	${.globals.x},${x},${y}
--------------------------------------------
<#-- setting number_format="currency" /-->
<#assign price = 42 />
${price}
${price?string}
${price?string.number}
${price?string.currency}
${price?string.percent}

mX:小数部分最小X位,MX:小数部分最大X位
<#assign x=2.50854/>
<#assign z=2.50254/>
<#assign y=4 />
#{x;M2};#{x;M1};#{x;m1};#{x;m2};#{x;m1M2};
#{z;M2};#{z;M1};#{z;m1};#{z;m2};#{z;m1M2};
#{y;M2};#{y;m1};#{y;m1M2};

<#assign lastUpdated = "2009-01-07 15:05"?datetime("yyyy-MM-dd HH:mm") />
  ${lastUpdated?string("yyyy-MM-dd HH:mm:ss")};
  ${lastUpdated?string("EEE,MMM d,yy")};
  ${lastUpdated?string("EEEE,MMMM dd,yyyy,hh:mm:ss a '('zzz')'")};
  ${lastUpdated?string.short};
  ${lastUpdated?string.long};
  ${lastUpdated?string.full};
item_index:是list当前值的下标,item_has_next:判断list是否还有值<br>
<#assign seq = ["winter", "spring", "summer", "autumn"]>
<#list seq as x>
  ${x_index}-${x}<#if x_has_next>,</#if>
</#list>

