package j2ee.research.role.type;

import java.util.Arrays;
import java.util.BitSet;

import org.apache.commons.codec.binary.Base64;

import utils.codec.ByteUtil;

/**  
 * @author yuanwei  
 * @version ctreateTime:2013-1-7 下午4:04:03
 *   
 */
public enum Role {
	ADMIN("ADMIN")
	,USER("USER")
	,ANONYMOUS("ANONYMOUS")
	,BATCHSTOCKOUT("批量发货库管员")
	,BALKSTOCKOUT("零散发货库管员")
	,FINANCE("FINANCE STAFF")
	,CALLCENTER("电话销售专员")
	,LEADER("CHIEF, LEADER")
	,SALESMANAGER("销售经理")
	,CUSTOMERSERVICER("客户服务专员")
	,CASHIER("出纳")
	,ACCOUNTANT("会计")
	,PRODUCTMANAGER("产品经理")
	,CUSTOMERSERVICEMANAGER("客户服务部经理")
	,TRIALORDERMANGER("试用品管理员，用来管理试用品的创建等工作")
	,WEBSITEMANAGER("增加能查看所有订单，但不能处理订单")
	,DEPARTMENTMANAGER("查看、确认免费订单")
	,RESELLERSALE("渠道销售")
	,RESELLERASSISTANT("渠道助理")
	,RESELLERMANAGER("渠道经理")
	,APPLAYCARD("申请产品码")
	,APPROVECARD("审批产品码")
	,DOWNLOADCARD("下载产品码")
	,MODIFYCARD("维护产品码")
	,DOWNLOADLOGISTICCARD("下载物流码")
	,ECQUESTION("ECQUESTION")
	,TRIALSHIPORDER("试用品发货库管员")
	,SMS("发短信")
	,INVOICE("发票专员")
	,CARDVIP("VIP卡导出")
	,ROLE_SAAS_USER("代理商系统用户")
	,ROLE_SAAS_ADMIN("代理商系统管理员")
	,ROLE_SAAS_RESELLERMANAGER("代理商系统渠道")
	,RECOMMENDMANAGER("友荐管理")
	;
	private String desc;
	private Role(String desc){
		this.desc=desc;
	}
	@Override
	public String toString() {
		return this.ordinal()+"-"+this.desc;
	}
	public static BitSet hashcode(Enum<?>... enums){
		if(enums!=null&&enums.length>0){
			BitSet set=new BitSet();
			for(Enum<?> e:enums){
				set.set(e.ordinal());
			}
			int cardinality=set.cardinality();
			System.out.println(ByteUtil.toBinaryString(set.toByteArray())+":"+set.toString()+"="+cardinality);
			System.out.println(Arrays.toString(set.toLongArray())+","+Base64.encodeBase64URLSafeString(set.toByteArray()));
			//System.out.println(ByteUtil.toBinaryString(set.hashCode())+":"+set.hashCode());
			return set;
		}
		return null;
	}
	public static void main(String[] args) {
		hashcode(ADMIN,USER,ANONYMOUS,ROLE_SAAS_RESELLERMANAGER);
		hashcode(USER,ANONYMOUS,ADMIN);
		hashcode(ANONYMOUS,ADMIN);
		hashcode(ADMIN);
		hashcode(ADMIN,RECOMMENDMANAGER,APPLAYCARD);
		hashcode(CASHIER,ROLE_SAAS_USER,ROLE_SAAS_RESELLERMANAGER);
	}
}
