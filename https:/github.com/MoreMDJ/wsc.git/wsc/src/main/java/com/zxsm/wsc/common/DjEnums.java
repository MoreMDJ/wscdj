package com.zxsm.wsc.common;

public class DjEnums {

	public enum INFTYPE
	{
		/**
		 * 销售订单头表
		 */
		ORDERINF,
		
		/**
		 * 销售订单行表
		 */
		ORDERGOODSINF,
		
		/**
		 * 销售订单券使用行表
		 */
		ORDERCOUPONINF,
		
		/**
		 * 到店自提单收货时间表
		 */
		ORDERRECEIVEINF,
		
		/**
		 * 销退订单头表
		 */
		RETURNORDERINF,
		
		/**
		 * 销退订单行表
		 */
		RETURNGOODSINF,
		
		/**
		 * 销退单的券退回表
		 */
		RETURNCOUPONINF,
		
		/**
		 * 到店退货单退货时间表
		 */
		RETURNTIMEINF,
		
		/**
		 * 收款表
		 */
		CASHRECEIPTINF,
		
		/**
		 * 退款表
		 */
		CASHREFUNDINF,
		
		/**
		 * WMS
		 */
		WMSWEBSERVICE,
		
		/**
		 * EBS
		 */
		EBSWEBSERVICE
	}
	
	
	/**
	/// <summary>
    /// 统一管理操作枚举
    /// </summary>
    */
    public enum ActionEnum
    {
        /// <summary>
        /// 所有
        /// </summary>
        All,
        /// <summary>
        /// 显示菜单
        /// </summary>
        Show,
        /// <summary>
        /// 查看内容
        /// </summary>
        View,
        /// <summary>
        /// 添加
        /// </summary>
        Add,
        /// <summary>
        /// 修改
        /// </summary>
        Edit,
        /// <summary>
        /// 删除
        /// </summary>
        Delete,
        /// <summary>
        /// 审核
        /// </summary>
        Audit,
        /// <summary>
        /// 回复
        /// </summary>
        Reply,
        /// <summary>
        /// 确认
        /// </summary>
        Confirm,
        /// <summary>
        /// 取消
        /// </summary>
        Cancel,
        /// <summary>
        /// 作废
        /// </summary>
        Invalid,
        /// <summary>
        /// 生成
        /// </summary>
        Build,
        /// <summary>
        /// 安装
        /// </summary>
        Instal,
        /// <summary>
        /// 卸载
        /// </summary>
        UnLoad,
        /// <summary>
        /// 登录
        /// </summary>
        Login,
        /// <summary>
        /// 备份
        /// </summary>
        Back,
        /// <summary>
        /// 还原
        /// </summary>
        Restore,
        /// <summary>
        /// 替换
        /// </summary>
        Replace,
        /// <summary>
        /// 复制
        /// </summary>
        Copy
    }
}
