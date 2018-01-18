
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `${tablename}`
-- ----------------------------
DROP TABLE IF EXISTS `${tablename}`;
CREATE TABLE `${tablename}` (
	<#list fieldList as var>
		<#if var[1] == 'Integer'>
			`${var[0]}` int(11) DEFAULT 0 COMMENT '${var[2]}',
		<#elseif var[1] == 'Date'>
		  	`${var[0]}` datetime DEFAULT NULL COMMENT '${var[2]}',
		<#elseif var[1] == 'Money'>
		  	`${var[0]}` decimal(10,2) DEFAULT NULL COMMENT '${var[2]}',
		<#else>
			`${var[0]}` varchar(50) DEFAULT NULL COMMENT '${var[2]}',
		 </#if>
	</#list>
  		PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
