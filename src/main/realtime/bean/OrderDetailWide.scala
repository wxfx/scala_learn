package bean

case class OrderDetailWide(var order_detail_id:Long = 0L,
                           var order_id:Long = 0L,
                           var order_status:String=null,
                           var create_time:String=null,
                           var user_id:Long = 0L,
                           var sku_id:Long = 0L,
                           var sku_price:Double=0D,
                           var sku_num:Long=0L,
                           var sku_name:String=null,
                           var benefit_reduce_amount:Double=0D,
                           var original_total_amount:Double=0D, //订单主表中的原始交易额=所有明细单价+数量的汇总值
                           var feight_fee:Double=0D,
                           var final_total_amount:Double=0D, //订单主表中的实际付款总额
                           var final_detail_amount:Double=0D, //从表中的实际分摊总额
                           var if_first_order:String = null,
                           var province_name:String=null,
                           var province_area_code:String=null,
                           var user_age_group:String = null,
                           var user_gender:String=null,
                           var dt:String = null,
                           var spu_id:Long = 0L,
                           var tm_id:Long = 0L,
                           var category3_id:Long = 0L,
                           var spu_name:String = null,
                           var tm_name:String=null,
                           var category3_name:String=null
                          )
