package bean

case class OrderInfo(
                    id:Long,
                    province_id:String,
                    order_status:String,
                    user_id:Long,
                    final_total_amount:Double,
                    benefit_reduce_amount:Double,
                    original_total_amount:Double,
                    feight_fee:Double,
                    expire_time:String,
                    create_time:String,
                    operate_time:String,
                    var create_date:String,
                    var create_hour:String,
                    var if_first_order:String,
                    var province_name:String,
                    var province_area_code:String,
                    var user_age_group:String, //20及以下 21-30岁 30以上
                    var user_gender:String // 男 女
                    )
